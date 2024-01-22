package pwr.ite.bedrylo.customer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceiptWithNumber {
    private static int numberCounter = 0;

    private String receipt;

    private int number;

    public ReceiptWithNumber(String receipt) {
        this.receipt = receipt;
        numberCounter++;
        this.number = numberCounter;
    }
}

