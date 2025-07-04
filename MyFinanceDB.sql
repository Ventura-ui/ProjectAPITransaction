CREATE DATABASE MyFinanceDB;
USE MyFinanceDB;

CREATE TABLE Transactions(
	id INTEGER auto_increment PRIMARY KEY,
	descricao VARCHAR(200) NOT NULL,
    valor DECIMAL NOT NULL,
    tipo ENUM('receita', 'despesa'),
    categoria VARCHAR(200) NOT NULL,
	data_criacao DATE NOT NULL
);

SELECT * FROM Transactions;