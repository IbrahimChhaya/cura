package ChildReports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("noteID")
    @Expose
    private int noteID;

    @SerializedName("feedback")
    @Expose
    private String feedback;

    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("psychID")
    @Expose
    private int psychID;

    @SerializedName("pairID")
    @Expose
    private int pairID;


    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }

    public int getPairID() {
        return pairID;
    }

    public void setPairID(int pairID) {
        this.pairID = pairID;
    }
}
