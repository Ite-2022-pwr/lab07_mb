package pwr.ite.bedrylo.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.SneakyThrows;

public class ExceptionPopup extends RuntimeException {

    @SneakyThrows
    public ExceptionPopup(String exceptionString) {
        super(exceptionString);
        Platform.runLater(() -> {
            Alert popup = new Alert(Alert.AlertType.ERROR, exceptionString, ButtonType.OK);
            popup.showAndWait();
        });
    }

    @SneakyThrows
    public ExceptionPopup(String exceptionString, Throwable throwable) {
        super(exceptionString, throwable);
        Platform.runLater(() -> {
            Alert popup = new Alert(Alert.AlertType.ERROR, exceptionString + "\n" + throwable.getLocalizedMessage(), ButtonType.OK);
            popup.showAndWait();
        });

    }

}
