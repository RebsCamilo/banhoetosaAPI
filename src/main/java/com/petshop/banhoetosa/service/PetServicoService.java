package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetServicoService {

//    private final PetRepository petRepository;
//    private final ServicoRepository servicoRepository;
    private final PetServicoRepository petServicoRepository;

    @Autowired
    public PetServicoService(PetServicoRepository petServicoRepository) { //PetRepository petRepository, ServicoRepository servicoRepository) {
//        this.petRepository = petRepository;
//        this.servicoRepository = servicoRepository;
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

    public Pet buscaPet(Long idPet) {
        return petServicoRepository.findPetByPet_Id(idPet).get();
//        return petRepository.findById(idPet).get();
    }

    public Servico buscaServico(Long idServico) {
        return petServicoRepository.findServicoByServico_Id(idServico).get();
//        return servicoRepository.findById(idServico).get();
    }

    public List<PetServico> buscaServicosPeloPet(Long idPet) {
        return petServicoRepository.findByPet_Id(idPet);
    }

    public boolean existeEsteServicoRealizadoNestePet(Long idServicoRealizado, Long idPet) {
        return petServicoRepository.hasServicoRealizadoByIdPet(idServicoRealizado, idPet);
    }

    public boolean existeServicoRealizadoPeloPetDesteTutor(Long idTutor) {
        return petServicoRepository.existsByPetId_TutorId(idTutor);
    }

    public boolean existeEstePetNesteTutor(Long idTutor, Long idPet) {
        return petServicoRepository.hasPetByIdTutor(idTutor, idPet);
    }

    public boolean StatusServico(Long idServicoOfertado) {
        return petServicoRepository.StatusServico(idServicoOfertado);
    }


    public boolean existsServicoByServico_Id(Long idServicoOfertado) {
        return petServicoRepository.existsServicoByServico_Id(idServicoOfertado);
    }

    public boolean existeServicoRealizadoNestePet(Long idPet) {
        return petServicoRepository.existsByPetId(idPet);
    }

    public List<PetServico> buscaServicosPeloTutorId(Long idTutor) {
        return petServicoRepository.findByPet_TutorId(idTutor);
    }

}
