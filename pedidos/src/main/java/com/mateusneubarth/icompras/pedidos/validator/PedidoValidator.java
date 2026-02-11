package com.mateusneubarth.icompras.pedidos.validator;

import com.mateusneubarth.icompras.pedidos.client.ClientesClient;
import com.mateusneubarth.icompras.pedidos.client.ProdutosClient;
import com.mateusneubarth.icompras.pedidos.client.representation.ClienteRepresentation;
import com.mateusneubarth.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.mateusneubarth.icompras.pedidos.model.ItemPedido;
import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.model.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            ResponseEntity<ClienteRepresentation> response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente encontrado: {}", cliente);
        } catch (FeignException.NotFound e) {
            String message = String.format("Cliente com código %d não encontrado", codigoCliente);
            log.error(message);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item) {
        try {
            ResponseEntity<ProdutoRepresentation> response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto encontrado: {}", produto);
        } catch (FeignException.NotFound e) {
            String message = String.format("Produto com código %d não encontrado", item.getCodigoProduto());
            log.error(message);
            throw new ValidationException("codigoProduto", message);
        }
    }
}
