package com.program.racewaykart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroupsSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label createGroupsButton;

    @FXML
    private VBox generalVBox;

    @FXML
    private Label groupsButton;

    @FXML
    private VBox groupsVBox;

    @FXML
    private HBox headerButtonsHBox;

    @FXML
    private HBox headersTableHBox;

    @FXML
    private HBox headersTableHBox1;

    @FXML
    private Label kartsButton;

    @FXML
    private ImageView logoImage;

    @FXML
    private TextField numberOfGroupsInput;

    @FXML
    private Label resetAllButton;

    @FXML
    private Label saveInTxtButton;

    @FXML
    private Label saveSerialButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox upperHBox;

    @FXML
    private VBox scrollPaneVBox;

    @FXML
    void goToKartsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("karts-view.fxml"));
        Scene kartsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(kartsScene);
    }

    @FXML
    void goToDriversScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("drivers-view.fxml"));
        Scene driversScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(driversScene);
    }

    @FXML
    void initialize() {
        scrollPaneVBox.getChildren().clear();
    }

}
