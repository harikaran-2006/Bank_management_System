package com.bank;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BankDAO {
	 public boolean createAccount(Account acc) throws SQLException {
	        String sql = "INSERT INTO accounts (account_no, holder_name, email, phone, balance, account_type) VALUES (?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setString(1, acc.getAccountNo());
	            ps.setString(2, acc.getHolderName());
	            ps.setString(3, acc.getEmail());
	            ps.setString(4, acc.getPhone());
	            ps.setDouble(5, acc.getBalance());
	            ps.setString(6, acc.getAccountType());
	            return ps.executeUpdate() > 0;
	        }
	    }

	    public Account getAccount(String accountNo) throws SQLException {
	        String sql = "SELECT * FROM accounts WHERE account_no = ?";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setString(1, accountNo);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) return mapAccount(rs);
	        }
	        return null;
	    }

	    public List<Account> getAllAccounts() throws SQLException {
	        List<Account> list = new ArrayList<>();
	        String sql = "SELECT * FROM accounts ORDER BY created_at DESC";
	        try (Statement st = DBConnection.getConnection().createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	            while (rs.next()) list.add(mapAccount(rs));
	        }
	        return list;
	    }

	    public boolean updateBalance(String accountNo, double newBalance) throws SQLException {
	        String sql = "UPDATE accounts SET balance = ? WHERE account_no = ?";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setDouble(1, newBalance);
	            ps.setString(2, accountNo);
	            return ps.executeUpdate() > 0;
	        }
	    }

	    public boolean deleteAccount(String accountNo) throws SQLException {
	        String sql = "DELETE FROM accounts WHERE account_no = ?";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setString(1, accountNo);
	            return ps.executeUpdate() > 0;
	        }
	    }

	    public void logTransaction(String accountNo, String type, double amount, String desc)
	            throws SQLException {
	        String sql = "INSERT INTO transactions (account_no, txn_type, amount, description) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setString(1, accountNo);
	            ps.setString(2, type);
	            ps.setDouble(3, amount);
	            ps.setString(4, desc);
	            ps.executeUpdate();
	        }
	    }

	    public void printTransactionHistory(String accountNo) throws SQLException {
	        String sql = "SELECT * FROM transactions WHERE account_no = ? ORDER BY txn_date DESC LIMIT 10";
	        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
	            ps.setString(1, accountNo);
	            ResultSet rs = ps.executeQuery();
	            System.out.println("\n-- Last 10 Transactions for " + accountNo + " --");
	            System.out.printf("%-15s %-12s %-12s %-30s%n", "Date", "Type", "Amount", "Description");
	            System.out.println("-".repeat(70));
	            while (rs.next()) {
	                System.out.printf("%-15s %-12s %-12.2f %-30s%n",
	                    rs.getTimestamp("txn_date").toLocalDateTime().toLocalDate(),
	                    rs.getString("txn_type"),
	                    rs.getDouble("amount"),
	                    rs.getString("description"));
	            }
	        }
	    }

	    private Account mapAccount(ResultSet rs) throws SQLException {
	        Account acc = new Account(
	            rs.getString("account_no"),
	            rs.getString("holder_name"),
	            rs.getString("email"),
	            rs.getString("phone"),
	            rs.getDouble("balance"),
	            rs.getString("account_type")
	        );
	        acc.setAccountId(rs.getLong("account_id"));
	        return acc;
	    }
}
