package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Classe controller responsável por atender as requisições do endpoint de carros
 */
@Api
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
     *
     * @return Iterable<Carro>
     */
    @ApiOperation(value = "Obter a lista de todos os carros") // Documentação para o Swagger
    @GetMapping()
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getCarros());
    }

    /**
     * Retorna o carro referenciado pelo identificador recebido
     *
     * @param id Identificador do carro
     * @return ResponseEntity
     */
    @ApiOperation(value = "Obter o carro a partir do id") // Documentação para o Swagger
    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getCarroById(id));
    }

    /**
     * Retorna a lista de carros de acordo com o tipo recebido
     *
     * @param tipo Tipo da lista
     * @return Iterable<Carro>
     */
    @ApiOperation(value = "Obter a lista de todos os carros de acordo com o tipo") // Documentação para o Swagger
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarrosByTipo(tipo);
        return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }

    /**
     * Retorna o objeto carro inserido
     *
     * @param carro Objeto de dados para ser persistido no banco de dados
     * @return Carro
     */
    @ApiOperation(value = "Inserir um carro") // Documentação para o Swagger
    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {
        // Insere o carro e obtém o objeto CarroDTO de retorno
        CarroDTO carroDTO = service.insert(carro);
        // Busca a URI do registro inserido
        URI location = getUri(carroDTO.getId());
        // Retorna OK com a location no header
        return ResponseEntity.created(location).build();
    }

    /**
     * Retorna a URI do objeto persistido
     *
     * @param id Id do registro
     * @return URI
     */
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    /**
     * Retorna o objeto carro atualizado
     *
     * @param id Identificador do registro a ser atualizado
     * @return Carro
     */
    @ApiOperation(value = "Alterar um carro") // Documentação para o Swagger
    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);
        CarroDTO carroDTO = service.update(id, carro);
        return carroDTO != null ? ResponseEntity.ok(carroDTO) : ResponseEntity.notFound().build();
    }

    /**
     * Retorna o id do objeto excluído
     *
     * @param id Identificador do registro a ser excluído
     * @return Long
     */
    @ApiOperation(value = "Excluir um carro") // Documentação para o Swagger
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
