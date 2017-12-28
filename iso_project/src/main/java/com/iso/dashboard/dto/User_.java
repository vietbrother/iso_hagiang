package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "USERS")
public class User_ implements Serializable {

    @Id
//    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ROLE", nullable = true)
    private String role;
    @Column(name = "USERNAME", nullable = true)
    private String userName;
    @Column(name = "FIRST_NAME", nullable = true)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = true)
    private String lastName;
    @Column(name = "TITLE", nullable = true)
    private String title;
    @Column(name = "SEX", nullable = true)
    private boolean male;
    @Column(name = "EMAIL", nullable = true)
    private String email;
    @Column(name = "LOCATION", nullable = true)
    private String location;
    @Column(name = "PHONE", nullable = true)
    private String phone;
//    private Integer newsletterSubscription;
    @Column(name = "WEBSITE", nullable = true)
    private String website;
    @Column(name = "BIRTH_DAY", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDay;

    @Column(name = "ORG_ID", nullable = true)
    private String orgId;
    @Column(name = "ORG_NAME", nullable = true)
    private String orgName;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(final boolean male) {
        this.male = male;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    
    @Override
    public String toString() {
        return "Users [id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + email + "]"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User_)) {
            return false;
        }
        User_ other = (User_) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

}
