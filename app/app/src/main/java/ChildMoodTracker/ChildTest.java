package ChildMoodTracker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

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


    public ChildTest(int childTestID, String date, String status, int childID, int testID, int noteID) {
        this.childTestID = childTestID;
        this.date = date;
        this.status = status;
        this.childID = childID;
        this.testID = testID;
        this.noteID = noteID;
    }

    public ChildTest(int childID, int testID) {
        this.childID = childID;
        this.testID = testID;
    }

    public ChildTest(int childID,String date, int testID)
    {
        this.childID = childID;
        this.testID = testID;
        this.date = date;
    }


    public ChildTest(JSONObject object) throws JSONException
    {
        //object.optString()
        childTestID = object.getInt("childTestID");
        date =object.getString("date");
        status =object.getString("status");
        childID =object.getInt("childID");
        testID = object.getInt("testID");
        noteID = object.optInt("noteID");
    }

    public ChildTest(int childTestID, int childID, int testID) {
        this.childTestID = childTestID;
        this.childID = childID;
        this.testID = testID;
    }

    //getters and setters
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
