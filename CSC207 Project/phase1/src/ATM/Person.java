package ATM;

/**
 * A class of Person with its username and password
 */
abstract class Person {

    private String username;
    String password;

    String getUsername() {
        return username;
    }

    /**
     * Changes the password of the customer.
     * @param newPassword The new password.
     */
    void changePassword(String newPassword) {
        this.password = newPassword;
    }

    Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    abstract void displayOptions();

    abstract void action(String option) throws Exception;
}
