package com.petshop.banhoetosa.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TutorRequest {
    
    //usando a anotação @JsonIgnore e @JsonProperty podemos usar o mesmo DTO para request e response (Validar com Florian)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private Long id;
    
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
