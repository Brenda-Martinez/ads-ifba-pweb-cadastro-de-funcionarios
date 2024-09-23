package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;

@Mapper(componentModel = "spring")
public abstract class DepartamentoMapper {
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Mapping(target = "gerenteId", expression = "java( findGerenteId(source) )")
    @Mapping(target = "gerenteNome", expression = "java( findGerenteNome(source) )")
    public abstract DepartamentoDTO toDepartamentoDTO(Departamento source);

    @Mapping(target = "gerente", expression = "java(findGerenteById(source.getGerenteId()))")
    @Mapping(target = "funcionarios", ignore = true)
    public abstract Departamento toDepartamento(DepartamentoDTO source);

    public UUID findGerenteId(Departamento source){
        
        if(source.getGerente() != null) {
            return source.getGerente().getId();
        }
        return null; 
    }

    public String findGerenteNome(Departamento source){
        
        if(source.getGerente() != null) {
            return source.getGerente().getNome();
        } 
        return null;
    }

    public Funcionario findGerenteById(UUID id){
        try {
            var gerenteDTO = funcionarioRepository.findById(id).get();
            return gerenteDTO;
        } catch ( Exception e) {
            return null;
        }
    }

}
