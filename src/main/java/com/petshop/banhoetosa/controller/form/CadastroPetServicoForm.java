package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CadastroPetServicoForm {

    @NotNull
    private Long idPet;
    @NotNull
    private Long idServico;
    private StatusServicoEnum statusServico;
    private StatusPagamentoEnum statusPagamento;

    public PetServico converter() {
        PetServico petServico = new PetServico();
        return petServico;
    }

}
