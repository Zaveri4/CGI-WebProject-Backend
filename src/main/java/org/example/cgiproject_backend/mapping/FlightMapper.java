package org.example.cgiproject_backend.mapping;

import org.example.cgiproject_backend.dto.FlightDto;
import org.example.cgiproject_backend.entity.FlightEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDto toDto(FlightEntity entity);
    FlightEntity toEntity(FlightDto dto);
}
