package com.petshop.banhoetosa.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TutorResponse {

    private Long id;
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;
    private List<String> nomePets;
//    private String nomeBairro;

}