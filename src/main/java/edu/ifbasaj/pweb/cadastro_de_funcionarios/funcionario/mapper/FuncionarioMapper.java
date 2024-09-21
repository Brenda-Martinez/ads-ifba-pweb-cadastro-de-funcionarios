package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.mapper;

import org.mapstruct.Mapper;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;

@Mapper(componentModel = "spring")
public abstract class FuncionarioMapper {
    
    public abstract FuncionarioDTO toFuncionarioDTO(Funcionario source);
    
    public abstract Funcionario toFuncionario(FuncionarioDTO source);

}
