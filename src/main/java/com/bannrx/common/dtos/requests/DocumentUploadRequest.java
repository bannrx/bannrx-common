package com.bannrx.common.dtos.requests;

import com.bannrx.common.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUploadRequest {

    @NotNull(message = "Please attach file/document/image/video to upload" )
    private MultipartFile file;
    @NotNull(message = "Type is mandatory.")
    private DocumentType type;

}
