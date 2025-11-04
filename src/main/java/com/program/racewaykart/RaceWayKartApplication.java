package com.program.racewaykart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RaceWayKartApplication extends Application {

    @Override
    public void start(Stage appStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RaceWayKartApplication.class.getResource("drivers-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        appStage.setTitle("RaceWayKart");

        appStage.getIcons().clear();
        appStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        appStage.setResizable(false);

        appStage.setScene(scene);

        appStage.setOnCloseRequest(_ -> System.exit(0));

        appStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}