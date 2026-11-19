package br.edu.ucsal.ms_clientes.exception;

import java.time.Instant;

public record RespostaErroPadrao (
		Instant dataHora,
		Integer status,
		String erro,
		String mensagem,
		String caminho
		)
{}
