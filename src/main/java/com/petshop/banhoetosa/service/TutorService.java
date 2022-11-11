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


    public List<Tutor> listar() {
        return tutorRepository.findAll();
    }

    @Transactional
    public Tutor cadastrar(Tutor tutor, Endereco endereco) {
        validarEmail(tutor.getId(), tutor.getEmail());
        tutor.cadastrar(endereco.cadastrar());
        return tutorRepository.save(tutor);
    }

    public Tutor detalhar(Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        return tutor.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    public Tutor atualizar(Long id, Tutor tutorAtt, Endereco enderecoAtt) {
        Tutor tutor = detalhar(id);
        validarEmail(id, tutorAtt.getEmail());
        tutor.atualizar(tutorAtt, enderecoAtt);
        return tutorRepository.save(tutor);
    }
    
    @Transactional
    public void deletar(Long id) {
        detalhar(id);
        tutorRepository.deleteById(id);
    }

    public void validarEmail(Long id, String email) {
        Optional<Tutor> tutor = tutorRepository.findByEmail(email);
        if (tutor.isPresent() && !tutor.get().getId().equals(id) ) { // e se o id do tutor buscado pelo email for igual ao passado na url
            throw new DataIntegratyViolationException("E-mail já cadastrado");
        }
    }

    public boolean existeId(Long id) {
        return tutorRepository.existsById(id);
    }
    
    public Tutor buscaTutor(Long id) {
        Optional<Tutor> tutor = tutorRepository.findById(id);
        try {
            return tutor.get();
        } catch (Exception ex) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
    }
    

}
