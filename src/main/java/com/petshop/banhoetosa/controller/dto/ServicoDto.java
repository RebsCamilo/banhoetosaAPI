package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Servico;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class ServicoDto {
    private Long id;
    private String descricaoServico;
    private BigDecimal preco;


    public ServicoDto(Servico servico) {
        this.id = servico.getId();
        this.descricaoServico = servico.getDescricaoServico();
        this.preco = servico.getPreco();
    }

    public static List<ServicoDto> converter(List<Servico> servicos) {
        return servicos.stream()
                .map(ServicoDto::new)
                .collect(Collectors.toList()); //converte uma lista de objeto em lista de PetDto
    }


}
