module ma.youcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbcp2;
    requires java.management;
    requires commons.validator;
    requires java.prefs;
    requires jbcrypt;
    requires javax.mail;


    opens ma.youcode.main to javafx.fxml;
    exports ma.youcode.main;

    opens ma.youcode.controllers to javafx.fxml;
    exports ma.youcode.controllers;

    opens ma.youcode.models to javafx.base;
    exports ma.youcode.models;



}