package com.petshop.banhoetosa.controller.request;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PetServicoRequest {

    @NotNull
    private Long idPet;
    @NotNull
    private Long idServico;
    private StatusServicoEnum statusServico;
    private StatusPagamentoEnum statusPagamento;

}
