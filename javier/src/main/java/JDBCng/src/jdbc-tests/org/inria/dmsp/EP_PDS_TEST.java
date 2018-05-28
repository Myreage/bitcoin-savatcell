package org.inria.dmsp;


public class EP_PDS_TEST {


 /*

  * PLANS TO TEST THE NEW PDS CODE

  */


public static String EP_FORMULAIRE_INSERT =
/* SQL : 	INSERT INTO Formulaire VALUES (?, ?, ?, ?, ?, ?) */
"/*EP \u0006 " + 
"6 2 2 -1 1 ?1 # " + /* CI_INSERT ref_tab:Formulaire(1), key_tab:Formulaire(1), col:IdGlobal(5), ka_id:1, key:?1(R0) */
"6 1 1 2 10 ?6 # " + /* CI_INSERT ref_tab:Formulaire(1), key_tab:Formulaire(1), col:Filtre(94), ka_id:10, key:?6(R1) */
"5 0 0 1 6 1 1 5 r0 6 ?2 7 ?3 8 ?4 9 ?5 94 r1 # " + /* TABLE_INSERT 6 cols, table Formulaire(1), is_table: 1, [col_id value] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID =
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"2 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID_NO_CI =
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"0 2 2 1 # " + /* SCAN, Table Formulaire (1) -->R0 */
"1 1 1 2 r0 6 1 1 5 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Formulaire(1), is_table: 1	-->R1 - R6 */
"4 0 0 1 5 0 ?1 r1 # " + /* SELECT, att: IdGlobal(5), comparator: 0, parameter: ?1 (R7), from pos: R1*/
"\u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID_FIS =
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"g 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID_SS =
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ? */
"/*EP \u0001 " + 
"h 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:5 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_FILTRE =
/* SQL : 	SELECT * FROM Formulaire WHERE Filtre = ? */
"/*EP \u0001 " + 
"2 1 1 -1 10 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:94 ka_id:10	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 5 6 7 8 9 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 0 6 Nom 1 0 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI =
/* SQL : 	SELECT * FROM Formulaire WHERE Filtre = ? */
"/*EP \u0001 " + 
"0 2 2 1 # " + /* SCAN, Table Formulaire (1) -->R0 */
"1 1 1 2 r0 6 1 1 5 6 7 8 9 94 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Formulaire(1), is_table: 1	-->R1 - R6 */
"4 0 0 1 94 0 ?1 r6 # " + /* SELECT, att: Filtre(94), comparator: 0, parameter: ?1 (R7), from pos: R6*/
"\u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_FILTRE_FIS =
/* SQL : 	SELECT * FROM Formulaire WHERE Filtre = ? */
"/*EP \u0001 " + 
"g 1 1 -1 10 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:94 ka_id:10	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 5 6 7 8 9 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 0 6 Nom 1 0 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_FILTRE_SS =
/* SQL : 	SELECT * FROM Formulaire WHERE Filtre = ? */
"/*EP \u0001 " + 
"h 1 1 -1 10 ?1 # " + /* CI_LOOKUP ref_tab:Formulaire(1) key_col_id:94 ka_id:10	 key:?1(R0) -->R1 */
"1 0 0 1 r1 5 1 1 5 6 7 8 9 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Formulaire(1), is_table: 1	-->R2 - R6 */
"\u0000 6 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 0 6 Nom 1 0 Filtre # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";



}
