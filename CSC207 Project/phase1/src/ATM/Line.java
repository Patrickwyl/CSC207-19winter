package ATM;

class Line extends Debt {

    Line (String accountNum, String ownerName){
        super(accountNum, ownerName);
    }

    void transferOut(double money, Account toAccount) throws Exception{
        if (money <= 0) {
            throw new Exception("The amount of money must be positive.");
        } else {
            Transaction transaction = new Transaction(money, toAccount, this, ATM.date);
            this.latestTransaction = transaction;
            toAccount.latestTransaction = transaction;
            this.balance += money;
            toAccount.transferIn(money);
            WriteFile.write(this.latestTransaction.toString(), "history.txt");
        }
    }

    void payBill(double money, String nonUser) throws Exception {
        if (money <= 0) {
            throw new Exception("A negative amount of money is not valid.");
        } else {
            this.balance += money;
            String message = money + "," + this.getAccountNum() + "," + nonUser;
            WriteFile.write(message, "outgoing.txt");
        }
    }
}
