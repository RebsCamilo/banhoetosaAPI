package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetServicoDto;
import com.petshop.banhoetosa.controller.dto.PetServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroPetServicoForm;
import com.petshop.banhoetosa.service.PetServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/petservico")
public class PetServicoController {

    @Autowired
    private PetServicoService petServicoService;

    @GetMapping
    public List<PetServicoDto> listar() {
        return petServicoService.listar();
    }

    @PostMapping
    public ResponseEntity<PetServicoDto> cadastrar(@RequestBody @Valid CadastroPetServicoForm form, UriComponentsBuilder uriBuilder) {
        return petServicoService.cadastrar(form, uriBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoPetServicoDto> detalhar(@PathVariable Long id) {
        return petServicoService.detalhar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetServicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetServicoForm form) {
        return petServicoService.atualizar(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return petServicoService.deletar(id);
    }



}
