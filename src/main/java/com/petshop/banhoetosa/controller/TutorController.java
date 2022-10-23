package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.TutorDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoTutorForm;
import com.petshop.banhoetosa.controller.form.CadastroTutorForm;
import com.petshop.banhoetosa.controller.request.TutorRequest;
import com.petshop.banhoetosa.controller.response.TutorDetalhesResponse;
import com.petshop.banhoetosa.controller.response.TutorResponse;
import com.petshop.banhoetosa.controller.mapper.*;
import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;


    @GetMapping
    public ResponseEntity<List<TutorResponse>> listar() {
        List<Tutor> lista = tutorService.listar();
        List<TutorResponse> listaResp = tutorMapper.tutorListToTutorResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid TutorRequest request) {
        if(tutorService.validarEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um tutor com este email");
        }
        Tutor tutor = tutorMapper.tutorRequestToTutor(request);
        Endereco endereco = tutorMapper.tutorRequestToEndereco(request);
        tutorService.cadastrar(tutor, endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tutor cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDetalhesResponse> detalhar(@PathVariable Long id) {
        if (tutorService.existeId(id)) {
            Tutor tutor = (tutorService.detalhar(id)).get();     //detalhar devolve Optional<Tutor>
            Endereco endereco = tutor.getEndereco();
            List<String> listaPets = tutor.getPets().stream().map(Pet::getNome).collect(Collectors.toList());
            TutorDetalhesResponse tutorResponse = tutorMapper.tutorEnderecoPetToTutorDetalhesResponse(tutor, endereco, listaPets);
            return ResponseEntity.status(HttpStatus.OK).body(tutorResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid TutorRequest request) {
        if(tutorService.existeId(id)) {
            Tutor tutor = TutorMapper.INSTANCE.tutorRequestToTutor(request);
            tutorService.atualizar(id, tutor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do tutor atualizado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTutorForm form) {
//        if(tutorService.existeId(id)) {
//            Tutor tutor = form.converter();
//            tutorService.atualizar(id, tutor);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do tutor atualizado com sucesso");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if(tutorService.existeId(id)) {
            tutorService.deletar(id);
            return ResponseEntity.status(HttpStatus.OK).body("Tutor excluído com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
    }
}
