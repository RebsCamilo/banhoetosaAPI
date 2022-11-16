package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value = "SELECT p FROM Pet p WHERE p.nome = ?1 AND p.tutor.id = ?2")
    Optional<Pet> findByNomeEIdTutor(String nomePet, Long id);

//    List<Pet> findByTutorId(Long tutorId);

    @Query(value = "SELECT\tcount(p.nome) > 0 " +
            "FROM pets p\n" +
            "INNER JOIN tutores t ON p.tutor_id = t.id\n" +
            "WHERE t.email = :email AND p.nome = :nome", nativeQuery = true)
    boolean hasThisPetNameByEmailDoTutor(@Param("nome") String nome, @Param("email") String email);
    
//    @Query(value = "SELECT p FROM Pet p WHERE p.id = ?1 AND p.tutor.id = ?2")
    @Query(value = "SELECT count(p.nome) > 0 FROM Pet p WHERE p.nome = ?1 AND p.tutor.id = ?2")
    boolean jaExisteNomePetCadastradoNesteTutor(String nome, Long idTutor);

//    Optional<List<Pet>> findByTutor(Tutor tutor);
    List<Pet> findByTutor(Tutor tutor);

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
    
    @Query(value = "SELECT p FROM Pet p WHERE p.id = ?1 AND p.tutor.id = ?2")
    Optional<Pet> findByPetAndTutor(Long idPet, Long idTutor);
    
    @Query(value = "SELECT count(p) > 0 FROM Pet p WHERE p.id = ?1 AND p.tutor.id = ?2")
    boolean existsByPetAndTutor(Long idPet, Long idTutor);
    

}






