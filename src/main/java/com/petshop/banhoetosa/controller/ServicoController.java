package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.model.mapper.ServicoMapper;
import com.petshop.banhoetosa.model.request.ServicoRequest;
import com.petshop.banhoetosa.model.response.ServicoDetalhesResponse;
import com.petshop.banhoetosa.model.response.ServicoResponse;
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
		Servico servico = servicoService.detalhar(id);
		ServicoDetalhesResponse servicoDetalhe = servicoMapper.servicoToServicoDetalhesResponse(servico);
		return ResponseEntity.status(HttpStatus.OK).body(servicoDetalhe);
	}

	@Operation(summary = "Atualiza o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado"),
			@ApiResponse(responseCode = "409", description = "Serviço com essa descrição já existe")
	})
	@PutMapping(value="/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid ServicoRequest request) {
		Servico servico = servicoMapper.servicoRequestToServico(request);
		servicoService.atualizar(id, servico);
		return ResponseEntity.status(HttpStatus.CREATED).body("Serviço atualizado com sucesso");
	}

	@Operation(summary = "Desativa o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Desativado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@DeleteMapping("/{id}/desativar")
	public ResponseEntity<Object> desativar(@PathVariable Long id) {
		servicoService.desativar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Serviço desativado com sucesso");
	}

	@Operation(summary = "Ativa o serviço pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ativado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<Object> ativar(@PathVariable Long id) {
		servicoService.ativar(id);
		return ResponseEntity.status(HttpStatus.OK).body("Serviço reativado com sucesso");
	}

}
