package machine;

import financialProducts.*;
import user.*;
import account.*;
import manageFile.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creating managers, customers and financialAdvisers after reading recorded information from files,
 * or asking for creation if file does not exists.
 */
public class Users {
    public Manager getManager() {
        return manager;
    }
    private Manager manager;
    private List<Customer> customers = new ArrayList<>();
    private List<FinancialAdviser> financialAdvisers = new ArrayList<>();
    private List<FinancialProduct> financialProducts = new ArrayList<>();
    private static Users Users;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    private Users(){}

    /**
     * Use singleton to ensure there is only one Users and avoid too many static attributes and methods.
     */
    public static Users getUsers(){
        if (Users == null) {
            Users = new Users();
        }
        return Users;
    }

    /**
     * Constructing manager.
     */
    private void constructManager() {
        try
        {
            String[] data = ReadFile.readFile("ATM/manager.txt").get(0).split(",");
            String username = data[0];
            String password = data[1];
            int[] constant = new int[7];
            for (int index = 2; index < data.length; index++){
                constant[index - 2] = Integer.valueOf(data[index]);
            }
            manager = Manager.getManager(username, password, constant);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Construct and return  a new customer.
     */
    private Customer constructUser(ArrayList<String> fileContent) {
        Customer customer = new Customer(fileContent.get(0), fileContent.get(1), fileContent.get(2));
        return constructHelper(fileContent, customer);
    }

    /**
     * Construct and return a new financialUser.
     */

    private FinancialAdviser.FinancialUser constructFinancialUser(ArrayList<String> fileContent) {
        FinancialAdviser.FinancialUser financialUser = new FinancialAdviser.FinancialUser(fileContent.get(0), fileContent.get(1), fileContent.get(2));
        return (FinancialAdviser.FinancialUser)constructHelper(fileContent, financialUser);
    }

    /**
     * A helper for constructing new customer and financialUser.
     */
    private Customer constructHelper(ArrayList<String> fileContent, Customer customer){
        for (int index = 3; index < fileContent.size(); index++){
            String[] accountInfo = fileContent.get(index).split(";");
            Accounts.getAccounts().constructAccount(accountInfo, customer);
        }
        customer.setPrimary((ChequingAccount) customer.accounts.get(AccountType.chequing.getIndex()).get(0));
        return customer;
    }


    /**
     * Use design pattern Dependency Injection when we add customer, financialAdviser and financialProduct.
     */
    private void addCustomer(Customer customer) {
        customers.add(customer);
    }
    private void addFinancialAdviser(FinancialAdviser financialAdviser) {
        financialAdvisers.add(financialAdviser);
    }
    private void addFinancialProduct(FinancialProduct financialProduct) { financialProducts.add(financialProduct); }

    /**
     * Constructing a list of Customer and load customer data if customer information exists.
     */
    private void loadCustomer() {
        ArrayList<ArrayList<String>> folder = ReadFile.readFolder("Customers");
        if (folder != null) {
            for (ArrayList<String> info : folder) {
                Customer customer = constructUser(info);
                addCustomer(customer);
            }
            for (Account account : Accounts.getAccounts().getAccountsList()) {
                if (account.getAccountType() == AccountType.jointChequing) {
                    Customer customer = customerFinder(((JointChequingAccount)account).secondOwnerName);
                    ((JointChequingAccount)account).secondOwner = customer;
                    customer.accounts.get(AccountType.jointChequing.getIndex()).add(account);
                }
            }
        }
    }

    /**
     * Constructing a list of FinancialAdviser and load financialAdviser data if financialAdviser information exists.
     */
    private void loadFinancialAdviser() {
        ArrayList<ArrayList<String>> folder = ReadFile.readFolder("FinancialAdvisers");
        if (folder != null) {
            for (ArrayList<String> info: folder) {
                Customer customer = constructFinancialUser(info);
                FinancialAdviser financialAdviser = new FinancialAdviser(customer);
                addFinancialAdviser(financialAdviser);
            }
        }
    }

    /**
     * Constructing a list of FinancialProducts and load financialProducts data if financialProduct information exists.
     */
    private void loadFinancialProduct(String filename){
        ArrayList<String> products = ReadFile.readFile(filename);
        for (String product: products){
            String[] info = product.split(";");
            double principle = Double.valueOf(info[4]);
            int period = Integer.valueOf(info[2]);
            String ownerName = info[0];
            try{
                Date startDate  = (ATM.format).parse(info[3]);
                if (info.length == 5){
                    loadGICProduct(principle, period, ownerName, startDate);
                }else if (info.length == 6){
                    Date paymentDate = (ATM.format).parse(info[5]);
                    loadBondProduct(principle, period, ownerName, startDate, paymentDate);
                }else {
                    Date paymentDate = (ATM.format).parse(info[6]);
                    double outstandingBalance = Double.valueOf(info[5]);
                    loadLoanProduct(principle, period, ownerName, startDate, paymentDate, outstandingBalance);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadGICProduct(double principle, int period, String ownerName, Date startDate){
        GIC gic = new GIC(principle, period, ownerName);
        gic.setDateOfStart(startDate);
        gic.setMaturityDate(gic.getDateOfStart());
        //gic.setMaturityDate(ATM.getATM().date);
        addFinancialProduct(gic);
        Users.customerFinder(ownerName).getFinancialProducts().get(1).add(gic);
    }

    private void loadBondProduct(double principle, int period, String ownerName, Date startDate, Date paymentDate){
        Bond bond = new Bond(principle, period, ownerName);
        //bond.setMaturityDate(ATM.getATM().date);
        bond.setDateOfStart(startDate);
        bond.setMaturityDate(bond.getDateOfStart());
        bond.setNextDateOfPayment(paymentDate);
        addFinancialProduct(bond);
        Users.customerFinder(ownerName).getFinancialProducts().get(0).add(bond);
    }

    private void loadLoanProduct(double principle, int period, String ownerName, Date startDate, Date paymentDate, double outstandingBalance){
        LoanProduct loan = new LoanProduct(principle, period, ownerName);
        loan.setDateOfStart(startDate);
        loan.setMaturityDate(loan.getDateOfStart());
        //loan.setMaturityDate(ATM.getATM().date);
        loan.setNextDateOfPayment(paymentDate);
        loan.setOutstandingBalance(outstandingBalance);
        addFinancialProduct(loan);
        Users.customerFinder(ownerName).getFinancialProducts().get(2).add(loan);
    }

    /**
     * Using username to find a Customer in customer list if exists.
     */
    public Customer customerFinder(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Using username to find a FinancialAdviser in financialAdviser list if exists.
     */
    public FinancialAdviser financialAdviserFinder(String username) {
        for (FinancialAdviser financialAdviser : financialAdvisers) {
            if (financialAdviser.getUsername().equals(username)) {
                return financialAdviser;
            }
        }
        return null;
    }

    public ArrayList<Customer> getCustomers() {
        return (ArrayList<Customer>) customers;
    }

    public ArrayList<FinancialAdviser> getFinancialAdvisers() {
        return (ArrayList<FinancialAdviser>) financialAdvisers; }

    public List<FinancialProduct> getFinancialProducts() {
        return financialProducts;
    }

    /**
     * Load all users into ATM system from files.
     */
    void loadFile(){
        constructManager();
        loadCustomer();
        loadFinancialAdviser();
        loadFinancialProduct("/history/financialProducts/BondProducts.txt");
        loadFinancialProduct("/history/financialProducts/GICProducts.txt");
        loadFinancialProduct("/history/financialProducts/LoanProducts.txt");
    }
}