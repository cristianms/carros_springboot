package com.example.carros.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Classe que representa a entidade User
 */
@Entity // Anotação necessária para que o Spring identifique essa classe como uma entidade
@Data // Anotação necessária que os métodos básicos do objeto sejam implementados em tempo de execução
public class User implements UserDetails {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // Para auto increment de outros bancos
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para auto increment do MySQL
    private Long id;
    private String nome;
    private String login;
    private String email;
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER) // Significa que haverá uma tabela "N para N"
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;


    // Métodos da interface UserDetails
    // Para este método "getAuthorities()" foi necessário que a entidade Role implementasse "GrantedAuthority"
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
