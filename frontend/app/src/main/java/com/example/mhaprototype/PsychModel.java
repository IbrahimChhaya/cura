package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class PsychModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("qualification")
    @Expose
    private String qualification;

    @SerializedName("regNumber")
    @Expose
    private String regNumber;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("speciality")
    @Expose
    private String speciality;

    @SerializedName("psychStatus")
    @Expose
    private String psychStatus;

    public PsychModel(String email, String password,String usertype) {
        this.email = email;
        this.password = password;

    }

    public PsychModel(JSONObject object) throws JSONException {
        email = object.getString("email");
    }


    // Getter Methods

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getAddress() {
        return address;
    }

    public String getQualification() {
        return qualification;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPsychStatus() {
        return psychStatus;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPsychStatus(String psychStatus) {
        this.psychStatus = psychStatus;
    }
}
