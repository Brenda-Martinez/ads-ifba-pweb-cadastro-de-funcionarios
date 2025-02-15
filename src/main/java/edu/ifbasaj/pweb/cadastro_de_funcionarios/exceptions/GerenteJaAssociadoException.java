package edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions;

public class GerenteJaAssociadoException extends RuntimeException {
    public GerenteJaAssociadoException(String gerenteNome, String departamentoNome) {
        super("O(a) funcionário(a) " + gerenteNome + " já é gerente do departamento " + departamentoNome);
    }
}
