package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PetFactory {
	public static Pet createPet() {
		Tutor tutor = Tutor.builder()
		                   .id(1L)
		                   .nome("Ana")
		                   .email("ana@mail")
		                   .build();
		Pet pet = Pet.builder()
		             .id(1L)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .tutor(tutor)
		             .build();
		tutor.setPets(List.of(pet));
		return pet;
	}
	
}
