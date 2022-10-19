package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CadastroPetForm {

    @NotNull @NotEmpty
    private String nome;
    private String especie;
    @NotNull @NotEmpty @Length(min = 3, max = 60)
    private String raca;
    @NotNull
    private Integer idade;
    @Length(max = 255)
    private String detalhe;
    @NotNull @NotEmpty
    private String emailTutor;

//    public Pet converter(TutorRepository tutorRepository) {
//        Tutor tutor = tutorRepository.findByNome(emailTutor);
//        //encontra o tutor no bd com o nome passado no cadastro
//        Pet pet = new Pet(nome, especie, raca, idade, detalhe, tutor); //cria o objeto Pet a ser retornado
//        return pet;
//    }

    public Pet converter() {
        Pet pet = new Pet(nome, especie, raca, idade, detalhe);
        return pet;
    }

}

