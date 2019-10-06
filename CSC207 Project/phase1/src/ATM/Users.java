package ATM;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creating managers and customers after reading recorded information from file, or asking for creation if file
 * does not exists.
 */
class Users {

    static Manager manager;
    static ArrayList<ArrayList<String>> folder = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Constructing manager.
     */
    static void constructManager() {
        try
        {
            String[] data = ReadFile.readFile("manager.txt").get(0).split(",");
            String username = data[0];
            String password = data[1];
            int chequingNum = Integer.valueOf(data[2]);
            int lineNum = Integer.valueOf(data[3]);
            int savingNum = Integer.valueOf(data[4]);
            int creditNum = Integer.valueOf(data[5]);

            manager = Manager.getManager(username, password);
            Manager.setChequingNum(chequingNum);
            Manager.setLineNum(lineNum);
            Manager.setSavingNum(savingNum);
            Manager.setCreditNum(creditNum);
        }
        catch(Exception e)
        {
            System.out.println("Please construct a manager account.");
            System.out.println("Please type in username.");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.next();
            System.out.println("Please type in password.");
            String password = scanner.next();
            Users.manager = Manager.getManager(username, password);
            System.out.println("Successfully created manager account. Username: " + username +
                    ". Password: " + password);
        }

    }

    /**
     * Constructing customers.
     */
    private static void constructCustomer(ArrayList<String> fileContent) {
        Customer customer = new Customer(fileContent.get(0), fileContent.get(1));
        addCustomer(customer);
        for (int index = 2; index < fileContent.size(); index++){
            String[] accountInfo = fileContent.get(index).split(";");
            Accounts.constructAccount(accountInfo, customer);
        }
        customer.setPrimary(customer.chequingAccounts.get(0));
    }

    private static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //helper
    /**
     * Read folder "Customer".
     */
    static ArrayList<ArrayList<String>> getFolder() {
        ArrayList<ArrayList<String>> folderList;
        try
        {
            folderList = ReadFile.readFolder("Customers");
            return folderList;
        }
        catch(Exception e){
            return null;
        }
    }


    //helper
    /**
     * Constructing a list of Customer if customer information exists.
     */
    static void getCustomerList() {
        if (folder != null) {
            for (ArrayList<String> info: folder) {
                constructCustomer(info);
            }
        }
    }

    /**
     * Using username to find a Customer in customer list if exists.
     */
    static Customer customerFinder(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

}