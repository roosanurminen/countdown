package com.example.countdowntimer;

public class CountdownData {
    private final String title;
    private final String date;

    public CountdownData(String title, String date) {
        this.title = title;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
