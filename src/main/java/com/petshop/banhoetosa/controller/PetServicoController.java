package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.controller.mapper.PetServicoMapper;
import com.petshop.banhoetosa.controller.request.PetServicoRequest;
import com.petshop.banhoetosa.controller.request.PetServicoUpdateRequest;
import com.petshop.banhoetosa.controller.response.PetServicoResponse;
import com.petshop.banhoetosa.model.PetServico;
import com.petshop.banhoetosa.service.PetServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/servicos-ja-realizados")
@Tag(name = "Serviços já realizados", description = "tudo sobre os serviços que já foram realizados")
public class PetServicoController {

    private final PetServicoService petServicoService;
    private final PetServicoMapper petServicoMapper;

    @Autowired
    public PetServicoController(PetServicoService petServicoService, PetServicoMapper petServicoMapper) {
        this.petServicoService = petServicoService;
        this.petServicoMapper = petServicoMapper;
    }


    @Operation(summary = "Busca todos serviços já realizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<List<PetServicoResponse>> listar() {
        List<PetServico> lista = petServicoService.listar();
        List<PetServicoResponse> listaResp = petServicoMapper.petServicoListToPetServicoResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @Operation(summary = "Cadastra um serviço a ser realizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "409", description = "Serviço indisponível ou Id referente ao Pet ou Serviço não encontrado")
    })
    @PostMapping(consumes="application/json")
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

    @Operation(summary = "Busca o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping(value="/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id) {
        if (petServicoService.existeId(id)) {
            PetServico petServico = (petServicoService.detalhar(id)).get();
            PetServicoResponse petServicoDetalhe = petServicoMapper.petServicoToPetServicoResponse(petServico);
            return ResponseEntity.status(HttpStatus.OK).body(petServicoDetalhe);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

    @Operation(summary = "Atualiza o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @PutMapping(value="/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid PetServicoUpdateRequest request) {
        if (petServicoService.existeId(id)) {
            PetServico petServicoAtt = petServicoMapper.petServicoUpdateRequestToPetServico(request);
            petServicoService.atualizar(id, petServicoAtt);
            return ResponseEntity.status(HttpStatus.OK).body("Serviço relacionado ao pet atualizado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

    @Operation(summary = "Deleta o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (petServicoService.existeId(id)) {
            petServicoService.deletar(id);
            return ResponseEntity.status(HttpStatus.OK).body("Serviço relacionado ao pet excluído com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço relacionado ao pet não encontrado");
    }

}
