package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.mapper.TutorMapper;
import com.petshop.banhoetosa.model.request.TutorRequest;
import com.petshop.banhoetosa.model.response.TutorDetalhesResponse;
import com.petshop.banhoetosa.model.response.TutorResponse;
import com.petshop.banhoetosa.model.domain.Endereco;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.service.TutorService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/tutores")
@Tag(name = "Tutores", description = "tudo sobre os tutores")
public class TutorController {
    
    public static final String ID = "/{id}";
    private final TutorService tutorService;
    private final TutorMapper tutorMapper;

    @Autowired
    public TutorController(TutorService tutorService, TutorMapper tutorMapper) {
        this.tutorService = tutorService;
        this.tutorMapper = tutorMapper;
    }


    @Operation(summary = "Busca todos os tutores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<List<TutorResponse>> listar() {
        List<Tutor> lista = tutorService.listar();
        List<TutorResponse> listaResp = tutorMapper.tutorListToTutorResponseList(lista);
        return ResponseEntity.status(HttpStatus.OK).body(listaResp);
    }

    @Operation(summary = "Cadastra um tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
//            @ApiResponse(responseCode = "409", description = "Tutor já cadastrado")
    })
    @PostMapping(consumes="application/json")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid TutorRequest request) {
        Tutor tutor = tutorMapper.tutorRequestToTutor(request);
        Endereco endereco = tutorMapper.tutorRequestToEndereco(request);
        tutorService.cadastrar(tutor, endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tutor cadastrado com sucesso");
    }

    @Operation(summary = "Busca o tutor pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping(value= ID)
    public ResponseEntity<TutorDetalhesResponse> detalhar(@PathVariable Long id) {
        Tutor tutor = (tutorService.detalhar(id));
        Endereco endereco = tutor.getEndereco();
        List<String> listaPets = tutor.getPets().stream().map(Pet::getNome).collect(Collectors.toList());
        TutorDetalhesResponse tutorDetalhe = tutorMapper.tutorEnderecoPetToTutorDetalhesResponse(tutor, endereco, listaPets);
        return ResponseEntity.status(HttpStatus.OK).body(tutorDetalhe);
    }

    @Operation(summary = "Atualiza o tutor pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastrado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @PutMapping(value= ID)
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid TutorRequest request) {
//        if(tutorService.existeId(id)) {
            Tutor tutor = tutorMapper.tutorRequestToTutor(request);
            Endereco endereco = tutorMapper.tutorRequestToEndereco(request);
            tutorService.atualizar(id, tutor, endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro do tutor atualizado com sucesso");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
    }

    @Operation(summary = "Deleta o tutor pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Id fornecido pode ser inválido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @DeleteMapping(ID)
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tutor excluído com sucesso");
//        if(tutorService.existeId(id)) {
//            tutorService.deletar(id);
//            return ResponseEntity.status(HttpStatus.OK).body("Tutor excluído com sucesso");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
    }
}
