package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.TutorDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoTutorForm;
import com.petshop.banhoetosa.controller.form.CadastroTutorForm;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;


    @GetMapping
    public ResponseEntity<List<TutorDto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(TutorDto.converter(tutorService.listar()));
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroTutorForm form) {

        if(tutorService.validarEmail(form.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um tutor com este email");
        }
        Tutor tutor = form.converter();
        tutorService.cadastrar(tutor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Tutor cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTutorDto> detalhar(@PathVariable Long id) {
        System.out.println(tutorService.existeId(id));
        if(tutorService.existeId(id)) {
            Tutor tutor = (tutorService.detalhar(id)).get();     //detalhar devolve Optional<Tutor>
            return ResponseEntity.status(HttpStatus.OK).body(new DetalhesDoTutorDto(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTutorForm form) {
        if(tutorService.existeId(id)) {
            Tutor tutor = form.converter();
            tutorService.atualizar(id, tutor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do tutor atualizado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if(tutorService.existeId(id)) {
            tutorService.deletar(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
