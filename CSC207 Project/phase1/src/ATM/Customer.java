package ATM;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles a customer with all his/her information.
 */
class Customer extends Person {
    /** Contains all of this customer's chequing accounts. */
    ArrayList<Chequing> chequingAccounts = new ArrayList<>();

    /** Contains all of this customer's saving accounts. */
    ArrayList<Saving> savingAccounts = new ArrayList<>();

    /** Contains all of this customer's line of credit accounts. */
    ArrayList<Line> linesCreditAccounts = new ArrayList<>();

    /** Contains all of this customer's credit accounts. */
    ArrayList<Credit> creditAccounts = new ArrayList<>();

    /** This customer's primary account. */
    Chequing primaryChequing;

    /**
     * Construct a new customer with username and initial password.
     * Constructed by manager.
     * @param username The username.
     * @param password The initial password given by manager.
     * */
    Customer(String username, String password) {
        super(username, password);
    }


    /**
     * Sets this customer's primary account.
     * @param primary The chequing account to be set as primary.
     */
    void setPrimary(Chequing primary) {
        try {
            if (chequingAccounts.contains(primary)) {
                chequingAccounts.forEach(chequing -> chequing.isPrimary = false);
                primary.isPrimary = true;
                primaryChequing = primary;
            }
        } catch (Exception e) {
            System.out.println("Invalid chequing account. Please enter the correct chequing account number.");
        }
    }

    /**
     * Customer requests an additional account from manager.
     * @param accountType The type customer wants to require.
     */

    private void requestAccount(String accountType) throws Exception {
            if (accountType.equals("saving") || accountType.equals("chequing") || accountType.equals("line") ||
            accountType.equals("credit")) {
                String request = this.getUsername() + "," + accountType;
                Manager.requests.add(request);
            } else {
                throw new Exception();
        }
    }

    /**
     * Gets this customer's net total balance.
     */
    private double getNetTotal() {
        double assetBalance = 0;
        double debtBalance = 0;
        for (Chequing chequing: chequingAccounts) {
            assetBalance += chequing.balance;
        }
        for (Saving saving: savingAccounts) {
            assetBalance += saving.balance;
        }
        for (Line line: linesCreditAccounts) {
            debtBalance += line.balance;
        }
        for (Credit credit: creditAccounts) {
            debtBalance += credit.balance;
        }
        return assetBalance - debtBalance;
    }

    /**
     * Rewrite the whole file of this customer containing all the information.
     */
    void rewriteWholeFile() {
        String fileName = "Customers/" + this.getUsername() + ".txt";
        WriteFile.clearInfo(fileName);
        WriteFile.write(this.getUsername(), fileName);
        WriteFile.write(password, fileName);
        primaryChequing.writeFile(fileName);
        for (Chequing chequingAccount : chequingAccounts) {
            if (!chequingAccount.isPrimary) {
                chequingAccount.writeFile(fileName);
            }
        }
        savingAccounts.forEach(saving -> saving.writeFile(fileName));
        linesCreditAccounts.forEach(line -> line.writeFile(fileName));
        creditAccounts.forEach(credit -> credit.writeFile(fileName));
    }

    /**
     * Get all information of this customer's chequing accounts.
     */
    private void getChequingInfo() {
        System.out.println(primaryChequing.toString());
        for (Chequing chequingAccount : chequingAccounts) {
            if (!chequingAccount.isPrimary) {
                System.out.println(chequingAccount);
            }
        }
    }

    /**
     * Get all information of this customer's saving accounts.
     */
    private void getSavingInfo() {
        savingAccounts.forEach(saving -> System.out.println(saving.toString()));
    }

    /**
     * Get all information of this customer's line of credits accounts.
     */
    private void getLineInfo() {
        linesCreditAccounts.forEach(lines -> System.out.println(lines.toString()));
    }

    /**
     * Get all information of this customer's credit accounts.
     */
    private void getCreditInfo() {
        creditAccounts.forEach(credit -> System.out.println(credit.toString()));
    }

    /**
     * Get all information of this customer's all accounts.
     */
    private void getUserInfo() {
        System.out.println(this.getUsername() + "'s net total balance is:" + getNetTotal());
        getChequingInfo();
        getSavingInfo();
        getLineInfo();
        getCreditInfo();
    }

    @Override
    void displayOptions() {
        System.out.println("Dear customer, how can I assist you? :)))");
        System.out.println("1. View accounts information.");
        System.out.println("2. Make transactions.");
        System.out.println("3. Pay bills.");
        System.out.println("4. Withdraw.");
        System.out.println("5. Request a new account.");
        System.out.println("6. Change Password.");
        System.out.println("7. Set Primary Account.");
        System.out.println("8. Deposit.");
        System.out.println("9. Log out.");
    }

    @Override
    void action(String option) throws Exception{
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case "1":
                getUserInfo();
                break;
            case "2":
                try {
                    System.out.println("Please type in your sending account number.");
                    getChequingInfo();
                    getSavingInfo();
                    getLineInfo();
                    Account sending = Accounts.accountFinder(scanner.next());
                    if (sending.getOwner() == this) {
                        System.out.println("Please type in receiving account number. You can choose from your account or " +
                                "type in other person's account.");
                        getChequingInfo();
                        getSavingInfo();
                        getLineInfo();
                        getCreditInfo();
                        Account receiving = Accounts.accountFinder(scanner.next());
                        System.out.println("Please type in amount of money you wish to transfer.");
                        double money = Double.valueOf(scanner.next());
                        switch (sending.getAccountType()) {
                            case "chequing":
                                ((Chequing) sending).transferOut(money, receiving);
                                System.out.println("Successfully transferred money!");
                                break;
                            case "saving":
                                ((Saving) sending).transferOut(money, receiving);
                                System.out.println("Successfully transferred money!");
                                break;
                            case "line":
                                ((Line) sending).transferOut(money, receiving);
                                System.out.println("Successfully transferred money!");
                                break;
                            default:
                                System.out.println("Credit accounts cannot perform transfer out action.");
                                break;
                        }
                    } else {
                        System.out.println("Please type in your own account number!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Unable to transfer." + e.getMessage());
                }
                break;
            case "3":
                try {
                    System.out.println("Please type in your sending account number.");
                    getChequingInfo();
                    getSavingInfo();
                    getLineInfo();
                    Account sending = Accounts.accountFinder(scanner.next());
                    if (sending.getOwner() == this) {
                        System.out.println("Please type in your payee.");
                        String payee = scanner.next();
                        System.out.println("Please type in amount of money you wish to pay.");
                        double money = Double.valueOf(scanner.next());
                        switch (sending.getAccountType()) {
                            case "chequing":
                                ((Chequing) sending).payBill(money, payee);
                                System.out.println("Successfully paid bill!");
                                break;
                            case "saving":
                                ((Saving) sending).payBill(money, payee);
                                System.out.println("Successfully paid bill!");
                                break;
                            case "line":
                                ((Line) sending).payBill(money, payee);
                                System.out.println("Successfully paid bill!");
                                break;
                            default:
                                System.out.println("Credit accounts cannot perform pay bill action.");
                                break;
                        }
                    } else {
                        System.out.println("Please type in your own account number!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Unable to pay bill." + e.getMessage());
                }
                break;
            case "4":
                try {
                    System.out.println("Please type in the account number you wish to withdraw from.");
                    getChequingInfo();
                    getCreditInfo();
                    Account withdraw = Accounts.accountFinder(scanner.next());
                    if (withdraw.getOwner() == this) {
                        System.out.println("Please type in amount of money you wish to withdraw.");
                        int money = Integer.valueOf(scanner.next());
                        if (withdraw.getAccountType().equals("chequing")) {
                            ((Chequing) withdraw).withdraw(money);
                        } else if (withdraw.getAccountType().equals("credit")) {
                            ((Credit) withdraw).withdraw(money);
                        } else {
                            System.out.println("Lines of credit accounts and saving accounts cannot withdraw.");
                        }
                    } else {
                        System.out.println("Please type in your own account number!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Unable to withdraw." + e.getMessage());
                }
                break;
            case "5":
                try {
                    System.out.println("Which type of account would you like to request?");
                    String type = scanner.next();
                    this.requestAccount(type);
                    System.out.println("Successfully requested a new account!");
                } catch (Exception e) {
                    System.out.println("Invalid account type. Please enter the correct account type.");
                }
                break;
            case "6":
                System.out.println("Please type in your new password.");
                String newPassword = scanner.next();
                this.changePassword(newPassword);
                System.out.println("Successfully change password!");
                break;
            case "7":
                for (int i = 0; i < chequingAccounts.size(); i++) {
                    System.out.println((i + 1) + "." + chequingAccounts.get(i).toString());
                }
                System.out.println("Please enter the index of the chequing account that you want to set to primary.");
                int index = Integer.valueOf(scanner.next()) - 1;
                if (index >= chequingAccounts.size() || index < 0) {
                    System.out.println("Invalid index. Please try again.");
                } else {
                    Chequing chequing = chequingAccounts.get(index);
                    setPrimary(chequing);
                    System.out.println("You have successfully set primary account to account "
                            + primaryChequing.getAccountNum());

                }
                break;
            case "8":
                System.out.println("If you want to deposit cash, please enter 1;" +
                        " if you want to deposit a cheque, please enter 2.");
                String choice = scanner.next();
                if (choice.equals("1")){
                    System.out.println("Please enter the amount of $5 bill");
                    int cash5 = Integer.valueOf(scanner.next());
                    System.out.println("Please enter the amount of $10 bill");
                    int cash10 = Integer.valueOf(scanner.next());
                    System.out.println("Please enter the amount of $20 bill");
                    int cash20 = Integer.valueOf(scanner.next());
                    System.out.println("Please enter the amount of $50 bill");
                    int cash50 = Integer.valueOf(scanner.next());
                    int total = cash5 * 5 + cash10*10  + cash20*20  + cash50*50;
                    if (cash5 < 0 || cash10 < 0 || cash20 < 0 || cash50 < 0){
                    System.out.println("Invalid number. Please try again.");
                    } else {
                        this.primaryChequing.deposit(cash5, cash10, cash20, cash50);
                        System.out.println("You have successfully deposit $" + total + " to your primary chequing " +
                                "account " + this.primaryChequing.getAccountNum());
                    }
                }
                else if (choice.equals("2")){
                    System.out.println("Please enter the amount of money in the cheque.");
                    int chequeMoney = Integer.valueOf(scanner.next());
                    if (chequeMoney < 0){
                        System.out.println("Invalid number. Please try again.");
                    } else{
                        this.primaryChequing.deposit(chequeMoney);
                        System.out.println("You have successfully deposit $" + chequeMoney + " to your primary " +
                                "chequing account " + this.primaryChequing.getAccountNum());
                    }
                }else{
                    System.out.println("Invalid number. Please try again.");
                }
                break;
            case "9":
                throw new Exception("Log Out");
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

}
