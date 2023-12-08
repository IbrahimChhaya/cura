package Appointment;

public class SubItem {
    private int subItemImage;
    private String subItemTitle;
    private String subItemDesc;
    private String subItemBookingTime;
    public String subItemBookingDate;
    private String subItemBookingStatus;
    private String subItemBookingType;

    //adding the ID
    private int bookingID;



    public SubItem(String subItemTitle, String subItemDesc, String bookingTime, int bookingID,
                   String subItemBookingDate,String subItemBookingStatus,String subItemBookingType ) {
        this.subItemTitle = subItemTitle;
        this.subItemDesc = subItemDesc;
        this.subItemBookingTime = bookingTime;
        this.bookingID=bookingID;
        this.subItemBookingDate = subItemBookingDate;
        this.subItemBookingStatus = subItemBookingStatus;
        this.subItemBookingType = subItemBookingType;
    }

    public String getSubItemBookingType() {
        return subItemBookingType;
    }

    public void setSubItemBookingType(String subItemBookingType) {
        this.subItemBookingType = subItemBookingType;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getSubItemBookingTime() {
        return subItemBookingTime;
    }

    public void setSubItemBookingTime(String subItemBookingTime) {
        this.subItemBookingTime = subItemBookingTime;
    }


    public int getSubItemImage() {
        return subItemImage;
    }

    public void setSubItemImage(int subItemImage) {
        this.subItemImage = subItemImage;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

    public String getSubItemDesc() {
        return subItemDesc;
    }

    public void setSubItemDesc(String subItemDesc) {
        this.subItemDesc = subItemDesc;
    }

    public String getSubItemBookingDate() {
        return subItemBookingDate;
    }

    public void setSubItemBookingDate(String subItemBookingDate) {
        this.subItemBookingDate = subItemBookingDate;
    }

    public String getSubItemBookingStatus() {
        return subItemBookingStatus;
    }

    public void setSubItemBookingStatus(String subItemBookingStatus) {
        this.subItemBookingStatus = subItemBookingStatus;
    }
}
