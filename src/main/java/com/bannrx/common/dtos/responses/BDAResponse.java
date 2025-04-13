package com.bannrx.common.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BDAResponse {
    private Set<String> userIds;
    private Set<String> errorCases;
}
