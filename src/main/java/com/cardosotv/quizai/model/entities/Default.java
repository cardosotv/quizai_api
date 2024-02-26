package com.cardosotv.quizai.model.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Temporal;

@MappedSuperclass
public abstract class Default {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private UUID id;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "updated_date")
    private Date updatedDate;
    @LastModifiedBy
    @Column(name = "updated_by")
    private UUID updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "deleted_date")
    private Date deletedDate;
    @Column(name = "deleted_by")
    private UUID deletedBy;

    public Default(){}

    public Default(Date createdDate, UUID createdBy) {
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }


    /**
     * @return UUID return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return Date return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return UUID return the createdBy
     */
    public UUID getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return Date return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return UUID return the updatedBy
     */
    public UUID getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return Date return the deletedDate
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    /**
     * @param deletedDate the deletedDate to set
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * @return UUID return the deletedBy
     */
    public UUID getDeletedBy() {
        return deletedBy;
    }

    /**
     * @param deletedBy the deletedBy to set
     */
    public void setDeletedBy(UUID deletedBy) {
        this.deletedBy = deletedBy;
    }

}