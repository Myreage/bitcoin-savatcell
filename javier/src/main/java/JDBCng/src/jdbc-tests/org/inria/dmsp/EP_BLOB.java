package org.inria.dmsp;

public class EP_BLOB
{
	public static String EP_EPISODE_INSERT =
		/* SQL : 	Insert into episode values (?,?,?,?,?,?) */
		"/*EP \u0006 " +
		"6 1 1 -1 0 ?1 # " + /* CI_INSERT ref_tab:Episode(0), key_tab:Episode(0), col:IdGlobal(0), ka_id:0, key:?1(R0) */
		"5 0 0 1 6 0 1 0 r0 1 ?2 2 ?3 3 ?4 4 ?5 97 ?6 # " + /* TABLE_INSERT 6 cols, table Episode(0), is_table: 1, [col_id value] */
		"\u0000*/";
	public static String EP_EPISODE_SELECT_BY_ID =
		/* SQL : 	SELECT * FROM Episode WHERE IdGlobal = ? */
		"/*EP \u0001 " +
		"2 1 1 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
		"1 0 0 1 r1 5 0 1 1 2 3 4 97 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table Episode(0), is_table: 1	-->R2 - R6 */
		"\u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 9 6 Image # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
		"\u0000*/";
	public static String EP_EPISODE_UPDATE =
		/* SQL : 	UPDATE Episode SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ?, Image = ? WHERE IdGlobal = ? */
		"/*EP \u0006 " +
		"2 1 1 -1 0 ?6 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?6(R0) -->R1 */
		"a 0 0 1 6 0 r1 0 ?6 1 ?1 2 ?2 3 ?3 4 ?4 97 ?5 # " + /* TABLE UPDATE, 6 cols, Table Episode(0), tuple pos: r1 [col_id res_id] */
		"\u0000*/";
	public static String EP_EPISODE_DELETE =
		/* SQL : 	DELETE FROM Episode WHERE IdGlobal = ? */
		"/*EP \u0001 " +
		"2 2 2 -1 0 ?1 # " + /* CI_LOOKUP ref_tab:Episode(0) key_col_id:0 ka_id:0	 key:?1(R0) -->R1 */
		"5 1 1 2 3 12 1 85 v10 86 r1 87 v10 # " + /* TABLE_INSERT 3 cols, table LogDeleted(12), is_table: 1, [col_id value] -->R2 - R3*/
		"9 0 0 1 0 r1 # " + /* TABLE DELETE, table Episode(0), tuple pos: R1 */
		"\u0000*/";
}
