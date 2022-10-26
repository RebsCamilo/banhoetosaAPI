package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetServicoService {

    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;
    private final PetServicoRepository petServicoRepository;

    @Autowired
    public PetServicoService(PetRepository petRepository, ServicoRepository servicoRepository, PetServicoRepository petServicoRepository) {
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
        this.petServicoRepository = petServicoRepository;
    }


    public List<PetServico> listar() {
        return petServicoRepository.findAll();
    }

    @Transactional

    public PetServico cadastrar(PetServico petServico, Long idPet, Long idServico) {
        Pet pet = buscaPet(idPet);
        Servico servico = buscaServico(idServico);
        petServico.cadastrar(pet, servico);
        return petServicoRepository.save(petServico);
    }

    public Optional<PetServico> detalhar(Long id) {
        return petServicoRepository.findById(id);
    }

    @Transactional
    public PetServico atualizar(Long id, PetServico petServicoAtt) {
        PetServico petServico = petServicoRepository.getReferenceById(id);
        petServico.atualizar(petServicoAtt);
        return petServicoRepository.save(petServico);
    }

    @Transactional
    public void deletar(Long id) {
        PetServico petServico = petServicoRepository.getReferenceById(id);
        petServico.setServico(null);
        petServicoRepository.delete(petServico);
    }

    //perguntar a florian se estes metodos deveriam estar em outra classe
    public boolean existeId(Long id) {
        return petServicoRepository.existsById(id);
    }

    public boolean validarIdPet(Long id) {
        return petRepository.existsById(id);
    }

    public boolean validarIdServico(Long id) {
        return servicoRepository.existsById(id);
    }

    public boolean validarStatusServico(Long id) {
        return servicoRepository.getReferenceById(id).getStatus();
    }

    public Pet buscaPet(Long id) {
        return petRepository.findById(id).get();
    }
    public Servico buscaServico(Long id) {
        return servicoRepository.findById(id).get();
    }



}
