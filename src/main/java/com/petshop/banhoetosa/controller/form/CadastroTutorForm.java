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
    @NotEmpty
    private String telefone2;
    @NotEmpty
    private String email;
    //cadastrar endereco
    @NotEmpty
    private String rua;
    private Integer numero;
    @NotEmpty
    private String bairro;
    @NotEmpty
    private String complemento;
    @NotEmpty
    private String cep;

    public Tutor converter() {
        Endereco endereco = new Endereco(rua, numero, bairro, complemento, cep);
        List<Endereco> listaEnderecos = new ArrayList<>();
        listaEnderecos.add(endereco);
        Tutor tutor = new Tutor(nome, telefone1, telefone2, email, listaEnderecos);
        return tutor;
    }
}
