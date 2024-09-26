package edu.ifbasaj.pweb.cadastro_de_funcionarios.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.repository.CargoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository.DepartamentoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceUtils {
    
    private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoRepository departamentoRepository;
    private final CargoRepository cargoRepository;

    public Boolean entidadesCargoExistem(){
        return (!cargoRepository.findAll().isEmpty()) ? true : false;
    }

    public Boolean entidadesDepartamentoExistem(){ 
        return (!departamentoRepository.findAll().isEmpty()) ? true : false;
    }

    public Boolean entidadesFuncionarioExistem(){
        return (!funcionarioRepository.findAll().isEmpty()) ? true : false;
    }

    public Boolean salarioMenorQueBase(Long cargoId, Double salario){
        var cargo = cargoRepository.findById(cargoId).get();
        return (salario < cargo.getSalarioBase()) ? true : false;
    }

    public List<String> funcionariosEmDepartamento(Long id){
        return funcionarioRepository.findAll().stream()
        .filter(fun -> fun.getDepartamento() != null)
        .filter(fun -> fun.getDepartamento().getId().equals(id))
        .map(fun -> fun.getNome())
        .collect(Collectors.toList());
    }

    public List<String> funcionariosComCargo(Long id){
        return funcionarioRepository.findAll().stream()
        .filter(fun -> fun.getCargo() != null)
        .filter(fun -> fun.getCargo().getId().equals(id))
        .map(fun -> fun.getNome())
        .collect(Collectors.toList());
    }

    public Optional<Departamento> funcionarioGerenciaDepartamento(Long funcionarioId){
        var departamento = departamentoRepository.findByGerenteId(funcionarioId);
        if(departamento.isPresent()){
            return departamento;
        }
        return Optional.empty();
    }

}
