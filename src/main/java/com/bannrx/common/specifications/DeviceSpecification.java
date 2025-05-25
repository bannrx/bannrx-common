package com.bannrx.common.specifications;

import com.bannrx.common.enums.Operator;
import com.bannrx.common.enums.Unit;
import com.bannrx.common.persistence.entities.Device;
import com.bannrx.common.searchCriteria.DeviceSearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.Objects;



public class DeviceSpecification {

    private static final String ISFRONTCAMERAPERSENT = "isFrontCameraAvailable";
    private static final String ISBACKCAMERAPERSENT = "isBackCameraAvailable";
    private static final String ISWORKING = "isWorking";
    private static final String DIMENSION = "dimension";


    public static Specification<Device> buildSearchCriteria(final DeviceSearchCriteria searchCriteria)  {
        var retVal = Specification.<Device>where(null);

        if(Objects.nonNull(searchCriteria.getIsFrontCameraAvailable())){
            retVal = retVal.and(filterByIsFrontCameraAvailable(searchCriteria.getIsFrontCameraAvailable()));
        }

        if(Objects.nonNull(searchCriteria.getIsBackCameraAvailable())){
            retVal = retVal.and(filterByIsBankCameraAvailable(searchCriteria.getIsBackCameraAvailable()));
        }

        if(Objects.nonNull(searchCriteria.getIsWorking())){
            retVal = retVal.and(filterByIsWorking(searchCriteria.getIsWorking()));
        }

        if(Objects.nonNull(searchCriteria.getLength())){
            retVal = retVal.and(jsonNumberFilter("$.length", searchCriteria.getLength(), searchCriteria.getLengthOperator()));
        }

        if(Objects.nonNull(searchCriteria.getBreadth())){
            retVal = retVal.and(jsonNumberFilter("$.breadth", searchCriteria.getBreadth(), searchCriteria.getBreadthOperator()));
        }

        if(Objects.nonNull(searchCriteria.getUnit())){
            retVal = retVal.and(filterByUnit(searchCriteria.getUnit()));
        }

        return retVal;
    }

    private static Specification<Device> filterByIsWorking(Boolean isWorking) {
        return ((root, query, criteriaBuilder) ->
            isWorking
            ? criteriaBuilder.isTrue(root.get(ISWORKING))
            : criteriaBuilder.isFalse(root.get(ISWORKING))
        );
    }

    private static Specification<Device> filterByIsBankCameraAvailable(Boolean isBackCameraAvailable) {
        return ((root, query, criteriaBuilder) ->
           isBackCameraAvailable
           ? criteriaBuilder.isTrue(root.get(ISBACKCAMERAPERSENT))
           : criteriaBuilder.isFalse(root.get(ISBACKCAMERAPERSENT))
        );
    }

    private static Specification<Device> filterByIsFrontCameraAvailable(Boolean isFrontCameraAvailable) {
        return ((root, query, criteriaBuilder) ->
            isFrontCameraAvailable
            ? criteriaBuilder.isTrue(root.get(ISFRONTCAMERAPERSENT))
            : criteriaBuilder.isFalse(root.get(ISFRONTCAMERAPERSENT))
        );
    }

    private static Specification<Device> jsonNumberFilter(String jsonPath, Float value, Operator operator){
        return (root, query, criteriaBuilder) -> {
            Expression<Float> jsonValue = getJsonExpression(jsonPath, root, criteriaBuilder, Float.class);
            return switch (operator) {
                case GREATERTHANOREQUALTO -> criteriaBuilder.ge(jsonValue, value);
                case LESSTHANOREQUALTO -> criteriaBuilder.le(jsonValue, value);
                case GREATERTHAN -> criteriaBuilder.gt(jsonValue, value);
                case LESSTHAN -> criteriaBuilder.lt(jsonValue, value);
                default -> criteriaBuilder.equal(jsonValue, value);
            };
        };
    }

    private static Specification<Device> filterByUnit(Unit unit){
        return (root, query, criteriaBuilder) -> {
            Expression<String> unitValue = getJsonExpression("$.unit", root, criteriaBuilder, String.class);
            return criteriaBuilder.equal(unitValue, unit.name());
        };
    }

    private static <T> Expression<T> getJsonExpression(String jsonPath, Root<Device> root, CriteriaBuilder criteriaBuilder, Class<T> returnType) {
        return criteriaBuilder.function(
            "JSON_EXTRACT",
            returnType,
            root.get(DIMENSION),
            criteriaBuilder.literal(jsonPath)
        );
    }
}
