package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;

import br.edu.ucsal.ms_clientes.dto.request.ClienteRequest;
import br.edu.ucsal.ms_clientes.util.TipoClienteEnum;


public class ClienteFabrica{
	public static Cliente criarCliente(ClienteRequest request) {
		switch(request.tipo()) {
		
		case TipoClienteEnum.PESSOA_FISICA:
			return new ClientePF(
					request.email(),
					request.telefone(),
					request.tipo(),
					Instant.now(),
					request.nomeCompleto(),
					request.cpf(),
					request.dataNascimento());
		case TipoClienteEnum.PESSOA_JURIDICA:
			return new ClientePJ(
					request.email(),
					request.telefone(),
					request.tipo(),
					Instant.now(),
					request.razaoSocial(),
					request.nomeFantasia(),
					request.cnpj(),
					request.inscricaoEstadual());
		default:
			throw new IllegalArgumentException("Tipo de entidade não suportado: " + request.tipo());
		}
	}

}
