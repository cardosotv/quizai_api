package com.cardosotv.quizai.model.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// import java.util.UUID;

@Entity
@Table(name = "subjects")
public class Subject {

    // private UUID id;
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private UUID id;
    @NotBlank(message = "Value field blank is not allowed.") @Size(min = 4, max = 20)
    private String name;
    private String about; 
    private String icon; 
    @Column(name="is_active")
    private boolean isActive;
    @Transient
    private String observation;

    public Subject(){}

    public Subject(String name, String about, String icon, boolean isActive, String observation){
        this.name = name; 
        this.about = about;
        this.icon = icon;
        this.isActive = isActive;
        this.observation = observation;
    }
     
    public String getObservation() {
        return this.observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
     * @return String return the subject
     */
    public String getName() {
        return name;
    }

    /**
     * @param subject the subject to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * @param about the about to set
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * @return String return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return boolean return the isActive
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setisActive(boolean isActive) {
        this.isActive = isActive;
    }

    // implements the copy of object 
    public void updateObject(Subject subject) {
        this.name = subject.name; 
        this.about = subject.about;
        this.icon = subject.icon;
        this.isActive = subject.isActive;
    }

}