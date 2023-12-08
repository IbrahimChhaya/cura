package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterModel {

    @SerializedName("userID")
    @Expose
    private int userID;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Email")
    @Expose
    private String Email;

    @SerializedName("Password")
    @Expose
    private String Password;

    @SerializedName("DOB")
    @Expose
    private String DOB;

    @SerializedName("UserType")
    @Expose
    private String UserType;

    @SerializedName("grade")
    @Expose
    private String grade;

    @SerializedName("ProfilePicture")
    @Expose
    private String ProfilePicture;

    @SerializedName("DateRegistered")
    @Expose
    private String DateRegistered;

    @SerializedName("Status")
    @Expose
    private String Status;

    public RegisterModel(String name, String email, String password, String dob, String usertype, String profilepicture, String dateregistered, String status){
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.DOB = dob;
        this.UserType = usertype;
        this.ProfilePicture = profilepicture;
        this.DateRegistered = dateregistered;
        this.Status = status;
    }

    public RegisterModel(JSONObject object) throws JSONException{
        userID = Integer.parseInt(object.getString("userID"));
        Name = object.getString("Name");
        Email = object.getString("Email");
        Password = object.getString("Password");
        DOB = object.getString("DOB");
        UserType = object.getString("UserType");
        ProfilePicture = object.getString("ProfilePicture");
        DateRegistered = object.getString("DateRegistered");
        Status = object.getString("Status");
    }


    // Getter Methods

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getDOB() {
        return DOB;
    }

    public String getUserType() {
        return UserType;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public String getDateRegistered() {
        return DateRegistered;
    }

    public String getStatus() {
        return Status;
    }

    // Setter Methods

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

    public void setProfilePicture(String ProfilePicture) {
        this.ProfilePicture = ProfilePicture;
    }

    public void setDateRegistered(String DateRegistered) {
        this.DateRegistered = DateRegistered;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
