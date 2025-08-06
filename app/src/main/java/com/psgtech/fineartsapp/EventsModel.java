package com.psgtech.fineartsapp;

public class EventsModel {
    String name, date, incharge;

    public EventsModel() {}

    public EventsModel(String name, String date, String incharge) {
        this.name = name;
        this.date = date;
        this.incharge = incharge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }
}
