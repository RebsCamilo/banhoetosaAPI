package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.util.CollectionUtils;

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
    private List<String> nomePets;
    private String nomeBairro;

    public TutorDto(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.telefone1 = tutor.getTelefone1();
        this.telefone2 = tutor.getTelefone2();
        this.email = tutor.getEmail();
        this.nomeBairro = tutor.getEndereco().getBairro();
        this.nomePets = new ArrayList<>();
        List<Pet> pets = tutor.getPets();
        if(!CollectionUtils.isEmpty(pets)) {
            this.nomePets.addAll(tutor.getPets()
                    .stream()
                    .map(Pet::getNome)
                    .collect(Collectors.toList())
            );
        }

//        this.nomeBairro = new ArrayList<>();
//        List<Endereco> enderecos = tutor.getEnderecos();
//        if(!enderecos.isEmpty()) {
//            this.nomeBairro.addAll(tutor.getEnderecos()
//                    .stream()
//                    .map(Endereco::getBairro)
//                    .collect(Collectors.toList())
//            );
//        }
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

