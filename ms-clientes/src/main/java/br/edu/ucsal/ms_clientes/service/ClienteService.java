package br.edu.ucsal.ms_clientes.service;


import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ucsal.ms_clientes.dto.request.ClienteRequest;
import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.model.ClienteFabrica;
import br.edu.ucsal.ms_clientes.model.ClientePF;
import br.edu.ucsal.ms_clientes.model.ClientePJ;
import br.edu.ucsal.ms_clientes.repository.ClientePFRepository;
import br.edu.ucsal.ms_clientes.repository.ClientePJRepository;
import br.edu.ucsal.ms_clientes.repository.ClienteRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
	
	private final ClienteRepository repository;
	private final ClientePJRepository pjRepository;
	private final ClientePFRepository pfRepository;
	

	public List<Cliente> listarTodos() {
		return repository.findAll();
	}

	public Cliente buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Cliente não localizado com o id " + id));
	}

	public void apagarPorId(Long id) {
		repository.delete(buscarPorId(id));
	}

	public Cliente criarNovoCliente(ClienteRequest request) {
		Cliente novoCliente = ClienteFabrica.criarCliente(request);
		
		if(novoCliente instanceof ClientePF && pfRepository.existsByCpf(request.cpf())) {
				throw new EntityExistsException("CPF já cadastrado");
			}
		
		if(novoCliente instanceof ClientePJ && pjRepository.existsByCnpj(request.cnpj())) {
				throw new EntityExistsException("CNPJ já cadastrado");
			}

		return repository.save(novoCliente);
	}
}
