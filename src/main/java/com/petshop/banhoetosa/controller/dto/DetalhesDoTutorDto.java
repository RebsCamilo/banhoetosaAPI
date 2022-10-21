package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Tutor;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class DetalhesDoTutorDto {
    private Long id;
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;
    private String rua;
    private Integer numero;
    private String bairro;
    private String complemento;
    private String cep;
    private LocalDateTime dataCadastro;
//    List<String> nomeBairro;
//    List<String> pets;


    public DetalhesDoTutorDto(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.telefone1 = tutor.getTelefone1();
        this.telefone2 = tutor.getTelefone2();
        this.email = tutor.getEmail();
        this.dataCadastro = tutor.getDataCadastro();
        this.rua = tutor.getEndereco().getRua();
        this.numero = tutor.getEndereco().getNumero();
        this.bairro = tutor.getEndereco().getBairro();
        this.complemento = tutor.getEndereco().getComplemento();
        this.cep = tutor.getEndereco().getCep();
    }
}


