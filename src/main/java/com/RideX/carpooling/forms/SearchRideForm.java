package com.RideX.carpooling.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchRideForm {
    private String from;
    private String to;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "SearchRideForm{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                '}';
    }
}
