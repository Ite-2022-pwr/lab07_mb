package pwr.ite.bedrylo.keeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pwr.ite.bedrylo.keeper.controller.KeeperController;

import java.io.IOException;

public class KeeperApp extends Application {
    
    private FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(KeeperApp.class.getResource("keeper-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Keeper App!");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() throws Exception{
        super.stop();
        KeeperController keeperController = fxmlLoader.getController();
        keeperController.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}