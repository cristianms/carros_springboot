package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Classe controller responsável por atender as requisições do endpoint de carros
 */
@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {

    /**
     * Injeção para tratar das requisições da entidade Carro
     */
    @Autowired
    private CarroService service;

    /**
     * Retorna a lista de todos os carros
     * @return Iterable<Carro>
     */
    @GetMapping()
    public Iterable<Carro> get() {
        return service.getCarros();
    }

    /**
     * Retorna o carro referenciado pelo identificador recebido
     * @param id Identificador do carro
     * @return Optional<Carro>
     */
    @GetMapping("/{id}")
    public Optional<Carro> getId(@PathVariable("id") Long id) {
        return service.getCarroById(id);
    }

    /**
     * Retorna a lista de carros de acordo com o tipo recebido
     * @param tipo Tipo da lista
     * @return Iterable<Carro>
     */
    @GetMapping("/tipo/{tipo}")
    public Iterable<Carro> getCarrosByTipo(@PathVariable("tipo") String tipo) {
        return service.getCarrosByTipo(tipo);
    }

    /**
     * Retorna o objeto carro inserido
     * @param carro Objeto de dados para ser persistido no banco de dados
     * @return Carro
     */
    @PostMapping
    public Carro post(@RequestBody Carro carro) {
        return service.insert(carro);
    }

    /**
     * Retorna o objeto carro atualizado
     * @param id Identificador do registro a ser atualizado
     * @return Carro
     */
    @PutMapping("/{id}")
    public Carro put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        return service.update(id, carro);
    }

    /**
     * Retorna o id do objeto excluído
     * @param id Identificador do registro a ser excluído
     * @return Long
     */
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
