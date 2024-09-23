package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.mapper.CargoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity.Cargo;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.CargoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper.DepartamentoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.service.DepartamentoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;

@Mapper(componentModel = "spring")
public abstract class FuncionarioMapper {
    
    @Autowired
    private CargoService cargoService;

    @Autowired
    private CargoMapper cargoMapper;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private DepartamentoMapper departamentoMapper;
    
    @Mapping(target = "cargoId", expression = "java( findCargoId(source) )")
    @Mapping(target = "cargoNome", expression = "java( findCargoNome(source) )")
    @Mapping(target = "departamentoId", expression = "java( findDepartamentoId(source) )")
    @Mapping(target = "departamentoNome", expression = "java( findDepartamentoNome(source) )")
    public abstract FuncionarioDTO toFuncionarioDTO(Funcionario source);
    
    @Mapping(target = "cargo", expression = "java(findCargoById(source.getCargoId()))")
    @Mapping(target = "departamento", expression = "java(findDepartamentoById(source.getCargoId()))")
    public abstract Funcionario toFuncionario(FuncionarioDTO source);

    public UUID findCargoId(Funcionario source){
        
        if(source.getCargo() != null) {
            return source.getCargo().getId();
        }
        return null; 
    }

    public String findCargoNome(Funcionario source){
        
        if(source.getCargo() != null) {
            return source.getCargo().getNome();
        } 
        return null;
    }

    public UUID findDepartamentoId(Funcionario source){
        
        if(source.getDepartamento() != null) {
            return source.getCargo().getId();
        }
        return null;
    }

    public String findDepartamentoNome(Funcionario source){
        
        if(source.getDepartamento() != null) {
            return source.getCargo().getNome();
        }
        return null;
    }


    public Cargo findCargoById(UUID id){
        try {
            var cargoDTO = cargoService.findById(id).get();
            return cargoMapper.toCargo(cargoDTO);
        } catch ( Exception e) {
            return null;
        }
    }

    public Departamento findDepartamentoById(UUID id){
        try {
            var departamentoDTO = departamentoService.findById(id).get();
            return departamentoMapper.toDepartamento(departamentoDTO);
        } catch ( Exception e) {
            return null;
        }
    }
}
