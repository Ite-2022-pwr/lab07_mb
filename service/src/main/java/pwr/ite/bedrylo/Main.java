package pwr.ite.bedrylo;

public class Main {
    public static void main(String[] args) {
        ItemRepository itemRepository = ItemRepository.getInstance();
        System.out.println(itemRepository.getAllAvailableItems());
        
    }
}