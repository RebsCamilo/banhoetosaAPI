package com.petshop.banhoetosa.controller;

import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.model.mapper.ServicoMapper;
import com.petshop.banhoetosa.model.response.ServicoResponse;
import com.petshop.banhoetosa.service.PetService;
import com.petshop.banhoetosa.service.ServicoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicoControllerTest {
	
	@InjectMocks
	private ServicoController controller;
	
	@Mock
	private ServicoService service;
	
	@Mock
	private ServicoMapper mapper;
	
	private static final Long ID = 1L;
	private static final String DESCRICAO_SERVICO = "banho";
	private static final BigDecimal PRECO = new BigDecimal("30.0");
	private static final Boolean STATUS = true;
	private static final List<PetServico> PET_SERVICOS = new ArrayList<>();
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar Lista de Serviços ofertados ativos")
	void deveRetornarListaDeServicosOfertadosAtivos() {
//		//given
//		Servico servico = Servico.builder()
//		                         .id(ID)
//		                         .descricaoServico(DESCRICAO_SERVICO)
//		                         .preco(PRECO)
//		                         .status(STATUS)
//		                         .petServicos(PET_SERVICOS)
//		                         .build();
//
//		ServicoResponse servicoResponse = ServicoResponse.builder()
//		                         .id(ID)
//		                         .descricaoServico(DESCRICAO_SERVICO)
//		                         .preco(PRECO)
//		                         .build();
//
//		//when
////		List<Servico> lista = new ArrayList<>();
////		lista.add(servico);
//		Mockito.when(service.listarAtivos()).thenReturn(List.of(servico));
//		Mockito.when(mapper.servicoListToServicoResponseList(List.of(servico))).thenReturn(List.of(servicoResponse));
//
//		ResponseEntity<List<ServicoResponse>> result = controller.listarAtivos();
//
//		Assertions.assertThat(result).isNotNull();
//		Assertions.assertThat(result.getBody()).isNotNull();
//		Assertions.assertThat(result.getClass()).isEqualTo(ResponseEntity.class);
//		Assertions.assertThat(result.getBody().getClass()).isEqualTo(ArrayList.class);
//		Assertions.assertThat(result.getBody().get(INDEX).getClass()).isEqualTo(ServicoResponse.class);
	}
	
	@Test
	void listar() {
	}
	
	@Test
	void cadastrar() {
	}
	
	@Test
	void detalhar() {
	}
	
	@Test
	void atualizar() {
	}
	
	@Test
	void desativar() {
	}
	
	@Test
	void ativar() {
	}
}

//PetControllerTest
//    @Test
//    public void deveriaCarregarListaDePetResponse() throws Exception {
//        URI uri = new URI("tutores/pets");
//        mockMvc
//            .perform(MockMvcRequestBuilders
//                .get(uri))
//            .andExpect(MockMvcResultMatchers
//                    .status()
//                    .is(200));
//    }


//PetRepositoryTest
//    @Autowired
//    private PetRepository repository; //é para usar autowired ou contrutor?
//
//    @Test
//    public void deveriaCarregarPetsPeloSeuNome() {
//        String nome = "miina";
//        List<Pet> listaPets = repository.findByNome(nome);
//        Assert.assertNotNull(nome);
//        String nomeEsperado = listaPets.stream()
//                .map(Pet::getNome)
//                .filter(n -> n.equals(nome))
//                .findFirst().get();
//        Assert.assertEquals(nome, nomeEsperado);
//    }
//
//    @Test
//    public void NaoDeveriaCarregarPetsPeloSeuNome() {
//        String nome = "nomeNaoExiste";
//        List<Pet> listaPets = repository.findByNome(nome);
//        Assert.assertEquals(new ArrayList<>(), listaPets); //compara com ArrayList vazio
//    }