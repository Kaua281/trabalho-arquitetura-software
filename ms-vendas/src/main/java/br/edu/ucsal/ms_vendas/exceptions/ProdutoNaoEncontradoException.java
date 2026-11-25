package br.edu.ucsal.ms_vendas.exceptions;

public class ProdutoNaoEncontradoException extends RecursoNaoEncontradoException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
