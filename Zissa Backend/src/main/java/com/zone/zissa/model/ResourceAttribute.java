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
 * The persistent class for the resource_attribute database table.
 * 
 */
@Entity
@Table(name = "resource_attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Resource_Attribute_ID")
    private int resourceAttributeID;

    @Column(nullable = false, length = 100)
    private String value;

    // bi-directional many-to-one association to Attribute
    @ManyToOne
    @JoinColumn(name = "FK_Attribute_ID", nullable = false)
    private Attribute attribute;

    // bi-directional many-to-one association to Resource
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Resource_ID", nullable = false)
    private Resource resource;

    public ResourceAttribute() {
        // zero argument constructor
    }

    public int getResource_Attribute_ID() {
        return this.resourceAttributeID;
    }

    public void setResourceAttributeID(int resourceAttributeID) {
        this.resourceAttributeID = resourceAttributeID;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @JsonIgnore
    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
