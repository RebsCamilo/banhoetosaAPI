package com.petshop.banhoetosa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.model.mapper.PetMapper;
import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
import com.petshop.banhoetosa.service.PetService;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.petshop.banhoetosa.factory.PetFactory.createPetComId;
import static com.petshop.banhoetosa.factory.PetFactory.createPetSemId;
import static com.petshop.banhoetosa.factory.PetRequestFactory.*;
import static com.petshop.banhoetosa.factory.TutorFactory.createTutorComId;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
		//given
		Pet pet = createPetComId();
		Pet pet2 = createPetComId();
		pet2.setId(2L);
		List<Pet> listaPet = List.of(pet, pet2);
		PetResponse petResponse = createPetResponse();
		PetResponse petResponse2 = createPetResponse();
		petResponse2.setId(2L);
		List<PetResponse> listaRespEsperada = List.of(petResponse, petResponse2);
		//when
		Mockito.when(service.listar()).thenReturn(listaPet);
		Mockito.when(mapper.petListToPetResponseList(Mockito.any())).thenReturn(listaRespEsperada);
		//then
		MvcResult mvcResult = mockMvc.perform(get("/tutores/pets"))
		       .andExpect(status().isOk()) //Verifying HTTP Request Matching
		       .andExpect(jsonPath("$", hasSize(2)))
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$[0].nome").exists())
		       .andExpect(jsonPath("$[0].nome").isString())
		       .andExpect(jsonPath("$[1].id").exists())
		       .andExpect(jsonPath("$[1].id").isNumber())
               .andReturn();
		
		//Verifica o Output Serialization
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(listaRespEsperada));
	}
	
	@Test
	@DisplayName("Deve retornar Ok - ListarPetsDoTutor")
	void deveRetornarOk_listarPetsDoTutor() throws Exception {
		//given
		Pet pet = createPetComId();
		List<Pet> listaPet = List.of(pet);
		PetResponse petResponse = createPetResponse();
		List<PetResponse> listaResp = List.of(petResponse);
		//when
		Mockito.when(service.listarPetsDoTutor(1L)).thenReturn(listaPet);
		Mockito.when(mapper.petListToPetResponseList(Mockito.any())).thenReturn(listaResp);
		//then
		mockMvc.perform(get("/tutores/{idTutor}/pets", 1L))
		       .andExpect(status().isOk())
	           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	           .andExpect(jsonPath("$[0].id").exists())
	           .andExpect(jsonPath("$[0].id").isNumber())
	           .andExpect(jsonPath("$[0].nome").exists())
	           .andExpect(jsonPath("$[0].nome").isString());
//			   .andExpect(content().string("Mel")); //como verifica se o nome é "Mel"? //Verifica-se o output serializado
	} //ver page https://stackoverflow.com/questions/46885972/mockmvc-in-junit-tests-checking-result-for-listobject-and-mapenum-object
	
	@Test
	@DisplayName("Deve retornar Output Serializado - ListarPetsDoTutor")
	void deveRetornarOutputSerializado_listarPetsDoTutor() throws Exception {
		//given
		Pet pet = createPetComId();
		List<Pet> listaPet = List.of(pet);
		PetResponse petResponse = createPetResponse();
		List<PetResponse> listaRespEsperada = List.of(petResponse);
		//when
		Mockito.when(service.listarPetsDoTutor(1L)).thenReturn(listaPet);
		Mockito.when(mapper.petListToPetResponseList(Mockito.any())).thenReturn(listaRespEsperada);
		//then
		MvcResult mvcResult = mockMvc.perform(get("/tutores/{idTutor}/pets", 1L))
		       .andExpect(status().isOk()).andReturn();
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(listaRespEsperada));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound por ObjectNotFoundException - ListarPetsDoTutor")
	void deveRetornarNotFoundPorObjectNotFoundException_listarPetsDoTutor() throws Exception {
		//when
		Mockito.when(service.listarPetsDoTutor(1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(get("/tutores/{idTutor}/pets", 1L))
		       .andExpect(status().isNotFound())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Erro na URL - ListarPetsDoTutor")
	void deveRetornarBadRequestPorErroNaUrl_listarPetsDoTutor() throws Exception {
		mockMvc.perform(get("/tutores/{idTutor}/pets", "1x"))
		       .andExpect(status().isBadRequest());
		//como tratar erro de paramtro na url (passou uma string e deveria receber um long)
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
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
					                .contentType(MediaType.APPLICATION_JSON)
					                .content(objectMapper.writeValueAsString(petRequest)))
		        .andExpect(status().isCreated())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isString())
		       .andExpect(content().string("Pet cadastrado com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por DataIntegratyViolationException - Cadastrar") //ex: nome pet ja existente
	void deveRetornarBadRequestPorDataIntegratyViolationException_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		Pet pet = createPetSemId();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.cadastrar(pet, 1L)).thenThrow(DataIntegratyViolationException.class);
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegratyViolationException));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound por ObjectNotFoundException - Cadastrar") //tutor não existe
	void deveRetornarNotFoundPorObjectNotFoundException_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		Pet pet = createPetSemId();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.cadastrar(pet, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isNotFound())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Nome Null - Cadastrar")
	void deveRetornarBadRequestPorValidationDeInputNomeNull_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setNome(null);
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Nome Empty - Cadastrar")
	void deveRetornarBadRequestPorValidationDeInputNomeEmpty_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setNome("");
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Raca - Cadastrar")
	void deveRetornarBadRequestPorValidationDeInputRaca_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setRaca("Pinscher rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
//		       .andExpect(jsonPath("$").exists());
//		       .andExpect(jsonPath("$").isMap())
//		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegratyViolationException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Idade - Cadastrar")
	void deveRetornarBadRequestPorValidationDeInputIdade_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setIdade(null);
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Detalhe - Cadastrar")
	void deveRetornarBadRequestPorValidationDeInputDetalhe_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setDetalhe("Detalhe eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Erro na URL - Cadastrar")
	void deveRetornarBadRequestPorErroNaUrl_cadastrar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		//then
		mockMvc.perform(post("/tutores/{idTutor}/pets", "1x")
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
		mockMvc.perform(get("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap());
				//como faço para conferir se objeto é o mesmo? se valores sao iguais
	}
	
	@Test
	@DisplayName("Deve retornar Output Serializado - Detalhar") ////////////////////////////////////////////////
	void deveRetornarOutputSerializado_detalhar() throws Exception {
		//given
		Pet pet = createPetComId();
		Tutor tutor = createTutorComId();
		pet.setTutor(tutor);
		PetDetalhesResponse petDetalheRespEsperada = createPetDetalhesResponse();
		//when
		Mockito.when(service.detalhar(1L, 1L)).thenReturn(pet);
		Mockito.when(mapper.petServicosToPetDetalhesResponse(Mockito.any())).thenReturn(petDetalheRespEsperada);
		//then
		MvcResult mvcResult = mockMvc.perform(get("/tutores/{idTutor}/pets/{idPet}", 1L, 1L))
		                             .andReturn();
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(petDetalheRespEsperada));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound por ObjectNotFoundException - Detalhar")
	void deveRetornarNotFoundPorObjectNotFoundException_detalhar() throws Exception {
		//when
		Mockito.when(service.detalhar(1L, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(get("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L))
		       .andExpect(status().isNotFound())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
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
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isString())
		       .andExpect(content().string("Cadastro do pet atualizado com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound por ObjectNotFoundException - Atualizar")
	void deveRetornarNotFoundPorObjectNotFoundException_atualizar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.atualizar(1L, pet, 1L)).thenThrow(ObjectNotFoundException.class);
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isNotFound())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por DataIntegratyViolationException - Atualizar")
	void deveRetornarBadRequestPorDataIntegratyViolationException_atualizar() throws Exception {
		//given
		Pet pet = createPetComId();
		PetRequest petRequest = createPetRequest();
		//when
		Mockito.when(mapper.petRequestToPet(Mockito.any())).thenReturn(pet);
		Mockito.when(service.atualizar(1L, pet, 1L)).thenThrow(DataIntegratyViolationException.class);
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegratyViolationException));
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Nome Null - Atualizar")
	void deveRetornarBadRequestPorValidationDeInputNomeNull_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setNome(null);
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Nome Empty - Atualizar")
	void deveRetornarBadRequestPorValidationDeInputNomeEmpty_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setNome("");
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Raca - Atualizar")
	void deveRetornarBadRequestPorValidationDeInputRaca_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setRaca("Pinscher rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Idade - Atualizar")
	void deveRetornarBadRequestPorValidationDeInputIdade_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setIdade(null);
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Validacao de Input Detalhe - Atualizar")
	void deveRetornarBadRequestPorValidationDeInputDetalhe_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		petRequest.setDetalhe("Detalhe eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
		//como por exceção para validacao de input?
	}
	
	@Test
	@DisplayName("Deve retornar BadRequest por Erro na URL - Atualizar")
	void deveRetornarBadRequestPorErroNaUrl_atualizar() throws Exception {
		//given
		PetRequest petRequest = createPetRequest();
		//then
		mockMvc.perform(put("/tutores/{idTutor}/pets/{idPet}", 1L, "1x")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(petRequest)))
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve retornar Ok - Deletar")
	void deveRetornarOk_deletar() throws Exception {
		//when
		Mockito.doNothing().when(service).deletar(1L, 1L);
		//then
		mockMvc.perform(delete("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isString())
		       .andExpect(content().string("Pet excluído com sucesso"));
	}
	
	@Test
	@DisplayName("Deve retornar NotFound por ObjectNotFoundException - Deletar")
	void deveRetornarNotFoundPorObjectNotFoundException_deletar() throws Exception {
		//when
		Mockito.doThrow(ObjectNotFoundException.class).when(service).deletar(1L, 1L);
		//then
		mockMvc.perform(delete("/tutores/{idTutor}/pets/{idTutor}", 1L, 1L))
		       .andExpect(status().isNotFound())
		       .andExpect(jsonPath("$").exists())
		       .andExpect(jsonPath("$").isMap())
		       .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
	}
	
}
