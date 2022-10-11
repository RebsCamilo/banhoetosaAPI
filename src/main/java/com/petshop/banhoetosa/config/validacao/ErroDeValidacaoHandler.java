package com.petshop.banhoetosa.config.validacao;

import com.petshop.banhoetosa.config.validacao.dto.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //devolve erro bad request
    @ExceptionHandler(MethodArgumentNotValidException.class) //interceptador, faz o tartamento da exceção
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) { //dentro do objeto exception estao os erros que aconteceram
        List<ErroDeFormularioDto> erroFormularioDto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
            erroFormularioDto.add(erro);
        });
        return erroFormularioDto;
    }
}
