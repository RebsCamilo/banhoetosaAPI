package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoServicoDto;
import com.petshop.banhoetosa.controller.dto.ServicoDto;
import com.petshop.banhoetosa.controller.form.CadastroServicoForm;
import com.petshop.banhoetosa.model.Servico;
import com.petshop.banhoetosa.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<ServicoDto> listar() {
        return ServicoDto.converter(servicoRepository.findAll());
    }

    public ResponseEntity<ServicoDto> cadastrar(@RequestBody @Valid CadastroServicoForm form, UriComponentsBuilder uriBuilder) {
        Servico servico = form.converter();
        servicoRepository.save(servico);

        URI uri = uriBuilder.path("/servicos/{id}").buildAndExpand(servico.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
        return ResponseEntity.created(uri).body(new ServicoDto(servico)); //devolve status 201 e redireciona para a pagina do objeto criado
    }

    public ResponseEntity<DetalhesDoServicoDto> detalhar(@PathVariable Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);
        if(servico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoServicoDto(servico.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
