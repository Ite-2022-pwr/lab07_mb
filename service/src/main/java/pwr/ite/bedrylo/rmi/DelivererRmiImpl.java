package pwr.ite.bedrylo.rmi;

import lombok.Setter;
import pl.edu.pwr.tkubik.jp.shop.api.ICallback;
import pl.edu.pwr.tkubik.jp.shop.api.IDeliverer;
import pl.edu.pwr.tkubik.jp.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DelivererRmiImpl extends UnicastRemoteObject implements IDeliverer, Serializable {
    
    public DelivererRmiImpl() throws RemoteException {
    }
    
    @Setter
    private Consumer<List<Item>> callbackForReturnItem;
    
    @Setter
    private BiConsumer<ICallback, List<Item>> callbackForConsumer;


    @Override
    public void returnOrder(List<Item> itemToReturnList) throws RemoteException {
        if (callbackForReturnItem != null) {
            callbackForReturnItem.accept(itemToReturnList);
        }
    }

    @Override
    public void response(ICallback iCallback, List<Item> list) throws RemoteException {
        if(callbackForConsumer != null) {
            callbackForConsumer.accept(iCallback, list);
        }
    }
}
