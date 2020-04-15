package com.example.carros.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca usuário por login
     *
     * @param login Login
     * @return User
     */
    User findByLogin(String login);
}
