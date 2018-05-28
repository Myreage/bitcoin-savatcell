package org.roberto.javier.PlugDB;
public class QEP {
public static String EP_WALLET_INSERT =
	"/*EP \u0002 6 1 1 -1 3 ?1 # 5 0 0 1 2 5 1 17 r0 18 ?2 # \u0000*/";

public static String EP_WALLET_SELECT =
	"/*EP \u0000 0 1 1 5 # 1 0 0 1 r0 2 5 1 17 18 # \u0000 2 1 1 idGlobal 9 2 wallet # \u0000*/";

public static String EP_WALLET_UPDATE =
	"/*EP \u0002 2 1 1 -1 3 ?2 # a 0 0 1 2 5 r1 17 ?2 18 v9?1 # \u0000*/";

public static String EP_USER_SELECT =
	"/*EP \u0002 0 3 3 3 # 1 2 2 3 r0 3 3 1 11 12 13 # 4 1 1 2 12 0 ?1 r2 # 4 0 0 1 13 0 ?2 r3 # \u0000 1 1 1 idGlobal # \u0000*/";

public static String EP_USER_INSERT =
	"/*EP \u0003 6 1 1 -1 1 ?1 # 5 0 0 1 3 3 1 11 r0 12 ?2 13 ?3 # \u0000*/";

public static String EP_WALTER_SELECT =
	"/*EP \u0001 0 2 2 4 # 1 1 1 2 r0 3 4 1 14 16 15 # 4 0 0 1 15 0 ?1 r3 # \u0000 2 1 1 idGlobal 2 2 expires # \u0000*/";

public static String EP_WALTER_DELETE =
	"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 2 4 1 14 15 # 4 2 2 3 15 0 ?1 r2 # 5 1 1 2 3 1 1 4 v14 5 r0 6 v10 # 9 0 0 1 4 r0 # \u0000*/";

public static String EP_SESSION_SELECT =
	"/*EP \u0001 0 2 2 8 # 1 1 1 2 r0 3 8 1 28 31 29 # 4 0 0 1 29 0 ?1 r3 # \u0000 2 1 1 idGlobal 2 2 expires # \u0000*/";

public static String EP_SESSION_SELECT_ALL =
	"/*EP \u0000 0 1 1 8 # 1 0 0 1 r0 1 8 1 28 # \u0000 1 1 1 idGlobal # \u0000*/";

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

public static String EP_ADDRESS_SELECT =
	"/*EP \u0001 0 2 2 6 # 1 1 1 2 r0 4 6 1 19 20 21 22 # 4 0 0 1 22 0 ?1 r4 # \u0000 4 1 1 idGlobal 0 2 address 1 3 balance 1 4 idWallet # \u0000*/";

public static String EP_ADDRESS_SELECT_ALL =
	"/*EP \u0000 0 1 1 6 # 1 0 0 1 r0 1 6 1 19 # \u0000 1 1 1 idGlobal # \u0000*/";

public static String EP_ADDRESS_INSERT =
	"/*EP \u0004 2 3 3 -1 3 ?4 # 6 2 2 3 4 ?1 # 5 1 1 2 1 0 0 0 r1 # 5 0 0 1 4 6 1 19 r2 20 ?2 21 ?3 22 ?4 # \u0000*/";

public static String EP_ADDRESS_UPDATE =
	"/*EP \u0002 2 2 2 -1 4 ?2 # 1 1 1 2 r1 2 6 1 20 22 # a 0 0 1 4 6 r1 19 ?2 20 r2 21 v1?1 22 r3 # \u0000*/";

public static String EP_ADDRESS_DELETE =
	"/*EP \u0001 0 4 4 6 # 1 3 3 4 r0 2 6 1 19 20 # 4 2 2 3 20 0 ?1 r2 # 5 1 1 2 3 1 1 4 v16 5 r0 6 v10 # 9 0 0 1 6 r0 # \u0000*/";

}

