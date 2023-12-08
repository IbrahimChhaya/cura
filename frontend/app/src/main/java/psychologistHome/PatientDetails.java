package psychologistHome;

public class PatientDetails {

    private int childID;
    private String childName;
    private String childDOB;
    private String childImage;
    private String childGrade;


    public PatientDetails(int childID, String childName, String childDOB, String childImage, String childGrade) {
        this.childID = childID;
        this.childName = childName;
        this.childDOB = childDOB;
        this.childImage = childImage;
        this.childGrade = childGrade;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildDOB() {
        return childDOB;
    }

    public void setChildDOB(String childDOB) {
        this.childDOB = childDOB;
    }

    public String getChildImage() {
        return childImage;
    }

    public void setChildImage(String childImage) {
        this.childImage = childImage;
    }

    public String getChildGrade() {
        return childGrade;
    }

    public void setChildGrade(String childGrade) {
        this.childGrade = childGrade;
    }
}
