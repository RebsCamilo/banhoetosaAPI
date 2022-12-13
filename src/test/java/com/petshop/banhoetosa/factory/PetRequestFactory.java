package com.petshop.banhoetosa.factory;

import com.petshop.banhoetosa.model.request.PetRequest;
import com.petshop.banhoetosa.model.response.PetDetalhesResponse;
import com.petshop.banhoetosa.model.response.PetResponse;

public class PetRequestFactory {
	
	public static PetResponse createPetResponse() {
		return PetResponse.builder()
		                  .id(1L)
	                      .nome("Mel")
	                      .raca("pinscher")
	                      .build();
	}
	
	public static PetDetalhesResponse createPetDetalhesResponse() {
		return PetDetalhesResponse.builder()
	                      .nome("Mel")
	                      .especie("cao")
	                      .raca("pinscher")
	                      .idade(10)
	                      .detalhe("Nenhum")
	                      .build();
	}
	
	public static PetRequest createPetRequest() {
		return PetRequest.builder()
		                 .nome("Mel")
		                 .especie("cao")
		                 .raca("pinscher")
		                 .idade(10)
		                 .detalhe("Nenhum")
		                 .build();
	}
	
	
}
