package br.edu.ucsal.ms_clientes.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ucsal.ms_clientes.dto.request.ClienteRequest;
import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.model.ClientePF;
import br.edu.ucsal.ms_clientes.model.ClientePJ;
import br.edu.ucsal.ms_clientes.repository.ClientePFRepository;
import br.edu.ucsal.ms_clientes.repository.ClientePJRepository;
import br.edu.ucsal.ms_clientes.repository.ClienteRepository;
import br.edu.ucsal.ms_clientes.util.TipoCliente;
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
		Cliente novoCliente = switch(request.tipo()) {
		
		case TipoCliente.PESSOA_FISICA ->{
	

			if(pfRepository.existsByCPF(request.CPF())) {
				throw new EntityExistsException("CPF já cadastrado");
			}
			Cliente pf = new ClientePF(
					request.email(),
					request.telefone(),
					request.tipo(),
					Instant.now(),
					request.nomeCompleto(),
					request.CPF(),
					request.dataNascimento());
			yield pf;
		}
		case TipoCliente.PESSOA_JURIDICA -> {
	
			if(pjRepository.existsByCNPJ(request.CNPJ())) {
				throw new EntityExistsException("CNPJ já cadastrado");
			}

			Cliente pj = new ClientePJ(
					request.email(),
					request.telefone(),
					request.tipo(),
					Instant.now(),
					request.razaoSocial(),
					request.nomeFantasia(),
					request.CNPJ(),
					request.inscricaoEstadual());
			yield pj;
		}
		};
		return repository.save(novoCliente);
	}
}
