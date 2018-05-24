package org.roberto.javier.PlugDB;

public class QEP {
public static final String META =
	"TAB_DESC,9\n"+
	"0	QEP	512\n"+
	"1	LogDeleted	12\n"+
	"2	UpdateLog	512\n"+
	"3	User	186\n"+
	"4	Walter	266\n"+
	"5	Wallet	28\n"+
	"6	Address	270\n"+
	"7	Transaction	272\n"+
	"8	Session	270\n"+
	"COL_DESC,32\n"+
	"0	0	IdGlobal	4	1	0\n"+
	"1	0	QEPStr	460	0	4\n"+
	"2	0	SQLStr	24	9	464\n"+
	"3	0	ExplainStr	24	9	488\n"+
	"4	1	TabId	4	1	0\n"+
	"5	1	TuplePos	4	1	4\n"+
	"6	1	Flag	4	1	8\n"+
	"7	2	TabId	4	1	0\n"+
	"8	2	TuplePos	4	1	4\n"+
	"9	2	TupleSize	4	1	8\n"+
	"10	2	CompleteTup	500	0	12\n"+
	"11	3	idGlobal	4	1	0\n"+
	"12	3	login	52	0	4\n"+
	"13	3	passHash	130	0	56\n"+
	"14	4	idGlobal	4	1	0\n"+
	"15	4	token	258	0	4\n"+
	"16	4	expires	4	2	262\n"+
	"17	5	idGlobal	4	1	0\n"+
	"18	5	wallet	24	9	4\n"+
	"19	6	idGlobal	4	1	0\n"+
	"20	6	address	258	0	4\n"+
	"21	6	balance	4	1	262\n"+
	"22	6	idWallet	4	1	266\n"+
	"23	7	idGlobal	4	1	0\n"+
	"24	7	address	130	0	4\n"+
	"25	7	to	130	0	134\n"+
	"26	7	amount	4	1	264\n"+
	"27	7	d	4	2	268\n"+
	"28	8	idGlobal	4	1	0\n"+
	"29	8	token	258	0	4\n"+
	"30	8	idUser	4	1	262\n"+
	"31	8	expires	4	2	266\n"+
	"FK_DESC,2\n"+
	"6	22	5	17\n"+
	"8	30	3	11\n"+
	"SKT_DESC,2\n"+
	"0	6	Address	4\n"+
	"1	8	Session	4\n"+
	"SKT_COL_DESC,2\n"+
	"0	0	5	17	1\n"+
	"1	0	3	11	1\n"+
	"CI_DESC,7\n"+
	"0	0	0	0	1\n"+
	"1	3	3	11	1\n"+
	"2	4	4	14	1\n"+
	"3	5	5	17	1\n"+
	"4	6	6	19	1\n"+
	"5	7	7	23	1\n"+
	"6	8	8	28	1\n"+
	"";

public static String EP_WALLET_SELECT =
	"/*EP \u0000 0 1 1 5 # 1 0 0 1 r0 2 5 1 17 18 # \u0000 2 1 1 idGlobal 9 2 wallet # \u0000*/";

public static String EP_WALLET_UPDATE =
	"/*EP \u0002 2 1 1 -1 3 ?2 # a 0 0 1 2 5 r1 17 ?2 18 v9?1 # \u0000*/";

public static String EP_USER_SELECT =
	"/*EP \u0002 0 3 3 3 # 1 2 2 3 r0 3 3 1 11 12 13 # 4 1 1 2 12 0 ?1 r2 # 4 0 0 1 13 0 ?2 r3 # \u0000 1 1 1 idGlobal # \u0000*/";

public static String EP_WALTER_SELECT =
	"/*EP \u0001 0 2 2 4 # 1 1 1 2 r0 3 4 1 14 16 15 # 4 0 0 1 15 0 ?1 r3 # \u0000 2 1 1 idGlobal 2 2 expires # \u0000*/";

public static String EP_WALTER_DELETE =
	"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 2 4 1 14 15 # 4 2 2 3 15 0 ?1 r2 # 5 1 1 2 3 1 1 4 v14 5 r0 6 v10 # 9 0 0 1 4 r0 # \u0000*/";

public static String EP_SESSION_SELECT =
	"/*EP \u0001 0 2 2 8 # 1 1 1 2 r0 3 8 1 28 31 29 # 4 0 0 1 29 0 ?1 r3 # \u0000 2 1 1 idGlobal 2 2 expires # \u0000*/";

public static String EP_SESSION_DELETE =
	"/*EP \u0001 0 4 4 8 # 1 3 3 4 r0 2 8 1 28 29 # 4 2 2 3 29 0 ?1 r2 # 5 1 1 2 3 1 1 4 v18 5 r0 6 v10 # 9 0 0 1 8 r0 # \u0000*/";

public static String EP_SESSION_INSERT =
	"/*EP \u0004 2 3 3 -1 1 ?3 # 6 2 2 3 6 ?1 # 5 1 1 2 1 1 0 0 r1 # 5 0 0 1 4 8 1 28 r2 29 ?2 30 ?3 31 ?4 # \u0000*/";

public static String EP_TRANSACTION_SELECT =
	"/*EP \u0001 0 2 2 7 # 1 1 1 2 r0 5 7 1 23 24 25 26 27 # 4 0 0 1 24 0 ?1 r2 # \u0000 5 1 1 idGlobal 0 2 address 0 3 to 1 4 amount 2 5 d # \u0000*/";

public static String EP_TRANSACTION_SELECT_ALL =
	"/*EP \u0000 0 1 1 7 # 1 0 0 1 r0 1 7 1 23 # \u0000 1 1 1 idGlobal # \u0000*/";

public static String EP_TRANSACTION_INSERT =
	"/*EP \u0005 6 1 1 -1 5 ?1 # 5 0 0 1 5 7 1 23 r0 24 ?2 25 ?3 26 ?4 27 ?5 # \u0000*/";

public static String EP_TRANSACTION_DELETE =
	"/*EP \u0001 0 4 4 7 # 1 3 3 4 r0 2 7 1 23 24 # 4 2 2 3 24 0 ?1 r2 # 5 1 1 2 3 1 1 4 v17 5 r0 6 v10 # 9 0 0 1 7 r0 # \u0000*/";

public static String EP_ADDRESS_SELECT_ALL =
	"/*EP \u0000 0 1 1 6 # 1 0 0 1 r0 1 6 1 19 # \u0000 1 1 1 idGlobal # \u0000*/";

public static String EP_ADDRESS_INSERT =
	"/*EP \u0004 2 3 3 -1 3 ?4 # 6 2 2 3 4 ?1 # 5 1 1 2 1 0 0 0 r1 # 5 0 0 1 4 6 1 19 r2 20 ?2 21 ?3 22 ?4 # \u0000*/";

public static String EP_ADDRESS_UPDATE =
	"/*EP \u0002 2 2 2 -1 4 ?2 # 1 1 1 2 r1 2 6 1 20 22 # a 0 0 1 4 6 r1 19 ?2 20 r2 21 v1?1 22 r3 # \u0000*/";

public static String EP_ADDRESS_DELETE =
	"/*EP \u0001 0 4 4 6 # 1 3 3 4 r0 2 6 1 19 20 # 4 2 2 3 20 0 ?1 r2 # 5 1 1 2 3 1 1 4 v16 5 r0 6 v10 # 9 0 0 1 6 r0 # \u0000*/";

}

