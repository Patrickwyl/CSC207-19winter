package financialProducts;

import machine.*;
import manageFile.WriteFile;

import java.util.Calendar;
import java.util.Date;


public class Bond extends FinancialProduct {
    private BondRateSaver bondRateSaver;


    // Input period in 1, 2, 5 years.
    public Bond(double principle, int period, String ownerName) {
        super(principle, period, ownerName);
        this.bondRateSaver = new BondRateSaver(period);
        this.productType = FinancialProductType.getType("bond");
        this.nextDateOfPayment = setPaymentDate(dateOfStart);
    }

    // Make semi-annual coupon payments.
    @Override
    public void makePayment() {
        if (nextDateOfPayment.compareTo(ATM.getATM().date) == 0) {
            this.owner.getPrimaryChequing().balance += bondRateSaver.computePayment(principle);
            owner.rewriteWholeFile();
            if (nextDateOfPayment.compareTo(dateOfMature) != 0) {
                nextDateOfPayment = setPaymentDate(nextDateOfPayment);
            }
            WriteFile.clearInfo("history/financialProducts/BondProducts.txt");
            reWriteWholeFile();
        }
    }

    private Date setPaymentDate(Date date) {
        Calendar c  = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 6);
        return c.getTime();
    }

    @Override
    public void writeFile(){
        String content = owner.getUsername() + ";" + productType.getName() + ";" + this.period + ";" +
                ATM.format.format(dateOfStart) + ";" + this.principle + ";" + ATM.format.format(this.nextDateOfPayment);
        WriteFile.write(content, "history/financialProducts/BondProducts.txt");
    }

    @Override
    public String toString() {
        return (this.productType.getName() +
                ": period:" + this.period +
                ", date of start:" + ATM.format.format(this.dateOfStart) +
                ", principle:" + String.format("%.2f", this.principle) +
                ", next payment date:" + ATM.format.format(this.nextDateOfPayment));
    }
}
