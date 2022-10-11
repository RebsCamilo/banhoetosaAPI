package com.petshop.banhoetosa.controller;


import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
import com.petshop.banhoetosa.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	private PetService petService;

	@GetMapping
	public List<PetDto> listar(String nomePet) {
		return petService.listar(nomePet);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<PetDto> cadastrar(@RequestBody @Valid CadastroPetForm form, UriComponentsBuilder uriBuilder) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		return petService.cadastrar(form, uriBuilder);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoPetDto> detalhar(@PathVariable Long id) {
		return petService.detalhar(id);
	}

	@PutMapping("/{id}")
	@Transactional //para comitar as alterações no banco de dados
	public ResponseEntity<PetDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetForm form) {
		return petService.atualizar(id, form);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
		return petService.deletar(id);
	}

}
