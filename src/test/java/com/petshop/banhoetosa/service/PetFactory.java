package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PetFactory {
	
	public static Pet createPetComId() {
		return Pet.builder()
                     .id(1L)
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
	}
	
	public static Pet createPetComIdAtualizacao() {
		return Pet.builder()
		             .id(1L)
		             .nome("Mel")
		             .especie("cao")
		             .raca("poodle")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
	}
	
	public static Pet createPetSemId() {
		return Pet.builder()
		             .nome("Mel")
		             .especie("cao")
		             .raca("pinscher")
		             .idade(10)
		             .detalhe("Nenhum")
		             .build();
	}
	
//	@Override
//	protected Object clone() {
//		Pet p = new Pet();
//		p.setId(p.getId());
//		p.setNome(p.getNome());
//		p.setEspecie(p.getEspecie());
//		p.setRaca(p.getRaca());
//		p.setIdade(p.getIdade());
//		p.setDetalhe(p.getDetalhe());
//		return p;
//	}
	
}
