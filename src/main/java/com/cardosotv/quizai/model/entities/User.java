package com.cardosotv.quizai.model.entities;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="users")
public class User extends Default{

    // @Id
    // @GeneratedValue(strategy =  GenerationType.AUTO)
    // private UUID id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name="birth_day")
    private Date birthDay;
    private String address;
    private int score;

    public User(){}

    public User(java.util.Date created_date, UUID created_by, String name, String email, String phone, Date birthDay,
            String address, int score) {

        super(created_date, created_by);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.address = address;
        this.score = score;
    }
    
    // /**
    //  * @return UUID return the id
    //  */
    // public UUID getId() {
    //     return id;
    // }

    // /**
    //  * @param id the id to set
    //  */
    // public void setId(UUID id) {
    //     this.id = id;
    // }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return Date return the birthDay
     */
    public Date getBirthDay() {
        //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return int return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }


    public void updateObject(User user, UUID idUpdatedBy){
        this.name = user.name;
        this.email = user.email;
        this.phone = user.phone;
        this.birthDay = user.birthDay;
        this.address = user.address;
        this.score = user.score;
        this.setUpdatedDate(new Date());
        this.setUpdatedBy(idUpdatedBy);
    }

}
