package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper;

import org.mapstruct.Mapper;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;

@Mapper(componentModel = "spring")
public abstract class DepartamentoMapper {
    
    public abstract DepartamentoDTO toDepartamentoDTO(Departamento source);
    
    public abstract Departamento toDepartamento(DepartamentoDTO source);

}
