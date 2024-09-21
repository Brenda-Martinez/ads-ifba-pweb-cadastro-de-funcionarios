package edu.ifbasaj.pweb.cadastro_de_funcionarios.service.funcionario.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.mapper.FuncionarioMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.repository.FuncionarioRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.service.funcionario.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {
    
    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;
    
    @Override
    public Optional<FuncionarioDTO> create(FuncionarioDTO funcionarioDTO) {
        
        var optEmail = repository.findByEmail(funcionarioDTO.getEmail());
        var optCpf = repository.findByCpf(funcionarioDTO.getCpf());

        if(optEmail.isPresent()){
            throw new IllegalArgumentException("O email já está sendo utilizado.");
        }

        if(optCpf.isPresent()){
            throw new IllegalArgumentException("O cpf já está sendo utilizado.");
        }

        var funcionarioSalvo = repository.save(mapper.toFuncionario(funcionarioDTO));

        if(funcionarioSalvo != null){
            return Optional.of(mapper.toFuncionarioDTO(funcionarioSalvo));
        }

        return Optional.empty();
    }

    @Override
    public List<FuncionarioDTO> findAll() {
        
        var entityList = repository.findAll();
        
        List<FuncionarioDTO> dtoList = new ArrayList<>();

        entityList.forEach( (entity) -> {
            dtoList.add(mapper.toFuncionarioDTO(entity));
        });

        return dtoList;
    }

    @Override
    public Optional<FuncionarioDTO> findById(UUID id) {
        
        var funcionarioSalvo = repository.findById(id);

        if (funcionarioSalvo.isEmpty()){
            throw new EntityNotFoundException();
        }

        return Optional.of(mapper.toFuncionarioDTO(funcionarioSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        findById(id);
        
        repository.deleteById(id);
    }

    @Override
    public Optional<FuncionarioDTO> update(FuncionarioDTO funcionarioDTO) {
        
        var funcionarioCorrespondente = findById(funcionarioDTO.getId()).get();

        if(!funcionarioCorrespondente.getCpf().equals(funcionarioDTO.getCpf())){
            throw new IllegalArgumentException("Não é possível editar o CPF");
        }

        var funcionarioSalvo = repository.save(mapper.toFuncionario(funcionarioDTO));

        return Optional.of(mapper.toFuncionarioDTO(funcionarioSalvo));
    }
    
    

}
