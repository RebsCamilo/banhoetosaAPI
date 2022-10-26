package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.mapper.PetServicoMapper;
import com.petshop.banhoetosa.controller.request.PetServicoRequest;
import com.petshop.banhoetosa.controller.request.PetServicoUpdateRequest;
import com.petshop.banhoetosa.controller.response.PetServicoResponse;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.service.PetServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/petservicos")
public class PetServicoController {

    private final PetServicoService petServicoService;
    private final PetServicoMapper petServicoMapper;

    @Autowired
    public PetServicoController(PetServicoService petServicoService, PetServicoMapper petServicoMapper) {
        this.petServicoService = petServicoService;
        this.petServicoMapper = petServicoMapper;
    }


    @GetMapping
    public ResponseEntity<List<PetServicoResponse>> listar() {
        List<PetServico> lista = petServicoService.listar();
        List<PetServicoResponse> listaResp = petServicoMapper.petServicoListToPetServicoResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid PetServicoRequest request) {
        if (!petServicoService.validarIdPet(request.getIdPet())) { //validar se id do pet existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id referente ao Pet não encontrado");
        }
        if (!petServicoService.validarIdServico(request.getIdServico())) { //validar se id do serviço existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id referente ao Serviço não encontrado");
        }
        if (!petServicoService.validarStatusServico(request.getIdServico())) { //validar se serviço esta ativo
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Serviço indisponível");
        }
        PetServico petServico = petServicoMapper.petServicoRequestToPetServico(request);
        petServicoService.cadastrar(petServico, request.getIdPet(), request.getIdServico());
        return ResponseEntity.status(HttpStatus.CREATED).body("Serviço relacionado ao pet cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id) {
        if (petServicoService.existeId(id)) {
            PetServico petServico = (petServicoService.detalhar(id)).get();
            PetServicoResponse petServicoDetalhe = petServicoMapper.petServicoToPetServicoResponse(petServico);
            return ResponseEntity.status(HttpStatus.OK).body(petServicoDetalhe);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid PetServicoUpdateRequest request) {
        if (petServicoService.existeId(id)) {
            PetServico petServicoAtt = petServicoMapper.petServicoUpdateRequestToPetServico(request);
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
