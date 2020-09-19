import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JTextField;
import javax.swing.UIManager;

public class ChangeBalanceDialog extends JDialog {
	
	/*private final String DEPOSIT_FILE = "deposit.txt";
	private final String WITHDRAW_FILE = "withdraw.txt";*/
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblProvideDescription;
	private JTextField txtEnterAmount;
	private JLabel lblProvideAmount;
	private JTextField txtEnterDescription;
	private JLabel lblWarning;
	private JButton btnOK;
	private JButton btnCancel;
	
	private ChangeBalance changeBalance = new ChangeBalance();
	private BankAccount current;

	/**
	 * Create the dialog.
	 */
	public ChangeBalanceDialog(BankAccount current, boolean isWithdraw, ChangeBalance changeBalance) {
		
		this.changeBalance = changeBalance;
		this.current = current;
		
		if (isWithdraw) {
			setTitle("Withdraw");
		} else {
			setTitle("Deposit");
		}
		setBounds(100, 100, 344, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblProvideDescription = new JLabel("(Optional) Provide a description");
			lblProvideDescription.setBounds(20, 59, 225, 14);
			lblProvideDescription.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblProvideDescription);
		}
		{
			txtEnterAmount = new JTextField("amount");
			txtEnterAmount.setBounds(20, 28, 261, 20);
			contentPanel.add(txtEnterAmount);
			txtEnterAmount.setColumns(10);
		}
		{
			lblProvideAmount = new JLabel("Please provide the amount:");
			lblProvideAmount.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblProvideAmount.setBounds(20, 11, 225, 14);
			contentPanel.add(lblProvideAmount);
		}
		{
			txtEnterDescription = new JTextField();
			txtEnterDescription.setBounds(20, 82, 261, 20);
			contentPanel.add(txtEnterDescription);
			txtEnterDescription.setColumns(10);
		}
		{
			lblWarning = new JLabel("");
			lblWarning = new JLabel((UIManager.getIcon("OptionPane.warningIcon")));
			lblWarning.setToolTipText("Input non-numeric");
			lblWarning.setBounds(291, 14, 36, 34);
			contentPanel.add(lblWarning);
		}
		{
			txtEnterAmount.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent arg0) {
					setControls();
					
				}

				public void insertUpdate(DocumentEvent arg0) {
					setControls();
					
				}

				public void removeUpdate(DocumentEvent arg0) {
					setControls();
					
				}
			});
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOK = new JButton("OK");
				btnOK.setActionCommand("OK");
				buttonPane.add(btnOK);
				getRootPane().setDefaultButton(btnOK);
				btnOK.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						btnOK_mouseClicked(arg0);
					}
				});
			}
			{
				btnCancel = new JButton("Cancel");
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
				btnCancel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						btnCancel_mouseClicked(arg0);
					}
				});
			}
		}
		
		setControls();
	}
	
	private void setControls() 
	{
		System.out.println("setControls");
		
		//ENABLING AND DISABLING CONTROLS
		boolean valueGiven = (this.txtEnterAmount.getText().isEmpty() == false);
		boolean nonNumericalValue = true; 
		
		try {
			nonNumericalValue = Double.valueOf(this.txtEnterAmount.getText()) == 0;
			
		}	catch (NumberFormatException e) {
		}
		
		this.lblWarning.setVisible(valueGiven && nonNumericalValue);
				
		boolean validValue = (nonNumericalValue == false);  
				
		this.btnOK.setEnabled(valueGiven && validValue);
		this.btnCancel.setEnabled(valueGiven && validValue);
	
	}
	protected void btnOK_mouseClicked(MouseEvent e) {
		
		if (this.btnOK.isEnabled() == false) {
			return;
		}
		
		double amount = Double.valueOf(this.txtEnterAmount.getText());
		String description = String.valueOf(this.txtEnterDescription.getText());
		if (this.txtEnterDescription.getText().isEmpty()) {
			description = "description";
		}
		this.current.description = description;
	
		if (getTitle() == "Withdraw") {
			this.current.withdraw(amount, description);
		
		} else {
			this.current.deposit(amount, description);
		}
		this.setVisible(false);
	}
	protected void btnCancel_mouseClicked(MouseEvent e)
	{
		if (this.btnCancel.isEnabled() == false) {
			return;
		}
		this.setVisible(false);	
	}
	

}
