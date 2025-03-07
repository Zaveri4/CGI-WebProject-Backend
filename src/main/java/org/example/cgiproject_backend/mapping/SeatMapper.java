package org.example.cgiproject_backend.mapping;

import org.example.cgiproject_backend.dto.SeatDto;
import org.example.cgiproject_backend.entity.SeatEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    List<SeatDto> toDtoList(List<SeatEntity> entities);
    SeatDto toDto(SeatEntity entity);
}
