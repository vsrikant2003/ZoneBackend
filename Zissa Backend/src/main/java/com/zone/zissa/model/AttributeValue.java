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
 * The persistent class for the attribute_value database table.
 * 
 */
@Entity
@Table(name = "attribute_value")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Attribute_Value_ID")
    private int attributeValueID;

    @Column(nullable = false, length = 100, name = "Value")
    private String value;

    // bi-directional many-to-one association to Attribute

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Attribute_ID", nullable = false)
    private Attribute attribute;

    public AttributeValue() {
        // zero argument constructor
    }

    public int getAttribute_Value_ID() {
        return this.attributeValueID;
    }

    public void setAttributeValueID(int attributeValueID) {
        this.attributeValueID = attributeValueID;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonIgnore
    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
