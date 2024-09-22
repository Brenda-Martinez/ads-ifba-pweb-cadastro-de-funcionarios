package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;

public interface CargoService {

    Optional<CargoDTO> create(CargoDTO c);

    Optional<CargoDTO> update(CargoDTO c);

    void remove(UUID id);

    List<CargoDTO> findAll();

    Optional<CargoDTO> findById(UUID id);

}
