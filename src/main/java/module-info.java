module com.racewaykart.program.racewaykart {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.program.racewaykart to javafx.fxml;
    exports com.program.racewaykart;
    exports com.program.racewaykart.enums;
}