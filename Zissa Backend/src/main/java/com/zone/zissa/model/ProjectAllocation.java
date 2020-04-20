package com.zone.zissa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the project_allocation database table.
 * 
 */
@Entity
@Table(name = "project_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectAllocation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Project_Allocation_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectAllocationID;

    // bi-directional many-to-one association to Allocation
    @OneToOne
    @JoinColumn(name = "FK_Allocation_ID", nullable = false)
    private Allocation allocation;

    // bi-directional many-to-one association to Project
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Project_ID", nullable = false)
    private Project project;

    public ProjectAllocation() {
        // zero argument constructor
    }

    public int getProject_Allocation_ID() {
        return this.projectAllocationID;
    }

    public void setProjectAllocationID(int projectAllocationID) {
        this.projectAllocationID = projectAllocationID;
    }

    @JsonIgnore
    public Allocation getAllocation() {
        return this.allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
