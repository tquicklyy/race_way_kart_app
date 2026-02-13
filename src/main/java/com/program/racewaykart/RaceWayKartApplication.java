package com.program.racewaykart;

import com.program.racewaykart.controller.DriversQualificationSceneController;
import com.program.racewaykart.controller.GroupsQualificationSceneController;
import com.program.racewaykart.controller.KartsSceneController;
import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.entity.Group;
import com.program.racewaykart.entity.Kart;
import com.program.racewaykart.enums.GrandPriStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RaceWayKartApplication extends Application {

    public static final String PATH_TO_DRIVERS_QUAL_FXML = "/com/program/racewaykart/drivers-qualification-view.fxml";
    public static final String PATH_TO_DRIVERS_RACE_FXML = "/com/program/racewaykart/drivers-race-view.fxml";
    public static final String PATH_TO_GROUPS_QUAL_FXML = "/com/program/racewaykart/groups-qualification-view.fxml";
    public static final String PATH_TO_GROUPS_RACE_FXML = "/com/program/racewaykart/groups-race-view.fxml";
    public static final String PATH_TO_KARTS_FXML = "/com/program/racewaykart/karts-view.fxml";

    public static Stage appStage;
    public static boolean isDataSaving;
    public static GrandPriStage grandPriStage;

    @Override
    public void start(Stage stage) throws IOException {
        File dataDir = new File("data");
        dataDir.mkdirs();

        try(ObjectInputStream inIsDataSaving = new ObjectInputStream(new FileInputStream("data/is_data_saving.dat"));
            ObjectInputStream inDriver = new ObjectInputStream(new FileInputStream("data/driver.dat"));
            ObjectInputStream inKart = new ObjectInputStream(new FileInputStream("data/kart.dat"));
            ObjectInputStream inGroup = new ObjectInputStream(new FileInputStream("data/group.dat"));
            ObjectInputStream inGrandPriStage = new ObjectInputStream(new FileInputStream("data/grand_pri_stage.dat"));
        ) {
            isDataSaving = inIsDataSaving.readBoolean();

            if(isDataSaving) {
                DriversQualificationSceneController.DRIVERS = (List<Driver>) inDriver.readObject();
                KartsSceneController.KARTS = (List<Kart>) inKart.readObject();
                GroupsQualificationSceneController.GROUPS = (List<Group>) inGroup.readObject();
                grandPriStage = (GrandPriStage) inGrandPriStage.readObject();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Пока сохранения нет будут падать ошибки, если есть, то файлы будут найдены
        }

        if(grandPriStage == null) grandPriStage = GrandPriStage.QUALIFICATION;

        FXMLLoader fxmlLoader;
        if(grandPriStage == GrandPriStage.QUALIFICATION) fxmlLoader = new FXMLLoader(RaceWayKartApplication.class.getResource("groups-qualification-view.fxml"));
        else fxmlLoader = new FXMLLoader(RaceWayKartApplication.class.getResource("groups-race-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        appStage = stage;

        appStage.setTitle("RaceWayKart");

        appStage.getIcons().clear();
        appStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        appStage.setResizable(false);

        appStage.setScene(scene);

        appStage.setOnCloseRequest(_ -> {
            Thread forceCloseThread = new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    System.exit(1);
                } catch (InterruptedException e) {}
            });
            forceCloseThread.setDaemon(true);
            forceCloseThread.start();

            try(ObjectOutputStream outDataSaving = new ObjectOutputStream(new FileOutputStream("data/is_data_saving.dat"))) {
                outDataSaving.writeBoolean(isDataSaving);
            } catch (Exception e) {
                e.printStackTrace();
            }


            try(ObjectOutputStream outDriver = new ObjectOutputStream(new FileOutputStream("data/driver.dat"));
                ObjectOutputStream outKart = new ObjectOutputStream(new FileOutputStream("data/kart.dat"));
                ObjectOutputStream outGroup = new ObjectOutputStream(new FileOutputStream("data/group.dat"));
                ObjectOutputStream outGrandPriStage = new ObjectOutputStream(new FileOutputStream("data/grand_pri_stage.dat"));) {

                if(isDataSaving) {
                    outDriver.writeObject(DriversQualificationSceneController.DRIVERS);
                    outKart.writeObject(KartsSceneController.KARTS);
                    outGroup.writeObject(GroupsQualificationSceneController.GROUPS);
                    outGrandPriStage.writeObject(grandPriStage);
                } else {
                    outDriver.writeObject(new ArrayList<Driver>());
                    outKart.writeObject(new ArrayList<Kart>());
                    outGroup.writeObject(new ArrayList<Group>());
                    outGrandPriStage.writeObject(GrandPriStage.QUALIFICATION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.exit(0);
        });

        appStage.toFront();
        appStage.show();
    }

    public static void resetAllData() {
        DriversQualificationSceneController.DRIVERS.clear();
        KartsSceneController.KARTS.clear();
        GroupsQualificationSceneController.GROUPS.clear();
    }

    public static void main(String[] args) {
        launch();
    }
}