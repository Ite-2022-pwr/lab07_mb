module pwr.ite.bedrylo.customer {
    requires javafx.controls;
    requires javafx.fxml;
    requires shop;
    requires pwr.ite.bedrylo.service;
    requires lombok;


    opens pwr.ite.bedrylo.customer to javafx.fxml;
    exports pwr.ite.bedrylo.customer;
    exports pwr.ite.bedrylo.customer.controller;
    opens pwr.ite.bedrylo.customer.controller to javafx.fxml;
}