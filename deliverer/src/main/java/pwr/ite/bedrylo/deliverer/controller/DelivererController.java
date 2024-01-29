package pwr.ite.bedrylo.deliverer.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pwr.tkubik.jp.shop.api.*;
import pwr.ite.bedrylo.rmi.DelivererRmiImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class DelivererController {
    @javafx.fxml.FXML
    private Label infoTextLabel;
    @javafx.fxml.FXML
    private TextField hostField;
    @javafx.fxml.FXML
    private TextField portField;
    @javafx.fxml.FXML
    private Button startButton;
    @javafx.fxml.FXML
    private Button stopButton;
    @javafx.fxml.FXML
    private Button takeOrderButton;
    @javafx.fxml.FXML
    private Button getCustomerInfoButton;
    @javafx.fxml.FXML
    private Button deliverOrderButton;

    private IKeeper keeperServer;

    private IDeliverer activeDeliverer = new DelivererRmiImpl();

    private int activeDelivererId;

    private ICustomer activeServedUser;

    private List<Item> holdingItemList = new ArrayList<>();

    public DelivererController() throws RemoteException {
    }

    @javafx.fxml.FXML
    public void onStartButtonClick() throws Exception {
        Registry registry = LocateRegistry.getRegistry("192.168.7.110");
        this.keeperServer = (IKeeper) registry.lookup("KeeperServer");
        this.activeDelivererId = keeperServer.register(activeDeliverer);
        ((DelivererRmiImpl) activeDeliverer).setCallbackForConsumer(this::acceptOrderFromKeeper);
        ((DelivererRmiImpl) activeDeliverer).setCallbackForReturnItem(this::returnItems);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        takeOrderButton.setDisable(false);
    }


    private void returnItems(List<Item> itemList) {
        try {
            keeperServer.returnOrder(itemList);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptOrderFromKeeper(ICallback iCallback, List<Item> itemList) {
        activeServedUser = (ICustomer) iCallback;
        holdingItemList.clear();
        holdingItemList.addAll(itemList);
        takeOrderButton.setDisable(true);
        deliverOrderButton.setDisable(false);

    }

    @javafx.fxml.FXML
    public void onStopButtonClick() throws RemoteException {
        this.keeperServer.unregister(this.activeDelivererId);
        this.activeDelivererId = 0;
        this.activeDeliverer = null;
        startButton.setDisable(true);
        stopButton.setDisable(true);
        takeOrderButton.setDisable(true);
        deliverOrderButton.setDisable(true);
    }

    @javafx.fxml.FXML
    public void onTakeOrderButtonClick() throws RemoteException {
        keeperServer.getOrder(activeDelivererId);
    }

    @javafx.fxml.FXML
    public void onDeliverOrderButtonClick() throws RemoteException {
        activeServedUser.putOrder(activeDeliverer, holdingItemList);
        activeServedUser = null;
        holdingItemList.clear();
        deliverOrderButton.setDisable(true);
        takeOrderButton.setDisable(false);
    }
}
