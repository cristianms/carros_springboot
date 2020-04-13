package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    /**
     * Retorna a lista de carros completa do banco de dados
     * @return Iterable<Carro>
     */
    public Iterable<Carro> getCarros() {
        return this.rep.findAll();
    }

    /**
     * Retorna a lista de carros criada em memória
     * @return List<Carro>
     */
    public List<Carro> getCarrosFake() {
        List<Carro> carros = new ArrayList<>();
        carros.add(new Carro(1L, "Fusca"));
        carros.add(new Carro(2L, "Brasília"));
        carros.add(new Carro(3L, "Chevete"));
        return carros;
    }
}
