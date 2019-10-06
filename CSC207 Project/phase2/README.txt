Please READ ME fully before using the program!


Getting started

How to clone the program?
The URL will be used is https://markus.teach.cs.toronto.edu/git/csc207-2019-01/group_0312
This will be the only URL that can be used to clone the program successfully. All others are not guaranteed to be right.
1. Open the IntelliJ IDEA, if you have a project that has been opened, go to "File" in the top left, and press
"Close Project" to get to the "choose project" view.
2. From the Welcome to IntelliJ IDEA window, select Check out from Version Control.
3. Paste the URL that are given and press "Clone".
4. If asked, press "Yes" to whether you are creating the project.
5. When "Import Project", choose "Create project from existing sources" and press "Next".
6. When choosing source files, choose only the "phase 2\src" one instead of both and click "Next".
7. Choose "phase 2" modules and press "Next".
8. If shown "File Already Exists", choose "Overwrite".
9. Select "1.8" as the project SDK and click "Next".
10. Now you have successfully cloned the program.

If you want to clone the program with terminal, do the following steps:
1. Open terminal.
2. Enter: $ git clone https://markus.teach.cs.toronto.edu/git/csc207-2019-01/group_0312）

* DO NOT ADD ANY FILES WHILE SETTING UP. Always click "NO".

How to run the program?
You will always need to be in IntelliJ to run the program.
If you clone the program with terminal, you need to do the following steps to open the project:
1. Open the terminal.
2. Enter: idea.
3. Click "Open" and find the right folder to click on.
Now you are in IntelliJ, do the following steps to run:
Click on phase2 >
              src >
                visualization >
       Right-click on ATMInterface > and select "Run 'ATMInterface.main()'"
If "Run 'ATMInterface.main()'" is not an option, then 1. Go to "File" in the top left and open "Project Structure".
                                                      2. Click "Project" on the left and select "1.8" to be the SDK.
                                                      3. Set the "Project language level" to "8".
                                                      4. Click on phase2 >
                                                         Right-click on src > and select "Mark Directory As -> Sources Root"
Now you should be able to run the program.
* AGAIN DO NOT ADD ANY FILES WHILE SETTING UP. Always click "NO".

This program contains sounds, so be prepared to the sudden sounds and keep the volume low just in case.

For Manager:

<1> Time
At the first time of running this ATM, you need to set the system's date. Once you successfully set the date, you
should not need to set again. Our program assumes you would shut down the machine manually every midnight and our
date calculation are based on this assumption. The date will be stored into a file named "time.txt", please do not
empty this file, or else you will have to set the date again.

<2> Manager account
After setting the date, you should create your manager account. You should follow the displayed instruction in the
program to type in your username and password. Our program has no restriction on the length and structure of your
username. For password, you need to construct one that has at least six characters which contain at least one uppercase
letter, one lowercase letter and one number. Please note that our program assumes only one manager exists and the login
info will be stored in a file named "manager.txt".
*** Please note that the program does not allow existence of customers if the manager account is not created, so please
do not delete this file or it will fail to load customers' information.

<3> Actions
Please note that you have to successfully log in to perform following actions.

1. Add customer: You will only be able to create one customer at a time. You should type in a new username different
from yourself's and any other customers' for any new customer. You need to choose a level for each customer depended on
their social credit score. Each new customer will have a default password 123456.

2. Manage customers' requests of adding accounts: You will see a display of all the user's requests and you can only
manage one at a time. By choosing the index of the requests, you have the option to approve or deny each request. If you
choose to approve, this request will be deleted.

3. Undo transactions: By typing in an account number, you can undo this account's up to 10 transactions. Please note that
if you undo one transaction(eg. A send B 3 dollars), this transaction will be deleted and the latest transaction of that
account will be the one before.

4. Manage financial products' requests: You will see a display of all the user's requests and you can only manage one at
a time. By choosing the index of the requests, you have the option to approve or deny each request. If the users do not
have enough balance in their primary chequing account, even you choose to approve the request, the financial products
still cannot be added. If approving successfully, this request will be deleted.

5. Change password: Type in your new password as instructed.

6. Load money to ATM: You have the option to load ATM after you see the alerts or whenever you feel in need, and the
current balance of the ATM is stored in "ATM.txt"
*** Please note that "ATM.txt" should never be empty. Our default ATM.txt contains a single line "50,50,50,50,4250"
If there is no money in the machine, this file should contain a single line in the format of "0,0,0,0,0".

7. Log out.

8. Add financial adviser: You will only be able to create one financial adviser at a time. You should type in a new
username different from yourself's, any other financial advisers' and customers' for any new customer. You need to
choose a level for each financial adviser depended on their social credit score. Each new financial adviser will have a
default password 123456. A financial adviser is also a customer.

9. Shut down: You should manually shut down the program every midnight. You need to login to shut down.
*** All changes made on the current date are loaded into files by the time you make changes, but please try to use the
shut down button instead of closing the window directly.


For customers:

<1> Actions
Please note that you have to successfully log in to perform following actions.

1. Get accounts info: You should get a display of all your accounts' information and a net total balance. The first
chequing account displayed will be your primary chequing account. For the first time of login, you should also notice
that you automatically have a default chequing account. You can request more chequing accounts later if you want, but
you will always have a default one. At the beginning, this default chequing will be your primary chequing, you can
set other chequing account to be primary afterwards.

2. Request financial products: You could request a financial product of any type. Our manager will handle your request.
Once your request is approved by our manager, you can check by viewing accounts info.

3. Make transactions: You could transfer between your accounts or send to other person's accounts. You have to type in
the account number to perform this action. You will get a display of all your accounts that allows you to transfer out
from. You will not be able to transfer if the amount exceeds your account balance.
*** Please note that you can only transfer to our bank's customers accounts. If you wish to pay someone who is not
our customer, please select pay bills.

4. Change password: Type in your new password as instructed.

5. Pay bills: You could pay to any person. Your paying amount should not exceed your account balance.

6. Set Primary Account: A primary account is the default destination for deposits. You can choose to set
only one of your chequing accounts(no other kinds of accounts are allowed) to be a primary account. If you do not
set one, your default primary account will be your first chequing account.

7. Withdraw: You could only withdraw from your chequing or credit accounts. Your withdraw amount could exceed your
account balance to over 100 if your account have positive balance before this action. If your account have negative
balance, you will not be able to withdraw. You will get cash in a combination of 5, 10, 20, 50 dollars bill.

8. Deposit: There are three ways to deposit your money. First, money from people who send from accounts of other banks
will be written in a file named "deposits.txt". They will be deposited to your accounts automatically once you login.
Second, you can deposit your money by cash. You will be asked to enter the correct amounts of each denomination in
5, 10, 20, 50 dollars bill. Third, you can also deposit your money by cheque. You will only be asked to enter the
amount on your cheque. You can always check balance and transactions to see the update. If you have any concerns,
please ask the manager for help.

9. Request accounts: You could request a new account of any type. Our manager will handle your request. Once your
request is approved by our manager, you can check by viewing accounts info.

10. Log out.

<2> Special notice
*** Please note that you could contact our manager if you wish to undo the transactions.
*** Paying bills cannot be undone.
*** Only manager can shut down the program every midnight by login.


For content and examples in all files:

<1> Folder Customers
This folder exists once the first customer has been successfully created. And each txt file in this folder contains
all the information of a corresponding customer.
1. Name of each txt file: "customer.username.txt"  *example:"David123.txt"
2. Content in each txt file:
first line: customer's username
second line: customer's password
third line: customer level
fourth line: primary chequing account information *format: number;type;balance;create date;latest transaction
fifth line to the last line are all the rest of accounts' information.
*example
a
1
platinum
chequing100000;chequing;6662.947140457454;2019/04/02;null,chequing100000,10000.0,2019/04/17
jointChequing100002;jointChequing;100.0;2019/04/14;chequing100001,jointChequing100002,100.0,2019/04/14;s
saving100003;saving,100.0;2018/08/09;null

<2> Folder ATM
(1) time.txt
It only contains one line that represents the current date in format of yyyy/MM/dd *example: 2019/01/01

(2) manager.txt
This contains one single line that represents all information of the manager.
format: username,password,chequingNum,lineNum,savingNum,creditNum
notice: chequingNum, lineNum, savingNum, creditNum, jointNum, MoneyMarketNum, USDChequingNum are the numbers of each
kind of accounts that are created. They are saved for creating the next account number.
*example:
manager123,1,2,0,0,0,2,4

(3) ATM.txt
"ATM.txt" should never be empty. Our default ATM.txt contains a single line "50,50,50,50,4250",
which means the ATM has 50 bills for each denomination in the order $5, $10, $20, $50 dollars bill.
If there is no money in the machine, this file should contain a single line in the format of "0,0,0,0,0".

(4) deposits.txt
Money from people who send from accounts of other banks will be written in a file named "deposits.txt".(one deposit each line)
two formats: 1.username,money
             2.username,cash5,cash10,cash20,cash50
*example:
David123,1000.0
love1314,20,10,5,20

<3> Folder financialAdvisers
This folder exists once the first financial adviser has been successfully created. And each txt file in this folder
contains all the information of a corresponding financial adviser.
1. Name of each txt file: "customer.username.txt"  *example:"David123.txt"
2. Content in each txt file:
first line: customer's username
second line: customer's password
third line: customer level
fourth line: primary chequing account information *format: number;type;balance;create date;latest transaction
fifth line to the last line are all the rest of accounts' information.
*example
d
123456
regular
chequing100001;chequing;0.0;2018/03/09;null
saving100003;saving,100.0;2018/08/09;null

<4> Folder alerts
(1) ATM_alerts.txt
When the amount of some denominations in ATM is below the limit 20, there will be one line alert in alerts.
And if the manager has dealt with the alert, it will be deleted from this file after shutting down the ATM.
*example:
The amount of some denominations is below the limit 20. The ATM now has 50 $5 bills, 0 $10 bills, 0 $20 bills,
and 0 $50 bills.

<5> Folder history
(1) outgoing.txt
All pay bills transactions are stored in outgoing.txt.(one bill each line)
*example:
100000.0,chequing100001,UofT
20.0,chequing100001,Shoppers

(2) transactions.txt
All transactions are stored in transactions.txt.(one transaction each line)
*example:
chequing100001,chequing100000,1000.0,2019/03/12

(3)
<1> Folder financialProducts
(1) BondProducts.txt
All Bond products are stored in BondProducts.txt.(one each line)
*example:
a;Bond;1;2019/04/09;1000.0;2019/10/09

(2) GICProducts.txt
All GIC products are stored in GICProducts.txt.(one each line)
*example:
a;GIC;2;2019/04/17;5000.0

(3) LoanProducts.txt
All Loan products are stored in LoanProducts.txt.(one each line)
*example:
a;Loan;1;2019/04/09;10000.0;10000.0;2019/05/09

<6> Folder request
(1) accountRequests.txt
It contains all customers' requests(one request each line).
If the manager has dealt with the request, the request will be deleted from this file after shutting down the ATM.
*example:
David123,saving
love1314,credit

(2) financialProductRequest.txt
It contains all financial products' requests(one request each line).
If the manager has dealt with the request, the request will be deleted from this file after shutting down the ATM.
*example:
1,5000.0,1,bond
1,9000.0,1,loan

<7> Folder interests
(1) savingInterest.txt
It contains all saving accounts' potentially monthly interests up to date(one account each line).
*example:
saving100000,0.0555422
saving100001,0.0

(2) moneyMarketInterest.txt
It contains all MoneyMarket accounts' potentially monthly interests and period interests up to date(one account each line).
*example:
moneyMarket100000,10,0.5583355,0.99861113
moneyMarket100003,50,2.5563589,9.55852136