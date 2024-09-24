package edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity.Cargo;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.service.CargoService;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.dto.DepartamentoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.model.entity.Departamento;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.mapper.DepartamentoMapper;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.dto.FuncionarioDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.funcionario.model.entity.Funcionario;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.departamento.repository.DepartamentoRepository;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.repository.CargoRepository;

@Mapper(componentModel = "spring")
public abstract class FuncionarioMapper {
    
    @Autowired
    private CargoService cargoService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    @Mapping(target = "cargo", expression = "java( toCargoDTO(source.getCargo()) )")
    @Mapping(target = "departamento", expression = "java( toDepartamentoDTO(source.getDepartamento()) )")
    @Mapping(target = "cargoId", source = "source.cargo.id")
    @Mapping(target = "departamentoId", source = "source.departamento.id")
    public abstract FuncionarioDTO toFuncionarioDTO(Funcionario source);
    
    @Mapping(target = "cargo", expression = "java( toCargo(source.getCargoId()))")
    @Mapping(target = "departamento", expression = "java( toDepartamento(source.getDepartamentoId()))")
    public abstract Funcionario toFuncionario(FuncionarioDTO source);

    public DepartamentoDTO toDepartamentoDTO(Departamento departamento){

        if(departamento != null){
            return departamentoMapper.toDepartamentoDTO(departamento);
        }
        return null;
    }

    public CargoDTO toCargoDTO(Cargo cargo){

        if(cargo != null){
            return cargoService.findById(cargo.getId()).get();
        }
        return null;
    }

    public Departamento toDepartamento(UUID id){

        if(id != null){
            return departamentoRepository.findById(id).get();
        }
        return null;
    }

    public Cargo toCargo(UUID id){

        if(id != null){
            return cargoRepository.findById(id).get();
        }
        return null;
    }
}
