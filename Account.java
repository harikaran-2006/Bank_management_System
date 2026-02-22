package com.bank;

public class Account {

	   private long   accountId;
	    private String accountNo;
	    private String holderName;
	    private String email;
	    private String phone;
	    private double balance;
	    private String accountType;

	    public Account(String accountNo, String holderName, String email,
	                   String phone, double balance, String accountType) {
	        this.accountNo   = accountNo;
	        this.holderName  = holderName;
	        this.email       = email;
	        this.phone       = phone;
	        this.balance     = balance;
	        this.accountType = accountType;
	    }

	    public long   getAccountId()   { return accountId; }
	    public String getAccountNo()   { return accountNo; }
	    public String getHolderName()  { return holderName; }
	    public String getEmail()       { return email; }
	    public String getPhone()       { return phone; }
	    public double getBalance()     { return balance; }
	    public String getAccountType() { return accountType; }

	    public void setBalance(double balance)   { this.balance = balance; }
	    public void setAccountId(long accountId) { this.accountId = accountId; }

	    @Override
	    public String toString() {
	        return String.format(
	            "Account No : %s\nHolder     : %s\nType       : %s\n" +
	            "Balance    : %.2f\nEmail      : %s\nPhone      : %s",
	            accountNo, holderName, accountType, balance, email, phone);
	    }
	}
