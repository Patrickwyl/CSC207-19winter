package user;

import financialProducts.FinancialProductType;
import machine.Users;
import manageFile.WriteFile;

import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends Person {

    public static List<String> productRequests = new ArrayList<>();


    Employee(String username, String password) {
        super(username, password);
    }

    /**
     * Approve or reject the Financial product request form a customer.
     */
    public String[] approveProductRequest(int index) throws Exception{
        try {
            String[] request = productRequests.get(index).split(",");
            addNewFinancialProduct(request[0], Double.valueOf(request[1]), Integer.valueOf(request[2]), request[3]);
            productRequests.remove(index);
            WriteFile.clearInfo("request/financialProductRequests.txt");
            for (String data : productRequests) {
                WriteFile.write(data, "request/financialProductRequests.txt");
            }
            String[] info;
            info = new String[]{request[0], request[1], request[2], request[3]};
            return info;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String[] rejectProductRequest(int index){
        String[] request = productRequests.get(index).split(",");
        productRequests.remove(index);
        WriteFile.clearInfo("request/financialProductRequests.txt");
        for (String data: productRequests) {
            WriteFile.write(data, "request/financialProductRequests.txt");
        }
        String[] info;
        info = new String[]{request[0], request[1], request[2], request[3]};
        return info;
    }

    private void addNewFinancialProduct(String ownerName, double principle, int period, String type) throws Exception{
        try {
            Customer customer = Users.getUsers().customerFinder(ownerName);
            FinancialProductType.getType(type).addFinancialProduct(customer, principle, period);
            customer.rewriteWholeFile();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
