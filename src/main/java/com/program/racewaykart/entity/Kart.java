package com.program.racewaykart.entity;

import java.io.Serializable;
import java.util.Objects;

public class Kart implements Serializable {

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Kart kart = (Kart) object;
        return ID == kart.ID && numberOfKart == kart.numberOfKart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, numberOfKart);
    }
}
