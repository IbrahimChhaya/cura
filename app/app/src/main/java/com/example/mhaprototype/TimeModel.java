package com.example.mhaprototype;

public class TimeModel {
    private float ticks;
    private float days;
    private float hours;
    private float milliseconds;
    private float minutes;
    private float seconds;
    private float totalDays;
    private float totalHours;
    private float totalMilliseconds;
    private float totalMinutes;
    private float totalSeconds;


    // Getter Methods

    public TimeModel( float hours, float minutes,  float totalHours) {

        this.hours = hours;
        this.minutes = minutes;
        this.totalHours = totalHours;

    }

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

