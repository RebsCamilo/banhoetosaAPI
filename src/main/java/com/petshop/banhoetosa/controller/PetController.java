package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
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
//@RequestMapping(value="/pets")
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
	@GetMapping(value = "tutores/pets", produces="application/json")
	public ResponseEntity<List<PetResponse>> listar() {
		List<Pet> lista = petService.listar();
		List<PetResponse> listaResp = petMapper.petListToPetResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@Operation(summary = "Busca pets vinculados a um tutor especifico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Encontrados com sucesso"), //, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))})
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "409", description = "Tutor não encontrado")
	})
	@GetMapping(value = "tutores/{idTutor}/pets", produces="application/json")
	public ResponseEntity<Object> listarPetsDoTutor(@PathVariable Long idTutor) {
//		if(!petService.existeTutorPeloId(idTutor)) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Id do tutor inválido");
//		}
		List<Pet> lista = petService.listarPetsDoTutor(idTutor);
		List<PetResponse> listaResp = petMapper.petListToPetResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@Operation(summary = "Cadastra um pet")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "409", description = "Pet já cadastrado ou tutor não encontrado")
	})
	@PostMapping(value = "tutores/{idTutor}/pets", consumes="application/json")
	public ResponseEntity<Object> cadastrar(@PathVariable Long idTutor, @RequestBody @Valid PetRequest request) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		//deveria estar na service
//		if(!petService.existeTutorPeloId(idTutor)) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tutor não encontrado");
//		}
//		String tutorEmail = petService.findTutorDoPetByTutorId(idTutor);
//		if (!petService.validarPet(request.getNome(), tutorEmail)) { //, request.getEmailTutor())) { //valida se o pet já esta cadastrado neste tutor e se o tutor existe
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("Pet já cadastrado");
//		}
		Pet pet = petMapper.petRequestToPet(request);
		petService.cadastrar(pet, idTutor);
		return ResponseEntity.status(HttpStatus.CREATED).body("Pet cadastrado com sucesso"); //ResponseEntity para devolver o status 201 na Response e os dados no PetDto
//        return ResponseEntity.status(HttpStatus.OK).body(new PetDto(pet));
	}

	@Operation(summary = "Busca o pet pelo seu id") //@Operation e @ApiResponses são anotações da implantação do swagger OpenApi
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@GetMapping(value="tutores/{idTutor}/pets/{id}")
	public ResponseEntity<PetDetalhesResponse> detalhar(@PathVariable Long idTutor, @PathVariable Long id) {
//		if(!petService.existeTutorPeloId(idTutor)) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!petService.existeId(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		String tutorEmail = petService.findTutorDoPetByTutorId(idTutor);
//		String petNome = petService.buscaPetPeloId(id).get().getNome();
//		if (petService.validarPet(petNome, tutorEmail)) { //valida se o pet esta cadastrado neste tutor e se o tutor existe
//			return ResponseEntity.notFound().build();
//		}
		Pet pet = petService.detalhar(id, idTutor);
//		Pet pet = (petService.detalhar(id)).get(); //detalhar devolve Optional<Pet>, não precisa pois existeId ja diz se tem pet com id especificado
//		Tutor tutor = pet.getTutor();
//			List<String> listaPetServicos = pet.getPetServicos()
//					.stream()
//					.map(petServico -> petServico.getServico().getDescricaoServico())
//					.toList();
		PetDetalhesResponse petDetalhe = petMapper.petServicosToPetDetalhesResponse(pet); //, tutor, listaPetServicos);
		return ResponseEntity.status(HttpStatus.OK).body(petDetalhe);
//        return ResponseEntity.notFound().build();
	}

	@Operation(summary = "Atualiza o pet pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Pet ou tutor a ser associado não encontrado")
	})
	@PutMapping(value="tutores/{idTutor}/pets/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long idTutor, @PathVariable Long id, @RequestBody @Valid PetRequest request) {
		Pet petAtualizacao = petMapper.petRequestToPet(request);
		petService.atualizar(id, petAtualizacao, idTutor);
		return ResponseEntity.status(HttpStatus.OK).body("Cadastro do pet atualizado com sucesso");
	}

	@Operation(summary = "Deleta o pet pelo seu id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
			@ApiResponse(responseCode = "404", description = "Não encontrado")
	})
	@DeleteMapping("tutores/{idTutor}/pets/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long idTutor, @PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
//		if(!petService.existeTutorPeloId(idTutor)) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
//		}
//		if (!petService.existeId(id)) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado");
//		}
//		String tutorEmail = petService.findTutorDoPetByTutorId(idTutor);
//		String petNome = petService.buscaPetPeloId(id).get().getNome();
//		if (petService.validarPet(petNome, tutorEmail)) { //retorna true caso o pet não esteja vinculado
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não está vinculado ao tutor");
//		}
		petService.deletar(id, idTutor);
		return ResponseEntity.status(HttpStatus.OK).body("Pet excluído com sucesso");
	}
}
