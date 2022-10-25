package com.petshop.banhoetosa.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PetDetalhesResponse {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private String detalhe;
    private String nomeTutor;
    private LocalDateTime dataCadastro;
    private List<String> listaPetServicos;

}
