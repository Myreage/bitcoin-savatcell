package org.inria.dmsp;


public class EP_PDS {


 /*

  * PLANS TO TEST THE NEW PDS CODE

  */


public static String EP_EPISODE_INSERT =
/* SQL : 	Insert into episode values (?,?,?,?,?) */
"/*EP \u0005 " + 
"6 1 1 -1 0 ?1 # " + /* CI_INSERT ref_tab:Episode(0), key_tab:Episode(0), col:IdGlobal(0), ka_id:0, key:?1(R0) */
"5 0 0 1 5 0 1 0 r0 1 ?2 2 ?3 3 ?4 4 ?5 # " + /* TABLE_INSERT 5 cols, table Episode(0), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_EPISODE_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Episode WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
"1 0 0 1 r1 4 0 1 1 2 3 4 # " + /* TABLE_LOOKUP pos:R1, 4 cols, table Episode(0), is_table: 1	-->R2 - R5 */
"\u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_INSERT =
/* SQL : 	INSERT INTO Formulaire VALUES (?, ?, ?, ?, ?, ?) */
"/*EP \u0006 " + 
"6 1 1 -1 1 ?1 # " + /* CI_INSERT ref_tab:Formulaire(1), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:1, key:?1(R0) */
"5 0 0 1 6 1 1 5 r0 6 ?2 7 ?3 8 ?4 9 ?5 94 ?6 # " + /* TABLE_INSERT 6 cols, table Formulaire(1), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_USER_INSERT =
/* SQL : 	Insert into userdmsp values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?, ?, ?) */
"/*EP \u0013 " + 
"6 1 1 -1 4 ?1 # " + /* CI_INSERT ref_tab:UserDMSP(2), key_tab:UserDMSP(2), col:IdGlobal(10), ka_id:4, key:?1(R0) */
"5 0 0 1 19 2 1 10 r0 11 ?2 12 ?3 13 ?4 14 ?5 15 ?6 16 ?7 17 ?8 18 ?9 19 ?10 20 ?11 21 ?12 22 ?13 23 ?14 24 ?15 25 ?16 26 ?17 27 ?18 93 ?19 # " + /* TABLE_INSERT 19 cols, table UserDMSP(2), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_USER_SELECT_BY_ID =
/* SQL : 	Select * from userdmsp where IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 4 ?1 # " + /* CI_LOOKUP ref_tab:UserDMSP(2) key_col_id:10 ka_id:4	 key:?1(R0) -->R1 */
"1 0 0 1 r1 18 2 1 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 93 # " + /* TABLE_LOOKUP pos:R1, 18 cols, table UserDMSP(2), is_table: 1	-->R2 - R19 */
"\u0000 19 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Type 0 7 Responsable 0 8 Identifiant 1 9 Civilite 0 10 Prenom 0 11 Adresse 0 12 Ville 0 13 CodePost 0 14 Tel 0 15 Mobile 0 16 Courriel 1 17 InfoLegale 0 18 Certificate 1 19 IdRole # " + /* META_RESULT, 19 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_EVENT_INSERT =
/* SQL : 	Insert into event values (?,?,?,?,?,?,?,?,?,?) */
"/*EP \n " + 
"2 6 6 -1 1 ?5 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?5(R0) -->R1 */
"2 7 7 -1 4 ?6 # " + /* CI_LOOKUP ref_tab:UserDMSP(2) key_col_id:10 ka_id:4	 key:?6(R2) -->R3 */
"7 5 5 6 7 # " + /* FLOW_X */
"2 8 8 -1 0 ?7 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?7(R4) -->R5 */
"7 4 4 5 8 # " + /* FLOW_X */
"6 3 3 4 2 ?5 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:2, key:?5(R6) */
"6 2 2 3 5 ?1 # " + /* CI_INSERT ref_tab:Event(3), key_tab:Event(3), col:IdGlobal(28), ka_id:5, key:?1(R7) */
"5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # " + /* TABLE_INSERT 3 cols, skt SktEvent(0), is_table: 0, [col_id value] */
"5 0 0 1 10 3 1 28 r7 29 ?2 30 ?3 31 ?4 32 ?5 33 ?6 34 ?7 35 ?8 95 ?9 96 ?10 # " + /* TABLE_INSERT 10 cols, table Event(3), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_EVENT_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Event WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 5 ?1 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?1(R0) -->R1 */
"1 0 0 1 r1 9 3 1 29 30 31 32 33 34 35 95 96 # " + /* TABLE_LOOKUP pos:R1, 9 cols, table Event(3), is_table: 1	-->R2 - R10 */
"\u0000 10 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # " + /* META_RESULT, 10 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_EVENT_SELECT_BY_IDFORM =
/* SQL : 	SELECT * FROM Event WHERE IdForm = ? */
"/*EP \u0001 " + 
"2 1 1 -1 2 ?1 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:5 ka_id:2	 key:?1(R0) -->R1 */
"1 0 0 1 r1 9 3 1 28 29 30 31 33 34 35 95 96 # " + /* TABLE_LOOKUP pos:R1, 9 cols, table Event(3), is_table: 1	-->R2 - R10 */
"\u0000 10 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 1 0 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # " + /* META_RESULT, 10 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_INFO_INSERT =
/* SQL : 	Insert into info values (?,?,?,?,?, ?,?,?,?,?,?,?) */
"/*EP \u000C " + 
"2 7 7 -1 5 ?5 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?5(R0) -->R1 */
"1 6 6 7 r1 3 0 0 0 1 2 # " + /* TABLE_LOOKUP pos:R1, 3 cols, skt SktEvent(0), is_table: 0  -->R2 - R4 */
"1 5 5 6 r2 1 1 1 5 # " + /* TABLE_LOOKUP pos:R2, 1 cols, table Formulaire(1), is_table: 1	-->R5 - R5 */
"2 8 8 -1 7 ?6 # " + /* CI_LOOKUP ref_tab:Comment(5) key_col_id:47 ka_id:7	 key:?6(R6) -->R7 */
"7 4 4 5 8 # " + /* FLOW_X */
"6 3 3 4 3 r5 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:3, key:R5 */
"6 2 2 3 6 ?1 # " + /* CI_INSERT ref_tab:Info(4), key_tab:Info(4), col:IdGlobal(36), ka_id:6, key:?1(R8) */
"5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r7 # " + /* TABLE_INSERT 5 cols, skt SktInfo(1), is_table: 0, [col_id value] */
"5 0 0 1 12 4 1 36 r8 37 ?2 38 ?3 39 ?4 40 ?5 41 ?6 42 ?7 43 ?8 44 ?9 45 ?10 46 ?11 92 ?12 # " + /* TABLE_INSERT 12 cols, table Info(4), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_INFO_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Info WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 6 ?1 # " + /* CI_LOOKUP ref_tab:Info(4) key_col_id:36 ka_id:6	 key:?1(R0) -->R1 */
"1 0 0 1 r1 11 4 1 37 38 39 40 41 42 43 44 45 46 92 # " + /* TABLE_LOOKUP pos:R1, 11 cols, table Info(4), is_table: 1	-->R2 - R12 */
"\u0000 12 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdComment 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 Filtre 1 12 IdConcept # " + /* META_RESULT, 12 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_COMMENT_INSERT 	=
/* SQL : 	Insert into comment values (?,?,?,?,?) */
"/*EP \u0005 " + 
"6 1 1 -1 7 ?1 # " + /* CI_INSERT ref_tab:Comment(5), key_tab:Comment(5), col:IdGlobal(47), ka_id:7, key:?1(R0) */
"5 0 0 1 5 5 1 47 r0 48 ?2 49 ?3 50 ?4 51 ?5 # " + /* TABLE_INSERT 5 cols, table Comment(5), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_COMMENT_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Comment WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 7 ?1 # " + /* CI_LOOKUP ref_tab:Comment(5) key_col_id:47 ka_id:7	 key:?1(R0) -->R1 */
"1 0 0 1 r1 4 5 1 48 49 50 51 # " + /* TABLE_LOOKUP pos:R1, 4 cols, table Comment(5), is_table: 1	-->R2 - R5 */
"\u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_ROLE_INSERT  =
/* SQL : 	Insert into role values (?,?,?,?,?) */
"/*EP \u0005 " + 
"6 1 1 -1 8 ?1 # " + /* CI_INSERT ref_tab:Role(6), key_tab:Role(6), col:IdGlobal(52), ka_id:8, key:?1(R0) */
"5 0 0 1 5 6 1 52 r0 53 ?2 54 ?3 55 ?4 56 ?5 # " + /* TABLE_INSERT 5 cols, table Role(6), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_ROLE_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Role WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 8 ?1 # " + /* CI_LOOKUP ref_tab:Role(6) key_col_id:52 ka_id:8	 key:?1(R0) -->R1 */
"1 0 0 1 r1 4 6 1 53 54 55 56 # " + /* TABLE_LOOKUP pos:R1, 4 cols, table Role(6), is_table: 1	-->R2 - R5 */
"\u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_HABILITATION_INSERT =	  
/* SQL : 	INSERT INTO Habilitation VALUES (?, ?, ?, ?, ?, ?) */
"/*EP \u0006 " + 
"2 4 4 -1 8 ?5 # " + /* CI_LOOKUP ref_tab:Role(6) key_col_id:52 ka_id:8	 key:?5(R0) -->R1 */
"2 5 5 -1 4 ?6 # " + /* CI_LOOKUP ref_tab:UserDMSP(2) key_col_id:10 ka_id:4	 key:?6(R2) -->R3 */
"7 3 3 4 5 # " + /* FLOW_X */
"6 2 2 3 9 ?1 # " + /* CI_INSERT ref_tab:Habilitation(8), key_tab:Habilitation(8), col:IdGlobal(57), ka_id:9, key:?1(R4) */
"5 1 1 2 2 2 0 0 r1 1 r3 # " + /* TABLE_INSERT 2 cols, skt SktHabili(2), is_table: 0, [col_id value] */
"5 0 0 1 6 8 1 57 r4 58 ?2 59 ?3 60 ?4 62 ?5 63 ?6 # " + /* TABLE_INSERT 6 cols, table Habilitation(8), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_HABILITATION_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Habilitation WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 9 ?1 # " + /* CI_LOOKUP ref_tab:Habilitation(8) key_col_id:57 ka_id:9	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 8 1 58 59 60 62 63 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Habilitation(8), is_table: 1	-->R2 - R6 */
"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_EVENT_UPDATE =
/* SQL : 	UPDATE Event SET Author = ?, TSSPT = ?, TSSANTEOS = ? ,  DateEvent = ?, DateFin = ?, Filtre = ? WHERE IdGlobal = ? */
"/*EP \u0007 " + 
"2 2 2 -1 5 ?7 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?7(R0) -->R1 */
"1 1 1 2 r1 3 3 1 32 33 34 # " + /* TABLE_LOOKUP pos:R1, 3 cols, table Event(3), is_table: 1	-->R2 - R4 */
"a 0 0 1 10 3 r1 28 ?7 29 ?1 30 ?2 31 ?3 32 r2 33 r3 34 r4 35 ?4 95 ?5 96 ?6 # " + /* TABLE UPDATE, 10 cols, Table Event(3), tuple pos: r1 [col_id res_id] */
"\u0000*/";


public static String EP_COMMENT_UPDATE =
/* SQL : 	UPDATE Comment SET Author = ?, TSSPT = ?, TSSanteos = ?, ValComment = ? WHERE IdGlobal = ? */
"/*EP \u0005 " + 
"2 1 1 -1 7 ?5 # " + /* CI_LOOKUP ref_tab:Comment(5) key_col_id:47 ka_id:7	 key:?5(R0) -->R1 */
"a 0 0 1 5 5 r1 47 ?5 48 ?1 49 ?2 50 ?3 51 ?4 # " + /* TABLE UPDATE, 5 cols, Table Comment(5), tuple pos: r1 [col_id res_id] */
"\u0000*/";


public static final String EP_INFO_DELETE =
/* SQL : 	DELETE FROM Info WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 2 2 -1 6 ?1 # " + /* CI_LOOKUP ref_tab:Info(4) key_col_id:36 ka_id:6	 key:?1(R0) -->R1 */
"5 1 1 2 3 12 1 85 v14 86 r1 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
"9 0 0 1 4 r1 # " + /* TABLE DELETE, table Info(4), tuple pos: R1 */
"\u0000*/";


public final static String EP_EVENT_DELETE =
/* SQL : 	DELETE FROM Event WHERE IdGlobal=? */
"/*EP \u0001 " + 
"2 2 2 -1 5 ?1 # " + /* CI_LOOKUP ref_tab:Event(3) key_col_id:28 ka_id:5	 key:?1(R0) -->R1 */
"5 1 1 2 3 12 1 85 v13 86 r1 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
"9 0 0 1 3 r1 # " + /* TABLE DELETE, table Event(3), tuple pos: R1 */
"\u0000*/";


}
