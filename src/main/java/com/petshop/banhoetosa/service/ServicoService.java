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


    public List<ServicoDto> listar() {
        return ServicoDto.converter(servicoRepository.findByStatus(true));
    }

    public List<ServicoDto> listarTodos() {
        return ServicoDto.converter(servicoRepository.findAll());
    }

    @Transactional
    public ResponseEntity<ServicoDto> cadastrar(CadastroServicoForm form, UriComponentsBuilder uriBuilder) {
        Servico servico = form.converter();
        servicoRepository.save(servico);

        URI uri = uriBuilder.path("/servico/{id}").buildAndExpand(servico.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
        return ResponseEntity.created(uri).body(new ServicoDto(servico)); //devolve status 201 e redireciona para a pagina do objeto criado
    }

    public ResponseEntity<DetalhesDoServicoDto> detalhar(Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if(servico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoServicoDto(servico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<ServicoDto> atualizar(Long id, AtualizacaoServicoForm form) {
        Optional<Servico> optional = servicoRepository.findById(id);
        if (optional.isPresent()) {
            Servico servico = form.atualizar(id, servicoRepository);
            return ResponseEntity.ok(new ServicoDto(servico));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> deletar(Long id) {
        Optional<Servico> optional = servicoRepository.findById(id);
        if (optional.isPresent()) {
//            servicoRepository.delete(optional.get());
            optional.get().setStatus(false);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}


