package com.bannrx.common.entities;

import com.bannrx.common.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@MappedSuperclass
@AllArgsConstructor
public class Persist {

    public Persist(){
        this.createdAt = new Date();
        this.modifiedAt = new Date();

    }

    public Persist(String createdBy, String modifiedBy){
        this.createdAt = new Date();
        this.modifiedAt = new Date();
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "status")
    private Status status;

    @Column(name = "created_at")
    private Date createdAt; // != null

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_at")
    private Date modifiedAt;  //!= null

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

}
