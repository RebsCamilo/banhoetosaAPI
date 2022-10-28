package com.petshop.banhoetosa.model.request;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PetServicoUpdateRequest {

    @NotNull
    private StatusServicoEnum statusServico;
    @NotNull
    private StatusPagamentoEnum statusPagamento;

}
