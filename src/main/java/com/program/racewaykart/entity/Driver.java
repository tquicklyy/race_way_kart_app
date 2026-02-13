package com.program.racewaykart.entity;

import java.io.Serializable;
import java.util.Objects;

public class Driver implements Serializable {

    private int raceGroup;

    private int ID;

    private final String surname;

    private final String name;

    private final String patronymic;

    private Kart currentKart;

    public Driver(int ID, String surname, String name, String patronymic) {
        this.ID = ID;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic.isBlank() ? "Отсутствует" : patronymic;
        this.raceGroup = -1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Kart getCurrentKart() {
        return currentKart;
    }

    public void setCurrentKart(Kart currentKart) {
        this.currentKart = currentKart;
    }

    public int getRaceGroup() {
        return raceGroup;
    }

    public void setRaceGroup(int raceGroup) {
        this.raceGroup = raceGroup;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Driver driver = (Driver) object;
        return ID == driver.ID && Objects.equals(surname, driver.surname) && Objects.equals(name, driver.name) && Objects.equals(patronymic, driver.patronymic) && Objects.equals(currentKart, driver.currentKart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, surname, name, patronymic, currentKart);
    }
}
