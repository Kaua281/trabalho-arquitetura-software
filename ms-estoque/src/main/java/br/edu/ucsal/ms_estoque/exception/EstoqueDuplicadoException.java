package br.edu.ucsal.ms_estoque.exception;

public class EstoqueDuplicadoException extends RuntimeException {

    public EstoqueDuplicadoException(String mensagem) {
        super(mensagem);
    }
}