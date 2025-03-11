package com.bannrx.common.entities;
import jakarta.persistence.*;
import lombok.*;
import rklab.utility.utilities.JsonUtils;



@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Address extends Persist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name="address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    //generic
    public Address(String createdBy,String modifiedBy){
        super(createdBy,modifiedBy);
    }

    public String toString(){
        return JsonUtils.jsonOf(this);
    }
}
