package org.roberto.javier.PlugDB;

public class Schema 
{
	public static final String META =
	"TAB_DESC,9\n"+
	"0	QEP	512\n"+
	"1	LogDeleted	12\n"+
	"2	UpdateLog	512\n"+
	"3	User	186\n"+
	"4	Walter	266\n"+
	"5	Wallet	28\n"+
	"6	Address	270\n"+
	"7	Transaction	528\n"+
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
	"24	7	address	258	0	4\n"+
	"25	7	to	258	0	262\n"+
	"26	7	amount	4	1	520\n"+
	"27	7	d	4	2	524\n"+
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

}
