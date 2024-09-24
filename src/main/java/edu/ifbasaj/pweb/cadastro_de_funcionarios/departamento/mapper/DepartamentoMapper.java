package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;

@Mapper(componentModel = "spring")
public abstract class DepartamentoMapper {
    
    @Lazy
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Mapping(target = "gerenteId", source = "source.gerente.id")
    @Mapping(target = "gerenteNome", source = "source.gerente.nome")
    public abstract DepartamentoDTO toDepartamentoDTO(Departamento source);

    @Mapping(target = "gerente", expression = "java( toFuncionario(source.getGerenteId()) )")
    @Mapping(target = "funcionarios", ignore = true)
    public abstract Departamento toDepartamento(DepartamentoDTO source);

    public Funcionario toFuncionario(UUID id){

        if(id != null){
            return funcionarioRepository.findById(id).get();          
        }
        return null;
    }

}
