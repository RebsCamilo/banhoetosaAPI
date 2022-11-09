package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Endereco;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.TutorRepository;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }


    public List<Tutor> listar() { //(Pageable paginacao) {
        return tutorRepository.findAll(); //(paginacao);
    }

    @Transactional
    public Tutor cadastrar(Tutor tutor, Endereco endereco) {
        validarEmail(tutor.getEmail());
        tutor.cadastrar(endereco);
        return tutorRepository.save(tutor);
    }

    public Tutor detalhar(Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        return tutor.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    public Tutor atualizar(Long id, Tutor tutorAtt, Endereco enderecoAtt) {
            Tutor tutor = tutorRepository.getReferenceById(id);
            tutor.atualizar(tutorAtt, enderecoAtt);
            return tutorRepository.save(tutor);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = tutorRepository.getReferenceById(id);
        tutorRepository.delete(tutor);
    }
    
    
    public void validarEmail(String email) {
        if (tutorRepository.existsByEmail(email)) {
            throw new DataIntegratyViolationException("E-mail já cadastrado");
        }
    }
//    public boolean validarEmail(String email) {
//        return tutorRepository.existsByEmail(email);
//    }

    public boolean existeId(Long id) {
        return tutorRepository.existsById(id);
    }

//    public Optional<Tutor> getTutorById(Long id) {
//        return tutorRepository.findById(id);
//    }



}
