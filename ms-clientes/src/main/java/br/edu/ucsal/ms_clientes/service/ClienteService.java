package br.edu.ucsal.ms_clientes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.repository.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
	
	private final ClienteRepository repository;

	public List<Cliente> listarTodos() {
		return repository.findAll();
	}
	

}
