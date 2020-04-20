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

/**
 * The persistent class for the resourcebin_attribute database table.
 * 
 */
@Entity
@Table(name = "resourcebin_attribute")
public class ResourcebinAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "resource_attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceAttributeID;

    @Column(nullable = false, length = 100)
    private String value;

    @OneToOne
    @JoinColumn(name = "FK_Attribute_ID", nullable = false)
    private Attribute attribute;

    // bi-directional many-to-one association to Resourcebin
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Resource_ID", nullable = false)
    private Resourcebin resourcebin;

    public ResourcebinAttribute() {
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

    @JsonIgnore
    public Resourcebin getResourcebin() {
        return this.resourcebin;
    }

    public void setResourcebin(Resourcebin resourcebin) {
        this.resourcebin = resourcebin;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
