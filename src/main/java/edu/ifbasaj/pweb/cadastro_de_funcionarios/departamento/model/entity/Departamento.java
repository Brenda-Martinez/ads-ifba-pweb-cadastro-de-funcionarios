package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity;

import java.util.List;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionarios;

    @OneToOne
    @JoinColumn(name = "gerente_id")
    private Funcionario gerente;
}
