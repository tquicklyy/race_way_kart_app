package com.program.racewaykart.helper;

import com.program.racewaykart.RaceWayKartApplication;
import com.program.racewaykart.enums.GrandPriStage;
import javafx.scene.control.ComboBox;

public class NodeHelper {

    public static void initGranPriComboBox(ComboBox<String> comboBox) {
        if(RaceWayKartApplication.grandPriStage == GrandPriStage.QUALIFICATION) {
            comboBox.getItems().add(GrandPriStage.QUALIFICATION.getLabel());
            comboBox.getSelectionModel().selectFirst();
            comboBox.getItems().add(GrandPriStage.RACE.getLabel());
        } else {
            comboBox.getItems().add(GrandPriStage.RACE.getLabel());
            comboBox.getSelectionModel().selectFirst();
            comboBox.getItems().add(GrandPriStage.QUALIFICATION.getLabel());
        }
    }
}
