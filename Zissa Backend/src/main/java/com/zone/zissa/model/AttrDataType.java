package com.zone.zissa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the attr_data_type database table.
 * 
 */
@Entity
@Table(name = "attr_data_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttrDataType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false,name="Data_Type_ID")
    private int dataTypeID;

    @Column(nullable = false, length = 20,name="Data_Type_Name")
    private String dataTypeName;

    // bi-directional many-to-one association to Attribute
    @OneToMany(mappedBy = "attrDataType")
    private Set<Attribute> attributes;

    public AttrDataType() {
        // zero argument constructor
    }

    public int getData_Type_ID() {
        return this.dataTypeID;
    }

    public void setDataTypeID(int dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public String getData_Type_Name() {
        return this.dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    @JsonIgnore
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
