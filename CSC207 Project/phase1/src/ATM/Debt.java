package ATM;

abstract class Debt extends Account{

    Debt(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    @Override
    protected void transferIn(double money) {
        this.balance -= money;
    }

}
