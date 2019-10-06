package financialProducts;

import java.util.Calendar;
import java.util.Date;

import machine.*;
import manageFile.WriteFile;
import user.*;

/**
 * An abstract super class for all types of financial products.
 */
public abstract class FinancialProduct {
    int period;
    double principle;
    Date dateOfStart;
    Date nextDateOfPayment;
    Date dateOfMature;
    Customer owner;
    FinancialProductType productType;

    // Input interest as Annualized percentage rate (nominal interest).
    FinancialProduct(double principle, int period, String ownerName) {
        this.period = period;
        this.principle = principle;
        this.owner = Users.getUsers().customerFinder(ownerName);
        this.dateOfStart = ATM.getATM().date;
        setMaturityDate(dateOfStart);
    }

    /**
     * Calculate and set the date of maturity when a new financial product is constructed.
     */
    public void setMaturityDate(Date start) {
        Calendar c  = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.YEAR, period);
        dateOfMature = c.getTime();
    }

    /**
     * Set the date of start when reload from file whenever program initializes.
     */
    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    /**
     * Set the next payment date when reload from file whenever program initializes.
     */
    public void setNextDateOfPayment(Date nextDateOfPayment) {
        this.nextDateOfPayment = nextDateOfPayment;
    }

    public Customer getOwner() {
        return owner;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public Date getDateOfMature() {
        return dateOfMature;
    }

    public FinancialProductType getProductType() {
        return productType;
    }
    /**
     * Abstract method to make payments.
     */
    public abstract void makePayment();

    /**
     * Abstract method to write file.
     */
    public abstract void writeFile();

    /**
     * To rewrite all files that stores financial product information when changes are made.
     */
    void reWriteWholeFile(){
        WriteFile.clearInfo("history/financialProducts/BondProducts.txt");
        WriteFile.clearInfo("history/financialProducts/GICProducts.txt");
        WriteFile.clearInfo("history/financialProducts/LoanProducts.txt");
        for (FinancialProduct product: Users.getUsers().getFinancialProducts()){
            product.writeFile();
        }
    }

}
