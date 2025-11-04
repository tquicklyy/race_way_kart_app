package com.program.racewaykart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.entity.Kart;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class DriversSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label addDataButton;

    @FXML
    private VBox addDataVBox;

    @FXML
    private HBox centerHBox;

    @FXML
    private VBox dataVBox;

    @FXML
    private VBox generalVBox;

    @FXML
    private Label groupsButton;

    @FXML
    private Label groupsButton3111;

    @FXML
    private Label groupsButton31111;

    @FXML
    private HBox headerButtonsHBox;

    @FXML
    private HBox headersTableHBox;

    @FXML
    private Label kartsButton;

    @FXML
    private ImageView logoImage;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField patronymicInput;

    @FXML
    private Label resetAllButton;

    @FXML
    private Label resetDriversButton;

    @FXML
    private Label saveSerialButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField surnameInput;

    @FXML
    private VBox tableVBox;

    @FXML
    private HBox upperHBox;

    public static ArrayList<Driver> DRIVERS = new ArrayList<>();

    @FXML
    void initialize() {
        dataVBox.getChildren().clear();

        if(!DRIVERS.isEmpty()) {
            for(Driver driver: DRIVERS) {
                createRow(driver);
            }
        }

        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
    }

    @FXML
    void goToKartsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("karts-view.fxml"));
        Scene kartsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(kartsScene);
    }

    @FXML
    void resetData(MouseEvent event) {
        DRIVERS.clear();
        dataVBox.getChildren().clear();
        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
    }

    @FXML
    void addData(MouseEvent event) {
        if(!isValidNameSurnamePatronymic(surnameInput.getText(), nameInput.getText())) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления водителя.", "Данные о водителе введены некорректно.");
            return;
        }

        Driver newDriver = addDataToDrivers();
        createRow(newDriver);
        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
        clearInputs();
    }

    void createRow(Driver newDriver) {
        HBox rowHBox = createHBoxRow();
        Label labelID = createIDLabelRow(newDriver.getID());
        Label labelSurname = createBlueLabelRow(newDriver.getSurname());
        Label labelName = createBlueLabelRow(newDriver.getName());
        Label labelPatronymic = createBlueLabelRow(newDriver.getPatronymic());
        Label labelDelete = createDeleteButtonRow(rowHBox, newDriver);

        rowHBox.getChildren().addAll(labelID, labelSurname, labelName, labelPatronymic, labelDelete);
        dataVBox.getChildren().add(rowHBox);
    }

    HBox createHBoxRow() {
        return new HBox();
    }

    Label createIDLabelRow(int ID) {
        Label label = new Label(String.valueOf(ID));
        label.getStyleClass().addAll("blue-white-button", "id-header");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        HBox.setMargin(label, new Insets(0,5,0,0));
        return label;
    }

    Label createBlueLabelRow(String text) {
        Label label = new Label(text);
        label.getStyleClass().addAll("blue-white-button", "data-items");

        label.setMinWidth(137);
        label.setMinHeight(35);
        label.setMaxWidth(137);
        label.setMaxHeight(35);

        HBox.setMargin(label, new Insets(0,5,0,0));
        return label;
    }

    Label createDeleteButtonRow(HBox parentToDelete, Driver driverToDelete) {
        Label label = new Label("X");
        label.getStyleClass().addAll("red-white-button", "id-header", "label-button");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        label.setOnMouseClicked(_ -> {
            Platform.runLater(() -> {
                dataVBox.getChildren().remove(parentToDelete);
                DRIVERS.remove(driverToDelete);
                hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
            });
        });

        return label;
    }

    void hideHeadersTableAndScrollPane(boolean isEmpty) {
        if(isEmpty) {
            headersTableHBox.setVisible(false);
            headersTableHBox.setDisable(true);
            scrollPane.setVisible(false);
            scrollPane.setDisable(true);
        } else {
            headersTableHBox.setVisible(true);
            headersTableHBox.setDisable(false);
            scrollPane.setVisible(true);
            scrollPane.setDisable(false);
        }
    }

    void clearInputs() {
        nameInput.setText("");
        surnameInput.setText("");
        patronymicInput.setText("");
    }

    Driver addDataToDrivers() {
        Driver newDriver = new Driver(DRIVERS.size() + 1, surnameInput.getText(), nameInput.getText(), patronymicInput.getText());
        DRIVERS.add(newDriver);
        return newDriver;
    }

    boolean isValidNameSurnamePatronymic(String surname, String name) {
        return !surname.isBlank() && !name.isBlank();

    }

}
