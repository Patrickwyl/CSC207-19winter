package financialProducts;
import machine.*;
import manageFile.WriteFile;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class LoanProduct extends FinancialProduct{
    private LoanRateSaver loanRateSaver;
    private double outstandingBalance;
    private double monthlyPayment;

    // Input period in year.
    public LoanProduct(double principle, int period, String ownerName) {
        super(principle, period, ownerName);
        this.outstandingBalance = principle;
        this.productType = FinancialProductType.getType("Loan");
        this.nextDateOfPayment = setPaymentDate(dateOfStart);
        this.loanRateSaver = new LoanRateSaver(period);
        this.monthlyPayment = loanRateSaver.computePayment(principle);
    }


    @Override
    public void makePayment() {
        if (nextDateOfPayment.compareTo(ATM.getATM().date) == 0) {
            try {
                owner.getPrimaryChequing().repayLoan(monthlyPayment);
                this.outstandingBalance = loanRateSaver.computePrincipleRepaid(getCurrentTerm(), principle);
                owner.rewriteWholeFile();
                if (nextDateOfPayment.compareTo(dateOfMature) != 0) {
                    nextDateOfPayment = setPaymentDate(nextDateOfPayment);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.outstandingBalance = outstandingBalance*(1+loanRateSaver.getInterest());
                loanRateSaver.setTerm(period*12 - getCurrentTerm());
                this.monthlyPayment = loanRateSaver.computePayment(outstandingBalance);
                this.nextDateOfPayment = setPaymentDate(nextDateOfPayment);
                this.dateOfMature = setPaymentDate(dateOfMature);
            }
            WriteFile.clearInfo("history/financialProducts/LoanProducts.txt");
            reWriteWholeFile();
        }
    }

    private Date setPaymentDate(Date date) {
        Calendar c  = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    private int getCurrentTerm() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant start = dateOfStart.toInstant();
        LocalDate localStart = start.atZone(defaultZoneId).toLocalDate();
        Instant next = nextDateOfPayment.toInstant();
        LocalDate localNext = next.atZone(defaultZoneId).toLocalDate();
        Period p = Period.between(localStart, localNext);
        return p.getMonths();
    }

    @Override
    public void writeFile(){
        String content = owner.getUsername() + ";" + productType.getName() + ";" + this.period + ";" +
                ATM.format.format(dateOfStart) + ";" + this.principle + ";" + this.outstandingBalance + ";" +
                ATM.format.format(this.nextDateOfPayment);
        WriteFile.write(content, "history/financialProducts/LoanProducts.txt");
    }

    @Override
    public String toString() {
        return (this.productType.getName() +
                ": period:" + this.period +
                ", date of start:" + ATM.format.format(this.dateOfStart) +
                ", principle:" + String.format("%.2f", this.principle) +
                ", outstanding balance:" + String.format("%.2f", this.outstandingBalance) +
                ", next payment date:" + ATM.format.format(this.nextDateOfPayment));
    }
}
