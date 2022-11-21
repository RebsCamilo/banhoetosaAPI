package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.service.PetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.petshop.banhoetosa.factory.PetResponseFactory.createPetDetalhesResponseComId;
import static com.petshop.banhoetosa.factory.PetFactory.createPetComId;
import static com.petshop.banhoetosa.factory.TutorFactory.createTutorComId;

@SpringBootTest
class PetControllerTest {
	
	@InjectMocks //injeta instancia real da classe, mas tudo o que for usado nela serão mocks
	private PetController controller;
	
	@Mock
	private PetService service;
	
	@Mock
	private PetMapper mapper;
	
	private static final Long ID = 1L;
	private static final int INDEX = 0;
	
	@Test
	void listar() {
	}
	
	@Test
	void listarPetsDoTutor() {
	}
	
	@Test
	void cadastrar() {
	}
	
	@Test
	@DisplayName("Deve retornar Sucesso - Detalhar")
	void deveRetornarSucesso_detalhar() {
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
	}
	
	@Test
	void atualizar() {
	}
	
	@Test
	void deletar() {
	}
}