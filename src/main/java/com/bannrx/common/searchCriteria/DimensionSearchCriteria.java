package com.bannrx.common.searchCriteria;

import com.bannrx.common.enums.Unit;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import rklab.utility.models.searchCriteria.PageableSearchCriteria;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DimensionSearchCriteria extends PageableSearchCriteria {

    private BigDecimal length;
    private BigDecimal breadth;
    private Unit unit;


    @Builder(builderMethodName = "DimensionSearchCriteriaBuilder")
    public DimensionSearchCriteria(
            final BigDecimal length,
            final BigDecimal breadth,
            final Unit unit,
            final Integer perPage,
            final Integer pageNo,
            final String sortBy,
            final Sort.Direction sortDirection
    ){
        super(perPage, pageNo, sortBy, sortDirection);
        this.length = length;
        this.breadth = breadth;
        this.unit = unit;
    }
}
