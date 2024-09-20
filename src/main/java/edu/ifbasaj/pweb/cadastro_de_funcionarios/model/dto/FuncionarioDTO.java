package edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto;

import java.time.LocalDate;
import java.util.UUID;

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
    private String cpf;

    @NotEmpty(message = "O email não pode ser vazio.")
    @Email(message = "O email deve ser válido, ex: exemplo@dominio.com")
    private String email;

    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$",
    message = "O telefone deve ser válido, ex: (00) 00000-0000")
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser vazia.")
    @Past(message = "A data de nascimento precisa ser válida.")
    private LocalDate dataNascimento;

    @NotNull(message = "A data de admissão não pode ser vazia.")
    @Past(message = "A data de admissão precisa ser válida.")
    private LocalDate dataAdmissao;

    @NotNull(message = "O salário não pode ser vazio.")
    private Double salario;

    @NotNull(message = "O status de atividade não pode ser vazio.")
    private Boolean ativo;

    /* @NotEmpty(message = "O cargo não pode ser vazio")
    private Cargo cargo;

    @NotEmpty(message = "O departamento pode ser vazio")
    private Departamento departamento;

    private Endereco endereco; */
}
