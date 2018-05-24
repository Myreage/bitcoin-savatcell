CREATE TABLE QEP
(
	IdGlobal numeric PRIMARY KEY,
	QEPStr char(458),
	SQLStr Blob,
	ExplainStr Blob
)
/

CREATE TABLE LogDeleted 
(
	TabId numeric,
	TuplePos numeric,
	Flag numeric 
) 
/

CREATE TABLE UpdateLog 
(
	TabId numeric,
	TuplePos numeric,
	TupleSize numeric,
	CompleteTup char(498)
)
/

CREATE TABLE User
(
	idGlobal numeric PRIMARY KEY,
	login char(50),
	passHash char(128)
)
/

CREATE TABLE Walter
(
	idGlobal numeric PRIMARY KEY,
	token char(256),
	expires date
)
/

CREATE TABLE Wallet
(
	idGlobal numeric PRIMARY KEY,
	wallet Blob
)
/

CREATE TABLE Address
(
	idGlobal numeric PRIMARY KEY,
	address char(256),
	balance numeric,
	idWallet numeric REFERENCES Wallet (idGlobal)
)
/

CREATE TABLE Transaction
(
	idGlobal numeric PRIMARY KEY,
	address char(128),
	to char(128),
	amount numeric,
	d date
)
/

CREATE TABLE Session
(
	idGlobal numeric PRIMARY KEY,
	token char(256),
	idUser numeric REFERENCES User (idGlobal),
	expires date
)
/

CREATE SKT Address
(
	Wallet (idGlobal)
)
/

CREATE SKT Session
(
	User (idGlobal)
)
/
