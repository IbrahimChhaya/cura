package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PsychologistCalendarModel {

    @SerializedName("psychCalendarID")
    @Expose
    private float psychCalendarID;

    @SerializedName("dayOfWeek")
    @Expose
    private String dayOfWeek;

    @SerializedName("singleStart")
    @Expose
    String singleStart;

    @SerializedName("singleEnd")
    @Expose
    String singleEnd;

    @SerializedName("repeatStart")
    @Expose
    String repeatStart;

    @SerializedName("repeatEnd")
    @Expose
    String repeatEnd;

    @SerializedName("psychID")
    @Expose
    private float psychID;

    @SerializedName("closed")
    @Expose
    private boolean closed;


    //Getters and setters


    public float getPsychCalendarID() {
        return psychCalendarID;
    }

    public void setPsychCalendarID(float psychCalendarID) {
        this.psychCalendarID = psychCalendarID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSingleStart() {
        return singleStart;
    }

    public void setSingleStart(String singleStart) {
        this.singleStart = singleStart;
    }

    public String getSingleEnd() {
        return singleEnd;
    }

    public void setSingleEnd(String singleEnd) {
        this.singleEnd = singleEnd;
    }

    public String getRepeatStart() {
        return repeatStart;
    }

    public void setRepeatStart(String repeatStart) {
        this.repeatStart = repeatStart;
    }

    public String getRepeatEnd() {
        return repeatEnd;
    }

    public void setRepeatEnd(String repeatEnd) {
        this.repeatEnd = repeatEnd;
    }

    public float getPsychID() {
        return psychID;
    }

    public void setPsychID(float psychID) {
        this.psychID = psychID;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
