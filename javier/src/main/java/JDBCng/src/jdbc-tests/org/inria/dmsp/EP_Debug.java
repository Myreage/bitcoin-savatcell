package org.inria.dmsp;

public interface EP_Debug {

  String EP_EVENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    // Select * from event
    "/*EP \u0000 0 1 1 3 # " +                          // SCAN, Table EVENT(3) -->R0
    "1 0 0 1 r0 10 3 1 28 29 30 31 32 33 34 35 95 96# " + // TABLE_LOOKUP, Pos R0, 8 cols, Table EVENT(3), a TABLE(1) --> R1 - R10
    "\u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # \u0000*/";
  // 8 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_INFO_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    // Select * from info
    "/*EP \u0000 0 1 1 4 # " +          // SCAN, Table INFO(4) -->R0
    "1 0 0 1 r0 12 4 1 36 37 38 39 40 41 42 43 44 45 46 92 # " +
    // TABLE_LOOKUP, Pos R0, 12 cols, Table INFO(4), a TABLE(1) --> R1 - R12
    "\u0000 12 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdComment 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 Filtre 1 12 IdConcept# \u0000*/";
  // 12 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_COMMENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    // Select * from comment
    "/*EP \u0000 0 1 1 5 # " +                  // SCAN, Table COMMENT(5) -->R0
    "1 0 0 1 r0 5 5 1 47 48 49 50 51 # " +  // TABLE_LOOKUP, Pos R0, 5 cols, Table COMMENT(5), a TABLE(1) --> R1 - R5
    "\u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # \u0000*/";
  // 5 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_HABILITATION_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    // Select * from habilitation
    "/*EP \u0000 0 1 1 8 # " +                      // SCAN, Table HABIL(7) -->R0
    "1 0 0 1 r0 6 8 1 57 58 59 60 62 63 # " +   // TABLE_LOOKUP, Pos R0, 6 cols, Table HABIL(7), a TABLE(1) --> R1 - R6
    "\u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";
  // 6 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_PATIENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST ???
    // Select * from matricepatient
    "/*EP \u0000 0 1 1 10 # " +                 // SCAN, Table MPAT(9) -->R0
    "1 0 0 1 r0 9 10 1 71 72 73 74 75 76 77 78 79 # " +
    // TABLE_LOOKUP, Pos R0, 9 cols, Table MPAT(9), a TABLE(1) --> R1 - R5
    "\u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";
  // 9 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_SYSTEM_SKTINFO_SELECT_STAR = 
	  // Select * from SKTInfo  
	  "/*EP \u0000 0 1 1 4 # " +     // SCAN, Table INFO(4) -->R0
	  "1 0 0 1 r0 5 1 0 0 1 2 3 4 # " +  // TABLE_LOOKUP, Pos R0, 5 cols, Table SKT INFO(4), a SKT(0) --> R1 - R5
	  "\u0000 6 1 0 PosInfo 1 1 PosForm 1 2 PosUser 1 3 PosEpisode 1 4 PosEvent 1 5 PosComment # \u0000*/";  // 5 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i
	 
  String EP_SYSTEM_SKTEVENT_SELECT_STAR = 
	  // Select * from SKTInfo  
	  "/*EP \u0000 0 1 1 3 # " +     // SCAN, Table INFO(4) -->R0
	  "1 0 0 1 r0 3 0 0 0 1 2 # " +  // TABLE_LOOKUP, Pos R0, 5 cols, Table SKT INFO(4), a SKT(0) --> R1 - R5
	  "\u0000 4 1 0 PosEvent 1 1 PosForm 1 2 PosUser 1 3 PosEpisode # \u0000*/";  // 5 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

  String EP_SYSTEM_LogDeleted =  
	  /* SQL : 	SELECT TabId, TuplePos, Flag FROM LogDeleted  */
	  "/*EP \u0000 " + 
	  "0 1 1 12 # " + /* SCAN, Table LogDeleted (12) -->R0 */
	  "1 0 0 1 r0 3 12 1 85 86 87 # " + /* TABLE_LOOKUP pos:R0, 3 cols, table LogDeleted(12), is_table: 1  -->R1 - R3 */
	  "\u0000 3 1 1 TabId 1 2 TuplePos 1 3 Flag # " + /* META_RESULT, 3 cols, type(0-char 1-num 2-date) [out_result name] */
	  "\u0000*/";

  String EP_SYSTEM_UpdateLog =  
	  /* SQL : 	SELECT TabId, TuplePos, TupleSize FROM UpdateLog */
	  "/*EP \u0000 " + 
	  "0 1 1 13 # " + /* SCAN, Table UpdateLog (13) -->R0 */
	  "1 0 0 1 r0 3 13 1 88 89 90 # " + /* TABLE_LOOKUP pos:R0, 3 cols, table UpdateLog(13), is_table: 1  -->R1 - R3 */
	  "\u0000 3 1 1 TabId 1 2 TuplePos 1 3 TupleSize # " + /* META_RESULT, 3 cols, type(0-char 1-num 2-date) [out_result name] */
	  "\u0000*/";
  
  String EP_SYSTEM_SKTEVENT_SELECT_FORMPOS_BY_EVENTPOS =
	// Select PosUser from SKTEvent where PosUser = ?
	"/*EP \u0001 " +
	"0 2 2 3 # " + /* SCAN, Table Event (3) -->R0 */
	"1 1 1 2 r0 1 0 0 1 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table SKT EVENT(0), is_table: 0  -->R1 */
	"4 0 0 1 10 0 ?1 r1 # " + /* SELECT, att: IdGlobal(10), comparator: 0, parameter: ?1 (R3), from pos: R2*/
	"\u0000 1 1 1 PosForm # \u0000*/";  // 1 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i
}
