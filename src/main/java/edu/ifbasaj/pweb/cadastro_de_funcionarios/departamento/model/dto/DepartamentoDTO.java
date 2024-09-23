package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartamentoDTO {
    
    UUID id;

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome deve conter de 3 a 100 caracteres.")
    private String nome;

    private String descricao;

    private UUID gerenteId;
    private String gerenteNome;
}
