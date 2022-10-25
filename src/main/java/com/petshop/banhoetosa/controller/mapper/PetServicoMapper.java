package com.petshop.banhoetosa.controller.mapper;

import com.petshop.banhoetosa.controller.request.PetServicoRequest;
import com.petshop.banhoetosa.controller.response.PetServicoResponse;
import com.petshop.banhoetosa.model.PetServico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetServicoMapper {

    PetServicoMapper INSTANCE = Mappers.getMapper(PetServicoMapper.class);


    PetServico petServicoResponseToPetServico(PetServicoResponse petServicoResponse);

    @Mapping(target="idPet", source="pet.id")
    @Mapping(target="idServico", source="servico.id")
    PetServicoResponse petServicoToPetServicoResponse(PetServico petServico);


    List<PetServicoResponse> petServicoListToPetServicoResponseList(List<PetServico> petServico);

    PetServico petSservicoRequestToPetServico(PetServicoRequest servicoRequest);
//
//    PetServicoDetalhesResponse servicoToPetServicoDetalhesResponse(PetServico servico);
//
//    PetServicoResponse map(PetServico petServico);

}
