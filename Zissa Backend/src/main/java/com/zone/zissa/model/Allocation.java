package com.zone.zissa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the allocation database table.
 * 
 */
@Entity
@Table(name = "allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Allocation_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allocationID;

    @Column(nullable = false, name = "Created_Date")
    private Timestamp createdDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "From_Date")
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "To_Date")
    private Date toDate;

    // bi-directional many-to-one association to AllocationType
    @ManyToOne
    @JoinColumn(name = "FK_Allocation_Type_ID", nullable = false)
    private AllocationType allocationType;

    // bi-directional many-to-one association to Resource
    @ManyToOne
    @JoinColumn(name = "FK_Resource_ID", nullable = false)
    private Resource resource;

    // bi-directional many-to-one association to Status
    @ManyToOne
    @JoinColumn(name = "FK_Status_ID", nullable = false)
    private Status status;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "FK_Create_User_ID", nullable = false)
    private User user;

    // bi-directional many-to-one association to EmployeeAllocation
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
    private EmployeeAllocation employeeAllocations;

    // bi-directional many-to-one association to OtherAllocation
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
    private OtherAllocation otherAllocations;

    // bi-directional many-to-one association to ProjectAllocation
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
    private ProjectAllocation projectAllocations;

    public Allocation() {
        // zero argument constructor
    }

    public int getAllocation_ID() {
        return this.allocationID;
    }

    public void setAllocationID(int allocationID) {
        this.allocationID = allocationID;
    }

    public Timestamp getCreated_Date() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getFrom_Date() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getTo_Date() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public AllocationType getAllocationType() {
        return this.allocationType;
    }

    public void setAllocationType(AllocationType allocationType) {
        this.allocationType = allocationType;
    }

    @JsonIgnore
    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmployeeAllocation getEmployeeAllocations() {
        return employeeAllocations;
    }

    public void setEmployeeAllocations(EmployeeAllocation employeeAllocations) {
        this.employeeAllocations = employeeAllocations;
    }

    public OtherAllocation getOtherAllocations() {
        return otherAllocations;
    }

    public void setOtherAllocations(OtherAllocation otherAllocations) {
        this.otherAllocations = otherAllocations;
    }

    public ProjectAllocation getProjectAllocations() {
        return projectAllocations;
    }

    public void setProjectAllocations(ProjectAllocation projectAllocations) {
        this.projectAllocations = projectAllocations;
    }
}
