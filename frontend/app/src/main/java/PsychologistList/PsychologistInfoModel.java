package PsychologistList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsychologistInfoModel {

    @SerializedName("userID")
    @Expose
    private float userID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    @SerializedName("dateRegistered")
    @Expose
    private String dateRegistered;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("psychID")
    @Expose
    private float psychID;

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

    public PsychologistInfoModel(float userID, String name, String email, String profilePicture, String dateRegistered, String status, float psychID, String address, String qualification, String regNumber, String description, String speciality) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.dateRegistered = dateRegistered;
        this.status = status;
        this.psychID = psychID;
        this.address = address;
        this.qualification = qualification;
        this.regNumber = regNumber;
        this.description = description;
        this.speciality = speciality;
    }

    //Getter and setter methods

    public float getUserID() {
        return userID;
    }

    public void setUserID(float userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPsychID() {
        return psychID;
    }

    public void setPsychID(float psychID) {
        this.psychID = psychID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}