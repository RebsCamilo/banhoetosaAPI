package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.TutorDto;
import com.petshop.banhoetosa.controller.form.CadastroTutorForm;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

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

    public ResponseEntity<TutorDto> cadastrar(@RequestBody CadastroTutorForm form, UriComponentsBuilder uriBuilder) {
        Tutor tutor = form.converter();
        tutorRepository.save(tutor);

        URI uri = uriBuilder.path("tutores/{id}").buildAndExpand(tutor.getId()).toUri();
        return ResponseEntity.created(uri).body(new TutorDto(tutor));
    }

    public ResponseEntity<DetalhesDoTutorDto> detalhar(@PathVariable Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        if(tutor.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTutorDto(tutor.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
