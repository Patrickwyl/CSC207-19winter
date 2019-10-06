package account;

import manageFile.*;

import java.util.Calendar;

/**
 * This SavingAccount class is used to calculate the monthly interest daily to every saving account.
 */
public class SavingAccount extends AssetAccount {

    private double monthlyInterest;

    void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    SavingAccount(String accountNum, String ownerName) {
        super(accountNum, ownerName);
    }

    /**
     * Calculate the monthly interest day by day and add them to the saving accounts on the first day of the month.
     */
    public void updateMonthlyInterest() {
        Calendar c = Calendar.getInstance();
        c.setTime(machine.ATM.getATM().date);
        if (c.get(Calendar.DAY_OF_MONTH) != c.getActualMaximum(Calendar.DAY_OF_MONTH)){
            monthlyInterest += (balance + monthlyInterest) * (0.0005 / 360);
        }else{
            monthlyInterest += (balance + monthlyInterest) * (0.0005 / 360);
            balance += monthlyInterest;
            monthlyInterest = 0;
        }
        writeInterestFile();
        owner.rewriteWholeFile();
    }

    /**
     * Write the file everyday to save the monthly interests and they will be loaded back to the program once it gets
     * opened again.
     */
    public void writeInterestFile() {
        String update = this.getAccountNum() + "," + monthlyInterest;
        WriteFile.write(update, "interests/savingInterest.txt");
    }
}
