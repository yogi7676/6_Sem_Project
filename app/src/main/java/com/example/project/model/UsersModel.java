package com.example.project.model;

public class UsersModel {
    String Block,Email,Name,Phone,type,UID;

    public UsersModel(String id,String block, String email, String name, String phone, String type) {
        Block = block;
        UID=id;
        Email = email;
        Name = name;
        Phone = phone;
        this.type = type;
    }

    public UsersModel() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
