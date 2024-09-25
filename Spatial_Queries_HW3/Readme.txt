Commands to run for connection to database, enabling spatial support, creating table, indexing and querying:

db2 connect to sample
db2se enable_db sample
db2 -tf "folder_path\create_trifacility.sql"
db2 -tf "folder_path\createfacilityidx.sql"
db2 -tf "folder_path\create_zcta.sql"
db2 -tf "folder_path\createzipidx.sql"
db2 -tf "folder_path\query1.sql"
db2 -tf "folder_path\query2.sql"


Experimenting with Spatial Indexing for Query 1 and 2: This has been tested for various indexing and the execution time has been recorded.
Every following line shows the setting and its corresponding execution time. In this case, every setting has been run for 3 times and the average has been taken.

For Query1:
Setting			Execution Time(ms)
(0.75, 2, 5)	449
(0.5, 2, 5)		422
(1, 2, 5)		464
(0.75, 1, 5)	445
(0.75, 3, 5)	450
(0.75, 0.5, 5)	417
(0.5, 0.5, 5)	479
(0.75, 2, 4)	466
(0.75, 2, 3)	461
(0.75, 2, 6)	423

For (0.75, 0.5, 5), the minimum execution time has been recorded.

	

For Query2:
Setting			Execution Time(ms)
(.25, 1.0, 2)	386
(.5, 1.0, 2)	447
(.1, 1.0, 2)	600
(.25, 2.0, 2)	399
(.25, 1.5, 2)	676
(.25, 2.5, 2)	721
(.25, 1.0, 3)	255
(.25, 1.0, 4)	172
(.25, 2.0, 1)	249
(.25, 2.0, 4)	983
For (.25, 1.0, 4), the minimum execution time has been recorded.