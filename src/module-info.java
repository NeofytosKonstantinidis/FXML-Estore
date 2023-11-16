module fxmlestore {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;

    opens sample;
    opens sample.controller;
    opens sample.listeners;
}