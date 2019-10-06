package account;

public abstract class DebtAccount extends Account{


    DebtAccount(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    boolean isOverLimit(){
        return balance >= this.getOwner().debtLimit();
    }

    @Override
    void transferIn(double money) {
        this.balance -= money;
    }

}
