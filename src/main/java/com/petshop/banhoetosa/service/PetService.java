package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.Tutor;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private PetServicoRepository petServicoRepository;



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

    @Transactional //para comitar as alterações no banco de dados
    public Pet cadastrar(Pet pet, String email) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
        pet.setTutor(buscaTutor(email));
        return petRepository.save(pet);

//        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
//        return ResponseEntity.created(uri).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response
    }

    public Optional<Pet> detalhar(Long id) { //	função detalhar(@PathVariable("id") Long codigo) -> se quisessemos usar codigo no lugar de id no corpo da função teriamos que associar o id da função com o do endereço da url
        return petRepository.findById(id);
    }

    @Transactional //para comitar as alterações no banco de dados
    public Pet atualizar(Long id, Pet petAtt, String email) {
        Pet pet = petRepository.getReferenceById(id);
            pet.setNome(petAtt.getNome());
            pet.setRaca(petAtt.getRaca());
            pet.setEspecie(petAtt.getEspecie());
            pet.setIdade(petAtt.getIdade());
            pet.setDetalhe(petAtt.getDetalhe());
            pet.setTutor(buscaTutor(email));

            return petRepository.save(pet);
        }


    @Transactional
    public void deletar(Long id) { // <?> diz que tem generics mas nao sabe o tipo
        Pet pet = petRepository.getReferenceById(id);
        petRepository.delete(pet);
    }


    public boolean validarPet(String nome, String email) {
        System.out.println("[service 1] " + existeEmailTutor(email));
//        if (!petRepository.existsByTutor_Email(email)) { //valida se o pet já esta cadastrado neste tutor e se o tutor existe
//            return true;
//        }
        if (existeEmailTutor(email)) { //valida se o tutor existe
            System.out.println("[service 2] ");
            System.out.println("[service 2] " + petRepository.hasThisPetNameByEmailDoTutor(nome, email));
            if (!petRepository.hasThisPetNameByEmailDoTutor(nome, email)) { //valida se o tutor ja tem pet com mesmo nome
                return true;
            }
        }
        System.out.println("FAAAAAAAAALSE");
        return false;
    }

//    public Tutor getTutorDoPet(String email) {
//        return tutorRepository.getReferenceByEmail(email);
//    }

    public boolean existeId(Long id) {
        return petRepository.existsById(id);
    }

    public boolean existeEmailTutor(String email) {
        return tutorRepository.existsByEmail(email);
    }

    public Tutor buscaTutor(String email) {
        return tutorRepository.findByEmail(email);
    }

}
