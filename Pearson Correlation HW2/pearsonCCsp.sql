CREATE OR REPLACE PROCEDURE ComputePearsonCoeff(IN stock1 CHAR(4), IN stock2 CHAR(4), OUT cc DECIMAL(4,3))
LANGUAGE SQL
BEGIN
DECLARE SQLSTATE CHAR(5) DEFAULT '00000';--
DECLARE n INTEGER;--
DECLARE pos_x DOUBLE;--
DECLARE pos_y DOUBLE;--
DECLARE total_x DOUBLE;--
DECLARE total_y DOUBLE;--
DECLARE total_xy DOUBLE;--
DECLARE total_x2 DOUBLE;--
DECLARE total_y2 DOUBLE;--
DECLARE r DOUBLE;--
DECLARE x CURSOR FOR SELECT Close FROM pearson.CSE532.STOCK WHERE STOCKNAME = stock1;--
DECLARE y CURSOR FOR SELECT Close FROM pearson.CSE532.STOCK WHERE STOCKNAME = stock2;--

SET n = 0;--
SET total_x = 0.0;--
SET total_y = 0.0;--
SET total_xy = 0.0;--
SET total_x2 = 0.0;--
SET total_y2 = 0.0;--

OPEN x;--
FETCH FROM x INTO pos_x;--
OPEN y;--
FETCH FROM y INTO pos_y;--

WHILE(SQLSTATE = '00000') DO
SET total_x = total_x + pos_x;--
SET total_y = total_y + pos_y;--
SET total_xy = total_xy + (pos_x * pos_y);--
SET total_x2 = total_x2 + (pos_x * pos_x);--
SET total_y2 = total_y2 + (pos_y * pos_y);--
SET n = n + 1;--

FETCH FROM x INTO pos_x;--
FETCH FROM y INTO pos_y;--
END WHILE;--

CLOSE x;--
CLOSE y;--

SET r = ((n * total_xy) - (total_x * total_y))/(sqrt(((n * total_x2) - (total_x * total_x)) * ((n * total_y2) - (total_y * total_y))));--
SET cc = CAST(r AS DECIMAL(4,3));--
END;