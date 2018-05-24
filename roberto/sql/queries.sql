--public static String EP_WALLET_SELECT =
SELECT idGlobal, wallet FROM Wallet
/ 
--public static String EP_WALLET_UPDATE =
UPDATE Wallet SET wallet=? WHERE idGlobal=?
/ 
--public static String EP_USER_SELECT = 
SELECT idGlobal FROM User WHERE login=? AND passHash=?
/
--public static String EP_WALTER_SELECT = 
SELECT idGlobal, expires FROM Walter WHERE token=?
/
--public static String EP_WALTER_DELETE = 
DELETE FROM Walter WHERE token=?
/
--public static String EP_SESSION_SELECT = 
SELECT idGlobal, expires FROM Session WHERE token=?
/
--public static String EP_SESSION_DELETE = 
DELETE FROM Session WHERE token=?
/
--public static String EP_SESSION_INSERT = 
INSERT INTO Session VALUES (?,?,?,?)
/
--public static String EP_TRANSACTION_SELECT = 
SELECT * FROM Transaction WHERE address=?
/
--public static String EP_TRANSACTION_SELECT_ALL = 
SELECT idGlobal FROM Transaction
/
--public static String EP_TRANSACTION_INSERT = 
INSERT INTO Transaction VALUES (?,?,?,?,?)
/
--public static String EP_TRANSACTION_DELETE = 
DELETE FROM Transaction WHERE address=?
/
--public static String EP_ADDRESS_SELECT_ALL = 
SELECT idGlobal FROM Address
/
--public static String EP_ADDRESS_INSERT = 
INSERT INTO Address VALUES (?,?,?,?)
/
--public static String EP_ADDRESS_UPDATE = 
UPDATE Address SET balance=? WHERE idGlobal=?
/
--public static String EP_ADDRESS_DELETE = 
DELETE FROM Address WHERE address=?
/
