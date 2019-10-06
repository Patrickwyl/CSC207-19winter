package user;
public class FinancialAdviser extends Employee {

    private FinancialUser financialUser;

    public FinancialUser getFinancialUser() {
        return financialUser;
    }

    public FinancialAdviser(Customer customer) {
        super(customer.getUsername(), customer.password);
        this.financialUser = (FinancialUser) customer;
    }

    public static class FinancialUser extends Customer {
        public FinancialUser(String username, String password, String customerLevel) {
            super(username, password, customerLevel);
        }
    }

    void writeWholeFile() {
        String fileName = "financialAdvisers/" + this.getUsername() + ".txt";
        financialUser.writeWholeFile(fileName);
    }
}
