package com.bannrx.common.persistence.entities;


import com.bannrx.common.enums.DocumentStatus;
import com.bannrx.common.enums.DocumentType;
import com.bannrx.common.persistence.Persist;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rklab.utility.utilities.JsonUtils;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(
        name = "documents",
        indexes = {
                @Index(name = "idx_doc_type_status", columnList = "document_type, status")
        }
)
public class Document extends Persist {

    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "etag")
    private String eTag;

    //This can be used for horizontal partitioning
    @Column(name = "bucket", nullable = false)
    private String bucket;

    @Override
    public String getPrefix() {
        return "DOC";
    }

    @Override
    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
