package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CargoDTO {
    
    Long id;

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O nome deve conter de 3 a 100 caracteres.")
    private String nome;

    private String descricao;

    @NotNull(message = "O salário não pode ser vazio.")
    private Double salarioBase;
}
