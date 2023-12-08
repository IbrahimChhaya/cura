package PsychologistList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkModel {

    @SerializedName("LinkID")
    @Expose
    private int LinkID;

    @SerializedName("PairID")
    @Expose
    private int PairID;

    @SerializedName("PsychID")
    @Expose
    private int PsychID;

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Rating")
    @Expose
    private int Rating;

    public int getLinkID() {
        return LinkID;
    }

    public void setLinkID(int linkID) {
        LinkID = linkID;
    }

    public int getPairID() {
        return PairID;
    }

    public void setPairID(int pairID) {
        PairID = pairID;
    }

    public int getPsychID() {
        return PsychID;
    }

    public void setPsychID(int psychID) {
        PsychID = psychID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
