package pwr.ite.bedrylo.rmi;

import lombok.Setter;
import pl.edu.pwr.tkubik.jp.shop.api.ICallback;
import pl.edu.pwr.tkubik.jp.shop.api.ICustomer;
import pl.edu.pwr.tkubik.jp.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomerRmiImpl extends UnicastRemoteObject implements ICustomer, Serializable {
    
    @Setter
    private BiConsumer<ICallback, List<Item>> callbackForPutOrder;
    
    @Setter
    private Consumer<String> callbackForReturnReceipt;
    
    @Setter
    private BiConsumer<ICallback, List<Item>> callbackForConsumer;
    
    public CustomerRmiImpl() throws RemoteException {
    }
    @Override
    public void putOrder(ICallback iCallback, List<Item> orderedItemList) throws RemoteException {
        if (callbackForPutOrder != null) {
            callbackForPutOrder.accept(iCallback, orderedItemList);
        }
    }

    @Override
    public void returnReceipt(String receiptString) throws RemoteException {
        if (callbackForReturnReceipt != null) {
            callbackForReturnReceipt.accept(receiptString);
        }
    }

    @Override
    public void response(ICallback iCallback, List<Item> list) throws RemoteException {
        if (callbackForConsumer != null) {
            callbackForConsumer.accept(iCallback, list);
        }
    }
}
