package com.bannrx.common.service;

import com.bannrx.common.dtos.device.DimensionDto;
import com.bannrx.common.dtos.responses.PageableResponse;
import com.bannrx.common.enums.Unit;
import com.bannrx.common.mappers.DimensionMapper;
import com.bannrx.common.persistence.entities.Dimension;
import com.bannrx.common.repository.DimensionRepository;
import com.bannrx.common.searchCriteria.DimensionSearchCriteria;
import com.bannrx.common.specifications.DimensionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.utilities.PageableUtils;
import java.util.Objects;



@Loggable
@Service
@RequiredArgsConstructor
public class DimensionService {

    private final DimensionRepository repository;

    public PageableResponse<DimensionDto> search(DimensionSearchCriteria searchCriteria) {
        setDefaultIfApplicable(searchCriteria);
        var pageable = PageableUtils.createPageable(searchCriteria);
        var dimensionPage = repository.findAll(DimensionSpecification.buildSearchCriteria(searchCriteria), pageable);
        var dimensionList = dimensionPage.getContent().stream()
                .map(this::getDimensionDto)
                .filter(Objects::nonNull)
                .toList();

        return new PageableResponse<>(dimensionList,searchCriteria);
    }

    public DimensionDto save(DimensionSearchCriteria searchCriteria) {
        if(Objects.nonNull(searchCriteria.getLength()) && Objects.nonNull(searchCriteria.getBreadth())){
            var dimensionEntity = DimensionMapper.INSTANCE.toEntity(searchCriteria);
             dimensionEntity = repository.save(dimensionEntity);
             return getDimensionDto(dimensionEntity);
        }
        return null;
    }

    private DimensionDto getDimensionDto(Dimension dimension) {
        return DimensionMapper.INSTANCE.toDto(dimension);
    }

    private void setDefaultIfApplicable(DimensionSearchCriteria searchCriteria) {
        if(Objects.nonNull(searchCriteria.getLength()) || Objects.nonNull(searchCriteria.getBreadth()) && Objects.isNull(searchCriteria.getUnit())){
            searchCriteria.setUnit(Unit.CM);
        }
    }
}