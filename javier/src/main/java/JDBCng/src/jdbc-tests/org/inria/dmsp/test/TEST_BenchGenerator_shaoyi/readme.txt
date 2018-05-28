Test the BenchGenerator (org.inria.dmsp.tools.BenchGenerator.java), which generates:

1) for table EVENT: 
150 tuples corresponding to form "fiche de liaison" 
+ 15 tuples corresponding to form "GIR medical";
==> 165 tuples in total.

2) for table COMMENT: 
1 tuple (the first one) is "no comment";
1 tuple corresponding to each event linked to "fiche de liaison";
24 tuples corresponding to each event linked to "GIR medical";
==> 1 + 150*1 + 15*24 = 511 tuples in total.

3) for table INFO:
6 tuples corresponding to each event linked to "fiche de liaison";
73 tuples corresponding to each event linked to "GIR medical";
==> 150*6 + 15*73 = 1995 tuples in total.
