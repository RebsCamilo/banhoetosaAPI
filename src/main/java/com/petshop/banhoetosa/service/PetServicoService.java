package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServicoService {

    private final PetService petService;
    private final ServicoService servicoService;
    private final TutorService tutorService;
    private final PetServicoRepository petServicoRepository;

    
    public List<PetServico> listar() {
        return petServicoRepository.findAll();
    }

    @Transactional
    public PetServico cadastrar(PetServico petServico, Long idTutor, Long idPet, Long idServico) {
        validarPetServico(idTutor, idPet, idServico);
        Pet pet = buscaPet(idPet);
        Servico servico = buscaServico(idServico);
        petServico.cadastrar(pet, servico);
        return petServicoRepository.save(petServico);
    }

    public PetServico detalhar(Long id, Long idTutor, Long idPet) {
        validarPetServicoParaAtualizar(id, idTutor, idPet);
        Optional<PetServico> petServico = petServicoRepository.findById(id);
        return petServico.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    public PetServico atualizar(Long id, Long idTutor, Long idPet, PetServico petServicoAtt) {
        PetServico petServico = detalhar(id, idTutor, idPet);
//        validarPetServicoParaAtualizar(id, idTutor, idPet);
        petServico.atualizar(petServicoAtt);
        return petServicoRepository.save(petServico);
    }

    @Transactional
    public void deletar(Long id, Long idTutor, Long idPet) {
        detalhar(id, idTutor, idPet);
        petServicoRepository.deleteById(id);
//        PetServico petServico = petServicoRepository.getReferenceById(id);
//        petServico.setServico(null);
//        petServicoRepository.delete(petServico);
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

    public List<PetServico> buscaServicosPeloPetId(Long idTutor, Long idPet) {
        petService.existePeloIdPetEIdTutor(idPet, idTutor);
//        if (!existeServicoRealizadoNestePet(idPet)) {
//            throw new ObjectNotFoundException("Objeto não encontrado");
//        }
        return petServicoRepository.findByPet_Id(idPet);
    }
    ////////////////////////////////////
//            if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
//    }
//        if (!petServicoService.existeServicoRealizadoNestePet(idPet)) { //validar se id do serviço existe
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum serviço realizado por este pet");
//    }

    public boolean existeEsteServicoRealizadoNestePet(Long idServicoRealizado, Long idPet) {
        return petServicoRepository.hasServicoRealizadoByIdPet(idServicoRealizado, idPet);
    }

    public boolean existeServicoRealizadoPeloPetDesteTutor(Long idTutor) {
        return petServicoRepository.existsByPetId_TutorId(idTutor);
    }

    public boolean existeEstePetNesteTutor(Long idTutor, Long idPet) {
        return petServicoRepository.hasPetByIdTutor(idTutor, idPet);
    }

//    public boolean StatusServico(Long idServicoOfertado) {
//        return petServicoRepository.StatusServico(idServicoOfertado);
//    }
//
//
//    public boolean existsServicoByServico_Id(Long idServicoOfertado) {
//        return petServicoRepository.existsServicoByServico_Id(idServicoOfertado);
//    }

    public boolean existeServicoRealizadoNestePet(Long idPet) {
        return petServicoRepository.existsByPetId(idPet);
    }

    public List<PetServico> buscaServicosPeloTutorId(Long idTutor) {
        if (!tutorService.existeTutorPeloId(idTutor)) {// || !petServicoService.existeServicoRealizadoPeloPetDesteTutor(idTutor)) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        return petServicoRepository.findByPet_TutorId(idTutor);
    }
    
    public void validarPetServico(Long idTutor, Long idPet, Long idServico) {
        petService.existePeloIdPetEIdTutor(idPet, idTutor);
        servicoService.existePeloId(idServico);
        servicoService.servicoEstaAtivo(idServico);
    }
    
    public void validarPetServicoParaAtualizar(Long id, Long idTutor, Long idPet) {
        Optional<PetServico> petServico = petServicoRepository.findById(id);
        if (petServico.isEmpty()) { // && !id.equals(idAtt)) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        if (!petServico.get().getPet().getTutor().getId().equals(idTutor) || !petServico.get().getPet().getId().equals(idPet)) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        validarPetServico(idTutor, idPet, petServico.get().getServico().getId());
    }
}
