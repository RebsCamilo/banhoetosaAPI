package com.petshop.banhoetosa.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pets")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String nome;
    @Column(length = 50)
    private String especie; //ex:cao, gato, papagaio
    @Column(length = 50)
    private String raca;
//    @Column(nullable = false)
    private Integer idade;
    private String detalhe;
    private LocalDateTime dataCadastro; // = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)
//    @Column(columnDefinition = "Boolean default true")
//    private Boolean status;

//    (cascade = CascadeType.PERSIST)
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @JsonIgnore
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)//, orphanRemoval = true)
    List<PetServico> petServicos;



    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", idade=" + idade +
                '}';
    }

    public Pet(Long id, String nome, String raca, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }
    
//    public Pet(Long id, String nome, String raca, Integer idade, Tutor tutor) {
//        this.id = id;
//        this.nome = nome;
//        this.raca = raca;
//        this.idade = idade;
//        this.tutor = tutor;
//    }

    public Pet cadastrar(Tutor tutor) {
        this.setTutor(tutor);
        this.setDataCadastro(LocalDateTime.now());
        return this;
    }

    public Pet atualizar(Pet petAtt) { //, Tutor tutorAtt) {
        this.setNome(petAtt.getNome());
        this.setRaca(petAtt.getRaca());
        this.setEspecie(petAtt.getEspecie());
        this.setIdade(petAtt.getIdade());
        this.setDetalhe(petAtt.getDetalhe());
//        this.setTutor(tutor);
        return this;
    }
    

    //    Não resolveu pois preciso do id das relações pet-servico
//    @ManyToMany
//    @JoinTable(name = "pets_servicos",
//                joinColumns = @JoinColumn(name = "servico_id"),
//                inverseJoinColumns = @JoinColumn(name = "pet_id"))
//    private List<Servico> servicos;


    //Construtor para PetCadastroForm
//    public Pet(String nome, String especie, String raca, Integer idade, String detalhe, Tutor tutor) {
//        this.nome = nome;
//        this.especie = especie;
//        this.raca = raca;
//        this.idade = idade;
//        this.detalhe = detalhe;
//        this.tutor = tutor;
//    }
//    public Pet(String nome, String especie, String raca, Integer idade, String detalhe) {
//        this.nome = nome;
//        this.especie = especie;
//        this.raca = raca;
//        this.idade = idade;
//        this.detalhe = detalhe;
////        this.tutor = tutor;
//    }


}
