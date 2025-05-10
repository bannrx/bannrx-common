package com.bannrx.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import rklab.utility.expectations.InvalidInputException;



public enum Unit {
    CM;

    @JsonCreator
    public static Unit fromString(String value) throws InvalidInputException {
        if (value == null) {
            throw new InvalidInputException("Unit cannot be null");
        }
        String normalized = value.trim().toUpperCase();
        if ("CM".equals(normalized)) {
            return CM;
        }
        throw new InvalidInputException("Invalid unit. Only CM is currently supported");
    }
}
