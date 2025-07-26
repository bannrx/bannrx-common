package com.bannrx.common.specifications;

import com.bannrx.common.enums.Unit;
import com.bannrx.common.persistence.entities.Dimension;
import com.bannrx.common.searchCriteria.DimensionSearchCriteria;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.util.Objects;


public class DimensionSpecification {

    private final static String LENGTH = "length";
    private final static String BREADTH = "breadth";
    private final static String UNIT = "unit";


    public static Specification<Dimension> buildSearchCriteria(final DimensionSearchCriteria searchCriteria){
        var retVal = Specification.<Dimension>where(null);

        if (Objects.nonNull(searchCriteria.getLength())){
            retVal = retVal.and(queryByDimensions(LENGTH, searchCriteria.getLength()));
        }

        if (Objects.nonNull(searchCriteria.getBreadth())){
            retVal = retVal.and(queryByDimensions(BREADTH, searchCriteria.getBreadth()));
        }

        if (Objects.nonNull(searchCriteria.getUnit())){
            retVal = retVal.and(filterByUnit(searchCriteria.getUnit()));
        }
        return retVal;
    }

    private static Specification<Dimension> queryByDimensions(String dimensionVal, BigDecimal value) {
        return (root, query, criteriaBuilder) -> {
            Path<Float> path = root.get(dimensionVal);
            return criteriaBuilder.equal(path, value);
        };
    }

    private static Specification<Dimension> filterByUnit(Unit unit){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(UNIT), unit.name());
    }

}
