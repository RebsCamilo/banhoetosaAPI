package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
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


    public Pet converter() {
        Pet pet = new Pet(this.nome, this.especie, this.raca, this.idade, this.detalhe);
        return pet;
    }
}
