====================================================================
Description : Performance test
====================================================================
generate data (10 ev, 10 co, 100 in)
grant access to user/role
connect as user/role
---- transaction ----
insert 128 info and 20 comments
rollback --> measure perf of rollback operation


