package org.inria.dmsp.schema;

public class EP_TEST {


	/*
	 * PLANS FOR TEST - NOT USED IN APPLI/SYNCHRO 
	 * 
	 * SELECT_STAR:
	 * 	EP_EPISODE_SELECT_STAR 
	 *	EP_FORMULAIRE_SELECT_STAR
	 *	EP_USER_SELECT_STAR
	 *	EP_EVENT_SELECT_STAR
	 *	EP_INFO_SELECT_STAR
	 *	EP_COMMENT_SELECT_STAR
	 *	EP_ROLE_SELECT_STAR
	 *	EP_HABILITATION_SELECT_STAR
	 *	EP_PATIENT_SELECT_STAR 
	 * INSERT PLANS:
	 *  EP_*_INSERT (FORMULAIRE, USER, ROLE, EPISODE, EVENT, INFO, COMMENT)
	 *  EP_PATIENT_INSERT_*_* 
	 * DELETE PLANS:
	 *  EP_*_DELETE_ALL (FORMULAIRE, USER, ROLE, EPISODE, EVENT, INFO, COMMENT, HABILITATION)
	 * MISCELLANEOUS:
	 *  EP_SELECT_6ATTS_USER_ALL
	 *  EP_SELECT_6ATTS_USER_LESSTHAN
	 */

	public static String EP_EVENT_DELETE_ALL = 
		/* SQL : 	Delete from Event  */
		"/*EP \u0000 " + 
		"0 3 3 3 # " + /* SCAN, Table Event (3) -->R0 */
		"1 2 2 3 r0 1 3 1 28 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Event(3), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v13 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 3 r0 # " + /* TABLE DELETE, table Event(3), tuple pos: R0 */
		"\u0000*/";


	public static String EP_INFO_DELETE_ALL = 
		/* SQL : 	Delete from Info */
		"/*EP \u0000 " + 
		"0 3 3 4 # " + /* SCAN, Table Info (4) -->R0 */
		"1 2 2 3 r0 1 4 1 36 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Info(4), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v14 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 4 r0 # " + /* TABLE DELETE, table Info(4), tuple pos: R0 */
		"\u0000*/";


	public static String EP_EPISODE_DELETE_ALL = 
		/* SQL : 	Delete from Episode */
		"/*EP \u0000 " + 
		"0 3 3 0 # " + /* SCAN, Table Episode (0) -->R0 */
		"1 2 2 3 r0 1 0 1 0 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Episode(0), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v10 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 0 r0 # " + /* TABLE DELETE, table Episode(0), tuple pos: R0 */
		"\u0000*/";


	public static String EP_FORMULAIRE_DELETE_ALL = 
		/* SQL : 	Delete from Formulaire  */
		"/*EP \u0000 " + 
		"0 3 3 1 # " + /* SCAN, Table Formulaire (1) -->R0 */
		"1 2 2 3 r0 1 1 1 5 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Formulaire(1), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v11 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 1 r0 # " + /* TABLE DELETE, table Formulaire(1), tuple pos: R0 */
		"\u0000*/";


	public static String EP_USER_DELETE_ALL = 
		/* SQL : 	Delete from UserDmsp  */
		"/*EP \u0000 " + 
		"0 3 3 2 # " + /* SCAN, Table UserDMSP (2) -->R0 */
		"1 2 2 3 r0 1 2 1 10 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table UserDMSP(2), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v12 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 2 r0 # " + /* TABLE DELETE, table UserDMSP(2), tuple pos: R0 */
		"\u0000*/";


	public static String EP_COMMENT_DELETE_ALL = 
		/* SQL : 	Delete from Comment  */
		"/*EP \u0000 " + 
		"0 3 3 5 # " + /* SCAN, Table Comment (5) -->R0 */
		"1 2 2 3 r0 1 5 1 47 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Comment(5), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v15 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 5 r0 # " + /* TABLE DELETE, table Comment(5), tuple pos: R0 */
		"\u0000*/";


	public static String EP_ROLE_DELETE_ALL= 
		/* SQL : 	Delete from Role  */
		"/*EP \u0000 " + 
		"0 3 3 6 # " + /* SCAN, Table Role (6) -->R0 */
		"1 2 2 3 r0 1 6 1 52 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Role(6), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v16 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 6 r0 # " + /* TABLE DELETE, table Role(6), tuple pos: R0 */
		"\u0000*/";


	public static String EP_HABILITATION_DELETE_ALL = 
		/* SQL : 	Delete from Habilitation  */
		"/*EP \u0000 " + 
		"0 3 3 8 # " + /* SCAN, Table Habilitation (8) -->R0 */
		"1 2 2 3 r0 1 8 1 57 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Habilitation(8), is_table: 1  -->R1 - R1 */
		"5 1 1 2 3 12 1 85 v18 86 r0 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 8 r0 # " + /* TABLE DELETE, table Habilitation(8), tuple pos: R0 */
		"\u0000*/";

public static String EP_EVENT_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Insert into event values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 " + 
"2 6 6 -1 1 ?4 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?4(R0) -->R1 */
"2 7 7 -1 4 ?5 # " + /* CI_LOOKUP ref_tab:UserDMSP(2) key_col_id:10 ka_id:4	 key:?5(R2) -->R3 */
"7 5 5 6 7 # " + /* FLOW_X */
"2 8 8 -1 0 ?6 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?6(R4) -->R5 */
"7 4 4 5 8 # " + /* FLOW_X */
"6 3 3 4 2 ?4 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:2, key:?4(R6) */
"6 2 2 3 5 v30 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Event(3), col:IdGlobal(28), ka_id:5, key:v30(R7) */
"5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # " + /* TABLE_INSERT 3 cols, skt SktEvent(0), is_table: 0, [col_id value] */
"5 0 0 1 10 3 1 29 ?1 30 ?2 31 ?3 32 ?4 33 ?5 34 ?6 35 ?7 95 ?8 96 ?9 28 r7 # " + /* TABLE_INSERT 10 cols, table Event(3), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_INFO_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Insert into info values (?,?,?,?,?, ?,?,?,?,?, ?) */
"/*EP \u000B " + 
"2 7 7 -1 5 ?4 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?4(R0) -->R1 */
"1 6 6 7 r1 3 0 0 0 1 2 # " + /* TABLE_LOOKUP pos:R1, 3 cols, skt SktEvent(0), is_table: 0  -->R2 - R4 */
"1 5 5 6 r2 1 1 1 5 # " + /* TABLE_LOOKUP pos:R2, 1 cols, table Formulaire(1), is_table: 1	-->R5 - R5 */
"2 8 8 -1 7 ?5 # " + /* CI_LOOKUP ref_tab:Comment(5) key_col_id:47 ka_id:7	 key:?5(R6) -->R7 */
"7 4 4 5 8 # " + /* FLOW_X */
"6 3 3 4 3 r5 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:3, key:R5 */
"6 2 2 3 6 v30 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Info(4), col:IdGlobal(36), ka_id:6, key:v30(R8) */
"5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r7 # " + /* TABLE_INSERT 5 cols, skt SktInfo(1), is_table: 0, [col_id value] */
"5 0 0 1 12 4 1 37 ?1 38 ?2 39 ?3 40 ?4 41 ?5 42 ?6 43 ?7 44 ?8 45 ?9 46 ?10 92 ?11 36 r8 # " + /* TABLE_INSERT 12 cols, table Info(4), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_COMMENT_INSERT 	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Insert into comment values (?,?,?,?) */
"/*EP \u0004 " + 
"6 1 1 -1 7 v30 # " + /* CI_INSERT ref_tab:Comment(5), key_tab:Comment(5), col:IdGlobal(47), ka_id:7, key:v30(R0) */
"5 0 0 1 5 5 1 48 ?1 49 ?2 50 ?3 51 ?4 47 r0 # " + /* TABLE_INSERT 5 cols, table Comment(5), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_EPISODE_SELECT_STAR =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from episode */
"/*EP \u0000 " + 
"0 1 1 0 # " + /* SCAN, Table Episode (0) -->R0 */
"1 0 0 1 r0 5 0 1 0 1 2 3 4 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table Episode(0), is_table: 1	-->R1 - R5 */
"\u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_STAR =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from formulaire */
"/*EP \u0000 " + 
"0 1 1 1 # " + /* SCAN, Table Formulaire (1) -->R0 */
"1 0 0 1 r0 6 1 1 5 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Formulaire(1), is_table: 1	-->R1 - R6 */
"\u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_USER_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from userdmsp */
"/*EP \u0000 " + 
"0 1 1 2 # " + /* SCAN, Table UserDMSP (2) -->R0 */
"1 0 0 1 r0 19 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 93 # " + /* TABLE_LOOKUP pos:R0, 19 cols, table UserDMSP(2), is_table: 1	-->R1 - R19 */
"\u0000 19 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Type 0 7 Responsable 0 8 Identifiant 1 9 Civilite 0 10 Prenom 0 11 Adresse 0 12 Ville 0 13 CodePost 0 14 Tel 0 15 Mobile 0 16 Courriel 1 17 InfoLegale 0 18 Certificate 1 19 IdRole # " + /* META_RESULT, 19 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_EVENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from event */
"/*EP \u0000 " + 
"0 1 1 3 # " + /* SCAN, Table Event (3) -->R0 */
"1 0 0 1 r0 10 3 1 28 29 30 31 32 33 34 35 95 96 # " + /* TABLE_LOOKUP pos:R0, 10 cols, table Event(3), is_table: 1	-->R1 - R10 */
"\u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # " + /* META_RESULT, 10 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_INFO_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from info */
"/*EP \u0000 " + 
"0 1 1 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 0 0 1 r0 12 4 1 36 37 38 39 40 41 42 43 44 45 46 92 # " + /* TABLE_LOOKUP pos:R0, 12 cols, table Info(4), is_table: 1	-->R1 - R12 */
"\u0000 12 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdComment 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 Filtre 1 12 IdConcept # " + /* META_RESULT, 12 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_COMMENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from comment */
"/*EP \u0000 " + 
"0 1 1 5 # " + /* SCAN, Table Comment (5) -->R0 */
"1 0 0 1 r0 5 5 1 47 48 49 50 51 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table Comment(5), is_table: 1	-->R1 - R5 */
"\u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_ROLE_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from role */
"/*EP \u0000 " + 
"0 1 1 6 # " + /* SCAN, Table Role (6) -->R0 */
"1 0 0 1 r0 5 6 1 52 53 54 55 56 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table Role(6), is_table: 1	-->R1 - R5 */
"\u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_HABILITATION_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	Select * from habilitation  */
"/*EP \u0000 " + 
"0 1 1 8 # " + /* SCAN, Table Habilitation (8) -->R0 */
"1 0 0 1 r0 6 8 1 57 58 59 60 62 63 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Habilitation(8), is_table: 1	-->R1 - R6 */
"\u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_PATIENT_SELECT_STAR = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST ???
/* SQL : 	Select * from matricepatient */
"/*EP \u0000 " + 
"0 1 1 10 # " + /* SCAN, Table MatricePatient (10) -->R0 */
"1 0 0 1 r0 9 10 1 71 72 73 74 75 76 77 78 79 # " + /* TABLE_LOOKUP pos:R0, 9 cols, table MatricePatient(10), is_table: 1	-->R1 - R9 */
"\u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # " + /* META_RESULT, 9 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_INSERT =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
/* SQL : 	Insert into formulaire values (?,?,?,?,?) */
"/*EP \u0005 " + 
"6 1 1 -1 1 v30 # " + /* CI_INSERT ref_tab:Formulaire(1), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:1, key:v30(R0) */
"5 0 0 1 6 1 1 6 ?1 7 ?2 8 ?3 9 ?4 94 ?5 5 r0 # " + /* TABLE_INSERT 6 cols, table Formulaire(1), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_USER_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
/* SQL : 	Insert into userdmsp values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?) */
"/*EP \u0012 " + 
"6 1 1 -1 4 v30 # " + /* CI_INSERT ref_tab:UserDMSP(2), key_tab:UserDMSP(2), col:IdGlobal(10), ka_id:4, key:v30(R0) */
"5 0 0 1 19 2 1 11 ?1 12 ?2 13 ?3 14 ?4 15 ?5 16 ?6 17 ?7 18 ?8 19 ?9 20 ?10 21 ?11 22 ?12 23 ?13 24 ?14 25 ?15 26 ?16 27 ?17 93 ?18 10 r0 # " + /* TABLE_INSERT 19 cols, table UserDMSP(2), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_ROLE_INSERT  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
/* SQL : 	Insert into role values (?,?,?,?) */
"/*EP \u0004 " + 
"6 1 1 -1 8 v30 # " + /* CI_INSERT ref_tab:Role(6), key_tab:Role(6), col:IdGlobal(52), ka_id:8, key:v30(R0) */
"5 0 0 1 5 6 1 53 ?1 54 ?2 55 ?3 56 ?4 52 r0 # " + /* TABLE_INSERT 5 cols, table Role(6), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_EPISODE_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
/* SQL : 	Insert into episode values (?,?,?,?) */
"/*EP \u0004 " + 
"6 1 1 -1 0 v30 # " + /* CI_INSERT ref_tab:Episode(0), key_tab:Episode(0), col:IdGlobal(0), ka_id:0, key:v30(R0) */
"5 0 0 1 5 0 1 1 ?1 2 ?2 3 ?3 4 ?4 0 r0 # " + /* TABLE_INSERT 5 cols, table Episode(0), is_table: 1, [col_id value] */
"\u0000*/";


// there are multiple insert EPs, selection is done in tupleInsert below 
// ---- Multiple INSERTS ---- 
// CATEGORIE AUTRE: 
	public static String EP_PATIENT_INSERT_AUTRE_USER	=  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 10 2 4 0 5 v10 52 r11 # " +  // TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0) --> R9
		"4 10 10 11 10 0 r6 r10 # " +   	// SELECT, Att IdGlobal(10), equal (0), value r6 (IdCat), From Pos r10
		"1 11 11 12 r11 1 2 1 10 # " +  	// TABLE_LOOKUP, Pos R11, 1 col, Table USERDMSP(2), a TABLE(1) --> R10
		"0 12 12 2 # \u0000*/"; 					// SCAN, Table USERDMSP(2) -->R11

	public static String EP_PATIENT_INSERT_AUTRE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 10 2 4 0 5 v10 52 r11 # " +  // TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0) --> R9
		"4 10 10 11 52 0 r6 r10 # " +   	// SELECT, Att IdGlobal(52), equal (0), value r6 (IdCat), From Pos r10
		"1 11 11 12 r11 1 6 1 52 # " +  	// TABLE_LOOKUP, Pos R11, 1 col, Table ROLE(6), a TABLE(1) --> R10
		"0 12 12 6 # \u0000*/"; 					// SCAN, Table ROLE(6) -->R11

// CATEGORIE USER: 
	public static String EP_PATIENT_INSERT_USER_USER	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " +   // TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # "+   					// FLOW
		"4 4 4 5 10 0 r4 r9 # " +   		// SELECT, Att IdGlobal(10), equal (0), value r4 (IdCat), From Pos r9
		"1 5 5 6 r10 1 2 1 10 # " +  		// TABLE_LOOKUP, Pos R10, 1 col, Table USERDMSP(2), a TABLE(1) --> R9
		"0 6 6 2 # "+  						// SCAN, Table USERDMSP(2) -->R10
		"4 10 10 11 10 0 r6 r11 # " +  		// SELECT, Att IdGlobal(10), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 2 1 10 # " +  	// TABLE_LOOKUP, Pos R12, 1 col, Table USER(2), a TABLE(1) --> R11
		"0 12 12 2 # \u0000*/";					// SCAN, Table USER(2) -->R12

	public static String EP_PATIENT_INSERT_USER_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " +   // TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # " +   				// FLOW
		"4 4 4 5 10 0 r4 r9 # " +   		// SELECT, Att IdGlobal(10), equal (0), value r4 (IdCat), From Pos r9
		"1 5 5 6 r10 1 2 1 10 # " +  		// TABLE_LOOKUP, Pos R10, 1 col, Table USERDMSP(2), a TABLE(1) --> R9
		"0 6 6 2 #" +  						// SCAN, Table USERDMSP(2) -->R10
		"4 10 10 11 52 0 r6 r11 # " +  		// SELECT, Att IdGlobal(10), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 6 1 52 # " + 		// TABLE_LOOKUP, Pos R12, 1 col, Table ROLE(6), a TABLE(1) --> R11
		"0 12 12 6 # \u0000*/";					// SCAN, Table ROLE(6) -->R12

// CATEGORIE FORMULAIRE: 
	public static String EP_PATIENT_INSERT_FORMULAIRE_USER	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " +   // TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # " +  					// FLOW
		"4 4 4 5 5 0 r4 r9 # " +  			// SELECT, Att IdGlobal(5), equal (0), value r4 (IdCat), From Pos r9
		"1 5 5 6 r10 1 1 1 5 # " + 			// TABLE_LOOKUP, Pos R10, 1 col, Table FORM(1), a TABLE(1) --> R9
		"0 6 6 1 # " + 						// SCAN, Table FORM(1) -->R10
		"4 10 10 11 10 0 r6 r11 # " + 		// SELECT, Att IdGlobal(10), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 2 1 10 # " + 		// TABLE_LOOKUP, Pos R12, 1 col, Table USER(2), a TABLE(1) --> R11
		"0 12 12 2 # \u0000*/";					// SCAN, Table USER(2) -->R12

	public static String EP_PATIENT_INSERT_FORMULAIRE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " + 	// TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # " + 					// FLOW
		"4 4 4 5 5 0 r4 r9 # " + 			// SELECT, Att IdGlobal(5), equal (0), value r4 (IdCat_Epi), From Pos r9
		"1 5 5 6 r10 1 1 1 5 # " + 			// TABLE_LOOKUP, Pos R10, 1 col, Table FORM(1), a TABLE(1) --> R9
		"0 6 6 1 # " + 						// SCAN, Table FORM(1) -->R10
		"4 10 10 11 52 0 r6 r11 # " + 		// SELECT, Att IdGlobal(52), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 6 1 52 # " + 		// TABLE_LOOKUP, Pos R12, 1 col, Table ROLE(6), a TABLE(1) --> R11
		"0 12 12 6 # \u0000*/";					// SCAN, Table ROLE(6) -->R12

// CATEGORIE EPISODE: 
	public static String EP_PATIENT_INSERT_EPISODE_USER	=  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " + 	// TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # " + 					// FLOW
		"4 4 4 5 0 0 r4 r9 # " + 			// SELECT, Att IdGlobal(5), equal (0), value r4 (IdCat_Epi), From Pos r9
		"1 5 5 6 r10 1 0 1 0 # " + 			// TABLE_LOOKUP, Pos R10, 1 col, Table EPI(0), a TABLE(1) --> R9
		"0 6 6 0 # "+ 						// SCAN, Table EPI(0) -->R10
		"4 10 10 11 10 0 r6 r11 # " + 		// SELECT, Att IdGlobal(10), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 2 1 10 # " + 		// TABLE_LOOKUP, Pos R12, 1 col, Table USER(2), a TABLE(1) --> R11
		"0 12 12 2 # \u0000*/";					// SCAN, Table USER(2) -->R12

	public static String EP_PATIENT_INSERT_EPISODE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
		//Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
		"/*EP \u0008 5 0 0 1 9 10 1 71 v30 72 ?1 73 ?2 74 ?3 75 ?4 76 ?5 77 ?6 78 ?7 79 ?8 # " + 
		// TABLE_INSERT, 9 colums, Table MPAT(9), a table (1), --> R0 - R8
		"5 1 1 2 2 4 0 5 r10 52 r12 # " + 	// TABLE_INSERT, 2 colums, Table SKT_MPAT(4), a SKT (0)
		"7 2 2 10 4 # " + 					// FLOW
		"4 4 4 5 0 0 r4 r9 # " + 			// SELECT, Att IdGlobal(5), equal (0), value r4 (IdCat_Epi), From Pos r9
		"1 5 5 6 r10 1 0 1 0 # " + 			// TABLE_LOOKUP, Pos R10, 1 col, Table EPI(0), a TABLE(1) --> R9
		"0 6 6 0 # " + 						// SCAN, Table EPI(0) -->R10
		"4 10 10 11 52 0 r6 r11 # " + 		// SELECT, Att IdGlobal(52), equal (0), value r6 (IdActeur), From Pos r11
		"1 11 11 12 r12 1 6 1 52 # " + 		// TABLE_LOOKUP, Pos R12, 1 col, Table ROLE(6), a TABLE(1) --> R11
		"0 12 12 6 # \u0000*/";					// SCAN, Table ROLE(6) -->R12

// ---- end of INSERTS ---- 

public static  String OEP_EVENT_AC_INSERT = 		 
/* SQL : 	Insert into event values (?,?,0, ?,?,?,?,?,?) */
"/*EP \u0008 " + 
"2 7 7 -1 1 ?3 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?3(R0) -->R1 */
"2 8 8 -1 4 ?4 # " + /* CI_LOOKUP ref_tab:UserDMSP(2) key_col_id:10 ka_id:4	 key:?4(R2) -->R3 */
"7 6 6 7 8 # " + /* FLOW_X */
"2 9 9 -1 0 ?5 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?5(R4) -->R5 */
"7 5 5 6 9 # " + /* FLOW_X */
"8 4 4 5 3 r3 r1 r5 # " + /* ACCESS_RIGHTS access_type: 3 (0-s,1-u,2-d,3-i) res_user:R3 res_form:R1 res_epis:R5 */
"6 3 3 4 2 ?3 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:2, key:?3(R6) */
"6 2 2 3 5 v30 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Event(3), col:IdGlobal(28), ka_id:5, key:v30(R7) */
"5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # " + /* TABLE_INSERT 3 cols, skt SktEvent(0), is_table: 0, [col_id value] */
"5 0 0 1 10 3 1 29 ?1 30 ?2 31 v10 32 ?3 33 ?4 34 ?5 35 ?6 95 ?7 96 ?8 28 r7 # " + /* TABLE_INSERT 10 cols, table Event(3), is_table: 1, [col_id value] */
"\u0000*/";


public static String OEP_INFO_AC_INSERT =  
/* SQL : 	Insert into info values (?,?,0,?,?,?, ?,?,?,?,?) */
"/*EP \n " + 
"2 8 8 -1 5 ?3 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?3(R0) -->R1 */
"1 7 7 8 r1 3 0 0 0 1 2 # " + /* TABLE_LOOKUP pos:R1, 3 cols, skt SktEvent(0), is_table: 0  -->R2 - R4 */
"8 6 6 7 3 r3 r2 r4 # " + /* ACCESS_RIGHTS access_type: 3 (0-s,1-u,2-d,3-i) res_user:R3 res_form:R2 res_epis:R4 */
"1 5 5 6 r2 1 1 1 5 # " + /* TABLE_LOOKUP pos:R2, 1 cols, table Formulaire(1), is_table: 1	-->R5 - R5 */
"2 9 9 -1 7 ?4 # " + /* CI_LOOKUP ref_tab:Comment(5) key_col_id:47 ka_id:7	 key:?4(R6) -->R7 */
"7 4 4 5 9 # " + /* FLOW_X */
"6 3 3 4 3 r5 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:3, key:R5 */
"6 2 2 3 6 v30 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Info(4), col:IdGlobal(36), ka_id:6, key:v30(R8) */
"5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r7 # " + /* TABLE_INSERT 5 cols, skt SktInfo(1), is_table: 0, [col_id value] */
"5 0 0 1 12 4 1 37 ?1 38 ?2 39 v10 40 ?3 41 ?4 42 ?5 43 ?6 44 ?7 45 ?8 46 ?9 92 ?10 36 r8 # " + /* TABLE_INSERT 12 cols, table Info(4), is_table: 1, [col_id value] */
"\u0000*/";


	// select * from sktinfo
	public static String EP_SKTINFO_SELECT_STAR = 
		// Select * from SKTInfo  
		"/*EP \u0000 0 1 1 4 # " +     // SCAN, Table INFO(4) -->R0
		"1 0 0 1 r0 5 1 0 0 1 2 3 4 # " +  // TABLE_LOOKUP, Pos R0, 5 cols, Table SKT INFO(4), a SKT(0) --> R1 - R5
		"\u0000 6 1 0 PosInfo 1 1 PosForm 1 2 PosUser 1 3 PosEpisode 1 4 PosEvent 1 5 PosComment # \u0000*/";  // 5 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

	// select * from sktevent
	public static String EP_SKTEVENT_SELECT_STAR = 
		// Select * from SKTInfo  
		"/*EP \u0000 0 1 1 3 # " +     // SCAN, Table INFO(4) -->R0
		"1 0 0 1 r0 3 0 0 0 1 2 # " +  // TABLE_LOOKUP, Pos R0, 5 cols, Table SKT INFO(4), a SKT(0) --> R1 - R5
		"\u0000 4 1 0 PosEvent 1 1 PosForm 1 2 PosUser 1 3 PosEpisode # \u0000*/";  // 5 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

	// ouverture d'un formulaire (Event.IdGlobal=?)
	public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
		//SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment
		//  FROM event e, info i, comment c
		//  WHERE i.Idevent=e.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=?
		"/*EP \u0001 0 5 5 4 # " +     // scan of table info(4) --> r0
		"1 4 4 5 r0 6 4 1 40 36 42 43 45 44 # " +
		// TABLE LOOKUP at pos r0,  retrieving 6 cols, of table info (4), it's a table (1), 40 (IdEvent), 36(IDG), 42 (ValChar), 43 (ValNum), 45    (Pos), 44 (ValDate) -->  r1-r6
		"4 3 3 4 40 0 ?1 r1 # " +
		// SELECT Col Info.IdEvent (40), is equal (0), to parameter 1, the value being in r1 --> R7
		"1 2 2 3 r0 5 1 0 0 1 2 3 4 # " +
		// TABLE LOOKUP at pos r0,  retrieving 1 cols, of table SKT info (1), it's a SKT (0), 47(IdInternComment)-->  r8 -> r12
		"1 1 1 2 r12 1 5 1 51 # " +
		"8 0 0 1 0 r9 r8 r10 # " +	
		// TABLE LOOKUP at pos r7,  retrieving 1 cols, of table Comment (5), it's a table(1), 51(ValComment)-->  r13
		"\u0000 6 1 2 IdGlobal 1 5 Position 0 3 Valchar 2 6 ValDate 1 4 Valnum  0 13 ValComment # \u0000*/";
	// 6 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i

// produced by the moulinette 
public static String EP_DELETED_SELECT_STAR =
/* SQL : 	Select * FROM TupleDeleted; */
"/*EP \u0000 " + 
"0 1 1 11 # " + /* SCAN, Table TupleDeleted (11) -->R0 */
"1 0 0 1 r0 5 11 1 80 81 82 83 84 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table TupleDeleted(11), is_table: 1	-->R1 - R5 */
"\u0000 5 1 1 IdGlobal 1 2 TabId 1 3 Author 1 4 TSSPT 1 5 TSSanteos # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NOAC_SELECT_FORM_BY_NAME=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.IdComment, i.ValChar, i.ValDate, i.ValNum FROM Event e, Info i, Formulaire f WHERE i.IdEvent = e.IdGlobal AND e.IdForm = f.IdGlobal AND f.Nom = ? */
"/*EP \u0001 " + 
"0 4 4 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 3 3 4 r0 1 1 0 0 # " + /* TABLE_LOOKUP pos:R0, 1 cols, skt SktInfo(1), is_table: 0  -->R1 - R1 */
"1 2 2 3 r1 1 1 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table Formulaire(1), is_table: 1	-->R2 - R2 */
"4 1 1 2 9 0 ?1 r2 # " + /* SELECT, att: Nom(9), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"1 0 0 1 r0 7 4 1 36 45 40 41 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 7 cols, table Info(4), is_table: 1	-->R4 - R10 */
"\u0000 7 1 4 IdGlobal 1 5 Position 1 6 IdEvent 1 7 IdComment 0 8 ValChar 2 9 ValDate 1 10 ValNum # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static final String EP_QUERY_NOAC_SELECT_USER =
/* SQL : 	SELECT Nom, Prenom, Ville, Tel, Mobile, Type FROM UserDMSP */
"/*EP \u0000 " + 
"0 1 1 2 # " + /* SCAN, Table UserDMSP (2) -->R0 */
"1 0 0 1 r0 6 2 1 14 19 21 23 24 15 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table UserDMSP(2), is_table: 1	-->R1 - R6 */
"\u0000 6 0 1 Nom 0 2 Prenom 0 3 Ville 0 4 Tel 0 5 Mobile 1 6 Type # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static final String EP_QUERY_NOAC_SELECT_USER_BETWEEN_NOM =
/* SQL : 	SELECT Nom, Prenom, Ville, Tel, Mobile, Type FROM UserDMSP WHERE Nom > ? AND Nom < ? */
"/*EP \u0002 " + 
"0 3 3 2 # " + /* SCAN, Table UserDMSP (2) -->R0 */
"1 2 2 3 r0 6 2 1 14 19 21 23 24 15 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table UserDMSP(2), is_table: 1	-->R1 - R6 */
"4 1 1 2 14 1 ?1 r1 # " + /* SELECT, att: Nom(14), comparator: 1, parameter: ?1 (R7), from pos: R1*/
"4 0 0 1 14 2 ?2 r1 # " + /* SELECT, att: Nom(14), comparator: 2, parameter: ?2 (R8), from pos: R1*/
"\u0000 6 0 1 Nom 0 2 Prenom 0 3 Ville 0 4 Tel 0 5 Mobile 1 6 Type # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_SELECT_FORM_BY_NAME_AND_IDEVENT=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.IdComment, i.ValChar, i.ValDate, i.ValNum FROM Event e, Info i, Formulaire f WHERE i.IdEvent = e.IdGlobal AND e.IdForm = f.IdGlobal AND f.Nom = ? AND i.IdEvent = ? */
"/*EP \u0002 " + 
"0 5 5 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 4 4 5 r0 7 4 1 36 45 40 41 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 7 cols, table Info(4), is_table: 1	-->R1 - R7 */
"4 3 3 4 40 0 ?2 r3 # " + /* SELECT, att: IdEvent(40), comparator: 0, parameter: ?2 (R8), from pos: R3*/
"1 2 2 3 r0 1 1 0 0 # " + /* TABLE_LOOKUP pos:R0, 1 cols, skt SktInfo(1), is_table: 0  -->R9 - R9 */
"1 1 1 2 r9 1 1 1 9 # " + /* TABLE_LOOKUP pos:R9, 1 cols, table Formulaire(1), is_table: 1	-->R10 - R10 */
"4 0 0 1 9 0 ?1 r10 # " + /* SELECT, att: Nom(9), comparator: 0, parameter: ?1 (R11), from pos: R10*/
"\u0000 7 1 1 IdGlobal 1 2 Position 1 3 IdEvent 1 4 IdComment 0 5 ValChar 2 6 ValDate 1 7 ValNum # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_AC_SELECT_FORM_BY_ID_AND_IDEVENT=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND e.IdForm = ? AND i.IdEvent = ? */
"/*EP \u0002 " + 
"0 7 7 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 6 6 7 r0 6 4 1 36 45 40 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Info(4), is_table: 1	-->R1 - R6 */
"4 5 5 6 40 0 ?2 r3 # " + /* SELECT, att: IdEvent(40), comparator: 0, parameter: ?2 (R7), from pos: R3*/
"1 4 4 5 r0 5 1 0 1 0 2 4 3 # " + /* TABLE_LOOKUP pos:R0, 5 cols, skt SktInfo(1), is_table: 0  -->R8 - R12 */
"8 3 3 4 0 r8 r9 r10 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R8 res_form:R9 res_epis:R10 */
"1 2 2 3 r12 1 3 1 32 # " + /* TABLE_LOOKUP pos:R12, 1 cols, table Event(3), is_table: 1	-->R13 - R13 */
"4 1 1 2 32 0 ?1 r13 # " + /* SELECT, att: IdForm(32), comparator: 0, parameter: ?1 (R14), from pos: R13*/
"1 0 0 1 r11 1 5 1 51 # " + /* TABLE_LOOKUP pos:R11, 1 cols, table Comment(5), is_table: 1	-->R15 - R15 */
"\u0000 7 1 1 IdGlobal 1 2 Position 1 3 IdEvent 0 4 ValChar 2 5 ValDate 1 6 ValNum 0 15 ValComment # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_QUERY_AC_SELECT_INFO_BY_FORM_BETWEEN_EVENT=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.IdConcept, i.ValChar, i.ValDate, i.ValNum, i.IdComment FROM Event e, Info i WHERE i.IdEvent = e.IdGlobal AND e.IdForm = ? AND i.IdEvent > ? AND i.IdEvent < ? */
"/*EP \u0003 " + 
"0 7 7 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 6 6 7 r0 8 4 1 36 45 40 92 42 44 43 41 # " + /* TABLE_LOOKUP pos:R0, 8 cols, table Info(4), is_table: 1	-->R1 - R8 */
"4 5 5 6 40 1 ?2 r3 # " + /* SELECT, att: IdEvent(40), comparator: 1, parameter: ?2 (R9), from pos: R3*/
"4 4 4 5 40 2 ?3 r3 # " + /* SELECT, att: IdEvent(40), comparator: 2, parameter: ?3 (R10), from pos: R3*/
"1 3 3 4 r0 4 1 0 1 0 2 3 # " + /* TABLE_LOOKUP pos:R0, 4 cols, skt SktInfo(1), is_table: 0  -->R11 - R14 */
"8 2 2 3 0 r11 r12 r13 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R11 res_form:R12 res_epis:R13 */
"1 1 1 2 r14 1 3 1 32 # " + /* TABLE_LOOKUP pos:R14, 1 cols, table Event(3), is_table: 1	-->R15 - R15 */
"4 0 0 1 32 0 ?1 r15 # " + /* SELECT, att: IdForm(32), comparator: 0, parameter: ?1 (R16), from pos: R15*/
"\u0000 8 1 1 IdGlobal 1 2 Position 1 3 IdEvent 1 4 IdConcept 0 5 ValChar 2 6 ValDate 1 7 ValNum 1 8 IdComment # " + /* META_RESULT, 8 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm = ? */
"/*EP \u0001 " + 
"0 6 6 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 5 5 6 r0 5 1 0 1 0 2 4 3 # " + /* TABLE_LOOKUP pos:R0, 5 cols, skt SktInfo(1), is_table: 0  -->R1 - R5 */
"8 4 4 5 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 3 3 4 r5 1 3 1 32 # " + /* TABLE_LOOKUP pos:R5, 1 cols, table Event(3), is_table: 1	-->R6 - R6 */
"4 2 2 3 32 0 ?1 r6 # " + /* SELECT, att: IdForm(32), comparator: 0, parameter: ?1 (R7), from pos: R6*/
"1 1 1 2 r0 6 4 1 36 45 40 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Info(4), is_table: 1	-->R8 - R13 */
"1 0 0 1 r4 1 5 1 51 # " + /* TABLE_LOOKUP pos:R4, 1 cols, table Comment(5), is_table: 1	-->R14 - R14 */
"\u0000 7 1 8 IdGlobal 1 9 Position 1 10 IdEvent 0 11 ValChar 2 12 ValDate 1 13 ValNum 0 14 ValComment # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM_BETWEEN_EVENT=
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm = ? AND i.IdEvent > ? AND i.IdEvent < ? */
"/*EP \u0003 " + 
"0 8 8 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 7 7 8 r0 6 4 1 36 45 40 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Info(4), is_table: 1	-->R1 - R6 */
"4 6 6 7 40 1 ?2 r3 # " + /* SELECT, att: IdEvent(40), comparator: 1, parameter: ?2 (R7), from pos: R3*/
"4 5 5 6 40 2 ?3 r3 # " + /* SELECT, att: IdEvent(40), comparator: 2, parameter: ?3 (R8), from pos: R3*/
"1 4 4 5 r0 5 1 0 1 0 2 4 3 # " + /* TABLE_LOOKUP pos:R0, 5 cols, skt SktInfo(1), is_table: 0  -->R9 - R13 */
"8 3 3 4 0 r9 r10 r11 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R9 res_form:R10 res_epis:R11 */
"1 2 2 3 r13 1 3 1 32 # " + /* TABLE_LOOKUP pos:R13, 1 cols, table Event(3), is_table: 1	-->R14 - R14 */
"4 1 1 2 32 0 ?1 r14 # " + /* SELECT, att: IdForm(32), comparator: 0, parameter: ?1 (R15), from pos: R14*/
"1 0 0 1 r12 1 5 1 51 # " + /* TABLE_LOOKUP pos:R12, 1 cols, table Comment(5), is_table: 1	-->R16 - R16 */
"\u0000 7 1 1 IdGlobal 1 2 Position 1 3 IdEvent 0 4 ValChar 2 5 ValDate 1 6 ValNum 0 16 ValComment # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


	public static String EP_QUERY_AC_SELECT_EVENTnop_INFO_COMMENT_BY_EVENT = 
		/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM event e, info i, comment c WHERE i.Idevent=e.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=? */
		"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 2 1 0 4 3 # 1 3 3 4 r2 1 3 1 28 # 4 2 2 3 28 0 ?1 r3 # 1 1 1 2 r0 5 4 1 36 45 42 44 43 # 1 0 0 1 r1 1 5 1 51 # \u0000 6 1 5 IdGlobal 1 6 Position 0 7 ValChar 2 8 ValDate 1 9 ValNum 0 10 ValComment # \u0000*/";

	public static final String EP_QUERY_NOAC_SELECT_USER_DUMMY =
		/* SQL : 	SELECT Nom, Prenom, Ville, Tel, Mobile, Type FROM UserDMSP */
		"/*EP \u0000 " + 
		"0 2 2 2 # " + /* SCAN, Table UserDMSP (2) -->R0 */
		"1 1 1 2 r0 6 2 1 14 19 21 23 24 15 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table UserDMSP(2), is_table: 1  -->R1 - R6 */
		"q 0 0 1 # " + /* DUMMY */
		"\u0000 6 0 1 Nom 0 2 Prenom 0 3 Ville 0 4 Tel 0 5 Mobile 1 6 Type # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
		"\u0000*/";
}
