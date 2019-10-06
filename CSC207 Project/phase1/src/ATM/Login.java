package ATM;

/**
 * Login is a class contains a helper method for customers and managers login. Return an instance Person.
 */
class Login {

    static Person loginPass(String username, String password) throws Exception {
        //If the person is a customer, check username and password.
        Customer user = Users.customerFinder(username);
        if (user != null) {
            if (user.password.equals(password)) {
                System.out.println("Successfully logged in as: " + username);
                return user;
            } else {
                throw new Exception("Wrong password.");
            }
            //If the person is a manager, check username and password.
        } else if (username.equals(Users.manager.getUsername())) {
            Manager manager = Users.manager;
            if (password.equals(manager.password)) {
                System.out.println("Successfully logged in as: " + username);
                return manager;
            } else {
                throw new Exception("Wrong password.");
            }
        } else {
            throw new Exception("This username is not valid.");
        }
    }
}
