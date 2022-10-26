package com.petshop.banhoetosa.controller.mapper;

import com.petshop.banhoetosa.controller.request.PetServicoRequest;
import com.petshop.banhoetosa.controller.request.PetServicoUpdateRequest;
import com.petshop.banhoetosa.controller.response.PetServicoResponse;
import com.petshop.banhoetosa.model.PetServico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetServicoMapper {

    PetServicoMapper INSTANCE = Mappers.getMapper(PetServicoMapper.class);


    PetServico petServicoResponseToPetServico(PetServicoResponse petServicoResponse); //necessario para usar o petServicoToPetServicoResponse

    @Mapping(target="idPet", source="pet.id")
    @Mapping(target="idServico", source="servico.id")
    PetServicoResponse petServicoToPetServicoResponse(PetServico petServico);


    List<PetServicoResponse> petServicoListToPetServicoResponseList(List<PetServico> petServico);

    PetServico petServicoRequestToPetServico(PetServicoRequest servicoRequest);

    PetServico petServicoUpdateRequestToPetServico(PetServicoUpdateRequest servicoRequest);

}