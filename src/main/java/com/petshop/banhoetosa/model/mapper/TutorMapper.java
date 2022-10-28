package com.petshop.banhoetosa.model.mapper;

import com.petshop.banhoetosa.model.request.TutorRequest;
import com.petshop.banhoetosa.model.response.TutorDetalhesResponse;
import com.petshop.banhoetosa.model.response.TutorResponse;
import com.petshop.banhoetosa.model.domain.Endereco;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorMapper {

    TutorMapper INSTANCE = Mappers.getMapper(TutorMapper.class);


    Tutor tutorRequestToTutor(TutorRequest tutorRequest);

    TutorResponse tutorToTutorResponse(Tutor tutor);

    List<TutorResponse> tutorListToTutorResponseList(List<Tutor> tutor);

    Endereco tutorRequestToEndereco(TutorRequest tutorRequest);

    @Mapping(target="id", source="tutor.id")
    @Mapping(target="dataCadastro", source="tutor.dataCadastro")
    TutorDetalhesResponse tutorEnderecoPetToTutorDetalhesResponse(Tutor tutor, Endereco endereco, List<String> petsNome);
    List<String> map(List<Pet> pets);
    String map(Pet pet);


}
