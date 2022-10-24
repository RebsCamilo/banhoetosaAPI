package com.petshop.banhoetosa.repository;

import com.petshop.banhoetosa.model.Servico;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByStatus(Boolean status);

    boolean existsByDescricaoServico(String descricaoServico);


}

