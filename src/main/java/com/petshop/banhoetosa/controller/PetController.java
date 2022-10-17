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
import org.springframework.web.util.UriComponentsBuilder;

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
	@ResponseStatus(HttpStatus.OK) //swagger open api
	public List<PetDto> listar(String nomePet) {
		return petService.listar(nomePet);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<PetDto> cadastrar(@RequestBody @Valid CadastroPetForm form) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
		Pet pet = form.converter(tutorRepository);
		petService.cadastrar(pet);

//        URI uri = uriBuilder.path("/pet/{id}").buildAndExpand(pet.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
        return ResponseEntity.ok().body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response
//		HttpStatus.CREATED)
	}

	@Operation(summary = "Get a pet by its id")
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
		return petService.detalhar(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PetDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetForm form) {
		return petService.atualizar(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
		return petService.deletar(id);
	}

}
