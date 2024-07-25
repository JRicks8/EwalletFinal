import java.util.ArrayList;  

public class User {
	private ArrayList <Currency>currencyRates = new ArrayList<Currency>();
	private ArrayList <Wage>Income = new ArrayList<Wage>();  // user income sources that user can record or view or search by type or month 
	private ArrayList <Expense>Spending = new ArrayList<Expense>(); //user's expenses 
	String username;
	String pwd;
	
	//current total income - total 
	double balance;
	
	// possible monthly savings, calculated using monthly income (most recent) assuming the data we have is for one year, and monthly and biweekly expenses, here you can assume yearly expenses that are recorded have already been paid. 
	double monthlysavings;	
	
	//should add constructor(s)
	User(String username,String password){
		this.username = username;
		this.pwd = password;
	}
	
	public void addExpenseList(Expense Ex) {
		Spending.add(Ex);
	}
	
	public void addIncomeList(Wage W) {
		Income.add(W);
	}
	
	public void addCurrencyRate(Currency currency) {
		currencyRates.add(currency);
	}
	
	public ArrayList<Currency> getCurrencyRates() {
		return currencyRates;
	}
	
	public Currency getCurrencyByName(String _name) {
		Currency returnCur = null;
		for(Currency cur : currencyRates) {
			if(cur.name.equals(_name)) {
				returnCur = cur;
			}
		}
		return returnCur;
	}
	
	public ArrayList<Wage> getIncome() {
		return Income;
	}
	
	public ArrayList<Expense> getSpending() {
		return Spending;
	}
	
	public double getTotalExpensesAmount() {
		double totalExpense = 0;
		
		for (Expense expense: Spending) {
			totalExpense += expense.amount * expense.yearlyfrequency;
		}
		
		return totalExpense;
	}
	
	public double getRecentIncome() {
		Wage recentWage;
		recentWage =  Income.get(Income.size() - 1);
		
		return recentWage.amount;
	}
}
