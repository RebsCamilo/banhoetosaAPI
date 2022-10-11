package com.petshop.banhoetosa.controller.form;

import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AtualizacaoTutorForm {
    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String telefone1;
    private String telefone2;
    private String email;
    private LocalDateTime dataCadastro;
    private Boolean status;


    public Tutor atualizar(Long id, TutorRepository tutorRepository) {
        Tutor tutor = tutorRepository.getReferenceById(id);

        tutor.setNome(this.nome);
        tutor.setTelefone1(this.telefone1);
        tutor.setTelefone2(this.telefone2);
        tutor.setEmail(this.email);
        tutor.setDataCadastro(LocalDateTime.now());
        tutor.setStatus(this.status);

        return tutor;
    }
}
