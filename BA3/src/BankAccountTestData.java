import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BankAccountTestData {

	static String[] chequingTransactions = new String[] {
			"2010,2,12,19,50,18,277.41,chequing transaction 1,false",
			"2010,3,18,20,42,30,60.31,chequing transaction 2,false",
			"2010,5,21,9,13,33,140.16,chequing transaction 3,false",
			"2010,7,26,18,31,57,893.56,chequing transaction 4,false",
			"2010,8,10,4,45,7,24.9,chequing transaction 5,false",
			"2010,8,20,13,29,19,564.86,chequing transaction 6,false",
			"2010,10,25,13,58,54,317.3,chequing transaction 7,false",
			"2011,5,11,9,6,23,149.96,chequing transaction 8,false",
			"2011,5,23,0,23,42,225.82,chequing transaction 9,true",
			"2011,9,20,16,55,50,610.56,chequing transaction 10,true",
			"2011,11,23,4,34,45,822.14,chequing transaction 11,true",
			"2012,1,19,18,43,24,996.91,chequing transaction 12,true",
			"2012,3,29,19,20,3,895.86,chequing transaction 13,true",
			"2012,5,22,9,57,15,633.13,chequing transaction 14,false",
			"2012,6,22,17,28,37,955.17,chequing transaction 15,true",
			"2012,8,13,6,4,51,464.14,chequing transaction 16,false",
			"2013,2,27,6,56,30,750.23,chequing transaction 17,true",
			"2013,9,10,8,14,15,641.3,chequing transaction 18,false",
			"2013,9,24,1,48,24,100.33,chequing transaction 19,true",
			"2014,1,16,17,43,42,639.45,chequing transaction 20,false",
			"2014,5,20,17,50,14,992.03,chequing transaction 21,true",
			"2014,6,6,11,35,5,909.25,chequing transaction 22,true",
			"2014,6,10,2,33,23,214.76,chequing transaction 23,false",
			"2014,11,27,0,17,39,35.64,chequing transaction 24,false",
			"2014,12,24,11,31,12,395.2,chequing transaction 25,false"
			};

	static String[] savingsTransactions = new String[] {
			"2010,7,12,20,7,44,796.97,savings transaction 1,false",
			"2010,8,25,17,14,23,197.32,savings transaction 2,true",
			"2010,9,17,11,57,26,116.25,savings transaction 3,false",
			"2011,3,16,6,48,35,172.36,savings transaction 4,true",
			"2011,4,13,17,9,25,748.77,savings transaction 5,true",
			"2011,5,31,6,50,22,998.4,savings transaction 6,true",
			"2011,8,3,14,36,14,975.76,savings transaction 7,false",
			"2011,8,5,18,40,44,286.29,savings transaction 8,false",
			"2011,10,28,12,10,56,92.67,savings transaction 9,false",
			"2011,11,24,5,9,13,640.98,savings transaction 10,false",
			"2012,2,7,1,52,22,563.25,savings transaction 11,false",
			"2012,3,28,0,11,38,486.03,savings transaction 12,false",
			"2012,4,5,3,25,11,330.88,savings transaction 13,true",
			"2012,7,25,7,0,6,714,savings transaction 14,true",
			"2013,3,4,9,37,51,312.42,savings transaction 15,true",
			"2013,5,23,1,20,58,929.41,savings transaction 16,true",
			"2013,6,26,20,6,54,382.28,savings transaction 17,true",
			"2013,7,16,13,47,12,408.59,savings transaction 18,false",
			"2013,8,22,23,15,16,367.36,savings transaction 19,false",
			"2013,8,23,13,49,17,75.79,savings transaction 20,false",
			"2013,10,21,21,30,55,502.67,savings transaction 21,true",
			"2013,11,29,10,49,32,813.05,savings transaction 22,true",
			"2014,4,2,22,40,23,386.21,savings transaction 23,false",
			"2014,4,28,20,22,3,236.88,savings transaction 24,true",
			"2014,7,18,1,7,47,516.04,savings transaction 25,false"
			};
		
	public static BankAccount getChequingBankAccount() {
		
		BankAccount chequing = new BankAccount("chequing", 0, 3, 5);
		addTransactions(chequingTransactions, chequing);
		return chequing;
	}

	public static BankAccount getSavingsBankAccount() {

		BankAccount savings = new BankAccount("saving", 0, 3, 5);
		addTransactions(savingsTransactions, savings);
		return savings;
		
	}
	
	private static void addTransactions(String[] transactions, BankAccount account) {
		
		for (int i = 0; i < transactions.length; i++) {

			String[] items = transactions[i].split(",");

			double amount = Double.parseDouble(items[6]);
			LocalDate date = LocalDate.of(Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]));
	        LocalTime time = LocalTime.of(Integer.parseInt(items[3]), Integer.parseInt(items[4]), Integer.parseInt(items[5]));    	
	    	LocalDateTime dateTime = LocalDateTime.of(date, time);
	    	String description = items[7];
	    	boolean deposit = Boolean.parseBoolean(items[8]);

	    	if (deposit) {
	    		account.deposit(dateTime, amount, description);
	    	} else {
	    		account.withdraw(dateTime, amount, description);
	    	}
	    	
	    	System.out.println(String.format("%d %f", i + 1, amount));
	    		
		}
		
	}
	
}
