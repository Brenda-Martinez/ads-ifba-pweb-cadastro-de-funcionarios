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
import edu.ifbasaj.pweb.cadastro_de_funcionarios.utils.ServiceUtils;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.endereco.service.EnderecoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.CampoVazioException;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.exceptions.EntidadeAssociadaException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {
    
    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;
    private final ServiceUtils utils;
    private final EnderecoService enderecoService;
    
    @Override
    public Optional<FuncionarioDTO> create(FuncionarioDTO funcionarioDTO) {

        if(emailJaExiste(funcionarioDTO.getEmail())){
            throw new IllegalArgumentException("O email já está sendo utilizado.");
        }

        if(cpfJaExiste(funcionarioDTO.getCpf())){
            throw new IllegalArgumentException("O cpf já está sendo utilizado.");
        }

        if(funcionarioDTO.getCargoId() != null){
            if(utils.salarioMenorQueBase(funcionarioDTO.getCargoId(), funcionarioDTO.getSalario())){
                throw new IllegalArgumentException("O salário do funcionário não pode ser menor que o salário base de seu cargo.");
            }
        }

        if(utils.entidadesCargoExistem() && funcionarioDTO.getCargoId() == null){
            throw new CampoVazioException("O campo cargo não pode ser vazio, " +
            "já existem cargos disponíveis no sistema.");
        }

        if(utils.entidadesDepartamentoExistem() && funcionarioDTO.getDepartamentoId() == null){
            throw new CampoVazioException("O campo departamento não pode ser vazio, " +
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

            var dto = mapper.toFuncionarioDTO(entity);

            if(dto.getEnderecoId() != null){
                var response = enderecoService.findById(dto.getEnderecoId());
                dto.setEndereco(response.block());
            }
            
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Override
    public Optional<FuncionarioDTO> findById(UUID id) {
        
        var funcionarioSalvo = repository.findById(id);

        if (funcionarioSalvo.isEmpty()){
            throw new EntityNotFoundException("Não foi possível encontrar este usuário no sistema.");
        }

        var dto = mapper.toFuncionarioDTO(funcionarioSalvo.get());

        if(dto.getEnderecoId() != null){
            var response = enderecoService.findById(dto.getEnderecoId());
            dto.setEndereco(response.block());
        }

        return Optional.of(dto);
    }

    @Override
    public void remove(UUID id) {
        
        var funcionarioDTO = findById(id).get();
        var departamento = utils.funcionarioGerenciaDeparamento(id);

        if(departamento.isPresent()){
            throw new EntidadeAssociadaException("Não foi possível remover " + funcionarioDTO.getNome()
                + " pois ele(a) é gerente do departamento " + departamento.get().getNome() + ".");
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<FuncionarioDTO> update(FuncionarioDTO funcionarioDTO) {
        
        var funcionarioCorrespondente = findById(funcionarioDTO.getId()).get();
        
        if(!funcionarioCorrespondente.getCpf().equals(funcionarioDTO.getCpf())){
            throw new IllegalArgumentException("Não é possível editar o CPF");
        }

        if(funcionarioDTO.getCargoId() != null){
            if(utils.salarioMenorQueBase(funcionarioDTO.getCargoId(), funcionarioDTO.getSalario())){
                throw new IllegalArgumentException("O salário do funcionário não pode ser menor que o salário base de seu cargo.");
            }
        }

        var funcionarioSalvo = repository.save(mapper.toFuncionario(funcionarioDTO));
        
        return Optional.of(mapper.toFuncionarioDTO(funcionarioSalvo));
    }
    
    public Boolean emailJaExiste(String email){
        var optEmail = repository.findByEmail(email);
        if(optEmail.isPresent()) return true;
        return false;
    }

    public Boolean cpfJaExiste(String cpf){
        var optCpf = repository.findByCpf(cpf);
        if(optCpf.isPresent()) return true;
        return false;
    }

}
