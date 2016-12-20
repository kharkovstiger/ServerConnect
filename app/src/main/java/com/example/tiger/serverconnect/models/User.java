package com.example.tiger.serverconnect.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class User implements Serializable{
    private BigInteger id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean sex;
    private Long birthday;
    private Boolean enabled;
    private String[] addressDataList;
    private Long registered;
    private Long updatedDate;

    public User(BigInteger id, String firstName, String lastName, String email, Boolean sex,
                Long birthday, Boolean enabled, String[] addressDataList, Long registered, Long updatedDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.enabled = enabled;
        this.addressDataList = addressDataList;
        this.registered = registered;
        this.updatedDate = updatedDate;
    }

    public User() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getAddressDataList() {
        return addressDataList;
    }

    public void setAddressDataList(String[] addressDataList) {
        this.addressDataList = addressDataList;
    }

    public Long getRegistered() {
        return registered;
    }

    public void setRegistered(Long registered) {
        this.registered = registered;
    }

    public Long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }
}
