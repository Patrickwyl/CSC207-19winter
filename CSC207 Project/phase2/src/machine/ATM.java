package machine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import manageFile.*;

/**
 * This ATM class is used as doing withdraw and deposit inside the ATM.
 * It is also used as writing a file for ATM, updating and checking the balance of ATM.
 */
public class ATM {

    private int cash5;
    private int cash10;
    private int cash20;
    private int cash50;
    private int total;
    public Date date;
    private double CADtoUSDRate = 1.3334;
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    private static ATM ATM;

    private ATM() {

    }

    /**
     * Use singleton to ensure there is only one ATM and avoid too many static attributes and methods.
     */
    public static ATM getATM() {
        if (ATM == null) {
            ATM = new ATM();
        }
        return ATM;
    }

    public double getCADtoUSDRate() {
        return CADtoUSDRate;
    }

    /**
     * Check ATM's balance to ensure that each denomination is enough for withdraw. If not, send alerts to manager.
     */
    public boolean checkATMBalance() {
        WriteFile.clearInfo("alerts/ATM_alerts.txt");
        if (cash5 < 20 || cash10 < 20 || cash20 < 20 || cash50 < 20) {
            WriteFile.write(displayAlert(), "alerts/ATM_alerts.txt");
            return true;
        } else {
            return false;
        }
    }

    public String displayAlert(){
        return "The amount of some denominations is below the limit 20. " + "The ATM now has " + cash5 +
                " $5 bills, " + cash10 + " $10 bills, " + cash20 + " $20 bills, and " + cash50 + " $50 bills.";
    }

    /**
     * Writing files after each withdraw and deposit to update.
     */
    private void writeFile() {
        WriteFile.clearInfo("ATM/ATM.txt");
        String update = cash5 + "," + cash10 + "," + cash20 + "," + cash50 + "," + total;
        WriteFile.write(update, "ATM/ATM.txt");
    }

    /**
     * A manageFile function for withdraw method.
     */
    private int[] withdrawCash(int money) {
        int[] cash = new int[4];
        int remain = money;
        int fifty = remain / 50;
        if (fifty >= cash50) {
            remain -= 50 * cash50;
            cash[0] = cash50;
        }else {
            remain -= 50 * fifty;
            cash[0] = fifty;
        }
        if (remain > 0 ){
            int twenty = remain / 20;
            if (twenty >= cash20) {
                remain -= 20 * cash20;
                cash[1] = cash20;
            }else {
                remain -= 20 * twenty;
                cash[1] = twenty;
            }
        }
        if (remain > 0){
            int ten = remain / 10;
            if (ten >= cash10) {
                remain -= 10 * cash10;
                cash[2] = cash10;
            }else {
                remain -= 10 * ten;
                cash[2] = ten;
            }
        }
        if (remain > 0 ){
            int five = remain / 5;
            cash[3] = five;
        }
        return cash;
    }

    /**
     * For withdraw money and update the file and balance in ATM.
     */
    public void withdraw(int money) throws Exception {
        if (total == 0) {
            throw new Exception("Sorry, we are currently running out of cash. " +
                    "Our manager will deal with this situation as soon as possible. Please try to withdraw later.");
        } else if (money > total) {
            throw new Exception("Sorry, the amount you have entered exceed the ATM's balance. Please try again.");
        } else if (money % 5 != 0) {
            throw new Exception("Sorry, you have entered the invalid amount. Please try again.");
        } else {
            int[] cash = withdrawCash(money);
            cash50 -= cash[0];
            cash20 -= cash[1];
            cash10 -= cash[2];
            cash5 -= cash[3];
            total = cash5 * 5 + cash10 * 10 + cash20 * 20 + cash50 * 50;
            writeFile();
            checkATMBalance();
        }
    }

    /**
     * For deposit money and update the file and the balance in ATM.
     */
    public void deposit(int cashFive, int cashTen, int cashTwenty, int cashFifty) {
        cash5 += cashFive;
        cash10 += cashTen;
        cash20 += cashTwenty;
        cash50 += cashFifty;
        total = cash5 * 5 + cash10 * 10 + cash20 * 20 + cash50 * 50;
        writeFile();
    }

    /**
     * For loading the file back into the program.
     */
    void loadFile(){
        ArrayList<String> info = ReadFile.readFile("ATM/ATM.txt");
        String[] balance = info.get(0).split(",");
        cash5 = Integer.valueOf(balance[0]);
        cash10 = Integer.valueOf(balance[1]);
        cash20 = Integer.valueOf(balance[2]);
        cash50 = Integer.valueOf(balance[3]);
        total = Integer.valueOf(balance[4]);
    }
}
