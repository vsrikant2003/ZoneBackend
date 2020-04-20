package com.zone.zissa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the resource database table.
 * 
 */
@Entity
@Table(name = "resource")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Resource_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceID;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, name = "Created_Date")
    private Timestamp createdDate;

    // bi-directional many-to-one association to Allocation
    @OneToMany(mappedBy = "resource")
    private Set<Allocation> allocations;

    // bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name = "FK_Category_ID", nullable = false)
    private Category category;

    // bi-directional many-to-one association to Status
    @ManyToOne
    @JoinColumn(name = "FK_Status_ID", nullable = false)
    private Status status;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "FK_Create_User_ID", nullable = false)
    private User user;

    // bi-directional many-to-one association to ResourceAttribute
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<ResourceAttribute> resourceAttributes;

    public Resource() {
        // zero argument constructor
    }

    public int getResource_ID() {
        return this.resourceID;
    }

    public Timestamp getCreated_Date() {
        return this.createdDate;
    }

    public String getCode() {
        return this.code;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Allocation> getAllocations() {
        return this.allocations;
    }

    public void setAllocations(Set<Allocation> allocations) {
        this.allocations = allocations;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ResourceAttribute> getResourceAttributes() {
        return this.resourceAttributes;
    }

    public void setResourceAttributes(List<ResourceAttribute> resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }
}
