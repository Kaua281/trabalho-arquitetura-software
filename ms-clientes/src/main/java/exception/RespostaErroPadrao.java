package exception;

import java.time.Instant;

public record RespostaErroPadrao (
		Instant dataHora,
		Integer status,
		String erro,
		String mensagem,
		String caminho
		)
{}
