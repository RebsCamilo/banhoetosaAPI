package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.dto.DetalhesDoPetServicoDto;
import com.petshop.banhoetosa.controller.dto.PetServicoDto;
import com.petshop.banhoetosa.controller.form.AtualizacaoPetServicoForm;
import com.petshop.banhoetosa.controller.form.CadastroPetServicoForm;
import com.petshop.banhoetosa.model.Pet;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.service.PetServicoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/petservicos")
public class PetServicoController {

    @Autowired
    private PetServicoService petServicoService;

    @GetMapping
    public ResponseEntity<List<PetServicoDto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(PetServicoDto.converter(petServicoService.listar()));
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid CadastroPetServicoForm form) {
        if (!petServicoService.validarIdPet(form.getIdPet())) { //validar se id do pet existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id referente ao Pet não encontrado");
        }
        if (!petServicoService.validarIdServico(form.getIdServico())) { //validar se id do serviço existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id referente ao Serviço não encontrado");
        }
        if (!petServicoService.validarStatusServico(form.getIdServico())) { //validar se serviço esta ativo
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Serviço indisponível");
        }
        PetServico petServico = form.converter();
        petServicoService.cadastrar(petServico, form.getIdPet(), form.getIdServico());
        return ResponseEntity.status(HttpStatus.CREATED).body("Serviço relacionado ao pet cadastrado com sucesso");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Object> detalhar(@PathVariable Long id) {
//        if (petServicoService.existeId(id)) {
//            PetServico petServico = (petServicoService.detalhar(id)).get();
//            return ResponseEntity.status(HttpStatus.OK).body(new DetalhesDoPetServicoDto(petServico));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
////        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPetServicoForm form) {
        if (petServicoService.existeId(id)) {
            PetServico petServicoAtt = form.converter();
            petServicoService.atualizar(id, petServicoAtt);
            return ResponseEntity.status(HttpStatus.OK).body("Serviço relacionado ao pet atualizado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (petServicoService.existeId(id)) {
            petServicoService.deletar(id);
            return ResponseEntity.status(HttpStatus.OK).body("Serviço relacionado ao pet excluído com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

}
