package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.model.Servico;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class DetalhesDoServicoDto {

    private Long id;
    private String descricaoServico;
    private BigDecimal preco;
    private LocalDateTime dataCadastro;
    private Boolean status;

    public DetalhesDoServicoDto(Servico servico) {
        this.id = servico.getId();
        this.descricaoServico = servico.getDescricaoServico();
        this.preco = servico.getPreco();
        this.dataCadastro = servico.getDataCadastro();
        this.status = servico.getStatus();
    }
}
