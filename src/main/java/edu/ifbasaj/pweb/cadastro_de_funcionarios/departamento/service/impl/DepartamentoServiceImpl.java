package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper.DepartamentoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository.DepartamentoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {
    
    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    
    @SuppressWarnings("unused")
    @Override
    public Optional<DepartamentoDTO> create(DepartamentoDTO departamentoDTO) {
        
        var optNome = repository.findByNome(departamentoDTO.getNome());

        if(optNome.isPresent()){
            throw new IllegalArgumentException("Departamento já existe.");
        }

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        if(departamentoSalvo != null){
            return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
        }

        return Optional.empty();
    }

    @Override
    public List<DepartamentoDTO> findAll() {
        
        var entityList = repository.findAll();
        
        List<DepartamentoDTO> dtoList = new ArrayList<>();

        entityList.forEach( (entity) -> {
            dtoList.add(mapper.toDepartamentoDTO(entity));
        });

        return dtoList;
    }

    @Override
    public Optional<DepartamentoDTO> findById(UUID id) {
        
        var departamentoSalvo = repository.findById(id);

        if (departamentoSalvo.isEmpty()){
            throw new EntityNotFoundException();
        }

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        findById(id);
        
        repository.deleteById(id);
    }

    @Override
    public Optional<DepartamentoDTO> update(DepartamentoDTO departamentoDTO) {
        
        @SuppressWarnings("unused")
        var departamentoCorrespondente = findById(departamentoDTO.getId()).get();

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
    }
    
}
