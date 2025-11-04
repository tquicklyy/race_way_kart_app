package com.program.racewaykart.entity;

public class Kart {

    private int ID;

    private int numberOfKart;

    public Kart(int ID, int numberOfKart) {
        this.ID = ID;
        this.numberOfKart = numberOfKart;
    }

    public Kart(int numberOfKart) {
        this.numberOfKart = numberOfKart;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumberOfKart() {
        return numberOfKart;
    }

    public void setNumberOfKart(int numberOfKart) {
        this.numberOfKart = numberOfKart;
    }
}
