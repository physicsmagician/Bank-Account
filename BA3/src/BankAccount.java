import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This is a program that will act like a bank account.
 *
 * @author Fatima Siddiqui
 * @Sept. 20/2018
 */
public class BankAccount implements Serializable
{
    public String accountNumber;
    public double balance = 0.00;
    public double withdrawalFee = 0.00;
    public double annualInterestRate = 0.00;

    
    public String description;
    public LocalDateTime localDateTime = LocalDateTime.now();
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    
    //constructs a bank account 
    public BankAccount (String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
    
    public BankAccount (String accountNumber, double balance)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    public BankAccount(String accountNumber, double balance, 
                    double withdrawalFee, double annualInterestRate)
    {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.withdrawalFee = withdrawalFee;
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * Overloaded version of deposit() that includes the ability to add a time
     * @param transactionTime is the time of transaction
     * @param amount is the amount one wishes to deposit
     * @param description is what to call the transaction
     */
    public void deposit(LocalDateTime transactionTime, double amount, String description)
    {
    	deposit(amount, description);
    	this.localDateTime = transactionTime;
    	
    	if (transactionTime == null) {
    		this.localDateTime = LocalDateTime.now();
    	}
    }
    public void deposit(double amount, String description)
    {
        deposit(amount);
        this.description = description;
    }
    public void deposit(double amount) 
    {
		this.balance = balance + amount;
		
		insertTransaction(new Transaction(localDateTime, amount, description));
	}

    /**
     * Overloaded version of withdraw() that includes the ability to add a time
     * @param transactiontime is the time of transaction
     * @param amount is the amount one wishes to deposit
     * @param description is what to call the transaction
     */
    public void withdraw(LocalDateTime transactionTime, double amount, String description)
    {
    	withdraw(amount, description);
    	this.localDateTime = transactionTime;
    	
    	if (transactionTime == null) {
    		this.localDateTime = LocalDateTime.now();
    	}
    	
    }
    public void withdraw(double amount, String description)
    {
        withdraw(amount);
        this.description = description;
    
    }
    public void withdraw(double amount) 
    {
    	this.balance = (balance - amount) - withdrawalFee;
    	
    	insertTransaction(new Transaction(localDateTime, amount, description));
    }

    /**
     * Orders the transactions array list
     * @param newTransaction is the current transaction
     */
    private void insertTransaction(Transaction newTransaction) {
    	
    	int index_to_insert = 0;
    	boolean later_transaction_found = false;
    	
    	LocalDateTime dateNewTransaction = newTransaction.getTransactionTime();
    	
    	if (transactions.size() >= 1) {	
    		for (int i = 0; i < transactions.size(); i++) {
    			
    			LocalDateTime current = transactions.get(i).getTransactionTime();
    			
    			if (current.isAfter(dateNewTransaction)) {
    				index_to_insert = i;
    				transactions.add(index_to_insert, newTransaction);
    				later_transaction_found = true;
    			}
    			
    			index_to_insert = i;
    			
    			if (later_transaction_found) {
    				break;
    			}
    		
    		}
    		if (later_transaction_found == false) {
    			transactions.add(newTransaction);
    		}
    	} else {
    		transactions.add(newTransaction);
    	}
    
    }
    
    /**
     * Get all the transactions that occur between a certain time
     * @param startTime is the lower bound of the transaction time
     * @param endTime is the upper bound of the transaction time
     * @return output array list
     */
    public ArrayList<Transaction> getTransactions(LocalDateTime startTime, LocalDateTime endTime) 
    {
    	ArrayList<Transaction> output = new ArrayList<Transaction>();
    	
    	if (startTime == null & endTime == null) {	//return array
    		return transactions;
    	}
    	
    	else if (startTime == null) {	//no upper bound
    		for (int i = 0; i < transactions.size(); i++) 
    		{
    			if (transactions.get(i).getTransactionTime().isBefore(endTime)) {
    				output.add(transactions.get(i));
    			}
    			else if (transactions.get(i).getTransactionTime() == endTime) {
    				output.add(transactions.get(i));
    			}
			}
    	}
    	
    	else if (endTime == null) {	//no lower bound
    		for (int i = 0; i < transactions.size(); i++) 
    		{
    			if (transactions.get(i).getTransactionTime() == startTime) {
    				output.add(transactions.get(i));
    			}
    			else if (transactions.get(i).getTransactionTime().isAfter(startTime)) {
    				output.add(transactions.get(i));
    			}
			}
    		
    	}
    	else {	//bounds provided
    		for (Transaction transaction : transactions) 
    		{
    			if (transaction.getTransactionTime().isAfter(startTime) && transaction.getTransactionTime().isBefore(endTime)) {
    				output.add(transaction);
    			}
    			else if (transaction.getTransactionTime() == startTime | transaction.getTransactionTime() == endTime) {
    				output.add(transaction);
    			}
    		}
    	}
    		
    	return output;
    	
	}
    
    public boolean isOverDrawn()
    {
        if (balance < 0){
            return true;
        }   
        else {
            return false;
        }
    }
    
    public String getAccountNumber() 
    {
        return accountNumber;
    }
    public double getBalance()
    {
        return balance;
    }
    public double getWithdrawalFee()
    {
        return withdrawalFee;
    }
    public double getAnnualInterestRate()
    {
        return annualInterestRate;
    }
    public void setInitialBalance(double Initialbalance)
    {
        this.balance = Initialbalance;
    }
    public void setAnnualInterestRate(double annualInterestRate)
    {
        this.annualInterestRate = annualInterestRate;
    }
    public void setWithdrawalFee(double withdrawalFee)
    {
        this.withdrawalFee = withdrawalFee;
        
    }
    
    /**
     * Creates an easy to read version of transaction
     */
    public String toString()
    {
    	if (isOverDrawn()) {
        	this.balance = balance * -1;
            String statement = "BankAccount " + accountNumber + ": ($" + String.format("%.2f)", balance);    
            return statement;
    	}
    	
    	else {
            String statement =  "BankAccount " + accountNumber + ": $" + String.format("%.2f", balance);  
            return statement;
    	}
   
    }
}