package com.program.racewaykart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {

    private int ID;

    private List<Driver> drivers = new ArrayList<>();

    private List<Kart> freeCarts;

    public Group(List<Kart> freeCarts, int ID) {
        this.freeCarts = freeCarts;
        this.ID = ID;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Kart> getFreeCarts() {
        return freeCarts;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void clearGroup() {
        for (Driver driver: drivers) {
            driver.setCurrentKart(null);
        }
    }
}
