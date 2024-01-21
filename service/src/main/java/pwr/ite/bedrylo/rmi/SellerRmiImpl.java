package pwr.ite.bedrylo.rmi;

import pl.edu.pwr.tkubik.jp.shop.api.ICallback;
import pl.edu.pwr.tkubik.jp.shop.api.ICustomer;
import pl.edu.pwr.tkubik.jp.shop.api.ISeller;
import pl.edu.pwr.tkubik.jp.shop.api.Item;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SellerRmiImpl extends UnicastRemoteObject implements ISeller, Serializable {
    
    public SellerRmiImpl() throws RemoteException{
    }
    
    @Override
    public void acceptOrder(ICustomer iCustomer, List<Item> itemListToBuy, List<Item> itemListToReturn) throws RemoteException {
        
    }

    @Override
    public void response(ICallback iCallback, List<Item> list) throws RemoteException {

    }
}
