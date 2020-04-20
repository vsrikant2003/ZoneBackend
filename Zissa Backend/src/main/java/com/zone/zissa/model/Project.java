package com.zone.zissa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Project_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;

    @Column(nullable = false, name = "Active_Status")
    private byte activeStatus;

    @Column(nullable = false, length = 5)
    private String projectName;

    // bi-directional many-to-one association to ProjectAllocation
    @OneToMany(mappedBy = "project")
    private Set<ProjectAllocation> projectAllocations;

    public Project() {
        // zero argument constructor
    }

    public int getProject_ID() {
        return this.projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public byte getActive_Status() {
        return this.activeStatus;
    }

    public void setActiveStatus(byte activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonIgnore
    public Set<ProjectAllocation> getProjectAllocations() {
        return projectAllocations;
    }

    public void setProjectAllocations(Set<ProjectAllocation> projectAllocations) {
        this.projectAllocations = projectAllocations;
    }
}
