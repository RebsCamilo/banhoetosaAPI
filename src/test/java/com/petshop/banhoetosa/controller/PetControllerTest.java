package com.petshop.banhoetosa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.service.PetService;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.petshop.banhoetosa.factory.PetFactory.createPetComId;
import static com.petshop.banhoetosa.factory.PetFactory.createPetSemId;
import static com.petshop.banhoetosa.factory.PetRequestFactory.createPetDetalhesResponse;
import static com.petshop.banhoetosa.factory.PetRequestFactory.createPetRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
class PetControllerTest {
	
	@Autowired
	private MockMvc mockMvc; //to simulate HTTP requests
	
//	@Mock
	@MockBean //automatically replaces the bean of the same type in the application context with a Mockito mock.
	private PetService service;
	
//	@Mock
//	@Autowired
	@MockBean
	private PetMapper mapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	@DisplayName("Deve retornar Ok - Listar")
	void deveRetornarOk_listar() throws Exception {
		mockMvc.perform(get("/tutores/pets"))
		        .andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Deve retornar Ok - ListarPetsDoTutor")
	void deveRetornarOk_listarPetsDoTutor() throws Exception {
		mockMvc.perform(get("/tutores/1/pets"))
		        .andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Deve retornar NotFound - ListarPetsDoTutor")
	void deveRetornarNotFound_listarPetsDoTutor() throws Exception {
		Mockito.when(service.listarPetsDoTutor(10L)).thenThrow(ObjectNotFoundException.class);
		mockMvc.perform(get("/tutores/10/pets"))
		        .andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest - ListarPetsDoTutor")
	void deveRetornarBadRequest_listarPetsDoTutor() throws Exception {
		mockMvc.perform(get("/tutores/1x/pets"))
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve retornar Created - Cadastrar")
	void deveRetornarCreated_cadastrar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.cadastrar(pet, 1L)).thenReturn(pet);
		//then
		mockMvc.perform(post("/tutores/1/pets")
					                .contentType(MediaType.APPLICATION_JSON)
					                .content(objectMapper.writeValueAsString(petRequest)))
		        .andExpect(status().isCreated())
		        .andExpect(content().string("Pet cadastrado com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por DataIntegratyViolation - Cadastrar") //ex: nome pet ja existente
	void deveRetornarBadRequestPorDataIntegratyViolation_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		Pet pet = createPetSemId();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.cadastrar(pet, 1L)).thenThrow(DataIntegratyViolationException.class);
		//then
		mockMvc.perform(post("/tutores/1/pets")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegratyViolationException));
	}
	
	@Test
	@DisplayName("Deve retornar ObjectNotFound - Cadastrar") //tutor não existe
	void deveRetornarObjectNotFound_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		Pet pet = createPetSemId();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.cadastrar(pet, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(post("/tutores/1/pets")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isNotFound())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest - Cadastrar")
	void deveRetornarBadRequest_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		//then
		mockMvc.perform(post("/tutores/1x/pets")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve retornar Ok - Detalhar")
	void deveRetornarOk_detalhar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetDetalhesResponse petDetalhesResponse = createPetDetalhesResponse();
		//when
		Mockito.when(service.detalhar(1L, 1L)).thenReturn(pet);
		Mockito.when(mapper.petServicosToPetDetalhesResponse(Mockito.any())).thenReturn(petDetalhesResponse);
		//then
		mockMvc.perform(get("/tutores/1/pets/1"))
		       .andExpect(status().isOk());
				//como faço para conferir se objeto é o mesmo? classe e parametros
//		       .andExpect(result -> assertTrue(result.getResponse() instanceof PetDetalhesResponse));
	}
	
	
	@Test
	@DisplayName("Deve retornar NotFound - Detalhar")
	void deveRetornarNotFound_detalhar() throws Exception {
		//when
		Mockito.when(service.detalhar(1L, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(get("/tutores/1/pets/1"))
		       .andExpect(status().isNotFound())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar Ok - Atualizar")
	void deveRetornarOk_atualizar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.atualizar(1L, pet, 1L)).thenReturn(pet);
		//then
		mockMvc.perform(put("/tutores/1/pets/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isOk())
		       .andExpect(content().string("Cadastro do pet atualizado com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound - Atualizar")
	void deveRetornarNotFound_atualizar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.atualizar(1L, pet, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(put("/tutores/1/pets/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isNotFound())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest - Atualizar")
	void deveRetornarBadRequest_atualizar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.atualizar(1L, pet, 1L)).thenThrow(DataIntegratyViolationException.class);
		//then
		mockMvc.perform(put("/tutores/1/pets/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegratyViolationException));
	}
	
	@Test
	@DisplayName("Deve retornar Ok - Deletar")
	void deveRetornarOk_deletar() throws Exception {
		//when
		Mockito.doNothing().when(service).deletar(1L, 1L);
		//then
		mockMvc.perform(delete("/tutores/1/pets/1"))
		       .andExpect(status().isOk())
		       .andExpect(content().string("Pet excluído com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound - Deletar")
	void deveRetornarNotFound_deletar() throws Exception {
		//when
		Mockito.doThrow(ObjectNotFoundException.class).when(service).deletar(1L, 1L);
		//then
		mockMvc.perform(delete("/tutores/1/pets/1"))
		       .andExpect(status().isNotFound())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}

	
}

//	@Test
//	@DisplayName("Deve retornar Not Found - ListarPetsDoTutor")
//	void deveRetornarNotFound_listarPetsDoTutor() throws Exception {
//
//		Mockito.when(tutorService.buscaTutor(ID)).thenThrow(ObjectNotFoundException.class);
//		//then
//		Assertions.assertThatThrownBy(() -> service.listarPetsDoTutor(ID)).isExactlyInstanceOf(ObjectNotFoundException.class);
//
//
//		mockMvc.perform(get("/tutores/10/pets"))
//				.getClass()
////                .contentType("application/json"))
////		        .andExpect(status().isNotFound());
//	}
	
	
//		@Test
//		@DisplayName("teste")
//		void testando2(){
//	//		mockMvc.getClass().equals(MockMvc.class);
//
//
//
//	//				perform(post("/tutores/pets")
//	//                .contentType("application/json"))
//	//		        .andExpect(status().isOk());
//		}
	
	
//	@Test
//	void testando2() throws Exception {
////		mockMvc.getClass().equals(MockMvc.class);
//		mockMvc.perform(get("/tutores/pets")
//				                .contentType("application/json"));
//
//
////				perform(post("/tutores/pets")
////                .contentType("application/json"))
////		        .andExpect(status().isOk());
//	}
	
	
//	@Test
//	void testando() throws Exception {
//		mockMvc.perform(get("/tutores/pets")
//                .contentType("application/json"))
//		        .andExpect(status().isOk());
//	}



	
//	@Test
//	@DisplayName("Deve retornar Ok - Listar")
//	void deveRetornarOk_listar() throws Exception {
//		String host = "tutores/pets";
////		String port = System.getProperty("http.server.port");
//		URL url = new URL("http://" + host); //+ ":" + port + "/test");
//
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("GET");
//		int responseCode = connection.getResponseCode();
//
//		Assertions.assertThat(responseCode).isEqualTo(HttpStatus.OK.value());
//	}
	
	

	
/*	@Test
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
	}*/
