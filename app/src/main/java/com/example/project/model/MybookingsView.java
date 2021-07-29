package com.example.project.model;

public class MybookingsView
{
    String GalleryName,GalleryFloor,GalleryBlock,Period,Date,EventName,BookingID;

    public MybookingsView(String period,String bookingID,String galleryName, String galleryFloor, String galleryBlock,String date, String eventName) {
        GalleryName = galleryName;
        BookingID=bookingID;
        Period=period;
        GalleryFloor = galleryFloor;
        GalleryBlock = galleryBlock;
        Date = date;
        EventName = eventName;
    }

    public MybookingsView() {
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getGalleryName() {
        return GalleryName;
    }

    public void setGalleryName(String galleryName) {
        GalleryName = galleryName;
    }

    public String getGalleryFloor() {
        return GalleryFloor;
    }

    public void setGalleryFloor(String galleryFloor) {
        GalleryFloor = galleryFloor;
    }

    public String getGalleryBlock() {
        return GalleryBlock;
    }

    public void setGalleryBlock(String galleryBlock) {
        GalleryBlock = galleryBlock;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
