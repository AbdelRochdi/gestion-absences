
module ma.youcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbcp2;
    requires java.management;

    opens ma.youcode.main to javafx.fxml;
    exports ma.youcode.main;

    opens ma.youcode.models to javafx.fxml;
    exports ma.youcode.models;

    opens ma.youcode.controllers to javafx.fxml;
    exports ma.youcode.controllers;
}