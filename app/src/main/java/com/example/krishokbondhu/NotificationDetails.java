package com.example.krishokbondhu;

public class NotificationDetails {
    private String date;
    private String info;

    public NotificationDetails(String date, String info) {
        this.date = date;
        this.info = info;
    }
    public String getDate() {
        return date;
    }
    public String getInfo() {return info;}
}
