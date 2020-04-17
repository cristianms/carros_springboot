package com.example.carros;

import com.example.carros.api.upload.FirebaseStorageService;
import com.example.carros.api.upload.UploadInput;
import com.example.carros.api.upload.UploadOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe responsável por executar os testes de upload
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Anotação necessária para realizar os testes de API com rest
public class UploadTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FirebaseStorageService service;

    /**
     * Método responsável por encapsular a autenticação
     *
     * @return TestRestTemplate
     */
    private TestRestTemplate basicAuth() {
        return rest.withBasicAuth("cristian", "123");
    }

    /**
     * Executa o teste de upload
     */
    @Test
    public void testUploadFirebase() {
        // Envia um arquivo e recebe a URL de acesso a ele no storage
        String url = service.upload(getUploadInput());
        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        // Se retorno 200 está ok
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    /**
     * Executa o teste de upload através da API
     */
    @Test
    public void testUploadAPI() {
        // Monta o objeto teste para upload
        UploadInput upload = getUploadInput();
        // Executa o post para a URL de upload passando o objeto criado
        ResponseEntity<UploadOutput> response = basicAuth().postForEntity("/api/v1/upload", upload, UploadOutput.class);
        System.out.println(response);
        // Verifica se criou
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Obtém o retorno
        UploadOutput out = response.getBody();
        assertNotNull(out);
        System.out.println(out);
        // Obtém a URL de acesso
        String url = out.getUrl();
        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        // Verifica o status de retorno da URL, se for 200 está ok
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    /**
     * Monta um arquivo teste para envio
     *
     * @return UploadInput
     */
    private UploadInput getUploadInput() {
        UploadInput upload = new UploadInput();
        upload.setFileName("nome.txt");
        // Base64 de "Ricardo Lecheta"
        upload.setBase64("UmljYXJkbyBMZWNoZXRh");
        upload.setMimeType("text/plain");
        return upload;
    }

}