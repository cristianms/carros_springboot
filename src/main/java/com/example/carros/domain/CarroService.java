package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    /**
     * Retorna a lista de carros completa do banco de dados
     *
     * @return List<CarroDTO>
     */
    public List<CarroDTO> getCarros() {
        // Recebe uma lista de carros List<Carro> e converte o  para um List<CarroDTO>
        return this.rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    /**
     * Retorna o carro referente ao id recebido
     *
     * @param id Identificador do carro
     * @return Carro
     */
    public Optional<CarroDTO> getCarroById(Long id) {
        return rep.findById(id).map(CarroDTO::create);
    }

    /**
     * Retorna a lista de carros filtrando por tipo
     *
     * @param tipo Tipo do carro
     * @return List<Carro>
     */
    public List<CarroDTO> getCarrosByTipo(String tipo) {
        // Recebe uma lista de carros List<Carro> e converte o  para um List<CarroDTO>
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    /**
     * Retorna o objeto carro inserido no banco de dados
     *
     * @param carro Objeto a ser inserido
     * @return Carro
     */
    public CarroDTO insert(Carro carro) {
        // Para prosseguir o objeto carro recebido não pode ser nulo
        Assert.notNull(carro, "Não foi possível inserir o registro");
        // Para prosseguir o carro.getId() recebido DEVE ser nulo
        Assert.isNull(carro.getId(), "Para atualizar o registro deve ser utilizado o método PUT");
        // Persiste no banco e converte o resultado para um CarroDTO
        return CarroDTO.create(rep.save(carro));
    }

    /**
     * Retorna o objeto carro inserido no banco de dados
     *
     * @param id        Identificador do objeto
     * @param carroPost Objeto com os dados para atualizar o registro
     * @return Carro
     */
    public CarroDTO update(Long id, Carro carroPost) {
        // Verifica se o id é nulo
        Assert.notNull(id, "Não foi possível inserir o registro");
        // Busca registro no banco de dados
        return rep.findById(id).map(carroDb -> {
            // Seta valores
            carroDb.setNome(carroPost.getNome());
            carroDb.setTipo(carroPost.getTipo());
            // Persiste no banco e retorna o objeto Carro convertido em CarroDTO
            return CarroDTO.create(rep.save(carroDb));
        }).orElseThrow(() -> new RuntimeException("Não foi possível atualizar o registro"));
    }

    /**
     * Retorna o id o registro excluído
     *
     * @param id Identificador do registro a ser excluído
     * @return Long
     */
    public boolean delete(Long id) {
        // Verifica se o id é nulo
        Assert.notNull(id, "Não foi possível deletar o registro");
        // Busca registro no banco de dados
        if (getCarroById(id).isPresent()) {
            // Persiste no banco
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
