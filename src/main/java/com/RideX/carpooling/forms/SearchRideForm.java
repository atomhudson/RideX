package com.RideX.carpooling.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchRideForm {
    private String from;
    private String to;
    private String fromCity;
    private String toCity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getFrom() {
        if (from != null && !from.trim().isEmpty()) {
            return from.trim();
        }
        return fromCity != null ? fromCity.trim() : "";
    }

    public void setFrom(String from) {
        this.from = from;
        if (this.fromCity == null || this.fromCity.trim().isEmpty()) {
            this.fromCity = from;
        }
    }

    public String getTo() {
        if (to != null && !to.trim().isEmpty()) {
            return to.trim();
        }
        return toCity != null ? toCity.trim() : "";
    }

    public void setTo(String to) {
        this.to = to;
        if (this.toCity == null || this.toCity.trim().isEmpty()) {
            this.toCity = to;
        }
    }

    public String getFromCity() {
        return getFrom();
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
        if (this.from == null || this.from.trim().isEmpty()) {
            this.from = fromCity;
        }
    }

    public String getToCity() {
        return getTo();
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
        if (this.to == null || this.to.trim().isEmpty()) {
            this.to = toCity;
        }
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
                "from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", date=" + date +
                '}';
    }
}
