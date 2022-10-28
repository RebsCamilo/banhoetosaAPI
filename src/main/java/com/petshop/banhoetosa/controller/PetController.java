package com.petshop.banhoetosa.controller;


import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.service.PetService;
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
@RequestMapping(value="/pets")
@Tag(name = "Pets", description = "tudo sobre os pets")
public class PetController {

	private final PetService petService;
	private final PetMapper petMapper;

	@Autowired
	public PetController(PetService petService, PetMapper petMapper) {
		this.petService = petService;
		this.petMapper = petMapper;
	}


	@Operation(summary = "Busca todos os pets")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrados com sucesso"), //, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))})
			@ApiResponse(responseCode = "400", description = "Erro na requisição")
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<PetResponse>> listar() {
		List<Pet> lista = petService.listar();
		List<PetResponse> listaResp = petMapper.petListToPetResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Pet encontrado"),
//			@ApiResponse(responseCode = "400", description = "Id fornecido é inválido"),
//			@ApiResponse(responseCode = "404", description = "Pet não encontrado")
//	})
	@Operation(summary = "Cadastra um pet")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "409", description = "Pet já cadastrado ou tutor não encontrado")
	})
	@PostMapping(consumes="application/json")
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid PetRequest request) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		if (!petService.validarPet(request.getNome() , request.getEmailTutor())) { //valida se o pet já esta cadastrado neste tutor e se o tutor existe
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Pet já cadastrado ou tutor não encontrado");
		}
		Pet pet = petMapper.petRequestToPet(request);
		petService.cadastrar(pet, request.getEmailTutor());
		return ResponseEntity.status(HttpStatus.CREATED).body("Pet cadastrado com sucesso"); //ResponseEntity para devolver o status 201 na Response e os dados no PetDto
//        return ResponseEntity.status(HttpStatus.OK).body(new PetDto(pet));
	}

	@Operation(summary = "Busca o pet pelo seu id") //@Operation e @ApiResponses são anotações da implantação do swagger OpenApi
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@GetMapping(value="/{id}")
	public ResponseEntity<PetDetalhesResponse> detalhar(@PathVariable Long id) {
		if (petService.existeId(id)) {
			Pet pet = (petService.detalhar(id)).get(); //detalhar devolve Optional<Pet>, não precisa pois existeId ja diz se tem pet com id especificado
			Tutor tutor = pet.getTutor();
			List<String> listaPetServicos = pet.getPetServicos()
					.stream()
					.map(petServico -> petServico.getServico().getDescricaoServico())
					.toList();
			PetDetalhesResponse petDetalhe = petMapper.petServicosToPetDetalhesResponse(pet, tutor, listaPetServicos);
			return ResponseEntity.status(HttpStatus.OK).body(petDetalhe);
		}
        return ResponseEntity.notFound().build();
	}

	@Operation(summary = "Atualiza o pet pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@PutMapping(value="/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid PetRequest request) {
		if (petService.existeId(id)) {
			Pet pet = petMapper.petRequestToPet(request);
			petService.atualizar(id, pet, request.getEmailTutor());
			return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do pet atualizado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado");
	}

	@Operation(summary = "Deleta o pet pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
		if(petService.existeId(id)) {
			petService.deletar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Pet excluído com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado");
	}
}
