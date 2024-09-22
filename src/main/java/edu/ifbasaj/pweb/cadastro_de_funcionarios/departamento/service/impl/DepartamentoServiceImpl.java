package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper.DepartamentoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository.DepartamentoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.GerenteJaAssociadoException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {
    
    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    private final FuncionarioRepository funcionarioRepository;
    
    @Override
    public Optional<DepartamentoDTO> create(DepartamentoDTO departamentoDTO) {
        var optNome = repository.findByNome(departamentoDTO.getNome());
        if (optNome.isPresent()) {
            throw new IllegalArgumentException("Departamento já existe.");
        }
        UUID gerenteId = departamentoDTO.getGerenteId();

        if (gerenteId != null) {
            Optional<Departamento> departamentoGerente = repository.findByGerenteId(gerenteId);
            
            if (departamentoGerente.isPresent()) {
                String gerenteNome = funcionarioRepository.findById(gerenteId)
                        .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"))
                        .getNome();
                String departamentoNome = departamentoGerente.get().getNome();
                throw new GerenteJaAssociadoException(gerenteNome, departamentoNome);
            }
        }

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
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
        
        var departamentoCorrespondente = findById(departamentoDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
    
        UUID gerenteId = departamentoDTO.getGerenteId();
    
        if (gerenteId != null) {
            Optional<Departamento> departamentoGerente = repository.findByGerenteId(gerenteId);
    
            if (departamentoGerente.isPresent() && !departamentoGerente.get().getId().equals(departamentoDTO.getId())) {
                String gerenteNome = funcionarioRepository.findById(gerenteId)
                        .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"))
                        .getNome();
                String departamentoNome = departamentoGerente.get().getNome();
                throw new GerenteJaAssociadoException(gerenteNome, departamentoNome);
            }
        }

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
    }
    
}
