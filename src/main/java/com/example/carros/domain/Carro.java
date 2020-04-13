package com.example.carros.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carro {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // Para auto increment de outros bancos
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para auto increment do MySQL
    private Long id;

    private String nome;

    /**
     * Construtor padrão necessário para o Springboot
     */
    public Carro() {
    }

    /**
     * Construtor
     * @param id Identificador do carro
     * @param nome Nome do carro
     */
    public Carro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
