package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByStatus(Boolean status);

    boolean existsByDescricaoServico(String descricaoServico);
    
    Optional<Servico> findByDescricaoServico(String descricao);
}

