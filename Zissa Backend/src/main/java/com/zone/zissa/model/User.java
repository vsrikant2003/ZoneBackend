package com.zone.zissa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "User_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(nullable = false, name = "Active_Status")
    private int activeStatus;

    @Column(nullable = false, name = "Created_Date")
    private Timestamp createdDate;

    @Column(nullable = false, length = 32)
    private String email;

    @Column(nullable = false, length = 20, name = "First_Name")
    private String firstName;

    @Column(nullable = false, length = 20, name = "Last_Name")
    private String lastName;

    @Column(nullable = false, length = 20, name = "User_Name")
    private String userName;

    @JsonIgnore
    // bi-directional many-to-one association to Allocation
    @OneToMany(mappedBy = "user")
    private Set<Allocation> allocations;
    @JsonIgnore
    // bi-directional many-to-one association to Attribute
    @OneToMany(mappedBy = "user")
    private Set<Attribute> attributes;
    @JsonIgnore
    // bi-directional many-to-one association to Category
    @OneToMany(mappedBy = "user")
    private Set<Category> categories;
    @JsonIgnore
    // bi-directional many-to-one association to Resource
    @OneToMany(mappedBy = "user")
    private List<Resource> resources;

    // bi-directional many-to-one association to Role
    @ManyToOne
    @JoinColumn(name = "FK_Role_ID", nullable = false)
    private Role role;

    public User() {
        // zero argument constructor
    }

    public int getUser_ID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getActive_Status() {
        return activeStatus;
    }

    public void setActiveStatus(int i) {
        this.activeStatus = i;
    }

    public Timestamp getCreated_Date() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_Name() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLast_Name() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Allocation> getAllocations() {
        return this.allocations;
    }

    public void setAllocations(Set<Allocation> allocations) {
        this.allocations = allocations;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
