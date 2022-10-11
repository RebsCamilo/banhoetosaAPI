package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.service.ServicoService;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PetServicoDto {

    private String descricaoServico; //Servico
    private BigDecimal preco; //Servico
    private Long id; //petServico
    private StatusServicoEnum statusServico; //petServico
    private StatusPagamentoEnum statusPagamento; //petServico

    public PetServicoDto(PetServico petServico) {
        this.descricaoServico = petServico.getServico().getDescricaoServico();
        this.preco = petServico.getServico().getPreco();
        this.id = petServico.getId();
        this.statusServico = petServico.getStatusServico();
        this.statusPagamento = petServico.getStatusPagamento();
    }
}
