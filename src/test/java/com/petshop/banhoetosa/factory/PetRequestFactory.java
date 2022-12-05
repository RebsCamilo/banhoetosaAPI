package com.petshop.banhoetosa.factory;

import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;

public class PetRequestFactory {
	
	public static PetRequest createPetDetalhesResponseComId() {
		return PetRequest.builder()
	                      .nome("Mel")
	                      .especie("cao")
	                      .raca("pinscher")
	                      .idade(10)
	                      .detalhe("Nenhum")
	                      .build();
	}
	
}
