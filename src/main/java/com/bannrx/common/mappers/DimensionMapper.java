package com.bannrx.common.mappers;

import com.bannrx.common.dtos.device.DimensionDto;
import com.bannrx.common.persistence.entities.Dimension;
import com.bannrx.common.searchCriteria.DimensionSearchCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DimensionMapper {
    DimensionMapper INSTANCE = Mappers.getMapper(DimensionMapper.class);

    DimensionDto toDto(final Dimension dimension);

    @Mapping(target = "perPage", constant = "20")
    @Mapping(target = "pageNo", constant = "1")
    @Mapping(target = "sortBy", constant = "createdAt")
    @Mapping(target = "sortDirection", constant = "ASC")
    DimensionSearchCriteria toSearchCriteria(final DimensionDto dto);
    Dimension toEntity(final DimensionSearchCriteria dto);

}
