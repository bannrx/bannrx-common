package com.bannrx.common.mappers;

import com.bannrx.common.dtos.DocumentDto;
import com.bannrx.common.persistence.entities.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDto toDto(final Document document);

    @Mapping(target = "ETag", source = "eTag")
    Document toEntity(
            final DocumentDto dto,
            final String eTag,
            final String bucket
    );

}
