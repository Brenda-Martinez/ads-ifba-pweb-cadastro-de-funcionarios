package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper.DepartamentoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository.DepartamentoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.CampoDisponivelVazioException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.GerenteJaAssociadoException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.RemocaoDepartamentoGerenteException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    
    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    private final FuncionarioService funcionarioService;

    public DepartamentoServiceImpl(DepartamentoRepository repository, DepartamentoMapper mapper,
        @Lazy FuncionarioService funcionarioService){
        
        this.repository = repository;
        this.mapper = mapper;
        this.funcionarioService = funcionarioService;
    }
    
    @Override
    public Optional<DepartamentoDTO> create(DepartamentoDTO departamentoDTO) {

        var optNome = repository.findByNome(departamentoDTO.getNome());
        if (optNome.isPresent()) {
            throw new IllegalArgumentException("Departamento já existe.");
        }

        List<FuncionarioDTO> funcionarios = funcionarioService.findAll();

        if(!funcionarios.isEmpty() && departamentoDTO.getGerenteId() == null){
            throw new CampoDisponivelVazioException("O campo gerente não pode ser vazio, " +
            "já existem funcionários disponíveis no sistema.");
        }

        var gerenteId = departamentoDTO.getGerenteId();
        if (gerenteId != null) {
            var departamentoGerente = repository.findByGerenteId(gerenteId);
    
            if (departamentoGerente.isPresent() && !departamentoGerente.get().getId().equals(departamentoDTO.getId())) {
                String gerenteNome = funcionarioService.findById(gerenteId).get().getNome();
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
            System.out.println("Ele passou aquiiiiiiiiiiiiiiii");
            throw new EntityNotFoundException("Departamento não foi encontrado no sistema.");
        }

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        var departamentoDTO = findById(id).get();

        funcionarioService.findAll().forEach( (fun) -> {
            if(fun.getDepartamento().getId() == departamentoDTO.getId()){
                throw new RemocaoDepartamentoGerenteException("Não foi possível remover o departamento "
                + departamentoDTO.getNome() + " pois o(a) gerente " + fun.getNome() + " está associado(a) à ele.");
            }
        });
        
        repository.deleteById(id);
    }

    @Override
    public Optional<DepartamentoDTO> update(DepartamentoDTO departamentoDTO) {
        
        findById(departamentoDTO.getId());
    
        var gerenteId = departamentoDTO.getGerenteId();
    
        if (gerenteId != null) {
            var departamentoGerente = repository.findByGerenteId(gerenteId);
    
            if (departamentoGerente.isPresent() && !departamentoGerente.get().getId().equals(departamentoDTO.getId())) {
                String gerenteNome = funcionarioService.findById(gerenteId).get().getNome();
                String departamentoNome = departamentoGerente.get().getNome();
                throw new GerenteJaAssociadoException(gerenteNome, departamentoNome);
            }
        }

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
    }
    
}
