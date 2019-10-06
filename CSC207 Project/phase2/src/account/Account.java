package account;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import machine.*;
import user.*;
import manageFile.*;


/**
 * An abstract super class of all types of accounts.
 */

public abstract class Account {
    private String accountNum; // "accountType + number"
    private AccountType accountType;
    public double balance;
    Date dateOfCreate; // get System time
    private List<Transaction> Transactions = new ArrayList<>(); // Store all transactions of this account except paying bills.
    Customer owner;


    Account(String accountNum, String ownerName) {
        this.accountNum = accountNum; //created by class Manager
        this.owner = Users.getUsers().customerFinder(ownerName);
        String type = accountNum.substring(0,accountNum.length()-6);
        this.accountType = AccountType.getType(type);
        this.balance = 0;
        this.dateOfCreate = ATM.getATM().date;
    }

    public String getAccountNum() {

        return accountNum;
    }

    public AccountType getAccountType() {

        return accountType;
    }

    public ArrayList<Transaction> getTransactions() {
        return (ArrayList<Transaction>) Transactions;
    }

    public Customer getOwner() {

        return owner;
    }

    double currencyExchange(double amount, String currencyType) {
        if (currencyType.equals("CAD")) {
            return amount / ATM.getATM().getCADtoUSDRate();
        } else  {
            return amount * ATM.getATM().getCADtoUSDRate();
        }
    }

    /**
     * // Write account info to "Owner.username.txt" created by class Customer.
     * @param filename: "Owner.username.txt"
     */
    public void writeFile(String filename){
        String content = accountNum + ";" + accountType + ";" + balance + ";" + ATM.format.format(dateOfCreate)
                + ";" + getLatestTransaction();
        WriteFile.write(content, filename);
    }

    abstract void transferIn(double money); // Update balance.

    Transaction getLatestTransaction(){
        if (Transactions.size() == 0) {
            return null;
        } else {
            return Transactions.get(Transactions.size() - 1);
        }
    }

    @Override
    public String toString() {
        String message;
        try{
            message = accountNum +
                    "\nbalance:" + String.format("%.2f", balance) +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    "\nlatestTransaction:" + getLatestTransaction().show();
        } catch (Exception e) {
            message = accountNum +
                    "\nbalance:" + String.format("%.2f", balance) +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    "\nlatestTransaction:" + getLatestTransaction();
        }
        return message;
    }
}
