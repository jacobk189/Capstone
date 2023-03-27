package com.example.capstone;

public class BuildingModel {

    private int ID;
    private String BUILDING_NAME;
    private String BUILDING_ADDRESS;
    private double BUILDING_LATITUDE;
    private double BUILDING_LONGITUDE;
    private String BUILDING_INFO;
    private String BUILDING_HISTORY;
    private String BUILDING_NICKNAME;

    public BuildingModel(int id, String name, String address, double latitude, double longitude, String info, String history, String nickname) {
        this.ID = id;
        this.BUILDING_NAME = name;
        this.BUILDING_ADDRESS = address;
        this.BUILDING_LATITUDE = latitude;
        this.BUILDING_LONGITUDE = longitude;
        this.BUILDING_INFO = info;
        this.BUILDING_HISTORY = history;
        this.BUILDING_NICKNAME = nickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getName() {
        return BUILDING_NAME;
    }

    public void setName(String name) {
        this.BUILDING_NAME = name;
    }

    public String getAddress() {
        return BUILDING_ADDRESS;
    }

    public void setAddress(String address) {
        this.BUILDING_ADDRESS = address;
    }

    public double getLatitude() {
        return BUILDING_LATITUDE;
    }

    public void setLatitude(double latitude) {
        this.BUILDING_LATITUDE = latitude;
    }

    public double getLongitude() {
        return BUILDING_LONGITUDE;
    }

    public void setLongitude(double longitude) {
        this.BUILDING_LONGITUDE = longitude;
    }

    public String getInfo() {
        return BUILDING_INFO;
    }

    public void setInfo(String info) {
        this.BUILDING_INFO = info;
    }

    public String getHistory() {
        return BUILDING_HISTORY;
    }

    public void setHistory(String history) {
        this.BUILDING_HISTORY = history;
    }

    public String getNickname() {
        return BUILDING_NICKNAME;
    }

    public void setNickname(String nickname) {
        this.BUILDING_NICKNAME = nickname;
    }

    public String toString() {
        return "BuildingModel{" +
                "buildingID=" + ID +
                ", buildingName='" + BUILDING_NAME + '\'' +
                ", buildingAddress='" + BUILDING_ADDRESS + '\'' +
                ", buildingLatitude=" + BUILDING_LATITUDE +
                ", buildingLongitude=" + BUILDING_LONGITUDE +
                ", buildingInfo='" + BUILDING_INFO + '\'' +
                ", buildingHistory='" + BUILDING_HISTORY + '\'' +
                ", buildingNickname='" + BUILDING_NICKNAME + '\'' +
                '}';
    }
}