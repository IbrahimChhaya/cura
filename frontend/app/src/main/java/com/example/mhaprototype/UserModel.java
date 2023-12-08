package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {

    @SerializedName("userID")
    @Expose
    private int userID;

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

    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("grade")
    @Expose
    private String grade;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("dateRegistered")
    @Expose
    private String dateRegistered;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("PairCode")
    @Expose
    private String PairCode;

    @SerializedName("Spice")
    @Expose
    private String Spice;

    //PicturePass
    @SerializedName("PicturePass")
    @Expose
    private boolean PicturePass;

    public boolean isPicturePass() {
        return PicturePass;
    }

    public void setPicturePass(boolean picturePass) {
        PicturePass = picturePass;
    }

    public String getPairCode() {
        return PairCode;
    }

    public void setPairCode(String pairCode) {
        PairCode = pairCode;
    }

    public String getSpice() {
        return Spice;
    }

    public void setSpice(String spice) {
        Spice = spice;
    }


    public UserModel(String email, String password,String usertype) {
        this.email = email;
        this.password = password;
        this.userType = usertype;
    }

    public UserModel(String email, String name, String password, String dob, String userType, String grade, String pPicture, String dateRegistered, String status){
        this.email = email;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.userType = userType;
        this.grade = grade;
        this.profilePicture = pPicture;
        this.dateRegistered = dateRegistered;
        this.status = status;
    }

    public UserModel(String email, String name, String password, String dob, String userType, String grade, String pPicture, String dateRegistered, String status,boolean picPass){
        this.email = email;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.userType = userType;
        this.grade = grade;
        this.profilePicture = pPicture;
        this.dateRegistered = dateRegistered;
        this.status = status;
        this.PicturePass = picPass;
    }

    public UserModel(JSONObject object) throws JSONException{
        email = object.getString("email");
        userType = object.getString("userType");
        userID = object.getInt("userID");
        status = object.getString("status");
        profilePicture = object.getString("profilePicture");
        name = object.getString("name");
        dob = object.optString("dob", null);
        grade = object.optString("grade", null);
        PairCode = object.optString("pairCode");
        password = object.optString("password");
        status = object.optString("status");
        Spice = object.optString("spice");
        dateRegistered = object.getString("dateRegistered");
        PicturePass = object.optBoolean("PicturePass");
    }

    // Getter Methods


    public int getUserID() {
        return userID;
    }

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

    public String getUserType() {
        return userType;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setUserID(int userID) {
        this.userID = userID;
    }

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

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade;}
}
