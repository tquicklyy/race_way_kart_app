package com.program.racewaykart.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.program.racewaykart.RaceWayKartApplication;
import com.program.racewaykart.controller.general.DriversGeneralController;
import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.enums.GrandPriStage;
import com.program.racewaykart.helper.AlertHelper;
import com.program.racewaykart.helper.NodeHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DriversQualificationSceneController extends DriversGeneralController {

    @FXML
    private VBox dataVBox;

    @FXML
    private HBox headersTableHBox;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField patronymicInput;

    @FXML
    private ComboBox<String> grandPriStageComboBox;

    @FXML
    private Label saveSerialButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField surnameInput;

    @FXML
    protected void initialize() {
        NodeHelper.initGranPriComboBox(grandPriStageComboBox);
        displayDataItems();

        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
        updateSaveSerialButton();
        setUpdateGranPriComboBox();
    }

    public void setUpdateGranPriComboBox() {
        grandPriStageComboBox.valueProperty().addListener((_, oldV, newV) -> {
            if(newV.equals(oldV)) return;
            FXMLLoader loader;
            try {
                loader = new FXMLLoader(RaceWayKartApplication.class.getResource(RaceWayKartApplication.PATH_TO_DRIVERS_RACE_FXML));
                RaceWayKartApplication.grandPriStage = GrandPriStage.RACE;
                GroupsQualificationSceneController.GROUPS.clear();
                Scene newScene = new Scene(loader.load());
                RaceWayKartApplication.appStage.setScene(newScene);
            } catch (IOException e) {
                System.out.println("Не удалось загрузить файл");
            }
        });
    }

    @FXML
    void goToGroupsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RaceWayKartApplication.PATH_TO_GROUPS_QUAL_FXML));
        Scene groupsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(groupsScene);
    }

    @FXML
    void changeStatusOfDataSaving() {
        RaceWayKartApplication.isDataSaving = !RaceWayKartApplication.isDataSaving;
        updateSaveSerialButton();
    }

    @FXML
    void resetData() {
        DRIVERS.clear();
        dataVBox.getChildren().clear();
        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
    }

    @FXML
    void addData() {
        if(!isValidNameSurnamePatronymic(surnameInput.getText(), nameInput.getText())) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления водителя.", "Данные о водителе введены некорректно.");
            return;
        }

        Driver newDriver = addDataToDrivers();
        createRow(newDriver);
        hideHeadersTableAndScrollPane(DRIVERS.isEmpty());
        clearInputs();
        displayDataItems();
    }

    void displayDataItems() {
        dataVBox.getChildren().clear();

        if(!DRIVERS.isEmpty()) {
            for(Driver driver: DRIVERS) {
                createRow(driver);
            }
        }
    }

    void createRow(Driver newDriver) {
        HBox rowHBox = createHBoxRow();
        TextField textFieldID = createIDTextFieldRow(newDriver.getID());
        TextField textFieldSurname = createBlueTextFieldRow(newDriver.getSurname());
        TextField textFieldName = createBlueTextFieldRow(newDriver.getName());
        TextField textFieldPatronymic = createBlueTextFieldRow(newDriver.getPatronymic());
        Label labelDelete = createDeleteButtonRow(rowHBox, newDriver);

        rowHBox.getChildren().addAll(textFieldID, textFieldSurname, textFieldName, textFieldPatronymic, labelDelete);
        dataVBox.getChildren().add(rowHBox);
    }

    HBox createHBoxRow() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        return hBox;
    }

    TextField createIDTextFieldRow(int ID) {
        TextField textField = new TextField(String.valueOf(ID));
        textField.setEditable(false);
        textField.getStyleClass().addAll("blue-white-button", "id-header", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(30);
        textField.setMinHeight(30);
        textField.setMaxWidth(30);
        textField.setMaxHeight(30);

        HBox.setMargin(textField, new Insets(0,5,0,0));
        return textField;
    }

    TextField createBlueTextFieldRow(String text) {
        TextField textField = new TextField(text);
        textField.setEditable(false);
        textField.getStyleClass().addAll("blue-white-button", "data-items", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(137);
        textField.setMinHeight(35);
        textField.setMaxWidth(137);
        textField.setMaxHeight(35);

        HBox.setMargin(textField, new Insets(0,5,0,0));
        return textField;
    }

    Label createDeleteButtonRow(HBox parentToDelete, Driver driverToDelete) {
        Label label = new Label("X");
        label.getStyleClass().addAll("red-white-button", "id-header", "label-button");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        label.setOnMouseClicked(_ -> Platform.runLater(() -> {
            dataVBox.getChildren().remove(parentToDelete);
            DRIVERS.remove(driverToDelete);
            displayDataItems();
            hideHeadersTableAndScrollPane(DRIVERS.isEmpty());

        }));

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
        ArrayList<Integer> takenId = new ArrayList<>();
        for(Driver driver: DRIVERS) {
            takenId.add(driver.getID());
        }

        int IDOfDriver = 1;
        while (takenId.contains(IDOfDriver)) {
            IDOfDriver++;
        }

        Driver newDriver = new Driver(IDOfDriver, surnameInput.getText(), nameInput.getText(), patronymicInput.getText());
        DRIVERS.add(IDOfDriver - 1, newDriver);
        return newDriver;
    }

    void updateSaveSerialButton() {
        if(RaceWayKartApplication.isDataSaving) {
            saveSerialButton.setText("Не сохранять");
        } else {
            saveSerialButton.setText("Сохранять");
        }
    }

}
