package user;

import java.util.ArrayList;
import java.util.List;
import account.*;
import financialProducts.*;
import manageFile.*;
import machine.*;


/**
 * This class handles a customer with all his/her information.
 */
public class Customer extends Person {
    public List<ArrayList<Account>> accounts = new ArrayList<>();

    /**
     * This customer's primary account.
     */
    private ChequingAccount primaryChequing;

    private CustomerLevel customerLevel;

    /**
     * This customer's financial.
     */
    private List<ArrayList<FinancialProduct>> financialProducts = new ArrayList<>();

    public ArrayList<ArrayList<FinancialProduct>> getFinancialProducts() {
        return (ArrayList<ArrayList<FinancialProduct>>) financialProducts;
    }

    /**
     * Construct a new customer with username and initial password.
     * Constructed by manager.
     *
     * @param username The username.
     * @param password The initial password given by manager.
     */
    public Customer(String username, String password, String customerLevel) {
        super(username, password);
        this.customerLevel = CustomerLevel.getLevel(customerLevel);
        for (int i = 0; i <= 6; i++) {
            accounts.add(new ArrayList<>());
        }
        for (int i = 0; i <= 2; i++) {
            financialProducts.add(new ArrayList<>());
        }
    }

    public int debtLimit() {
        return customerLevel.getDebtLimit();
    }

    /**
     * Sets this customer's primary account.
     *
     * @param primary The chequing account to be set as primary.
     */
    public void setPrimary(ChequingAccount primary) {
        try {
            if (accounts.get(AccountType.chequing.getIndex()).contains(primary)) {
                if (primaryChequing != null) {
                    primaryChequing.isPrimary = false;
                }
                primary.isPrimary = true;
                primaryChequing = primary;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChequingAccount getPrimaryChequing() {
        return primaryChequing;
    }


    /**
     * Customer requests an additional account from manager.
     *
     * @param accountType The type customer wants to require.
     */

    public void requestAccount(String accountType) throws Exception {
        if (AccountType.getType(accountType) != null) {
            String request = this.getUsername() + "," + accountType;
            Users.getUsers().getManager().requests.add(request);
            WriteFile.write(request, "request/accountRequests.txt");
        } else {
            throw new Exception();
        }
    }

    public void requestJointAccount(String otherOwner) throws Exception {
        if (Users.getUsers().customerFinder(otherOwner) == null
                && Users.getUsers().financialAdviserFinder(otherOwner) == null ||
                otherOwner.equals(this.getUsername())) {
            throw new Exception("The username you enter is not valid.");
        }
        String request = this.getUsername() + "," + "jointChequing," + otherOwner;
        Users.getUsers().getManager().requests.add(request);
        WriteFile.write(request, "request/accountRequests.txt");
    }

    public void requestFinancialProduct(String productType, double principle, int period) throws Exception {
        if (FinancialProductType.getType(productType) != null) {
            String request = this.getUsername() + "," + principle + "," + period + "," + productType;
            Employee.productRequests.add(request);
            WriteFile.write(request, "request/financialProductRequests.txt");
        } else {
            throw new Exception();
        }
    }

    /**
     * Gets this customer's net total balance.
     */
    private double getNetTotal() {
        double balance = 0;
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account instanceof DebtAccount) {
                    balance -= account.balance;
                } else {
                    balance += account.balance;
                }
            }
        }
        return balance;
    }

    /**
     * Rewrite the whole file of this customer containing all the information.
     */
    public void rewriteWholeFile() {
        String fileName = "Customers/" + this.getUsername() + ".txt";
        writeWholeFile(fileName);
    }

    void writeWholeFile(String fileName) {
        WriteFile.clearInfo(fileName);
        WriteFile.write(this.getUsername(), fileName);
        WriteFile.write(password, fileName);
        WriteFile.write(customerLevel.name(), fileName);
        primaryChequing.writeFile(fileName);
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account.getAccountType() == AccountType.jointChequing){
                    if (account.getOwner().getUsername().equals(this.getUsername())){
                        account.writeFile(fileName);
                    }
                }else if (account != primaryChequing) {
                    account.writeFile(fileName);
                }
            }
        }
    }

    public String userInfo() {
        StringBuilder info = new StringBuilder(this.getUsername() + "'s net total balance is:" +
                String.format("%.2f", getNetTotal()));
        info.append("\n").append(primaryChequing.toString());
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account != primaryChequing) {
                    info.append("\n").append(account);
                }
            }
        }
        return info.toString();
    }

    public String getProductInfo(){
        StringBuilder info = new StringBuilder("Your financial product(s): ");
        for (ArrayList<FinancialProduct> products: financialProducts){
            for (FinancialProduct product: products){
                info.append("\n").append(product);
            }
        }
        return info.toString();
    }

    public String transferOutableAccounts() {
        StringBuilder info = new StringBuilder("Your transferable accounts: ");
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account.getAccountType() != AccountType.credit) {
                    info.append("\n").append(account);
                }
            }
        }
        return info.toString();
    }

    public String withdrawables() {
        StringBuilder info = new StringBuilder("Your withdrawable accounts: ");
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account instanceof Withdrawable) {
                    info.append("\n").append(account);
                }
            }
        }
        return info.toString();
    }

    public String chequingInfo() {
        StringBuilder info = new StringBuilder("Your chequing accounts: ");
        for (ArrayList<Account> accountList : accounts) {
            for (Account account : accountList) {
                if (account.getAccountType() == AccountType.chequing) {
                    info.append("\n").append(account);
                }
            }
        }
        return info.toString();
    }
}