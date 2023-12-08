package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUserModel {

    @SerializedName("userID")
    @Expose
    private float userID;

    @SerializedName("oldPassword")
    @Expose
    private String oldPassword;

    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    @SerializedName("newStatus")
    @Expose
    private String newStatus;

    @SerializedName("newName")
    @Expose
    private String newName;

    @SerializedName("oldEmail")
    @Expose
    private String oldEmail;

    @SerializedName("newEmail")
    @Expose
    private String newEmail;

    @SerializedName("newDOB")
    @Expose
    private String newDOB;

    @SerializedName("newProfilePicture")
    @Expose
    private String newProfilePicture;

    public UpdateUserModel(Integer userID, String email) {
        this.userID = userID;
        this.oldEmail = email;

    }

    public UpdateUserModel(JSONObject object) throws JSONException {
        this.oldEmail = object.getString("email");
        this.userID = Float.parseFloat(object.getString("userID"));
    }


    // Getter Methods

    public float getUserID() {
        return userID;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getNewName() {
        return newName;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getNewDOB() {
        return newDOB;
    }

    public String getNewProfilePicture() {
        return newProfilePicture;
    }

    // Setter Methods

    public void setUserID(float userID) {
        this.userID = userID;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public void setNewDOB(String newDOB) {
        this.newDOB = newDOB;
    }

    public void setNewProfilePicture(String newProfilePicture) {
        this.newProfilePicture = newProfilePicture;
    }
}
