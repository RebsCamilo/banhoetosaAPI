package com.petshop.banhoetosa.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ServicoDetalhesResponse {

    private Long id;
    private String descricaoServico;
    private BigDecimal preco;
    private LocalDateTime dataCadastro;

}
