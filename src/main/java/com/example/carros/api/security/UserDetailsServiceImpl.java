package com.example.carros.api.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe responsável por sobrescrever e customizar o acesso ao UserDetails
 */
@Service(value = "userDetailsService") // Necessário para identificar a interface ao injetar a dependência
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Encoder para senha
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (username.equals("user")) {
            return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
        } else if (username.equals("admin")) {
            return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
