module pwr.ite.bedrylo.deliverer {
    requires javafx.controls;
    requires javafx.fxml;


    opens pwr.ite.bedrylo.deliverer to javafx.fxml;
    exports pwr.ite.bedrylo.deliverer;
}