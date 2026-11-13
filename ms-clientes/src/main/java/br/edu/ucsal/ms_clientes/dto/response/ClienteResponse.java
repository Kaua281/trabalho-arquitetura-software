package br.edu.ucsal.ms_clientes.dto.response;

import br.edu.ucsal.ms_clientes.model.Cliente;

public record ClienteResponse(String nome, String telefone) {
	
	public static ClienteResponse  converterParaDTO(Cliente c) {
	return new ClienteResponse(c.getNome(),c.getTelefone());
	}

}
