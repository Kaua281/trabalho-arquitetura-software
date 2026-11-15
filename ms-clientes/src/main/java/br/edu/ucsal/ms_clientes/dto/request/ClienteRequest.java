package br.edu.ucsal.ms_clientes.dto.request;

import java.time.LocalDate;

import br.edu.ucsal.ms_clientes.util.TipoCliente;

public record ClienteRequest(
		TipoCliente tipo,
		String email,
		String telefone,
		
		String nomeCompleto,
		String CPF,
		LocalDate dataNascimento,
		
		String razaoSocial,
		String nomeFantasia,
		String CNPJ,
		String inscricaoEstadual
		) {

}
