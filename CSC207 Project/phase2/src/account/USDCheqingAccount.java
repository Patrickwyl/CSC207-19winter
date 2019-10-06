package account;

public class USDCheqingAccount extends AssetAccount{

    USDCheqingAccount(String accountNum, String ownerName) { super(accountNum, ownerName); }

    @Override
    /*
     Check if toAccount is Canadian dollars, charge transaction fee.
    */
    public void transferOut(double money, Account toAccount) throws Exception {
        super.transferOut(money, toAccount);
    }
}
