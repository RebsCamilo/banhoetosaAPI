package com.petshop.banhoetosa.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TutorDetalhesResponse {
//    private Long tutorId;
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
    private List<String> petsNome;

}


