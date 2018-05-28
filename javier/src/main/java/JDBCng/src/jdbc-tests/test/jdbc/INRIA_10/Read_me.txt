====================================================================
Description :
====================================================================
Load Schema

Insert Data
Select * from info where IdEvent = "0x00000000000000000000000000000000000001BE"
commit

Insert 5 tuples to table INFO (IdEvent = "0x00000000000000000000000000000000000001BE")
Select * from info where IdEvent = "0x00000000000000000000000000000000000001BE"

Rollback_Transaction

Select * from info where IdEvent = "0x00000000000000000000000000000000000001BE"

Unload Schema


