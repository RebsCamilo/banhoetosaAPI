package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetDto;
import com.petshop.banhoetosa.controller.dto.PetDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetForm;
import com.petshop.banhoetosa.controller.form.CadastroPetForm;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.repository.PetRepository;
import com.petshop.banhoetosa.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;



    public List<PetDto> listar(String nomePet) { //não é boa pratica devolver sua entidade no controller e sim um Dto
        if (nomePet == null) {
            return PetDto.converter(petRepository.findAll());
        } else {
            return PetDto.converter(petRepository.findByNome(nomePet));
        }
    }

    @Transactional
    public ResponseEntity<PetDto> cadastrar(@RequestBody @Valid CadastroPetForm form, UriComponentsBuilder uriBuilder) {  //@RequestBody indica ao Spring que os parâmetros enviados no corpo da requisição devem ser atribuídos ao parâmetro do método
        Pet pet = form.converter(tutorRepository);
        petRepository.save(pet);

        URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri(); //o ResponseEntity precisa o uri para ser criado. Este recebe a url da pagina a ser redirecionada
        return ResponseEntity.created(uri).body(new PetDto(pet)); //ResponseEntity para devolver o status 201 na Response
    }

    public ResponseEntity<DetalhesDoPetDto> detalhar(@PathVariable Long id) {
//	public void detalhar(@PathVariable("id") Long codigo) { se quisessemos usar codigo no lugar de id no corpo da função teriamos que associar o id da função com o do endereço da url dessa forma
        Optional<Pet> pet = petRepository.findById(id);
//		petServicoRepository.findByIdPet()
        if(pet.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoPetDto(pet.get())); //pega o pet que esta dentro do optional
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<PetDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetForm form) {
        Optional<Pet> optional = petRepository.findById(id);
        if(optional.isPresent()) {
            Pet pet = form.atualizar(id, petRepository);
            return ResponseEntity.ok(new PetDto(pet));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) { // <?> diz que tem generics mas nao sabe o tipo
        Optional<Pet> pet = petRepository.findById(id);
        if(pet.isPresent()) {
            petRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
