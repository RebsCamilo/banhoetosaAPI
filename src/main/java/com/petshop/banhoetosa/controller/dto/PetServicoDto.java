package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
public class PetServicoDto {

    private String descricaoServico; //Servico
    private BigDecimal preco; //Servico
    private Long id; //petServico
    private StatusServicoEnum statusServico; //petServico
    private StatusPagamentoEnum statusPagamento; //petServico

}
