module com.racewaykart.program.racewaykart {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.program.racewaykart to javafx.fxml;
    exports com.program.racewaykart;
    exports com.program.racewaykart.enums;
    exports com.program.racewaykart.helper;
    opens com.program.racewaykart.helper to javafx.fxml;
    exports com.program.racewaykart.controller;
    opens com.program.racewaykart.controller to javafx.fxml;
}