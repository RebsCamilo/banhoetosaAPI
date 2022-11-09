package com.petshop.banhoetosa.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ServicoResponse {

    private Long id;
    private String descricaoServico;
    private BigDecimal preco;

}
