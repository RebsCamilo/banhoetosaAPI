package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Endereco;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TutorServiceTest {
	
	@InjectMocks
	private TutorService service;
	
	@Mock
	private TutorRepository repository;
	
	private static final Long ID = 1L;
	private static final String NOME = "Ana";
	private static final String TELEFONE1 = "98888-0000";
	private static final String EMAIL = "ana@mail";
	private static final Endereco ENDERECO = new Endereco("Rua", 100, "Bairro", "Complemento", "Cep");
	private static final List<Pet> LISTA_PETS = new ArrayList<>();
	
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar Lista de Tutor")
	void deveRetornarListaDeTutor() {
		//given
		Tutor tutor = Tutor.builder()
		               .id(ID)
		               .nome(NOME)
		               .telefone1(TELEFONE1)
		               .email(EMAIL)
		               .endereco(ENDERECO)
		               .pets(LISTA_PETS)
		               .build();
		
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of(tutor));
		List<Tutor> result = service.listar();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Tutor.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getNome()).isEqualTo(NOME);
		Assertions.assertThat(result.get(INDEX).getTelefone1()).isEqualTo(TELEFONE1);
		Assertions.assertThat(result.get(INDEX).getEmail()).isEqualTo(EMAIL);
		Assertions.assertThat(result.get(INDEX).getEndereco()).isEqualTo(ENDERECO);
		Assertions.assertThat(result.get(INDEX).getPets()).isEqualTo(LISTA_PETS);
	}
	
	@Test
	@DisplayName("Deve retornar Lista Vazia")
	public void deveRetornarNull() {
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of());
		List<Tutor> result = service.listar();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar o Tutor cadastrado")
	void deveRetornarOTutorCadastrado() {
		//given
		Tutor tutor = Tutor.builder()
		                   .id(ID)
		                   .nome(NOME)
		                   .telefone1(TELEFONE1)
		                   .email(EMAIL)
		                   .endereco(ENDERECO)
		                   .pets(LISTA_PETS)
		                   .build();
		//when
		Mockito.when(repository.save(tutor)).thenReturn(tutor);
		Tutor result = service.cadastrar(tutor, ENDERECO);
		
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Tutor.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getNome()).isEqualTo(NOME);
		Assertions.assertThat(result.getTelefone1()).isEqualTo(TELEFONE1);
		Assertions.assertThat(result.getEmail()).isEqualTo(EMAIL);
		Assertions.assertThat(result.getEndereco()).isEqualTo(ENDERECO);
		Assertions.assertThat(result.getPets()).isEqualTo(LISTA_PETS);
	}
	
	@Test
	@DisplayName("Deve retornar um Optional com Tutor")
	void deveRetornarUmOptionalComTutor() {
		Optional<Tutor> tutor = Optional.of(Tutor.builder()
	                                         .id(ID)
	                                         .nome(NOME)
	                                         .telefone1(TELEFONE1)
	                                         .email(EMAIL)
	                                         .endereco(ENDERECO)
	                                         .pets(LISTA_PETS)
	                                         .build());
		
		//when
		Mockito.when(repository.findById(ID)).thenReturn(tutor);
		Tutor result = service.detalhar(ID);
//		Optional<Tutor> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEqualTo(tutor.get());
	}
	
	@Test
	@DisplayName("Deve retornar um Optional sem Pet")
	public void deveRetornarUmOptionalSemPet() {
		//when
		Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());
		Tutor result = service.detalhar(ID);
//		Optional<Tutor> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isNull();
//		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar Tutor atualizado")
	void deveRetornarTutorAtualizado() {
		//given
		Tutor tutor = Tutor.builder()
		                   .id(ID)
		                   .nome(NOME)
		                   .telefone1(TELEFONE1)
		                   .email(EMAIL)
		                   .endereco(ENDERECO)
		                   .pets(LISTA_PETS)
		                   .build();
		
		Tutor tutorAtt = Tutor.builder()
		                   .id(ID)
		                   .nome("Maria")
		                   .telefone1(TELEFONE1)
		                   .email(EMAIL)
		                   .endereco(ENDERECO)
		                   .pets(LISTA_PETS)
		                   .build();
		
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(tutor);
		Mockito.when(repository.save(tutor)).thenReturn(tutor);
		Tutor result = service.atualizar(ID, tutorAtt, ENDERECO);
		
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Tutor.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getNome()).isEqualTo("Maria");
		Assertions.assertThat(result.getTelefone1()).isEqualTo(TELEFONE1);
		Assertions.assertThat(result.getEmail()).isEqualTo(EMAIL);
		Assertions.assertThat(result.getEndereco()).isEqualTo(ENDERECO);
		Assertions.assertThat(result.getPets()).isEqualTo(LISTA_PETS);
		
	}
	
	@Test
	@DisplayName("Deve deletar Tutor")
	void deveDeletarTutor() {
		//given
		Tutor tutor = Tutor.builder()
		                   .id(ID)
		                   .nome(NOME)
		                   .telefone1(TELEFONE1)
		                   .email(EMAIL)
		                   .endereco(ENDERECO)
		                   .pets(LISTA_PETS)
		                   .build();
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(tutor);
		Mockito.doNothing().when(repository).delete(tutor);
		//then
		service.deletar(ID);
		Mockito.verify(repository, Mockito.times(1)).delete(tutor);
	}
	
}