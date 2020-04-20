package com.zone.zissa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the allocation_type database table.
 * 
 */
@Entity
@Table(name = "allocation_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllocationType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Allocation_Type_ID")
    private byte allocationTypeID;

    @Column(nullable = false, length = 20, name = "Allocation_Name")
    private String allocationName;

    // bi-directional many-to-one association to Allocation
    @OneToMany(mappedBy = "allocationType")
    private Set<Allocation> allocations;

    public AllocationType() {
        // zero argument constructor
    }

    public byte getAllocation_Type_ID() {
        return this.allocationTypeID;
    }

    public void setAllocationTypeID(byte allocationTypeID) {
        this.allocationTypeID = allocationTypeID;
    }

    public String getAllocation_Name() {
        return this.allocationName;
    }

    public void setAllocationName(String allocationName) {
        this.allocationName = allocationName;
    }

    @JsonIgnore
    public Set<Allocation> getAllocations() {
        return this.allocations;
    }

    public void setAllocations(Set<Allocation> allocations) {
        this.allocations = allocations;
    }
}
