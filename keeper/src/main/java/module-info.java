module pwr.ite.bedrylo.keeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens pwr.ite.bedrylo.keeper to javafx.fxml;
    exports pwr.ite.bedrylo.keeper;
}