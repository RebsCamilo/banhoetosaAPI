package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Tutor findByNome(String nome);

    boolean existsByEmail(String email);


}
