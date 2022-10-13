package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.PetServico;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class PetServicoDetalheDoPetDto {

    private String descricaoServico; //Servico
    private BigDecimal preco; //Servico
    private Long id; //petServico
    private StatusServicoEnum statusServico; //petServico
    private StatusPagamentoEnum statusPagamento; //petServico
    private LocalDateTime dataServico;

    public PetServicoDetalheDoPetDto(PetServico petServico) {
        this.descricaoServico = petServico.getServico().getDescricaoServico();
        this.preco = petServico.getServico().getPreco();
        this.id = petServico.getId();
        this.statusServico = petServico.getStatusServico();
        this.statusPagamento = petServico.getStatusPagamento();
        this.dataServico = LocalDateTime.now();
    }
}
