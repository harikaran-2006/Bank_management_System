package com.bank;
import java.util.Scanner;
public class Finalbankmanagement {
	public static void main(String[] args) {
        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==============================");
            System.out.println("   BANK MANAGEMENT SYSTEM");
            System.out.println("==============================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. List All Accounts");
            System.out.println("8. Delete Account");
            System.out.println("9. Exit");
            System.out.println("==============================");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Account No   : "); String no    = sc.nextLine();
                    System.out.print("Full Name    : "); String name  = sc.nextLine();
                    System.out.print("Email        : "); String email = sc.nextLine();
                    System.out.print("Phone        : "); String phone = sc.nextLine();
                    System.out.print("Init Deposit : "); double dep   = sc.nextDouble(); sc.nextLine();
                    System.out.print("Type (SAVINGS/CURRENT): "); String type = sc.nextLine().toUpperCase();
                    service.createAccount(no, name, email, phone, dep, type);
                }
                case 2 -> {
                    System.out.print("Account No : "); String no  = sc.nextLine();
                    System.out.print("Amount     : "); double amt = sc.nextDouble(); sc.nextLine();
                    service.deposit(no, amt);
                }
                case 3 -> {
                    System.out.print("Account No : "); String no  = sc.nextLine();
                    System.out.print("Amount     : "); double amt = sc.nextDouble(); sc.nextLine();
                    service.withdraw(no, amt);
                }
                case 4 -> {
                    System.out.print("From Account : "); String from = sc.nextLine();
                    System.out.print("To Account   : "); String to   = sc.nextLine();
                    System.out.print("Amount       : "); double amt  = sc.nextDouble(); sc.nextLine();
                    service.transfer(from, to, amt);
                }
                case 5 -> { System.out.print("Account No : "); service.checkBalance(sc.nextLine()); }
                case 6 -> { System.out.print("Account No : "); service.transactionHistory(sc.nextLine()); }
                case 7 -> service.listAllAccounts();
                case 8 -> { System.out.print("Account No : "); service.deleteAccount(sc.nextLine()); }
                case 9 -> {
                    DBConnection.closeConnection();
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
