package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNome(String nomePet);

//    List<Pet> findByTutorId(Long tutorId);

    @Query(value = "SELECT\tcount(p.nome) > 0 " +
            "FROM pets p\n" +
            "INNER JOIN tutores t ON p.tutor_id = t.id\n" +
            "WHERE t.email = :email AND p.nome = :nome", nativeQuery = true)
    boolean hasThisPetNameByEmailDoTutor(@Param("nome") String nome, @Param("email") String email);

    Optional<List<Pet>> findByTutorId(Long tutorId);

    //Da erro
//    @Query(value = "SELECT\tp.nome" +
//            "FROM pets p\n" +
//            "INNER JOIN tutores t ON p.tutor_id = t.id\n" +
//            "WHERE t.email = :email AND p.nome = :nome", nativeQuery = true)
//    boolean existsPetByEmailDoTutor(@Param("nome") String nome, @Param("email") String email);

    boolean existsByTutor_Id(Long id);

    @Query(value = "SELECT t.email\n" +
            "FROM pets p\n" +
            "INNER JOIN tutores t ON p.tutor_id = t.id\n" +
            "WHERE t.id = :idTutor", nativeQuery = true)
    String findEmailTutorByTutorId(Long idTutor);

}






