package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity;

import java.util.List;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;

    @OneToOne(fetch = FetchType.LAZY)
    private Funcionario gerente;
}
