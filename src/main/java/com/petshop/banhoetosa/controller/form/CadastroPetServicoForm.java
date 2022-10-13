package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.ServicoRepository;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class CadastroPetServicoForm {

    @NotNull
    private Long idPet;
    @NotNull
    private Long idServico;
    private StatusServicoEnum statusServico;
    private StatusPagamentoEnum statusPagamento;


    public PetServico converter(PetRepository petRepository, ServicoRepository servicoRepository) {
        Optional<Pet> pet = petRepository.findById(idPet);
        Optional<Servico> servico = servicoRepository.findById(idServico);
        System.out.println(servico.get().getId() + " " + servico.get().getDescricaoServico());
        PetServico petServico = new PetServico(pet.get(), servico.get(), statusServico, statusPagamento);
        return petServico;
    }


}
