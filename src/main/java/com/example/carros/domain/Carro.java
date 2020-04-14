package com.example.carros.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Classe que representa a entidade Carro
 */
@Entity // Anotação necessária para que o Spring identifique essa classe como uma entidade
@Data // Anotação necessária que os métodos básicos do objeto sejam implementados em tempo de execução
public class Carro {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // Para auto increment de outros bancos
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para auto increment do MySQL
    private Long id;
    private String nome;
    private String tipo;
    private String descricao;

    @Column(name = "url_foto")
    private String urlFoto;

    @Column(name = "url_video")
    private String urlVideo;

    private String latitude;
    private String longitude;

}
