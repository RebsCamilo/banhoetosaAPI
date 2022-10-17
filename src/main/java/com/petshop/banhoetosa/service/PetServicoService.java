package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetServicoDto;
import com.petshop.banhoetosa.controller.dto.PetServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroPetServicoForm;
import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PetServicoRepository petServicoRepository;

    public List<PetServicoDto> listar() {
        return PetServicoDto.converter(petServicoRepository.findAll());
    }

    @Transactional

    public ResponseEntity<PetServicoDto> cadastrar(CadastroPetServicoForm form, UriComponentsBuilder uriBuilder) {
        PetServico petServico = form.converter(petRepository, servicoRepository);
        if (petServico.getServico().getStatus()) {
            petServico.setStatusServico(StatusServicoEnum.AGUARDANDO);
            petServico.setStatusPagamento(StatusPagamentoEnum.EM_ABERTO);
            petServicoRepository.save(petServico);

            URI uri = uriBuilder.path("/petservicos/{id}").buildAndExpand(petServico.getId()).toUri();
            return ResponseEntity.created(uri).body(new PetServicoDto(petServico));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<DetalhesDoPetServicoDto> detalhar(Long id) {
        Optional<PetServico> petServico = petServicoRepository.findById(id);
        if (petServico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoPetServicoDto(petServico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<PetServicoDto> atualizar(Long id, AtualizacaoPetServicoForm form) {
        Optional<PetServico> optional = petServicoRepository.findById(id);
        if (optional.isPresent()) {
            PetServico petServico = form.atualizar(id, petServicoRepository, petRepository, servicoRepository);
            petServicoRepository.save(petServico);

            return ResponseEntity.ok(new PetServicoDto(petServico));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> deletar(Long id) {
        Optional<PetServico> petServico = petServicoRepository.findById(id);
        if (petServico.isPresent()) {
            petServico.get().setServico(null);
            petServicoRepository.delete(petServico.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
