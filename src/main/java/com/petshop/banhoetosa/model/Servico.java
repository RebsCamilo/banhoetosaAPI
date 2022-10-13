package com.petshop.banhoetosa.model;

import lombok.*;

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

//    Não resolveu pois preciso do id das relações pet-servico
//    @ManyToMany(mappedBy = "servicos")
//    private List<Pet> pets;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
    List<PetServico> petServicos;

    public Servico(String descricaoServico, BigDecimal preco) {
        this.descricaoServico = descricaoServico;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", descricaoServico='" + descricaoServico + '\'' +
                ", preco=" + preco +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
