package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.mapper.ServicoMapper;
import com.petshop.banhoetosa.controller.request.ServicoRequest;
import com.petshop.banhoetosa.controller.response.ServicoDetalhesResponse;
import com.petshop.banhoetosa.controller.response.ServicoResponse;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

	private final ServicoService servicoService;
	private final ServicoMapper servicoMapper;

	@Autowired
	public ServicoController(ServicoService servicoService, ServicoMapper servicoMapper) {
		this.servicoService = servicoService;
		this.servicoMapper = servicoMapper;
	}


	@GetMapping
	public ResponseEntity<List<ServicoResponse>> listarAtivos() {
		List<Servico> lista = servicoService.listarAtivos();
		List<ServicoResponse> listaResp = servicoMapper.servicoListToServicoResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@GetMapping("/todos")
	public ResponseEntity<List<ServicoResponse>> listar() {
		List<Servico> lista = servicoService.listar();
		List<ServicoResponse> listaResp = servicoMapper.servicoListToServicoResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid ServicoRequest request) {
		if (servicoService.existeDescricao(request.getDescricaoServico())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um serviço com essa descrição");
		}
		Servico servico = servicoMapper.servicoRequestToServico(request);
		servicoService.cadastrar(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body("Serviço cadastrado com sucesso");
	}

	@GetMapping("/{id}")
	public ResponseEntity<ServicoDetalhesResponse> detalhar(@PathVariable Long id) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			Servico servico = servicoService.detalhar(id);
			ServicoDetalhesResponse servicoDetalhe = servicoMapper.servicoToServicoDetalhesResponse(servico);
			return ResponseEntity.status(HttpStatus.OK).body(servicoDetalhe);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid ServicoRequest request) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			Servico servico = servicoMapper.servicoRequestToServico(request);
			if (!servicoService.existeDescricao(servico.getDescricaoServico()) || servicoService.descricaoIgualIdDescricao(id, request.getDescricaoServico())) {
				servicoService.atualizar(id, servico);
				return ResponseEntity.status(HttpStatus.CREATED).body("Serviço atualizado com sucesso");
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço com mesma descrição já existente");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

	@DeleteMapping("/desativar/{id}")
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
