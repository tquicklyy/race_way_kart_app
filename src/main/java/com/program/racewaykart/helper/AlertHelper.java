package com.program.racewaykart.helper;

import com.program.racewaykart.RaceWayKartApplication;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class AlertHelper {

    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.initOwner(RaceWayKartApplication.appStage);
        alert.initModality(Modality.WINDOW_MODAL);

        alert.showAndWait();
    }

}
