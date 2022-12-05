package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
import com.petshop.banhoetosa.service.PetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.petshop.banhoetosa.factory.PetResponseFactory.*;
import static com.petshop.banhoetosa.factory.PetFactory.*;
import static com.petshop.banhoetosa.factory.TutorFactory.*;

@SpringBootTest
class PetControllerTest {
	
	@InjectMocks //injeta instancia real da classe, mas tudo o que for usado nela serão mocks
	private PetController controller;
	
	@Mock
	private PetService service;
	
	@Mock
	private PetMapper mapper;
	
	//	private static final Long ID = 1L;
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar uma lista de PetResponse - Listar")
	void deveRetornarUmaListaDePetResponse_listar() {
		//given
		Pet pet = createPetComId();
		PetResponse petResponse = createPetResponseComId();
		List<Pet> listaPets = List.of(pet);
		List<PetResponse> listaPetsResponse = List.of(petResponse);
		//when
		Mockito.when(service.listar()).thenReturn(listaPets);
		Mockito.when(mapper.petListToPetResponseList(listaPets)).thenReturn(listaPetsResponse);
		ResponseEntity<List<PetResponse>> result = controller.listar();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(ResponseEntity.class);
//		Assertions.assertThat(result.getBody().getClass()).isEqualTo(List.class); //java.util.ImmutableCollections.List12
		Assertions.assertThat(result.getBody().get(INDEX).getClass()).isEqualTo(listaPetsResponse.get(INDEX).getClass());
		Assertions.assertThat(result.getBody().size()).isEqualTo(listaPetsResponse.size());
		Assertions.assertThat(result.getBody().get(INDEX).getId()).isEqualTo(listaPetsResponse.get(INDEX).getId());
		Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Deve retornar uma lista de PetResponse - ListarPetsDoTutor")
	void deveRetornarUmaListaDePetResponse_listarPetsDoTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		PetResponse petResponse = createPetResponseComId();
		List<PetResponse> listaPetsResponse = List.of(petResponse);
		//variaveis
		Long tutorId = tutor.getId();
		List<Pet> tutorPets = tutor.getPets();
		//when
		Mockito.when(service.listarPetsDoTutor(tutorId)).thenReturn(tutorPets);
		Mockito.when(mapper.petListToPetResponseList(tutorPets)).thenReturn(listaPetsResponse);
		ResponseEntity<List<PetResponse>> result = controller.listarPetsDoTutor(tutorId);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(ResponseEntity.class);
		//		Assertions.assertThat(result.getBody().getClass()).isEqualTo(List.class); //java.util.ImmutableCollections.List12
		Assertions.assertThat(result.getBody().get(INDEX).getClass()).isEqualTo(listaPetsResponse.get(INDEX).getClass());
		Assertions.assertThat(result.getBody().size()).isEqualTo(listaPetsResponse.size());
		Assertions.assertThat(result.getBody().get(INDEX).getId()).isEqualTo(listaPetsResponse.get(INDEX).getId());
		Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
//	@Test
//	void cadastrar() {
//		//given
//		Tutor tutor = createTutorComId();
//		Pet pet = createPetComId();
//		tutor.setPets(List.of(pet));
//		pet.setTutor(tutor);
//	}
	
	@Test
	@DisplayName("Deve retornar um PetDetalhesResponse - Detalhar")
	void deveRetornarUmPetDetalhesResponse_detalhar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		PetDetalhesResponse petDetalhesResponse = createPetDetalhesResponseComId();
		//variaveis
		Long petId = pet.getId();
		Long tutorId = tutor.getId();
		//when
		Mockito.when(service.detalhar(petId, tutorId)).thenReturn(pet);
		Mockito.when(mapper.petServicosToPetDetalhesResponse(pet)).thenReturn(petDetalhesResponse);
		ResponseEntity<PetDetalhesResponse> result = controller.detalhar(tutorId, petId);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getBody()).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(ResponseEntity.class);
		Assertions.assertThat(result.getBody().getClass()).isEqualTo(PetDetalhesResponse.class);
		Assertions.assertThat(result.getBody().getId()).isEqualTo(petDetalhesResponse.getId());
		Assertions.assertThat(result.getBody().getNome()).isEqualTo(petDetalhesResponse.getNome());
		Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void atualizar() {
	}
	
	@Test
	void deletar() {
	}
}