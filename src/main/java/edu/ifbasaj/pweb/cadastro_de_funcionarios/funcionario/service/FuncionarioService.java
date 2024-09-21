package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;

public interface FuncionarioService {

    Optional<FuncionarioDTO> create(FuncionarioDTO f);

    Optional<FuncionarioDTO> update(FuncionarioDTO f);

    void remove(UUID id);

    List<FuncionarioDTO> findAll();

    Optional<FuncionarioDTO> findById(UUID id);

}
