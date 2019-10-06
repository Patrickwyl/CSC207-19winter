package ATM;

class Chequing extends Asset{

    boolean isPrimary;

    Chequing(String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    void withdraw(int money) throws Exception{
        if (balance <= money - 100 || balance <= 0){
            throw new Exception("Not enough balance in account " + this.getAccountNum());
        } else if (money <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else {
            try {
                ATM.withdraw(money);
                this.balance -= money;
                this.latestTransaction = new Transaction(money, this, false, ATM.date);
                WriteFile.write(this.latestTransaction.toString(), "history.txt");
                System.out.println("Successfully withdrawn " + money);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    void deposit(double chequing) {
        this.balance += chequing;
        this.latestTransaction = new Transaction(chequing, this, true, ATM.date);
        WriteFile.write(this.latestTransaction.toString(), "history.txt");
    }
    void deposit(int cash5, int cash10, int cash20, int cash50){
        int total = cash5 * 5 + cash10 * 10 + cash20 * 20 + cash50 * 50;
        this.balance += total;
        this.latestTransaction = new Transaction(total, this, true, ATM.date);
        ATM.deposit(cash5, cash10, cash20, cash50);
        WriteFile.write(this.latestTransaction.toString(), "history.txt");
    }
}
