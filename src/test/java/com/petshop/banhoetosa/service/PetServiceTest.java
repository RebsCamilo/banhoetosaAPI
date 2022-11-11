package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class PetServiceTest {
	
	@InjectMocks
	private PetService service;
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private TutorRepository tutorRepository;
	
	private static final Long ID = 1L;
	private static final String NOME = "Mel";
	private static final String ESPECIE = "cao";
	private static final String RACA = "pinscher";
	private static final int IDADE = 10;
	private static final String DETALHE = "Nenhum";

	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar um Optional com Pet")
	public void deveRetornarUmOptionalComPet() {
		//given
//		Long id = 1L;
		Optional<Pet> pet = Optional.of(Pet.builder()
		                                   .id(ID)
		                                   .nome("Mel")
		                                   .especie("cao")
		                                   .raca("pinscher")
		                                   .idade(10)
		                                   .detalhe("Nenhum")
		                                   .build());
		//when
		Mockito.when(petRepository.findById(ID)).thenReturn(pet);
		Optional<Pet> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEqualTo(pet);
	}
	
	@Test
	@DisplayName("Deve retornar um Optional sem Pet")
	public void deveRetornarUmOptionalSemPet() {
		//given
//		Long id = 1L;
		//when
		Mockito.when(petRepository.findById(ID)).thenReturn(Optional.empty());
		Optional<Pet> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar uma lista com um Pet")
	public void deveRetornarUmaListaDePet() {
		//given
//		Long id = 1L;
		Pet pet = Pet.builder()
                           .id(ID)
                           .nome("Mel")
                           .especie("cao")
                           .raca("pinscher")
                           .idade(10)
                           .detalhe("Nenhum")
                           .build();
		//when
		Mockito.when(petRepository.findAll()).thenReturn(List.of(pet));
		List<Pet> result = service.listar();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Pet.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getNome()).isEqualTo(NOME);
		Assertions.assertThat(result.get(INDEX).getEspecie()).isEqualTo(ESPECIE);
		Assertions.assertThat(result.get(INDEX).getRaca()).isEqualTo(RACA);
		Assertions.assertThat(result.get(INDEX).getIdade()).isEqualTo(IDADE);
		Assertions.assertThat(result.get(INDEX).getDetalhe()).isEqualTo(DETALHE);
	}
	
	@Test
	@DisplayName("Deve retornar Lista Vazia")
	public void deveRetornarNull() {
		//when
		Mockito.when(petRepository.findAll()).thenReturn(List.of());
		List<Pet> result = service.listar();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar um Pet cadastrado")
	public void deveRetornarUmPetCadastrado() {
		//given
		Pet pet = Pet.builder()
		             .id(ID)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
		
		Tutor tutor = Tutor.builder()
		                   .id(ID)
		                   .nome("Ana")
		                   .telefone1("98888-0000")
		                   .email("ana@mail")
		                   .pets(new ArrayList())
		                   .build();
		
		//when
		Mockito.when(petRepository.save(pet)).thenReturn(pet);
		Pet result = service.cadastrar(pet, "ana@mail");
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Pet.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getNome()).isEqualTo(NOME);
		Assertions.assertThat(result.getEspecie()).isEqualTo(ESPECIE);
		Assertions.assertThat(result.getRaca()).isEqualTo(RACA);
		Assertions.assertThat(result.getIdade()).isEqualTo(IDADE);
		Assertions.assertThat(result.getDetalhe()).isEqualTo(DETALHE);
	}
	
	@Test
	@DisplayName("Deve retornar Lista de Pets do Tutor Especifico")
	public void deveRetornarListaDePetsDoTutorEspecifico() {
		//given
		Tutor tutor = Tutor.builder()
		                   .id(ID)
		                   .nome("Ana")
		                   .telefone1("98888-0000")
		                   .email("ana@mail")
		                   .pets(new ArrayList())
		                   .build();
		
		Pet pet = Pet.builder()
		             .id(ID)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
		
		tutor.getPets().add(pet);
		
		//when
		Mockito.when(petRepository.findByTutorId(ID)).thenReturn(Optional.of(List.of(pet)));
		List<Pet> result = service.listarPetsDoTutor(ID);
		
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Pet.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getNome()).isEqualTo(NOME);
		Assertions.assertThat(result.get(INDEX).getEspecie()).isEqualTo(ESPECIE);
		Assertions.assertThat(result.get(INDEX).getRaca()).isEqualTo(RACA);
		Assertions.assertThat(result.get(INDEX).getIdade()).isEqualTo(IDADE);
		Assertions.assertThat(result.get(INDEX).getDetalhe()).isEqualTo(DETALHE);
	}
	
	@Test
	@DisplayName("Deve retornar Pet do Tutor Especifico atualizado")
	public void deveRetornarPetDoTutorEspecificoAtualizado() {
		//given
		Tutor tutor = Tutor.builder()
	                   .id(ID)
	                   .nome("Ana")
	                   .telefone1("98888-0000")
	                   .email("ana@mail")
	                   .pets(new ArrayList())
	                   .build();
		
		Pet pet = Pet.builder()
		             .id(ID)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .tutor(tutor)
		             .build();
		
		Pet petAtt = Pet.builder()
		                .id(ID)
		                .nome("Melzinha")
		                .especie("cao")
		                .raca("pinscher")
		                .idade(10)
		                .detalhe("Nenhum")
		                .tutor(tutor)
		                .build();
		
		//when
		Mockito.when(petRepository.getReferenceById(ID)).thenReturn(pet);
		Mockito.when(service.buscaTutor("ana@mail")).thenReturn(tutor);
		Mockito.when(petRepository.save(pet)).thenReturn(pet);
		Pet result = service.atualizar(ID, petAtt, "ana@mail");
		
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Pet.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getNome()).isEqualTo("Melzinha");
		Assertions.assertThat(result.getEspecie()).isEqualTo(ESPECIE);
		Assertions.assertThat(result.getRaca()).isEqualTo(RACA);
		Assertions.assertThat(result.getIdade()).isEqualTo(IDADE);
		Assertions.assertThat(result.getDetalhe()).isEqualTo(DETALHE);
	}
	
	@Test
	@DisplayName("Deve deletar Pet")
	public void deveDeletarPet() {
		//given
		Pet pet = Pet.builder()
		             .id(ID)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
		
		//when
		Mockito.when(petRepository.getReferenceById(ID)).thenReturn(pet);
		Mockito.doNothing().when(petRepository).delete(pet);
		//then
		service.deletar(ID);
		Mockito.verify(petRepository, Mockito.times(1)).delete(pet);
	
	}
	
}