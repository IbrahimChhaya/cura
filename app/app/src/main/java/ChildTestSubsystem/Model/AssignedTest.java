package ChildTestSubsystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ChildTestSubsystem.Test;

public class AssignedTest {

    @SerializedName("childTestID")
    @Expose
    private int childTestID;

    @SerializedName("testAssigned")
    @Expose
    private Test testAssigned;


    public int getChildTestID() {
        return childTestID;
    }

    public void setChildTestID(int childTestID) {
        this.childTestID = childTestID;
    }

    public Test getTestAssigned() {
        return testAssigned;
    }

    public void setTestAssigned(Test testAssigned) {
        this.testAssigned = testAssigned;
    }
}
