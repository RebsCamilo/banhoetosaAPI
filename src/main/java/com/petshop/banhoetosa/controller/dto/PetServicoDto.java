package com.petshop.banhoetosa.controller.dto;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Servico;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class PetServicoDto {

    private Long id;
    private LocalDateTime dataCadastro;
    private StatusServicoEnum statusServico;
    private StatusPagamentoEnum statusPagamento;
    private String nomePet;
    private String descricaoServico;


    public PetServicoDto(PetServico petServico) {
        this.id = petServico.getId();
        this.dataCadastro = petServico.getDataCadastro();
        this.statusServico = petServico.getStatusServico();
        this.statusPagamento = petServico.getStatusPagamento();
        this.nomePet = petServico.getPet().getNome();
        this.descricaoServico = petServico.getServico().getDescricaoServico();
    }

    public static List<PetServicoDto> converter(List<PetServico> petServicos) {
        return petServicos.stream()
                .map(PetServicoDto::new)
                .collect(Collectors.toList()); //converte uma lista de objeto em lista de PetServicoDto
    }
}
