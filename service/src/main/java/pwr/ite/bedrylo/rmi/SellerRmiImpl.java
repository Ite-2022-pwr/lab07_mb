package pwr.ite.bedrylo.rmi;

import lombok.Setter;
import pl.edu.pwr.tkubik.jp.shop.api.ICallback;
import pl.edu.pwr.tkubik.jp.shop.api.ICustomer;
import pl.edu.pwr.tkubik.jp.shop.api.ISeller;
import pl.edu.pwr.tkubik.jp.shop.api.Item;
import pwr.ite.bedrylo.TriConsumer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;

public class SellerRmiImpl extends UnicastRemoteObject implements ISeller, Serializable {
    
    @Setter
    private TriConsumer<ICustomer, List<Item>, List<Item>> callbackForAcceptOrder;
    
    @Setter
    private BiConsumer<ICallback, List<Item>> callbackForConsumer;
    
    public SellerRmiImpl() throws RemoteException{
    }
    
    @Override
    public void acceptOrder(ICustomer iCustomer, List<Item> itemListToBuy, List<Item> itemListToReturn) throws RemoteException {
        if (callbackForAcceptOrder != null) {
            callbackForAcceptOrder.accept(iCustomer, itemListToBuy, itemListToReturn);
        }
    }

    @Override
    public void response(ICallback iCallback, List<Item> list) throws RemoteException {
        if (callbackForConsumer != null) {
            callbackForConsumer.accept(iCallback, list);
        }
    }
}
