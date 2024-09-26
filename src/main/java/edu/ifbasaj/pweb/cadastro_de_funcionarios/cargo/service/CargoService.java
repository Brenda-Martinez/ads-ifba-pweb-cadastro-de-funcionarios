package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service;

import java.util.List;
import java.util.Optional;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;

public interface CargoService {

    Optional<CargoDTO> create(CargoDTO c);

    Optional<CargoDTO> update(CargoDTO c);

    void remove(Long id);

    List<CargoDTO> findAll();

    Optional<CargoDTO> findById(Long id);

}
