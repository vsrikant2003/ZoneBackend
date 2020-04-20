package com.zone.zissa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the other_allocation database table.
 * 
 */
@Entity
@Table(name = "other_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAllocation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Other_Allocation_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int otherAllocationID;

    @Column(nullable = false, length = 20, name = "Assignee_Name")
    private String assigneeName;

    // bi-directional many-to-one association to Allocation
    @OneToOne
    @JoinColumn(name = "FK_Allocation_ID", nullable = false)
    private Allocation allocation;

    public OtherAllocation() {
        // zero argument constructor
    }

    public int getOther_Allocation_ID() {
        return this.otherAllocationID;
    }

    public void setOtherAllocationID(int otherAllocationID) {
        this.otherAllocationID = otherAllocationID;
    }

    public String getAssignee_Name() {
        return this.assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @JsonIgnore
    public Allocation getAllocation() {
        return this.allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }
}
