package com.example.carros.domain;

import org.springframework.data.repository.CrudRepository;

public interface CarroRepository extends CrudRepository<Carro, Long> {
    /**
     * Retorna a lista de carros filtrando por tipo
     * @param tipo Tipo do carro
     * @return Iterable<Carro>
     */
    Iterable<Carro> findByTipo(String tipo);
}
