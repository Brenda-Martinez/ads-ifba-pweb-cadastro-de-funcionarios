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
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.CampoVazioException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.GerenteJaAssociadoException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.utils.ServiceUtils;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.EntidadeAssociadaException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {
    
    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    private final ServiceUtils utils;
    
    @Override
    public Optional<DepartamentoDTO> create(DepartamentoDTO departamentoDTO) {
 
        if (nomeJaExiste(departamentoDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um departamento com esse nome.");
        }

        if(utils.entidadesFuncionarioExistem() && departamentoDTO.getGerenteId() == null){
            throw new CampoVazioException("O campo gerente não pode ser vazio, " +
            "já existem funcionários disponíveis no sistema.");
        }

        var depGerenciado = utils.funcionarioGerenciaDeparamento(departamentoDTO.getGerenteId());

        if (depGerenciado.isPresent()) {
            throw new GerenteJaAssociadoException(depGerenciado.get().getGerente().getNome(),
            depGerenciado.get().getNome()); 
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
            throw new EntityNotFoundException("Departamento não foi encontrado no sistema.");
        }

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        findById(id).get();

        var funcionarios = utils.funcionariosEmDepartamento(id);
        
        if(!funcionarios.isEmpty()){
            throw new EntidadeAssociadaException("Não foi possível remover o departamento pois estes funcionários "
                + funcionarios + " estão associados à ele.");
        }
        
        repository.deleteById(id);
    }

    @Override
    public Optional<DepartamentoDTO> update(DepartamentoDTO departamentoDTO) {
        
        findById(departamentoDTO.getId());
    
        var depGerenciado = utils.funcionarioGerenciaDeparamento(departamentoDTO.getGerenteId());

        if (depGerenciado.isPresent()) {
            throw new GerenteJaAssociadoException(depGerenciado.get().getGerente().getNome(),
            depGerenciado.get().getNome()); 
        }

        var departamentoSalvo = repository.save(mapper.toDepartamento(departamentoDTO));

        return Optional.of(mapper.toDepartamentoDTO(departamentoSalvo));
    }

    public Boolean nomeJaExiste(String nome){
        var optCpf = repository.findByNome(nome);
        if(optCpf.isPresent()) return true;
        return false;
    }
    
}
