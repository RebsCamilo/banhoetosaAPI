package com.petshop.banhoetosa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tutores")
@Data
//@Getter
//@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String nome;
    @Column(length = 16)
    private String telefone1;
    @Column(length = 16)
    private String telefone2;
    @Column(length = 100)
    private String email;
    private LocalDateTime dataCadastro; // = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)

    @OneToOne(cascade = CascadeType.ALL) //mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;


    public Tutor(Long id, String nome, String telefone1, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone1 = telefone1;
        this.email = email;
    }

    public Tutor cadastrar(Endereco endereco) {
        this.setEndereco(endereco);
        this.setDataCadastro(LocalDateTime.now());
        return this;
    }

    public Tutor atualizar(Tutor tutorAtt, Endereco enderecoAtt) {
        this.setNome(tutorAtt.getNome());
        this.setTelefone1(tutorAtt.getTelefone1());
        this.setTelefone2(tutorAtt.getTelefone2());
        this.setEmail(tutorAtt.getEmail());
        this.setEndereco(this.getEndereco().atualizar(enderecoAtt));
        return this;
    }
    
}

