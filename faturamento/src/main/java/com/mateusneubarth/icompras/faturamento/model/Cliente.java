package com.mateusneubarth.icompras.faturamento.model;

import lombok.Builder;

@Builder
public record Cliente(
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone
) {
}
