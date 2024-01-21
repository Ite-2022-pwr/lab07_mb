module pwr.ite.bedrylo.deliverer {
    requires javafx.controls;
    requires javafx.fxml;
    requires shop;
    requires pwr.ite.bedrylo.service;
    requires lombok;

    opens pwr.ite.bedrylo.deliverer to javafx.fxml;
    exports pwr.ite.bedrylo.deliverer;
    exports pwr.ite.bedrylo.deliverer.controller;
    opens pwr.ite.bedrylo.deliverer.controller;
}