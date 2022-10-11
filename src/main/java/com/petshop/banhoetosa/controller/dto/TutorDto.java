package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class TutorDto {

    private Long id;
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;
    private LocalDateTime dataCadastro;
    private List<String> nomePets;
    private List<String> NomeBairro;

    public TutorDto(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.telefone1 = tutor.getTelefone1();
        this.telefone2 = tutor.getTelefone2();
        this.email = tutor.getEmail();
        this.dataCadastro = tutor.getDataCadastro();

        this.nomePets = new ArrayList<>();
        if(!nomePets.isEmpty()) {
            this.nomePets.addAll(tutor.getPets()
                    .stream()
                    .map(Pet::getNome)
                    .collect(Collectors.toList())
            );
        }
        this.NomeBairro = new ArrayList<>();
        if(!NomeBairro.isEmpty()) {
            this.NomeBairro.addAll(tutor.getEnderecos()
                .stream()
                .map(Endereco::getBairro)
                .collect(Collectors.toList())
            );
        }

    }


//    public TutorDto(Tutor tutor) {
//        this.nome = tutor.getNome();
//        this.telefone1 = tutor.getTelefone1();
//        this.telefone2 = tutor.getTelefone2();
//        this.email = tutor.getEmail();
//        this.dataCadastro = tutor.getDataCadastro();
//
//        this.nomeEndereco = new ArrayList<>();
//        this.nomeEndereco.addAll(tutor.getEnderecos()
//                        .stream()
//                        .map(Endereco::getBairro)
//                        .collect(Collectors.toList())
//        );
//    }

    //        this.listaPetServicos.addAll(pet.getPetServicos() //teria que pegar o PetServicoDto
//                        .stream()
//                        .filter(p -> p.equals())
//                        .map(PetServicoDto::new)
//                        .collect(Collectors.toList())
//        );

    // Não funciona com @AllArgsConstructor, precisei declarar construtor acima
    // converte uma lista de Tutor em lista de Dto
    public static List<TutorDto> converter(List<Tutor> tutores) {
        return tutores.stream()
                .map(TutorDto::new)
                .collect(Collectors.toList());
    }

}

