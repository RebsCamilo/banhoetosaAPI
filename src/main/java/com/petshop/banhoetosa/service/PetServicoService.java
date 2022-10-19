package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetServicoDto;
import com.petshop.banhoetosa.controller.dto.PetServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroPetServicoForm;
import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.ServicoRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PetServicoService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private PetServicoRepository petServicoRepository;

    public List<PetServico> listar() {
        return petServicoRepository.findAll();
    }

    @Transactional

    public PetServico cadastrar(PetServico petServico, Long idPet, Long idServico) {
        petServico.setStatusServico(StatusServicoEnum.AGUARDANDO);
        petServico.setStatusPagamento(StatusPagamentoEnum.EM_ABERTO);
        petServico.setPet(buscaPet(idPet));
        petServico.setServico(buscaServico(idServico));
        System.out.println(" 0000000000000001 " + petServico.getPet().getIdade());
        System.out.println(" 0000000000000002 " + petServico.getServico().getDataCadastro());
        System.out.println(" 0000000000000003 " + petServico.getServico().getDescricaoServico());
        return petServicoRepository.save(petServico);
    }

    public Optional<PetServico> detalhar(Long id) {
        return petServicoRepository.findById(id);
    }

    @Transactional
    public PetServico atualizar(Long id, PetServico petServicoAtt) {
        PetServico petServico = petServicoRepository.getReferenceById(id);

        petServico.setStatusServico(petServicoAtt.getStatusServico());
        petServico.setStatusPagamento(petServicoAtt.getStatusPagamento());

        return petServicoRepository.save(petServico);
    }

    @Transactional
    public void deletar(Long id) {
        PetServico petServico = petServicoRepository.getReferenceById(id);
        petServico.setServico(null);
        petServicoRepository.delete(petServico);
    }


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
