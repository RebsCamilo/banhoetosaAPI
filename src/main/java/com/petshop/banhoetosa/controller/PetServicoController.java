package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.mapper.PetServicoMapper;
import com.petshop.banhoetosa.model.request.PetServicoRequest;
import com.petshop.banhoetosa.model.request.PetServicoUpdateRequest;
import com.petshop.banhoetosa.model.response.PetServicoResponse;
import com.petshop.banhoetosa.model.domain.PetServico;
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
//@RequestMapping(value="/servicos")
@Tag(name = "Serviços realizados", description = "tudo sobre os serviços que já foram realizados")
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
    @GetMapping(value ="tutores/pets/servicos", produces="application/json")
    public ResponseEntity<List<PetServicoResponse>> listarTodos() {
        List<PetServico> lista = petServicoService.listarTodos();
        List<PetServicoResponse> listaResp = petServicoMapper.petServicoListToPetServicoResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @Operation(summary = "Busca serviços vinculados a pets de tutor especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrados com sucesso"), //, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))})
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "409", description = "Tutor não encontrado")
    })
    @GetMapping(value = "tutores/{idTutor}/pets/servicos", produces="application/json")
    public ResponseEntity<Object> listarServicosDosPetsDoTutor(@PathVariable Long idTutor) {
//        if (!petServicoService.existeTutorPeloId(idTutor)) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Id do tutor inválido");
//        }
        if (!petServicoService.existeServicoRealizadoPeloPetDesteTutor(idTutor)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ainda não foi realizado nenhum serviço relacionado aos pets deste tutor");
        }
        List<PetServico> lista = petServicoService.buscaServicosPeloTutorId(idTutor);
        List<PetServicoResponse> listaResp = petServicoMapper.petServicoListToPetServicoResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @Operation(summary = "Busca serviços vinculados a pet especifico de tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrados com sucesso"), //, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))})
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "409", description = "Tutor não encontrado")
    })
    @GetMapping(value = "tutores/{idTutor}/pets/{idPet}/servicos", produces="application/json")
    public ResponseEntity<Object> listarServicosDoPet(@PathVariable Long idTutor, @PathVariable Long idPet) {
        if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
        }
        if (!petServicoService.existeServicoRealizadoNestePet(idPet)) { //validar se id do serviço existe
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum serviço realizado por este pet");
        }
        List<PetServico> lista = petServicoService.buscaServicosPeloPet(idPet);
        List<PetServicoResponse> listaResp = petServicoMapper.petServicoListToPetServicoResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @Operation(summary = "Cadastra um serviço a ser realizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "409", description = "Serviço indisponível ou Id referente ao Pet ou Serviço não encontrado")
    })
    @PostMapping(value = "tutores/{idTutor}/pets/{idPet}/servicos", consumes="application/json")
    public ResponseEntity<Object> cadastrar(@PathVariable Long idTutor, @PathVariable Long idPet, @RequestBody @Valid PetServicoRequest request) {
        if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
        }
        if (!petServicoService.existsServicoByServico_Id(request.getIdServico()) || !petServicoService.StatusServico(request.getIdServico())) { //validar se id do serviço existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Serviço não encontrado");
        }
        PetServico petServico = petServicoMapper.petServicoRequestToPetServico(request);
        petServicoService.cadastrar(petServico, idPet, request.getIdServico());
        return ResponseEntity.status(HttpStatus.CREATED).body("Serviço relacionado ao pet cadastrado com sucesso");
    }

    @Operation(summary = "Busca o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping(value = "tutores/{idTutor}/pets/{idPet}/servicos/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long idTutor, @PathVariable Long idPet, @PathVariable Long id) {
        if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
        }
        if(!petServicoService.existeEsteServicoRealizadoNestePet(id, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço realizado relacionado ao pet não encontrado");
        }
        PetServico petServico = (petServicoService.detalhar(id)).get();
        PetServicoResponse petServicoDetalhe = petServicoMapper.petServicoToPetServicoResponse(petServico);
        return ResponseEntity.status(HttpStatus.OK).body(petServicoDetalhe);
    }

    @Operation(summary = "Atualiza o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @PutMapping(value="tutores/{idTutor}/pets/{idPet}/servicos/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long idTutor, @PathVariable Long idPet, @PathVariable Long id, @RequestBody @Valid PetServicoUpdateRequest request) {
        if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
        }
        if(!petServicoService.existeEsteServicoRealizadoNestePet(id, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço realizado relacionado ao pet não encontrado");
        }
        PetServico petServicoAtt = petServicoMapper.petServicoUpdateRequestToPetServico(request);
        petServicoService.atualizar(id, petServicoAtt);
        return ResponseEntity.status(HttpStatus.OK).body("Serviço relacionado ao pet atualizado com sucesso");
    }

    @Operation(summary = "Deleta o serviço realizado pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @DeleteMapping(value="tutores/{idTutor}/pets/{idPet}/servicos/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long idTutor, @PathVariable Long idPet, @PathVariable Long id) {
        if(!petServicoService.existeEstePetNesteTutor(idTutor, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do pet ou tutor inválido");
        }
        if(!petServicoService.existeEsteServicoRealizadoNestePet(id, idPet)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço realizado relacionado ao pet não encontrado");
        }
        petServicoService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Serviço realizado excluído com sucesso");
    }

}
