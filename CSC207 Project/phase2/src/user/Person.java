package user;

import machine.Users;

import java.util.regex.Pattern;

/**
 * A class of Person with its username and password
 */
public abstract class Person {

    private String username;
    public String password;

    public String getUsername() {
        return username;
    }



    private static Pattern patternPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");

    public static boolean IsValidPassword(String password) {
        return patternPassword.matcher(password).matches();
    }

    /**
     * Changes the password of the customer.
     * @param newPassword The new password.
     */
    public void changePassword(String newPassword) throws Exception{
        if (IsValidPassword(newPassword)) {
            this.password = newPassword;
            Customer customer = Users.getUsers().customerFinder(username);
            FinancialAdviser financialAdviser = Users.getUsers().financialAdviserFinder(username);
            Manager manager = Users.getUsers().getManager();
            if (customer != null){
                customer.rewriteWholeFile();
            }else if(financialAdviser != null){
                financialAdviser.writeWholeFile();
            }else {
                manager.writeFile();
            }
        } else {
            throw new Exception("Not a strong password.");
        }
    }

    Person(String username, String password) {
        this.username = username;
        this.password = password;
    }
}