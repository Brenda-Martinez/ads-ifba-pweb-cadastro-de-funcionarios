package edu.ifbasaj.pweb.cadastro_de_funcionarios.mapper;

import org.mapstruct.Mapper;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.model.entity.Funcionario;

@Mapper(componentModel = "spring")
public abstract class FuncionarioMapper {
    
    public abstract FuncionarioDTO toFuncionarioDTO(Funcionario source);
    
    public abstract Funcionario toFuncionario(FuncionarioDTO source);

}
