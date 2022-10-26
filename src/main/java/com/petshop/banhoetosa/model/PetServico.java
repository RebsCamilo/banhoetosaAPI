package com.petshop.banhoetosa.model;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//classe para representar a tabela de relação de pet e serviço. Precisei adicionar id e data
//ref https://stackoverflow.com/questions/71456192/why-hibernate-is-not-performing-the-join-on-this-many-to-many-association-table
@Entity
@Table(name = "pet_servico")
@Data
@NoArgsConstructor
public class PetServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataCadastro = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)
    @Column//(columnDefinition = "varchar(30) default 'AGUARDANDO'")
    @Enumerated(EnumType.STRING)
    private StatusServicoEnum statusServico; // = StatusServicoEnum.AGUARDANDO;
    @Column//(columnDefinition = "varchar(30) default 'EM_ABERTO'")
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamento; // = StatusPagamentoEnum.EM_ABERTO;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pet")
    private Pet pet;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_servico")
    private Servico servico;


    public PetServico cadastrar(Pet pet, Servico servico) {
        this.setStatusServico(StatusServicoEnum.AGUARDANDO);
        this.setStatusPagamento(StatusPagamentoEnum.EM_ABERTO);
        this.setPet(pet);
        this.setServico(servico);
        return this;
    }

    public PetServico atualizar(PetServico petServicoAtt) {
        this.setStatusServico(petServicoAtt.getStatusServico());
        this.setStatusPagamento(petServicoAtt.getStatusPagamento());
        return this;
    }

//    public PetServico(Pet pet, Servico servico, StatusServicoEnum statusServico, StatusPagamentoEnum statusPagamento) {
//        this.pet = pet;
//        this.servico = servico;
//        this.statusServico = statusServico;
//        this.statusPagamento = statusPagamento;
//    }
//
//    public PetServico(StatusServicoEnum statusServico, StatusPagamentoEnum statusPagamento) {
//        this.statusServico = statusServico;
//        this.statusPagamento = statusPagamento;
//    }
}

