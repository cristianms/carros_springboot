package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
     *
     * @return Iterable<Carro>
     */
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
    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable("id") Long id) {
        return service.getCarroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna a lista de carros de acordo com o tipo recebido
     *
     * @param tipo Tipo da lista
     * @return Iterable<Carro>
     */
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
    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {
        try {
            service.insert(carro);
            return ResponseEntity.created(getUri(carro.getId())).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
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
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return service.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
