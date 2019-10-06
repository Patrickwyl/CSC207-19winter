package account;

import manageFile.*;
import user.Customer;
import machine.*;

public class  JointChequingAccount extends ChequingAccount {

    public Customer secondOwner;
    public String secondOwnerName;


    JointChequingAccount(String accountNum, String ownerName) {
        super(accountNum, ownerName);
    }

    @Override
    public void writeFile(String filename){
        String content = this.getAccountNum() + ";" + this.getAccountType() + ";" + balance + ";" + ATM.format.format(dateOfCreate)
                + ";" + this.getLatestTransaction() + ";" + secondOwnerName;
        WriteFile.write(content, filename);
    }

    @Override
    public String toString() {
        String message;
        try{
            message = getAccountNum() +
                    "\nbalance:" + String.format("%.2f", balance) +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    "\nlatestTransaction:" + getLatestTransaction().show()+
                    "\nOwners:"+ "User " + this.owner.getUsername() +" and "+ "User "+secondOwnerName;
        } catch (Exception e) {
            message = getAccountNum() +
                    "\nbalance:" + String.format("%.2f", balance) +
                    ", dateOfCreate:" + ATM.format.format(dateOfCreate) +
                    "\nlatestTransaction:" + getLatestTransaction()+
                    "\nOwners:"+ "User " + this.owner.getUsername() +" and "+ "User "+secondOwnerName;
        }
        return message;
    }
}
