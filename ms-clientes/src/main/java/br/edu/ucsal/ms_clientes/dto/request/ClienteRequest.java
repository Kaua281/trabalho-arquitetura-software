package br.edu.ucsal.ms_clientes.dto.request;

import java.time.LocalDate;

import br.edu.ucsal.ms_clientes.util.TipoClienteEnum;

public record ClienteRequest(
		TipoClienteEnum tipo,
		String email,
		String telefone,
		
		String nomeCompleto,
		String cpf,
		LocalDate dataNascimento,
		
		String razaoSocial,
		String nomeFantasia,
		String cnpj,
		String inscricaoEstadual
		) {

}
