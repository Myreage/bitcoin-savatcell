package org.inria.dmsp;

public class EP_QEP
{
	public static String EP_QEP_INSERT =
		// INSERT INTO QEP VALUES (?,?,?,?)
		"/*EP \u0004 6 1 1 -1 10 ?1 # 5 0 0 1 4 14 1 97 r0 98 ?2 99 ?3 100 ?4 # \u0000*/";
	
	public static String EP_QEP_SELECT_BY_ID =
		// SELECT * FROM QEP WHERE IdGlobal=?
		"/*EP \u0001 2 1 1 -1 10 ?1 # 1 0 0 1 r1 3 14 1 98 99 100 # \u0000 4 1 0 IdGlobal 0 2 QEPStr 9 3 SQLStr 9 4 ExplainStr # \u0000*/";
	
	public static String EP_QEP_SELECT =
		// SELECT * FROM QEP
		"/*EP \u0000 0 1 1 14 # 1 0 0 1 r0 4 14 1 97 98 99 100 # \u0000 4 1 1 IdGlobal 0 2 QEPStr 9 3 SQLStr 9 4 ExplainStr # \u0000*/";
	
	public final static String EP_QEP_DELETE =
		// DELETE FROM QEP WHERE IdGlobal=?
		"/*EP \u0001 2 2 2 -1 10 ?1 # 5 1 1 2 3 12 1 85 v114 86 r1 87 v10 # 9 0 0 1 14 r1 # \u0000*/";
}
