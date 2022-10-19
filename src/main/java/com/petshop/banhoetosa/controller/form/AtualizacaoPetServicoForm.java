package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.ServicoRepository;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AtualizacaoPetServicoForm {

//    private Long idPet;
//    private Long idServico;
    @NotNull
    private StatusServicoEnum statusServico;
    @NotNull
    private StatusPagamentoEnum statusPagamento;


    public PetServico converter() {
        return new PetServico(statusServico, statusPagamento);
    }

}
