package com.example.carros.api.security;

import com.example.carros.domain.User;
import com.example.carros.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe responsável por sobrescrever e customizar o acesso ao UserDetails
 */
@Service(value = "userDetailsService") // Necessário para identificar a interface ao injetar a dependência
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Injeta repositório responsável por buscar o usuário no banco de dados
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Método sobrescrito para tratar da autenticação dos usuários
     *
     * @param username Nome de usuário/login
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca as o usuário pelo login recebido
        User user = userRepository.findByLogin(username);
        if (user == null) {
            // Caso não encontre lança exception
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        // Retorna os detalhes do usuário logado
        // Foi possível retornar o próprio User pois ele está implementando UserDetails e seus métodos
        return user;
    }
}
