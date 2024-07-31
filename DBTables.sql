
CREATE TABLE Expenses (
    user_id INT,
    source VARCHAR(255),
    amount DOUBLE,
    yearlyfrequency INT
);

CREATE TABLE Income (
    user_id INT,
    source VARCHAR(255),
    amount DOUBLE,
    month VARCHAR(255) 
);

CREATE TABLE Users (
    username VARCHAR(255),
    password VARCHAR(255) 
);