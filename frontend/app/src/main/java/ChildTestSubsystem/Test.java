package ChildTestSubsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("testID")
    @Expose
    private int testID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("psychID")
    @Expose
    private int psychID;


    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }
}
