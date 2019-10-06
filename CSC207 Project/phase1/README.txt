Please READ ME before using the program!


For Manager:

<1> Time
At the first time of running this ATM, you need to set the system's date. Once you successfully set the date, you
should not need to set again. Our program assumes you would shut down the machine manually every midnight and our
date calculation are based on this assumption. The date will be stored into a file named "time.txt", please do not
empty this file, or else you will have to set the date again.

<2> Manager account
After setting the date, you should create your manager account. You should follow the displayed instruction in the
program to type in your username and password. Our program has no restriction on the length and structure of your
username and password. Please note that our program assumes only one manager exists and the login info will be
stored in a file named "manager.txt".
*** Please note that the program does not allow existence of customers if the manager account is not created, so please
do not delete this file or it will fail to load customers' information.

<3> Actions
Please note that you have to successfully log in to perform following actions.
*** Please note that if you do not follow instructions (ie. typed in other things when you are asked to type in index)
you will be automatically logged out.

1. Add customer: You will only be able to create one customer at a time. You should type in a new username different
from yourself's and any other customers' for any new customer. Each new customer will have a default password 123456.

2. Manage customers' requests of adding accounts: You will see a display of all the user's requests and you can only
manage one at a time. You have the option to approve or deny each request.

3. Undo transactions: By typing in an account number, you can undo this account's latest transaction. Please note that
if you undo one transaction (eg. A send B 3 dollars), the latest transaction of that account will be "Undo transaction
A send B 3 dollars". If you choose to undo again, the program will redo the undone transaction (eg. A send B 3 dollars).

4. Load money to ATM: You have the option to load ATM after you see the alerts or whenever you feel in need, and the
current balance of the ATM is stored in "ATM.txt"
*** Please note that "ATM.txt" should never be empty. Our default ATM.txt contains a single line "50,50,50,50,4250"
If there is no money in the machine, this file should contain a single line in the format of "0,0,0,0,0".

5. Change password: Type in your new password as instructed.

6. Log out.

7. Shut down: You should manually shut down the program every midnight. You need to login to shut down.
*** All changes made on the current date will be loaded into files only by the time you shut down.



For customers:

<1> Actions
Please note that you have to successfully log in to perform following actions.

1. Get accounts info: You should get a display of all your accounts' information and a net total balance. The first
chequing account displayed will be your primary chequing account. For the first time of login, you should also notice
that you automatically have a default chequing account. You can request more chequing accounts later if you want, but
you will always have a default one. At the beginning, this default chequing will be your primary chequing, you can
set other chequing account to be primary afterwards.

2. Transfers: You could transfer between your accounts or send to other person's accounts. You have to type in the
account number to perform this action. You will get a display of all your accounts that allows you to transfer out
from. You will not be able to transfer if the amount exceeds your account balance.
*** Please note that you can only transfer to our bank's customers accounts. If you wish to pay someone who is not
our customer, please select pay bills.

3. Pay bills: You could pay to any person. Your paying amount should not exceed your account balance.

4. Withdraw: You could only withdraw from your chequing or credit accounts. Your withdraw amount could exceed your
account balance to over 100 if your account have positive balance before this action. If your account have negative
balance, you will not be able to withdraw. You will get cash in a combination of 5, 10, 20, 50 dollars bill.

5. Request accounts: You could request a new account of any type. Our manager will handle your request. Once your
request is approved by our manager, you can check by viewing accounts info.

6. Change password: Type in your new password as instructed.

7. Set Primary Account: A primary account is the default destination for deposits. You can choose to set
only one of your chequing accounts(no other kinds of accounts are allowed) to be a primary account. If you do not
set one, your default primary account will be your first chequing account.

8. Deposit: There are three ways to deposit your money. First, money from people who send from accounts of other banks
will be written in a file named "deposits.txt". They will be deposited to your accounts automatically once you login.
Second, you can deposit your money by cash. You will be asked to enter the correct amounts of each denomination in
5, 10, 20, 50 dollars bill. Third, you can also deposit your money by cheque. You will only be asked to enter the
amount on your cheque. You can always check balance and transactions to see the update. If you have any concerns,
please ask the manager for help.

9. Log out.

<2> Special notice
*** Please note that you could contact our manager if you wish to undo one transaction.
*** Paying bills cannot be undone.
*** Only manager can shut down the program every midnight by login.
*** Please note that if you do not follow instructions (ie. typed in account number when you are asked to type in index)
you will be automatically logged out.


For content and examples in all files:


<1> Folder Customers
This folder exists once the first customer has been successfully created. And each txt file in this folder contains
all the information of a corresponding customer.
1. Name of each txt file: "customer.username.txt"  *example:"David123.txt"
2. Content in each txt file:
first line: customer's username
second line: customer's password
third line: primary chequing account information *format: number;type;balance;create date;latest transaction
fourth line to the last line are all the rest of accounts' information.
*example
d
123456
chequing100001;chequing;0.0;2018/03/09;null
saving100003;saving,100.0;2018/08/09;null

<2> time.txt
It only contains one line that represents the current date in format of yyyy/MM/dd *example: 2019/01/01

<3> requests.txt
It contains all customers' requests(one request each line).
If the manager has dealt with the request, the request will be deleted from this file after shutting down the ATM.
*example:
David123,saving
love1314,credit

<4> outgoing.txt
All pay bills transactions are stored in outgoing.txt.(one bill each line)
*example:
100000.0,chequing100001,UofT
20.0,chequing100001,Shoppers

<5> ATM.txt
"ATM.txt" should never be empty. Our default ATM.txt contains a single line "50,50,50,50,4250",
which means the ATM has 50 bills for each denomination in the order $5, $10, $20, $50 dollars bill.
If there is no money in the machine, this file should contain a single line in the format of "0,0,0,0,0".

<6> alerts.txt
When the amount of some denominations in ATM is below the limit 20, there will be one line alert in alerts.
And if the manager has dealt with the alert, it will be deleted from this file after shutting down the ATM.
*example:
The amount of some denominations is below the limit 20. The ATM now has 50 $5 bills, 0 $10 bills, 0 $20 bills,
and 0 $50 bills.

<7> manager.txt
This contains one single line that represents all information of the manager.
format: username,password,chequingNum,lineNum,savingNum,creditNum
notice: chequingNum, lineNum, savingNum, creditNum are the numbers of each kind of accounts that are created. They are
saved for creating the next account number.
*example:
manager123,1,2,0,0,0

<8> history.txt
It contains all the transactions except the pay bills.(one transaction each line)
format: sending account, receiving account, amount of money, transaction time, isUndo
notice: The "isUndo" part here has only two conditions: true or false. Once the transaction happens, it will be "false".
If the manager chooses to undo it once, it will change from "false" to "true". If undoing again, it will be
back to "false".
*example:
chequing100001, chequing100000,10.0,2018/03/17,false    //this means transfer 10 from chequing100001 to chequing100000
null,chequing100001,100.0,2018/03/19,false              //this means deposit 100 by cheque
chequing100001,null,1440.0,2018/03/22,false             //this means withdraw 1440

<9> deposits.txt
Money from people who send from accounts of other banks will be written in a file named "deposits.txt".(one deposit each line)
two formats: 1.username,money
             2.username,cash5,cash10,cash20,cash50
*example:
David123,1000.0
love1314,20,10,5,20