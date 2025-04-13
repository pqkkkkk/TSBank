select * from Customer;
select * from BankAccount;
select * from BankTransaction;


alter table BankTransaction alter column counterPartyId nvarchar(15);
update BankAccount set balance = 0 where ID = '038375906412345';

delete from BankAccount;
delete from Customer;
delete from BankTransaction;