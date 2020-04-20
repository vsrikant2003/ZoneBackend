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
 * The persistent class for the category_attribute database table.
 * 
 */
@Entity
@Table(name = "category_attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryAttribute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "Category_Attribute_ID")
    private int categoryAttributeID;

    // bi-directional many-to-one association to Attribute
    @ManyToOne
    @JoinColumn(name = "FK_Attribute_ID", nullable = false)
    private Attribute attribute;

    // bi-directional many-to-one association to Category
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_Category_ID", nullable = false)
    private Category category;

    @Column(nullable = false)
    private boolean isDefault;

    public CategoryAttribute() {
        // zero argument constructor
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getCategory_Attribute_ID() {
        return this.categoryAttributeID;
    }

    public void setCategoryAttributeID(int categoryAttributeID) {
        this.categoryAttributeID = categoryAttributeID;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @JsonIgnore
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
