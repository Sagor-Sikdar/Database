CREATE TABLE pearson.CSE532.STOCK (
    StockName VARCHAR(4),
    Date DATE,
    Open DOUBLE,
    High DOUBLE,
    Low DOUBLE,
    Close DOUBLE,
    AdjClose DOUBLE,
    Volume INT
);

CREATE INDEX date_index ON pearson.CSE532.STOCK (Date);