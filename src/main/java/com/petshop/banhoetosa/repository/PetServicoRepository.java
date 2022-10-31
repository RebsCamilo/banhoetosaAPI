package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetServicoRepository extends JpaRepository<PetServico, Long> {

//    boolean existsByPet_Id(Long id);
//
//    @Query(value = "SELECT ps.id\n" +
//            "FROM pet_servico ps\n" +
//            "INNER JOIN pets p ON ps.id_pet = p.id\n" +
//            "INNER JOIN servicos s ON  ps.id_servico = s.id\n" +
//            "WHERE s.id = :idServico AND p.id = :idPet", nativeQuery = true)
//    List<PetServico> findIdServicosRealizadosPorIdPetEIdServico(Long idServico, Long idPet);

    @Query("SELECT p FROM Pet p WHERE p.id = ?1")
    Optional<Pet> findPetByPet_Id(Long idPet);

    @Query("SELECT s FROM Servico s WHERE s.id = ?1")
    Optional<Servico> findServicoByServico_Id(Long idTutor);

    boolean existsByPetId_TutorId(Long idTutor);

    List<PetServico> findByPet_TutorId(Long idTutor);

    List<PetServico> findByPet_Id(Long id);

    boolean existsByPetId(Long idPet);

    @Query(value = "SELECT count(ps.id) > 0\n" +
            "FROM pet_servico ps\n" +
            "INNER JOIN pets p ON ps.id_pet = p.id\n" +
            "WHERE ps.id = :idServicoRealizado AND p.id = :idPet", nativeQuery = true)
    boolean hasServicoRealizadoByIdPet(Long idServicoRealizado, Long idPet);

    @Query(value = "SELECT  count(p.id) > 0\n" +
            "FROM pets p\n" +
            "INNER JOIN tutores t ON t.id = p.tutor_id\n" +
            "WHERE p.id = :idPet AND t.id = :idTutor", nativeQuery = true)
    boolean hasPetByIdTutor(Long idTutor, Long idPet);


    boolean existsServicoByServico_Id(Long idServicoOfertado);


    @Query(value = "SELECT status\n" +
            "FROM servicos s\n" +
            "WHERE s.id = :idServicoOfertado", nativeQuery = true)
    boolean StatusServico(Long idServicoOfertado);

//    boolean existsTutorByTutor_Id(Long idTutor);

}
