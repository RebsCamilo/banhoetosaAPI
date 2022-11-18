package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Tutor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TutorFactory {
	
	public static Tutor createTutorComId() {
		return Tutor.builder()
		                   .id(1L)
		                   .nome("Ana")
		                   .email("ana@mail")
		                   .build();
	}
	
}
