package diyeats.model.wallet;

import java.time.LocalDate;

//@@author GaryStu
/**
 * Deposit is a public class that inherits from the transaction class.
 */
public class Deposit extends Transaction {
    /**
     * Constructor for deposit class.
     * @param amountString the amount of money to be deposited.
     * @param date the date when the deposit occurs.
     */
    public Deposit(String amountString, LocalDate date) {
        super(amountString, date);
        super.type = "DEP";
    }
}
