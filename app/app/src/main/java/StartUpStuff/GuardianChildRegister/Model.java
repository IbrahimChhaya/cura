package StartUpStuff.GuardianChildRegister;

import com.example.mhaprototype.GlobalVariables;

public class Model {
    ChildInfo ChildInfoObject;
    private float guardianID;


    // Getter Methods

    public ChildInfo getChildInfo() {
        return ChildInfoObject;
    }

    public float getGuardianID() {
        return guardianID;
    }

    // Setter Methods

    public void setChildInfo(ChildInfo childInfoObject) {
        this.ChildInfoObject = childInfoObject;
    }

    public void setGuardianID(float guardianID) {
        this.guardianID = guardianID;
    }

    public Model(int GuardianID){
        this.guardianID = GuardianID;
    }
}
class ChildInfo {
    private float userID;
    private String name;
    private String email;
    private String password;
    private String dob;
    private String userType;
    private String grade;
    private String profilePicture;
    private String dateRegistered;
    private String status;
    private String pairCode;
    private String spice;



    public ChildInfo(String email, String name, String password, String dob, String userType, String grade, String pPicture, String dateRegistered, String status){
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

    // Getter Methods

    public float getUserID() {
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

    public String getGrade() {
        return grade;
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

    public String getPairCode() {
        return pairCode;
    }

    public String getSpice() {
        return spice;
    }

    // Setter Methods

    public void setUserID(float userID) {
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

    public void setGrade(String grade) {
        this.grade = grade;
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

    public void setPairCode(String pairCode) {
        this.pairCode = pairCode;
    }

    public void setSpice(String spice) {
        this.spice = spice;
    }


}

