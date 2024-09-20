package edu.ifbasaj.pweb.cadastro_de_funcionarios.service.funcionario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto.FuncionarioDTO;

public interface FuncionarioService {

    Optional<FuncionarioDTO> create(FuncionarioDTO f);

    Optional<FuncionarioDTO> update(FuncionarioDTO f);

    void remove(UUID id);

    List<FuncionarioDTO> findAll();

    FuncionarioDTO findById(UUID id);

}
