package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    @Autowired
    ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }


    public List<Servico> listarAtivos() {
        return servicoRepository.findByStatus(true); //"substituir por filtro"
    }

    public List<Servico> listar() {
        return servicoRepository.findAll(); //"substituir por filtro"
    }

    @Transactional
    public Servico cadastrar(Servico servico) {
        servico.ativar();
        return servicoRepository.save(servico);
    }

    public Servico detalhar(Long id) {
        return servicoRepository.getReferenceById(id);
    }

    @Transactional
    public Servico atualizar(Long id, Servico servicoAtt) {
        Servico servico = servicoRepository.getReferenceById(id);
        servico.atualizar(servicoAtt);
        return servicoRepository.save(servico);
    }

    @Transactional
    public void desativar(Long id) {
        servicoRepository.getReferenceById(id).desativar();
    }

    @Transactional
    public void ativar(Long id) {
        servicoRepository.getReferenceById(id).ativar();
    }


    public boolean existeDescricao(String descricaoServico) {
        return servicoRepository.existsByDescricaoServico(descricaoServico);
    }

    public boolean existeId(Long id) {
        return servicoRepository.existsById(id);
    }

    public boolean status(Long id) {
        return servicoRepository.getReferenceById(id).getStatus();
    }

    public boolean descricaoIgualIdDescricao(long id, String descricao) {
        return servicoRepository.getReferenceById(id).getDescricaoServico().equals(descricao);
    }


}


