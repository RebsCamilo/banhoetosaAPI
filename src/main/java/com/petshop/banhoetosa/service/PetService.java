package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final TutorService tutorService; // usar o TutorService

    @Autowired
    public PetService(PetRepository petRepository, TutorService tutorService) {
        this.petRepository = petRepository;
        this.tutorService = tutorService;
    }

//    public List<Pet> listar(String nomePet) { //não é boa pratica devolver sua entidade no controller e sim um Dto (fazer isso na camada controller)
//        if (nomePet == null) {
//            return petRepository.findAll();
//        } else {
//            return petRepository.findByNome(nomePet);
//        }
//    }

    public List<Pet> listar() {
        return petRepository.findAll();
    }

    public List<Pet> listarPetsDoTutor(Long idTutor) {
        Tutor tutor = tutorService.buscaTutor(idTutor);
        return petRepository.findByTutor(tutor);
    }

    @Transactional //para comitar as alterações no banco de dados
    public Pet cadastrar(Pet pet, Long idTutor) {
        ////////////////////////
        validarNomePet(pet.getId(), pet.getNome(), idTutor);
        Tutor tutor = tutorService.buscaTutor(idTutor);
        pet.cadastrar(tutor);
        return petRepository.save(pet);
//        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
//        return ResponseEntity.created(uri).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response
    }

    public Pet detalhar(Long id, Long idTutor) { //	função detalhar(@PathVariable("id") Long codigo) -> se quisessemos usar codigo no lugar de id no corpo da função teriamos que associar o id da função com o do endereço da url
        existePeloIdPetEIdTutor(id, idTutor);
        return petRepository.getReferenceById(id);
    }

    @Transactional //para comitar as alterações no banco de dados
    public Pet atualizar(Long id, Pet petAtualizacao, Long idTutor) {
        Pet pet = detalhar(id, idTutor);
        validarNomePet(id, pet.getNome(), idTutor);
        pet.atualizar(petAtualizacao);
        return petRepository.save(pet);
    }
   
    @Transactional
    public void deletar(Long id, Long idTutor) { // <?> diz que tem generics mas nao sabe o tipo
        detalhar(id, idTutor);
        petRepository.deleteById(id);
//        Pet pet = petRepository.getReferenceById(id);
////        pet.setPetServicos(null);
//        petRepository.delete(pet);
    }


    public boolean jaExisteNomePet(String nome, Long idTutor) {
        return !petRepository.jaExisteNomePetCadastradoNesteTutor(nome, idTutor);
    }

//    public Tutor getTutorDoPet(String email) {
//        return tutorRepository.getReferenceByEmail(email);
//    }

//    public boolean existeId(Long id) {
//        return petRepository.existsById(id);
//    }
//
//    public boolean existeEmailTutor(String email) {
//        return tutorRepository.existsByEmail(email);
//    }

//    public Tutor buscaTutor(String email) {
//        if (tutorRepository.findByEmail(email).isPresent()) {
//            return tutorRepository.findByEmail(email).get();
//        }
//        throw new ObjectNotFoundException("E-mail do tutor não encontrado");
//    }

//    public List<Pet> buscaPetPorIdTutor(Long tutorId) {
//        if (petRepository.findByTutorId(tutorId).isPresent()) {
//            return petRepository.findByTutorId(tutorId).get();
//        }
//        return null;
//    }
//
//    public Optional<Pet> buscaPetPeloId(Long id) {
//        return petRepository.findById(id);
//    }
//
//    public boolean existeTutorPeloId(Long idTutor) {
//        return petRepository.existsByTutor_Id(idTutor);
//    }
//
//    public String findTutorDoPetByTutorId(Long idTutor) {
//        return petRepository.findEmailTutorByTutorId(idTutor);
//    }

    public void existePeloIdPetEIdTutor(Long idPet, Long idTutor) {
        Optional<Pet> pet = petRepository.findByPetAndTutor(idPet, idTutor);
        if (pet.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
    }
    
    public void validarNomePet(Long id, String nome, Long idTutor) {
        Optional<Pet> pet = petRepository.findByNomeEIdTutor(nome, idTutor);
        if (pet.isPresent()) { //pet tem que existir E ter o id diferente do passado na url E possuir nome diferente os demais pets deste tutor (exceto se for o proprio nome)
            if (!pet.get().getId().equals(id) || (!pet.get().getNome().equals(nome) && jaExisteNomePet(nome, idTutor))) {
                throw new DataIntegratyViolationException("Pet já cadastrado");
            }
        }
    }

}
