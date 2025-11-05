package com.program.racewaykart.entity;

public class Driver {

    private int ID;

    private String surname;

    private String name;

    private String patronymic;

    private Kart currentKart;

    public Driver(int ID, String surname, String name, String patronymic) {
        this.ID = ID;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic.isBlank() ? "Отсутствует" : patronymic;
    }

    public Driver(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic.isBlank() ? "Отсутствует" : patronymic;
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

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Kart getCurrentKart() {
        return currentKart;
    }

    public void setCurrentKart(Kart currentKart) {
        this.currentKart = currentKart;
    }
}
