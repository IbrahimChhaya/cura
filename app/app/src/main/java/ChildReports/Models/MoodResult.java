package ChildReports.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class MoodResult {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("moodValue")
    @Expose
    private int moodValue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMoodValue() {
        return moodValue;
    }

    public void setMoodValue(int moodValue) {
        this.moodValue = moodValue;
    }
}
