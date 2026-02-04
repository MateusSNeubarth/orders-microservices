package com.mateusneubarth.icompras.pedidos.model;

import com.mateusneubarth.icompras.pedidos.model.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;
}
