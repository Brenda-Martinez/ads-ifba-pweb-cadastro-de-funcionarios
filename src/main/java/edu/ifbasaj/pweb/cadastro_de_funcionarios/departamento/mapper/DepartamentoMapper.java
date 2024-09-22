package edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.repository.FuncionarioRepository;

@Mapper(componentModel = "spring")
public abstract class DepartamentoMapper {
    
    @Autowired
    protected FuncionarioRepository funcionarioRepository;

    @Mapping(source = "gerente.id", target = "gerenteId")  // mapeia o ID do gerente para o DTO
    public abstract DepartamentoDTO toDepartamentoDTO(Departamento source);

    @Mapping(target = "gerente", ignore = true)  // ignora a associação gerente durante o mapeamento
    public abstract Departamento toDepartamento(DepartamentoDTO source);

    @AfterMapping
    protected void setGerente(DepartamentoDTO dto, @MappingTarget Departamento departamento) {
        if (dto.getGerenteId() != null) {
            Funcionario gerente = funcionarioRepository.findById(dto.getGerenteId())
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
            departamento.setGerente(gerente);
        } else {
            departamento.setGerente(null);
        }
    }

}
