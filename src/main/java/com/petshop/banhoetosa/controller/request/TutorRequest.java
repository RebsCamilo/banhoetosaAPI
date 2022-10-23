package com.petshop.banhoetosa.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TutorRequest {

    @NotNull
    @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String telefone1;
    private String telefone2;
    @NotNull @NotEmpty
    private String email;
    //cadastrar endereco
    private String rua;
    private Integer numero;
    @NotNull @NotEmpty
    private String bairro;
    private String complemento;
    private String cep;
}
