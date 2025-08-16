package com.bannrx.common.persistence.entities;

import com.bannrx.common.persistence.Persist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.*;




@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserProfile extends Persist {


    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BankDetails> bankDetails = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @JsonIgnore
    private Business business;

    @JsonManagedReference
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Device> deviceSet = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Document> documentSet = new HashSet<>();

    @JsonIgnore
    public void appendAddress(Address address) {
        if (Objects.nonNull(address)){
            var existing = Optional.ofNullable(this.getAddresses())
                    .orElse(new LinkedHashSet<>());
            address.setUserProfile(this);
            existing.add(address);
            this.setAddresses(existing);
        }
    }

    @JsonIgnore
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUserProfile(this);
    }

    @JsonIgnore
    public void appendBankDetail(BankDetails bankDetails) {
        if (Objects.nonNull(bankDetails)){
            var existing = Optional.ofNullable(this.getBankDetails())
                    .orElse(new LinkedHashSet<>());
            bankDetails.setUserProfile(this);
            existing.add(bankDetails);
            this.setBankDetails(existing);
        }
    }

    @JsonIgnore
    public void removeBankDetail(BankDetails bankDetail) {
        bankDetails.remove(bankDetail);
        bankDetail.setUserProfile(null);
    }

    @JsonIgnore
    public void appendDocument(Document document){
        if (Objects.nonNull(document)){
            var existing = Optional.ofNullable(this.getDocumentSet())
                    .orElse(new HashSet<>());
            document.setUserProfile(this);
            existing.add(document);
            this.setDocumentSet(existing);
        }
    }

    @JsonIgnore
    public void removeDocument(Document document){
        documentSet.remove(document);
        document.setUserProfile(null);
    }

    @Override
    public String getPrefix() {
        return "UP";
    }
}
