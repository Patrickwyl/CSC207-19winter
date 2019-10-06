package ATM;

import java.io.*;
import java.text.ParseException;
import java.util.*;

class Simulator {


    private static void setDate() {
        String ans;
        Scanner scanner = new Scanner(System.in);
        ATM.date = new Date();
        ATM.format.setLenient(false);
        boolean valid_date = false;
        while (!valid_date){
            System.out.println("Please set the date(yyyy/MM/dd):");
            ans = scanner.next();
            try {
                ATM.date = ATM.format.parse(ans);
                valid_date = true;
            } catch (ParseException e){
                System.out.println("Wrong format(yyyy/MM/dd).");
            }
        }
        System.out.println("Successfully set the date to " + ATM.format.format(ATM.date) + ".");
        String date = ATM.format.format(ATM.date);
        WriteFile.write(date, "time.txt");
    }

    private static void setSystemDate(){
        // read file from time.txt
        ArrayList<String> time = ReadFile.readFile("time.txt");
        if (time.isEmpty()){
            // set system data, save to file and to main attribute
            setDate();
        } else {
            try {
                ATM.date = (ATM.format).parse(time.get(0));
                Calendar c = Calendar.getInstance();
                c.setTime(ATM.date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                ATM.date = c.getTime();
                System.out.println("Today is " + ATM.format.format(ATM.date));
            } catch (ParseException e){
                System.out.println("Unable to load the date. Please reset system date.");
                setDate();
            }
        }
    }

    private static void initialize() {
        setSystemDate();
        WriteFile.clearInfo("time.txt");
        WriteFile.write(ATM.format.format(ATM.date), "time.txt");
        Users.constructManager();
        Users.folder = Users.getFolder();
        Users.getCustomerList();
        Accounts.updateTransaction();
        Accounts.depositAll();
        WriteFile.clearInfo("deposits.txt");
        Manager.requests = ReadFile.readFile("requests.txt");
        ArrayList<String> info = ReadFile.readFile("ATM.txt");
        String[] balance = info.get(0).split(",");
        ATM.cash5 = Integer.valueOf(balance[0]);
        ATM.cash10 = Integer.valueOf(balance[1]);
        ATM.cash20 = Integer.valueOf(balance[2]);
        ATM.cash50 = Integer.valueOf(balance[3]);
        ATM.total = Integer.valueOf(balance[4]);
        WriteFile.clearInfo("ATM.txt");
        Calendar c = Calendar.getInstance();
        c.setTime(ATM.date);
        if (c.get(Calendar.DAY_OF_MONTH) == 1){
            for (Account account: Accounts.accounts) {
                if (account.getAccountType().equals("saving")){
                    ((Saving)account).updateInterest();
                }
            }
        }
    }

    private static Person login() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please login. Please type in username: ");
            String username = scanner.next();
            System.out.println("Please type in password: ");
            String password = scanner.next();
            try {
               return Login.loginPass(username,password);
            } catch (Exception e) {
                System.out.println("Please try again." + e.getMessage());
            }
        }
    }

    private static void writeFile(){
        // write request to requests.txt
        WriteFile.clearInfo("requests.txt");
        for (String request:Manager.requests){
            WriteFile.write(request, "requests.txt");
        }
        // write customer info to each customer's txt file
        File directory = new File("phase1/files/Customers");
        directory.mkdirs();
        for (Customer customer:Users.customers){
            customer.rewriteWholeFile();
        }
        Manager manager = Users.manager;
        WriteFile.clearInfo("manager.txt");
        manager.writeFile();
        WriteFile.clearInfo("ATM.txt");
        ATM.writeFile();
    }


    public static void main(String[] args) {
        boolean running;
        System.out.println("Welcome :)");
        Scanner scanner = new Scanner(System.in);
        initialize();
        running = true;
        while (running) {
            Person person = login();
            boolean loggedIn = true;
            while (loggedIn) {
                person.displayOptions();
                String option = scanner.next();
                try {
                    person.action(option);
                } catch (Exception e) {
                    loggedIn = false;
                    if (e.getMessage().equals("Shut down")) {
                        running = false;
                    }
                }
            }
            System.out.println("You has successfully logged out. Goodbye. Have a nice day! :)");

        }
        writeFile();
    }
}
