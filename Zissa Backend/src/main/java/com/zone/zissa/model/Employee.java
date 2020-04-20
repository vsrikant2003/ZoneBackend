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
 * The persistent class for the employee database table.
 * 
 */
@Entity
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Employee_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeID;

    @Column(nullable = false, name = "Active_Status")
    private byte activeStatus;

    @Column(nullable = false, length = 20, name = "First_Name")
    private String firstName;

    @Column(nullable = false, length = 20, name = "Last_Name")
    private String lastName;

    @Column(nullable = false, length = 20, name = "User_Name")
    private String userName;

    // bi-directional many-to-one association to EmployeeAllocation
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeAllocation> employeeAllocations;

    public Employee() {
        // zero argument constructor
    }

    public int getEmployee_ID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public byte getActive_Status() {
        return this.activeStatus;
    }

    public void setActiveStatus(byte activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getFirst_Name() {
        return this.firstName;
    }

    public String getLast_Name() {
        return this.lastName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonIgnore
    public Set<EmployeeAllocation> getEmployeeAllocations() {
        return employeeAllocations;
    }

    public void setEmployeeAllocations(Set<EmployeeAllocation> employeeAllocations) {
        this.employeeAllocations = employeeAllocations;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
