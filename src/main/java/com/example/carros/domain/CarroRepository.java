package com.example.carros.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    /**
     * Retorna a lista de carros filtrando por tipo
     *
     * @param tipo Tipo do carro
     * @return List<Carro>
     */
    List<Carro> findByTipo(String tipo);
}
