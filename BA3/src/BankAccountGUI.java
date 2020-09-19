import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.SystemColor;
import java.awt.Window;

public class BankAccountGUI extends JFrame {

	private final String SETTINGS_FILE = "res/settings.txt";
	private final String SELECT_CHEQUING = "chequing";
	private final String SELECT_SAVINGS = "savings";
	
	private JPanel contentPane;
	private JLabel lblAccount;
	private JLabel lblCurrentBalance;
	private JTextField txtBalance;
	private JComboBox cboChooseAccount;
	private JButton btnWithdraw;
	private JButton btnDeposit;
	private JButton btnTransactions;
	
	//bankAccounts
	BankAccount chequing = BankAccountTestData.getChequingBankAccount();  //new BankAccount("chequing"); //
	BankAccount savings =  BankAccountTestData.getSavingsBankAccount();   //new BankAccount("savings"); //
	public BankAccount current = savings;	
	Settings settings = new Settings();

	ChangeBalanceDialog changeBalanceDialog;
	TransactionsDialog transactionsDialog;
	ChangeBalance changeBalance = new ChangeBalance();
	Transactions transaction = new Transactions();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankAccountGUI frame = new BankAccountGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BankAccountGUI() {
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		
		setTitle("BankAccount");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 214);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblAccount = new JLabel("Account:");
		lblAccount.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAccount.setBounds(10, 36, 83, 14);
		contentPane.add(lblAccount);
		
		lblCurrentBalance = new JLabel("Current Balance:");
		lblCurrentBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentBalance.setBounds(10, 89, 143, 14);
		contentPane.add(lblCurrentBalance);
		
		txtBalance = new JTextField();
		txtBalance.setBounds(136, 82, 186, 32);
		contentPane.add(txtBalance);
		txtBalance.setColumns(10);
		txtBalance.setEditable(false);
		txtBalance.setText(String.valueOf(String.format("%.2f", current.getBalance())));
		txtBalance.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent arg0) {
				setControls();

			}

			public void insertUpdate(DocumentEvent arg0) {
				readControls();

			}

			public void removeUpdate(DocumentEvent arg0) {
				readControls();

			}
		});
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnWithdraw_mouseClicked(arg0);
			}
		});
		btnWithdraw.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnWithdraw.setBounds(10, 141, 120, 23);
		contentPane.add(btnWithdraw);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeposit_mouseClicked(e);
			}
		});
		btnDeposit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDeposit.setBounds(140, 141, 111, 23);
		contentPane.add(btnDeposit);
		
		String[] accountArray = {"Chequing", "Savings"};
		 cboChooseAccount = new JComboBox(accountArray);
		 cboChooseAccount.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		cboChooseAccount_actionPerformed(e);
		 	}
		 });
		cboChooseAccount.setBackground(Color.WHITE);
		cboChooseAccount.setBounds(136, 29, 186, 33);
		contentPane.add(cboChooseAccount);
		
		btnTransactions = new JButton("Transactions");
		btnTransactions.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTransactions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnTransactions_mouseClicked(arg0);
			}
		});
		btnTransactions.setBounds(261, 141, 119, 23);
		contentPane.add(btnTransactions);
	
		Serializer s = new Serializer();
		try {
			settings = (Settings)s.deserialize(SETTINGS_FILE);
			if (settings == null) {
				throw new Exception();
			}
			
		} catch (Exception e1) {
			settings = new Settings();
		}
		
		if (settings.getCurrentAccount().equals("savings") ) {	//default option when window is first opened
			this.cboChooseAccount.setSelectedIndex(1);
			this.current = savings;
		}
		else {	
			this.cboChooseAccount.setSelectedIndex(0);
			this.current = chequing;
		}
		
		setControls();
	}
	
	//for showing the logic to the user
	//NOTE - if the document listener has setControls() within all the constructors,
	//and setControls() also attempts to reset the text area on the GUI,
	//an "Attempt to mutate in notification" error will appear...
	//This is why I have used readControls within the last two methods instead... This appears to work.
	public void setControls() 
	{
		System.out.println("setControls");

		txtBalance.setText(String.valueOf(String.format("%.2f", current.getBalance())));
		
		//ENABLING AND DISABLING OF CONTROLS
		if (!chequing.isOverDrawn() && !savings.isOverDrawn() && Double.valueOf(current.getBalance()) >= 0.0) {
			txtBalance.setForeground(Color.BLACK);
			btnWithdraw.setEnabled(true);
			if (Double.valueOf(current.getBalance()) == 0.0) {
				btnWithdraw.setEnabled(false); //do not allow user to withdraw when there is no balance
			}
		} 
		else {
			txtBalance.setForeground(Color.red);
			btnWithdraw.setEnabled(false);
		}
		
		btnDeposit.setEnabled(true);         
		btnTransactions.setEnabled(true);
	}
	
	//for interpreting the logic
	public void readControls() {
		System.out.println("readControls");
		System.out.println(current.getBalance());

	}
	
	protected void cboChooseAccount_actionPerformed(ActionEvent e) 
	{
	    if (cboChooseAccount.getSelectedIndex() == 0) {
	    	this.current = chequing;
	    } 
	    else {
	    	this.current = savings;
	    }
	    setControls();
	}

	protected void btnWithdraw_mouseClicked(MouseEvent arg0) 
	{
		if (this.btnWithdraw.isEnabled() == false) {
			return;
		}
		
		ChangeBalanceDialog withdraw = new ChangeBalanceDialog(this.current, true, this.changeBalance);
		withdraw.setLocationRelativeTo(this);
		withdraw.setModalityType(ModalityType.APPLICATION_MODAL);
		withdraw.setVisible(true);
	
		setControls();

	}
	protected void btnDeposit_mouseClicked(MouseEvent e) 
	{
		if (this.btnDeposit.isEnabled() == false) {
			return;
		}
		
		ChangeBalanceDialog deposit = new ChangeBalanceDialog(this.current, false, this.changeBalance);
		deposit.setLocationRelativeTo(this);
		deposit.setModalityType(ModalityType.APPLICATION_MODAL);
		deposit.setVisible(true);
		
		setControls();
	}
	
	protected void btnTransactions_mouseClicked(MouseEvent arg0) 
	{
		if (this.btnTransactions.isEnabled() == false) {
			return;
		}
		TransactionsDialog transactionsDialog = new TransactionsDialog(this.current, this.transaction);
		transactionsDialog.setLocationRelativeTo(this);
		transactionsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		transactionsDialog.setVisible(true);
	}
	
	//saving the settings once the window is closed
	protected void this_windowClosing(WindowEvent e) 
	{
		if (this.current == savings) {	
			settings.setCurrentAccount(SELECT_SAVINGS);
		}
		else {
			settings.setCurrentAccount(SELECT_CHEQUING);
		}
		
		Serializer s = new Serializer();
		try {
			s.serialize(settings, SETTINGS_FILE);
		} catch (Exception e1) {
			System.out.println(e1.toString());
		}
		
	}
}
