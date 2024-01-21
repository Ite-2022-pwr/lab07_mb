module pwr.ite.bedrylo.keeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires shop;
    requires pwr.ite.bedrylo.service;


    opens pwr.ite.bedrylo.keeper to javafx.fxml;
    exports pwr.ite.bedrylo.keeper;
    exports pwr.ite.bedrylo.keeper.controller;
    opens pwr.ite.bedrylo.keeper.controller to javafx.fxml;
}