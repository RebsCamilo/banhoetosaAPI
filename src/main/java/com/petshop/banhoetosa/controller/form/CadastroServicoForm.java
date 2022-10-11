package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Servico;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CadastroServicoForm {

    @NotNull @NotEmpty
    private String descricaoServico;
    @NotNull
    private BigDecimal preco;

    public Servico converter() {
        Servico servico = new Servico(descricaoServico, preco);
        return servico;
    }
}
