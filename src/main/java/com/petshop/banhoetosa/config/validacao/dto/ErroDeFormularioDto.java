package com.petshop.banhoetosa.config.validacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroDeFormularioDto { //classe que representa o json a ser mandado para o client em caso de esceção de formulario
    private String campo;
    private String mensagemDeErro;
}
