package com.petshop.banhoetosa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enderecos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150)
    private String rua;
    @Column(length = 20)
    private Integer numero;
    @Column(length = 100)
    private String bairro;
    @Column(length = 150)
    private String complemento;
    @Column(length = 10)
    private String cep;
    private LocalDateTime dataCadastro = LocalDateTime.now(); //tem alguma forma do spring manter as alterações no banco (guardar os logs?)

    @OneToOne(mappedBy = "endereco")
    private Tutor tutor;

    public Endereco(String rua, Integer numero, String bairro, String complemento, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
    }
    
    public Endereco cadastrar() {
        this.setDataCadastro(LocalDateTime.now());
        return this;
    }

    public Endereco atualizar(Endereco enderecoAtt) {
        this.setRua(enderecoAtt.getRua());
        this.setNumero(enderecoAtt.getNumero());
        this.setBairro(enderecoAtt.getBairro());
        this.setComplemento(enderecoAtt.getComplemento());
        this.setCep(enderecoAtt.getCep());
        return this;
    }
}