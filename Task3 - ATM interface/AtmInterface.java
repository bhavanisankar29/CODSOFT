import java.util.InputMismatchException;
import java.util.Scanner;

public class AtmInterface {
    public static void main(String[] args) {
        System.out.println("Welcome to the ATM Interface!");
        System.out.println("Create a new account to get started.");
        Scanner scn = new Scanner(System.in);
        double initialDeposit = 0;
        while (true) {
            try {
                System.out.print("Enter the initial deposit amount to open your bank account: ");
                initialDeposit = scn.nextDouble();
                if (initialDeposit < 0) {
                    System.out.println("Invalid input! Please enter a non-negative amount.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                scn.nextLine();
            }
        }
        BankAccount account = new BankAccount(initialDeposit);
        ATM atm = new ATM(account);
        while(true) {
            atm.showMenu();
            int choice;
            try {
                System.out.print("Please select an option (1-4): ");
                choice = scn.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number between 1 and 4.");
                scn.nextLine();
                continue;
            }
            atm.performTransactions(choice, scn);
        }
    }
}
class BankAccount {
    private double balance;

    public BankAccount(double initialDeposit) {
        this.balance = initialDeposit;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("\n Deposit successful! Current balance: Rs." + balance);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("\n Withdrawal successful! Current balance: Rs." + balance);
        } else {
            System.out.println("Invalid withdrawal amount. Please enter a positive value that does not exceed your current balance.");
        }
    }
}
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }
    public void performTransactions(int choice, Scanner scn) {
        switch (choice) {
            case 1:
                System.out.println("\n Current balance: Rs." + account.getBalance());
                break;
            case 2:
                try {
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scn.nextDouble();
                    account.deposit(depositAmount);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a numeric value.");
                    scn.nextLine();
                }
                break;
            case 3:
                try {
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scn.nextDouble();
                    account.withdraw(withdrawalAmount);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a numeric value.");
                    scn.nextLine();
                }
                break;
            case 4:
                System.out.println("Thank you for using the ATM. Goodbye!");
                scn.close();
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}