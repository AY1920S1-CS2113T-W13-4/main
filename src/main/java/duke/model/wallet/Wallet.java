package duke.model.wallet;

import java.math.BigDecimal;

public class Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    public Wallet() {
    }

    public TransactionList getTransactions() {
        return transactions;
    }

    private Account getAccount() {
        return account;
    }

    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            this.account.withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            this.account.deposit(transactionAmount);
        }
    }

    public void updateAccountBalance(Wallet wallet) {
        this.transactions = wallet.getTransactions();
        this.account = wallet.getAccount();
    }

    public Boolean addPaymentTransaction(Payment payment) {
        if (this.account.isSufficientBalance(payment.getTransactionAmount())) {
            this.account.withdraw(payment.getTransactionAmount());
            this.transactions.addTransaction(payment);
            return true;
        }
        return false;
    }
}
