package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.Endereco;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.EnderecoRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TutorService {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;


    public List<Tutor> listar() {
        return tutorRepository.findAll();
    }

    @Transactional
    public Tutor cadastrar(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public Optional<Tutor> detalhar(Long id) {
        return tutorRepository.findById(id);
    }

    @Transactional
    public Tutor atualizar(Long id, Tutor tutorAtt) {
            Tutor tutor = tutorRepository.getReferenceById(id);
            tutor.setNome(tutorAtt.getNome());
            tutor.setTelefone1(tutorAtt.getTelefone1());
            tutor.setTelefone2(tutorAtt.getTelefone2());
            tutor.setEmail(tutorAtt.getEmail());

            Endereco endereco = tutor.getEndereco();
            endereco.setRua(tutorAtt.getEndereco().getRua()); //-----------
            endereco.setNumero(tutorAtt.getEndereco().getNumero());
            endereco.setBairro(tutorAtt.getEndereco().getBairro());
            endereco.setComplemento(tutorAtt.getEndereco().getComplemento());
            endereco.setCep(tutorAtt.getEndereco().getCep());
//            enderecoRepository.save(endereco);

            return tutorRepository.save(tutor);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = tutorRepository.getReferenceById(id);
        tutorRepository.delete(tutor);
    }



    public boolean validarEmail(String email) {
        return tutorRepository.existsByEmail(email);
    }

    public boolean existeId(Long id) {
        return tutorRepository.existsById(id);
    }
}


