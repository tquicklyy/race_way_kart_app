package com.program.racewaykart.entity;

import java.util.List;

public class Group {

    private List<Driver> drivers;

    private List<Kart> freeCarts;

    public Group(List<Kart> freeCarts) {
        this.freeCarts = freeCarts;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Kart> getFreeCarts() {
        return freeCarts;
    }

    public void setFreeCarts(List<Kart> freeCarts) {
        this.freeCarts = freeCarts;
    }
}
