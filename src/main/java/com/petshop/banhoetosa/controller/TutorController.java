package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.TutorDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoTutorForm;
import com.petshop.banhoetosa.controller.form.CadastroTutorForm;
import com.petshop.banhoetosa.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public List<TutorDto> listar() {
        return tutorService.listar();
    }

    @PostMapping
    public ResponseEntity<TutorDto> cadastrar(@RequestBody @Valid CadastroTutorForm form, UriComponentsBuilder uriBuilder) {
        return tutorService.cadastrar(form, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTutorDto> detalhar(@PathVariable Long id) {
        return tutorService.detalhar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTutorForm form) {
        return tutorService.atualizar(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return tutorService.deletar(id);
    }

}