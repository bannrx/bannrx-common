package com.bannrx.common.searchCriteria;

import com.bannrx.common.enums.Operator;
import com.bannrx.common.enums.Unit;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import rklab.utility.models.searchCriteria.PageableSearchCriteria;



@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeviceSearchCriteria extends PageableSearchCriteria {
    private Boolean isFrontCameraAvailable;
    private Boolean isBackCameraAvailable;
    private Boolean isWorking;
    private Float length;
    private Operator lengthOperator;
    private Float breadth;
    private Operator breadthOperator;
    private Unit unit;


    @Builder(builderMethodName = "deviceSearchCriteriaBuilder")
    public DeviceSearchCriteria(
            final Boolean isFrontCameraAvailable,
            final Boolean isBackCameraAvailable,
            final Boolean isWorking,
            final Float length,
            final Operator lengthOperator,
            final Float breadth,
            final Operator breadthOperator,
            final Unit unit,
            final int perPage,
            final int pageNo,
            final String sortBy,
            final Sort.Direction sortDirection
    ){
        super(perPage, pageNo, sortBy, sortDirection);
        this.isFrontCameraAvailable = isFrontCameraAvailable;
        this.isBackCameraAvailable = isBackCameraAvailable;
        this.isWorking = isWorking;
        this.length = length;
        this.lengthOperator = lengthOperator;
        this.breadth = breadth;
        this.breadthOperator = breadthOperator;
        this.unit = unit;
    }
}
