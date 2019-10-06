package ATM;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manager is the subclass of class Person which allows application to set system time, add account and customer,
 * undo transaction and add money to ATM.
 */
class Manager extends Person {

    private static int chequingNum;
    private static int lineNum;
    private static int savingNum;
    private static int creditNum;
    private static Manager manager;
    static ArrayList<String> requests = new ArrayList<>();

    /**
     * Use singleton design pattern to ensure there is only one manager.
     */
    private Manager(String username, String password) {
        super(username, password);
    }

    static Manager getManager(String username, String password){
        if (manager == null){
            manager = new Manager(username, password);
        }
        return manager;
    }

    static void setChequingNum(int chequingNum) {
        Manager.chequingNum = chequingNum;
    }

    static void setLineNum(int lineNum) {
        Manager.lineNum = lineNum;
    }

    static void setSavingNum(int savingNum) {
        Manager.savingNum = savingNum;
    }

    static void setCreditNum(int creditNum) {
        Manager.creditNum = creditNum;
    }


    /**
     * A method to add customer with a default primary chequing account and create a txt file to
     * store the information of the customer.
     */
    private void addCustomer(String username){
        String password = "123456";
        Customer customer = new Customer(username, password);
        Users.customers.add(customer);
        addAccount(username, "chequing");
        customer.setPrimary(customer.chequingAccounts.get(0));
    }

    /**
     * Helper methods to generate account numbers and add different types of accounts.
     */
    private static void addChequingAccount(String username, String type){
        int num = 100000 + chequingNum;
        chequingNum ++;
        Chequing chequing = new Chequing(type + num, username);
        Customer customer = Users.customerFinder(username);
        customer.chequingAccounts.add(chequing);
        Accounts.accounts.add(chequing);
    }

    private static void addSavingAccount(String username, String type){
        int num = 100000 + savingNum;
        savingNum ++;
        Saving saving = new Saving(type + num, username);
        Customer customer = Users.customerFinder(username);
        customer.savingAccounts.add(saving);
        Accounts.accounts.add(saving);
    }

    private static void addLineAccount(String username, String type){
        int num = 100000 + lineNum;
        lineNum ++;
        Line line = new Line(type + num, username);
        Customer customer = Users.customerFinder(username);
        customer.linesCreditAccounts.add(line);
        Accounts.accounts.add(line);
    }

    private static void addCreditAccount(String username, String type){
        int num = 100000 + creditNum;
        creditNum ++;
        Credit credit = new Credit(type + num, username);
        Customer customer = Users.customerFinder(username);
        customer.creditAccounts.add(credit);
        Accounts.accounts.add(credit);
    }

    private static void addAccount(String username, String type){
        switch (type) {
            case "chequing":
                addChequingAccount(username, type);
                break;
            case "saving":
                addSavingAccount(username, type);
                break;
            case "line":
                addLineAccount(username, type);
                break;
            case "credit":
                addCreditAccount(username, type);
                break;
        }
    }

    /**
     * A method that can undo transaction
     */
    private void undoTransaction(Transaction transaction){
        transaction.undo();
    }

    void writeFile(){
        // save manager information to manager.txt
        String username = getUsername();
        String password = this.password;
        String s = username + "," + password + ","
                + chequingNum + "," + lineNum + "," + savingNum + "," + creditNum;
        WriteFile.write(s,"manager.txt");
    }

    /**
     * A method that add money to ATM. When the number of different denominations of cash exceed 20, the delete the
     * alert in the alerts.txt.
     */
    private void addMoneyToATM(int c5, int c10, int c20, int c50){
        ATM.deposit(c5, c10, c20, c50);
        // if the number of each cash denomination exceeds 20, then delete alert
        if (!ATM.checkATMBalance()){
            WriteFile.clearInfo("alerts.txt");
            System.out.println("You have resolved this alert.");
        }
    }

    private static void verifyRequest(int index){
        Scanner scanner = new Scanner(System.in);
        String[] request = requests.get(index).split(",");
        System.out.println("Do you approve the request from " + request[0] + "?" +
                "Please type 'Y' or 'N'.");
        String ans = scanner.next();
        if (ans.equals("Y")){
            // request[0] is username, request[1] is account type
            addAccount(request[0], request[1]);
            System.out.println("You successfully created a new account to " + request[0]);
            requests.remove(index);
        }else if (ans.equals("N")){
            requests.remove(index);
            System.out.println("You have denied " + request[0] + "'s request.");
        }else{
            System.out.println("Please type a valid key.");
        }

    }

    @Override
    void displayOptions() {
        System.out.println("Dear manager, welcome aboard. :)))");
        System.out.println("1. Create a new customer.");
        System.out.println("2. View the new account requests");
        System.out.println("3. Undo a transaction.");
        System.out.println("4. Load money to ATM machine.");
        System.out.println("5. Change password.");
        System.out.println("6. Log out.");
        System.out.println("7. Shut down ATM.");
    }

    @Override
    void action(String option) throws Exception {
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case "1":
                System.out.println("Please enter the username of the new customer.");
                while (true) {

                    String username = scanner.next();
                    if (Users.customerFinder(username) != null || Users.manager.getUsername().equals(username)) {
                        System.out.println("The username already exists. Please type a different username");
                    } else {
                        addCustomer(username);
                        System.out.println("Successfully created new customer. Username: " + username
                                + " Password: 123456.");
                        break;
                    }
                }

                break;
            case "2":
                // loop over all the requests and print out
                for (int i = 0; i < requests.size(); i++) {
                    String[] info = requests.get(i).split(",");
                    String username = info[0];
                    String type = info[1];
                    System.out.println((i + 1) + "." + username + " requests a " + type + " account.");
                }
                System.out.println("Which request do you want to verify? Please type in the index of the request" +
                        "you want to verify.");
                // let manager choose one request to verify.
                int index = Integer.valueOf(scanner.next()) - 1;
                if (index >= requests.size() || index < 0) {
                    System.out.println("Invalid index. Please try again.");
                } else {
                    verifyRequest(index);
                }

                break;
            case "3":
                System.out.println("Please enter a account number to undo its latest transaction.");
                String accountNum = scanner.next();
                Account account = Accounts.accountFinder(accountNum);
                if (account == null) {
                    System.out.println("Invalid account number. Please try again.");
                } else {
                    Transaction latestTransaction = account.latestTransaction;
                    if (latestTransaction == null) {
                        System.out.println("There is no transaction in this account yet.");
                    } else {
                        undoTransaction(latestTransaction);
                        System.out.println("You successfully undid the latest transaction of account " + accountNum);
                    }
                }

                break;
            case "4":
                System.out.println("Please enter the amount of $5 bill you load into the ATM.");
                int cash5 = Integer.valueOf(scanner.next());
                System.out.println("Please enter the amount of $10 bill you load into the ATM.");
                int cash10 = Integer.valueOf(scanner.next());
                System.out.println("Please enter the amount of $20 bill you load into the ATM.");
                int cash20 = Integer.valueOf(scanner.next());
                System.out.println("Please enter the amount of $50 bill you load into the ATM.");
                int cash50 = Integer.valueOf(scanner.next());

                if (cash5 < 0 || cash10 < 0 || cash20 < 0 || cash50 < 0) {
                    System.out.println("Please enter a valid number!!");
                } else {
                    addMoneyToATM(cash5, cash10, cash20, cash50);
                    System.out.println("You have successfully load money to ATM.");
                }

                break;
            case "5":
                System.out.println("Please type in your new password.");
                String newPassword = scanner.next();
                this.changePassword(newPassword);
                System.out.println("Successfully change password!");

                break;
            case "6":
                throw new Exception("Log Out");

            case "7":
                throw new Exception("Shut down");

            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }
}
