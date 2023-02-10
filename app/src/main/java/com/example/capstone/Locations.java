package com.example.capstone;

import android.location.Location;

public class Locations {

    String Name;
    Double Xcoordinate;
    Double Ycoordinate;
    String Info;
    String History;

    public Locations(String name, Double xcoordinate, Double ycoordinate, String info, String history) {
        Name = name;
        Xcoordinate = xcoordinate;
        Ycoordinate = ycoordinate;
        Info = info;
        History = history;
    }

    public String getName() {
        return Name;
    }

    public Double getXcoordinate() {
        return Xcoordinate;
    }

    public Double getYcoordinate() {
        return Ycoordinate;
    }

    public String getInfo() {
        return Info;
    }

    public String getHistory() {
        return History;
    }

    Locations Threem = new Locations("Mary Minahan McCormick Residence Hall", 12031223.2112122, 12312312.31231231, "freshman and sophmore housing", "I lived here first semester of freshman year" );
}
