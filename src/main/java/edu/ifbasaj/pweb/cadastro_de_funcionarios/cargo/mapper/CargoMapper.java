package edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.dto.CargoDTO;
import edu.ifbasaj.pweb.cadastro_de_funcionarios.cargo.model.entity.Cargo;

@Mapper(componentModel = "spring")
public abstract class CargoMapper {
    
    public abstract CargoDTO toCargoDTO(Cargo source);
    
    @Mapping(target = "funcionarios", ignore = true)
    public abstract Cargo toCargo(CargoDTO source);
}