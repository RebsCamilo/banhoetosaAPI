package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNome(String nomePet);

    // se quisesse acessar o nome do tutor do pet. poderia fzr algo parecido com: //findByEntidade_AtributoDaEntidade
//    List<Pet> findByTutor_Nome(String nomeTutor);
    // caso queira mudar o nome da função, é preciso usar a anotação @Query e criar o JPQL manualmente igualando
    // com a variavel buscada e a declarando como parametro com o @Param na assinatura da funcao
//    @Query("SELECT p FROM Pet p WHERE p.tutor.nome = :nomeTutor")
//    List<Pet> BuscarPorNomeDoTutor(@Param("nomeTutor")String nomeTutor);

}






