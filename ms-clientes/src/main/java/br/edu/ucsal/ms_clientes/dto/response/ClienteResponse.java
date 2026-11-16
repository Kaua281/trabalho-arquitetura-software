package br.edu.ucsal.ms_clientes.dto.response;

import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.model.ClientePF;
import br.edu.ucsal.ms_clientes.model.ClientePJ;

public record ClienteResponse(Long id, String nome, String email, String tipo, String idReceitaFederal) {

	public static ClienteResponse  converterParaDTO(Cliente cliente) {
		if(cliente instanceof ClientePF pf){
			return new ClienteResponse(
					pf.getId(),
					pf.getNomeCompleto(),
					pf.getEmail(),
					cliente.getTipoClienteEnum().name(),
					pf.getCpf());
		}
		if(cliente instanceof ClientePJ pj) {
			return new ClienteResponse(
					pj.getId(),
					pj.getRazaoSocial(),
					pj.getEmail(),
					cliente.getTipoClienteEnum().name(),
					pj.getCnpj());
		}
		throw new IllegalArgumentException("Tipo de cliente desconhecido");
	}
}
