package com.dal.travelapp;

import java.io.Serializable;

 class Traveller implements Serializable {
    String first_name, last_name;

    String getFirst_name() {
        return first_name;
    }

    void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    String getLast_name() {
        return last_name;
    }

    void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
