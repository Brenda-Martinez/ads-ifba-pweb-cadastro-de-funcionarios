package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service;

import java.util.List;
import java.util.Optional;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;

public interface FuncionarioService {

    Optional<FuncionarioDTO> create(FuncionarioDTO f);

    Optional<FuncionarioDTO> update(FuncionarioDTO f);

    void remove(Long id);

    List<FuncionarioDTO> findAll();

    Optional<FuncionarioDTO> findById(Long id);

}
