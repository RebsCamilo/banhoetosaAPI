package com.petshop.banhoetosa.model.mapper;

import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
import com.petshop.banhoetosa.model.domain.Endereco;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);


    Pet petRequestToPet(PetRequest petRequest);

    PetResponse petToPetResponse(Pet pet);

    List<PetResponse> petListToPetResponseList(List<Pet> pet);

    Endereco petRequestToEndereco(PetRequest petRequest);

//    @Mapping(target="id", source="pet.id")
//    @Mapping(target="nome", source="pet.nome")
//    @Mapping(target="dataCadastro", source="pet.dataCadastro")
//    @Mapping(target="nomeTutor", source="tutor.nome")
    PetDetalhesResponse petServicosToPetDetalhesResponse(Pet pet);//, Tutor tutor, List<String> listaPetServicos);

    List<String> map(List<Pet> pets);
    String map(Pet pet);

}
