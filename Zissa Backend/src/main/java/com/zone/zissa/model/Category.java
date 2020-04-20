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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Category_ID")
    private int categoryID;

    @Column(nullable = false, length = 20, name = "Code_Pattern")
    private String codePattern;

    @Column(nullable = false, name = "Created_Date")
    private Timestamp createdDate;

    @Column(nullable = false, length = 20, name = "Name")
    private String name;

    // bi-directional many-to-one association to User\

    @ManyToOne
    @JoinColumn(name = "FK_Create_User_ID")
    private User user;

    // bi-directional many-to-one association to CategoryAttribute

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<CategoryAttribute> categoryAttributes;

    // bi-directional many-to-one association to Permission
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Permission> permissions;

    @JsonIgnore
    // bi-directional many-to-one association to Resource
    @OneToMany(mappedBy = "category")
    private List<Resource> resources;

    public Category() {
        // zero argument constructor
    }

    public int getCategory_ID() {
        return this.categoryID;
    }

    public void setCategoryID(int categoryId) {
        this.categoryID = categoryId;
    }

    public String getCode_Pattern() {
        return this.codePattern;
    }

    public void setCodePattern(String codePattern) {
        this.codePattern = codePattern;
    }

    public Timestamp getCreated_Date() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CategoryAttribute> getCategoryAttributes() {
        return this.categoryAttributes;
    }

    public void setCategoryAttributes(Set<CategoryAttribute> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Resource> getResources() {
        return this.resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
