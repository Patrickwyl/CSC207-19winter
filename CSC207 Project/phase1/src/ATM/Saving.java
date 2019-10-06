package ATM;

import static java.lang.StrictMath.round;

class Saving extends Asset {
    Saving(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }
    void updateInterest() {
        this.balance = this.balance * 1.001;
    }
}
