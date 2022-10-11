package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Pet;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class DetalhesDoPetDto {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private String detalhe;
    private String nomeTutor;
    private LocalDateTime dataCadastro;
    private Boolean status;
    private List<PetServicoDto> listaPetServicos;


    public DetalhesDoPetDto(Pet pet) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.especie = pet.getEspecie();
        this.raca = pet.getRaca();
        this.idade = pet.getIdade();
        this.detalhe = pet.getDetalhe();
        this.nomeTutor = pet.getTutor().getNome();
        this.status = pet.getStatus();
        this.dataCadastro = pet.getDataCadastro();
        this.listaPetServicos = new ArrayList<>();

        this.listaPetServicos.addAll(pet.getPetServicos()
                    .stream()
                    .map(PetServicoDto::new)
                    .collect(Collectors.toList())

        );

//        this.listaPetServicos.addAll(pet.getPetServicos() //teria que pegar o PetServicoDto
//                        .stream()
//                        .filter(p -> p.equals())
//                        .map(PetServicoDto::new)
//                        .collect(Collectors.toList())
//        );

    }

}


