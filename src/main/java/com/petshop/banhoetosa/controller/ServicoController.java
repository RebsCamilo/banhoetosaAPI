package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoServicoDto;
import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.dto.ServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
import com.petshop.banhoetosa.controller.form.CadastroServicoForm;
import com.petshop.banhoetosa.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;


	@GetMapping
	public List<ServicoDto> listar() {
		return servicoService.listar();
	}

	@PostMapping
	public ResponseEntity<ServicoDto> cadastrar(@RequestBody @Valid CadastroServicoForm form, UriComponentsBuilder uriBuilder) {
		return servicoService.cadastrar(form, uriBuilder);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoServicoDto> detalhar(@PathVariable Long id) {
		return servicoService.detalhar(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ServicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoServicoForm form) {
		return servicoService.atualizar(id, form);
	}

	@DeleteMapping("/{id}")
	// <?> diz que retorna generics mas nao sabe o tipo
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		return servicoService.deletar(id);
	}



}
