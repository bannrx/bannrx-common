package com.bannrx.common.mappers;

import com.bannrx.common.dtos.campaign.CampaignDto;
import com.bannrx.common.enums.Phase;
import com.bannrx.common.persistence.entities.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CampaignMapper {
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    Campaign toEntity(final CampaignDto campaignDto, final Phase phase);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(CampaignDto dto, @MappingTarget Campaign entity);

    CampaignDto toDto(final Campaign campaign);
}
