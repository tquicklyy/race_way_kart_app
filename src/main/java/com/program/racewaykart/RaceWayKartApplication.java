package com.program.racewaykart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RaceWayKartApplication extends Application {

    public static Stage appStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RaceWayKartApplication.class.getResource("drivers-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        appStage = stage;

        appStage.setTitle("RaceWayKart");

        appStage.getIcons().clear();
        appStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        appStage.setResizable(false);

        appStage.setScene(scene);

        appStage.setOnCloseRequest(_ -> System.exit(0));
        appStage.toFront();
        appStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}