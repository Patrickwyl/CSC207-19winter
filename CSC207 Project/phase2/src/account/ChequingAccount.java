package account;


import manageFile.*;
import machine.ATM;

public class ChequingAccount extends AssetAccount implements Withdrawable{

    public boolean isPrimary;

    ChequingAccount(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    public void withdraw(int money) throws Exception{
        if (balance <= money - 100 || balance <= 0){
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else if (money <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else {
            ATM.getATM().withdraw(money);
            this.balance -= money;
            Transaction transaction = new Transaction(money, this, false, ATM.getATM().date);
            this.getTransactions().add(transaction);
            WriteFile.write(transaction.toString(), "history/transactions.txt");
            System.out.println("Successfully withdrawn " + money);
            this.getOwner().rewriteWholeFile();
        }
    }

    public void deposit(double chequing) {
        this.balance += chequing;
        Transaction transaction = new Transaction(chequing, this, true, ATM.getATM().date);
        this.getTransactions().add(transaction);
        WriteFile.write(transaction.toString(), "history/transactions.txt");
        this.getOwner().rewriteWholeFile();
    }
    public void deposit(int cash5, int cash10, int cash20, int cash50){
        ATM atm = ATM.getATM();
        int total = cash5 * 5 + cash10 * 10 + cash20 * 20 + cash50 * 50;
        this.balance += total;
        Transaction transaction = new Transaction(total, this, true, ATM.getATM().date);
        this.getTransactions().add(transaction);
        atm.deposit(cash5, cash10, cash20, cash50);
        WriteFile.write(transaction.toString(), "history/transactions.txt");
        this.getOwner().rewriteWholeFile();
    }

    public void invest(double principle) throws Exception{
        if (balance <= principle - 100 || balance <= 0){
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else {
            balance -= principle;
        }
    }

    public void repayLoan(double payment) throws Exception {
        if (balance <= payment - 100 || balance <= 0) {
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else {
            balance -= payment;
        }
    }
}
