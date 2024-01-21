package pwr.ite.bedrylo.rmi;

import pl.edu.pwr.tkubik.jp.shop.api.*;
import pwr.ite.bedrylo.ItemRepository;
import pwr.ite.bedrylo.model.Order;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class KeeperRmiImpl implements IKeeper {
    
    private final Map<Integer, ICallback> clients = new HashMap<>();
    
    private final ItemRepository itemRepository = ItemRepository.getInstance();
    
    private final Queue<Order> orderQueue = new ArrayBlockingQueue<>(50);
    
    private static Integer idCounter = 0;
    
    
    @Override
    public int register(ICallback iCallback) throws RemoteException {
        Integer id = idCounter++;
        clients.put(id, iCallback);
        return id;
    }

    @Override
    public boolean unregister(int clientId) throws RemoteException {
        ICallback clientCallback = clients.get(clientId);
        if (clientCallback == null) {
            return false;
        }
        clients.remove(clientId);
        return false;
    }

    @Override
    public void getOffer(int clientId) throws RemoteException {
        ICustomer customerCallback =(ICustomer) clients.get(clientId);
        customerCallback.response(null, itemRepository.getAllAvailableItems());
    }

    @Override
    public void putOrder(int customerId, List<Item> orderedItems) throws RemoteException {
        ICallback customerCallback = clients.get(customerId);
        List<Item> availableOrderedItems = new ArrayList<>();
        for (Item itemFromOrder: orderedItems) {
            Item tempItem = itemRepository.findAvailableItemByDescription(itemFromOrder.getDescription());
            if (tempItem == null) {
                continue;
            }
            int quantityToTake = Math.min(tempItem.getQuantity(), itemFromOrder.getQuantity());
            tempItem.setQuantity(tempItem.getQuantity()-quantityToTake);
            itemFromOrder.setQuantity(quantityToTake);
            availableOrderedItems.add(itemFromOrder);
        }
        Order order = new Order(customerCallback, availableOrderedItems);
        orderQueue.add(order);
    }

    @Override
    public List<ISeller> getSellers() throws RemoteException {
        return clients.values()
                .stream()
                .filter(client->(client instanceof ISeller))
                .map(sellerClient->((ISeller) sellerClient))
                .toList();
    }

    @Override
    public void getOrder(int delivererId) throws RemoteException {
        Order orderFromQueue = orderQueue.poll();
        if (orderFromQueue == null) {
            return;
        }
        IDeliverer deliverer =(IDeliverer) clients.get(delivererId);
        if (deliverer == null) {
            orderQueue.add(orderFromQueue);
            return;
        }
        deliverer.response(orderFromQueue.getCustomer(),orderFromQueue.getItemList());

    }

    @Override
    public void returnOrder(List<Item> returningItemList) throws RemoteException {
        for (Item returningItem: returningItemList){
            Item tempItem = itemRepository.findItemByDescription(returningItem.getDescription());
            if (tempItem == null) {
                continue;
            }
            tempItem.setQuantity(tempItem.getQuantity()+returningItem.getQuantity());
        }
    }
}
