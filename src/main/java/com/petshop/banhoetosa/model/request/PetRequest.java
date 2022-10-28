package com.petshop.banhoetosa.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PetRequest {

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

}
