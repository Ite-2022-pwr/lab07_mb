package pwr.ite.bedrylo;

import pl.edu.pwr.tkubik.jp.shop.api.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemRepository {
    
    private static ItemRepository INSTANCE = null;
    
    private final List<Item> items = new ArrayList<>();
    
    private final Random random = new Random(2137);

    public ItemRepository(){
        for (int i = 0; i < 100; i++) {
            items.add(new Item("towar"+i, random.nextInt(1,100)));
        }
    }
    
    public List<Item> getAllAvailableItems(){
        return items.stream()
                .filter(item->(item.getQuantity()>0))
                .toList();
    }
    
    public Item findAvailableItemByDescription(String description){
        Item tempItem = findItemByDescription(description);
        return tempItem.getQuantity() > 0 ? tempItem : null;
    }
    
    public Item findItemByDescription(String description){
        return items.stream()
                .filter(item->(item.getDescription().equals(description)))
                .findFirst()
                .orElse(null);
    }
    
    
    
    public void removeItemByDescription(String description){
        items.stream()
                .filter(o -> (o.getDescription().equals(description)))
                .findFirst().ifPresent(itemToDelete -> itemToDelete.setQuantity(0));
    }
    
    public static ItemRepository getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ItemRepository();
        }
        return INSTANCE;
    }
    
    
    
}
