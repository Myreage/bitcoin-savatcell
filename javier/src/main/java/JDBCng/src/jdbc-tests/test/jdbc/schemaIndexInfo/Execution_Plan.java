/*
 * List of Execution Plans
 * Last modified : 20-11-2008 12h
 *
 * */

package test.jdbc.schemaIndexInfo;

public class Execution_Plan {

  /*
   * ** use '#' to indicate the end of a line (one line contains one operator) **
   *
   * SCAN 		:	0 pos id    table_id
   *
   * TABLE_LOOKUP : 	1 pos id    child   id_intern		nb_columns    {table_id| SKT_id} {0:SKT| 1:TABLE}     column_id1 ...
   *
   * CI_LOOKUP 	:	2 pos id    child   ref_tab_id  key_column_id key
   *
   * MERGE		:	3 pos id    child1  child2	in_result1	in_result2
   *
   * SELECT 		:	4 pos id    child   column_id   comparator      value       in_res
   * ** comparators : "=" is 0, ">" 1, "<" 2, ">=" 3, "<=" 4, "!=" 5
   *
   * ** For TABLE_INSERT, values are expressed like it is described in plan_loader.c **
   * TABLE_INSERT : 	5 pos id    child   nb_columns    {table_id| SKT_id} {0:SKT| 1:TABLE}   column_id1    value1      ...
   *
   * CI_INSERT 	:	6 pos id    child   ref_tab_id		key_tab_id		key_column_id	key		tuple_pos
   *
   * FLOW_X		: 	7 pos id	child1  child2
   *
   * ACCESS_RIGHTS:   8 pos id    child   access_type		in_res_UserDMSP		in_res_Formulaire	in_res_Episode
   * 							 access_type in {0=read, 1=update, 2=delete, 3=insert}
   * TABLE_DELETE:   9  pos id    child   table_id res
   * 
   * TABLE_UPDATE:   a  pos id    child   nb_col tab_id id_intern col1 value1 col2 value2
   *
   * CONST_LOOKUP:  e pos id child nb_cst cst_id1  cst_id2 ...
   *
   * META_RESULT 	:  	*     nb_columns    type_col1     out_result1 name_col1   ...
   *
  */


// COMMENT
static public final String COMMENT_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into comment values (?,?,?,?,?) */
"/*EP \u0005 5 0 0 -1 5 6 1 48 ?1 49 ?2 50 ?3 51 ?4 52 ?5 # \u0000*/";


static public final String COMMENT_NOAC_INSERT	=
/* SQL : 	Insert into comment values (?,?,?,?) */
"/*EP \u0004 5 0 0 -1 5 6 1 49 ?1 50 ?2 51 ?3 52 ?4 48 v30 # \u0000*/";


static public final String COMMENT_NOAC_SELECT_STAR =
/* SQL : 	select * from comment */
"/*EP \u0000 0 1 1 6 # 1 0 0 1 r0 5 6 1 48 49 50 51 52 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # \u0000*/";


// CONCEPT
static public final String CONCEPT_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into concept values (?,?,?,?,?,?,?) */
"/*EP \u0007 5 0 0 -1 7 3 1 23 ?1 24 ?2 25 ?3 26 ?4 27 ?5 28 ?6 29 ?7 # \u0000*/";


static public final String CONCEPT_NOAC_SELECT_STAR =
/* SQL : 	select * from concept */
"/*EP \u0000 0 1 1 3 # 1 0 0 1 r0 7 3 1 23 24 25 26 27 28 29 # \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 Unit 0 6 Nom 1 7 DataType # \u0000*/";


// EPISODE
static public final String EPISODE_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into episode values (?,?,?,?,?) */
"/*EP \u0005 5 0 0 -1 5 0 1 0 ?1 1 ?2 2 ?3 3 ?4 4 ?5 # \u0000*/";


static public final String EPISODE_NOAC_SELECT_STAR =
/* SQL : 	select * from episode */
"/*EP \u0000 0 1 1 0 # 1 0 0 1 r0 5 0 1 0 1 2 3 4 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";



// EVENT
static public final String EVENT_AC_INSERT	=
/* SQL : 	Insert into event values (?,?,?,?,?,?,?) */
"/*EP \u0007 0 7 7 1 # 1 6 6 7 r0 1 1 1 5 # 4 5 5 6 5 0 ?4 r1 # 0 10 10 2 # 1 9 9 10 r3 1 2 1 10 # 4 8 8 9 10 0 ?5 r4 # 7 4 4 5 8 # 0 13 13 0 # 1 12 12 13 r6 1 0 1 0 # 4 11 11 12 0 0 ?6 r7 # 7 3 3 4 11 # 8 2 2 3 3 r3 r0 r6 # 5 1 1 2 3 0 0 0 r0 1 r3 2 r6 # 5 0 0 1 8 4 1 31 ?1 32 ?2 33 ?3 34 ?4 35 ?5 36 ?6 37 ?7 30 v30 # \u0000*/";


static public final String EVENT_NOAC_INSERT_WITH_IDS =
/* SQL : 	Insert into event values (?,?,?,?,?,?,?,?) */
"/*EP \u0008 0 6 6 1 # 1 5 5 6 r0 1 1 1 5 # 4 4 4 5 5 0 ?5 r1 # 0 9 9 2 # 1 8 8 9 r3 1 2 1 10 # 4 7 7 8 10 0 ?6 r4 # 7 3 3 4 7 # 0 12 12 0 # 1 11 11 12 r6 1 0 1 0 # 4 10 10 11 0 0 ?7 r7 # 7 2 2 3 10 # 5 1 1 2 3 0 0 0 r0 1 r3 2 r6 # 5 0 0 1 8 4 1 30 ?1 31 ?2 32 ?3 33 ?4 34 ?5 35 ?6 36 ?7 37 ?8 # \u0000*/";


static public final String EVENT_NOAC_SELECT_STAR =
/* SQL : 	select * from event */
"/*EP \u0000 0 1 1 4 # 1 0 0 1 r0 8 4 1 30 31 32 33 34 35 36 37 # \u0000 8 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent # \u0000*/";


// FORMULAIRE
static public final String FORMULAIRE_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into formulaire values (?,?,?,?,?) */
"/*EP \u0005 5 0 0 -1 5 1 1 5 ?1 6 ?2 7 ?3 8 ?4 9 ?5 # \u0000*/";


static public final String FORMULAIRE_NOAC_SELECT_STAR =
/* SQL : 	select * from formulaire */
"/*EP \u0000 0 1 1 1 # 1 0 0 1 r0 5 1 1 5 6 7 8 9 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


public static final String SELECT_NOAC_FORM = 
/* SQL : 	SELECT nom, IdGlobal FROM formulaire */
"/*EP \u0000 0 1 1 1 # 1 0 0 1 r0 2 1 1 9 5 # \u0000 2 0 1 nom 1 2 IdGlobal # \u0000*/";



// HABILITATION
static public final String HABILITATION_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into habilitation values (?,?,?,?,?,?) */
"/*EP \u0006 0 5 5 7 # 1 4 4 5 r0 1 7 1 53 # 4 3 3 4 53 0 ?5 r1 # 0 8 8 2 # 1 7 7 8 r3 1 2 1 10 # 4 6 6 7 10 0 ?6 r4 # 7 2 2 3 6 # 5 1 1 2 2 2 0 0 r0 1 r3 # 5 0 0 1 6 8 1 58 ?1 59 ?2 60 ?3 61 ?4 62 ?5 63 ?6 # \u0000*/";


static public final String HABILITATION_NOAC_SELECT_STAR =
/* SQL : 	select * from habilitation */
"/*EP \u0000 0 1 1 8 # 1 0 0 1 r0 6 8 1 58 59 60 61 62 63 # \u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";


// INFO
static public final String INFO_AC_SELECT_STAR =
/* SQL : 	select * from info */
"/*EP \u0000 0 3 3 5 # 1 2 2 3 r0 3 1 0 2 1 3 # 8 1 1 2 0 r1 r2 r3 # 1 0 0 1 r0 10 5 1 38 39 40 41 42 43 44 45 46 47 # \u0000 10 1 4 IdGlobal 1 5 Author 1 6 TSSPT 1 7 TSSanteos 1 8 IdEvent 1 9 IdConcept 1 10 IdComment 0 11 ValChar 1 12 ValNum 1 13 Position # \u0000*/";


static public final String INFO_NOAC_INSERT =
/* SQL : 	Insert into info values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 0 9 9 4 # 1 8 8 9 r0 1 4 1 30 # 4 7 7 8 30 0 ?4 r1 # 1 6 6 7 r0 3 0 0 0 1 2 # 0 12 12 3 # 1 11 11 12 r6 1 3 1 23 # 4 10 10 11 23 0 ?5 r7 # 7 5 5 6 10 # 0 15 15 6 # 1 14 14 15 r9 1 6 1 48 # 4 13 13 14 48 0 ?6 r10 # 7 4 4 5 13 # 6 3 3 4 0 ?5 # 6 2 2 3 1 ?4 # 5 1 1 2 6 1 0 0 r0 1 r3 2 r4 3 r5 4 r6 5 r9 # 5 0 0 1 10 5 1 39 ?1 40 ?2 41 ?3 42 r13 43 r12 44 ?6 45 ?7 46 ?8 47 ?9 38 v30 # \u0000*/";


static public final String INFO_AC_INSERT	=
/* SQL : 	Insert into info values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 0 10 10 4 # 1 9 9 10 r0 1 4 1 30 # 4 8 8 9 30 0 ?4 r1 # 1 7 7 8 r0 3 0 0 0 1 2 # 8 6 6 7 3 r4 r3 r5 # 0 13 13 3 # 1 12 12 13 r6 1 3 1 23 # 4 11 11 12 23 0 ?5 r7 # 7 5 5 6 11 # 0 16 16 6 # 1 15 15 16 r9 1 6 1 48 # 4 14 14 15 48 0 ?6 r10 # 7 4 4 5 14 # 6 3 3 4 0 ?5 # 6 2 2 3 1 ?4 # 5 1 1 2 6 1 0 0 r0 1 r3 2 r4 3 r5 4 r6 5 r9 # 5 0 0 1 10 5 1 39 ?1 40 ?2 41 ?3 42 r13 43 r12 44 ?6 45 ?7 46 ?8 47 ?9 38 v30 # \u0000*/";


static public final String INFO_NOAC_INSERT_WITH_IDS =
/* SQL : 	Insert into info values (?,?,?,?,?,?,?,?,?,?) */
"/*EP \n 0 9 9 4 # 1 8 8 9 r0 1 4 1 30 # 4 7 7 8 30 0 ?5 r1 # 1 6 6 7 r0 3 0 0 0 1 2 # 0 12 12 3 # 1 11 11 12 r6 1 3 1 23 # 4 10 10 11 23 0 ?6 r7 # 7 5 5 6 10 # 0 15 15 6 # 1 14 14 15 r9 1 6 1 48 # 4 13 13 14 48 0 ?7 r10 # 7 4 4 5 13 # 6 3 3 4 0 ?6 # 6 2 2 3 1 ?5 # 5 1 1 2 6 1 0 0 r0 1 r3 2 r4 3 r5 4 r6 5 r9 # 5 0 0 1 10 5 1 38 ?1 39 ?2 40 ?3 41 ?4 42 r13 43 r12 44 ?7 45 ?8 46 ?9 47 ?10 # \u0000*/";


static public final String INFO_NOAC_SELECT_STAR =
/* SQL : 	select * from info */
"/*EP \u0000 0 1 1 5 # 1 0 0 1 r0 10 5 1 38 39 40 41 42 43 44 45 46 47 # \u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdConcept 1 7 IdComment 0 8 ValChar 1 9 ValNum 1 10 Position # \u0000*/";


// old schemaV3, with TupleDeleted, but QEPGenerator doesn't use this table, it's only for synchros 
static public final String INFO_NOAC_DELETE_BY_EVENT_ID =
/* SQL : 	DELETE from INFO where IdEvent = ? */
"/*EP \u0001 9 0 0 1 5 r7 # " + //
"5 1 1 2 3 12 1 85 v15 86 r7 87 v10 # " + //r0, r1 Insert Log Delete
"5 2 2 3 5 11 1 80 r5 81 r0 82 r3 83 r4 84 v10 # " + // r2
"e 3 3 4 2 1 2 # " + //const lookup r3 r4
"1 4 4 5 r7 1 5 1 38 # " + //r5
"2 5 5 -1 1 ?1 # \u0000*/"; // r6 r7


static public final String INFO_NOAC_UPDATE_Valchar_BY_ID =
/* SQL : 	update Info set ValChar = ? where Info.IdGlobal = ? */
"/*EP \u0002 " + 
"0 3 3 5 # " + /* SCAN, Table Info (5) -->R0 */
"1 2 2 3 r0 9 5 1 39 40 41 42 43 44 46 47 38 # " + /* TABLE_LOOKUP pos:R0, 9 cols, table Info(5), is_table: 1	-->R1 - R9 */
"4 1 1 2 38 0 ?2 r9 # " + /* SELECT, att: IdGlobal(38), comparator: 0, parameter: ?2 (R10), from pos: R9*/
"a 0 0 1 10 5 r0 38 ?2 39 r1 40 r2 41 r3 42 r4 43 r5 44 r6 45 ?1 46 r7 47 r8 # " + /* TABLE UPDATE, 10 cols, Table Info(5), tuple pos: r0 [col_id res_id] */
"\u0000*/";


// MATRICEDMSP
static public final String MATRICEDMSP_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into matriceDMSP values (?,?,?,?,?,?,?) */
"/*EP \u0007 0 5 5 1 # 1 4 4 5 r0 1 1 1 5 # 4 3 3 4 5 0 ?5 r1 # 0 8 8 7 # 1 7 7 8 r3 1 7 1 53 # 4 6 6 7 53 0 ?6 r4 # 7 2 2 3 6 # 5 1 1 2 2 3 0 0 r0 1 r3 # 5 0 0 1 7 9 1 64 ?1 65 ?2 66 ?3 67 ?4 68 ?5 69 ?6 70 ?7 # \u0000*/";


static public final String MATRICEDMSP_NOAC_SELECT_STAR =
/* SQL : 	select * from matriceDMSP */
"/*EP \u0000 0 1 1 9 # 1 0 0 1 r0 7 9 1 64 65 66 67 68 69 70 # \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdRole 1 7 Autorisations # \u0000*/";


// MATRICEPATIENT
static public final String MATRICEPATIENT_NOAC_INSERT_EPISODE_USER_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_EPISODE_ROLE_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_FORM_USER_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_FORM_ROLE_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_USER_USER_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_USER_ROLE_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_OTHER_USER_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 53 r11 # 4 10 10 11 10 0 r6 r10 # 1 11 11 12 r11 1 2 1 10 # 0 12 12 2 # \u0000*/";


static public final String MATRICEPATIENT_NOAC_INSERT_OTHER_ROLE_WITH_IDS	=
/* SQL : 	Insert into matricepatient values (?,?,?,?,?,?,?,?,?) */
"/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 53 r11 # 4 10 10 11 53 0 r6 r10 # 1 11 11 12 r11 1 7 1 53 # 0 12 12 7 # \u0000*/";



static public final String MATRICEPATIENT_NOAC_SELECT_STAR =
/* SQL : 	select * from matricepatient */
"/*EP \u0000 0 1 1 10 # 1 0 0 1 r0 9 10 1 71 72 73 74 75 76 77 78 79 # \u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";


// QUERY
static public final String QUERY_AC_SELECT_EVENT_BY_FORM   =
/* SQL : 	select e.DateEvent, e.IdGlobal, e.IdForm, e.IdUser, f.Nom from event e, formulaire f where f.IdGlobal = e.IdForm */
"/*EP \u0000 0 4 4 4 # 1 3 3 4 r0 3 0 0 1 0 2 # 8 2 2 3 0 r1 r2 r3 # 1 1 1 2 r0 3 4 1 37 30 35 # 1 0 0 1 r2 2 1 1 5 9 # \u0000 5 2 4 DateEvent 1 5 IdGlobal 1 7 IdForm 1 6 IdUser 0 8 Nom # \u0000*/";


static public final String QUERY_NOAC_SELECT_INFO_BY_EVENT   =
/* SQL : 	select * from info where IdEvent = ? */
"/*EP \u0001 2 1 1 -1 1 ?1 # 1 0 0 1 r1 9 5 1 38 39 40 41 43 44 45 46 47 # \u0000 10 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 1 0 IdEvent 1 6 IdConcept 1 7 IdComment 0 8 ValChar 1 9 ValNum 1 10 Position # \u0000*/";


static public final String SELECT_NOAC_FORM_EVENT_USER = 
/* SQL : 	SELECT formulaire.nom, event.dateEvent, userdmsp.nom FROM formulaire, userdmsp, event WHERE event.idUser=userdmsp.IdGlobal and event.idform=formulaire.IdGlobal */
"/*EP \u0000 0 4 4 4 # 1 3 3 4 r0 2 0 0 0 1 # 1 2 2 3 r1 1 1 1 9 # 1 1 1 2 r0 1 4 1 37 # 1 0 0 1 r2 1 2 1 14 # \u0000 3 0 3 nom 2 4 dateEvent 0 5 nom # \u0000*/";


public static final String SELECT_NOAC_EVENT_USER_BY_FORM = 
/* SQL : 	SELECT event.dateEvent, userdmsp.nom FROM userdmsp, event WHERE event.idUser=userdmsp.IdGlobal and event.idform=? */
"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 2 4 1 37 34 # 4 2 2 3 34 0 ?1 r2 # 1 1 1 2 r0 1 0 0 1 # 1 0 0 1 r4 1 2 1 14 # \u0000 2 2 1 dateEvent 0 5 nom # \u0000*/";


public static final String SELECT_NOAC_EVENT_USER_BY_FORM_2 = 
/* SQL : 	SELECT event.dateEvent, userdmsp.nom FROM userdmsp, event, formulaire WHERE event.idUser=userdmsp.IdGlobal and event.IdForm=formulaire.idglobal and formulaire.idglobal=? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 2 0 0 1 0 # 1 3 3 4 r2 1 1 1 5 # 4 2 2 3 5 0 ?1 r3 # 1 1 1 2 r0 1 4 1 37 # 1 0 0 1 r1 1 2 1 14 # \u0000 2 2 5 dateEvent 0 6 nom # \u0000*/";


public static final String SELECT_AC_EVENT_USER_BY_EVENT = 
/* SQL : 	SELECT userdmsp.nom, event.dateEvent FROM userdmsp, event, formulaire WHERE userdmsp.IdGlobal=event.IdUser and event.idform=formulaire.IdGlobal and event.IdGlobal=? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 2 4 1 37 30 # 4 3 3 4 30 0 ?1 r2 # 1 2 2 3 r0 3 0 0 1 0 2 # 8 1 1 2 0 r4 r5 r6 # 1 0 0 1 r4 1 2 1 14 # \u0000 2 0 7 nom 2 1 dateEvent # \u0000*/";


public static final String SELECT_AC_INFO_COMMENT_CONCEPT_BY_EVENT = 
/* SQL : 	SELECT i.IdGlobal, i.IdEvent, i.IdConcept, i.IdComment, i.Valchar, i.Valnum, i.Position, c.IdGlobal, c.ValComment, co.IdGlobal, co.Unit, co.Nom, co.dataType FROM info i, comment c, concept co WHERE i.IdConcept=co.IdGlobal and i.IdComment=c.IdGlobal and i.IdEvent=? */
"/*EP \u0001 " + 
"2 5 5 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:Info(5) key_col_id:42 ka_id:1	 key:?1(R0) -->R1 */
"1 4 4 5 r1 5 1 0 2 1 3 5 4 # " + /* TABLE_LOOKUP pos:R1, 5 cols, skt SktInfo(1), is_table: 0  -->R2 - R6 */
"8 3 3 4 0 r2 r3 r4 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R2 res_form:R3 res_epis:R4 */
"1 2 2 3 r1 6 5 1 38 43 44 45 46 47 # " + /* TABLE_LOOKUP pos:R1, 6 cols, table Info(5), is_table: 1	-->R7 - R12 */
"1 1 1 2 r5 2 6 1 48 52 # " + /* TABLE_LOOKUP pos:R5, 2 cols, table Comment(6), is_table: 1	-->R13 - R14 */
"1 0 0 1 r6 4 3 1 23 27 28 29 # " + /* TABLE_LOOKUP pos:R6, 4 cols, table Concept(3), is_table: 1	-->R15 - R18 */
"\u0000 13 1 7 IdGlobal 1 0 IdEvent 1 8 IdConcept 1 9 IdComment 0 10 Valchar 1 11 Valnum 1 12 Position 1 13 IdGlobal 0 14 ValComment 1 15 IdGlobal 1 16 Unit 0 17 Nom 1 18 dataType # " + /* META_RESULT, 13 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


// buggy when generated by QEPGenerator (fetch idcomment instead of idconcept, wtf?) 
public static final String SELECT_AC_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT = 
/* SQL : 	SELECT e.IdGlobal, e.IdForm, e.IdUser, e.IdEpisode, e.DateEvent, i.IdGlobal, i.IdEvent, i.IdConcept, i.IdComment, i.Valchar, i.Valnum, i.Position, c.IdGlobal, c.ValComment, co.IdGlobal, co.Unit, co.Nom, co.dataType FROM event e, info i, comment c, concept co WHERE i.Idevent=e.IdGlobal and i.IdConcept=co.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=? */
"/*EP \u0001 0 7 7 5 # " + //r0
"1 6 6 7 r0 6 1 0 0 1 2 3 4 5 # " + //r1-r6
"8 5 5 6 0 r3 r2 r4 # " +
"1 4 4 5 r1 6 4 1 30 34 35 36 37 30 # " + // r7-r12
"4 3 3 4 30 0 ?1 r12 # " + // r13
"1 2 2 3 r0 7 5 1 38 42 43 44 45 46 47 # " + // r14-r20
"1 1 1 2 r6 2 6 1 48 52 # " + // r21 r22
"1 0 0 1 r5 4 3 1 23 27 28 29 # " + // r23-r26
"\u0000 18 1 7 IdGlobal 1 8 IdForm 1 9 IdUser 1 10 IdEpisode 2 11 DateEvent 1 14 IdGlobal 1 15 IdEvent 1 16 IdConcept 1 17 IdComment 0 18 Valchar 1 19 Valnum 1 20 Position 1 21 IdGlobal 0 22 ValComment 1 23 IdGlobal 1 24 Unit 0 25 Nom 1 26 dataType # \u0000*/";


public static final String SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT = 
/* SQL : 	SELECT u.IdGlobal, u.nom, u.Prenom, u.sexe, u.adresse, u.ville, u.codepost, u.tel1, u.tel2, u.usertype, e.IdGlobal, e.IdForm, e.IdUser, e.IdEpisode, e.DateEvent, i.IdGlobal, i.IdEvent, i.IdConcept, i.IdComment, i.Valchar, i.Valnum, i.Position, c.IdGlobal, c.ValComment, co.IdGlobal, co.Unit, co.Nom, co.dataType FROM userdmsp u, event e, info i, comment c, concept co WHERE u.IdGlobal=e.IdUser and i.Idevent=e.IdGlobal and i.IdConcept=co.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=? */
"/*EP \u0001 " + 
"0 8 8 5 # " + /* SCAN, Table Info (5) -->R0 */
"1 7 7 8 r0 6 1 0 2 1 3 0 5 4 # " + /* TABLE_LOOKUP pos:R0, 6 cols, skt SktInfo(1), is_table: 0  -->R1 - R6 */
"8 6 6 7 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 5 5 6 r4 5 4 1 30 34 35 36 37 # " + /* TABLE_LOOKUP pos:R4, 5 cols, table Event(4), is_table: 1	-->R7 - R11 */
"4 4 4 5 30 0 ?1 r7 # " + /* SELECT, att: IdGlobal(30), comparator: 0, parameter: ?1 (R12), from pos: R7*/
"1 3 3 4 r1 10 2 1 10 14 15 16 17 18 19 20 21 22 # " + /* TABLE_LOOKUP pos:R1, 10 cols, table UserDMSP(2), is_table: 1	-->R13 - R22 */
"1 2 2 3 r0 7 5 1 38 42 43 44 45 46 47 # " + /* TABLE_LOOKUP pos:R0, 7 cols, table Info(5), is_table: 1	-->R23 - R29 */
"1 1 1 2 r5 2 6 1 48 52 # " + /* TABLE_LOOKUP pos:R5, 2 cols, table Comment(6), is_table: 1	-->R30 - R31 */
"1 0 0 1 r6 4 3 1 23 27 28 29 # " + /* TABLE_LOOKUP pos:R6, 4 cols, table Concept(3), is_table: 1	-->R32 - R35 */
"\u0000 28 1 13 IdGlobal 0 14 nom 0 15 Prenom 0 16 sexe 0 17 adresse 0 18 ville 0 19 codepost 0 20 tel1 0 21 tel2 1 22 usertype 1 7 IdGlobal 1 8 IdForm 1 9 IdUser 1 10 IdEpisode 2 11 DateEvent 1 23 IdGlobal 1 24 IdEvent 1 25 IdConcept 1 26 IdComment 0 27 Valchar 1 28 Valnum 1 29 Position 1 30 IdGlobal 0 31 ValComment 1 32 IdGlobal 1 33 Unit 0 34 Nom 1 35 dataType # " + /* META_RESULT, 28 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static final String SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE = 
/* SQL : 	SELECT u.IdGlobal, u.nom, u.Prenom, u.sexe, u.adresse, u.ville, u.codepost, u.tel1, u.tel2, u.usertype, e.IdGlobal, e.IdForm, e.IdUser, e.IdEpisode, e.DateEvent, i.IdGlobal, i.IdEvent, i.IdConcept, i.IdComment, i.Valchar, i.Valnum, i.Position, c.IdGlobal, c.ValComment, co.IdGlobal, co.Unit, co.Nom, co.dataType FROM userdmsp u, event e, info i, comment c, concept co WHERE u.idGlobal=e.Iduser and i.Idevent=e.IdGlobal and i.IdConcept=co.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=? AND e.dateEvent=? */
"/*EP \u0002 " + 
"0 9 9 5 # " + /* SCAN, Table Info (5) -->R0 */
"1 8 8 9 r0 6 1 0 2 1 3 0 5 4 # " + /* TABLE_LOOKUP pos:R0, 6 cols, skt SktInfo(1), is_table: 0  -->R1 - R6 */
"8 7 7 8 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 6 6 7 r4 5 4 1 30 34 35 36 37 # " + /* TABLE_LOOKUP pos:R4, 5 cols, table Event(4), is_table: 1	-->R7 - R11 */
"4 5 5 6 30 0 ?1 r7 # " + /* SELECT, att: IdGlobal(30), comparator: 0, parameter: ?1 (R12), from pos: R7*/
"4 4 4 5 37 0 ?2 r11 # " + /* SELECT, att: DateEvent(37), comparator: 0, parameter: ?2 (R13), from pos: R11*/
"1 3 3 4 r1 10 2 1 10 14 15 16 17 18 19 20 21 22 # " + /* TABLE_LOOKUP pos:R1, 10 cols, table UserDMSP(2), is_table: 1	-->R14 - R23 */
"1 2 2 3 r0 7 5 1 38 42 43 44 45 46 47 # " + /* TABLE_LOOKUP pos:R0, 7 cols, table Info(5), is_table: 1	-->R24 - R30 */
"1 1 1 2 r5 2 6 1 48 52 # " + /* TABLE_LOOKUP pos:R5, 2 cols, table Comment(6), is_table: 1	-->R31 - R32 */
"1 0 0 1 r6 4 3 1 23 27 28 29 # " + /* TABLE_LOOKUP pos:R6, 4 cols, table Concept(3), is_table: 1	-->R33 - R36 */
"\u0000 28 1 14 IdGlobal 0 15 nom 0 16 Prenom 0 17 sexe 0 18 adresse 0 19 ville 0 20 codepost 0 21 tel1 0 22 tel2 1 23 usertype 1 7 IdGlobal 1 8 IdForm 1 9 IdUser 1 10 IdEpisode 2 11 DateEvent 1 24 IdGlobal 1 25 IdEvent 1 26 IdConcept 1 27 IdComment 0 28 Valchar 1 29 Valnum 1 30 Position 1 31 IdGlobal 0 32 ValComment 1 33 IdGlobal 1 34 Unit 0 35 Nom 1 36 dataType # " + /* META_RESULT, 28 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static final String SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE_FORM = 
/* SQL : 	SELECT f.IdGlobal, f.nom, u.IdGlobal, u.nom, u.Prenom, u.sexe, u.adresse, u.ville, u.codepost, u.tel1, u.tel2, u.usertype, e.IdGlobal, e.IdForm, e.IdUser, e.IdEpisode, e.DateEvent, i.IdGlobal, i.IdEvent, i.IdConcept, i.IdComment, i.Valchar, i.Valnum, i.Position, c.IdGlobal, c.ValComment, co.IdGlobal, co.Unit, co.Nom, co.dataType FROM formulaire f, userdmsp u, event e, info i, comment c, concept co WHERE f.IdGlobal=e.IdForm and u.idGlobal=e.Iduser and i.Idevent=e.IdGlobal and i.IdConcept=co.IdGlobal and i.IdComment=c.IdGlobal and e.IdGlobal=? AND e.dateEvent=? and f.nom=? */
"/*EP \u0003 " + 
"0 11 11 5 # " + /* SCAN, Table Info (5) -->R0 */
"1 10 10 11 r0 6 1 0 2 1 3 0 5 4 # " + /* TABLE_LOOKUP pos:R0, 6 cols, skt SktInfo(1), is_table: 0  -->R1 - R6 */
"8 9 9 10 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 8 8 9 r4 5 4 1 30 34 35 36 37 # " + /* TABLE_LOOKUP pos:R4, 5 cols, table Event(4), is_table: 1	-->R7 - R11 */
"4 7 7 8 30 0 ?1 r7 # " + /* SELECT, att: IdGlobal(30), comparator: 0, parameter: ?1 (R12), from pos: R7*/
"4 6 6 7 37 0 ?2 r11 # " + /* SELECT, att: DateEvent(37), comparator: 0, parameter: ?2 (R13), from pos: R11*/
"1 5 5 6 r2 2 1 1 5 9 # " + /* TABLE_LOOKUP pos:R2, 2 cols, table Formulaire(1), is_table: 1	-->R14 - R15 */
"4 4 4 5 9 0 ?3 r15 # " + /* SELECT, att: Nom(9), comparator: 0, parameter: ?3 (R16), from pos: R15*/
"1 3 3 4 r1 10 2 1 10 14 15 16 17 18 19 20 21 22 # " + /* TABLE_LOOKUP pos:R1, 10 cols, table UserDMSP(2), is_table: 1	-->R17 - R26 */
"1 2 2 3 r0 7 5 1 38 42 43 44 45 46 47 # " + /* TABLE_LOOKUP pos:R0, 7 cols, table Info(5), is_table: 1	-->R27 - R33 */
"1 1 1 2 r5 2 6 1 48 52 # " + /* TABLE_LOOKUP pos:R5, 2 cols, table Comment(6), is_table: 1	-->R34 - R35 */
"1 0 0 1 r6 4 3 1 23 27 28 29 # " + /* TABLE_LOOKUP pos:R6, 4 cols, table Concept(3), is_table: 1	-->R36 - R39 */
"\u0000 30 1 14 IdGlobal 0 15 nom 1 17 IdGlobal 0 18 nom 0 19 Prenom 0 20 sexe 0 21 adresse 0 22 ville 0 23 codepost 0 24 tel1 0 25 tel2 1 26 usertype 1 7 IdGlobal 1 8 IdForm 1 9 IdUser 1 10 IdEpisode 2 11 DateEvent 1 27 IdGlobal 1 28 IdEvent 1 29 IdConcept 1 30 IdComment 0 31 Valchar 1 32 Valnum 1 33 Position 1 34 IdGlobal 0 35 ValComment 1 36 IdGlobal 1 37 Unit 0 38 Nom 1 39 dataType # " + /* META_RESULT, 30 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


// ROLE
static public final String ROLE_NOAC_INSERT_WITH_IDS	=
/* SQL : 	Insert into role values (?,?,?,?,?) */
"/*EP \u0005 5 0 0 -1 5 7 1 53 ?1 54 ?2 55 ?3 56 ?4 57 ?5 # \u0000*/";


static public final String ROLE_NOAC_SELECT_STAR =
/* SQL : 	select * from role */
"/*EP \u0000 0 1 1 7 # 1 0 0 1 r0 5 7 1 53 54 55 56 57 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


// USERDMSP
static public final String USERDMSP_NOAC_INSERT	=
/* SQL : 	Insert into userdmsp values (?,?,?,?,?,?,?,?,?,?,?,?) */
"/*EP \u000C 5 0 0 -1 13 2 1 11 ?1 12 ?2 13 ?3 14 ?4 15 ?5 16 ?6 17 ?7 18 ?8 19 ?9 20 ?10 21 ?11 22 ?12 10 v30 # \u0000*/";


// This one is manually written because QEPGen generates \xd that obviously will break the string. Replaced by \r 
static public final String USERDMSP_NOAC_INSERT_WITH_IDS =
/* SQL : 	Insert into userdmsp values (?,?,?,?,?,?,?,?,?,?,?,?,?) */
"/*EP \r 5 0 0 -1 13 2 1 10 ?1 11 ?2 12 ?3 13 ?4 14 ?5 15 ?6 16 ?7 17 ?8 18 ?9 19 ?10 20 ?11 21 ?12 22 ?13 # \u0000*/";


static public final String USERDMSP_NOAC_SELECT_STAR =
/* SQL : 	select * from userdmsp */
"/*EP \u0000 0 1 1 2 # 1 0 0 1 r0 13 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 # \u0000 13 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 0 6 Prenom 0 7 Sexe 0 8 Adresse 0 9 Ville 0 10 CodePost 0 11 Tel1 0 12 Tel2 1 13 UserType # \u0000*/";


// TupleDeleted
static public final String TupleDeleted_NOAC_SELECT_STAR	=
/* SQL : 	Select * from TupleDeleted */
"/*EP \u0000 " + 
"0 1 1 11 # " + /* SCAN, Table TupleDeleted (11) -->R0 */
"1 0 0 1 r0 5 11 1 80 81 82 83 84 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table TupleDeleted(11), is_table: 1	-->R1 - R5 */
"\u0000 5 1 1 IdGlobal 1 2 TabId 1 3 Author 1 4 TSSPT 1 5 TSSanteos # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


static public final String LogDeleted_NOAC_SELECT_STAR	=
/* SQL : 	Select * from LogDeleted */
"/*EP \u0000 " + 
"0 1 1 12 # " + /* SCAN, Table LogDeleted (12) -->R0 */
"1 0 0 1 r0 3 12 1 85 86 87 # " + /* TABLE_LOOKUP pos:R0, 3 cols, table LogDeleted(12), is_table: 1	-->R1 - R3 */
"\u0000 3 1 1 TabId 1 2 TuplePos 1 3 Flag # " + /* META_RESULT, 3 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


}
