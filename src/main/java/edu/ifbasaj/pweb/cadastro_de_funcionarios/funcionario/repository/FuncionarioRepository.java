package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID>{
    
    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByCpf(String cpf);
    
}
