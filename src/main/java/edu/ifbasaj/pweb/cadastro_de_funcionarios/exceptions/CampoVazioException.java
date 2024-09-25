package edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions;

public class CampoVazioException extends RuntimeException {
    public CampoVazioException(String msg) {
        super(msg);
    }
}