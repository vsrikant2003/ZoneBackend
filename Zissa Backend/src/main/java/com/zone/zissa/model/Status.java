package com.zone.zissa.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the status database table.
 * 
 */
@Entity
@Table(name = "status")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Status_ID")
    private byte statusID;

    @Column(nullable = false, length = 20, name = "Status_Name")
    private String statusName;

    // bi-directional many-to-one association to Allocation
    @OneToMany(mappedBy = "status")
    private Set<Allocation> allocations;

    // bi-directional many-to-one association to Resource
    @OneToMany(mappedBy = "status")
    private List<Resource> resources;

    public Status() {
        // zero argument constructor
    }

    public byte getStatus_ID() {
        return this.statusID;
    }

    public void setStatusID(byte statusID) {
        this.statusID = statusID;
    }

    public String getStatus_Name() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @JsonIgnore
    public Set<Allocation> getAllocations() {
        return this.allocations;
    }

    public void setAllocations(Set<Allocation> allocations) {
        this.allocations = allocations;
    }

    @JsonIgnore
    public List<Resource> getResources() {
        return this.resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
