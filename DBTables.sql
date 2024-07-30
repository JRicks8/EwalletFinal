


CREATE TABLE EXPENSES (

	ExpenseID varchar(255),
	ExpenseAmount int,
	Yearly int, 
	CurrencyID varchar(255)
	);
	
	CREATE TABLE Income (
    IncomeID varchar(255),
    IncomeAmount int,
    Month int,
);

CREATE TABLE UserLogin (
	UserID varchar(255),
	UserEmail varchar(255),
	UserPW varchar(255)
);