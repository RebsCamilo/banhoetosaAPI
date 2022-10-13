package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Tutor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class DetalhesDoTutorDto {
    private Long id;
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;
    private LocalDateTime dataCadastro;
//    List<String> nomeBairro;
//    List<String> pets;


    public DetalhesDoTutorDto(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.telefone1 = tutor.getTelefone1();
        this.telefone2 = tutor.getTelefone2();
        this.email = tutor.getEmail();
        this.dataCadastro = tutor.getDataCadastro();

//        this.detalhe = tutor.getDetalhe();
//        this.nomeTutor = tutor.getTutor().getNome();
        //        this.listaPetServicos = new ArrayList<>();
//
//        this.listaPetServicos.addAll(pet.getPetServicos() //teria que pegar o PetServicoDto
//                        .stream()
//                        .filter(p -> p.equals())
//                        .map(PetServicoDto::new)
//                        .collect(Collectors.toList())
//        );
    }
}


