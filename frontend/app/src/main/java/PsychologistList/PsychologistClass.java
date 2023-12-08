package PsychologistList;

public class PsychologistClass {

    private int psychID;
    private String psychName;
    private String psychLocation;
    private String qualification;
    private String psychImage;
    private String psychDescription;


    public PsychologistClass(int psychID, String psychName, String psychLocation, String qualification, String psychImage,
                             String psychDescription) {
        this.psychID = psychID;
        this.psychName = psychName;
        this.psychLocation = psychLocation;
        this.qualification = qualification;
        this.psychImage = psychImage;
        this.psychDescription = psychDescription;
    }

    public String getPsychImage() {
        return psychImage;
    }

    public void setPsychImage(String psychImage) {
        this.psychImage = psychImage;
    }

    public String getPsychDescription() {
        return psychDescription;
    }

    public void setPsychDescription(String psychDescription) {
        this.psychDescription = psychDescription;
    }

    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }

    public String getPsychName() {
        return psychName;
    }

    public void setPsychName(String psychName) {
        this.psychName = psychName;
    }

    public String getPsychLocation() {
        return psychLocation;
    }

    public void setPsychLocation(String psychLocation) {
        this.psychLocation = psychLocation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
