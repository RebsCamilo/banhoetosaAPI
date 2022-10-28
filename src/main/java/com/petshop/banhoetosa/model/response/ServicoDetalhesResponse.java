package com.petshop.banhoetosa.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ServicoDetalhesResponse {

    private Long id;
    private String descricaoServico;
    private BigDecimal preco;
    private LocalDateTime dataCadastro;

}
