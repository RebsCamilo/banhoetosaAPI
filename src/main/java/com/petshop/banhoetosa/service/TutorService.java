package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.TutorDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoTutorForm;
import com.petshop.banhoetosa.controller.form.CadastroTutorForm;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TutorService {
    @Autowired
    private TutorRepository tutorRepository;


    public List<TutorDto> listar() {
        return TutorDto.converter(tutorRepository.findAll());
    }

    @Transactional
    public ResponseEntity<TutorDto> cadastrar(CadastroTutorForm form, UriComponentsBuilder uriBuilder) {
        Tutor tutor = form.converter();
        tutorRepository.save(tutor);

        URI uri = uriBuilder.path("tutor/{id}").buildAndExpand(tutor.getId()).toUri();
        return ResponseEntity.created(uri).body(new TutorDto(tutor));
    }

    public ResponseEntity<DetalhesDoTutorDto> detalhar(Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if(tutor.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTutorDto(tutor.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<TutorDto> atualizar(Long id, AtualizacaoTutorForm form) {
        Optional<Tutor> optional = tutorRepository.findById(id);
        if (optional.isPresent()) {
            Tutor tutor = form.atualizar(id, tutorRepository);
            return ResponseEntity.ok(new TutorDto(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> deletar(Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if (tutor.isPresent()) {
            tutorRepository.delete(tutor.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
