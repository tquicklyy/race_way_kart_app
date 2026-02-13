package com.program.racewaykart.controller.general;

import com.program.racewaykart.RaceWayKartApplication;
import com.program.racewaykart.entity.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DriversGeneralController {

    protected abstract void initialize();

    public static List<Driver> DRIVERS = new ArrayList<>();

    @FXML
    void goToKartsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RaceWayKartApplication.PATH_TO_KARTS_FXML));
        Scene kartsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(kartsScene);
    }

    @FXML
    void resetAllData() {
        RaceWayKartApplication.resetAllData();
        initialize();
    }

    protected boolean isValidNameSurnamePatronymic(String surname, String name) {
        return !surname.isBlank() && !name.isBlank();
    }

}
