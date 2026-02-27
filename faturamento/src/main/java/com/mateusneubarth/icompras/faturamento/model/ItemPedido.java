package com.mateusneubarth.icompras.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ItemPedido{

    private Long codigo;
    private String nome;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;
}