package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.repository.ServicoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServicoServiceTest {
	
	@InjectMocks
	private ServicoService service;
	
	@Mock
	private ServicoRepository repository;
	
	private static final Long ID = 1L;
	private static final String DESCRICAO_SERVICO = "banho";
	private static final BigDecimal PRECO = new BigDecimal("30.0");
	private static final Boolean STATUS = true;
	private static final List<PetServico> PET_SERVICOS = new ArrayList<>();
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar Lista de Serviços ofertados ativos")
	void deveRetornarListaDeServicosOfertadosAtivos() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(STATUS)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		
		//when
		Mockito.when(repository.findByStatus(true)).thenReturn(List.of(servico));
		List<Servico> result = service.listarAtivos();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Servico.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getDescricaoServico()).isEqualTo(DESCRICAO_SERVICO);
		Assertions.assertThat(result.get(INDEX).getPreco()).isEqualTo(PRECO);
		Assertions.assertThat(result.get(INDEX).getStatus()).isEqualTo(STATUS);
		Assertions.assertThat(result.get(INDEX).getPetServicos()).isEqualTo(PET_SERVICOS);
	}
	
	@Test
	@DisplayName("Deve retornar Lista de Serviços ofertados ativos vazia")
	void deveRetornarListaDeServicosOfertadosAtivosVazia() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(false)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of(servico));
		List<Servico> result = service.listarAtivos();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar Lista de Serviços ofertados")
	void deveRetornarListaDeServicosOfertados() {
		//given
		Servico servico = Servico.builder()
	                              .id(ID)
				                  .descricaoServico(DESCRICAO_SERVICO)
				                  .preco(PRECO)
				                  .status(STATUS)
				                  .petServicos(PET_SERVICOS)
	                              .build();
		
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of(servico));
		List<Servico> result = service.listar();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(Servico.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getDescricaoServico()).isEqualTo(DESCRICAO_SERVICO);
		Assertions.assertThat(result.get(INDEX).getPreco()).isEqualTo(PRECO);
		Assertions.assertThat(result.get(INDEX).getStatus()).isEqualTo(STATUS);
		Assertions.assertThat(result.get(INDEX).getPetServicos()).isEqualTo(PET_SERVICOS);
	}
	
	@Test
	@DisplayName("Deve retornar Lista Vazia")
	public void deveRetornarNull() {
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of());
		List<Servico> result = service.listar();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	
	@Test
	@DisplayName("Deve retornar o Servico cadastrado")
	void deveRetornarOServicoCadastrado() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(STATUS)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		//when
		Mockito.when(repository.save(servico)).thenReturn(servico);
		Servico result = service.cadastrar(servico);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Servico.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getDescricaoServico()).isEqualTo(DESCRICAO_SERVICO);
		Assertions.assertThat(result.getPreco()).isEqualTo(PRECO);
		Assertions.assertThat(result.getStatus()).isEqualTo(STATUS);
		Assertions.assertThat(result.getPetServicos()).isEqualTo(PET_SERVICOS);
	}
	
	@Test
	@DisplayName("Deve retornar um Servico Detalhado")
	void deveRetornarUmServicoDetalhado() {
		//given
		Servico servico = Servico.builder()
                                 .id(ID)
                                 .descricaoServico(DESCRICAO_SERVICO)
                                 .preco(PRECO)
                                 .status(STATUS)
                                 .petServicos(PET_SERVICOS)
                                 .build();
		
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(servico);
		Servico result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEqualTo(servico);
	}
	
	@Test
	@DisplayName("Deve retornar Null caso nao exista servico")
	public void deveRetornarNullCasoNaoExistaServico() {
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(null);
		//then
		Assertions.assertThat(service.detalhar(ID)).isNull();
	}
	
	
	@Test
	@DisplayName("Deve retornar Servico atualizado")
	void deveRetornarServicoAtualizado() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(STATUS)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		
		Servico servicoAtt = Servico.builder()
			                        .id(ID)
			                        .descricaoServico("Tosa")
			                        .preco(PRECO)
			                        .status(STATUS)
			                        .petServicos(PET_SERVICOS)
			                        .build();
		
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(servico);
		Mockito.when(repository.save(servico)).thenReturn(servico);
		Servico result = service.atualizar(ID, servicoAtt);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(Servico.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getDescricaoServico()).isEqualTo("Tosa");
		Assertions.assertThat(result.getPreco()).isEqualTo(PRECO);
		Assertions.assertThat(result.getStatus()).isEqualTo(STATUS);
		Assertions.assertThat(result.getPetServicos()).isEqualTo(PET_SERVICOS);
	}
	

	
	@Test
	@DisplayName("Deve retornar Servico atualizado")
	void desativar() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(STATUS)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(servico);
		service.desativar(ID);
		//then
		Assertions.assertThat(servico).isNotNull();
		Assertions.assertThat(servico.getClass()).isEqualTo(Servico.class);
		Assertions.assertThat(servico.getStatus()).isEqualTo(false);
	}
	
	@Test
	@DisplayName("Deve retornar Servico atualizado")
	void ativar() {
		//given
		Servico servico = Servico.builder()
		                         .id(ID)
		                         .descricaoServico(DESCRICAO_SERVICO)
		                         .preco(PRECO)
		                         .status(false)
		                         .petServicos(PET_SERVICOS)
		                         .build();
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(servico);
		service.ativar(ID);
		//then
		Assertions.assertThat(servico).isNotNull();
		Assertions.assertThat(servico.getClass()).isEqualTo(Servico.class);
		Assertions.assertThat(servico.getStatus()).isEqualTo(STATUS);
	}
	
	
}