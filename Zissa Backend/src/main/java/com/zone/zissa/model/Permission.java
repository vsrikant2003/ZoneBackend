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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name = "permission")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Permission_ID")
    private int permissionID;

    // bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name = "FK_Category_ID", nullable = false)
    private Category category;

    // bi-directional many-to-one association to Operation
    @ManyToOne
    @JoinColumn(name = "FK_Operation_ID", nullable = false)
    private Operation operation;

    // bi-directional many-to-one association to Role
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Role_ID", nullable = false)
    private Role role;

    public Permission() {
        // zero argument constructor
    }

    public int getPermission_ID() {
        return this.permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @JsonIgnore
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
