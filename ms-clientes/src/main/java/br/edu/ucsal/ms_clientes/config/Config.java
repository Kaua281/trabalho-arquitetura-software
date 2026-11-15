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
import br.edu.ucsal.ms_clientes.model.ClientePF;
import br.edu.ucsal.ms_clientes.model.ClientePJ;
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
						new ClientePF(
								"ironman@starkindustries.com",
								"555323232",
								TipoCliente.PESSOA_FISICA,
								Instant.now(),
								"Tony Stark",
								"22211133345",
								LocalDate.of(1972, 12, 3)
								),
						new ClientePF(
								"nromanov@shield.com",
								"555323111",
								TipoCliente.PESSOA_FISICA,
								Instant.now(),
								"Natasha Romanov",
								"111222333345",
								LocalDate.of(1983,1,2)
								),
						new ClientePJ(
								"ceo@starkindustries.com",
								"555323232",
								TipoCliente.PESSOA_JURIDICA,
								Instant.now(),
								"Howard Stark and Son Company",
								"Stark Industries",
								"22233344400001",
								"112233445"
								)
						));
				clientes.forEach(repository::save);
			}

		};
	}
}
