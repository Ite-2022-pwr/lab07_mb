package pwr.ite.bedrylo.model;

import lombok.Data;
import pl.edu.pwr.tkubik.jp.shop.api.ICallback;
import pl.edu.pwr.tkubik.jp.shop.api.Item;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    private ICallback customer;

    private List<Item> itemList;

    public Order(ICallback customer, List<Item> itemList) {
        this.customer = customer;
        this.itemList = new ArrayList<>();
        this.itemList.addAll(itemList);
    }

}
