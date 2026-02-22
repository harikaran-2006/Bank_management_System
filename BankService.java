package com.bank;
import java.sql.Connection;
import java.sql.SQLException;
public class BankService  {

    private final BankDAO dao = new BankDAO();

    public boolean createAccount(String accNo, String name, String email,
                                  String phone, double initDeposit, String type) {
        try {
            Account acc = new Account(accNo, name, email, phone, initDeposit, type);
            boolean result = dao.createAccount(acc);
            if (result) {
                dao.logTransaction(accNo, "DEPOSIT", initDeposit, "Initial deposit");
                System.out.println("Account created: " + accNo);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    public boolean deposit(String accNo, double amount) {
        try {
            Account acc = dao.getAccount(accNo);
            if (acc == null) { System.out.println("Account not found."); return false; }
            if (amount <= 0) { System.out.println("Amount must be positive."); return false; }
            double newBalance = acc.getBalance() + amount;
            dao.updateBalance(accNo, newBalance);
            dao.logTransaction(accNo, "DEPOSIT", amount, "Cash deposit");
            System.out.printf("Deposited %.2f | New Balance: %.2f%n", amount, newBalance);
            return true;
        } catch (SQLException e) {
            System.err.println("Deposit error: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw(String accNo, double amount) {
        try {
            Account acc = dao.getAccount(accNo);
            if (acc == null)               { System.out.println("Account not found."); return false; }
            if (amount <= 0)               { System.out.println("Amount must be positive."); return false; }
            if (acc.getBalance() < amount) { System.out.println("Insufficient balance."); return false; }
            double newBalance = acc.getBalance() - amount;
            dao.updateBalance(accNo, newBalance);
            dao.logTransaction(accNo, "WITHDRAWAL", amount, "Cash withdrawal");
            System.out.printf("Withdrawn %.2f | New Balance: %.2f%n", amount, newBalance);
            return true;
        } catch (SQLException e) {
            System.err.println("Withdrawal error: " + e.getMessage());
            return false;
        }
    }

    public boolean transfer(String fromAccNo, String toAccNo, double amount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            Account from = dao.getAccount(fromAccNo);
            Account to   = dao.getAccount(toAccNo);
            if (from == null) { System.out.println("Source account not found.");      conn.rollback(); return false; }
            if (to == null)   { System.out.println("Destination account not found."); conn.rollback(); return false; }
            if (from.getBalance() < amount) { System.out.println("Insufficient balance."); conn.rollback(); return false; }
            dao.updateBalance(fromAccNo, from.getBalance() - amount);
            dao.updateBalance(toAccNo,   to.getBalance()   + amount);
            dao.logTransaction(fromAccNo, "TRANSFER", amount, "Transfer to "   + toAccNo);
            dao.logTransaction(toAccNo,   "TRANSFER", amount, "Transfer from " + fromAccNo);
            conn.commit();
            System.out.printf("Transferred %.2f from %s to %s%n", amount, fromAccNo, toAccNo);
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("Transfer error: " + e.getMessage());
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void checkBalance(String accNo) {
        try {
            Account acc = dao.getAccount(accNo);
            if (acc == null) { System.out.println("Account not found."); return; }
            System.out.printf("Balance for %s (%s): %.2f%n", acc.getHolderName(), accNo, acc.getBalance());
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void deleteAccount(String accNo) {
        try {
            boolean result = dao.deleteAccount(accNo);
            System.out.println(result ? "Account deleted." : "Account not found.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void listAllAccounts() {
        try {
            var list = dao.getAllAccounts();
            if (list.isEmpty()) { System.out.println("No accounts found."); return; }
            list.forEach(acc -> System.out.println(acc + "\n"));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void transactionHistory(String accNo) {
        try { dao.printTransactionHistory(accNo); }
        catch (SQLException e) { System.err.println("Error: " + e.getMessage()); }
    }
	

}
