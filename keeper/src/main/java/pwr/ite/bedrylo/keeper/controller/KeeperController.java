package pwr.ite.bedrylo.keeper.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.edu.pwr.tkubik.jp.shop.api.IKeeper;
import pwr.ite.bedrylo.rmi.KeeperRmiImpl;
import pwr.ite.bedrylo.util.ExceptionPopup;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class KeeperController {
    @FXML
    private Label infoTextLabel;
    
    @FXML
    private Button startButton;
    
    private IKeeper keeperServer;
    
    

    @FXML
    protected void onStartButtonClick() {
        try {
            startButton.setDisable(true);
            this.keeperServer = new KeeperRmiImpl();
            Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            rmiRegistry.rebind("KeeperServer", keeperServer);
            infoTextLabel.setText("Uruchomiono KeeperServer");
        }catch (Exception e){
            ExceptionPopup exceptionPopup = new ExceptionPopup(e.getLocalizedMessage());
        }
    }
    
    
    public void stop(){
        try {
            UnicastRemoteObject.unexportObject(keeperServer, true);
        }catch (Exception e) {
            ExceptionPopup exceptionPopup = new ExceptionPopup(e.getLocalizedMessage());
        }
    }
}