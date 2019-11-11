package diyeats.logic.dummy;

import diyeats.model.wallet.Account;
import diyeats.model.wallet.Payment;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.TransactionList;
import diyeats.model.wallet.Wallet;

import java.math.BigDecimal;

/**
 * This is a stub wallet class for testing purposes.
 */
public class DummyWallet extends Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    public DummyWallet() {
    }

    @Override
    public TransactionList getTransactions() {
        return transactions;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public BigDecimal getAccountBalance() {
        return new BigDecimal(0);
    }

    @Override
    public void setAccountBalance(String accountBalance) {
    }

    @Override
    public void updateAccountBalance(Transaction transaction) {
    }

    @Override
    public void updateAccountBalance(Wallet wallet) {
    }

    @Override
    public Boolean addPaymentTransaction(Payment payment) {
        return true;
    }
}
