package com.petshop.banhoetosa.model;

import lombok.AllArgsConstructor;
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
    private LocalDateTime dataCadastro = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)

    @OneToOne(cascade = CascadeType.ALL) //mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    public Tutor(String nome, String telefone1, String telefone2, String email, Endereco endereco) {
        this.nome = nome;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.endereco = endereco;
    }

    public Tutor(String nome, String telefone1, String telefone2, String email) {
        this.nome = nome;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
    }

    public Tutor(String email) {
        this.email = email;
    }
}

