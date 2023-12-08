package PsychologistCalendar;

public class calendarItem {

    private int calItemImage;
    private String calItemTitle;
    private String calItemDesc;
    private String calItemBookingTime;

    public calendarItem(String calItemTitle, String calItemDesc, String calItemBookingTime) {
        this.calItemTitle = calItemTitle;
        this.calItemDesc = calItemDesc;
        this.calItemBookingTime = calItemBookingTime;
    }

    public int getCalItemImage() {
        return calItemImage;
    }

    public void setCalItemImage(int calItemImage) {
        this.calItemImage = calItemImage;
    }

    public String getCalItemTitle() {
        return calItemTitle;
    }

    public void setCalItemTitle(String calItemTitle) {
        this.calItemTitle = calItemTitle;
    }

    public String getCalItemDesc() {
        return calItemDesc;
    }

    public void setCalItemDesc(String calItemDesc) {
        this.calItemDesc = calItemDesc;
    }

    public String getCalItemBookingTime() {
        return calItemBookingTime;
    }

    public void setCalItemBookingTime(String calItemBookingTime) {
        this.calItemBookingTime = calItemBookingTime;
    }
}
