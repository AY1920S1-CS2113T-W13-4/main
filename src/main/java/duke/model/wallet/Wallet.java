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

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    public void setAccountBalance(String accountBalance) {
        this.account.setAmount(new BigDecimal(accountBalance));
    }

    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            this.account.withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            this.account.deposit(transactionAmount);
        }
    }

    public void UpdateAccountBalance(Wallet wallet) {
        this.transactions = wallet.getTransactions();
        this.account = wallet.getAccount();
    }

    public void addTransactions(String cost, String date) {
        this.transactions.addTransaction(new Transaction(cost, date));
        this.account.withdraw(new BigDecimal(cost));
    }
}
