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
 * The persistent class for the operation database table.
 * 
 */
@Entity
@Table(name = "operation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Operation_ID")
    private int operationID;

    @Column(nullable = false, length = 20)
    private String name;

    // bi-directional many-to-one association to Permission
    @OneToMany(mappedBy = "operation")
    private Set<Permission> permissions;

    public Operation() {
        // zero argument constructor
    }

    public int getOperation_ID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
