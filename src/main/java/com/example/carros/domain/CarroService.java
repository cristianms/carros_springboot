package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    /**
     * Retorna a lista de carros completa do banco de dados
     *
     * @return Iterable<Carro>
     */
    public Iterable<Carro> getCarros() {
        return this.rep.findAll();
    }

    /**
     * Retorna o carro referente ao id recebido
     *
     * @param id Identificador do carro
     * @return Carro
     */
    public Optional<Carro> getCarroById(Long id) {
        return rep.findById(id);
    }

    /**
     * Retorna a lista de carros filtrando por tipo
     *
     * @param tipo Tipo do carro
     * @return Iterable<Carro>
     */
    public Iterable<Carro> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    /**
     * Retorna o objeto carro inserido no banco de dados
     *
     * @param carro Objeto a ser inserido
     * @return Carro
     */
    public Carro insert(Carro carro) {
        // Para prosseguir o objeto carro recebido não pode ser nulo
        Assert.notNull(carro, "Não foi possível inserir o registro");
        // Para prosseguir o carro.getId() recebido DEVE ser nulo
        Assert.isNull(carro.getId(), "Para atualizar o registro deve ser utilizado o método PUT");
        // Persiste no banco
        return rep.save(carro);
    }

    /**
     * Retorna o objeto carro inserido no banco de dados
     *
     * @param id        Identificador do objeto
     * @param carroPost Objeto com os dados para atualizar o registro
     * @return Carro
     */
    public Carro update(Long id, Carro carroPost) {
        // Verifica se o id é nulo
        Assert.notNull(id, "Não foi possível inserir o registro");
        // Busca registro no banco de dados
        return getCarroById(id).map(carroDb -> {
            // Seta valores
            carroDb.setNome(carroPost.getNome());
            carroDb.setTipo(carroPost.getTipo());
            // Persiste no banco
            return rep.save(carroDb);
        }).orElseThrow(() -> new RuntimeException("Não foi possível atualizar o registro"));
    }

    /**
     * Retorna o id o registro excluído
     * @param id Identificador do registro a ser excluído
     * @return Long
     */
    public Long delete(Long id) {
        // Verifica se o id é nulo
        Assert.notNull(id, "Não foi possível deletar o registro");
        // Busca registro no banco de dados
        return getCarroById(id).map(carroDb -> {
            // Persiste no banco
            rep.delete(carroDb);
            return id;
        }).orElseThrow(() -> new RuntimeException("Não foi possível deletar o registro"));
    }
}
