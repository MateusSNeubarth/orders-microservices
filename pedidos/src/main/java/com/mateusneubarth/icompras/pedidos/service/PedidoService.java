package com.mateusneubarth.icompras.pedidos.service;

import com.mateusneubarth.icompras.pedidos.client.ClientesClient;
import com.mateusneubarth.icompras.pedidos.client.ProdutosClient;
import com.mateusneubarth.icompras.pedidos.client.ServicoBancarioClient;
import com.mateusneubarth.icompras.pedidos.client.representation.ClienteRepresentation;
import com.mateusneubarth.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.mateusneubarth.icompras.pedidos.model.DadosPagamento;
import com.mateusneubarth.icompras.pedidos.model.ItemPedido;
import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.model.enums.StatusPedido;
import com.mateusneubarth.icompras.pedidos.model.enums.TipoPagamento;
import com.mateusneubarth.icompras.pedidos.model.exception.ItemNaoEcontradoException;
import com.mateusneubarth.icompras.pedidos.publisher.PagamentoPublisher;
import com.mateusneubarth.icompras.pedidos.repository.ItemPedidoRepository;
import com.mateusneubarth.icompras.pedidos.repository.PedidoRepository;
import com.mateusneubarth.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        pedidoValidator.validar(pedido);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        pedido.setTotal(
                pedido.getItens().stream()
                        .map(item ->
                                item.getValorUnitario()
                                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
                        )
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
        return pedido;
    }

    public void atualizarStatusPagamento(
            Long codigoPedido,
            String chavePagamento,
            Boolean success,
            String observacoes
    ) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);
        if (pedidoEncontrado.isEmpty()) {
            String message = String.format("Pedido não encontrado para o código %d e chave de pagamento %s.", codigoPedido, chavePagamento);
            log.error(message);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();
        if (success) {
            prepararEPublicarPedidoPago(pedido);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        pedidoRepository.save(pedido);
    }

    private void prepararEPublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()) {
            log.error("Pedido não encontrado para o código: {}", codigoPedido);
            throw new ItemNaoEcontradoException("Pedido não encontrado para o código: " + codigoPedido);
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = DadosPagamento.builder()
                .tipoPagamento(tipoPagamento)
                .dados(dadosCartao)
                .build();

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando processamento.");

        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);

        pedidoRepository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigoPedido) {
        Optional<Pedido> pedido = pedidoRepository.findById(codigoPedido);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        ResponseEntity<ClienteRepresentation> response = apiClientes.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item) {
        Long codigoProduto = item.getCodigoProduto();
        ResponseEntity<ProdutoRepresentation> response = apiProdutos.obterDados(codigoProduto);
        item.setNome(response.getBody().nome());
    }
}
