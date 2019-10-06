package account;

import machine.*;
import user.*;
import manageFile.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creating all accounts for all customers after reading recorded information from file, or asking for creation if file
 * does not exists.
 */
public class Accounts {

    private List<Account> accountsList = new ArrayList<>();
    private static Accounts Accounts;

    private Accounts(){}

    /**
     * Use singleton to ensure there is only one Accounts and avoid too many static attributes and methods.
     */
    public static Accounts getAccounts(){
        if (Accounts == null) {
            Accounts = new Accounts();
        }
        return Accounts;
    }

    public List<Account> getAccountsList() {
        return accountsList;
    }

    /**
     * Use design pattern Dependency Injection when we add an account.
     */
    private void addAccount(Account account) {
        accountsList.add(account);
    }

    /**
     * Constructing accounts based on account types.
     */
    public void constructAccount(String[] accountInfo, Customer owner) {
        AccountType type = AccountType.getType(accountInfo[1]);
        Account account = type.constructAccount(accountInfo,owner);
        account.balance = Double.parseDouble(accountInfo[2]);
        try {
            account.dateOfCreate = ATM.format.parse(accountInfo[3]);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        addAccount(account);
        if (account.getAccountType() == AccountType.jointChequing){
            ((JointChequingAccount)account).secondOwnerName = accountInfo[5];
        }
    }


    /**
     * Using account number to find an Account in accounts list if exists.
     */
    public Account accountFinder(String accNum) {
        for (Account account : accountsList) {
            if (account.getAccountNum().equals(accNum)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Update all transactions for all accounts.
     */
    public void updateTransaction() {
        ArrayList<String> data = ReadFile.readFile("history/transactions.txt");
        for (String trans : data) {
            String[] info = trans.split(",");
            Account sendingAccount = accountFinder(info[0]);
            Account receivingAccount = accountFinder(info[1]);
            double money = Double.parseDouble(info[2]);
            try {
                Date date = ATM.format.parse(info[3]);
                Transaction transaction;
                if (sendingAccount == null) {
                    transaction = new Transaction(money, receivingAccount, true, date);
                    receivingAccount.getTransactions().add(transaction);
                } else if (receivingAccount == null) {
                    transaction = new Transaction(money, sendingAccount, false, date);
                    sendingAccount.getTransactions().add(transaction);
                } else {
                    transaction = new Transaction(money, receivingAccount, sendingAccount, date);
                    receivingAccount.getTransactions().add(transaction);
                    sendingAccount.getTransactions().add(transaction);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
    }

    /**
     * Read the deposits.txt and deposit all
     */
    public void depositAll(){
        ArrayList<String> data = ReadFile.readFile("ATM/deposits.txt");
        for (String deposit: data) {
            String[] info = deposit.split(",");
            if (info.length == 2){
                Customer customer = Users.getUsers().customerFinder(info[0]);
                if (customer != null){
                    double money = Double.valueOf(info[1]);
                    customer.getPrimaryChequing().deposit(money);
                }
            }else {
                Customer customer = Users.getUsers().customerFinder(info[0]);
                int cash5 = Integer.valueOf(info[1]);
                int cash10 = Integer.valueOf(info[2]);
                int cash20 = Integer.valueOf(info[3]);
                int cash50 = Integer.valueOf(info[4]);
                if (customer != null){
                    customer.getPrimaryChequing().deposit(cash5,cash10,cash20,cash50);
                }
            }
        }
    }

    /**
     * Rewrite transactions.txt.
     */
    public void rewriteWholeTransactions(){
        WriteFile.clearInfo("history/transactions.txt");
        for (Account account: accountsList){
            ArrayList<Transaction> transactions = account.getTransactions();
            for (Transaction transaction: transactions){
                WriteFile.write(transaction.toString(), "history/transactions.txt");
            }
        }
    }

    /**
     * Load all interest data for saving account and money market account.
     */
    public void loadInterests(){
        ArrayList<String> savingInterests = ReadFile.readFile("interests/savingInterest.txt");
        ArrayList<String> moneyMarketInterests = ReadFile.readFile("interests/moneyMarketInterest.txt");
        for (String data: savingInterests){
            String[] info = data.split(",");
            Account account = accountFinder(info[0]);
            double monthlyInterest = Double.valueOf(info[1]);
            ((SavingAccount)account).setMonthlyInterest(monthlyInterest);
        }
        for (String data: moneyMarketInterests){
            String[] info = data.split(",");
            Account account = accountFinder(info[0]);
            int period = Integer.valueOf(info[1]);
            double regularInterest = Double.valueOf(info[2]);
            double extraInterest = Double.valueOf(info[3]);
            ((MoneyMarketAccount)account).setPeriod(period);
            ((MoneyMarketAccount)account).setRegularInterest(regularInterest);
            ((MoneyMarketAccount)account).setExtraInterest(extraInterest);
        }
    }

}