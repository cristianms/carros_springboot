package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe responsável por executar os testes de API
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Anotação necessária para realizar os testes de API com rest
public class CarrosAPITest {

    /**
     * Dependência que permite simular requisições à API
     */
    @Autowired
    protected TestRestTemplate rest;

    /**
     * Método que simular uma requisição para uma URL para obter um carro
     *
     * @param url URL para onde se deseja fazer a chamada
     * @return ResponseEntity
     */
    private ResponseEntity<CarroDTO> getCarro(String url) {
        // Executa a chamada sem autenticação
        // return rest.getForEntity(url, CarroDTO.class);
        // Executa a chamada com autenticação
        return rest.withBasicAuth("user", "123").getForEntity(url, CarroDTO.class);
    }

    /**
     * Método que simula uma requisição para uma URL para obter uma lista de carros
     *
     * @param url URL para onde se deseja fazer a chamada
     * @return ResponseEntity<List < CarroDTO>>
     */
    private ResponseEntity<List<CarroDTO>> getCarros(String url) {
        // Executa a chamada sem autenticação
        // return rest.exchange(
        // Executa a chamada com autenticação
        return rest.withBasicAuth("user", "123").exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>() {
                });
    }

    @Test
    public void testSave() {
        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");
        // Insert
        // Executa a chamada sem autenticação
        // ResponseEntity response = rest.postForEntity("/api/v1/carros", carro, null);
        // Executa a chamada com autenticação
        ResponseEntity response = rest.withBasicAuth("admin", "123").postForEntity("/api/v1/carros", carro, null);
        System.out.println(response);
        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();

        assertNotNull(c);
        assertEquals("Porshe", c.getNome());
        assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        rest.withBasicAuth("user", "123").withBasicAuth("user", "123").delete(location);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
    }

    @Test
    public void testLista() {
        List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
        assertNotNull(carros);
        assertEquals(30, carros.size());
    }

    @Test
    public void testListaPorTipo() {
        assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());
        assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
    }

    @Test
    public void testGetOk() {
        ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarroDTO c = response.getBody();
        assertEquals("Ferrari FF", c.getNome());
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getCarro("/api/v1/carros/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}