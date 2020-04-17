package com.example.carros.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe responsável pela segurança da API
 */
@Configuration // Para que o Spring entenda essa classe como uma configuração da API
@EnableWebSecurity // Para habilitar a segurança do Spring
@EnableGlobalMethodSecurity(securedEnabled = true) // Para utilizar as configurações de roles e acessos a métodos da API
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Injeção que possui as regras de usuários e roles
     */
    @Autowired
    @Qualifier("userDetailsService") // Como há duas implementações isso identifica que deve usar a minha
    private UserDetailsService userDetailsService;

    /**
     * Método responsável por sobrescrever regras de segurança das requisições HTTP
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable(); // Para permitir requisições de servidores externos
    }

    /**
     * Método responsável por validar os dados de acesso e autenticar o usuário
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
