package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service;

import java.util.List;
import java.util.Optional;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;

public interface DepartamentoService {

    Optional<DepartamentoDTO> create(DepartamentoDTO c);

    Optional<DepartamentoDTO> update(DepartamentoDTO c);

    void remove(Long id);

    List<DepartamentoDTO> findAll();

    Optional<DepartamentoDTO> findById(Long id);

}
