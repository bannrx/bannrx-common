package com.bannrx.common.dtos;

import com.bannrx.common.dtos.requests.DocumentUploadRequest;
import com.bannrx.common.enums.DocumentStatus;
import com.bannrx.common.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private String id;
    private String fileName;
    private DocumentType type;
    private DocumentStatus status;
    private String url;
    private String mimeType;
    private String createdAt;
    private String modifiedAt;

    public static DocumentDto of(DocumentUploadRequest request){
        var multipartFile = request.getFile();
        var retVal = new DocumentDto();
        retVal.setFileName(multipartFile.getOriginalFilename());
        retVal.setMimeType(multipartFile.getContentType());
        retVal.setType(request.getType());
        return retVal;
    }

}
