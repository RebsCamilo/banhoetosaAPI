package com.petshop.banhoetosa.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ServicoRequest {

    @NotNull
    @NotEmpty
    private String descricaoServico;
    @NotNull
    private BigDecimal preco;

}
