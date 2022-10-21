package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AtualizacaoTutorForm {
    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String telefone1;
    private String telefone2;
    private String email;
    //atualizar endereco
    private String rua;
    private Integer numero;
    @NotNull @NotEmpty
    private String bairro;
    private String complemento;
    private String cep;


    public Tutor converter() {
        Endereco endereco = new Endereco(rua, numero, bairro, complemento, cep);
        return new Tutor(nome, telefone1, telefone2, email, endereco);
    }

//    public Tutor atualizar(Long id, TutorRepository tutorRepository) {
//        Tutor tutor = tutorRepository.getReferenceById(id);
//
//        tutor.setNome(this.nome);
//        tutor.setTelefone1(this.telefone1);
//        tutor.setTelefone2(this.telefone2);
//        tutor.setEmail(this.email);
//
//        return tutor;
//    }
}
