package br.edu.ucsal.ms_estoque.exception;

public class EstoqueNaoEncontradoException extends RuntimeException {

    public EstoqueNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}