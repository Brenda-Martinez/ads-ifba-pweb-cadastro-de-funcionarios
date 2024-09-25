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
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.EntidadeAssociadaException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.utils.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
    
    private final CargoRepository repository;
    private final CargoMapper mapper;
    private final ServiceUtils utils;
    
    @Override
    public Optional<CargoDTO> create(CargoDTO cargoDTO) {
        
        if (nomeJaExiste(cargoDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um cargo com esse nome.");
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
            throw new EntityNotFoundException("Cargo não foi encontrado no sistema.");
        }

        return Optional.of(mapper.toCargoDTO(cargoSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        findById(id);
        
        var funcionarios = utils.funcionariosComCargo(id);
        
        if(!funcionarios.isEmpty()){
            throw new EntidadeAssociadaException("Não foi possível remover o cargo pois funcionários "
                + funcionarios + " estão associados à ele.");
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<CargoDTO> update(CargoDTO cargoDTO) {
        
        findById(cargoDTO.getId());

        var cargoSalvo = repository.save(mapper.toCargo(cargoDTO));

        return Optional.of(mapper.toCargoDTO(cargoSalvo));
    }
    
    
    public Boolean nomeJaExiste(String nome){
        var optCpf = repository.findByNome(nome);
        if(optCpf.isPresent()) return true;
        return false;
    }

}
