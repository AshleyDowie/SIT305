package com.example.task91p.model;

public class Advert {
    private int advertId;
    private String advertName;
    private String advertPhone;
    private String advertDescription;
    private String advertDate;
    private String advertLocation;
    private String advertPostType;

    public Advert(String name, String phone, String description, String date, String location, String postType) {
        this.advertName = name;
        this.advertPhone = phone;
        this.advertDescription = description;
        this.advertDate = date;
        this.advertLocation = location;
        this.advertPostType = postType;
    }

    public Advert(int id, String name, String phone, String description, String date, String location, String postType) {
        this.advertId = id;
        this.advertName = name;
        this.advertPhone = phone;
        this.advertDescription = description;
        this.advertDate = date;
        this.advertLocation = location;
        this.advertPostType = postType;
    }

    public int getAdvertId() {
        return advertId;
    }

    public String getAdvertName() {
        return advertName;
    }

    public String getAdvertPhone() {
        return advertPhone;
    }

    public String getAdvertDescription() {
        return advertDescription;
    }

    public String getAdvertDate() {
        return advertDate;
    }

    public String getAdvertLocation() {
        return advertLocation;
    }

    public String getAdvertPostType() {
        return advertPostType;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public void setAdvertPhone(String advertPhone) {
        this.advertPhone = advertPhone;
    }

    public void setAdvertDescription(String advertDescription) {
        this.advertDescription = advertDescription;
    }

    public void setAdvertDate(String advertDate) {
        this.advertDate = advertDate;
    }

    public void setAdvertLocation(String advertLocation) {
        this.advertLocation = advertLocation;
    }

    public void setAdvertPostType(String advertPostType) {
        this.advertPostType = advertPostType;
    }
}
