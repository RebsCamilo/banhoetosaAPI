package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.service.PetService;
import com.petshop.banhoetosa.service.TutorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.petshop.banhoetosa.factory.PetFactory.createPetComId;
import static com.petshop.banhoetosa.factory.TutorFactory.createTutorComId;

@DataJpaTest
class PetRepositoryTest {
	
	@Autowired
	private PetRepository petRepository;
	
	
	@Test
	void injectedComponentsAreNotNull(){
		Assertions.assertThat(petRepository).isNotNull();
	}
	
	
	@Test
	@DisplayName("Deve retornar Optional Com Pet - findByNomeEIdTutor")
	void deveRetornarOptionalComPet_findByNomeEIdTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		petRepository.save(pet);
        //then
		Assertions.assertThat(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId())).isNotNull();
		Assertions.assertThat(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId()).get().getId()).isGreaterThan(0L);
		Assertions.assertThat(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId())).isExactlyInstanceOf(Optional.class);
		Assertions.assertThat(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId()).get().getNome()).isEqualTo("Filomena");
		Assertions.assertThat(petRepository.findByNomeEIdTutor(pet.getNome(), tutor.getId()).get().getTutor()).isEqualTo(tutor);
	}
	
	@Test
	@DisplayName("Deve retornar Optional Sem Pet - findByNomeEIdTutor")
	void deveRetornarOptionalSemPet_findByNomeEIdTutor() {
		Assertions.assertThat(petRepository.findByNomeEIdTutor("Filomena", 1L)).isEmpty();
		Assertions.assertThat(petRepository.findByNomeEIdTutor("Filomena", 1L)).isExactlyInstanceOf(Optional.class);
	}
	
	@Test
	@DisplayName("Deve retornar True - jaExisteNomePetCadastradoNesteTutor")
	void DeveRestornarTrue_jaExisteNomePetCadastradoNesteTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		petRepository.save(pet);
		//then
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), tutor.getId())).isNotNull();
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), tutor.getId())).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), tutor.getId())).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	@DisplayName("Deve retornar False - jaExisteNomePetCadastradoNesteTutor")
	void DeveRestornarFalse_jaExisteNomePetCadastradoNesteTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		
		String nomeErrado = "Zelda";
		Long idErrado = 3L;
		//when
		petRepository.save(pet);
		//then
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, tutor.getId())).isNotNull();
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, tutor.getId())).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, tutor.getId())).isEqualTo(Boolean.FALSE);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), idErrado)).isNotNull();
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), idErrado)).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(pet.getNome(), idErrado)).isEqualTo(Boolean.FALSE);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, idErrado)).isNotNull();
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, idErrado)).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.jaExisteNomePetCadastradoNesteTutor(nomeErrado, idErrado)).isEqualTo(Boolean.FALSE);
	}
	
	@Test
	@DisplayName("Deve retornar Lista com Pets - findByTutor")
	void deveRetornarListaComPets_findByTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		petRepository.save(pet);
		//then
		Assertions.assertThat(petRepository.findByTutor(tutor)).isNotNull();
		Assertions.assertThat(petRepository.findByTutor(tutor)).isExactlyInstanceOf(ArrayList.class);
		Assertions.assertThat(petRepository.findByTutor(tutor).get(0)).isExactlyInstanceOf(Pet.class);
		Assertions.assertThat(petRepository.findByTutor(tutor).get(0).getNome()).isEqualTo("Filomena");
		Assertions.assertThat(petRepository.findByTutor(tutor).get(0).getTutor()).isEqualTo(tutor);
	}
	
	@Test
	@DisplayName("Deve retornar Lista vazia - findByTutor")
	void deveRetornarListaVazia_findByTutor() {
		//given
		Tutor tutor = createTutorComId();
		tutor.setId(3L);
		//then
		Assertions.assertThat(petRepository.findByTutor(tutor)).isEmpty();
		Assertions.assertThat(petRepository.findByTutor(tutor)).isExactlyInstanceOf(ArrayList.class);
	}
	
	@Test
	@DisplayName("Deve retornar True - existsByPetAndTutor")
	void deveRetornarTrue_existsByPetAndTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		//when
		petRepository.save(pet);
		//then
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), tutor.getId())).isNotNull();
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), tutor.getId())).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), tutor.getId())).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	@DisplayName("Deve retornar False - existsByPetAndTutor")
	void deveRetornarFalse_existsByPetAndTutor() {
		//given
		Tutor tutor = createTutorComId();
		Pet pet = createPetComId();
		pet.setNome("Filomena");
		tutor.setPets(List.of(pet));
		pet.setTutor(tutor);
		
		Long idPetErrado = 3L;
		Long idTutorErrado = 3L;
		//when
		petRepository.save(pet);
		//then
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, tutor.getId())).isNotNull();
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, tutor.getId())).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, tutor.getId())).isEqualTo(Boolean.FALSE);
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), idTutorErrado)).isNotNull();
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), idTutorErrado)).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.existsByPetAndTutor(pet.getId(), idTutorErrado)).isEqualTo(Boolean.FALSE);
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, idTutorErrado)).isNotNull();
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, idTutorErrado)).isExactlyInstanceOf(Boolean.class);
		Assertions.assertThat(petRepository.existsByPetAndTutor(idPetErrado, idTutorErrado)).isEqualTo(Boolean.FALSE);
	}
	
}