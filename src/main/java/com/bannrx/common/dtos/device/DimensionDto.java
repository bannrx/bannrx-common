package com.bannrx.common.dtos.device;

import com.bannrx.common.enums.Unit;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;



@Data
public class DimensionDto {
    @NotNull(message = "length cannot be null")
    @Positive(message = "Length must be positive")
    private Float length;

    @NotNull(message = "breadth cannot be null")
    @Positive(message = "Breadth must be positive")
    private Float breadth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Unit cannot be null")
    private Unit unit;

}
