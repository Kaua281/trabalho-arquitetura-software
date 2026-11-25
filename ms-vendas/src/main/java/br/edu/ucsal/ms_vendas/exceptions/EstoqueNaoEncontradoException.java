package br.edu.ucsal.ms_vendas.exceptions;

public class EstoqueNaoEncontradoException extends RecursoNaoEncontradoException {
    public EstoqueNaoEncontradoException(String message) {
        super(message);
    }
}
