package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes utiizando o service CarrosService
 */
@SpringBootTest
class CarrosServiceTest {

    @Autowired
    CarroService service;

    /**
     * Teste para inserir e excluir um registro no banco
     */
    @Test
    void testSave() {
        // Cria objeto Carro para teste
        Carro carro = new Carro();
        carro.setNome("Ferrari");
        carro.setTipo("Esportivos");
        // Persiste
        CarroDTO carroDTOinserido = service.insert(carro);
        // Teste se a inserção retornou positivo
        assertNotNull(carroDTOinserido);
        assertNotNull(carroDTOinserido.getId());
        // Busca o registro persistido
        Optional<CarroDTO> optional = service.getCarroById(carroDTOinserido.getId());
        // Testa se encontrou o registro inserido
        assertTrue(optional.isPresent());
        // Obtém o objeto CarroDTO que estava no Optional
        CarroDTO carroDTOConsulta = optional.get();
        // testa se nome e tipo são iguais
        assertEquals(carroDTOinserido.getNome(), carroDTOConsulta.getNome());
        assertEquals(carroDTOinserido.getTipo(), carroDTOConsulta.getTipo());
        // Deleta registro
        service.delete(carroDTOinserido.getId());
        // Testa se o registro foi de fato excluído
        assertFalse(service.getCarroById(carroDTOinserido.getId()).isPresent());
    }

    /**
     * Teste para verificar a quantidade de linhas da tabela
     */
    @Test
    void testLista() {
        // Busca lista de carros
        List<CarroDTO> carros = service.getCarros();
        // Testa se é igual a 30
        assertEquals(30, carros.size());
    }

    /**
     * Teste para verificar a consistência da tabela
     */
    @Test
    void testGet() {
        // Busca o registro id 11
        Optional<CarroDTO> carro = service.getCarroById(11L);
        // Testa se encontrou resultado
        assertTrue(carro.isPresent());
        // Obtém DTO contido no resultado
        CarroDTO carroDTO = carro.get();
        // Testa se o nome bate com o esperado
        assertEquals("Ferrari FF", carroDTO.getNome());
    }

    /**
     * Teste para verifica a quantidade de registros por tipo
     */
    @Test
    void testListaPorTipo() {
        // Testa se existem 10 registros do tipo "esportivos"
        assertEquals(10, service.getCarrosByTipo("esportivos").size());
        // Testa se existem 10 registros do tipo "classicos"
        assertEquals(10, service.getCarrosByTipo("classicos").size());
        // Testa se existem 10 registros do tipo "luxo"
        assertEquals(10, service.getCarrosByTipo("luxo").size());
        // Testa se existe 0 registros do tipo "x"
        assertEquals(0, service.getCarrosByTipo("x").size());
    }

}
