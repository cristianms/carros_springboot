package com.example.carros.domain.dto;

import com.example.carros.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * Classe DTO da entidade Carro
 */
@Data
public class CarroDTO {

    private Long id;
    private String nome;
    private String tipo;

    /**
     * Método responsável por converter um objeto Carro em um CarroDTO através da dependência ModelMapper
     *
     * @param carro Objeto Carro
     * @return CarroDTO
     */
    public static CarroDTO create(Carro carro) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(carro, CarroDTO.class);
    }
}
