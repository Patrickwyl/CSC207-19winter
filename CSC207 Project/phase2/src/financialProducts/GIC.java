package financialProducts;
import machine.*;
import manageFile.WriteFile;


public class GIC extends FinancialProduct {
    private GICRateSaver gicRateSaver;

    public GIC(double principle, int period, String ownerName) {
        super(principle, period, ownerName);
        this.productType = FinancialProductType.getType("GIC");
        this.gicRateSaver = new GICRateSaver(period);
        this.nextDateOfPayment = dateOfMature;
    }

    @Override
    public void makePayment() {
        if (dateOfMature.compareTo(ATM.getATM().date) == 0) {
            this.owner.getPrimaryChequing().balance += gicRateSaver.computePayment(principle);
            owner.rewriteWholeFile();
            WriteFile.clearInfo("history/financialProducts/GICProducts.txt");
            reWriteWholeFile();
        }
    }

    @Override
    public void writeFile(){
        String content = owner.getUsername() + ";" + productType.getName() + ";" + this.period + ";" +
                ATM.format.format(dateOfStart) + ";" + this.principle;
        WriteFile.write(content, "history/financialProducts/GICProducts.txt");
    }

    @Override
    public String toString() {
        return (this.productType.getName() +
                ": period:" + this.period +
                ", date of start:" + ATM.format.format(this.dateOfStart) +
                ", principle:" + String.format("%.2f", this.principle) +
                ", next payment date:" + ATM.format.format(this.dateOfMature));
    }

}
