package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoServicoDto;
import com.petshop.banhoetosa.controller.dto.ServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroServicoForm;
import com.petshop.banhoetosa.model.Servico;
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
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;


    public List<Servico> listarAtivos() {
        return servicoRepository.findByStatus(true); //"substituir por filtro"
    }

    public List<Servico> listar() {
        return servicoRepository.findAll(); //"substituir por filtro"
    }

    @Transactional
    public Servico cadastrar(Servico servico) {
        servico.setStatus(true);
        return servicoRepository.save(servico);
    }

    public Servico detalhar(Long id) {
        return servicoRepository.getReferenceById(id);
    }

    @Transactional
    public Servico atualizar(Long id, Servico servicoAtt) {
        Servico servico = servicoRepository.getReferenceById(id);

        servico.setDescricaoServico(servicoAtt.getDescricaoServico());
        servico.setPreco(servicoAtt.getPreco());

        return servicoRepository.save(servico);
    }

    @Transactional
    public void desativar(Long id) {
        servicoRepository.getReferenceById(id).setStatus(false);
    }

    @Transactional
    public void ativar(Long id) {
        servicoRepository.getReferenceById(id).setStatus(true);
    }

//    @Transactional
//    public ResponseEntity<?> ativar(Long id) {
//        Optional<Servico> optional = servicoRepository.findById(id);
//        if (optional.isPresent()) {
//            optional.get().setStatus(true);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    public boolean existeDescricao(String descricaoServico) {
        return servicoRepository.existsByDescricaoServico(descricaoServico);
    }

    public boolean existeId(Long id) {
        return servicoRepository.existsById(id);
    }

    public boolean status(Long id) {
        return servicoRepository.getReferenceById(id).getStatus();
    }


}


