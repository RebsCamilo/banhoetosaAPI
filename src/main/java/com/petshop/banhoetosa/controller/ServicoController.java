package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.mapper.ServicoMapper;
import com.petshop.banhoetosa.controller.request.ServicoRequest;
import com.petshop.banhoetosa.controller.response.ServicoDetalhesResponse;
import com.petshop.banhoetosa.controller.response.ServicoResponse;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/servicos")
@Tag(name = "Serviços ofertados", description = "tudo sobre os serviços ofertados pelo estabelecimento")
public class ServicoController {

	private final ServicoService servicoService;
	private final ServicoMapper servicoMapper;

	@Autowired
	public ServicoController(ServicoService servicoService, ServicoMapper servicoMapper) {
		this.servicoService = servicoService;
		this.servicoMapper = servicoMapper;
	}


	@Operation(summary = "Busca todos os serviços disponíveis")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição")
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<ServicoResponse>> listarAtivos() {
		List<Servico> lista = servicoService.listarAtivos();
		List<ServicoResponse> listaResp = servicoMapper.servicoListToServicoResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@Operation(summary = "Busca todos os serviços")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição")
	})
	@GetMapping(value = "/todos", produces="application/json")
	public ResponseEntity<List<ServicoResponse>> listar() {
		List<Servico> lista = servicoService.listar();
		List<ServicoResponse> listaResp = servicoMapper.servicoListToServicoResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@Operation(summary = "Cadastra um serviço")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "409", description = "Serviço já cadastrado")
	})
	@PostMapping(consumes="application/json")
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid ServicoRequest request) {
		if (servicoService.existeDescricao(request.getDescricaoServico())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um serviço com essa descrição");
		}
		Servico servico = servicoMapper.servicoRequestToServico(request);
		servicoService.cadastrar(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body("Serviço cadastrado com sucesso");
	}

	@Operation(summary = "Busca o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@GetMapping(value="/{id}")
	public ResponseEntity<ServicoDetalhesResponse> detalhar(@PathVariable Long id) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			Servico servico = servicoService.detalhar(id);
			ServicoDetalhesResponse servicoDetalhe = servicoMapper.servicoToServicoDetalhesResponse(servico);
			return ResponseEntity.status(HttpStatus.OK).body(servicoDetalhe);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Operation(summary = "Atualiza o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@PutMapping(value="/{id}")
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

	@Operation(summary = "Desativa o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Desativado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@DeleteMapping("/{id}/desativar")
	public ResponseEntity<Object> desativar(@PathVariable Long id) {
		if (servicoService.existeId(id) && servicoService.status(id)) {
			servicoService.desativar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Serviço desativado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

	@Operation(summary = "Ativa o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ativado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<Object> ativar(@PathVariable Long id) {
		if (servicoService.existeId(id)) {
			servicoService.ativar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Serviço reativado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
	}

}
