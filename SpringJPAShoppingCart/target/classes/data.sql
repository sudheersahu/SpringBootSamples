insert into Accounts (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('employee1', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_EMPLOYEE');
 
insert into Accounts (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('manager1', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_MANAGER');

----------------
insert into products (CODE, NAME, PRICE, CREATE_DATE)
values ('S001', 'Core Java', 100, sysdate);
 
insert into products (CODE, NAME, PRICE, CREATE_DATE)
values ('S002', 'Spring for Beginners', 50, sysdate);
 
insert into products (CODE, NAME, PRICE, CREATE_DATE)
values ('S003', 'Swift for Beginners', 120, sysdate);
 
insert into products (CODE, NAME, PRICE, CREATE_DATE)
values ('S004', 'Oracle XML Parser', 120, sysdate);
 
insert into products (CODE, NAME, PRICE, CREATE_DATE)
values ('S005', 'CSharp Tutorial for Beginers', 110, sysdate);