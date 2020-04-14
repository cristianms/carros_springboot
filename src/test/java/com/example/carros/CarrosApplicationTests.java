package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	CarroService carroService;

	@Test
	void contextLoads() {
	}

	@Test
	void teste1() {
		// Cria objeto Carro para teste
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("Esportivos");
		// Persiste
		CarroDTO carroDTOinserido = carroService.insert(carro);
		// Teste se a inserção retornou positivo
		assertNotNull(carroDTOinserido);
		assertNotNull(carroDTOinserido.getId());
		// Busca o registro persistido
		Optional<CarroDTO> optional = carroService.getCarroById(carroDTOinserido.getId());
		// Testa se encontrou o registro inserido
		assertTrue(optional.isPresent());
		// Obtém o objeto CarroDTO que estava no Optional
		CarroDTO carroDTOConsulta = optional.get();
		// testa se nome e tipo são iguais
		assertEquals(carroDTOinserido.getNome(), carroDTOConsulta.getNome());
		assertEquals(carroDTOinserido.getTipo(), carroDTOConsulta.getTipo());
		// Deleta registro
		carroService.delete(carroDTOinserido.getId());
		// Testa se o registro foi de fato excluído
		assertFalse(carroService.getCarroById(carroDTOinserido.getId()).isPresent());
	}

}
