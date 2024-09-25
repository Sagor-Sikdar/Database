alter table CSE532.STOCK alter column StockName set default 'AAL';
load from "C:\Users\matth\OneDrive\Desktop\Assignment2\AAL.csv" of del insert into CSE532.STOCK (Date, Open, High, Low, Close, AdjClose, Volume);
alter table CSE532.STOCK alter column StockName set default 'XOM';
load from "C:\Users\matth\OneDrive\Desktop\Assignment2\XOM.csv" of del insert into CSE532.STOCK (Date, Open, High, Low, Close, AdjClose, Volume);