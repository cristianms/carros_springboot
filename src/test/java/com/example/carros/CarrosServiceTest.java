package com.example.carros;

import com.example.carros.api.exception.ObjectNotFoundException;
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
        CarroDTO carroDTOConsulta = service.getCarroById(carroDTOinserido.getId());
        // testa se nome e tipo são iguais
        assertEquals(carroDTOinserido.getNome(), carroDTOConsulta.getNome());
        assertEquals(carroDTOinserido.getTipo(), carroDTOConsulta.getTipo());
        // Deleta registro
        service.delete(carroDTOinserido.getId());
        // Testa se o registro foi de fato excluído
        try {
            assertNull(service.getCarroById(carroDTOinserido.getId()));
            // Se for nulo tem algo errado, deveria lançar exception
            fail("O carro não foi excluído. Verifique os testes");
        } catch (ObjectNotFoundException e) {
            // Ok, a exception é esperada pois não existe registro para retorno
        }
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
        CarroDTO carroDTO = service.getCarroById(11L);
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
