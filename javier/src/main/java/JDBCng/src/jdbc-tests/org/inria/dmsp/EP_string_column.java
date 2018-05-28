package org.inria.dmsp;

public class EP_string_column
{
	public static String EP_EPISODE_INSERT =
			/* SQL : 	INSERT INTO Episode VALUES (?,?,?,?,?) */
			"/*EP \u0005 " + 
			"6 1 1 -1 0 ?1 # " + /* CI_INSERT ref_tab:Episode(0), key_tab:Episode(0), col:IdGlobal(0), ka_id:0, key:?1(R0) */
			"5 0 0 1 5 0 1 0 r0 1 ?2 2 ?3 3 ?4 4 ?5 # " + /* TABLE_INSERT 5 cols, table Episode(0), is_table: 1, [col_id value] */
			"\u0000*/";

	public static String EP_EPISODE_SELECT =
		/* SQL : 	SELECT * FROM Episode */
		"/*EP \u0000 " + 
		"0 1 1 0 # " + /* SCAN, Table Episode (0) -->R0 */
		"1 0 0 1 r0 5 0 1 0 1 2 3 4 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table Episode(0), is_table: 1	-->R1 - R5 */
		"\u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
		"\u0000*/";

	public static String EP_EPISODE_UPDATE =
		/* SQL : 	UPDATE Episode SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ? WHERE IdGlobal = ? */
		"/*EP \u0005 " + 
		"2 1 1 -1 0 ?5 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?5(R0) -->R1 */
		"a 0 0 1 5 0 r1 0 ?5 1 ?1 2 ?2 3 ?3 4 ?4 # " + /* TABLE UPDATE, 5 cols, Table Episode(0), tuple pos: r1 [col_id res_id] */
		"\u0000*/";

	public static String EP_EPISODE_DELETE =
		/* SQL : 	DELETE FROM Episode WHERE IdGlobal = ? */
		"/*EP \u0001 " + 
		"2 2 2 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
		"5 1 1 2 3 12 1 85 v10 86 r1 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 0 r1 # " + /* TABLE DELETE, table Episode(0), tuple pos: R1 */
		"\u0000*/";
}
