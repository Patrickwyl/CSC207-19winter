package ATM;

class Credit extends Debt {
    Credit (String accountNum, String ownerName){
        super(accountNum, ownerName);
    }
    void withdraw(int money) throws Exception{
        if (money <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else {
            try {
                ATM.withdraw(money);
                this.balance += money;
                this.latestTransaction = new Transaction(money, this, false, ATM.date);
                WriteFile.write(this.latestTransaction.toString(), "history.txt");
                System.out.println("Successfully withdrawn " + money);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
