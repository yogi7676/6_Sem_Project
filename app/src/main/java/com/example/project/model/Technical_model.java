package com.example.project.model;

public class Technical_model
{
    String FromTime,ToTime,Date,EventName,BookingID,EventDepartment,GuestName,GuestCount,Chairs,Bells,Remuneration,Sapling,GalleryName,GalleryFloor,GalleryBlock,UserName,PhoneNo;
    String Rose,Bouquet,Momento,StandMike,Cordless,Colar,WaterBottles,Transportation,Others,Laptop,Projector,FoodSnacks;
    String GalleryID,UserID;
    String EventCoordinator,Period;

    public Technical_model() {
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public Technical_model(String period, String eventCoordinator, String galleryid, String userid, String fromTime, String toTime, String date, String eventName, String bookingID, String eventDepartment, String guestName, String guestCount, String chairs, String bells, String remuneration, String sapling, String galleryName, String galleryFloor, String galleryBlock, String userName, String phoneNo, String rose, String bouquet, String momento, String standMike, String cordless, String colar, String waterBottles, String transportation, String others, String laptop, String projector, String foodSnacks) {
        EventCoordinator=eventCoordinator;
        Period=period;
        GalleryID=galleryid;
        UserID=userid;
        FromTime = fromTime;
        ToTime = toTime;
        Date = date;
        EventName = eventName;
        BookingID = bookingID;
        EventDepartment = eventDepartment;
        GuestName = guestName;
        GuestCount = guestCount;
        Chairs = chairs;
        Bells = bells;
        Remuneration = remuneration;
        Sapling = sapling;
        GalleryName = galleryName;
        GalleryFloor = galleryFloor;
        GalleryBlock = galleryBlock;
        UserName = userName;
        PhoneNo = phoneNo;
        Rose = rose;
        Bouquet = bouquet;
        Momento = momento;
        StandMike = standMike;
        Cordless = cordless;
        Colar = colar;
        WaterBottles = waterBottles;
        Transportation = transportation;
        Others = others;
        Laptop = laptop;
        Projector = projector;
        FoodSnacks = foodSnacks;
    }

    public String getEventCoordinator() {
        return EventCoordinator;
    }

    public void setEventCoordinator(String eventCoordinator) {
        EventCoordinator = eventCoordinator;
    }

    public String getFromTime() {
        return FromTime;
    }

    public String getGalleryID() {
        return GalleryID;
    }

    public void setGalleryID(String galleryID) {
        GalleryID = galleryID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
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

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getEventDepartment() {
        return EventDepartment;
    }

    public void setEventDepartment(String eventDepartment) {
        EventDepartment = eventDepartment;
    }

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        GuestName = guestName;
    }

    public String getGuestCount() {
        return GuestCount;
    }

    public void setGuestCount(String guestCount) {
        GuestCount = guestCount;
    }

    public String getChairs() {
        return Chairs;
    }

    public void setChairs(String chairs) {
        Chairs = chairs;
    }

    public String getBells() {
        return Bells;
    }

    public void setBells(String bells) {
        Bells = bells;
    }

    public String getRemuneration() {
        return Remuneration;
    }

    public void setRemuneration(String remuneration) {
        Remuneration = remuneration;
    }

    public String getSapling() {
        return Sapling;
    }

    public void setSapling(String sapling) {
        Sapling = sapling;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getRose() {
        return Rose;
    }

    public void setRose(String rose) {
        Rose = rose;
    }

    public String getBouquet() {
        return Bouquet;
    }

    public void setBouquet(String bouquet) {
        Bouquet = bouquet;
    }

    public String getMomento() {
        return Momento;
    }

    public void setMomento(String momento) {
        Momento = momento;
    }

    public String getStandMike() {
        return StandMike;
    }

    public void setStandMike(String standMike) {
        StandMike = standMike;
    }

    public String getCordless() {
        return Cordless;
    }

    public void setCordless(String cordless) {
        Cordless = cordless;
    }

    public String getColar() {
        return Colar;
    }

    public void setColar(String colar) {
        Colar = colar;
    }

    public String getWaterBottles() {
        return WaterBottles;
    }

    public void setWaterBottles(String waterBottles) {
        WaterBottles = waterBottles;
    }

    public String getTransportation() {
        return Transportation;
    }

    public void setTransportation(String transportation) {
        Transportation = transportation;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }

    public String getLaptop() {
        return Laptop;
    }

    public void setLaptop(String laptop) {
        Laptop = laptop;
    }

    public String getProjector() {
        return Projector;
    }

    public void setProjector(String projector) {
        Projector = projector;
    }

    public String getFoodSnacks() {
        return FoodSnacks;
    }

    public void setFoodSnacks(String foodSnacks) {
        FoodSnacks = foodSnacks;
    }
}
