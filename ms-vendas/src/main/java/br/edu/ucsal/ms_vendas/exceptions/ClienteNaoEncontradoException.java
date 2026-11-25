package br.edu.ucsal.ms_vendas.exceptions;

public class ClienteNaoEncontradoException extends RecursoNaoEncontradoException {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
