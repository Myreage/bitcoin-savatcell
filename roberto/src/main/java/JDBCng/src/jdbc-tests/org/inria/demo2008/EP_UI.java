package org.inria.demo2008;

/**
 * Execution Plans for dmsp servlet
 */
public interface EP_UI {

  /* SELECT * from userDMSP where IdGlobal = ?*/
  public static final String EP_QUERY_NOAC_SELECT_USERDMSP_BY_ID = " /*EP 0 2 2 2 # 1 1 1 2 r0 13 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 # 4 0 0 1 10 0 ?1 r1 # \u0000 13 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 0 6 Prenom 0 7 Sexe 0 8 Adresse 0 9 Ville 0 10 CodePost 0 11 Tel1 0 12 Tel2 1 13 UserType # \u0000*/";

  // SELECT IdGlobal FROM Event WHERE IdForm = ?
  public static final String EP_QUERY_AC_SELECT_EVENT_ID_BY_FORM = "/*EP 0 4 4 4 # 1 3 3 4 r0 4 4 1 30 34 35 37 # 1 2 2 3 r0 3 0 0 0 1 2 # 4 1 1 2 34 0 ?1 r2 # 8 0 0 1 0 r6 r5 r7 # \u0000 1 1 1 IdGlobal # \u0000*/";
  public static final String EP_QUERY_NOAC_SELECT_EVENT_ID_BY_FORM =  "/*EP 0 2 2 4 # 1 1 1 2 r0 2 4 1 30 34 # 4 0 0 1 34 0 ?1 r2 # \u0000 1 1 1 IdGlobal # \u0000*/";

  // SELECT DateEvent,IdGlobal,IdForm,IdUser,Nom from event e, Formulaire f where f.idGlobal = e.idForm
  public static final String EP_QUERY_AC_SELECT_EVENT_BY_FORM = "/*EP 0 4 4 4 # 1 3 3 4 r0 4 4 1 30 34 35 37 # 1 2 2 3 r0 3 0 0 0 1 2 # 1 1 1 2 r5 1 1 1 9 # 8 0 0 1 0 r6 r5 r7 # \u0000 5 2 4 DateEvent 1 1 IdGlobal 1 2 IdForm 1 3 IdUser 0 8 Nom # \u0000*/";

  // SELECT c.Nom, c.DataType, i.ValChar, i.ValNum, o.ValComment from info i, concept c
  // where i.IdConcept=c.IdIntern and i.IdEvent=? and i.IdComment=o.IdGlobal
  public static final String EP_QUERY_AC_SELECT_INFO_BY_EVENT = "/*EP 0 6 6 5 # 1 5 5 6 r0 4 5 1 42 45 46 47 # 4 4 4 5 42 0 ?1 r1 # 1 3 3 4 r0 5 1 0 1 2 3 4 5 # 1 2 2 3 r9 2 3 1 28 29 # 1 1 1 2 r10 1 6 1 52 # 8 0 0 1 0 r7 r6 r8 # \u0000 5 0 11 Nom 1 12 DataType 0 2 ValChar 1 3 ValNum 0 13 ValComment # \u0000*/";
  public static final String EP_QUERY_NOAC_SELECT_INFO_BY_EVENT = "/*EP 2 1 1 -1 5 42 ?1 # 1 0 0 1 r1 10 5 1 38 39 40 41 42 43 44 45 46 47 # \u0000 10 1 2 IdGlobal 1 3 Author 1 4 TSSPT 1 5 TSSanteos 1 6 IdEvent 1 7 IdConcept 1 8 IdComment 0 9 ValChar 1 10 ValNum 1 11 Position # \u0000*/";

  // SELECT IdGlobal, DataType FROM CONCEPT WHERE Nom=?
  public static final String EP_QUERY_AC_SELECT_CONCEPT_BY_NOM = "/*EP 0 2 2 3 # 1 1 1 2 r0 3 3 1 23 28 29 # 4 0 0 1 28 0 ?1 r2 # \u0000 2 1 1 IdGlobal 1 3 DataType # \u0000*/";

  // SELECT IdGlobal from formulaire where Nom=?
  public static final String EP_FORMULAIRE_AC_SELECT_ID_WHERE_NOM = "/*EP 0 2 2 1 # 1 1 1 2 r0 2 1 1 5 9 # 4 0 0 1 9 0 ?1 r2 # \u0000 1 1 1 IdGlobal # \u0000*/";

  // SELECT IdGlobal, Nom from episode
  public static final String EP_QUERY_SELECT_EPISODE = "/*EP 1 0 0 1 r2 2 0 1 0 4 # 0 1 1 0 # \u0000 2 1 0 IdGlobal 0 1 Nom # \u0000*/";

  // SELECT IdGlobal, ValComment from comment
  public static final String EP_QUERY_SELECT_COMMENT = "/*EP 1 0 0 1 r2 2 6 1 48 52 # 0 1 1 6 # \u0000 2 1 0 IdGlobal 0 1 ValComment # \u0000*/";

  // INSERT/UPDATE/DELETE into event values (?,?,?,?,?,?,?,?,?)
  public static final String EP_EVENT_AC_INSERT = "/*EP 5 0 0 1 8 4 1 30 ?1 31 ?2 32 ?3 33 ?4 34 ?5 35 ?6 36 ?7 37 ?8 # 5 1 1 2 3 0 0 5 r9 10 r11 0 r13 # 8 2 2 3 3 r11 r9 r13 # 7 3 3 4 5 # 7 4 4 8 11 # 4 5 5 6 5 0 r4 r8 # 1 6 6 7 r9 1 1 1 5 # 0 7 7 1 # 4 8 8 9 10 0 r5 r10 # 1 9 9 10 r11 1 2 1 10 # 0 10 10 2 # 4 11 11 12 0 0 r6 r12 # 1 12 12 13 r13 1 0 1 0 # 0 13 13 0 # \u0000*/";
  public static final String OEP_EVENT_AC_INSERT = "/*EP 5 0 0 1 8 4 1 30 v30 31 ?1 32 ?2 33 v10 34 ?3 35 ?4 36 ?5 37 ?6 # 5 1 1 2 3 0 0 5 r9 10 r11 0 r13 # 8 2 2 3 3 r11 r9 r13 # 7 3 3 4 5 # 7 4 4 8 11 # 4 5 5 6 5 0 r4 r8 # 1 6 6 7 r9 1 1 1 5 # 0 7 7 1 # 4 8 8 9 10 0 r5 r10 # 1 9 9 10 r11 1 2 1 10 # 0 10 10 2 # 4 11 11 12 0 0 r6 r12 # 1 12 12 13 r13 1 0 1 0 # 0 13 13 0 # \u0000*/";
  public static final String EP_EVENT_AC_UPDATE = null;// TODO
  public static final String EP_EVENT_AC_DELETE = null;// TODO

  // INSERT/UPDATE/DELETE into info values (?,?,?,?,?,?,?,?,?,?,?)
  public static final String EP_INFO_AC_INSERT = "/*EP 5 0 0 1 10 5 1 38 ?1 39 ?2 40 ?3 41 ?4 42 ?5 43 ?6 44 ?7 45 ?8 46 ?9 47 ?10 # 6 1 1 2 5 5 42 r4 t5 # 6 2 2 3 5 5 43 r5 t5 # 5 3 3 4 6 1 0 30 r16 5 r12 10 r13 0 r14 23 r18 48 r20 # 8 4 4 5 3 r13 r12 r14 # 7 5 5 13 10 # 7 13 13 6 14 # 1 6 6 7 r16 3 0 0 0 1 2 # 4 7 7 8 30 0 r4 r15 # 1 8 8 9 r16 1 4 1 30 # 0 9 9 4 # 4 10 10 11 23 0 r5 r17 # 1 11 11 12 r18 1 3 1 23 # 0 12 12 3 # 4 14 14 15 48 0 r6 r19 # 1 15 15 16 r20 1 6 1 48 # 0 16 16 6 # \u0000*/";
  public static final String OEP_INFO_AC_INSERT = "/*EP 5 0 0 1 10 5 1 38 v30 39 ?1 40 ?2 41 v10 42 ?3 43 ?4 44 ?5 45 ?6 46 ?7 47 ?8 # 6 1 1 2 5 5 42 r4 t5 # 6 2 2 3 5 5 43 r5 t5 # 5 3 3 4 6 1 0 30 r16 5 r12 10 r13 0 r14 23 r18 48 r20 # 8 4 4 5 3 r13 r12 r14 # 7 5 5 13 10 # 7 13 13 6 14 # 1 6 6 7 r16 3 0 0 0 1 2 # 4 7 7 8 30 0 r4 r15 # 1 8 8 9 r16 1 4 1 30 # 0 9 9 4 # 4 10 10 11 23 0 r5 r17 # 1 11 11 12 r18 1 3 1 23 # 0 12 12 3 # 4 14 14 15 48 0 r6 r19 # 1 15 15 16 r20 1 6 1 48 # 0 16 16 6 # \u0000*/";
  public static final String EP_INFO_AC_UPDATE = null;// TODO
  public static final String EP_INFO_AC_DELETE = null;// TODO

  // INSERT/UPDATE/DELETE into userdmsp values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
  public static final String EP_USERDMSP_AC_INSERT = "/*EP 5 0 0 -1 13 2 1 10 ?1 11 ?2 12 ?3 13 ?4 14 ?5 15 ?6 16 ?7 17 ?8 18 ?9 19 ?10 20 ?11 21 ?12 22 ?13 # \u0000*/";
  public static final String EP_USERDMSP_AC_UPDATE = null;// TODO
  public static final String EP_USERDMSP_AC_DELETE = null;// TODO

  // INSERT/UPDATE/DELETE into comment values (?,?,?,?,?)
  public static final String EP_COMMENT_AC_INSERT = "/*EP 5 0 0 -1 5 6 1 48 ?1 49 ?2 50 ?3 51 ?4 52 ?5 # \u0000*/";
  public static final String OEP_COMMENT_AC_INSERT = "/*EP 5 0 0 -1 5 6 1 48 v30 49 ?1 50 ?2 51 v10 52 ?3 # \u0000*/";
  public static final String EP_COMMENT_AC_UPDATE = null;// TODO
  public static final String EP_COMMENT_AC_DELETE = null;// TODO
}
