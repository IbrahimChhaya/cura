package ChildMoodTracker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ChildAnswer {

    @SerializedName("childAnswerID")
    @Expose
    private int childAnswerID;

    @SerializedName("childTestID")
    @Expose
    private int childTestID;

    @SerializedName("answerID")
    @Expose
    private int answerID;


    public ChildAnswer(int childAnswerID, int childTestID, int answerID) {
        this.childAnswerID = childAnswerID;
        this.childTestID = childTestID;
        this.answerID = answerID;
    }

    public ChildAnswer(int answerID) {
        this.answerID = answerID;
    }
    public ChildAnswer(JSONObject object) throws JSONException
    {
        childAnswerID = object.getInt("childAnswerID");
        childTestID = object.getInt("childTestID");
        answerID = object.getInt("answerID");
    }


    public int getChildAnswerID() {
        return childAnswerID;
    }

    public void setChildAnswerID(int childAnswerID) {
        this.childAnswerID = childAnswerID;
    }

    public int getChildTestID() {
        return childTestID;
    }

    public void setChildTestID(int childTestID) {
        this.childTestID = childTestID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }
}
