package Appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentModel {

    @SerializedName("bookingInfo")
    @Expose
    private BookingModel bookingInfo;

    @SerializedName("patientName")
    @Expose
    private String patientName;

    public BookingModel getBookingInfo() {
        return bookingInfo;
    }

    public void setBookingInfo(BookingModel bookingInfo) {
        this.bookingInfo = bookingInfo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

class BookingModel {

    @SerializedName("bookingID")
    @Expose
    private float bookingID;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("time")
    @Expose
    Time time;

    @SerializedName("cancelled")
    @Expose
    private String cancelled;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("calendarID")
    @Expose
    private float calendarID;

    @SerializedName("pairID")
    @Expose
    private float pairID;


    // Getter Methods

    public float getBookingID() {
        return bookingID;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getCancelled() {
        return cancelled;
    }

    public String getType() {
        return type;
    }

    public float getCalendarID() {
        return calendarID;
    }

    public float getPairID() {
        return pairID;
    }

    // Setter Methods

    public void setBookingID(float bookingID) {
        this.bookingID = bookingID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(Time timeObject) {
        this.time = timeObject;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCalendarID(float calendarID) {
        this.calendarID = calendarID;
    }

    public void setPairID(float pairID) {
        this.pairID = pairID;
    }
}
class Time {
    @SerializedName("ticks")
    @Expose
    private float ticks;

    @SerializedName("days")
    @Expose
    private float days;

    @SerializedName("hours")
    @Expose
    private float hours;

    @SerializedName("milliseconds")
    @Expose
    private float milliseconds;

    @SerializedName("minutes")
    @Expose
    private float minutes;

    @SerializedName("seconds")
    @Expose
    private float seconds;

    @SerializedName("totalDays")
    @Expose
    private float totalDays;

    @SerializedName("totalHours")
    @Expose
    private float totalHours;

    @SerializedName("totalMilliseconds")
    @Expose
    private float totalMilliseconds;

    @SerializedName("totalMinutes")
    @Expose
    private float totalMinutes;

    @SerializedName("totalSeconds")
    @Expose
    private float totalSeconds;


    // Getter Methods

    public float getTicks() {
        return ticks;
    }

    public float getDays() {
        return days;
    }

    public float getHours() {
        return hours;
    }

    public float getMilliseconds() {
        return milliseconds;
    }

    public float getMinutes() {
        return minutes;
    }

    public float getSeconds() {
        return seconds;
    }

    public float getTotalDays() {
        return totalDays;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public float getTotalMilliseconds() {
        return totalMilliseconds;
    }

    public float getTotalMinutes() {
        return totalMinutes;
    }

    public float getTotalSeconds() {
        return totalSeconds;
    }

    // Setter Methods

    public void setTicks(float ticks) {
        this.ticks = ticks;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public void setMilliseconds(float milliseconds) {
        this.milliseconds = milliseconds;
    }

    public void setMinutes(float minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }

    public void setTotalDays(float totalDays) {
        this.totalDays = totalDays;
    }

    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }

    public void setTotalMilliseconds(float totalMilliseconds) {
        this.totalMilliseconds = totalMilliseconds;
    }

    public void setTotalMinutes(float totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public void setTotalSeconds(float totalSeconds) {
        this.totalSeconds = totalSeconds;
    }
}
