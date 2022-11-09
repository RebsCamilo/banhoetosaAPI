package com.petshop.banhoetosa.service;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import com.petshop.banhoetosa.model.domain.Pet;
import com.petshop.banhoetosa.model.domain.PetServico;
import com.petshop.banhoetosa.model.domain.Servico;
import com.petshop.banhoetosa.model.domain.Tutor;
import com.petshop.banhoetosa.repository.PetServicoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetServicoServiceTest {
	
	@InjectMocks
	private PetServicoService service;
	@Mock
	private PetServicoRepository repository;
	
	private static final Long ID = 1L;
	private static final StatusServicoEnum STATUS_SERVICO_ENUM = StatusServicoEnum.AGUARDANDO;
	private static final StatusPagamentoEnum STATUS_PAGAMENTO_ENUM = StatusPagamentoEnum.EM_ABERTO;
	private static final Pet PET = new Pet(ID, "Mel", "pinscher", 10);
	private static final Servico SERVICO = new Servico(ID, "banho", new BigDecimal("30.0"));
	private static final int INDEX = 0;
	
	
	@Test
	@DisplayName("Deve retornar Lista de Serviços realizados")
	void deveRetornarListaDeServicosRealizados() {
		//given
		PetServico petServico = PetServico.builder()
		                        .id(ID)
		                        .statusServico(STATUS_SERVICO_ENUM)
		                        .statusPagamento(STATUS_PAGAMENTO_ENUM)
		                        .pet(PET)
		                        .servico(SERVICO)
		                        .build();
		
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of(petServico));
		List<PetServico> result = service.listarTodos();
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(INDEX).getClass()).isEqualTo(PetServico.class);
		
		Assertions.assertThat(result.get(INDEX).getId()).isEqualTo(ID);
		Assertions.assertThat(result.get(INDEX).getStatusServico()).isEqualTo(STATUS_SERVICO_ENUM);
		Assertions.assertThat(result.get(INDEX).getStatusPagamento()).isEqualTo(STATUS_PAGAMENTO_ENUM);
		Assertions.assertThat(result.get(INDEX).getPet()).isEqualTo(PET);
		Assertions.assertThat(result.get(INDEX).getServico()).isEqualTo(SERVICO);
	}
	
	@Test
	@DisplayName("Deve retornar Lista Vazia")
	public void deveRetornarNull() {
		//when
		Mockito.when(repository.findAll()).thenReturn(List.of());
		List<PetServico> result = service.listarTodos();
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	@Test
	@DisplayName("Deve retornar o Tutor cadastrado")
	void deveRetornarOTutorCadastrado() {
		//given
		PetServico petServico = PetServico.builder()
		                                  .id(ID)
		                                  .statusServico(STATUS_SERVICO_ENUM)
		                                  .statusPagamento(STATUS_PAGAMENTO_ENUM)
		                                  .pet(PET)
		                                  .servico(SERVICO)
		                                  .build();
		//when
		Mockito.when(repository.save(petServico)).thenReturn(petServico);
		PetServico result = service.cadastrar(petServico, ID, ID);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(PetServico.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getStatusServico()).isEqualTo(STATUS_SERVICO_ENUM);
		Assertions.assertThat(result.getStatusPagamento()).isEqualTo(STATUS_PAGAMENTO_ENUM);
		Assertions.assertThat(result.getPet()).isEqualTo(PET);
		Assertions.assertThat(result.getServico()).isEqualTo(SERVICO);
	}
	
	@Test
	@DisplayName("Deve retornar um Optional com Tutor")
	void deveRetornarUmOptionalComTutor() {
		Optional<PetServico> petServico = Optional.of(PetServico.builder()
		                                  .id(ID)
		                                  .statusServico(STATUS_SERVICO_ENUM)
		                                  .statusPagamento(STATUS_PAGAMENTO_ENUM)
		                                  .pet(PET)
		                                  .servico(SERVICO)
		                                  .build());
		
		//when
		Mockito.when(repository.findById(ID)).thenReturn(petServico);
		Optional<PetServico> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEqualTo(petServico);
	}
	
	@Test
	@DisplayName("Deve retornar um Optional sem PetServico")
	public void deveRetornarUmOptionalSemPetServico() {
		//when
		Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());
		Optional<PetServico> result = service.detalhar(ID);
		//then
		Assertions.assertThat(result).isEmpty();
	}
	
	
	@Test
	@DisplayName("Deve retornar PetServico atualizado")
	void deveRetornarPetServicoAtualizado() {
		//given
		PetServico petServico = PetServico.builder()
		                                  .id(ID)
		                                  .statusServico(STATUS_SERVICO_ENUM)
		                                  .statusPagamento(STATUS_PAGAMENTO_ENUM)
		                                  .pet(PET)
		                                  .servico(SERVICO)
		                                  .build();
		
		PetServico petServicoAtt = PetServico.builder()
		                                  .id(ID)
		                                  .statusServico(STATUS_SERVICO_ENUM)
		                                  .statusPagamento(StatusPagamentoEnum.PAGO)
		                                  .pet(PET)
		                                  .servico(SERVICO)
		                                  .build();
		
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(petServico);
		Mockito.when(repository.save(petServico)).thenReturn(petServico);
		PetServico result = service.atualizar(ID, petServicoAtt);
		//then
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getClass()).isEqualTo(PetServico.class);
		
		Assertions.assertThat(result.getId()).isEqualTo(ID);
		Assertions.assertThat(result.getStatusServico()).isEqualTo(STATUS_SERVICO_ENUM);
		Assertions.assertThat(result.getStatusPagamento()).isEqualTo(STATUS_PAGAMENTO_ENUM);
		Assertions.assertThat(result.getPet()).isEqualTo(PET);
		Assertions.assertThat(result.getServico()).isEqualTo(SERVICO);
	}
	
	@Test
	@DisplayName("Deve deletar PetServico")
	void deveDeletarPetServico() {
		//given
		PetServico petServico = PetServico.builder()
		                                  .id(ID)
		                                  .statusServico(STATUS_SERVICO_ENUM)
		                                  .statusPagamento(STATUS_PAGAMENTO_ENUM)
		                                  .pet(PET)
		                                  .servico(SERVICO)
		                                  .build();
		//when
		Mockito.when(repository.getReferenceById(ID)).thenReturn(petServico);
		Mockito.doNothing().when(repository).delete(petServico);
		//then
		service.deletar(ID);
		Mockito.verify(repository, Mockito.times(1)).delete(petServico);
	}

}