package account;


import manageFile.*;
import machine.ATM;

public class LineOfCreditAccount extends DebtAccount implements TransferOutable{

    LineOfCreditAccount(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    public void transferOut(double amount, Account toAccount) throws Exception{
        if (amount <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else if (this == toAccount){
            throw new Exception("Cannot transfer to the same account.");
        } else if (isOverLimit()){
            throw new Exception("You have reached your limit of this account.");
        } else {
            double money = amount;
            if (toAccount.getAccountType() == AccountType.USDChequing) {
                money = currencyExchange(amount, "USD");
            }
            Transaction transaction = new Transaction(money, toAccount, this, ATM.getATM().date);
            this.getTransactions().add(transaction);
            toAccount.getTransactions().add(transaction);
            this.balance += money;
            toAccount.transferIn(money);
            WriteFile.write(transaction.toString(), "history/transactions.txt");
            this.getOwner().rewriteWholeFile();
            toAccount.getOwner().rewriteWholeFile();
        }
    }

    public void payBill(double money, String nonUser) throws Exception {
        if (money <= 0) {
            throw new Exception("A negative amount of money is not valid.");
        } else if (isOverLimit()){
            throw new Exception("You have reached your limit of this account.");
        } else {
            this.balance += money;
            String message = money + "," + this.getAccountNum() + "," + nonUser;
            WriteFile.write(message, "history/outgoing.txt");
            this.getOwner().rewriteWholeFile();
        }
    }
}
