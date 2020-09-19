import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class BankAccountExtendedTest extends junit.framework.TestCase
{

    BankAccount chequing1;
    BankAccount chequing2;
    BankAccount chequing3;
    
    Transaction t1;
        
    protected void setUp()
    {
        chequing1 = new BankAccount("chequing1");
        chequing2 = new BankAccount("chequing2", 100);
        chequing3 = new BankAccount("chequing3", 100, 2.00, 5.0);        
    }
        
    public void testAccessors()
    {
        assertEquals("chequing2",  chequing2.getAccountNumber());
        assertEquals(100.0,  chequing2.getBalance(), 0);
        assertEquals(5.0,  chequing3.getAnnualInterestRate(), 0);
        assertEquals(2.0,  chequing3.getWithdrawalFee(), 0);
    }
    
    public void testMutators()
    {
        //test for setters
        chequing1.setWithdrawalFee(3.0);
        chequing1.setAnnualInterestRate(4.0);
        assertEquals(3.0,  chequing1.getWithdrawalFee(), 0);
        assertEquals(4.0,  chequing1.getAnnualInterestRate(), 0);
    }
    
    public void testDeposits()
    {                   
        //test various deposits and withdrawals
        chequing1.setWithdrawalFee(3.0);
        
        chequing1.deposit(500, "deposit 1");
        assertEquals(500.0,  chequing1.getBalance(), 0);
            
        chequing1.withdraw(200, "withdrawal 1");
        assertEquals(297.0,  chequing1.getBalance(), 0);            
        assertEquals(false,  chequing1.isOverDrawn());
                    
        chequing1.withdraw(300,"withdrawal 2");
        assertEquals(-6.0,  chequing1.getBalance(), 0);
        assertEquals(true,  chequing1.isOverDrawn());
        
        chequing2.deposit(0.42, "deposit 2");
        assertEquals(100.42,  chequing2.getBalance(), 0.00);
        chequing2.deposit(0.001,  "deposit 3");
        assertEquals(100.421,  chequing2.getBalance(), 0.001);
        
    }

    public void testToString() {
        
        assertEquals("BankAccount chequing1: $0.00",  chequing1.toString());
        assertEquals("BankAccount chequing2: $100.00",  chequing2.toString());
        
        chequing2.deposit(0.42,  "deposit 1");
        assertEquals("BankAccount chequing2: $100.42",  chequing2.toString());

        chequing2.deposit(0.001, "deposit 2");
        assertEquals("BankAccount chequing2: $100.42",  chequing2.toString());

        BankAccount chequing4 = new BankAccount("chequing4", -100);
        assertEquals("BankAccount chequing4: ($100.00)",  chequing4.toString());
    }
    
    public void testGetTransactions()
    {
    	chequing1.setWithdrawalFee(3.0);
    	
        LocalDate d1 = LocalDate.of(2000, 12, 31);
        LocalTime t1 = LocalTime.of(14, 15, 30);    	
    	LocalDateTime dt1 = LocalDateTime.of(d1, t1);
    	chequing1.deposit(dt1, 600, "deposit 1");
    	    	
        LocalDate d2 = LocalDate.of(2001, 01, 02);
        LocalTime t2 = LocalTime.of(14, 15, 30);    	
    	LocalDateTime dt2 = LocalDateTime.of(d2, t2);
    	chequing1.withdraw(dt2, 200, "withdrawal 1");
    	
        LocalDate d3 = LocalDate.of(2001, 01, 01);
        LocalTime t3 = LocalTime.of(14, 15, 30);    	
    	LocalDateTime dt3 = LocalDateTime.of(d3, t3);
    	chequing1.withdraw(dt3, 300, "withdrawal 2");

        LocalDate d4 = LocalDate.of(2001, 01, 03);
        LocalTime t4 = LocalTime.of(14, 15, 30);    	
    	LocalDateTime dt4 = LocalDateTime.of(d4, t4);
    	chequing1.deposit(dt4, 100, "deposit 2");
    	
    	//order should be: ["deposit 1"], ["withdrawal 2"], ["withdrawal 1"], ["deposit 2"] (past to present)
    	
    	ArrayList<Transaction> trans1 = chequing1.getTransactions(null, null);
    	assertEquals(4, trans1.size());
    	assertEquals("withdrawal 1", trans1.get(2).getDescription());
    	
    	ArrayList<Transaction> trans2 = chequing1.getTransactions(dt2, null);
    	assertEquals(2, trans2.size());
    	assertEquals("withdrawal 1", trans2.get(0).getDescription());
    	
    	ArrayList<Transaction> trans3 = chequing1.getTransactions(null, dt3);
    	assertEquals(2, trans3.size());
    	assertEquals("withdrawal 2", trans3.get(1).getDescription());
    	
    	ArrayList<Transaction> trans4 = chequing1.getTransactions(dt3, dt2);
    	assertEquals(2, trans4.size());  
    	
    	//test if transactions without dateTime will be added to end of list
        chequing1.deposit(50);
        chequing1.withdraw(50);
        ArrayList<Transaction> trans5 = chequing1.getTransactions(null, null);
        assertEquals(6, trans5.size());
        
        //test if transactions without dateTime have a dateTime equivalent to 'now'
        assertEquals(false, LocalDateTime.now().isBefore(trans5.get(5).getTransactionTime()));
        assertEquals(false, trans5.get(5).getTransactionTime() == null);
        
        //test if transactions are returned in correct order
        for (int i = 0; i < trans5.size() - 1; i++) {
            Transaction earlierTranscation = trans5.get(i);
            Transaction laterTranscation = trans5.get(i+1);
            assertEquals(false, (laterTranscation.getTransactionTime().isBefore(earlierTranscation.getTransactionTime())));            
        }
        
        


    }
   
}

