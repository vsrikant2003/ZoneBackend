package com.zone.zissa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * The persistent class for the attribute database table.
 * 
 */
@Entity
@Table(name = "attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "Attribute_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short attributeID;

    @Column(nullable = false, name = "Created_Date")
    private Timestamp createdDate;

    @Column(nullable = false, length = 20, name = "Name")
    private String name;

    // bi-directional many-to-one association to AttrDataType
    @ManyToOne
    @JoinColumn(name = "FK_Data_Type_ID", nullable = false)
    private AttrDataType attrDataType;

    // bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Create_User_ID", nullable = false)
    private User user;

    // bi-directional many-to-one association to AttributeValue
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
    private Set<AttributeValue> attributeValues;

    @JsonIgnore
    // bi-directional many-to-one association to CategoryAttribute
    @OneToMany(mappedBy = "attribute")
    private Set<CategoryAttribute> categoryAttributes;

    @JsonIgnore
    // bi-directional many-to-one association to ResourceAttribute
    @OneToMany(mappedBy = "attribute")
    private Set<ResourceAttribute> resourceAttributes;

    public Attribute() {
        // zero argument constructor
    }

    public short getAttribute_ID() {
        return this.attributeID;
    }

    public void setAttributeID(short attributeID) {
        this.attributeID = attributeID;
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

    public AttrDataType getAttrDataType() {
        return this.attrDataType;
    }

    public void setAttrDataType(AttrDataType attrDataType) {
        this.attrDataType = attrDataType;
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<AttributeValue> getAttributeValues() {
        return this.attributeValues;
    }

    public void setAttributeValues(Set<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Set<CategoryAttribute> getCategoryAttributes() {
        return this.categoryAttributes;
    }

    public void setCategoryAttributes(Set<CategoryAttribute> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }

    public Set<ResourceAttribute> getResourceAttributes() {
        return this.resourceAttributes;
    }

    public void setResourceAttributes(Set<ResourceAttribute> resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }
}
