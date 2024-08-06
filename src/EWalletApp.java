import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class EWalletApp extends JFrame{

	// Initializing Calculations Object
	Calculations calc = new Calculations(new User("User","Password"));

	// Initialize JPanel cards as a field
	JPanel cards;
	
	// TODO TESTING THIS FILE CHOOSER
	JFileChooser importFile = new JFileChooser("C:\\",FileSystemView.getFileSystemView());
	
	// JTextFields
	JTextField usernameField = new JTextField();		// Used in loginWindow()
	JTextField passwordField = new JTextField();		// Used in loginWindow()
	JTextField sourceIncomeField = new JTextField();	// Used in mainPanel()
	JTextField amountIncomeField = new JTextField();	// Used in mainPanel()
	JTextField monthField = new JTextField();			// Used in mainPanel()
	JTextField sourceExpenseField = new JTextField();	// Used in mainPanel()
	JTextField amountExpenseField = new JTextField();	// Used in mainPanel()
	JTextField yearlyFreqField = new JTextField();		// Used in mainPanel()
	JTextField exportFileField = new JTextField("C:\\");		// Used in reportPanel()
	static JTextField filterField = new JTextField();	// Used in reportPanel()
	JTextField whenBuyItemField = new JTextField(); 	// User in whenCanIBuy()
	JTextField whenBuyAmountField = new JTextField(); 	// User in whenCanIBuy()

	
	// JButtons
	JButton loginButton = new JButton("Login");									// Used in loginWindow()
	JButton addExpenseButton = new JButton("Add Expense");						// Used in mainPanel()
	JButton addIncomeButton = new JButton("Add Income");						// Used in mainPanel()
	JButton reportWindowButton = new JButton("Reports");						// Used IN mainPanel()
	JButton convertForeignCurrencyButton = new JButton("Convert Foreign Currency");	// Used in mainPanel()
	JButton importIncomeFileButton	= new JButton("Import Income File");			// Used in mainPanel()
	JButton importExpenseFileButton = new JButton("Import Expense File");				// Used in mainPanel()
	JButton returnButton = new JButton("Return To Main Menu"); 					// Used in reportPanel()
	JButton filterButton = new JButton("Filter");			 					// Used in reportPanel()
	JButton printIncomeButton = new JButton("Income Report");					// Used in reportPanel()
	JButton printExpenseButton = new JButton("Expense Report");					// Used in reportPanel()
	JButton printFullReportButton = new JButton("Print Full Report");			// Used in reportPanel()
	JButton exportFileButton = new JButton("Export Report");			// Used in reportPanel()
	JButton whenCanIBuyCalculateButton = new JButton("Calculate");				// User in whenCanIBuy()
	JButton returnWhenButton = new JButton("Return To Main Menu"); 				// Used in whenCanIBuy()
	JButton whenCanIBuyWindowButton = new JButton("When Can I Buy");			// Used in whenCanIBuy()
	JButton returnReportButton = new JButton("Main Menu");
	
	// JLabels
	JLabel statusLabel = new JLabel();									// Used in loginWindow() and to show who is logged in
	JLabel reportTitle = new JLabel("Report", SwingConstants.CENTER);	// Used in reportWindow() and actionPerformed()
	static JLabel savingsLabel = new JLabel();							// Used in MainPanel()
	JLabel whenCanIBuyCalculatedLabel = new JLabel("Estimate months needed to save.");	// Used in whenCanIBuy()

	//JRadioButtons
	JRadioButton expenseRadio = new JRadioButton("Expense");
	JRadioButton incomeRadio = new JRadioButton("Income");
	JRadioButton expenseTypeRadio = new JRadioButton("Expense By Type");
	JRadioButton incomeTypeRadio = new JRadioButton("Income By Type");
	ButtonGroup exportButtonGroup = new ButtonGroup();
	
	
	// static block to initialize the database connection
	static {
		EWalletDB.createConnection();
	}
	
	//show a popup message with the given text
	public void PopupMessage(String _message) {
		JOptionPane.showMessageDialog(this, _message);
	}
		
	
	/**
	 * Constructor for MainGUI
	 */
	public EWalletApp() {
		////////////////////////////
		/// Frame Settings
		//////////////////////////
		setSize(300, 200);     // Sets the size of the Frame
		setTitle("EWallet");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		// Initialized actionPerformed()
		actionPerformed();
	
		//Initializes currency, for currency conversion testing
		calc.addCurrency("TestCur", 2);
		
		//set calc.gui to this object.
		calc.gui = this;
		

		// Adding Components to Button Group
		exportButtonGroup.add(expenseRadio);
		exportButtonGroup.add(incomeRadio);
		exportButtonGroup.add(expenseTypeRadio);
		exportButtonGroup.add(incomeTypeRadio);

		// Create card panel with Card Layout
		cards = new JPanel(new CardLayout());
		cards.add(loginWindow(), "LoginPanel");
		cards.add(mainPanel(), "MainPanel");
		cards.add(reportWindow(), "ReportPanel");
		cards.add(whenCanIBuyWindow(), "WhenWindow");


		add(cards);
	}

	public JPanel mainPanel() {
		// Create main panel with BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Create a sub panel with GridLayout for the Income and Expense sections
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5); // Padding

		// Create a sub panel with BoxLayout for the action buttons
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
		actionPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		// JLabels
		JLabel sourceIncomeLabel = new JLabel("Source:");
		JLabel amountIncomeLabel = new JLabel("Amount:");
		JLabel monthLabel = new JLabel("Month:");
		JLabel sourceExpenseLabel = new JLabel("Source:");
		JLabel amountExpenseLabel = new JLabel("Amount:");
		JLabel yearlyFreqLabel = new JLabel("Yearly Freq:");
		JLabel actionsLabel = new JLabel("Actions", SwingConstants.CENTER);
		actionsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel expensesLabel = new JLabel("Expenses");
		JLabel incomeLabel = new JLabel("Income");
		JLabel savingsLabel = new JLabel();
		savingsLabel.setText("Monthly Savings: " + Double.toString(calc.userAtHand.monthlysavings));

		//////////////
		// Adding labels and text fields to the mainPanel with GridBagLayout

		// Column 1
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(incomeLabel, gbc);

		gbc.gridy = 1;
		centerPanel.add(sourceIncomeLabel, gbc);

		gbc.gridy = 2;
		centerPanel.add(amountIncomeLabel, gbc);

		gbc.gridy = 3;
		centerPanel.add(monthLabel, gbc);

		// Column 2 
		gbc.ipadx = 60;
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(sourceIncomeField, gbc);

		gbc.gridy = 2;
		centerPanel.add(amountIncomeField, gbc);

		gbc.gridy = 3;
		centerPanel.add(monthField, gbc);

		gbc.gridy = 4;
		centerPanel.add(addIncomeButton, gbc);

		// Column 3
		gbc.ipadx = 0;
		gbc.gridx = 2;
		gbc.gridy = 0;
		centerPanel.add(expensesLabel, gbc);

		gbc.gridy = 1;
		centerPanel.add(sourceExpenseLabel, gbc);

		gbc.gridy = 2;
		centerPanel.add(amountExpenseLabel, gbc);

		gbc.gridy = 3;
		centerPanel.add(yearlyFreqLabel, gbc);

		// Column 4
		gbc.ipadx = 60;
		gbc.gridx = 3;
		gbc.gridy = 1;
		centerPanel.add(sourceExpenseField, gbc);

		gbc.gridy = 2;
		centerPanel.add(amountExpenseField, gbc);

		gbc.gridy = 3;
		centerPanel.add(yearlyFreqField, gbc);

		gbc.gridy = 4;
		centerPanel.add(addExpenseButton, gbc);
		//////////////
		
		// Add buttons to actions panel
		actionPanel.add(actionsLabel);
		actionPanel.add(Box.createVerticalStrut(5)); // Add space between label and buttons
		actionPanel.add(reportWindowButton);
		actionPanel.add(Box.createVerticalStrut(5)); // Add space between buttons
		actionPanel.add(convertForeignCurrencyButton);
		actionPanel.add(Box.createVerticalStrut(5)); // Add space between buttons
		actionPanel.add(importIncomeFileButton);
		actionPanel.add(Box.createVerticalStrut(5)); // Add space between buttons
		actionPanel.add(importExpenseFileButton);
		actionPanel.add(Box.createVerticalStrut(5)); // Add space between buttons
		actionPanel.add(whenCanIBuyWindowButton);
		actionPanel.add(Box.createVerticalStrut(20)); // Add space between buttons
		
		// Add the sub-panel to the center of the main panel
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(actionPanel, BorderLayout.EAST);

		return mainPanel;
	}

	/**
	 * Creates the panel for user login.
	 * @return JPanel representing the login window.
	 */
	public JPanel loginWindow() {	        
		// Panel for user login
		// Initializes panel for the login view
		JPanel loginPanel = new JPanel(new GridLayout(3,2));
		
		// Initializes sub panel
		JPanel southLoginPanel = new JPanel(new FlowLayout());


		// JLabels 
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel hintLabel = new JLabel("Hint: User, Password"); // TODO This can be change too

		// Add Components to Panel
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		loginPanel.add(hintLabel); 
		loginPanel.add(loginButton);

		// Add Components to southLoginPanel
		southLoginPanel.add(statusLabel);
		southLoginPanel.add(Box.createHorizontalStrut(30));
		southLoginPanel.add(savingsLabel);


		// Add Panel to Frame
		setLayout(new BorderLayout());
		add(loginPanel, BorderLayout.CENTER);
		add(southLoginPanel, BorderLayout.SOUTH);


		return loginPanel;
	}

	public JPanel reportWindow() {
		// Panel for report Window using border layout
		JPanel mainReportPanel = new JPanel(new BorderLayout());

		// Sub panel for reportPanel using box layout
		JPanel reportPanel = new JPanel();
		reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));

		// Sub Panel for westReportPanel, using boxLayout
		JPanel westReportPanel = new JPanel();
		westReportPanel.setLayout(new BoxLayout(westReportPanel, BoxLayout.Y_AXIS));
		
		// Sub Panel for eastReportPanel, using boxLayout
		JPanel eastReportPanel = new JPanel();
		eastReportPanel.setLayout(new BoxLayout(eastReportPanel, BoxLayout.Y_AXIS));

		// JList for Reports Information
		JList<String> reportList = new JList<>(calc.reportListModel);
		
		// JLabels
		JLabel filterLabel = new JLabel("Filter By Type");
		
		
		
		// Adding components to westReportPanel
		westReportPanel.add(Box.createVerticalStrut(35)); // Add space between components
		westReportPanel.add(filterLabel);
		westReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		westReportPanel.add(filterField);
		westReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		westReportPanel.add(filterButton);
		westReportPanel.add(Box.createVerticalStrut(35)); // Add space between components
		
		// Adding components to eastReportPanel
		eastReportPanel.add(printIncomeButton);
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		eastReportPanel.add(printExpenseButton);
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		eastReportPanel.add(printFullReportButton);
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		eastReportPanel.add(expenseRadio);
		eastReportPanel.add(incomeRadio);
		eastReportPanel.add(expenseTypeRadio);
		eastReportPanel.add(incomeTypeRadio);		
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		eastReportPanel.add(exportFileField);
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components
		eastReportPanel.add(exportFileButton);
		eastReportPanel.add(Box.createVerticalStrut(5)); // Add space between components

		
		
		// Adding components to mainReportPanel
		mainReportPanel.add(reportTitle, BorderLayout.NORTH);
		mainReportPanel.add(reportList, BorderLayout.CENTER);
		mainReportPanel.add(returnReportButton, BorderLayout.SOUTH);
		mainReportPanel.add(westReportPanel, BorderLayout.WEST);
		mainReportPanel.add(eastReportPanel, BorderLayout.EAST);

		return mainReportPanel;
	}
	
	private JPanel whenCanIBuyWindow() {		
		
		// Main Panel for whenCanIBuyWindow
		JPanel mainWhenPanel = new JPanel(new BorderLayout());

		// Sub Panel for mainCenterWhenPanel, using boxLayout
		JPanel mainCenterWhenPanel = new JPanel();
		mainCenterWhenPanel.setLayout(new BoxLayout(mainCenterWhenPanel, BoxLayout.Y_AXIS));

		// Sub Panel for mainEastWhenPanel, using boxLayout
		JPanel mainEastWhenPanel = new JPanel();
		mainEastWhenPanel.setLayout(new BoxLayout(mainEastWhenPanel, BoxLayout.Y_AXIS));


		// JLabels
		JLabel whenBuyItemLabel = new JLabel("Item:");
		JLabel whenBuyAmountLabel = new JLabel("Amount");
		
		// Adding components to mainCenterWhenPanel
		mainCenterWhenPanel.add(Box.createVerticalStrut(30)); // Add space between components
		mainCenterWhenPanel.add(whenBuyItemLabel);
		mainCenterWhenPanel.add(whenBuyItemField);
		mainCenterWhenPanel.add(whenBuyAmountLabel);
		mainCenterWhenPanel.add(whenBuyAmountField);
		mainCenterWhenPanel.add(Box.createVerticalStrut(30)); // Add space between components
		mainCenterWhenPanel.add(whenCanIBuyCalculateButton);
		mainCenterWhenPanel.add(Box.createVerticalStrut(15)); // Add space between components

		
		// Adding components to mainEastWhenPanel
		mainEastWhenPanel.add(Box.createVerticalStrut(50)); // Add space between components
		mainEastWhenPanel.add(whenCanIBuyCalculatedLabel);
		mainEastWhenPanel.add(Box.createHorizontalStrut(70)); // Add space between components

		
		// Adding components to mainWhenPanel
		mainWhenPanel.add(mainEastWhenPanel, BorderLayout.EAST);
		mainWhenPanel.add(mainCenterWhenPanel, BorderLayout.CENTER);
		mainWhenPanel.add(returnWhenButton, BorderLayout.SOUTH);
		
		return mainWhenPanel;
	}

	public void actionPerformed() {
		// Action for the LoginButton
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout)(cards.getLayout());
				String inputUsername = usernameField.getText();
				String inputPassword = passwordField.getText();
				boolean isValidLogin;

				//TODO: Login functionality
				//isValidLogin = tryLoginAsUser(inputUsername, inputPassword);
				isValidLogin = true;

				if (isValidLogin) {
					statusLabel.setText("Login Successful: "+ inputUsername);

					savingsLabel.setText("Monthly Savings: " + Double.toString(calc.userAtHand.monthlysavings));

					//Switches to Expense Panel
					cardLayout.show(cards, "MainPanel");
					setSize(700, 300);
				}
				else {
					statusLabel.setText("Invalid Username or Password");
				}		
			}
		});

		addExpenseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				// Handle add expense action here
				String source = sourceExpenseField.getText();
				double amount = Double.parseDouble(amountExpenseField.getText());
				int freq = Integer.parseInt(yearlyFreqField.getText());

				// Adding Expense and calling Calc.addexpense
				Expense newExpense = new Expense(source, amount, freq);
				calc.addExpense(newExpense);	
				
				// Get user ID
				int userId = 1;
				EWalletDB.addExpense(userId, source, amount, freq);
			}
		});

		addIncomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Handle add income action here
				String source = sourceIncomeField.getText();
				double amount = Double.parseDouble(amountIncomeField.getText());
				double month = Double.parseDouble(monthField.getText());

				// Adding Income and calling Calc.addMonthlyIncome
				Wage newWage = new Wage(source, amount, month);
				calc.addMonthlyIncome(newWage);
				
				// Get user ID
				int userId = 1;
				EWalletDB.addIncome(userId, source, amount, month);
			}
		});
		
		reportWindowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout)(cards.getLayout());
				
				// Switches to the Report Panel 
				cardLayout.show(cards, "ReportPanel");
				setSize(700, 350);
			}	
		});

		printIncomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reportTitle.setText("Income Report");

				// Calls PrintIncomereport() from Calculations
				calc.PrintIncomereport();
			}	
		});

		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (reportTitle.getText().equals("Income Report")) {

					reportTitle.setText("Income Report By Type " + filterField.getText());

					// Calls PrintIncomereportbyType() from Calculations
					calc.PrintIncomereportbyTpe();
				}
				else if ( reportTitle.getText().equals("Expense Report"))  {
					reportTitle.setText("Expense Report By Type " + filterField.getText());
					
					// TODO call prints PrintExpensebyType() from calculations
					calc.PrintExpensebyType();
				}
			}
		});

		returnReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout)(cards.getLayout());

				// Switches to the Main Panel 
				cardLayout.show(cards, "MainPanel");
				setSize(700, 300);
			}
		});
		
		returnWhenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout)(cards.getLayout());

				// Switches to the Main Panel 
				cardLayout.show(cards, "MainPanel");
				setSize(700, 300);
			}
		});

		//TODO print expense report Button Action
		printExpenseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Handle add Expense action here
				reportTitle.setText("Expense Report");
				
				calc.PrintExpensereport();

			}
		});

		//TODO print full report Button Action
		printFullReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Handle add Expense action here
				reportTitle.setText("Full Report");
				
				calc.PrintFullreport();
		

			}
		});
		
		convertForeignCurrencyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Conversion conv = new Conversion(calc);
				conv.setVisible(true);

			}
		});
		
		expenseTypeRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reportTitle.setText("Expenses by Type");
				
				calc.PrintExpensebyType();
			}
		});
		
		
		//TODO Import File Button Action
		importIncomeFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importFile.showSaveDialog(null);
				
				if(importFile.getSelectedFile() != null) {
					calc.loadIncomeFile(importFile.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		importExpenseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importFile.showSaveDialog(null);
				
				if(importFile.getSelectedFile() != null) {
					calc.loadExpenseFile(importFile.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		exportFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Handle Export File action here
				calc.filePath = exportFileField.getText();
				if(expenseRadio.isSelected()) {
					calc.kindOfReport = "Expense";
					calc.exportReport("expense");
				}
				else if(expenseTypeRadio.isSelected()) {
					calc.kindOfReport = "ExpenseByType";
					calc.reportType = filterField.getText();
					calc.exportReport("expense_by_type");
				}
				else if(incomeRadio.isSelected()) {
					calc.kindOfReport = "Income";
					calc.reportType = filterField.getText();
					calc.exportReport("income");
				}
				else if(incomeTypeRadio.isSelected()) {
					calc.kindOfReport = "IncomeByType";
					calc.reportType = filterField.getText();
					calc.exportReport("income_by_type");
				}
			}
		});
		
		// Handles when Can I Buy button action here
		whenCanIBuyWindowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout)(cards.getLayout());

				// Switches to the Main Panel 
				cardLayout.show(cards, "WhenWindow");		
			}
		});
		
		// Call whenCanIBuy method froms calculations and returns an INT
		whenCanIBuyCalculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int months; 		// Stores retuns int value from calc.whenCanIBuy(itemName, amount);
				String itemName = whenBuyItemField.getText();
				double amount = Double.parseDouble(whenBuyAmountField.getText());
				
				months = calc.whenCanIBuy(itemName, amount);
				
				whenCanIBuyCalculatedLabel.setText("You will be able to purchase " + itemName + " in " + months + "month(s).");
			}
		});
		
	}

	public static void main(String[] args) {
		EWalletApp GUI = new EWalletApp();

		GUI.setVisible(true); 
	}
}
