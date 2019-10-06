package account;

public interface TransferOutable {

    void transferOut(double money, Account toAccount) throws Exception;
    void payBill(double money, String nonUser) throws Exception;
}
