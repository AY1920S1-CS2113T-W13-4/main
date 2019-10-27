package duke.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction {
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
    protected String date = dateParser.format(Calendar.getInstance().getTime());

    public Transaction(String amountString, String dateString) {
        this.transactionAmount = new BigDecimal(amountString);
        this.date = dateString;
    }

    public String getType() {
        return this.type;
    }

    public String getDate() {
        return this.date;
    }

    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    @Override
    public String toString() {
        return " SGD" + this.transactionAmount + "| " + getDate();
    }
}
