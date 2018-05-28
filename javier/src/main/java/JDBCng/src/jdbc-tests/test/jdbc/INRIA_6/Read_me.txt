====================================================================
Description :
====================================================================
Connect
Load Schema

Insert FailurePoint: NOR_WRITE_ARRAY, table CONCEPT
Insert one tuple into USERDMSP
Insert one tuple into CONCEPT
SELECT * FROM USERDMSP
SELECT * FROM CONCEPT

Remove Failure Point
Insert one tuple into USERDMSP
Insert one tuple into CONCEPT
commit
SELECT * FROM USERDMSP
SELECT * FROM CONCEPT

Insert FailurePoint: NAND_WRITE_FAIL, table CONCEPT
Insert Data
SELECT * FROM USERDMSP
SELECT * FROM CONCEPT

Remove Failure Point
Insert Data
commit
SELECT * FROM USERDMSP
SELECT * FROM CONCEPT

Insert FailurePoint: NOR_ERASE_FAIL, table CONCEPT
Insert Data
SELECT * FROM CONCEPT

Remove Failure Point
Insert Data
SELECT * FROM USERDMSP
SELECT * FROM CONCEPT

Unload Schema

Disconnect
