package account;

import manageFile.*;
import machine.ATM;


public abstract class AssetAccount extends Account implements TransferOutable{
    AssetAccount(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    @Override
    void transferIn(double money) {
        this.balance += money;
    }

    @Override
    public void transferOut(double amount, Account toAccount) throws Exception{
        if (balance <= amount) {
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else if (amount <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else if (this == toAccount){
            throw new Exception("Cannot transfer to the same account.");
        } else {
            double money = amount;
            if (this.getAccountType() == AccountType.USDChequing) {
                if (toAccount.getAccountType() != AccountType.USDChequing) {
                    money = currencyExchange(amount, "USD");
                }
            } else {
                if (toAccount.getAccountType() == AccountType.USDChequing) {
                    money = currencyExchange(amount, "CAD");
                }
            }
            Transaction transaction = new Transaction(amount, toAccount, this, ATM.getATM().date);
            this.getTransactions().add(transaction);
            toAccount.getTransactions().add(transaction);
            this.balance -= amount;
            toAccount.transferIn(money);
            WriteFile.write(transaction.toString(), "history/transactions.txt");
            this.getOwner().rewriteWholeFile();
            toAccount.getOwner().rewriteWholeFile();
        }
    }

    public void payBill(double money, String nonUser) throws Exception {
        if (balance <= money){
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else if (money <= 0) {
            throw new Exception("A negative amount of money is not valid.");
        } else {
            this.balance -= money;
            String message = money + "," + this.getAccountNum() + "," + nonUser;
            WriteFile.write(message, "history/outgoing.txt");
            this.getOwner().rewriteWholeFile();
        }
    }

}
