package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, UUID>{
    
    Optional<Departamento> findByNome(String nome);

    Optional<Departamento> findByGerenteId(UUID gerenteId);

}
