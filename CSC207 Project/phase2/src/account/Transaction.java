package account;
import java.util.Date;

import machine.ATM;

/**
 * This class stores transaction information.
 */
public class Transaction {
    private double money;
    private Account receivingAccount;
    private Account sendingAccount;
    private final Date transactionTime;

    Transaction(double money, Account receiving, Account sending, Date date) {
        this.money = money;
        this.receivingAccount = receiving;
        this.sendingAccount = sending;
        this.transactionTime = date;
    }

    Transaction(double money, Account account, boolean isDeposit, Date date) {
        this.money = money;
        this.transactionTime = date;
        if (isDeposit) {
            this.receivingAccount = account;
            this.sendingAccount = null;
        } else {
            this.receivingAccount = null;
            this.sendingAccount = account;
        }
    }

    public void undo() {
        if (receivingAccount == null){
            sendingAccount.balance += money;
            sendingAccount.getTransactions().remove(this);
        }
        if (sendingAccount == null){
            receivingAccount.balance -= money;
            receivingAccount.getTransactions().remove(this);
        }else{
            if (receivingAccount instanceof AssetAccount) {
                receivingAccount.balance -= money;
            } else {
                receivingAccount.balance += money;
            }
            if (receivingAccount instanceof AssetAccount) {
                sendingAccount.balance += money;
            } else {
                sendingAccount.balance -= money;
            }
            sendingAccount.getTransactions().remove(this);
            receivingAccount.getTransactions().remove(this);
        }
    }

    double getMoney() {
        return this.money;
    }

    @Override
    public String toString() {
        if (this.sendingAccount == null) {
            return null + "," + receivingAccount.getAccountNum() + ","
                    + money + "," + ATM.format.format(transactionTime);

        } else if (this.receivingAccount == null) {
            return sendingAccount.getAccountNum() + "," + null + ","
                        + money + "," + ATM.format.format(transactionTime);
        } else {
            return sendingAccount.getAccountNum() + "," + receivingAccount.getAccountNum() + ","
                    + money + "," + ATM.format.format(transactionTime);
        }
    }

    String show(){
        Account sending;
        Account receiving;
        String message = "";
        receiving = receivingAccount;
        sending = sendingAccount;
        if (sending == null) {
            message += receiving.getAccountNum() + " deposited $"
                    + String.format("%.2f", money) + " on " + ATM.format.format(transactionTime);

        } else if (receiving == null) {
            message += sending.getAccountNum() + " withdrew $"
                    + String.format("%.2f", money) + " on " + ATM.format.format(transactionTime);
        } else {
            message += sending.getAccountNum() + " sent " + receiving.getAccountNum() + " $"
                    + String.format("%.2f", money) + " on " + ATM.format.format(transactionTime);
        }
        return message;
    }

}