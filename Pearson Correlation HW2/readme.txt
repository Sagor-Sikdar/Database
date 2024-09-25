Task 1:

db2 -tf "path of createtable.sql" // in my case, the path is C:\Users\sagor\OneDrive\Desktop\Assignment2/createtable.sql
db2 -tf "path of load.sql" // in my case, the path is C:\Users\sagor\OneDrive\Desktop\Assignment2/load.sql


Task 2:

db2 -tf "path for pearsonCCsp" // in my case, the path is C:\Users\sagor\OneDrive\Desktop\Assignment2/pearsonCCsp.sql


Task 3:

javac pearsonCC
java ComputePearsonCoeff dbname login passwd stock1 stock2 // here dbname = "pearson", "stock1" = "AAL", "stock2" = "XOM"


Extra Credit:

db2 -td@ -tf "path for PearsonCCudf.sql" // in my case, the path is C:\Users\sagor\OneDrive\Desktop\Assignment2/PearsonCCudf.sql"

SELECT myavg(T1.Close, T2.Close) FROM pearson.CSE532.STOCK T1, pearson.CSE532.STOCK T2 WHERE T1.STOCKNAME != T2.STOCKNAME;

