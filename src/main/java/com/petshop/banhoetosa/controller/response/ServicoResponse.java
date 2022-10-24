package com.petshop.banhoetosa.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServicoResponse {

    private Long id;
    private String descricaoServico;
    private BigDecimal preco;

}
