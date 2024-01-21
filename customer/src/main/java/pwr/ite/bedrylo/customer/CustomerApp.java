package pwr.ite.bedrylo.customer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

public class CustomerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CustomerApp.class.getResource("customer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    @SneakyThrows
    public void stop(){
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}