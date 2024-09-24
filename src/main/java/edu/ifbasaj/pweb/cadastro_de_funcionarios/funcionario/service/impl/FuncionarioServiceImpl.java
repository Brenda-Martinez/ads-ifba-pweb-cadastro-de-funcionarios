package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.mapper.FuncionarioMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.service.FuncionarioService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.CargoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.CampoDisponivelVazioException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.RemocaoDepartamentoGerenteException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {
    
    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;
    private final DepartamentoService departamentoService;
    private final CargoService cargoService;
    
    @Override
    public Optional<FuncionarioDTO> create(FuncionarioDTO funcionarioDTO) {
        
        var optEmail = repository.findByEmail(funcionarioDTO.getEmail());
        var optCpf = repository.findByCpf(funcionarioDTO.getCpf());

        List<CargoDTO> cargos = cargoService.findAll();
        List<DepartamentoDTO> departamentos = departamentoService.findAll();

        if(optEmail.isPresent()){
            throw new IllegalArgumentException("O email já está sendo utilizado.");
        }

        if(optCpf.isPresent()){
            throw new IllegalArgumentException("O cpf já está sendo utilizado.");
        }

        if(!cargos.isEmpty() && funcionarioDTO.getCargoId() == null){
            throw new CampoDisponivelVazioException("O campo cargo não pode ser vazio, " +
            "já existem cargos disponíveis no sistema.");
        }

        if(!departamentos.isEmpty() && funcionarioDTO.getDepartamentoId() == null){
            throw new CampoDisponivelVazioException("O campo departamento não pode ser vazio, " +
            "já existem departamentos disponíveis no sistema.");
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
            throw new EntityNotFoundException("Não foi possível encontrar este usuário no sistema.");
        }

        return Optional.of(mapper.toFuncionarioDTO(funcionarioSalvo.get()));
    }

    @Override
    public void remove(UUID id) {
        
        var funcionarioDTO = findById(id).get();

        departamentoService.findAll().forEach( (dep) -> {
            if(dep.getGerenteId() == funcionarioDTO.getId()){
                throw new RemocaoDepartamentoGerenteException("Não foi possível remover "
                + funcionarioDTO.getNome() + " pois ele(a) é gerente do departamento " + dep.getNome() + ".");
            }
        });

        
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
