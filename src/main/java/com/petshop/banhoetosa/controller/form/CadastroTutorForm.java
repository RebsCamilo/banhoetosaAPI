package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Tutor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CadastroTutorForm {

    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String telefone1;
    private String telefone2;
    @NotNull @NotEmpty
    private String email;
    //cadastrar endereco
    private String rua;
    private Integer numero;
    @NotNull @NotEmpty
    private String bairro;
    private String complemento;
    private String cep;

    public Tutor converter() {
        Endereco endereco = new Endereco(rua, numero, bairro, complemento, cep);
        List<Endereco> listaEnderecos = new ArrayList<>();
        listaEnderecos.add(endereco);
        Tutor tutor = new Tutor(nome, telefone1, telefone2, email, listaEnderecos);
        return tutor;
    }
}
