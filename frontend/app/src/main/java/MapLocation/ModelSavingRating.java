package MapLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSavingRating {

    @SerializedName("guardianID")
    @Expose
    private int guardianID;

    @SerializedName("psychID")
    @Expose
    private int psychID;

    @SerializedName("rating")
    @Expose
    private int rating;

    public ModelSavingRating(int GuardianID, int PsychID, int Rating){
        guardianID = GuardianID;
        psychID = PsychID;
        rating = Rating;
    }

    // Getter Methods

    public int getGuardianID() {
        return guardianID;
    }

    public int getPsychID() {
        return psychID;
    }

    public int getRating() {
        return rating;
    }

    // Setter Methods

    public void setGuardianID(int guardianID) {
        this.guardianID = guardianID;
    }

    public void setPsychID(int psychID) {
        this.psychID = psychID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
