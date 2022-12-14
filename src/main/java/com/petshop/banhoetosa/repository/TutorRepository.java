package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Tutor findByNome(String nome);
    
    Optional<Tutor> findByEmail(String email);
    
    boolean existsByEmail(String email);

    Tutor getReferenceByEmail(String email);

}
