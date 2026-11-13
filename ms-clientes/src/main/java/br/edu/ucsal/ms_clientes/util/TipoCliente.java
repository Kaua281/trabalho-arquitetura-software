package br.edu.ucsal.ms_clientes.util;

import lombok.Getter;

@Getter
public enum TipoCliente {
	PESSOA_FISICA("PF"),
	PESSOA_JURIDICA("PJ");
	
	private final String valor;

	private TipoCliente(String valor) {
		this.valor = valor;
	}
}
