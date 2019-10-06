package user;
import java.util.ArrayList;
import java.util.List;
import account.*;
import manageFile.*;
import machine.*;

/**
 * Manager is the subclass of class Person which allows application to set system time, add account and customer,
 * undo transaction and add money to ATM.
 */
public class Manager extends Employee {

    public List<String> requests = new ArrayList<>();
    private int[] typeNum;

    private static Manager manager;
    private Manager(String username, String password, int[] constant) {
        super(username, password);
        typeNum = constant;
        writeFile();
    }

    /**
     * Use singleton design pattern to ensure there is only one manager and avoid too many static attributes and methods.
     */
    public static Manager getManager(String username, String password, int[] constant){
        if (manager == null){
            manager = new Manager(username, password, constant);
        }
        return manager;
    }

    /**
     * A method to add customer with a default primary chequing account and create a txt file to
     * store the information of the customer.
     */
    public void addCustomer(String username, String userLevel){
        String password = "123456";
        Customer customer = new Customer(username, password, userLevel);
        Users.getUsers().getCustomers().add(customer);
        AccountType.chequing.addAccount(customer, typeNum);
        customer.setPrimary((ChequingAccount) customer.accounts.get(2).get(0));
        writeFile();
        customer.rewriteWholeFile();
    }

    public void addFinancialAdviser(String username, String userLevel) {
        String password = "123456";
        Customer customer = new FinancialAdviser.FinancialUser(username, password, userLevel);
        FinancialAdviser adviser = new FinancialAdviser(customer);
        Users.getUsers().getFinancialAdvisers().add(adviser);
        AccountType.chequing.addAccount(customer, typeNum);
        customer.setPrimary((ChequingAccount) customer.accounts.get(2).get(0));
        adviser.writeWholeFile();
    }

    /**
     * Helper methods to generate account numbers and add different types of accounts.
     */

    private void addJointChequingAccount(String username1, String username2){
        Customer customer1 = Users.getUsers().customerFinder(username1);
        Customer customer2 = Users.getUsers().customerFinder(username2);
        JointChequingAccount joint = (JointChequingAccount) AccountType.jointChequing.addAccount(customer1, typeNum);
        joint.secondOwner = customer2;
        joint.secondOwnerName = username2;
        customer2.accounts.get(AccountType.jointChequing.getIndex()).add(joint);
        Accounts.getAccounts().getAccountsList().add(joint);
        writeFile();
        customer1.rewriteWholeFile();
        customer2.rewriteWholeFile();
    }

    private void addAccount(String username, String type){
        Customer customer = Users.getUsers().customerFinder(username);
        AccountType.getType(type).addAccount(customer, typeNum);
        writeFile();
        customer.rewriteWholeFile();
    }

    /**
     * A method that can undo latest n transactions of an account.
     */
    public void undoTransaction(Account account, int number) throws Exception {
        int size = account.getTransactions().size();
        if (number > size) {
            throw new Exception("Undo unsuccessfully, the number you entered beyond the amount of transactions of this account.");
        } else {
            for (int index = size - 1; index >= size - number; index--) {
                account.getTransactions().get(index).undo();
            }
            account.getOwner().rewriteWholeFile();
            Accounts.getAccounts().rewriteWholeTransactions();
        }
    }
    void writeFile(){
        // save manager information to manager.txt
        String username = getUsername();
        String password = this.password;
        StringBuilder s = new StringBuilder(username + "," + password);
        for (int number: typeNum){
            s.append(",").append(number);
        }
        WriteFile.clearInfo("ATM/manager.txt");
        WriteFile.write(s.toString(),"ATM/manager.txt");
    }

    /**
     * A method that add money to ATM. When the number of different denominations of cash exceed 20, the delete the
     * alert in the ATM_alerts.txt.
     */
    public void addMoneyToATM(int c5, int c10, int c20, int c50){
        ATM.getATM().deposit(c5, c10, c20, c50);
        ATM.getATM().checkATMBalance();
    }

    /**
     * Approve or reject the account request form a customer.
     */
    public String[] approveAccountRequest(int index){
        String[] request = requests.get(index).split(",");
        String[] info;
        if (request[1].equals("jointChequing")) {
            addJointChequingAccount(request[0], request[2]);
            info = new String[]{request[0],request[1],request[2]};
        } else {
            // request[0] is username, request[1] is account type
            addAccount(request[0], request[1]);
            info = new String[]{request[0], request[1]};
        }
        requests.remove(index);
        WriteFile.clearInfo("request/accountRequests.txt");
        for (String data: requests){
            WriteFile.write(data, "request/accountRequests.txt");
        }
        return info;
    }

    public String rejectAccountRequest(int index){
        String[] request = requests.get(index).split(",");
        requests.remove(index);
        return request[0];
    }

}
