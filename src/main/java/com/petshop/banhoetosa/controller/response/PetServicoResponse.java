package com.petshop.banhoetosa.controller.response;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PetServicoResponse {

    @NotNull
    private Long idPet;
    @NotNull
    private Long idServico;
    private StatusServicoEnum statusServico;
    private StatusPagamentoEnum statusPagamento;

}
