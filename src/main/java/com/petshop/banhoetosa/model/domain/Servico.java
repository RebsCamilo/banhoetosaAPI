package com.petshop.banhoetosa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "servicos")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String descricaoServico;
    @Column(nullable = false)
    private BigDecimal preco;
    private LocalDateTime dataCadastro = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)

    @Column(columnDefinition = "Boolean default true")
    private Boolean status;

    @OneToMany(mappedBy = "servico")//, cascade = CascadeType.ALL, orphanRemoval = true)
    List<PetServico> petServicos;


    public Servico ativar() {
        this.setStatus(true);
        return this;
    }

    public Servico desativar() {
        this.setStatus(false);
        return this;
    }

    public Servico atualizar(Servico servicoAtt) {
        this.setDescricaoServico(servicoAtt.getDescricaoServico());
        this.setPreco(servicoAtt.getPreco());
        return this;
    }

//    public Servico(String descricaoServico, BigDecimal preco) {
//        this.descricaoServico = descricaoServico;
//        this.preco = preco;
//    }

}
