package ATM;


import java.util.ArrayList;
import java.util.Date;

/**
 * Creating all accounts for all customers after reading recorded information from file, or asking for creation if file
 * does not exists.
 */
class Accounts {

    static ArrayList<Account> accounts = new ArrayList<>();

    private static void addAccount(Account a) {
        accounts.add(a);
    }

    /**
     * Constructing accounts based on account types.
     */
    static void constructAccount(String[] accountInfo, Customer owner) {
        String type = accountInfo[1];
        Account account;
        switch (type){
            case ("credit"):
                account = new Credit(accountInfo[0], owner.getUsername());
                owner.creditAccounts.add((Credit)account);
                break;
            case ("chequing"):
                account = new Chequing(accountInfo[0], owner.getUsername());
                owner.chequingAccounts.add((Chequing)account);
                break;
            case ("saving"):
                account = new Saving(accountInfo[0], owner.getUsername());
                owner.savingAccounts.add((Saving)account);
                break;
            default:
                account = new Line(accountInfo[0], owner.getUsername());
                owner.linesCreditAccounts.add((Line)account);
        }
        account.balance = Double.parseDouble(accountInfo[2]);
        try {
            account.dateOfCreate = ATM.format.parse(accountInfo[3]);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (!accountInfo[4].equals("null")) {
            account.yesterdayLastTransaction = accountInfo[4].split(",");
        }
        addAccount(account);
    }


    /**
     * Using account number to find an Account in accounts list if exists.
     */
    static Account accountFinder(String accNum) {
        for (Account account : accounts) {
            if (account.getAccountNum().equals(accNum)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Update latest transaction for a customer. Or creating a new Transaction if transaction does not exist.
     */
    static void updateTransaction() {
        for (Account account : accounts) {
            if (account.yesterdayLastTransaction != null) {
                if (!checkDuplication(account)) {
                    Transaction trans;
                    Account sendingAcc = accountFinder(account.yesterdayLastTransaction[0]);
                    Account receivingAcc = accountFinder(account.yesterdayLastTransaction[1]);
                    double money = Double.parseDouble(account.yesterdayLastTransaction[2]);
                    try {
                        Date date = ATM.format.parse(account.yesterdayLastTransaction[3]);
                        if (sendingAcc != null && sendingAcc.getAccountNum().equals("")) {
                            trans = new Transaction(money, receivingAcc, true, date);
                        } else if (receivingAcc != null && receivingAcc.getAccountNum().equals("")) {
                            trans = new Transaction(money, sendingAcc, false, date);
                        } else {
                            trans = new Transaction(money, receivingAcc, sendingAcc, date);
                        }
                        trans.isUndo = Boolean.parseBoolean(account.yesterdayLastTransaction[4]);
                        account.latestTransaction = trans;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Transaction trans;
                    if (account == accountFinder(account.yesterdayLastTransaction[0])) {
                        trans = accountFinder(account.yesterdayLastTransaction[1]).latestTransaction;
                        account.latestTransaction = trans;
                    } else {
                        trans = accountFinder(account.yesterdayLastTransaction[2]).latestTransaction;
                        account.latestTransaction = trans;
                    }
                }
            }
        }
    }

    /**
     * Check if there's already a same Latest Transaction exists for a customer.
     */
    private static boolean checkDuplication(Account acc) {
        Account sendingAcc = accountFinder(acc.yesterdayLastTransaction[0]);
        Account receivingAcc = accountFinder(acc.yesterdayLastTransaction[1]);
        if (sendingAcc == null || receivingAcc == null) {
            return false;
        } else if (acc == sendingAcc) {
            if (receivingAcc.latestTransaction == null) {
                return false;
            } else return receivingAcc.latestTransaction.getSendingAccount() == acc;
        }
        else {
            if (sendingAcc.latestTransaction == null) {
                return false;
            } else return sendingAcc.latestTransaction.getReceivingAccount() == acc;
        }
    }


    static void depositAll(){
        ArrayList<String> data = ReadFile.readFile("deposits.txt");
        for (String deposit: data) {
            String[] info = deposit.split(",");
            if (info.length == 2){
                Customer customer = Users.customerFinder(info[0]);
                if (customer != null){
                    double money = Double.valueOf(info[1]);
                    customer.primaryChequing.deposit(money);
                }
            }else {
                Customer customer = Users.customerFinder(info[0]);
                int cash5 = Integer.valueOf(info[1]);
                int cash10 = Integer.valueOf(info[2]);
                int cash20 = Integer.valueOf(info[3]);
                int cash50 = Integer.valueOf(info[4]);
                if (customer != null){
                    customer.primaryChequing.deposit(cash5,cash10,cash20,cash50);
                }
            }
        }
    }

}