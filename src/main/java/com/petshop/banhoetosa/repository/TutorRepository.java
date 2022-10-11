package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
//    List<Tutor> findByNome(String nome);
    Tutor findByNome(String nome);

}
