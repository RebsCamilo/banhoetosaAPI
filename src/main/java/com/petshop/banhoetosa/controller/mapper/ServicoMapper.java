package com.petshop.banhoetosa.controller.mapper;

import com.petshop.banhoetosa.controller.request.ServicoRequest;
import com.petshop.banhoetosa.controller.response.ServicoResponse;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    ServicoMapper INSTANCE = Mappers.getMapper(ServicoMapper.class);


    List<ServicoResponse> servicoListToServicoResponseList(List<Servico> servico);

    Servico servicoRequestToServico(ServicoRequest servicoRequest);

    ServicoResponse map(Servico servico);

}
