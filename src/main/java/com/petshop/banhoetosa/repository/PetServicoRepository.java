package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetServicoRepository extends JpaRepository<PetServico, Long> {

//    @Modifying
//    @Query("delete from PetService p where p.pet = ?pet")
//    void deleteByPet(Pet pet);

}
