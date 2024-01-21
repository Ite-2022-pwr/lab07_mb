module pwr.ite.bedrylo.seller {
    requires javafx.controls;
    requires javafx.fxml;
    requires shop;
    requires pwr.ite.bedrylo.service;


    opens pwr.ite.bedrylo.seller to javafx.fxml;
    exports pwr.ite.bedrylo.seller;
    exports pwr.ite.bedrylo.seller.controller;
    opens pwr.ite.bedrylo.seller.controller;
}