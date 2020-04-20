package com.zone.zissa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The persistent class for the resourcebin database table.
 * 
 */
@Entity
public class Resourcebin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false, name = "resource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceID;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false, name = "created_date")
    private Timestamp createdDate;
    // bi-directional many-to-one association to Category

    @ManyToOne
    @JoinColumn(name = "FK_Category_ID", nullable = false)
    private Category category;

    @Column(nullable = false, name = "fk_create_user_id")
    private int fKCreateUserID;

    @Column(nullable = false, name = "fk_status_id")
    private byte fKStatusID;

    @Column(nullable = false)
    private String disposeReason;

    // bi-directional many-to-one association to ResourcebinAttribute
    @OneToMany(mappedBy = "resourcebin", cascade = CascadeType.ALL)
    private List<ResourcebinAttribute> resourcebinAttributes;

    public Resourcebin() {
        // zero argument constructor
    }

    public String getDisposeReason() {
        return disposeReason;
    }

    public void setDisposeReason(String disposeReason) {
        this.disposeReason = disposeReason;
    }

    public int getResource_ID() {
        return this.resourceID;
    }

    public String getCode() {
        return this.code;
    }

    public Timestamp getCreated_Date() {
        return this.createdDate;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getFK_Create_User_ID() {
        return this.fKCreateUserID;
    }

    public void setFKCreateUserID(int fkCreateUserID) {
        this.fKCreateUserID = fkCreateUserID;
    }

    public byte getFK_Status_ID() {
        return this.fKStatusID;
    }

    public void setFKStatusID(byte fkStatusID) {
        this.fKStatusID = fkStatusID;
    }

    public List<ResourcebinAttribute> getResourcebinAttributes() {
        return this.resourcebinAttributes;
    }

    public void setResourcebinAttributes(List<ResourcebinAttribute> resourcebinAttributes) {
        this.resourcebinAttributes = resourcebinAttributes;
    }
}
