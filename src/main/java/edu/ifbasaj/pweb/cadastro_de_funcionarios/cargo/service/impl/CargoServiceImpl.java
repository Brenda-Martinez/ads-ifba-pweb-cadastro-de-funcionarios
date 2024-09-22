package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.mapper.CargoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.repository.CargoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.CargoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    
    private final CargoRepository repository;
    private final CargoMapper mapper;
    
    @SuppressWarnings("unused")
    @Override
    public Optional<CargoDTO> create(CargoDTO cargoDTO) {
        
        var optNome = repository.findByNome(cargoDTO.getNome());

        if(optNome.isPresent()){
            throw new IllegalArgumentException("Cargo já existe.");
        }

        var cargoSalvo = repository.save(mapper.toCargo(cargoDTO));

        if(cargoSalvo != null){
            return Optional.of(mapper.toCargoDTO(cargoSalvo));
        }

        return Optional.empty();
    }

    @Override
    public List<CargoDTO> findAll() {
        
        var entityList = repository.findAll();
        
        List<CargoDTO> dtoList = new ArrayList<>();

        entityList.forEach( (entity) -> {
            dtoList.add(mapper.toCargoDTO(entity));
        });

        return dtoList;
    }

    @Override
    public Optional<CargoDTO> findById(UUID id) {
        
        var cargoSalvo = repository.findById(id);

        if (cargoSalvo.isEmpty()){
            throw new EntityNotFoundException();
        }

        return Optional.of(mapper.toCargoDTO(cargoSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        findById(id);
        
        repository.deleteById(id);
    }

    @Override
    public Optional<CargoDTO> update(CargoDTO cargoDTO) {
        
        @SuppressWarnings("unused")
        var cargoCorrespondente = findById(cargoDTO.getId()).get();

        var cargoSalvo = repository.save(mapper.toCargo(cargoDTO));

        return Optional.of(mapper.toCargoDTO(cargoSalvo));
    }
    
    

}
