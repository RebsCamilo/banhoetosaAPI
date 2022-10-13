package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Pet;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//If you want an immutable data transfer object, annotate it as @Value instead.
@Value //Ao inves de @Data, usa-se @Value para data transfer objects imutaveis (não gera setters)
//@NoArgsConstructor
//@AllArgsConstructor
public class EnderecoDto { //apenas classes primitivas do Java, não classes criadas por voce

    private Long id;
    private String rua;
    private Integer numero;
    private String bairro;
    private String complemento;
    private String cep;
    private LocalDateTime dataCadastro;

    public EnderecoDto(Endereco endereco) {
        this.id = endereco.getId();
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.complemento = endereco.getComplemento();
        this.cep = endereco.getCep();
        this.dataCadastro = endereco.getDataCadastro();
    }

//    public static List<EnderecoDto> converter(List<Endereco> endereco) {
//        return endereco.stream()
//                .map(EnderecoDto::new)
//                .collect(Collectors.toList()); //converte uma lista de Pet em lista de PetDto
//    }

}

//This will generate a getter but no setter for the id, and a getter and setter for the name
//    @Data
//    public class Exemplo {
//        @Setter(AccessLevel.NONE)
//        private Long id;
//        private String name;
//    }

