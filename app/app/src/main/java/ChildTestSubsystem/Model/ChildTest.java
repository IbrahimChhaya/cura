package ChildTestSubsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildTest {

    @SerializedName("childTestID")
    @Expose
    private int childTestID;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("childID")
    @Expose
    private int childID;

    @SerializedName("testID")
    @Expose
    private int testID;

    @SerializedName("noteID")
    @Expose
    private int noteID;



    public int getChildTestID() {
        return childTestID;
    }

    public void setChildTestID(int childTestID) {
        this.childTestID = childTestID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }
}
