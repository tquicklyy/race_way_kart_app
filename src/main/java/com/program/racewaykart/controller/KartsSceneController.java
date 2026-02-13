package com.program.racewaykart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.program.racewaykart.RaceWayKartApplication;
import com.program.racewaykart.entity.Kart;
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

public class KartsSceneController {

    @FXML
    private VBox dataVBox;

    @FXML
    private HBox headersTableHBox;

    @FXML
    private TextField numberKartInput;

    @FXML
    private ComboBox<String> grandPriStageComboBox;

    @FXML
    private Label saveSerialButton;

    @FXML
    private ScrollPane scrollPane;

    public static List<Kart> KARTS = new ArrayList<>();

    @FXML
    void initialize() {
        NodeHelper.updateGranPriComboBox(grandPriStageComboBox);
        displayDataItems();

        hideHeadersTableAndScrollPane(KARTS.isEmpty());
        updateSaveSerialButton();
    }

    @FXML
    void goToGroupsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/program/racewaykart/groups-qualification-view.fxml"));
        Scene groupsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(groupsScene);
    }

    @FXML
    void goToDriversScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/program/racewaykart/drivers-qualification-view.fxml"));
        Scene driversScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(driversScene);
    }

    @FXML
    void changeStatusOfDataSaving() {
        RaceWayKartApplication.isDataSaving = !RaceWayKartApplication.isDataSaving;
        updateSaveSerialButton();
    }

    @FXML
    void resetAllData() {
        RaceWayKartApplication.resetAllData();
        initialize();
    }

    @FXML
    void resetData() {
        KARTS.clear();
        dataVBox.getChildren().clear();
        hideHeadersTableAndScrollPane(KARTS.isEmpty());
    }

    @FXML
    void addData() {
        if(!isValidKartNumber(numberKartInput.getText())) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления карта.", "Данные о карте введены некорректно.");
            return;
        }

        try {
            Integer.parseInt(numberKartInput.getText());
        } catch (Exception e) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления карта.", "Введён недопустимо большой номер карта.");
            return;
        }

        if(!isKartNumberNotRepeated(Integer.parseInt(numberKartInput.getText()))) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления карта.", "Карт с таким номером уже добавлен.");
            return;
        }

        Kart newKart = addDataToKarts();
        createRow(newKart);
        hideHeadersTableAndScrollPane(KARTS.isEmpty());
        clearInputs();
    }

    void displayDataItems() {
        dataVBox.getChildren().clear();

        if(!KARTS.isEmpty()) {
            for(Kart kart: KARTS) {
                createRow(kart);
            }
        }
    }

    void createRow(Kart newKart) {
        HBox rowHBox = createHBoxRow();
        TextField textFieldId = createIDTextFieldRow(KARTS.indexOf(newKart) + 1);
        TextField textFieldNumberOfCart = createBlueTextFieldRow(String.valueOf(newKart.getNumberOfKart()));
        Label labelDelete = createDeleteButtonRow(rowHBox, newKart);

        rowHBox.getChildren().addAll(textFieldId, textFieldNumberOfCart, labelDelete);
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

        textField.setMinWidth(421);
        textField.setMinHeight(35);
        textField.setMaxWidth(421);
        textField.setMaxHeight(35);

        HBox.setMargin(textField, new Insets(0,5,0,0));
        return textField;
    }

    Label createDeleteButtonRow(HBox parentToDelete, Kart kartToDelete) {
        Label label = new Label("X");
        label.getStyleClass().addAll("red-white-button", "id-header", "label-button");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        label.setOnMouseClicked(_ -> Platform.runLater(() -> {
            dataVBox.getChildren().remove(parentToDelete);
            KARTS.remove(kartToDelete);
            displayDataItems();
            hideHeadersTableAndScrollPane(KARTS.isEmpty());
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
        numberKartInput.setText("");
    }

    Kart addDataToKarts() {
        Kart newKart = new Kart(Integer.parseInt(numberKartInput.getText()));
        KARTS.add(newKart);
        return newKart;
    }

    boolean isValidKartNumber(String numberOfCart) {
        return numberOfCart.matches("\\d+");
    }

    boolean isKartNumberNotRepeated(int number) {
        for(Kart kart: KARTS) {
            if(kart.getNumberOfKart() == number) return false;
        }
        return true;
    }

    void updateSaveSerialButton() {
        if(RaceWayKartApplication.isDataSaving) {
            saveSerialButton.setText("Не сохранять");
        } else {
            saveSerialButton.setText("Сохранять");
        }
    }
}