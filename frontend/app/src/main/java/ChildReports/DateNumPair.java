package ChildReports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateNumPair {

    @SerializedName("weekStartDate")
    @Expose
    private String weekStartDate;

    @SerializedName("count")
    @Expose
    private int count;


    public String getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(String weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
