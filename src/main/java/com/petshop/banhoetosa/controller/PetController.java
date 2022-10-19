package com.petshop.banhoetosa.controller;


import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
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

	@GetMapping
//	@ResponseStatus(HttpStatus.OK) //anotacao da swagger open api
	public ResponseEntity<List<PetDto>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(PetDto.converter(petService.listar()));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroPetForm form) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		System.out.println("[controller 3] " + form.getNome() + " " + form.getEmailTutor());
		if (petService.validarPet(form.getNome() , form.getEmailTutor())) { //valida se o pet já esta cadastrado neste tutor e se o tutor existe
			Pet pet = form.converter();
			petService.cadastrar(pet, form.getEmailTutor());
			return ResponseEntity.status(HttpStatus.CREATED).body("Pet cadastrado com sucesso");
		}
//        return ResponseEntity.status(HttpStatus.OK).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response e os dados no PetDto
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Pet já cadastrado ou tutor não encontrado");
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
