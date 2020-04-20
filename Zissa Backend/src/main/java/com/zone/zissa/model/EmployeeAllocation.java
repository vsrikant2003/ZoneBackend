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
 * The persistent class for the employee_allocation database table.
 * 
 */
@Entity
@Table(name = "employee_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeAllocation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Employee_Allocation_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeAllocationID;

    // bi-directional many-to-one association to Allocation
    @OneToOne
    @JoinColumn(name = "FK_Allocation_ID", nullable = false)
    private Allocation allocation;

    // bi-directional many-to-one association to Employee
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Employee_ID", nullable = false)
    private Employee employee;

    public EmployeeAllocation() {
        // zero argument constructor
    }

    public int getEmployee_Allocation_ID() {
        return this.employeeAllocationID;
    }

    public void setEmployeeAllocationID(int employeeAllocationID) {
        this.employeeAllocationID = employeeAllocationID;
    }

    @JsonIgnore
    public Allocation getAllocation() {
        return this.allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
