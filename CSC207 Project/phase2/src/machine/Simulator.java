package machine;

import java.io.*;
import java.util.*;

import financialProducts.FinancialProduct;
import user.*;
import account.*;
import manageFile.*;

public class Simulator {
    private static Simulator simulator;

    private Simulator(){

    }

    /**
     * Use singleton to ensure there is only one Simulator and avoid too many static attributes and methods.
     */
    public static Simulator getSimulator(){
        if (simulator == null){
            simulator = new Simulator();
        }
        return simulator;
    }

    public void constructManager(String username, String password){
        int[] constant = {0,0,0,0,0,0,0};
        Manager manager = Manager.getManager(username, password, constant);
        Users.getUsers().setManager(manager);
    }

    public void setSystemDate() throws Exception{
        // read file from time.txt
        ArrayList<String> time = ReadFile.readFile("ATM/time.txt");
        if (time.isEmpty()){
            // set system data, save to file and to main attribute
            throw new Exception();
        } else {
            ATM.getATM().date = (ATM.format).parse(time.get(0));
        }
    }

    public void initialize(){
        File directory1 = new File("phase2/files/Customers");
        directory1.mkdirs();
        File directory2 = new File("phase2/files/financialAdvisers");
        directory2.mkdirs();
        Users.getUsers().loadFile();
        Accounts.getAccounts().updateTransaction();
        Accounts.getAccounts().depositAll();
        WriteFile.clearInfo("ATM/deposits.txt");
        Users.getUsers().getManager().requests = ReadFile.readFile("request/accountRequests.txt");
        Employee.productRequests = ReadFile.readFile("request/financialProductRequests.txt");
        ATM.getATM().loadFile();
        Accounts.getAccounts().loadInterests();
    }

    public Person loginPass(String username, String password) throws Exception {
        Customer customer = Users.getUsers().customerFinder(username);
        FinancialAdviser financialAdviser = Users.getUsers().financialAdviserFinder(username);
        Manager manager = Users.getUsers().getManager();
        //If the person is a customer, check username and password.
        if (customer != null) {
            return LoginPassHelper(customer, password);
        } //If the person is a financialAdviser, check username and password.
        else if(financialAdviser != null){
            return LoginPassHelper(financialAdviser, password);
        } //If the person is a manager, check username and password.
        else if (username.equals(manager.getUsername())) {
            return LoginPassHelper(manager, password);
        } else {
            throw new Exception("This username is not valid.");
        }
    }

    private Person LoginPassHelper(Person person, String password) throws Exception {
        if (person.password.equals(password)) {
            return person;
        } else {
            throw new Exception("Wrong password.");
        }
    }
    
    public void updateDate() {
        for (Account account : Accounts.getAccounts().getAccountsList()) {
            if (account.getAccountType() == AccountType.saving) {
                ((SavingAccount) account).updateMonthlyInterest();
            } else if (account.getAccountType() == AccountType.moneyMarket) {
                ((MoneyMarketAccount) account).updateInterest();
            }
        }
        recordInterests();
        Calendar c = Calendar.getInstance();
        c.setTime(ATM.getATM().date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        ATM.getATM().date = c.getTime();
        WriteFile.clearInfo("ATM/time.txt");
        WriteFile.write(ATM.format.format(ATM.getATM().date), "ATM/time.txt");
        for (FinancialProduct product: Users.getUsers().getFinancialProducts()){
            product.makePayment();
            if (product.getDateOfMature().compareTo(ATM.getATM().date) == 0) {
                if (product.getProductType().getIndex() == 0) {
                    product.getOwner().getFinancialProducts().get(0).remove(product);
                } else if (product.getProductType().getIndex() == 1) {
                    product.getOwner().getFinancialProducts().get(1).remove(product);
                } else {
                    product.getOwner().getFinancialProducts().get(2).remove(product);
                }
                product.getOwner().rewriteWholeFile();
                Users.getUsers().getFinancialProducts().remove(product);
                WriteFile.clearInfo("history/financialProducts/BondProducts.txt");
                WriteFile.clearInfo("history/financialProducts/GICProducts.txt");
                WriteFile.clearInfo("history/financialProducts/LoanProducts.txt");
                for (FinancialProduct each: Users.getUsers().getFinancialProducts()){
                    each.writeFile();
                }
            }

        }

    }

    private void recordInterests(){
        WriteFile.clearInfo("interests/savingInterest.txt");
        WriteFile.clearInfo("interests/moneyMarketInterest.txt");
        for (Account account: Accounts.getAccounts().getAccountsList()){
            if (account.getAccountType() == AccountType.saving){
                ((SavingAccount)account).writeInterestFile();
            }else  if(account.getAccountType() == AccountType.moneyMarket){
                ((MoneyMarketAccount)account).writeInterestFile();
            }
        }
    }
}
