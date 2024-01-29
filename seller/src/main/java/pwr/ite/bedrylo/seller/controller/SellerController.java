package pwr.ite.bedrylo.seller.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.edu.pwr.tkubik.jp.shop.api.*;
import pwr.ite.bedrylo.rmi.SellerRmiImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class SellerController {
    @javafx.fxml.FXML
    private Label infoTextLabel;
    @javafx.fxml.FXML
    private Button startButton;
    @javafx.fxml.FXML
    private Button stopButton;

    private IKeeper keeperServer;

    private ISeller activeSeller = new SellerRmiImpl();

    private int activeSellerId;

    public SellerController() throws RemoteException {
    }

    @javafx.fxml.FXML
    public void onStartButtonClick() throws Exception {
        Registry registry = LocateRegistry.getRegistry("192.168.7.110");
        keeperServer = (IKeeper) registry.lookup("KeeperServer");
        activeSellerId = keeperServer.register(activeSeller);
        ((SellerRmiImpl) activeSeller).setCallbackForAcceptOrder(this::callbackForAcceptOrder);
        ((SellerRmiImpl) activeSeller).setCallbackForConsumer(this::callbackForConsumer);
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    private void callbackForConsumer(ICallback iCallback, List<Item> itemList) {
    }

    private void callbackForAcceptOrder(ICustomer iCustomer, List<Item> itemsToBuy, List<Item> itemsToReturn) {
        try {
            keeperServer.returnOrder(itemsToReturn);
            StringBuilder receipt = new StringBuilder();
            for (Item itemToBuy : itemsToBuy) {
                receipt.append(itemToBuy.toString());
            }
            iCustomer.returnReceipt(receipt.toString());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void onStopButtonClick() throws RemoteException {
        keeperServer.unregister(activeSellerId);
        activeSeller = null;
        activeSellerId = 0;
        stopButton.setDisable(true);
        startButton.setDisable(true);
    }
}
