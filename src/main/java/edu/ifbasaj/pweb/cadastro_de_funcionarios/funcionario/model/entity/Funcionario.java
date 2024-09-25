package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity;

import java.time.LocalDate;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity.Cargo;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String cpf;

    private String email;

    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    private Double salario;

    private Boolean ativo;

    @ManyToOne
    private Cargo cargo;

    @ManyToOne
    private Departamento departamento;
    
    @Column(name = "endereco_id")
    private Long enderecoId;
}
