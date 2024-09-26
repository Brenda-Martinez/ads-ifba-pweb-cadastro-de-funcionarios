package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    
    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByCpf(String cpf);
    
}
