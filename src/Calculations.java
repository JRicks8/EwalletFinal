
import java.io.*;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
public class Calculations implements Expenser{
	public User userAtHand;
    public String kindOfReport; // used to determine which type of report is printed by exportReport(). 
    public String reportType; // used to determine which which income or expense entries are used in filtered reports. 
    public String filePath; //the filepath where new files are created. 
    public double totalSavings; 
    public EWalletApp gui;

    // Initialize user 
    public Calculations(User user) {
    	this.userAtHand = user;
    }
    
    // Generates ReportListModel List for JList GUI use 
 	DefaultListModel<String> reportListModel = new DefaultListModel<>();;
    
	// As a user I'd like to add a monthly expense so I can track and report my expenses - 3pts
	public void addExpense (Expense Ex) {
		userAtHand.addExpenseList(Ex);
		updateMonthlySavings();		// Updates Monthly Saving when Expense is added
	}
	// As a user I'd like to add a monthly income so I can track and report my income all year - 3pts
	public void addMonthlyIncome (Wage W) {
		userAtHand.addIncomeList(W);
		updateMonthlySavings();	// Updates Monthly Savings when Monthly Income is added
	}
	//As  a user I would like to view a detailed report of all expenses, income, and summary information 
	//summary information include : total income, total income for each type, total income for each month, total expense, total expense for each type, 
	//total savings (total income- total expenses) to date, if the total savings are less than zero it should be reported as total new debt. 	
	public void PrintFullreport() {
		
		// Clears Current List information and prints updates
		reportListModel.clear();
		
		double totalExpenses = 0;
		double totalIncome = 0;
		ArrayList <String>expenseTypes = new ArrayList<String>();
		ArrayList <Double>expenseTypeTotals = new ArrayList<Double>();
		ArrayList <String>incomeTypes = new ArrayList<String>();
		ArrayList <Double>incomeTypeTotals = new ArrayList<Double>();
		
		System.out.println("Creating full report...");
		System.out.println();
		
		int i;
		//print expenses
		String exp = ("Expenses:");
		reportListModel.addElement(exp);
		for (i=0; i<userAtHand.getSpending().size(); i++) {
			System.out.println("Source: " + userAtHand.getSpending().get(i).source + " Amount: " + userAtHand.getSpending().get(i).amount + " Frequency (per year): " + userAtHand.getSpending().get(i).yearlyfrequency);
			
			String rep1 = ("Source: " + userAtHand.getSpending().get(i).source + " Amount: " + userAtHand.getSpending().get(i).amount + " Frequency (per year): " + userAtHand.getSpending().get(i).yearlyfrequency);
			reportListModel.addElement(rep1);
			
			//collect total expenses
			totalExpenses = totalExpenses + ((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
			
			//check if the source is already a recorded type
			if (expenseTypes.contains(userAtHand.getSpending().get(i).source.toUpperCase())) {
				//get the index of the type in the type list
				int index = expenseTypes.indexOf(userAtHand.getSpending().get(i).source.toUpperCase());
				
				//add the amount to the totals by types list in the same index spot
				double newExpenseTotal = expenseTypeTotals.get(index) + ((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
				expenseTypeTotals.set(index, newExpenseTotal);
				
			}
			//if the type isn't in the list add the source to the type list and the amount to the total by type list
			else {
				expenseTypes.add(userAtHand.getSpending().get(i).source.toUpperCase());
				expenseTypeTotals.add((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
			}
		}
		System.out.println();
		
		//print income report
		String inc = ("Income:");
		reportListModel.addElement(inc);
		for (i=0; i<userAtHand.getIncome().size(); i++) {
			System.out.println("Source: " + userAtHand.getIncome().get(i).source + " Amount: " + userAtHand.getIncome().get(i).amount + " Times Per Month: " + userAtHand.getIncome().get(i).Month);
			
			String rep2 = ("Source: " + userAtHand.getIncome().get(i).source + " Amount: " + userAtHand.getIncome().get(i).amount + " Times Per Month: " + userAtHand.getIncome().get(i).Month);
			reportListModel.addElement(rep2);
			
			//collect total income
			double thisMonthIncome = userAtHand.getIncome().get(i).amount * userAtHand.getIncome().get(i).Month;
			totalIncome += thisMonthIncome;
			
			//check if the source is already a recorded type
			if (incomeTypes.contains(userAtHand.getIncome().get(i).source.toUpperCase())) {
				//get the index of the type in the type list
				int index = incomeTypes.indexOf(userAtHand.getIncome().get(i).source.toUpperCase());
				
				//add the amount to the totals by types list in the same index spot
				double newIncomeTotal = incomeTypeTotals.get(index) + thisMonthIncome;
				incomeTypeTotals.set(index, newIncomeTotal);
				
			}
			//if the type isn't in the list add the source to the type list and the amount to the total by type list
			else {
				incomeTypes.add(userAtHand.getIncome().get(i).source.toUpperCase());
				incomeTypeTotals.add(thisMonthIncome);
			}
		}
		System.out.println();
		
		//print the summary information
		String rep3 = ("Summary:");
		reportListModel.addElement(rep3);
		String rep4 = ("Total Income: " + totalIncome);
		reportListModel.addElement(rep4);
		String lineBreak = "";
		reportListModel.addElement(lineBreak);
		
		for (i=0; i<incomeTypes.size(); i++) {
			String rep5 = ("Income Source: " + incomeTypes.get(i) + " Total Income: " + incomeTypeTotals.get(i));
			reportListModel.addElement(rep5);
		}
		reportListModel.addElement(lineBreak);
		
		String rep7 = ("Total Expenses: " + totalExpenses);
		reportListModel.addElement(rep7);
		reportListModel.addElement(lineBreak);
		
		for (i=0; i<expenseTypes.size(); i++) {
			String rep8 = ("Expense Type: " + expenseTypes.get(i) + " Total Expenses: " + expenseTypeTotals.get(i));
			reportListModel.addElement(rep8);
		}
		reportListModel.addElement(lineBreak);
		
		if (totalSavings < 0 ) {
			String rep9 = ("Total Debt: " + (totalIncome - totalExpenses));
			reportListModel.addElement(rep9);
		}
		else {
			String rep10 = ("Total Savings: " + (totalIncome - totalExpenses));
			reportListModel.addElement(rep10);
		}
	}
	//As  a user I would like to view a detailed report of all expenses, and summary information for expenses 
	public void PrintExpensereport() {
		reportListModel.clear();
		double totalExpenses = 0;
		
		String rep1 = ("Creating Expense report...");
		reportListModel.addElement(rep1);
		String lineBreak = "";
		reportListModel.addElement(lineBreak);
		
		int i;
		//print expenses
		String rep2 = ("Expenses:");
		reportListModel.addElement(rep2);
		for (i=0; i<userAtHand.getSpending().size(); i++) {
			String rep3 = ("Source: " + userAtHand.getSpending().get(i).source + " Amount: " + userAtHand.getSpending().get(i).amount + " Frequency (per year): " + userAtHand.getSpending().get(i).yearlyfrequency);
			reportListModel.addElement(rep3);
			//collect total expenses
			totalExpenses = totalExpenses + ((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
		}
		System.out.println();
		
		String rep20 = ("Total Expenses: " + totalExpenses);
		reportListModel.addElement(rep20);
	}
	
	//As  a user I would like to view a detailed report of all income, and summary information for income
	public void PrintIncomereport() {
		String incomeInfo; 		// Used to store Source, amount, Month

		// Clears Current List information and prints updates
		reportListModel.clear();
		
		// Gets information for Report Income
		for (Wage wage: userAtHand.getIncome()) {
			incomeInfo = "Source: " + wage.source + "    Amount: " + wage.amount + "    Month: " + wage.Month;
			reportListModel .addElement(incomeInfo);
		}
	}
	
	//As  a user I would like to view a detailed report of income of a certain type, and summary information for income
	public void PrintIncomereportbyTpe(){
		String incomeInfo; 		// Used to store Source, amount, Month
		String type; 			// User input from filter text field

		reportListModel.clear();
		
		type = EWalletApp.filterField.getText();
		
		// Gets filtered information for Report Income
		for (Wage wage: userAtHand.getIncome()) {
			if (wage.source.equals(type)) {
				incomeInfo = "Source: " + wage.source + "    Amount: " + wage.amount + "    Month: " + wage.Month;
				reportListModel .addElement(incomeInfo);
			}
		}		
	}
	
	//As  a user I would like to view a detailed report of expense of a certain type , and summary information for expenses
	public void PrintExpensebyType() {
		reportListModel.clear();
		ArrayList <String>expenseTypes = new ArrayList<String>();
		ArrayList <Double>expenseTypeTotals = new ArrayList<Double>();
		
		String rep1 = ("Creating Expense By Type report...");
		reportListModel.addElement(rep1);
		
		int i;
		//print expenses
		String rep2 = ("Expenses By Type:");
		reportListModel.addElement(rep2);
		for (i=0; i<userAtHand.getSpending().size(); i++) {		
			//check if the source is already a recorded type
			if (expenseTypes.contains(userAtHand.getSpending().get(i).source.toUpperCase())) {
				//get the index of the type in the type list
				int index = expenseTypes.indexOf(userAtHand.getSpending().get(i).source.toUpperCase());
				
				//add the amount to the totals by types list in the same index spot
				double newExpenseTotal = expenseTypeTotals.get(index) + ((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
				expenseTypeTotals.set(index, newExpenseTotal);
				
			}
			//if the type isn't in the list add the source to the type list and the amount to the total by type list
			else {
				expenseTypes.add(userAtHand.getSpending().get(i).source.toUpperCase());
				expenseTypeTotals.add((userAtHand.getSpending().get(i).amount) * (userAtHand.getSpending().get(i).yearlyfrequency));
			}
		}
		System.out.println();
		
		System.out.println("Summary: ");
		for (i=0; i<expenseTypes.size(); i++) {
			String rep4 = ("Expense Type: " + expenseTypes.get(i) + " Total Expenses: " + expenseTypeTotals.get(i));
			reportListModel.addElement(rep4);
		}
		System.out.println();
	}
	
	//used to display an error message if problems occur during read/write operations. 
	public void IOError(String error) {
		gui.PopupMessage("IO Error: " + error);
	}
	
	// As a user I would like to choose a report and export it as an external file (any type is fine preferences are csv or JSON)
	public void exportReport(String reportTitle) {
		//stores the path and name of the file to be created.
		String fullFilepath = filePath + reportTitle + ".csv";
		
		//create the file in the destination. 
		File file = new File(fullFilepath);
		try {
			if(file.createNewFile() == false) {
				System.out.println("File " + reportTitle + ".csv has been overwritten. "); 
			}
		}
		catch(IOException e) {
			IOError(e.toString());
			return;
		}
		
		//write the data to file.
		try {
			FileWriter writer = new FileWriter(file);
			
			if(kindOfReport.equals("Income")) {
				writer.write(kindOfReport + "\n");
				for(Wage income : userAtHand.getIncome()) {
					writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
				}
			}
			else if(kindOfReport.equals("IncomeByType")) {
				writer.write(kindOfReport + "\n");
				for(Wage income : userAtHand.getIncome()) {
					if(income.source.equals(reportType)) {
						writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
					}
				}
			}
			else if(kindOfReport.equals("Expense")) {
				writer.write(kindOfReport + "\n");
				for(Expense spending : userAtHand.getSpending()) {
					writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
				}
			}
			else if(kindOfReport.equals("ExpenseByType")) {
				writer.write(kindOfReport + "\n");
				for(Expense spending : userAtHand.getSpending()) {
					if(spending.source.equals(reportType)) {
						writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
					}
				}
			}
			
			writer.close();
		}
		catch(IOException e) {
			IOError(e.toString());
			return;
		}
	}
	//	As a user I would like to view my current balance in a different currency 
	//Bonus : try to use the same convert function to convert from foreign currency to USD 
	public double convertForeignCurrency(Currency C, double amount) {
		double returnAmount = 0;
		
		returnAmount = C.rate * amount;
		
		return returnAmount; 
	}
	
	//used to find a currency from the userAtHand's currency rates list.
	public Currency findCurrencyByName(String name) {
		Currency returnCurrency = null;
		
		for(Currency cur : userAtHand.getCurrencyRates()) {
			if(cur.name.equals(name)) {
				returnCurrency = cur;
				break;
			}
		}
		
		return returnCurrency;
	}
	
	public void addCurrency(String name, double rate) {
		userAtHand.addCurrencyRate(new Currency(name, rate));
	}
	
	//	As a user I would like to load multiple expenses from an external file all at once returning true if loaded successfully and false otherwise 
	public boolean loadIncomeFile(String filePath) {
		File loadedFile = null;
		
		
		try {
			loadedFile = new File(filePath);
		}
		catch(Exception E) {
			IOError("file not found. ");
		}
		
		try {
			ArrayList<Wage> incomes = new ArrayList<Wage>(); //data read from the file will be stored here and added to userAtHand if no problems occur. 
			
			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
			String line = "";
			String source;
			double amount;
			double month;
			int lineNumber = 1; //used to keep track of which line is being read. 
			
			br.readLine(); //first line contains data field names and should be skipped. 
			
			while ((line = br.readLine()) != null) {   //go through each line in the file
				lineNumber++;
				
				//if a line is completely empty, ignore it.
				if(line.equals("")) {
					continue;
				}
				
				String[] splitLine = line.split(",");   // use comma as separator to split the line into parts.
				
				if(splitLine.length < 3) {
					IOError("invalid data on line " + lineNumber + ", 3 data points are required for each line. ");
					br.close();
					return false;
				}
				
				//first data point is source. 
				source = splitLine[0];
				
				//second data point is amount, must be parse-able as double. 
				try {
					amount = Double.parseDouble(splitLine[1]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", second data point must be a number. ");
					br.close();
					return false;
				}
				
				//third data point is month. 
				try {
					month = Double.parseDouble(splitLine[2]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", third data point must be a number. ");
					br.close();
					return false;
				}
				
				//if all three data points work, add a new expense.
				incomes.add(new Wage(source, amount, month));
			}
			
			br.close();
			
			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
			for(Wage inc : incomes) {
				userAtHand.addIncomeList(inc);
			}
			// Updates Monthly savings
			updateMonthlySavings();
			
			return true;
		}
		catch(Exception E){
			IOError(E.toString());
			return false;
		}
		
		
	}
	//	As a user I would like to load multiple income from an external file all at once returning true if loaded successfully and false otherwise 
	public boolean loadExpenseFile(String filePath) {
		File loadedFile = null;
		
		
		try {
			loadedFile = new File(filePath);
		}
		catch(Exception E) {
			IOError("file not found. ");
		}
		
		try {
			ArrayList<Expense> expenses = new ArrayList<Expense>(); //data read from the file will be stored here and added to userAtHand if no problems occur. 
			
			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
			String line = "";
			String source;
			double amount;
			int yearlyFrequency;
			int lineNumber = 1; //used to keep track of which line is being read. 
			
			br.readLine(); //first line contains data field names and should be skipped. 
			
			while ((line = br.readLine()) != null) {   //go through each line in the file
				lineNumber++;
				
				//if a line is completely empty, ignore it.
				if(line.equals("")) {
					continue;
				}
				
				String[] splitLine = line.split(",");   // use comma as separator to split the line into parts.
				
				if(splitLine.length < 3) {
					IOError("invalid data on line " + lineNumber + ", 3 data points are required for each line. ");
					br.close();
					return false;
				}
				
				//first data point is source. 
				source = splitLine[0];
				
				//second data point is amount, must be parse-able as double. 
				try {
					amount = Double.parseDouble(splitLine[1]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", second data point must be a number. ");
					br.close();
					return false;
				}
				
				//third data point is yearly frequency, must be parse-able as int. 
				try {
					yearlyFrequency = Integer.parseInt(splitLine[2]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", third data point must be an integer. ");
					br.close();
					return false;
				}
				
				//if all three data points work, add a new expense.
				expenses.add(new Expense(source, amount, yearlyFrequency));
			}
			
			br.close();
			
			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
			for(Expense exp : expenses) {
				userAtHand.addExpenseList(exp);
			}
			
			// Update Monthly Savings 
			updateMonthlySavings();
			
			return true;
		}
		catch(Exception E){
			IOError(E.toString());
			return false;
		}
	}
	// As a user I would like to provide an item and a price and get an estimate in number of months needed to save up to buy this item. (based on current monthly saving. 
	public int whenCanIBuy(String itemname,double  price) {


		if (userAtHand.monthlysavings <= 0) {
			return Integer.MAX_VALUE; // Indicates that saving is not possible with current monthly savings
		}
		return (int) Math.ceil(price / userAtHand.monthlysavings);


	}
	
	// updates monthly savings based on latest added income and expenses. This is an internal function not called by the users.  Bonus: what is the most efficient way to call it (when?)? 
	public void updateMonthlySavings() {
		userAtHand.monthlysavings = userAtHand.getRecentIncome() - userAtHand.getTotalExpensesAmount();
		EWalletApp.savingsLabel.setText("Monthly Savings: " + Double.toString(userAtHand.monthlysavings));
	}

}
