package com.zone.zissa.model;

import java.io.Serializable;
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
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "role")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Role_ID")
    private int roleID;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int administration;

    // bi-directional many-to-one association to AllocationType
    @ManyToOne
    @JoinColumn(name = "FK_Default_Category", nullable = false)
    private Category defaultCategory;

    // bi-directional many-to-one association to Permission

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Permission> permissions;

    // bi-directional many-to-one association to User
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role() {
        // zero argument constructor
    }

    public int getRole_ID() {
        return this.roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdministration() {
        return administration;
    }

    public void setAdministration(int administration) {
        this.administration = administration;
    }

    public List<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Category getDefaultCategory() {
        return defaultCategory;
    }

    public void setDefaultCategory(Category defaultCategory) {
        this.defaultCategory = defaultCategory;
    }
}
