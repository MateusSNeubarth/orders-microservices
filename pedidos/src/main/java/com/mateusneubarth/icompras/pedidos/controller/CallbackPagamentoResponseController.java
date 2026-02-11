package com.mateusneubarth.icompras.pedidos.controller;

import com.mateusneubarth.icompras.pedidos.controller.dto.CallbackPagamentoResponseDTO;
import com.mateusneubarth.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos/callback-pagamentos")
@RequiredArgsConstructor
public class CallbackPagamentoResponseController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody CallbackPagamentoResponseDTO request,
            @RequestHeader(required = true, name = "apiKey") String apiKey
    ) {
        pedidoService.atualizarStatusPagamento(
                request.codigo(),
                request.chavePagamento(),
                request.status(),
                request.observacoes()
        );
        return ResponseEntity.ok().build();
    }
}
