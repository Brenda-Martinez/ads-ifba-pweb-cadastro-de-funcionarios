package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, UUID>{
    
    Optional<Cargo> findByNome(String nome);

}
