package pwr.ite.bedrylo.customer.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pwr.tkubik.jp.shop.api.*;
import pwr.ite.bedrylo.rmi.CustomerRmiImpl;
import pwr.ite.bedrylo.customer.ReceiptWithNumber;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerController {

    
    @FXML
    private TextArea receiptTextArea;
    @FXML
    private Button registerButtons;
    @FXML
    private Button unregisterButton;
    @FXML
    private Button getOfferButton;
    @FXML
    private TableView<Item> offerTable;
    @FXML
    private Button addToOrderButton;
    @FXML
    private Button RemoveFromOrderButton;
    @FXML
    private Button putOrderButton;
    @FXML
    private TableView<Item> preOrderTable;
    @FXML
    private TableView<Item> cartTable;
    @FXML
    private Button acceptBySellerButton;
    @FXML
    private Button moveToReturnButton;
    @FXML
    private Button moveToCartButton;
    @FXML
    private Button refreshCartButton;
    @FXML
    private TableView<Item> returnTable;
    @FXML
    private Button returnButton;
    @FXML
    private TableView<ReceiptWithNumber> receiptTable;
    @FXML
    private Button showReceiptButton;
    @FXML
    private Button receiptTableRefreshButton;
    
    
    private IKeeper keeperServer; 
    
    private ICustomer activeCustomer = new CustomerRmiImpl();
    
    private int activeCustomerId;
    
    private final ObservableList<Item> customerItems = FXCollections.observableArrayList();
    
    private final ObservableList<Item> offer = FXCollections.observableArrayList();

    private final ObservableList<Item> preOrderItems = FXCollections.observableArrayList();
    
    private final ObservableList<Item> toReturnItems = FXCollections.observableArrayList();
    private final ObservableList<ReceiptWithNumber> receipts = FXCollections.observableArrayList();
    
    
    public CustomerController() throws Exception{}
    

    @FXML
    public void onRegisterButtonClick() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        this.keeperServer = (IKeeper) registry.lookup("KeeperServer");
        this.activeCustomerId = keeperServer.register(activeCustomer);
        offerTable.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(offer));
        preOrderTable.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(preOrderItems));
        cartTable.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(customerItems));
        returnTable.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(toReturnItems));
        receiptTable.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(receipts));
        ((CustomerRmiImpl) activeCustomer).setCallbackForPutOrder(this::callbackForPutOrder);
        ((CustomerRmiImpl) activeCustomer).setCallbackForReturnReceipt(this::callbackForReturnReceipt);
        ((CustomerRmiImpl) activeCustomer).setCallbackForConsumer(this::callbackForConsumer);
        enableButtons();
    }

    private void enableButtons() {
        registerButtons.setDisable(true);
        unregisterButton.setDisable(false);
        getOfferButton.setDisable(false);
        addToOrderButton.setDisable(false);
        RemoveFromOrderButton.setDisable(false);
        putOrderButton.setDisable(false);
        acceptBySellerButton.setDisable(false);
        moveToReturnButton.setDisable(false);
        moveToCartButton.setDisable(false);
        returnButton.setDisable(false);
        showReceiptButton.setDisable(false);
        receiptTableRefreshButton.setDisable(false);
        refreshCartButton.setDisable(false);
    }

    private void callbackForConsumer(ICallback iCallback, List<Item> itemList) {
        offer.clear();
        preOrderItems.clear();
        offer.addAll(itemList);
    }

    private void callbackForReturnReceipt(String receipt) {
        receipts.add(new ReceiptWithNumber(receipt));
        receiptTable.refresh();
        customerItems.clear();
        toReturnItems.clear();
    }

    private void callbackForPutOrder(ICallback iCallback, List<Item> itemList) {
        for(Item item : itemList){
            Item temp = customerItems.stream()
                    .filter(o->(o.getDescription().equals(item.getDescription())))
                    .findFirst()
                    .orElse(null);
            if (temp == null){
                customerItems.add(item);
            } else {
                temp.setQuantity(temp.getQuantity()+item.getQuantity());
            }
        }
        cartTable.refresh();
    }

    @FXML
    public void onUnRegisterButtonClick() throws Exception{
        this.keeperServer.unregister(activeCustomerId);
        this.activeCustomerId = 0;
        this.activeCustomer = null;
    }

    @FXML
    public void onGetOfferButtonClick() throws RemoteException {
        keeperServer.getOffer(activeCustomerId);
    }
    

    @FXML
    public void onAddToOrderButtonClick() {
        moveItemsBetweenTables(offerTable,preOrderTable,offer,preOrderItems);
    }

    @FXML
    public void onRemoveFromOrderButtonClick() {
        moveItemsBetweenTables(preOrderTable, offerTable,preOrderItems,offer);
    }

    @FXML
    public void onPutOrderButtonClick() throws RemoteException {
        keeperServer.putOrder(activeCustomerId, preOrderItems.stream().toList());
        preOrderItems.clear();
    }

    @FXML
    public void onAcceptBySellerButtonClick() throws RemoteException {
        ISeller seller = keeperServer.getSellers().get(0);
        if (seller != null){
            seller.acceptOrder(activeCustomer,customerItems.stream().toList(), toReturnItems.stream().toList());
        }
    }

    @FXML
    public void onRemoveFromCartButtonClick() {
        moveItemsBetweenTables(cartTable,returnTable,customerItems,toReturnItems);
    }

    @FXML
    public void onAddToCartButtonClick() {
        moveItemsBetweenTables(returnTable, cartTable, toReturnItems, customerItems);
    }
    
    @FXML
    public void onReturnButtonClick() throws RemoteException {
        keeperServer.returnOrder(toReturnItems.stream().toList()); // ! narazie tak bo IKeeper nie ma metody do zdobycia delivererów
        toReturnItems.clear();
        returnTable.refresh();
    }

    @FXML
    public void onShowReceiptButton() {
        receiptTextArea.setText(receiptTable.getSelectionModel().getSelectedItem().getReceipt());
    }
    
    
    private void moveItemsBetweenTables(TableView<Item> tableToMoveFrom, 
                                        TableView<Item> tableToMoveTo, 
                                        ObservableList<Item> moveFromList, 
                                        ObservableList<Item> moveToList){
        Item itemToRemove = tableToMoveFrom.getSelectionModel().getSelectedItem();
        if (itemToRemove == null || itemToRemove.getQuantity() <= 0){
            return;
        }
        Item temp = moveToList.stream()
                .filter(o->(o.getDescription().equals(itemToRemove.getDescription())))
                .findFirst()
                .orElse(null);
        if (temp == null) {
            moveToList.add(new Item(itemToRemove.getDescription(), 1));
        } else {
            temp.setQuantity(temp.getQuantity()+1);
        }
        int remainingQuantity = itemToRemove.getQuantity()-1;
        if (remainingQuantity <= 0) {
            moveFromList.remove(itemToRemove);
        }
        itemToRemove.setQuantity(remainingQuantity);
        tableToMoveFrom.refresh();
        tableToMoveTo.refresh();
    }
    
    
}