package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.repository.ServicoRepository;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    

    public List<Servico> listarAtivos() {
        return servicoRepository.findByStatus(true); //"substituir por filtro" ?
    }

    public List<Servico> listar() {
        return servicoRepository.findAll(); //"substituir por filtro" ?
    }

    @Transactional
    public Servico cadastrar(Servico servico) {
        validarDescricao(servico.getId(), servico.getDescricaoServico());
        servico.cadastrar();
        return servicoRepository.save(servico);
    }

    public Servico detalhar(Long id) {
        servicoEstaAtivo(id);
        return servicoRepository.getReferenceById(id);
    }

    @Transactional
    public Servico atualizar(Long id, Servico servicoAtt) {
        Servico servico = detalhar(id);
        validarDescricao(id, servicoAtt.getDescricaoServico());
        servico.atualizar(servicoAtt);
        return servicoRepository.save(servico);
    }

    @Transactional
    public void desativar(Long id) {
        servicoEstaAtivo(id);
        servicoRepository.getReferenceById(id).desativar();
    }

    @Transactional
    public void ativar(Long id) {
        servicoEstaInativo(id);
        servicoRepository.getReferenceById(id).ativar();
    }
    
    
    public void servicoEstaAtivo(Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if (servico.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        if (!servico.get().getStatus()) {
            throw new DataIntegratyViolationException("Servico inativo");
        }
    }
    
    public void servicoEstaInativo(Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if (servico.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        if (servico.get().getStatus()) {
            throw new DataIntegratyViolationException("Servico ativo");
        }
    }

    public boolean existeDescricao(String descricaoServico) {
        return servicoRepository.existsByDescricaoServico(descricaoServico);
    }
    
    public void existePeloId(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
    }
    
    public void validarDescricao(Long id, String descricao) {
        Optional<Servico> servico = servicoRepository.findByDescricaoServico(descricao);
        if (servico.isPresent()) {
            if (!servico.get().getId().equals(id) || (!servico.get().getDescricaoServico().equals(descricao) && existeDescricao(descricao))) {
                throw new DataIntegratyViolationException("Servico já cadastrado");
            }
        }
    }
//
//    public boolean existeId(Long id) {
//        return servicoRepository.existsById(id);
//    }
//
//    public boolean status(Long id) {
//        return servicoRepository.getReferenceById(id).getStatus();
//    }
//
//    public boolean descricaoIgualIdDescricao(long id, String descricao) {
//        return servicoRepository.getReferenceById(id).getDescricaoServico().equals(descricao);
//    }


}


