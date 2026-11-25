package br.edu.ucsal.ms_vendas.exceptions;

public class VendaNaoEncontradaException extends RecursoNaoEncontradoException {
    public VendaNaoEncontradaException(String message) {
        super(message);
    }
}
