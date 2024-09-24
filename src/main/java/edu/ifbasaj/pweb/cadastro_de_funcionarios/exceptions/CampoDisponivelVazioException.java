package edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions;

public class CampoDisponivelVazioException extends RuntimeException {
    public CampoDisponivelVazioException(String msg) {
        super(msg);
    }
}