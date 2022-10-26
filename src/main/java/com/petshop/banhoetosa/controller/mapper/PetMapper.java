package com.petshop.banhoetosa.controller.mapper;

import com.petshop.banhoetosa.controller.request.PetRequest;
import com.petshop.banhoetosa.controller.response.PetDetalhesResponse;
import com.petshop.banhoetosa.controller.response.PetResponse;
import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
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

    @Mapping(target="id", source="pet.id")
    @Mapping(target="nome", source="pet.nome")
    @Mapping(target="dataCadastro", source="pet.dataCadastro")
    @Mapping(target="nomeTutor", source="tutor.nome")
    PetDetalhesResponse petServicosToPetDetalhesResponse(Pet pet, Tutor tutor, List<String> listaPetServicos);

    List<String> map(List<Pet> pets);
    String map(Pet pet);

}