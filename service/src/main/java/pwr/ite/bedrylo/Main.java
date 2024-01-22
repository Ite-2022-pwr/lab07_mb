package pwr.ite.bedrylo;

import pwr.ite.bedrylo.util.ItemRepository;

public class Main {
    public static void main(String[] args) {
        ItemRepository itemRepository = ItemRepository.getInstance();
        System.out.println(itemRepository.getAllAvailableItems());

    }
}