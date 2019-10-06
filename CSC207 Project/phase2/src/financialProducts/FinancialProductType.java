package financialProducts;

import machine.Users;
import user.Customer;

public enum FinancialProductType {
    bond("Bond", 0), gic("GIC", 1), loan("Loan", 2);

    private String name;
    private int index;

    FinancialProductType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName(){
        return this.name;
    }
    public int getIndex(){
        return this.index;
    }

    public static FinancialProductType getType(String name){
        for (FinancialProductType type: FinancialProductType.values()){
            if (type.name().equals(name) || type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }

    private FinancialProduct newFinancialProduct(double principle, int period, String ownerName) throws Exception{
        Customer owner = Users.getUsers().customerFinder(ownerName);
        if (this == bond) {
            try {
                BondRateSaver bond = new BondRateSaver(period);
                owner.getPrimaryChequing().invest(bond.computePrice(principle));
                return new Bond(principle, period, ownerName);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else if (this == gic) {
            try {
                owner.getPrimaryChequing().invest(principle);
                return new GIC(principle, period, ownerName);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            return new LoanProduct(principle, period, ownerName);
        }
    }

    public void addFinancialProduct(Customer user, double principle, int period) throws Exception{
        try {
            FinancialProduct product;
            String name = user.getUsername();
            product = newFinancialProduct(principle, period, name);
            user.getFinancialProducts().get(index).add(product);
            Users.getUsers().getFinancialProducts().add(product);
            product.writeFile();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
