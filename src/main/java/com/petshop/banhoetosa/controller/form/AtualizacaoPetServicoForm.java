package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.PetServico;
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

    public PetServico atualizar(Long id, PetServicoRepository petServicoRepository, PetRepository petRepository, ServicoRepository servicoRepository) {
        PetServico petServico = petServicoRepository.getReferenceById(id);

//        petServico.setPet(petRepository.getReferenceById(this.idPet));
//        petServico.setServico(servicoRepository.getReferenceById(this.idServico));
        petServico.setStatusServico(this.statusServico);
        petServico.setStatusPagamento(this.statusPagamento);

        return petServico;
    }


}
