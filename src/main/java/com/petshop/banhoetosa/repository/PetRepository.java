package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNome(String nomePet);

    @Query(value = "SELECT\tcount(p.nome) > 0 " +
            "FROM pets p\n" +
            "INNER JOIN tutores t ON p.id_tutor = t.id\n" +
            "WHERE t.email = :email AND p.nome = :nome", nativeQuery = true)
    boolean hasThisPetNameByEmailDoTutor(@Param("nome") String nome, @Param("email") String email);

//    @Query(value = "SELECT\tp.nome = :nome " +
//            "FROM pets p\n" +
//            "INNER JOIN tutores t ON p.id_tutor = t.id\n" +
//            "WHERE t.email = :email", nativeQuery = true)
//    boolean existsPetByEmailDoTutor(@Param("nome") String nome, @Param("email") String email);

    boolean existsByTutor_Email(String email);

}






