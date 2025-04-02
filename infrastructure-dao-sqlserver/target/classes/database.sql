CREATE DATABASE TSBANKTEST;
USE TSBANKTEST;
GO

CREATE table Customer(
    ID INT IDENTITY(1,1) PRIMARY KEY,
    fullName NVARCHAR(50),
    email NVARCHAR(30),
    password VARCHAR(30),
    IdOnTicSys NVARCHAR(50)
);
CREATE TABLE BankAccount(
    ID nvarchar(15) PRIMARY KEY,
    balance FLOAT ,
	currency nvarchar(10),
	isLocked bit,
    customerId INT
)
CREATE TABLE BankTransaction(
    ID INT IDENTITY(1,1) PRIMARY KEY,
    type nvarchar(20),
    amount FLOAT,
    accountId nvarchar(15),
    counterPartyId INT,
    createdDate DATE,
    createdTime TIME,
	currency nvarchar(10),
    note nvarchar(100),
    status nvarchar(20)
)

alter table BankAccount add foreign key (customerId) references Customer(ID);
alter table BankTransaction add foreign key (accountId) references BankAccount(ID);

alter table BankTransaction add currency nvarchar(10);
alter table BankAccount add currency nvarchar(10);
alter table BankAccount add isLocked bit;

