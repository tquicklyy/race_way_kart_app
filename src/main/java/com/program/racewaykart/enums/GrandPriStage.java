package com.program.racewaykart.enums;

public enum GrandPriStage {
    RACE("Этап: Гонка"),
    QUALIFICATION("Этап: Квалификация");

    private final String label;

    GrandPriStage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
