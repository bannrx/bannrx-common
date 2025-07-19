package com.bannrx.common.persistence.entities;

import com.bannrx.common.dtos.device.DimensionDto;
import com.bannrx.common.persistence.Persist;
import com.bannrx.common.utilities.DeviceDimensionConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rklab.utility.utilities.JsonUtils;


@EqualsAndHashCode(callSuper = true, exclude = "userProfile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Device extends Persist {

    @Column(columnDefinition = "json")
    @Convert(converter = DeviceDimensionConverter.class)
    private DimensionDto dimension;

    @Column(name = "is_front_camera_available")
    private Boolean isFrontCameraAvailable;

    @Column(name = "is_back_camera_available")
    private Boolean isBackCameraAvailable;

    @Column(name = "is_working")
    private Boolean isWorking;

    @Column(name = "remarks")
    private String remarks;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Override
    public String getPrefix() {
        return "DE";
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
