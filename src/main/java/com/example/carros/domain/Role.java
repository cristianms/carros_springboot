package com.example.carros.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe que representa a entidade Role
 */
@Entity // Anotação necessária para que o Spring identifique essa classe como uma entidade
@Data // Anotação necessária que os métodos básicos do objeto sejam implementados em tempo de execução
public class Role implements GrantedAuthority {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // Para auto increment de outros bancos
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para auto increment do MySQL
    private Long id;
    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }
}
