package edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {
    
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
}
