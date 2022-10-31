package com.petshop.banhoetosa.model.mapper;

import com.petshop.banhoetosa.model.request.PetServicoRequest;
import com.petshop.banhoetosa.model.request.PetServicoUpdateRequest;
import com.petshop.banhoetosa.model.response.PetServicoResponse;
import com.petshop.banhoetosa.model.domain.PetServico;
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

//    @Mapping(target="idPet", source="pet.id")
//    @Mapping(target="idServico", source="servico.id")
    List<PetServicoResponse> petServicoListToPetServicoResponseList(List<PetServico> petServico);

    PetServico petServicoRequestToPetServico(PetServicoRequest servicoRequest);

    PetServico petServicoUpdateRequestToPetServico(PetServicoUpdateRequest servicoRequest);

}
