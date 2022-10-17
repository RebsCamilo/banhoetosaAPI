package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
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



    public List<PetDto> listar(String nomePet) { //não é boa pratica devolver sua entidade no controller e sim um Dto
        if (nomePet == null) {
            return PetDto.converter(petRepository.findAll());
        } else {
            return PetDto.converter(petRepository.findByNome(nomePet));
        }
    }

    @Transactional //para comitar as alterações no banco de dados
    public Pet cadastrar(Pet pet) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
        return petRepository.save(pet);

//        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
//        return ResponseEntity.created(uri).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response
    }

    public ResponseEntity<DetalhesDoPetDto> detalhar(Long id) {
//	public void detalhar(@PathVariable("id") Long codigo) { se quisessemos usar codigo no lugar de id no corpo da função teriamos que associar o id da função com o do endereço da url dessa forma
        Optional<Pet> pet = petRepository.findById(id);
//		petServicoRepository.findByIdPet()
        if(pet.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoPetDto(pet.get())); //pega o pet que esta dentro do optional
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional //para comitar as alterações no banco de dados
    public ResponseEntity<PetDto> atualizar(Long id, AtualizacaoPetForm form) {
        Optional<Pet> optional = petRepository.findById(id);
        if(optional.isPresent()) {
            Pet pet = form.atualizar(id, petRepository);
            return ResponseEntity.ok(new PetDto(pet));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> deletar(Long id) { // <?> diz que tem generics mas nao sabe o tipo
        Optional<Pet> pet = petRepository.findById(id);
        if(pet.isPresent()) {
//            pet.get().setTutor(null);
//            pet.get().getPetServicos().forEach(petServico -> petServico.setServico(null));
//            pet.get().setPetServicos(null);
            petRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
