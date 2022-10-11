package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.repository.ServicoRepository;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AtualizacaoServicoForm {

    @NotNull
    @NotEmpty
    private String descricaoServico;
    @NotNull
    private BigDecimal preco;

    public Servico atualizar(Long id, ServicoRepository servicoRepository) {
        Servico servico = servicoRepository.getReferenceById(id);

        servico.setDescricaoServico(this.descricaoServico);
        servico.setPreco(this.preco);

        return servico;
    }
}
