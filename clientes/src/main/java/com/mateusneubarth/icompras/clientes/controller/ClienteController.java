package com.mateusneubarth.icompras.clientes.controller;

import com.mateusneubarth.icompras.clientes.model.Cliente;
import com.mateusneubarth.icompras.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        clienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> obterPorCodigo(@PathVariable Long codigo) {
        return clienteService.obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
