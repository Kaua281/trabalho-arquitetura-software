package br.edu.ucsal.ms_clientes.service;


import java.util.List;
import java.util.Objects;

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

	public Cliente atualizaCliente(Long id, ClienteRequest request) {
		Cliente cliente = buscarPorId(id);
		
		if(request.tipo() != null && request.tipo() != cliente.getTipoClienteEnum()) {
			throw new IllegalArgumentException("Não é permitido alterar o tipo do cliente");
		}
		
		
		if(cliente instanceof ClientePF pf && 
				request.cpf() != null  && 
				!Objects.equals(request.cpf(),pf.getCpf())) {
			throw new IllegalArgumentException("CPF não pode ser alterado");
		}
		
		if(cliente instanceof ClientePJ pj && 
				request.cnpj() != null &&
				!Objects.equals(request.cnpj(), pj.getCnpj())) {
			throw new IllegalArgumentException("CNPJ não pode ser alterado");
		}
		
		cliente.atualizaDados(request);
		return repository.save(cliente);
	}
}
