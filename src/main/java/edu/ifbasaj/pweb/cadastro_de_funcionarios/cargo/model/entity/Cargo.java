package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity;

import java.util.List;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descricao;

    @Column(name = "base_salario")
    private Double salarioBase;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios;
}
