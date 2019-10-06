package account;

import user.Customer;

public enum AccountType {
    credit("credit account", 0), line("line of credit account", 1), chequing("chequing account", 2),
    saving("saving account", 3), moneyMarket("money market account", 4),
    jointChequing("joint chequing account", 5), USDChequing("USD chequing account", 6);

    private String name;
    private int index;

    AccountType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName(){
        return this.name;
    }
    public int getIndex(){
        return this.index;
    }
    public static AccountType getType(String name){
        for (AccountType type: AccountType.values()){
            if (type.name().equals(name) || type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }
    public Account constructAccount(String[] accountInfo, Customer owner){
        Account account = newAccount(accountInfo[0], owner.getUsername());
        account.owner = owner;
        owner.accounts.get(index).add(account);
        return account;
    }
    private Account newAccount(String accountNum, String username){
        if (this == credit) {
            return new CreditAccount(accountNum, username);
        } else if (this == line) {
            return new LineOfCreditAccount(accountNum, username);
        } else if (this == chequing) {
            return new ChequingAccount(accountNum, username);
        } else if (this == saving) {
            return new SavingAccount(accountNum, username);
        } else if (this == moneyMarket) {
            return new MoneyMarketAccount(accountNum, username);
        } else if (this == jointChequing) {
            return new JointChequingAccount(accountNum, username);
        } else {
            return new USDCheqingAccount(accountNum, username);
        }
    }
    public Account addAccount(Customer user, int[] typeNum){
        Account account;
        String username = user.getUsername();
        String number = String.valueOf(100000 + typeNum[index]);
        String accountNum = this.name() + number;
        typeNum[index] ++;
        account = newAccount(accountNum, username);
        account.owner = user;
        user.accounts.get(index).add(account);
        Accounts.getAccounts().getAccountsList().add(account);
        return account;
    }
}
