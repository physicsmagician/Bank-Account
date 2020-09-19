import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.Color;


public class TransactionsDialog extends JDialog implements ActionListener{

	private final String TRANSACTION_FILE = "transaction.txt";
	private final JPanel contentPanel = new JPanel();
	
	private JCheckBox chckbxStartDate;
	private JCheckBox chckbxEndDate;
	private JComboBox cboStartMonth;
	private JComboBox cboStartDay;
	private JComboBox cboStartYear;
	private JComboBox cboEndMonth;
	private JComboBox cboEndDay;
	private JComboBox cboEndYear;
	private JTextArea txtArTransactions;
	
	int monthStart= 0;
	int dayStart = 0;
	int yearStart = 0;
	int monthEnd = 0;
	int dayEnd = 0;
	int yearEnd = 0;
	
	LocalDate d1;
	LocalDate d2;
	LocalDateTime dt1;
	LocalDateTime dt2;
	
	private BankAccount current;
	private Transactions transactions = new Transactions();

	/**
	 * Create the dialog.
	 */
	public TransactionsDialog(BankAccount current, Transactions transaction) {
		this.current = current;
		this.transactions = transaction;
		
		setTitle("Transactions");
		setBounds(100, 100, 450, 445);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 128, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};		
		Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
							11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
							21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
		Integer[] years = {2007, 2008, 2009, 2010, 
				2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020};
		
		 cboStartMonth = new JComboBox(months);
		cboStartMonth.setBounds(131, 19, 70, 20);
		contentPanel.add(cboStartMonth);
		//cboStartMonth.addActionListener(this);
		
		 cboStartDay = new JComboBox(days);
		cboStartDay.setBounds(202, 19, 70, 20);
		contentPanel.add(cboStartDay);
		//cboStartDay.addActionListener(this);
		
		 cboStartYear = new JComboBox(years);
		cboStartYear.setBounds(277, 19, 100, 20);
		cboStartYear.addActionListener(this);
		contentPanel.add(cboStartYear);
		cboStartYear.addActionListener(this);
		
		 cboEndMonth = new JComboBox(months);
		cboEndMonth.setBounds(131, 56, 70, 20);
		contentPanel.add(cboEndMonth);
		//cboEndMonth.addActionListener(this);
		
		 cboEndDay = new JComboBox(days);
		cboEndDay.setBounds(202, 56, 70, 20);
		contentPanel.add(cboEndDay);
		//cboEndDay.addActionListener(this);
		
		 cboEndYear = new JComboBox(years);
		cboEndYear.setBounds(277, 56, 100, 20);
		contentPanel.add(cboEndYear);
		cboEndYear.addActionListener(this);
		
		chckbxStartDate = new JCheckBox("Start Date:");
		chckbxStartDate.setBackground(new Color(128, 128, 0));
		chckbxStartDate.setBounds(28, 18, 99, 23);
		contentPanel.add(chckbxStartDate);
		chckbxStartDate.addActionListener(this);
		
		chckbxEndDate = new JCheckBox("End Date:");
		chckbxEndDate.setBackground(new Color(128, 128, 0));
		chckbxEndDate.setBounds(28, 55, 97, 23);
		contentPanel.add(chckbxEndDate);
		chckbxEndDate.addActionListener(this);
		
		txtArTransactions = new JTextArea(" Date     Time       Amount      Description \n --------------------------------------------");
		txtArTransactions.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtArTransactions.setBackground(new Color(255, 215, 0));
		txtArTransactions.setEditable(false);
		txtArTransactions.setBounds(10, 92, 414, 303);
		contentPanel.add(txtArTransactions);
		
		setControls();
	}
	
	public void setControls() 
	{
		System.out.println("setControls");
		
		boolean startDateSelected = chckbxStartDate.isSelected();
		boolean endDateSelected = chckbxEndDate.isSelected();
		
		//ENABLING AND DISABLING CONTROLS
		if (startDateSelected) {
			cboStartMonth.setEnabled(true);
			cboStartDay.setEnabled(true);
			cboStartYear.setEnabled(true);
			
		} else {
			cboStartMonth.setEnabled(false);
			cboStartDay.setEnabled(false);
			cboStartYear.setEnabled(false);
		}
		
		if (endDateSelected) {
			cboEndMonth.setEnabled(true);
			cboEndDay.setEnabled(true);
			cboEndYear.setEnabled(true);
			
		} else {
			cboEndMonth.setEnabled(false);
			cboEndDay.setEnabled(false);
			cboEndYear.setEnabled(false);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		
		System.out.println("action performed");
		
		txtArTransactions.setText(" Date     Time       Amount      Description \n --------------------------------------------");
		dt1 = null;
		dt2 = null;
		
			if (cboStartMonth.getSelectedItem() != null) {
	            this.monthStart= cboStartMonth.getSelectedIndex() + 1;
	        }
			if (cboStartDay.getSelectedItem() != null) {
				this.dayStart = Integer.parseInt(cboStartDay.getSelectedItem().toString());
	        }
			if (cboStartDay.getSelectedItem() != null) {
				this.yearStart = Integer.parseInt(cboStartYear.getSelectedItem().toString());
	        }
		
			if (cboEndMonth.getSelectedItem() != null) {
				this.monthEnd = cboEndMonth.getSelectedIndex() + 1;
	        }
			if (cboEndDay.getSelectedItem() != null) {
				this.dayEnd = Integer.parseInt(cboEndDay.getSelectedItem().toString());
	        }
			if (cboEndDay.getSelectedItem() != null) {
				this.yearEnd = Integer.parseInt(cboEndYear.getSelectedItem().toString());
	        }
		
		if (chckbxStartDate.isSelected()) {
			 if (this.yearStart == 0 | this.monthStart == 0 | this.dayStart == 0) {
				 dt1 = null;
			 } 
			 else {
				 d1 = LocalDate.of(this.yearStart, this.monthStart, this.dayStart);
				 dt1 = LocalDateTime.of(d1, LocalTime.now());
			 }
		}
		if ( chckbxEndDate.isSelected()) {
			 if (yearEnd == 0 | monthEnd == 0 | dayEnd == 0) {
				 dt2 = null;
			 } 
			 else {
				 d2 = LocalDate.of(yearEnd, monthEnd, dayEnd);
				 dt2 = LocalDateTime.of(d2, LocalTime.now());
			 }
		}
			
		 try {
			
			 if (chckbxStartDate.isSelected() || chckbxEndDate.isSelected()) {
				 ArrayList<Transaction> output = current.getTransactions(dt1, dt2);
				 for (Transaction transaction : output) {
					txtArTransactions.append("\n" + transaction.getTransactionTime() + "    " + transaction.getAmount() + "  " + transaction.getDescription());
				
				 }
			 } else {
				 //do nothin
			 }
				
         } catch (DateTimeException e1){

        	 System.out.println("Invalid date");
         }
		
		setControls();
		
	}
	
}
