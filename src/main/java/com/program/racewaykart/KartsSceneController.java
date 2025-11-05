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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KartsSceneController {

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
    private HBox headerButtonsHBox;

    @FXML
    private HBox headersTableHBox;

    @FXML
    private Label kartsButton;

    @FXML
    private ImageView logoImage;

    @FXML
    private TextField numberKartInput;

    @FXML
    private Label resetAllButton;

    @FXML
    private Label resetKartsButton;

    @FXML
    private Label saveSerialButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox tableVBox;

    @FXML
    private HBox upperHBox;

    public static ArrayList<Kart> KARTS = new ArrayList<>();

    @FXML
    void initialize() {
        dataVBox.getChildren().clear();

        if(!KARTS.isEmpty()) {
            for(Kart kart: KARTS) {
                createRow(kart);
            }
        }

        hideHeadersTableAndScrollPane(KARTS.isEmpty());
    }

    @FXML
    void goToGroupsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("groups-view.fxml"));
        Scene groupsScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(groupsScene);
    }

    @FXML
    void goToDriversScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("drivers-view.fxml"));
        Scene driversScene = new Scene(loader.load());
        RaceWayKartApplication.appStage.setScene(driversScene);
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

        if(!isKartNumberNotRepeated(Integer.parseInt(numberKartInput.getText()))) {
            AlertHelper.showErrorAlert("Ошибка добавления.", "Ошибка добавления карта.", "Карт с таким номером уже добавлен.");
            return;
        }

        Kart newKart = addDataToKarts();
        createRow(newKart);
        hideHeadersTableAndScrollPane(KARTS.isEmpty());
        clearInputs();
    }

    void createRow(Kart newKart) {
        HBox rowHBox = createHBoxRow();
        Label labelID = createIDLabelRow(newKart.getID());
        Label labelNumberOfCart = createBlueLabelRow(String.valueOf(newKart.getNumberOfKart()));
        Label labelDelete = createDeleteButtonRow(rowHBox, newKart);

        rowHBox.getChildren().addAll(labelID, labelNumberOfCart, labelDelete);
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

        label.setMinWidth(421);
        label.setMinHeight(35);
        label.setMaxWidth(421);
        label.setMaxHeight(35);

        HBox.setMargin(label, new Insets(0,5,0,0));
        return label;
    }

    Label createDeleteButtonRow(HBox parentToDelete, Kart kartToDelete) {
        Label label = new Label("X");
        label.getStyleClass().addAll("red-white-button", "id-header", "label-button");

        label.setMinWidth(30);
        label.setMinHeight(30);
        label.setMaxWidth(30);
        label.setMaxHeight(30);

        label.setOnMouseClicked(_ -> {
            Platform.runLater(() -> {
                dataVBox.getChildren().remove(parentToDelete);
                KARTS.remove(kartToDelete);
                hideHeadersTableAndScrollPane(KARTS.isEmpty());
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
        numberKartInput.setText("");
    }

    Kart addDataToKarts() {
        Kart newKart = new Kart(KARTS.size() + 1, Integer.parseInt(numberKartInput.getText()));
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

}
