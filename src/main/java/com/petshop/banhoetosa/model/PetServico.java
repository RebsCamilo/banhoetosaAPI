package com.petshop.banhoetosa.model;

import com.petshop.banhoetosa.enums.StatusPagamentoEnum;
import com.petshop.banhoetosa.enums.StatusServicoEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

//classe para representar a tabela de relação de pet e serviço. Precisei adicionar id e data
//ref https://stackoverflow.com/questions/71456192/why-hibernate-is-not-performing-the-join-on-this-many-to-many-association-table
@Entity
@Data
@Table(name = "pet_servico")
public class PetServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataCadastro = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)
    @Enumerated(EnumType.STRING)
    private StatusServicoEnum statusServico;
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum statusPagamento;

    @ManyToOne
    @JoinColumn(name = "id_pet")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

}

