package com.bannrx.common.persistence;

import com.bannrx.common.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import rklab.utility.utilities.IdGenerator;

import java.util.Date;

@Data
@MappedSuperclass
public abstract class Persist {

    @Id
    private String id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @PrePersist
    public void onPrePersist(){
        if (StringUtils.isBlank(this.id)){
            this.setId(this.getPrefix().concat(IdGenerator.generateId()));
        }
        this.setCreatedAt(new Date());
        if (StringUtils.isBlank(this.createdBy)){
            this.setCreatedBy(getLoggedInUserId());
        }
        onPreUpdate();
    }

    @PreUpdate
    public void onPreUpdate(){
        this.setModifiedAt(new Date());
        this.setModifiedBy(setDefaultModifiedBy());
    }

    /**
     * Fetches the logged-in user id.
     * This is yet to be built.
     * If not found should return null.
     *
     * @return logged in user id
     */
    private String getLoggedInUserId(){
        return "default";
    }

    public String setDefaultModifiedBy(){
        return getLoggedInUserId();
    }

    public abstract String getPrefix();

}
