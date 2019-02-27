package training.supportbank;

import java.time.LocalDate;

public class Transaction {

    LocalDate date;
    String from;
    String to;
    String narrative;
    Double amount;

    public String toString() {
        return "Transaction from " + from + " to " + to + " for £" + amount + " for " + narrative;
    }
}
