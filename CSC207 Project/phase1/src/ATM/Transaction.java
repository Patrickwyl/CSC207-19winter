package ATM;
import java.util.Date;

/**
 * This class stores transaction information.
 */
class Transaction {
    private double money;
    private Account receivingAccount;
    private Account sendingAccount;
    private final Date transactionTime;
    boolean isUndo;

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

    void undo() {
        Transaction transaction = new Transaction(money, sendingAccount, receivingAccount, ATM.date);
        if (receivingAccount != null) {
            String type = receivingAccount.getAccountType();
            if (type.equals("saving") || type.equals("chequing")) {
                receivingAccount.balance -= money;
            } else {
                receivingAccount.balance += money;
            }
            receivingAccount.latestTransaction = transaction;
        }
        if (sendingAccount != null) {
            String type = sendingAccount.getAccountType();
            if (type.equals("saving") || type.equals("chequing")) {
                sendingAccount.balance += money;
            } else {
                sendingAccount.balance -= money;
            }
            sendingAccount.latestTransaction = transaction;
        }
        if (!isUndo) {
            transaction.isUndo = true;
        }
    }

    double getMoney() {
        return this.money;
    }

    Account getReceivingAccount() {
        return this.receivingAccount;
    }

    Account getSendingAccount() {
        return this.sendingAccount;
    }

    @Override
    public String toString() {
        if (this.sendingAccount == null) {
            return null + "," + receivingAccount.getAccountNum() + ","
                    + money + "," + ATM.format.format(transactionTime) + "," + isUndo;

        } else if (this.receivingAccount == null) {
            return sendingAccount.getAccountNum() + "," + null + ","
                        + money + "," + ATM.format.format(transactionTime) + "," + isUndo;
        } else {
            return sendingAccount.getAccountNum() + ", " + receivingAccount.getAccountNum() + ","
                    + money + "," + ATM.format.format(transactionTime) + "," + isUndo;
        }
    }

    String show(){
        Account sending;
        Account receiving;
        String message = "";
        if (this.isUndo){
            sending = receivingAccount;
            receiving = sendingAccount;
            message += "Undo transaction: ";
        } else {
            receiving = receivingAccount;
            sending = sendingAccount;
        }
        if (sending == null) {
            message += receiving.getAccountNum() + " deposited $"
                    + money + " on " + ATM.format.format(transactionTime);

        } else if (receiving == null) {
            message += sending.getAccountNum() + " withdrew $"
                    + money + " on " + ATM.format.format(transactionTime);
        } else {
            message += sending.getAccountNum() + " sent " + receiving.getAccountNum() + " $"
                    + money + " on " + ATM.format.format(transactionTime);
        }
        return message;
    }

}