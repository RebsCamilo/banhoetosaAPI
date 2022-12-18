package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.service.PetService;
import com.petshop.banhoetosa.service.TutorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
	void deveRetornarOptionalDePet_findByNomeEIdTutor() {
//		given
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
		
	}
	
	@Test
	void hasThisPetNameByEmailDoTutor() {
	}
	
	@Test
	void jaExisteNomePetCadastradoNesteTutor() {
	}
	
	@Test
	void findByTutor() {
	}
	
	@Test
	void existsByTutor_Id() {
	}
	
	@Test
	void findEmailTutorByTutorId() {
	}
	
	@Test
	void existsByPetAndTutor() {
	}
}