package com.dal.travelapp;

import java.io.Serializable;

public class Booking implements Serializable {
    private String compny_logo, flight_name, flight_code, depart_plc, destination_plc, depart_time, destination_time, pls_day, totl_hour, no_stops, price;

    public String getFlight_name() {
        return flight_name;
    }

    public String getCompny_logo() {
        return compny_logo;
    }

    public void setCompny_logo(String compny_logo) {
        this.compny_logo = compny_logo;
    }

    public void setFlight_name(String flight_name) {
        this.flight_name = flight_name;
    }

    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }

    public String getDepart_plc() {
        return depart_plc;
    }

    public void setDepart_plc(String depart_plc) {
        this.depart_plc = depart_plc;
    }

    public String getDestination_plc() {
        return destination_plc;
    }

    public void setDestination_plc(String destination_plc) {
        this.destination_plc = destination_plc;
    }

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = depart_time;
    }

    public String getDestination_time() {
        return destination_time;
    }

    public void setDestination_time(String destination_time) {
        this.destination_time = destination_time;
    }

    public String getPls_day() {
        return pls_day;
    }

    public void setPls_day(String pls_day) {
        this.pls_day = pls_day;
    }

    public String getTotl_hour() {
        return totl_hour;
    }

    public void setTotl_hour(String totl_hour) {
        this.totl_hour = totl_hour;
    }

    public String getNo_stops() {
        return no_stops;
    }

    public void setNo_stops(String no_stops) {
        this.no_stops = no_stops;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
