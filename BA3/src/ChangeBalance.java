import java.io.Serializable;

import javax.swing.JPanel;

/**
 * This class is a merger of the deposit and withdraw classes 
 * @author Fatima Siddiqui
 *
 */
public class ChangeBalance extends JPanel {

	private BankAccount current;
	private BankAccount chequing;
	private BankAccount savings;
	
	public double getBalance() {
		return current.getBalance();
	}
}
