package psychologistHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthBookingCount {

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("bookingCount")
    @Expose
    private int bookingCount;


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }
}
