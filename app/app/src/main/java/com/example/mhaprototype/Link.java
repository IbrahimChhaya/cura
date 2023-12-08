package com.example.mhaprototype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Link {

    @SerializedName("linkID")
    @Expose
    private int linkID;

    @SerializedName("pairID")
    @Expose
    private int pairID;

    @SerializedName("psychID")
    @Expose
    private int psychID;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("dateLinked")
    @Expose
    private String dateLinked;


    public int getLinkID() {
        return linkID;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public int getPairID() {
        return pairID;
    }

    public void setPairID(int pairID) {
        this.pairID = pairID;
    }

    public int getPsychID() {
        return psychID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateLinked() {
        return dateLinked;
    }

    public void setDateLinked(String dateLinked) {
        this.dateLinked = dateLinked;
    }
}
