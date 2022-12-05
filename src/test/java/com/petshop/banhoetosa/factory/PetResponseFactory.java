package com.petshop.banhoetosa.factory;

import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PetResponseFactory {
	
	public static PetDetalhesResponse createPetDetalhesResponseComId() {
		return PetDetalhesResponse.builder()
		          .id(1L)
		          .nome("Mel")
		          .especie("cao")
		          .raca("pinscher")
		          .idade(10)
		          .detalhe("Nenhum")
//                  .dataCadastro(LocalDateTime.parse("2022-11-21 16:00:00"))
		          .build();
	}
	
	public static PetResponse createPetResponseComId() {
		return PetResponse.builder()
		                          .id(1L)
		                          .nome("Mel")
		                          .raca("pinscher")
		                          .build();
	}
	
}
