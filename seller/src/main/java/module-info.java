module pwr.ite.bedrylo.seller {
    requires javafx.controls;
    requires javafx.fxml;


    opens pwr.ite.bedrylo.seller to javafx.fxml;
    exports pwr.ite.bedrylo.seller;
}