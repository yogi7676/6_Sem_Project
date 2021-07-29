package com.example.project.model;

public class Galleriesmodel {

    private String Name,Seats,Block,Floor,Image,Pid;

    public Galleriesmodel()
    {

    }

    public Galleriesmodel(String name, String seats, String block, String floor, String image,String pid) {
        Name = name;
        Seats = seats;
        Block = block;
        Floor = floor;
        Image = image;
        Pid=pid;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
