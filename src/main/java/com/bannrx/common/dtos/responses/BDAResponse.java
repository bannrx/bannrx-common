package com.bannrx.common.dtos.responses;

import com.bannrx.common.dtos.BDAUserExcelDto;
import lombok.Data;
import rklab.utility.dto.InvalidExcelRecordDto;

import java.util.List;


@Data
public class BDAResponse {
    private List<String> createdUserId;
    private List<BDAUserExcelDto> bdaUserExcelDtoList;
    private List<InvalidExcelRecordDto> invalidExcelRecordDtoList;
}
