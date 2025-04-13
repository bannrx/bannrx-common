package com.bannrx.common.utilities;

import org.apache.poi.ss.usermodel.*;
import rklab.utility.annotations.ColumnNotEmpty;
import com.bannrx.common.dtos.responses.BDAResponse;
import com.bannrx.common.dtos.BDAUserExcelDto;
import rklab.utility.dto.InvalidExcelRecordDto;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ExcelUtil {
    public static BDAResponse validateAndParseToExcel(InputStream excelFile, Class<?> dtoClass, int sheetNumber)  {
        List<InvalidExcelRecordDto> errorList = new ArrayList<>();
        List<BDAUserExcelDto> dtos = new ArrayList<>();
        var res = new BDAResponse();
        List<Field> validatedFields = Arrays.stream(dtoClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ColumnNotEmpty.class))
                .toList();
        try(Workbook workbook = WorkbookFactory.create(excelFile)){
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            for(int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++){
                Row row = sheet.getRow(rowIdx);
                if(row == null)continue;
                var dto = new BDAUserExcelDto();
                boolean isValid = true;

                for(Field field : validatedFields){
                    ColumnNotEmpty annotation = field.getAnnotation(ColumnNotEmpty.class);
                    int col = annotation.column();
                    String errorMessage = annotation.message();

                    Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = getCellValueAsString(cell);
                    if(cellValue.isEmpty()){
                        errorList.add(new InvalidExcelRecordDto(rowIdx+1, col, errorMessage));
                        isValid = false;
                    }else {
                        field.setAccessible(true);
                        field.set(dto, cellValue);
                    }
                }

                if(isValid)dtos.add(dto);
            }

        }catch(IOException e){
            throw new RuntimeException("Failed to read Excel file", e);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Invalid sheet number: " + sheetNumber, e);
        }catch (Exception e){
            throw new RuntimeException("Error during Excel validation", e);
        }

        res.setBdaUserExcelDtoList(dtos);
        res.setInvalidExcelRecordDtoList(errorList);
        return res;
    }

    private static String getCellValueAsString(Cell cell){
        if(cell == null || cell.getCellType() == CellType.BLANK) return "";

        return switch(cell.getCellType()){
            case STRING -> cell.toString().trim();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue()).trim();
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue()).trim();
            default -> "";
        };
    }
}
