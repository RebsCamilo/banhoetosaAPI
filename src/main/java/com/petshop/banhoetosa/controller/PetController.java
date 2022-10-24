package com.petshop.banhoetosa.controller;


import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
import com.petshop.banhoetosa.controller.mapper.PetMapper;
import com.petshop.banhoetosa.controller.request.PetRequest;
import com.petshop.banhoetosa.controller.response.PetResponse;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.repository.TutorRepository;
import com.petshop.banhoetosa.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	private PetService petService;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private PetMapper petMapper;

	@GetMapping
//	@ResponseStatus(HttpStatus.OK) //anotacao da swagger open api
	public ResponseEntity<List<PetResponse>> listar() {
		List<Pet> lista = petService.listar();
		List<PetResponse> listaResp = petMapper.petListToPetResponseList(lista);
		return ResponseEntity.status(HttpStatus.OK).body(listaResp);
	}

	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid PetRequest request) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		if (!petService.validarPet(request.getNome() , request.getEmailTutor())) { //valida se o pet já esta cadastrado neste tutor e se o tutor existe
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Pet já cadastrado ou tutor não encontrado");
		}
		Pet pet = petMapper.petRequestToPet(request);
//		Pet pet = form.converter();
		petService.cadastrar(pet, request.getEmailTutor());
		return ResponseEntity.status(HttpStatus.CREATED).body("Pet cadastrado com sucesso");
//        return ResponseEntity.status(HttpStatus.OK).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response e os dados no PetDto
	}

	@Operation(summary = "Get a pet by its id") //@Operation e @ApiResponses são anotações da implantação do swagger OpenApi
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the pet",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Pet.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Pet not found",
					content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoPetDto> detalhar(@PathVariable Long id) {
		if (petService.existeId(id)) {
			Pet pet = (petService.detalhar(id)).get(); //detalhar devolve Optional<Pet>, não precisa pois existeId ja diz se tem pet com id especificado
			return ResponseEntity.status(HttpStatus.OK).body(new DetalhesDoPetDto(pet));
		}
        return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetForm form) {
		if (petService.existeId(id)) {
			Pet pet = form.converter();
			petService.atualizar(id, pet, form.getEmailTutor());
			return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do pet atualizado com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
		if(petService.existeId(id)) {
			petService.deletar(id);
			return ResponseEntity.status(HttpStatus.OK).body("Pet excluído com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet não encontrado");
	}

}
