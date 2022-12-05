package com.petshop.banhoetosa.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetResponse {

    private Long id;
    private String nome;
    private String raca;

}
