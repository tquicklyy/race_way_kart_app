package com.program.racewaykart;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.entity.Group;
import com.program.racewaykart.entity.Kart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    public static List<Group> GROUPS = new ArrayList<>();

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
    void initialize() {
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

        if(DriversSceneController.DRIVERS.isEmpty()) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "В программу не внесены данные о водителях.");
            return;
        }

        if(!numberOfGroupsInput.getText().matches("^[1-9]\\d*$")) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "Указанное число некорректно (отрицательное или 0).");
            return;
        }

        int numberOfGroups = Integer.parseInt(numberOfGroupsInput.getText());

        if(numberOfGroups > DriversSceneController.DRIVERS.size()) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "Указанное число превышает количество водителей.");
            return;
        }

        if((DriversSceneController.DRIVERS.size() / numberOfGroups) + (DriversSceneController.DRIVERS.size() % numberOfGroups != 0 ? 1 : 0) > KartsSceneController.KARTS.size()) {
            AlertHelper.showErrorAlert("Ошибка создания", "Ошибка создания групп", "Недостаточно картов для создания такого количества групп.");
            return;
        }

        resetData();
        addDataToGroups(numberOfGroups);
        for (Group group: GROUPS) createGroupTable(group);
        hideHeadersTableAndScrollPane(GROUPS.isEmpty());
    }

    void createGroupTable(Group group) {
        VBox groupVBox = createGroupVBox();
        Label groupNumberLabel = createGroupNumberLabel(group.getID());

        HBox headersHBox = createHBoxRow();
        Label numberHeader = createBlackWhiteCircleLabel("№");
        Label idHeader = createBlackWhiteCircleLabel("ID");
        Label surnameHeader = createBlackLabelRow("Фамилия");
        Label nameHeader = createBlackLabelRow("Имя");
        Label patronymicHeader = createBlackLabelRow("Отчество");
        Label numberKartHeader = createBlackLabelRow("Номер карта");
        headersHBox.getChildren().addAll(numberHeader, idHeader, surnameHeader, nameHeader, patronymicHeader, numberKartHeader);

        groupVBox.getChildren().addAll(groupNumberLabel, headersHBox);

        for (Driver driver: group.getDrivers()) {
            HBox dataHBox = createHBoxRow();
            Label numberDataLabel = createBlueWhiteCircleLabel(String.valueOf(group.getDrivers().indexOf(driver) + 1));
            Label idDataLabel = createBlueWhiteCircleLabel(String.valueOf(driver.getID()));
            Label surnameDataLabel = createBlueLabelRow(driver.getSurname());
            Label nameDataLabel = createBlueLabelRow(driver.getName());
            Label patronymicDataLabel = createBlueLabelRow(driver.getPatronymic());
            Label numberKartDataLabel = createBlueLabelRow(String.valueOf(driver.getCurrentKart().getNumberOfKart()));

            dataHBox.getChildren().addAll(numberDataLabel, idDataLabel, surnameDataLabel, nameDataLabel, patronymicDataLabel, numberKartDataLabel);
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

    Label createGroupNumberLabel(int number) {
        Label label = new Label(String.format("Группа №%d", number));
        label.getStyleClass().addAll("black-white-button", "group-header");

        label.setMinWidth(701);
        label.setMinHeight(30);
        label.setMaxWidth(701);
        label.setMaxHeight(30);

        VBox.setMargin(label, new Insets(0,0,5,0));
        return label;
    }

    HBox createHBoxRow() {
        HBox hBox = new HBox();
        hBox.setSpacing(4);
        VBox.setMargin(hBox, new Insets(0,0,5,0));
        return hBox;
    }

    Label createBlackWhiteCircleLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().addAll("black-white-button", "id-header");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        return label;
    }

    Label createBlueWhiteCircleLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().addAll("blue-white-button", "id-header");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        return label;
    }

    Label createBlackLabelRow(String text) {
        Label label = new Label(text);
        label.getStyleClass().addAll("black-white-button", "data-headers");

        label.setMinWidth(155);
        label.setMinHeight(30);
        label.setMaxWidth(155);
        label.setMaxHeight(30);

        return label;
    }

    Label createBlueLabelRow(String text) {
        Label label = new Label(text);
        label.getStyleClass().addAll("blue-white-button", "data-items");

        label.setMinWidth(155);
        label.setMinHeight(35);
        label.setMaxWidth(155);
        label.setMaxHeight(35);

        return label;
    }

    void addDataToGroups(int numberOfGroups) {
        for(int i = 0; i < numberOfGroups; i++) {
            GROUPS.add(new Group(new ArrayList<>(KartsSceneController.KARTS), i + 1));
        }

        List<Driver> drivers = new ArrayList<>(DriversSceneController.DRIVERS);

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
