====================================================================
Description :
====================================================================
Connect
Load Schema
Insert Data
commit
Select all the tuples from Events
Select all the tuples from Info

Delete from info where IdEvent = "+"0x000000000000000000000000000000000000015a
commit

Select all the tuples from Events
Select all the tuples from Info

Delete from info where IdEvent = "+"0x000000000000000000000000000000000000015b
Rollback_Transaction

Select all the tuples from Events
Select all the tuples from Info

Unload Schema
Disconnect
