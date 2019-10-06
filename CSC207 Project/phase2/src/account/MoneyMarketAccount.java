package account;

import manageFile.WriteFile;

import java.util.Calendar;

/**
 * This MoneyMarketAccount class is used to calculate both monthly interests and period interests.
 * This is different from the SavingAccount class, since the MoneyMarket Accounts have higher interest rate.
 */
public class MoneyMarketAccount extends AssetAccount implements TransferOutable{

    private int period;

    private double regularInterest;

    private double extraInterest;

    MoneyMarketAccount(String accountNum, String ownerName) {
        super(accountNum, ownerName);
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    void setRegularInterest(double regularInterest) {
        this.regularInterest = regularInterest;
    }

    void setExtraInterest(double extraInterest) {
        this.extraInterest = extraInterest;
    }

    @Override
    public void transferOut(double amount, Account toAccount) throws Exception {
        super.transferOut(amount, toAccount);
        period = 0;
        extraInterest = 0;
    }

    /**
     * Calculate the monthly interests for MoneyMarket accounts. If the balance is lower than $5000, it will use the
     * same interest rate. Otherwise, the interest rate will be higher.
     */
    private void updateRegularInterest(){
        Calendar c = Calendar.getInstance();
        c.setTime(machine.ATM.getATM().date);
        if (c.get(Calendar.DAY_OF_MONTH) != c.getActualMaximum(Calendar.DAY_OF_MONTH)){
            if (balance < 5000){
                regularInterest += (balance + regularInterest) * (0.0005 / 360);
            }else {
                regularInterest += (balance + regularInterest + extraInterest) * (0.0105 / 360);
            }
        }else {
            if (balance < 5000) {
                regularInterest += (balance + regularInterest) * (0.0005 / 360);
                balance += regularInterest;
                regularInterest = 0;
            }else {
                regularInterest += (balance + regularInterest + extraInterest) * (0.0105 / 360);
                balance += regularInterest;
                regularInterest = 0;
            }
        }
    }

    /**
     * Calculate the period interest for all MoneyMarket accounts. If the balance is lower than $5000, it will never
     * calculate the period interest. If a period which is 90 days is in progress, the period will be back to 0 days
     * when a transaction of transferring out happens. Otherwise, the period interests will be added to the accounts
     * once it reaches 90 days.
     */
    private void updateExtraInterest(){
        if (balance < 5000){
            extraInterest = 0;
            period = 0;
        }else if (period < 90){
            extraInterest += (balance + extraInterest + regularInterest) * (0.00075 / 360);
            period += 1;
        }else if (period == 90){
            extraInterest += (balance + extraInterest + regularInterest) * (0.00075 / 360);
            balance += extraInterest;
            extraInterest = 0;
            period = 0;
        }
    }

    public void updateInterest(){
        updateRegularInterest();
        updateExtraInterest();
        writeInterestFile();
        owner.rewriteWholeFile();
    }

    /**
     * Write the file everyday to save the monthly interests, period interests and period day and they will be loaded
     * back to the program once the program gets opened again.
     */
    public void writeInterestFile() {
        String update = this.getAccountNum() + "," + period + "," + regularInterest + "," + extraInterest;
        WriteFile.write(update, "interests/moneyMarketInterest.txt");
    }
}
