package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoServicoDto;
import com.petshop.banhoetosa.controller.dto.DetalhesDoTutorDto;
import com.petshop.banhoetosa.controller.dto.ServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroServicoForm;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;


	@GetMapping
	public ResponseEntity<List<ServicoDto>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(ServicoDto.converter(servicoService.listar()));
	}

	@GetMapping("/todos")
	public ResponseEntity<List<ServicoDto>> listarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(ServicoDto.converter(servicoService.listarTodos()));
	}

	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroServicoForm form) {
		if (servicoService.existeDescricao(form.getDescricaoServico())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um serviço com essa descrição");
		}
		Servico servico = form.converter();
		servicoService.cadastrar(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body("Serviço cadastrado com sucesso");
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoServicoDto> detalhar(@PathVariable Long id) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			Servico servico = (servicoService.detalhar(id));
			return ResponseEntity.status(HttpStatus.OK).body(new DetalhesDoServicoDto(servico));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoServicoForm form) {
		System.out.println(servicoService.existeId(id));
		if (servicoService.existeId(id) && servicoService.status(id)) {
			Servico servico = form.converter();
			servicoService.atualizar(id, servico);
			return ResponseEntity.status(HttpStatus.CREATED).body("Serviço atualizado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

	@DeleteMapping("/desativar/{id}")
	// <?> diz que retorna generics mas nao sabe o tipo
	public ResponseEntity<Object> desativar(@PathVariable Long id) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			servicoService.desativar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Serviço desativado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

	@PatchMapping("/ativar/{id}")
	public ResponseEntity<Object> ativar(@PathVariable Long id) {
		if (servicoService.existeId(id)) {
			servicoService.ativar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Serviço reativado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

}
