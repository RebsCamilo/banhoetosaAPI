package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//If you want an immutable data transfer object, annotate it as @Value instead.
@Value //Ao inves de @Data, usa-se @Value para data transfer objects imutaveis (não gera setters)
//@NoArgsConstructor
//@AllArgsConstructor
public class PetDto { //apenas classes primitivas do Java, não classes criadas por voce

    private Long id;
    private String nome;
    private String raca;
    private LocalDateTime dataCadastro;

    public PetDto(Pet pet) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.raca = pet.getRaca();
        this.dataCadastro = pet.getDataCadastro();
    }

    public static List<PetDto> converter(List<Pet> pets) {
        return pets.stream()
                .map(PetDto::new)
                .collect(Collectors.toList()); //converte uma lista de Pet em lista de PetDto
    }

}

//This will generate a getter but no setter for the id, and a getter and setter for the name
//    @Data
//    public class Exemplo {
//        @Setter(AccessLevel.NONE)
//        private Long id;
//        private String name;
//    }

