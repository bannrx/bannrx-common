package com.bannrx.common.persistence;

import com.bannrx.common.dtos.SecurityUserDto;
import com.bannrx.common.persistence.entities.User;
import com.bannrx.common.utilities.SecurityUtils;
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

    @Column(name = "active")
    private boolean active;

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
        this.active = true;
        onPreUpdate();
    }

    @PreUpdate
    public void onPreUpdate(){
        this.setModifiedAt(new Date());
        if (StringUtils.isBlank(this.modifiedBy)){
            this.setModifiedBy(setDefaultModifiedBy());
        }
    }

    /**
     * Fetches the logged-in user id.
     *
     * @return logged in user id
     */
    private String getLoggedInUserId(){
        var principle = SecurityUtils.getLoggedInUser();
        if (principle instanceof SecurityUserDto user)
            return user.getId();
        return null;
    }

    protected String setDefaultModifiedBy(){
        return getLoggedInUserId();
    }

    public abstract String getPrefix();

}
