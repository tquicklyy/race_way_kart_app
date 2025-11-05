package com.program.racewaykart;

import com.program.racewaykart.entity.Driver;
import com.program.racewaykart.entity.Group;
import com.program.racewaykart.entity.Kart;
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

    public static Stage appStage;
    public static boolean isDataSaving;

    @Override
    public void start(Stage stage) throws IOException {
        File dataDir = new File("data");
        dataDir.mkdirs();

        try(ObjectInputStream inIsDataSaving = new ObjectInputStream(new FileInputStream("data/is_data_saving.dat"));
            ObjectInputStream inDriver = new ObjectInputStream(new FileInputStream("data/driver.dat"));
            ObjectInputStream inKart = new ObjectInputStream(new FileInputStream("data/kart.dat"));
            ObjectInputStream inGroup = new ObjectInputStream(new FileInputStream("data/group.dat"))
        ) {
            isDataSaving = inIsDataSaving.readBoolean();

            if(isDataSaving) {
                DriversSceneController.DRIVERS = (List<Driver>) inDriver.readObject();
                KartsSceneController.KARTS = (List<Kart>) inKart.readObject();
                GroupsSceneController.GROUPS = (List<Group>) inGroup.readObject();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Пока сохранения нет будут падать ошибки, если есть, то файлы будут найдены
        }

        FXMLLoader fxmlLoader = new FXMLLoader(RaceWayKartApplication.class.getResource("groups-view.fxml"));
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
                ObjectOutputStream outGroup = new ObjectOutputStream(new FileOutputStream("data/group.dat"))) {

                if(isDataSaving) {
                    outDriver.writeObject(DriversSceneController.DRIVERS);
                    outKart.writeObject(KartsSceneController.KARTS);
                    outGroup.writeObject(GroupsSceneController.GROUPS);
                } else {
                    outDriver.writeObject(new ArrayList<Driver>());
                    outKart.writeObject(new ArrayList<Kart>());
                    outGroup.writeObject(new ArrayList<Group>());
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
        DriversSceneController.DRIVERS.clear();
        KartsSceneController.KARTS.clear();
        GroupsSceneController.GROUPS.clear();
    }

    public static void main(String[] args) {
        launch();
    }
}