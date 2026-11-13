package br.edu.ucsal.ms_clientes.config;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.repository.ClienteRepository;
import br.edu.ucsal.ms_clientes.util.TipoCliente;

@Configuration
public class Config {
	
	@Order(1)
	@Bean
	CommandLineRunner carregaListaClientesMock(ClienteRepository repository) {
		return args ->{
			if (repository.count()==0) {
				List<Cliente> clientes = new ArrayList<>(List.of(
						new Cliente(
								"Tony Stark",
								"ironman@starkindustries.com",
								"555323232",
								null,
								TipoCliente.PESSOA_FISICA,
								"11122233344",
								LocalDate.of(1972, 10, 1),
								Instant.now()
								),
						new Cliente(
								"Natasha Romanov",
								"nromanov@shield.com",
								"555323111",
								null,
								TipoCliente.PESSOA_FISICA,
								"11122223231",
								LocalDate.of(1980, 1, 1),
								Instant.now()
								),
						new Cliente(
								"Tony Stark",
								"ceo@starkindustries.com",
								"555323232",
								"Stark Industries",
								TipoCliente.PESSOA_JURIDICA,
								"22233344450001",
								LocalDate.of(1945, 6, 20),
								Instant.now()
								)
						));
				clientes.forEach(repository::save);
			}

		};
	}
}
