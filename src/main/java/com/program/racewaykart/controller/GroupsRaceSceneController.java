package com.program.racewaykart.controller;

import com.program.racewaykart.RaceWayKartApplication;
import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.entity.Group;
import com.program.racewaykart.entity.Kart;
import com.program.racewaykart.helper.AlertHelper;
import com.program.racewaykart.helper.NodeHelper;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsRaceSceneController {

    @FXML
    private Label saveSerialButton;

    @FXML
    private ComboBox<String> grandPriStageComboBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox scrollPaneVBox;

    public static List<Group> GROUPS = new ArrayList<>();

    @FXML
    void goToKartsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/program/racewaykart/karts-view.fxml"));
        Scene kartsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(kartsScene);
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
    void resetData() {
        for(Group group: GROUPS) group.clearGroup();
        GROUPS.clear();

        scrollPaneVBox.getChildren().clear();

        hideHeadersTableAndScrollPane(GROUPS.isEmpty());
    }

    @FXML
    void resetAllData() {
        RaceWayKartApplication.resetAllData();
        initialize();
    }

    @FXML
    void onSaveInTxtButton() {
        if(GROUPS.isEmpty()) {
            AlertHelper.showErrorAlert("Ошибка сохранения", "Ошибка сохранения данных", "Отсутствуют группы для сохранения в текстовый файл.");
            return;
        }

        FileChooser saveTxtChooser = new FileChooser();
        saveTxtChooser.setTitle("Сохранить группы");

        FileChooser.ExtensionFilter filterTxt = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        saveTxtChooser.getExtensionFilters().clear();
        saveTxtChooser.getExtensionFilters().add(filterTxt);

        File txtFileToSave = saveTxtChooser.showSaveDialog(RaceWayKartApplication.appStage);

        if(txtFileToSave != null) {
            try (FileWriter fw = new FileWriter(txtFileToSave)) {
                fw.write("");
            } catch (Exception e) {
                AlertHelper.showErrorAlert("Ошибка сохранения", "Ошибка сохранения данных", "Не удалось сохранить данные в файл .txt");
            }

            int lengthOfNumber = 2;
            int lengthOfID = 2;
            int lengthOfSurname = "Фамилия".length();
            int lengthOfName = "Имя".length();
            int lengthOfPatronymic = "Отсутствует".length();
            int lengthOfNumberOfCart = "Номер карта".length();

            for (Group group: GROUPS) {
                for (Driver driver: group.getDrivers()) {
                    if(String.valueOf(group.getID()).length() > lengthOfNumber) lengthOfNumber = String.valueOf(group.getID()).length();
                    if(String.valueOf(driver.getID()).length() > lengthOfID) lengthOfID = String.valueOf(driver.getID()).length();
                    if(driver.getSurname().length() > lengthOfSurname) lengthOfSurname = driver.getSurname().length();
                    if(driver.getName().length() > lengthOfName) lengthOfName = driver.getName().length();
                    if(driver.getPatronymic().length() > lengthOfPatronymic) lengthOfPatronymic = driver.getPatronymic().length();
                }
            }

            try(FileWriter writer = new FileWriter(txtFileToSave, true)) {
                String mainHeader = "Группы." + System.lineSeparator() + System.lineSeparator();
                writer.write(mainHeader);

                for (Group group: GROUPS) {
                    String header = String.format("Группа №%s.", group.getID()) + System.lineSeparator();
                    String headerItems = String.format("%-" + lengthOfNumber +"s", "№")
                            + "  " + String.format("%-" + lengthOfID +"s", "ID")
                            + "   " + String.format("%-" + lengthOfSurname +"s", "Фамилия")
                            + "   " + String.format("%-" + lengthOfName +"s", "Имя")
                            + "   " + String.format("%-" + lengthOfPatronymic +"s", "Отчество")
                            + "   " + String.format("%-" + lengthOfNumberOfCart +"s", "Номер карта") + System.lineSeparator();
                    writer.write(header);
                    writer.write(headerItems);

                    for(Driver driver: group.getDrivers()) {
                        String dataItems = String.format("%-" + lengthOfNumber +"s", group.getDrivers().indexOf(driver) + 1)
                                + "   " + String.format("%-" + lengthOfID +"s", group.getID())
                                + "   " + String.format("%-" + lengthOfSurname +"s", driver.getSurname())
                                + "   " + String.format("%-" + lengthOfName +"s", driver.getName())
                                + "   " + String.format("%-" + lengthOfPatronymic +"s", driver.getPatronymic())
                                + "   " + String.format("%-" + lengthOfNumberOfCart +"s", driver.getCurrentKart().getNumberOfKart()) + System.lineSeparator();

                        writer.write(dataItems);
                    }
                    writer.write(System.lineSeparator());
                }
            } catch (Exception e) {
                AlertHelper.showErrorAlert("Ошибка сохранения", "Ошибка сохранения данных", "Не удалось сохранить данные в файл .txt");
            }
        }
    }

    @FXML
    void initialize() {
        NodeHelper.updateGranPriComboBox(grandPriStageComboBox);
        scrollPaneVBox.getChildren().clear();

        if(!GROUPS.isEmpty()) {
            for(Group group: GROUPS) {
                createGroupTable(group);
            }
        }

        hideHeadersTableAndScrollPane(GROUPS.isEmpty());
        updateSaveSerialButton();
    }

    void hideHeadersTableAndScrollPane(boolean isEmpty) {
        if(isEmpty) {
            scrollPane.setVisible(false);
            scrollPane.setDisable(true);
        } else {
            scrollPane.setVisible(true);
            scrollPane.setDisable(false);
        }
    }

    @FXML
    void createGroups() {
        if(KartsSceneController.KARTS.isEmpty()) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "В программу не внесены данные о картах.");
            return;
        }

        if(DriversQualificationSceneController.DRIVERS.isEmpty()) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "В программу не внесены данные о водителях.");
            return;
        }

        resetData();
        for (Group group: GROUPS) createGroupTable(group);
        hideHeadersTableAndScrollPane(GROUPS.isEmpty());
    }

    void createGroupTable(Group group) {
        VBox groupVBox = createGroupVBox();
        TextField groupNumberTextField = createGroupNumberTextField(group.getID());

        HBox headersHBox = createHBoxRow();
        TextField numberHeader = createBlackWhiteCircleTextField("№");
        TextField idHeader = createBlackWhiteCircleTextField("ID");
        TextField surnameHeader = createBlackTextFieldRow("Фамилия");
        TextField nameHeader = createBlackTextFieldRow("Имя");
        TextField patronymicHeader = createBlackTextFieldRow("Отчество");
        TextField numberKartHeader = createBlackTextFieldRow("Номер карта");
        headersHBox.getChildren().addAll(numberHeader, idHeader, surnameHeader, nameHeader, patronymicHeader, numberKartHeader);

        groupVBox.getChildren().addAll(groupNumberTextField, headersHBox);

        for (Driver driver: group.getDrivers()) {
            HBox dataHBox = createHBoxRow();
            TextField numberDataTextField = createBlueWhiteCircleTextField(String.valueOf(group.getDrivers().indexOf(driver) + 1));
            TextField idDataTextField = createBlueWhiteCircleTextField(String.valueOf(driver.getID()));
            TextField surnameDataTextField = createBlueTextFieldRow(driver.getSurname());
            TextField nameDataTextField = createBlueTextFieldRow(driver.getName());
            TextField patronymicDataTextField = createBlueTextFieldRow(driver.getPatronymic());
            TextField numberKartDataTextField = createBlueTextFieldRow(String.valueOf(driver.getCurrentKart().getNumberOfKart()));

            dataHBox.getChildren().addAll(numberDataTextField, idDataTextField, surnameDataTextField, nameDataTextField, patronymicDataTextField, numberKartDataTextField);
            groupVBox.getChildren().add(dataHBox);
        }

        scrollPaneVBox.getChildren().add(groupVBox);
    }

    VBox createGroupVBox() {
        VBox groupVBox = new VBox();

        groupVBox.setAlignment(Pos.TOP_CENTER);
        groupVBox.getStyleClass().add("table-group");

        groupVBox.setMinWidth(717);
        groupVBox.setMaxWidth(717);

        groupVBox.setPadding(new Insets(5,5,5,5));

        return groupVBox;
    }

    TextField createGroupNumberTextField(int number) {
        TextField textField = new TextField(String.format("Группа №%d", number));
        textField.setEditable(false);
        textField.getStyleClass().addAll("black-white-button", "group-header", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(701);
        textField.setMinHeight(30);
        textField.setMaxWidth(701);
        textField.setMaxHeight(30);

        VBox.setMargin(textField, new Insets(0,0,5,0));
        return textField;
    }

    HBox createHBoxRow() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(4);
        VBox.setMargin(hBox, new Insets(0,0,5,0));
        return hBox;
    }

    TextField createBlackWhiteCircleTextField(String text) {
        TextField textField = new TextField(text);
        textField.setEditable(false);
        textField.getStyleClass().addAll("black-white-button", "id-header", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(30);
        textField.setMinHeight(30);
        textField.setMaxWidth(30);
        textField.setMaxHeight(30);

        return textField;
    }

    TextField createBlueWhiteCircleTextField(String text) {
        TextField textField = new TextField(text);
        textField.setEditable(false);
        textField.getStyleClass().addAll("blue-white-button", "id-header", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(30);
        textField.setMinHeight(30);
        textField.setMaxWidth(30);
        textField.setMaxHeight(30);

        return textField;
    }

    TextField createBlackTextFieldRow(String text) {
        TextField textField = new TextField(text);
        textField.setEditable(false);
        textField.getStyleClass().addAll("black-white-button", "data-headers", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(155);
        textField.setMinHeight(30);
        textField.setMaxWidth(155);
        textField.setMaxHeight(30);

        return textField;
    }

    TextField createBlueTextFieldRow(String text) {
        TextField textField = new TextField(text);
        textField.setEditable(false);
        textField.getStyleClass().addAll("blue-white-button", "data-items", "text-field-clear-padding-cursor-default");

        textField.setMinWidth(155);
        textField.setMinHeight(35);
        textField.setMaxWidth(155);
        textField.setMaxHeight(35);

        return textField;
    }

    void addDataToGroups(int numberOfGroups) {
        for(int i = 0; i < numberOfGroups; i++) {
            GROUPS.add(new Group(new ArrayList<>(KartsSceneController.KARTS), i + 1));
        }

        List<Driver> drivers = new ArrayList<>(DriversQualificationSceneController.DRIVERS);

        int numberOfGroup = 0;
        Random rnd = new Random();

        while (!drivers.isEmpty()) {
            Driver nextDriver = drivers.get(rnd.nextInt(drivers.size()));
            drivers.remove(nextDriver);

            Group currentGroup = GROUPS.get(numberOfGroup % numberOfGroups);
            Kart kartToDriver = currentGroup.getFreeCarts().get(rnd.nextInt(currentGroup.getFreeCarts().size()));
            nextDriver.setCurrentKart(kartToDriver);
            currentGroup.getDrivers().add(nextDriver);
            currentGroup.getFreeCarts().remove(kartToDriver);

            numberOfGroup++;
        }
    }

    void updateSaveSerialButton() {
        if(RaceWayKartApplication.isDataSaving) {
            saveSerialButton.setText("Не сохранять");
        } else {
            saveSerialButton.setText("Сохранять");
        }
    }

}
