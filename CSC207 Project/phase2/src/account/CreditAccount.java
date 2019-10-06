package account;


import manageFile.*;
import machine.ATM;


public class CreditAccount extends DebtAccount implements Withdrawable {

    CreditAccount(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }
    public void withdraw(int money) throws Exception{
        if (money <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else if (isOverLimit()){
            throw new Exception("You have reached your limit of this account.");
        } else {
            ATM atm = ATM.getATM();
            atm.withdraw(money);
            this.balance += money;
            Transaction transaction = new Transaction(money, this, false, atm.date);
            this.getTransactions().add(transaction);
            WriteFile.write(transaction.toString(), "history/transactions.txt");
            System.out.println("Successfully withdrawn " + money);
            this.getOwner().rewriteWholeFile();
        }
    }
}
