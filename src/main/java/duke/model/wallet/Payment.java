package duke.model.wallet;

import duke.model.wallet.Transaction;

import java.math.BigDecimal;

public class Payment extends Transaction {
    public Payment(BigDecimal transactionAmount, String dateString) {
        super(transactionAmount, dateString);
        super.type = "PAY";
    }

    public Payment(BigDecimal transactionAmount) {
        super(transactionAmount);
        super.type = "PAY";
    }

    @Override
    public String toString() {
        return "[PAY]" + super.toString();
    }
}
