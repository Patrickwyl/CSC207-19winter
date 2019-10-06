package ATM;
import java.util.Date;

/**
 * An abstract super class of all types of accounts.
 */

abstract class Account {
    private String accountNum; // "accountType + number"
    private String accountType;
    double balance;
    Date dateOfCreate; // get System time
    Transaction latestTransaction; // Update every transaction except paying bills.
    private Customer owner;
    String[] yesterdayLastTransaction; // array of yesterday latest transaction.


    Account(String accountNum, String ownerName) {
        this.accountNum = accountNum; //created by class Manager
        this.owner = Users.customerFinder(ownerName);
        String type;
        if (accountNum.startsWith("chequing")){
            type = "chequing";
        }else if (accountNum.startsWith("saving")){
            type = "saving";
        }else if (accountNum.startsWith("credit")){
            type = "credit";
        }else {
            type = "line";
        }
        this.accountType = type;
        this.balance = 0;
        this.dateOfCreate = ATM.date;
    }

    double getBalance() {

        return balance;
    }

    String getAccountNum() {

        return accountNum;
    }

    String getAccountType() {

        return accountType;
    }

    Customer getOwner() {

        return owner;
    }

    /**
     * // Write account info to "Owner.username.txt" created by class Customer.
     * @param filename: "Owner.username.txt"
     */
    void writeFile(String filename){
        String content = accountNum + ";" + accountType + ";" + balance + ";" + ATM.format.format(dateOfCreate)
                + ";" + latestTransaction;
        WriteFile.write(content, filename);
    }

    protected abstract void transferIn(double money); // Update balance.

    @Override
    public String toString() {
        String message;
        try{
            message = accountNum +
                    ": balance:" + balance +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    ", latestTransaction:" + latestTransaction.show();
        } catch (Exception e) {
            message = accountNum +
                    ": balance:" + balance +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    ", latestTransaction:" + latestTransaction;
        }
        return message;
    }
}
