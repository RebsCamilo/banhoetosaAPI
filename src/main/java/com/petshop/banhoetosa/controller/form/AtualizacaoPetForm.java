package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.repository.PetRepository;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AtualizacaoPetForm {

    @NotNull
    @NotEmpty
    private String nome;
    private String especie;
    @NotNull @NotEmpty @Length(min = 3, max = 60)
    private String raca;
    @NotNull
    private Integer idade;
    @Length(max = 255)
    private String detalhe;

    public Pet atualizar(Long id, PetRepository petRepository) {
        Pet pet = petRepository.getReferenceById(id);

        pet.setNome(this.nome);
        pet.setEspecie(this.especie);
        pet.setRaca(this.raca);
        pet.setIdade(this.idade);
        pet.setDetalhe(this.detalhe);

        return pet;
    }
}
