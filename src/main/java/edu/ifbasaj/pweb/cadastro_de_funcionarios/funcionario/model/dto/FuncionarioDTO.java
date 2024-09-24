package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto;

import java.time.LocalDate;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FuncionarioDTO {
    
    UUID id;

    @NotEmpty(message = "O nome não pode ser vazio.")
    @Size(min = 3, max = 50, message = "O nome deve conter de 3 a 50 caracteres.")
    private String nome;

    @NotEmpty(message = "O cpf não pode ser vazio.")
    @Pattern(regexp= "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
        message = "O CPF digitado não é válido.")
    private String cpf;

    @NotEmpty(message = "O email não pode ser vazio.")
    @Email(message = "O E-mail digitado não é válido.")
    private String email;

    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$",
    message = "O telefone digitado não é válido.")
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser vazia.")
    @Past(message = "A data de nascimento digitada não é válida.")
    private LocalDate dataNascimento;

    @NotNull(message = "A data de admissão não pode ser vazia.")
    @Past(message = "A data de Admissão digitado não é válida.")
    private LocalDate dataAdmissao;

    @NotNull(message = "O salário não pode ser vazio.")
    private Double salario;

    @NotNull(message = "O status de atividade não pode ser vazio.")
    private Boolean ativo;

    private UUID cargoId;
    private CargoDTO cargo;
    
    private UUID departamentoId;
    private DepartamentoDTO departamento;


    /* private Endereco endereco; */
}
