package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.petshop.banhoetosa.factory.PetFactory.*;
import static com.petshop.banhoetosa.factory.TutorFactory.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class) //funcionando mesmo sem essa anotação
class PetServiceTest {
	
	@InjectMocks
	private PetService service;
	
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private TutorRepository tutorRepository;
	
	@Mock
	private TutorService tutorService;
	
	private static final Long ID = 1L;
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar uma lista com um Pet - Listar")
	public void deveRetornarUmaListaDePet_listar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		Mockito.when(petRepository.findAll()).thenReturn(List.of(pet));
		List<Pet> result = service.listar();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Pet.class);

		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(pet.getId());
		Assertions.assertThat(result.get(INDEX).getNome()).isEqualTo(pet.getNome());
		Assertions.assertThat(result.get(INDEX).getEspecie()).isEqualTo(pet.getEspecie());
		Assertions.assertThat(result.get(INDEX).getRaca()).isEqualTo(pet.getRaca());
		Assertions.assertThat(result.get(INDEX).getIdade()).isEqualTo(pet.getIdade());
		Assertions.assertThat(result.get(INDEX).getDetalhe()).isEqualTo(pet.getDetalhe());
	}

	@Test
	@DisplayName("Deve retornar Lista Vazia - Listar")
	public void deveRetornarListaVazia_listar() {
		//when
		Mockito.when(petRepository.findAll()).thenReturn(List.of());
		List<Pet> result = service.listar();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar uma lista com um Pet do Tutor Especifico - ListarPetsDoTutor")
	public void deveRetornarUmaListaDePetDoTutorEspecifico_listarPetsDoTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		Mockito.when(tutorService.buscaTutor(tutor.getId())).thenReturn(tutor);
		Mockito.when(petRepository.findByTutor(tutor)).thenReturn(List.of(pet));
		List<Pet> result = service.listarPetsDoTutor(tutor.getId());
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Pet.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(pet.getId());
		Assertions.assertThat(result.get(INDEX).getNome()).isEqualTo(pet.getNome());
		Assertions.assertThat(result.get(INDEX).getEspecie()).isEqualTo(pet.getEspecie());
		Assertions.assertThat(result.get(INDEX).getRaca()).isEqualTo(pet.getRaca());
		Assertions.assertThat(result.get(INDEX).getIdade()).isEqualTo(pet.getIdade());
		Assertions.assertThat(result.get(INDEX).getDetalhe()).isEqualTo(pet.getDetalhe());
	}
	
	@Test
	@DisplayName("Deve retornar Lista Vazia - ListarPetsDoTutor")
	public void deveRetornarListaVazia_listarPetsDoTutor() {
		//given
		Tutor tutor = createTutorComId();
		//variaveis
		Long tutorId = tutor.getId();
		//when
		Mockito.when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(tutor));
		Mockito.when(petRepository.findByTutor(tutor)).thenReturn(List.of());
		List<Pet> result = service.listarPetsDoTutor(tutorId);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.isEmpty());
	}
	
	@Test
	@DisplayName("Deve retornar Uma Exceção ObjectNotFound - ListarPetsDoTutor")
	public void deveRetornarUmaExcecaoObjectNotFound_listarPetsDoTutor() { //testa caso tutor não exista
		//when
		Mockito.when(tutorService.buscaTutor(ID)).thenThrow(ObjectNotFoundException.class);
		//then
		Assertions.assertThatThrownBy(() -> service.listarPetsDoTutor(ID)).isExactlyInstanceOf(ObjectNotFoundException.class);
	}

	@Test
	@DisplayName("Deve retornar um Pet - Cadastrar")
	public void deveRetornarUmPet_cadastrar() {
		//given
		Tutor tutor = createTutorComId();
		Pet petSemId = createPetSemId();
		Pet petComId = createPetComId();
		tutor.setPets(List.of(petSemId));
		petSemId.setTutor(tutor);
		//variaveis
		Long tutorId = tutor.getId();
		//when
		Mockito.when(petRepository.findByNomeEIdTutor(petSemId.getNome(), tutorId)).thenReturn(Optional.empty());
		Mockito.when(tutorService.buscaTutor(tutorId)).thenReturn(tutor);
		Mockito.when(petRepository.save(petSemId)).thenReturn(petComId);
		Pet result = service.cadastrar(petSemId, tutorId);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(petComId);
		
		Assertions.assertThat(result.getId()).isEqualTo(petComId.getId());
		Assertions.assertThat(result.getNome()).isEqualTo(petComId.getNome());
		Assertions.assertThat(result.getEspecie()).isEqualTo(petComId.getEspecie());
		Assertions.assertThat(result.getRaca()).isEqualTo(petComId.getRaca());
		Assertions.assertThat(result.getIdade()).isEqualTo(petComId.getIdade());
		Assertions.assertThat(result.getTutor()).isEqualTo(petComId.getTutor());
		Assertions.assertThat(result.getDetalhe()).isEqualTo(petComId.getDetalhe());
	}
	
	@Test
	@DisplayName("Deve retornar uma Excecao DataIntegratyViolation - Cadastrar")
	public void deveRetornarUmaExcecaoDataIntegratyViolation_cadastrar() {
		//given //pet ja cadastrado neste tutor
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		Mockito.when(service.validarNomePet(pet.getId(), pet.getNome(), tutor.getId())).thenThrow(DataIntegratyViolationException.class);
		//then
		Assertions.assertThatThrownBy(() -> service.cadastrar(pet, tutor.getId()))
		          .isExactlyInstanceOf(DataIntegratyViolationException.class);
	}
	
	@Test
	@DisplayName("Deve retornar uma Excecao ObjectNotFound - Cadastrar")
	public void deveRetornarUmaExcecaoObjectNotFound_cadastrar() {
		//given
		Tutor tutor = createTutorComId();
		Pet petSemId = createPetSemId();
		//when
		Mockito.when(petRepository.findByNomeEIdTutor(petSemId.getNome(), tutor.getId())).thenReturn(Optional.empty());
		Mockito.when(tutorService.buscaTutor(tutor.getId())).thenThrow(ObjectNotFoundException.class);
		//then
		Assertions.assertThatThrownBy(() -> service.cadastrar(petSemId, tutor.getId()))
		          .isExactlyInstanceOf(ObjectNotFoundException.class);
	}
	
	@Test
	@DisplayName("Deve retornar um Pet - Detalhar")
	public void deveRetornarUmPet_detalhar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//variaveis
		Long petId = pet.getId();
		Long tutorId = tutor.getId();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.TRUE);
		Mockito.when(service.existePeloIdPetEIdTutor(petId, tutorId)).thenReturn(Boolean.TRUE);
		Mockito.when(petRepository.getReferenceById(petId)).thenReturn(pet);
		Pet result = service.detalhar(petId, pet.getId());
		//then
		Assertions.assertThat(result).isEqualTo(pet);
	}
	
	@Test
	@DisplayName("Deve retornar uma Exceção ObjectNotFound - Detalhar")
	public void deveRetornarUmaExcecaoObjectNotFound_detalhar() {
		//when
		Mockito.when(petRepository.existsByPetAndTutor(ID, ID)).thenReturn(Boolean.FALSE);
		//then
		Assertions.assertThatThrownBy(() -> service.existePeloIdPetEIdTutor(ID, ID)).isExactlyInstanceOf(ObjectNotFoundException.class);
	}
	
	@Test
	@DisplayName("Deve retornar um Pet - Atualizar")
	public void deveRetornarUmPet_atualizar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		Pet petAtt = createPetComIdAtualizacao();
		//variaveis
		Long tutorId = tutor.getId();
		Long petId = pet.getId();
		String petAttNome = petAtt.getNome();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.TRUE);
		Mockito.when(petRepository.getReferenceById(petId)).thenReturn(pet);
		Mockito.when(petRepository.findByNomeEIdTutor(petAttNome, tutorId)).thenReturn(Optional.of(pet));
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petAttNome, tutorId)).thenReturn(Boolean.TRUE);
//		Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenReturn(petAtt);
		Mockito.when(petRepository.save(pet)).thenReturn(petAtt);
		Pet result = service.atualizar(petId, petAtt, tutorId);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(petAtt);
		Assertions.assertThat(result.getNome()).isEqualTo(petAtt.getNome());
		Assertions.assertThat(result.getRaca()).isEqualTo("poodle");
	}
	
	@Test
	@DisplayName("Deve retornar uma Excecao ObjectNotFound - Atualizar")
	public void deveRetornarExcecaoObjectNotFound_atualizar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		Pet petAtt = createPetComIdAtualizacao();
		//variaveis
		Long tutorId = tutor.getId();
		Long petId = pet.getId();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.FALSE);
		//then
		Assertions.assertThatThrownBy(() -> service.atualizar(petId, petAtt, tutorId)).isExactlyInstanceOf(ObjectNotFoundException.class);
	}
	
	@Test
	@DisplayName("Deve retornar uma Excecao DataIntegratyViolation - Atualizar")
	public void deveRetornarExcecaoDataIntegratyViolation_atualizar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setTutor(tutor);
		Pet pet2 = createPetComId();
		pet2.setTutor(tutor);
		pet2.setNome("Miina");
		pet2.setId(2L);
		tutor.setPets(List.of(pet, pet2));
		Pet petAtt = createPetComId();
		petAtt.setNome("Miina");
		//variaveis
		Long tutorId = tutor.getId();
		Long petId = pet.getId();
		String petAttNome = petAtt.getNome();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.TRUE);
		Mockito.when(petRepository.getReferenceById(petId)).thenReturn(pet);
		Mockito.when(petRepository.findByNomeEIdTutor(petAttNome, tutorId)).thenReturn(Optional.of(pet2));
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petAttNome, tutorId)).thenReturn(Boolean.TRUE);
		//then
		Assertions.assertThatThrownBy(() -> service.atualizar(petId, petAtt, tutorId)).isExactlyInstanceOf(DataIntegratyViolationException.class);
	}
	
	@Test
	@DisplayName("Deve retornar Void - Deletar")
	public void deveRetornarVoid_deletar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//variaveis
		Long tutorId = tutor.getId();
		Long petId = pet.getId();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.TRUE);
		Mockito.doNothing().when(petRepository).delete(pet);
		service.deletar(petId, tutorId);
		//then
		Mockito.verify(petRepository, Mockito.times(1)).deleteById(petId);
	}
	
	@Test
	@DisplayName("Deve retornar uma Excecao ObjectNotFound - Deletar")
	public void deveRetornarExcecaoObjectNotFound_deletar() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		//variaveis
		Long tutorId = tutor.getId();
		Long petId = pet.getId();
		//when
		Mockito.when(petRepository.existsByPetAndTutor(petId, tutorId)).thenReturn(Boolean.FALSE);
		//then
		Assertions.assertThatThrownBy(() -> service.deletar(petId, tutorId)).isExactlyInstanceOf(ObjectNotFoundException.class);
	}
	
	
	@Test
	@DisplayName("Deve retornar True - JaExisteNomePet")
	public void deveRetornarTrue_jaExisteNomePet() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//variaveis
		Long tutorId = tutor.getId();
		String petNome = pet.getNome();
		//when
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petNome, tutorId)).thenReturn(Boolean.TRUE);
		boolean result = service.jaExisteNomePet(petNome, tutorId);
		//then
		Assertions.assertThat(result).isEqualTo(true);
	}
	
	@Test
	@DisplayName("Deve retornar False - JaExisteNomePet")
	public void deveRetornarFalse_jaExisteNomePet() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//variaveis
		Long tutorId = tutor.getId();
		String petNome = pet.getNome();
		//when
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petNome, tutorId)).thenReturn(Boolean.FALSE);
		boolean result = service.jaExisteNomePet(petNome, tutorId);
		//then
		Assertions.assertThat(result).isEqualTo(false);
	}
	
	
	
	@Test
	@DisplayName("Deve retornar True com Pet Presente - ValidarNomePet")
	public void deveRetornarTrueComPetPresente_validarNomePet() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		Mockito.when(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId())).thenReturn(Optional.of(pet));
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), tutor.getId())).thenReturn(Boolean.TRUE);
		boolean result = service.validarNomePet(pet.getId(), pet.getNome(), tutor.getId());
		//then
		Assertions.assertThat(result).isEqualTo(true);
	}
	
	@Test
	@DisplayName("Deve retornar True com Pet de mesmo Nome Presente - ValidarNomePet")
	public void deveRetornarTrueComPetDeMesmoNomePresente_validarNomePet() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setTutor(tutor);
		tutor.setPets(List.of(pet));
		Pet petDuplicado = createPetComId(); //mesmo nome (ja existe)
		//when
		Mockito.when(petRepository.findByNomeEIdTutor(petDuplicado.getNome(), tutor.getId())).thenReturn(Optional.of(pet));
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petDuplicado.getNome(), tutor.getId())).thenReturn(Boolean.TRUE);
		boolean result = service.validarNomePet(petDuplicado.getId(), petDuplicado.getNome(), tutor.getId());
		//then
		Assertions.assertThat(result).isEqualTo(true);
	}
	
	@Test
	@DisplayName("Deve retornar Excecao DataIntegratyViolation - ValidarNomePet")
	public void deveRetornarExcecaoDataIntegratyViolation_validarNomePet() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setTutor(tutor);
		tutor.setPets(List.of(pet));
		Pet petDuplicado = createPetComId(); //mesmo nome (ja existe)
		pet.setId(2L);
		//when
		Mockito.when(petRepository.findByNomeEIdTutor(petDuplicado.getNome(), tutor.getId())).thenReturn(Optional.of(pet));
		Mockito.when(petRepository.jaExisteNomePetCadastradoNesteTutor(petDuplicado.getNome(), tutor.getId())).thenReturn(Boolean.TRUE);
		//then
		Assertions.assertThatThrownBy(() -> service.validarNomePet(petDuplicado.getId(), petDuplicado.getNome(), tutor.getId()))
		          .isExactlyInstanceOf(DataIntegratyViolationException.class);
	}
	
}
