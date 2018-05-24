package org.inria.dmsp;


public class EP_IDX {


 /*

  * PLANS TO BENCHMARK SEQUENTIAL INDEXES

  * EP_<attribute>_<index type>

  */

//  Populate MyTable 

public static String EP_POPULATE =
/* SQL : 	INSERT INTO MyTable VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); */
"/*EP \n " + 
"6 9 9 -1 0 ?1 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:IdGlobal(0), ka_id:0, key:?1(R0) */
"6 8 8 9 1 ?2 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:NumDup100(1), ka_id:1, key:?2(R1) */
"6 7 7 8 2 ?3 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:NumDup10(2), ka_id:2, key:?3(R2) */
"6 6 6 7 3 ?4 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:NumMs1(3), ka_id:3, key:?4(R3) */
"6 5 5 6 4 ?5 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:NumMs10(4), ka_id:4, key:?5(R4) */
"6 4 4 5 5 ?6 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:ChDup100(5), ka_id:5, key:?6(R5) */
"6 3 3 4 6 ?7 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:ChDup10(6), ka_id:6, key:?7(R6) */
"6 2 2 3 7 ?8 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:ChMs1(7), ka_id:7, key:?8(R7) */
"6 1 1 2 8 ?9 # " + /* CI_INSERT ref_tab:MyTable(0), key_tab:MyTable(0), col:ChMs10(8), ka_id:8, key:?9(R8) */
"5 0 0 1 10 0 1 0 r0 1 r1 2 r2 3 r3 4 r4 5 r5 6 r6 7 r7 8 r8 9 ?10 # " + /* TABLE_INSERT 10 cols, table MyTable(0), is_table: 1, [col_id value] */
"\u0000*/";


//  Select IdGlobal 

public static String EP_IDGLOBAL_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE IdGlobal = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 0 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 0 0 ?1 r2 # " + /* SELECT, att: IdGlobal(0), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_IDGLOBAL_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE IdGlobal = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_IDGLOBAL_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE IdGlobal = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_IDGLOBAL_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE IdGlobal = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select NumDup100 

public static String EP_NUMDUP100_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup100 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 1 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 1 0 ?1 r2 # " + /* SELECT, att: NumDup100(1), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP100_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup100 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:1 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP100_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup100 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:1 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP100_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup100 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 1 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:1 ka_id:1	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select NumDup10 

public static String EP_NUMDUP10_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup10 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 2 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 2 0 ?1 r2 # " + /* SELECT, att: NumDup10(2), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP10_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup10 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 2 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:2 ka_id:2	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP10_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup10 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 2 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:2 ka_id:2	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMDUP10_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumDup10 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 2 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:2 ka_id:2	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select NumMs1 

public static String EP_NUMMS1_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs1 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 3 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 3 0 ?1 r2 # " + /* SELECT, att: NumMs1(3), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS1_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs1 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 3 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:3 ka_id:3	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS1_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs1 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 3 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:3 ka_id:3	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS1_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs1 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 3 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:3 ka_id:3	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select NumMs10 

public static String EP_NUMMS10_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs10 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 4 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 4 0 ?1 r2 # " + /* SELECT, att: NumMs10(4), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS10_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs10 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 4 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:4 ka_id:4	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS10_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs10 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 4 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:4 ka_id:4	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_NUMMS10_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE NumMs10 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 4 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:4 ka_id:4	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select ChDup100 

public static String EP_CHDUP100_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup100 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 5 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 5 0 ?1 r2 # " + /* SELECT, att: ChDup100(5), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP100_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup100 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 5 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:5 ka_id:5	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP100_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup100 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 5 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:5 ka_id:5	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP100_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup100 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 5 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:5 ka_id:5	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select ChDup10 

public static String EP_CHDUP10_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup10 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 6 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 6 0 ?1 r2 # " + /* SELECT, att: ChDup10(6), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP10_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup10 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 6 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:6 ka_id:6	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP10_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup10 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 6 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:6 ka_id:6	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHDUP10_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChDup10 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 6 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:6 ka_id:6	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select ChMs1 

public static String EP_CHMS1_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs1 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 7 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 7 0 ?1 r2 # " + /* SELECT, att: ChMs1(7), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS1_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs1 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 7 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:7 ka_id:7	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS1_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs1 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 7 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:7 ka_id:7	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS1_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs1 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 7 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:7 ka_id:7	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Select ChMs10 

public static String EP_CHMS10_NO =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs10 = ?; */
"/*EP \u0001 " + 
"0 2 2 0 # " + /* SCAN, Table MyTable (0) -->R0 */
"1 1 1 2 r0 2 0 1 9 8 # " + /* TABLE_LOOKUP pos:R0, 2 cols, table MyTable(0), is_table: 1	-->R1 - R2 */
"4 0 0 1 8 0 ?1 r2 # " + /* SELECT, att: ChMs10(8), comparator: 0, parameter: ?1 (R3), from pos: R2*/
"\u0000 1 0 1 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS10_FIS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs10 = ?; */
"/*EP \u0001 " + 
"g 1 1 -1 8 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:8 ka_id:8	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS10_SS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs10 = ?; */
"/*EP \u0001 " + 
"h 1 1 -1 8 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:8 ka_id:8	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_CHMS10_HS =
/* SQL : 	SELECT Something FROM MyTable WHERE ChMs10 = ?; */
"/*EP \u0001 " + 
"2 1 1 -1 8 ?1 # " + /* CI_LOOKUP ref_tab:MyTable(0) key_col_id:8 ka_id:8	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


//  Fake 

public static String EP_FAKE =
"/*EP \u0001 " + 
"z 1 1 -1 4 ?1 # " + /* FAKE_LOOKUP ref_tab:MyTable(0) key_col_id:4 ka_id:4	 key:?1(R0) -->R1 */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


public static String EP_FAKE_DUMMY =
"/*EP \u0001 " + 
"z 2 2 -1 4 ?1 # " + /* FAKE_LOOKUP ref_tab:MyTable(0) key_col_id:4 ka_id:4	 key:?1(R0) -->R1 */
"q 1 1 2 # " + /* DUMMY */
"1 0 0 1 r1 1 0 1 9 # " + /* TABLE_LOOKUP pos:R1, 1 cols, table MyTable(0), is_table: 1	-->R2 - R2 */
"\u0000 1 0 2 Something # " + /* META_RESULT, 1 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


}
