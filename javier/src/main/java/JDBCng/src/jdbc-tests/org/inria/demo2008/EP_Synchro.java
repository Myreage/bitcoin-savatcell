package org.inria.demo2008;

/**
 * Execution Plans for synchronization algorithm
 * ALL EPs are without access control (NOAC)
 */
public interface EP_Synchro {

  // Insert into episode values (?,?,?,?,?)
  String EP_EPISODE_INSERT = "/*EP 5 0 0 -1 5 0 1 0 ?1 1 ?2 2 ?3 3 ?4 4 ?5 # \u0000*/";
  // Select from episode where IdGlobal = ?
  String EP_EPISODE_SELECT_BY_ID =
    "/*EP 0 2 2 0 # " +
    "1 1 1 2 r0 5 0 1 0 1 2 3 4 # " +
    "4 0 0 1 0 0 ?1 r1 # " +
    " \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // Select * from episode where TSSPT > ?
  String EP_EPISODE_SELECT_ON_TSSPT = "/*EP 4 0 0 1 2 1 ?1 r3 # 1 1 1 2 r6 5 0 1 0 1 2 3 4 # 0 2 2 0 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // Update episode SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_EPISODE_UPDATE = null; // TODO

  // Insert into formulaire values (?,?,?,?,?)
  String EP_FORMULAIRE_INSERT = "/*EP 5 0 0 -1 5 1 1 5 ?1 6 ?2 7 ?3 8 ?4 9 ?5 # \u0000*/";
  // Select from formulaire where IdGlobal = ?
  String EP_FORMULAIRE_SELECT_BY_ID =
    "/*EP 0 2 2 1 # " +
    "1 1 1 2 r0 5 1 1 5 6 7 8 9 # " +
    "4 0 0 1 5 0 ?1 r1 # " +
    " \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // Select * from formulaire where TSSPT > ?
  String EP_FORMULAIRE_SELECT_ON_TSSPT = "/*EP 4 0 0 1 7 1 ?1 r3 # 1 1 1 2 r6 5 1 1 5 6 7 8 9 # 0 2 2 1 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // Update formulaire SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_FORMULAIRE_UPDATE = null; // TODO

  // Insert into userdmsp values (?,?,?,?,?,?,?,?,?,?,?,?,?)
  String EP_USER_INSERT = "/*EP 5 0 0 -1 13 2 1 10 ?1 11 ?2 12 ?3 13 ?4 14 ?5 15 ?6 16 ?7 17 ?8 18 ?9 19 ?10 20 ?11 21 ?12 22 ?13 # \u0000*/";
  // Select from userdmsp where IdGlobal = ?
  String EP_USER_SELECT_BY_ID =
    " /*EP 0 2 2 2 # " +
    "1 1 1 2 r0 13 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 # " +
    "4 0 0 1 10 0 ?1 r1 # " +
    " \u0000 13 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 0 6 Prenom 0 7 Sexe 0 8 Adresse 0 9 Ville 0 10 CodePost 0 11 Tel1 0 12 Tel2 1 13 UserType # \u0000*/";
  // Select * from userdmsp where TSSPT > ?
  String EP_USER_SELECT_ON_TSSPT = "/*EP 0 2 2 2 # 1 1 1 2 r0 13 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 # 4 0 0 1 12 1 ?1 r3 # \u0000 13 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 0 6 Prenom 0 7 Sexe 0 8 Adresse 0 9 Ville 0 10 CodePost 0 11 Tel1 0 12 Tel2 1 13 UserType # \u0000*/";
  // Update userdmsp SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_USER_UPDATE = null; // TODO

  // Insert into concept values (?,?,?,?,?,?,?)
  String EP_CONCEPT_INSERT = "/*EP 5 0 0 -1 7 3 1 23 ?1 24 ?2 25 ?3 26 ?4 27 ?5 28 ?6 29 ?7 # \u0000*/";
  // Select from concept where IdGlobal = ?
  String EP_CONCEPT_SELECT_BY_ID =
    "/*EP 0 2 2 3 # " +
    "1 1 1 2 r0 7 3 1 23 24 25 26 27 28 29 # " +
    "4 0 0 1 23 0 ?1 r1 # " +
    " \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 Unit 0 6 Nom 1 7 DataType # \u0000*/";
  // Select * from concept where TSSPT > ?
  String EP_CONCEPT_SELECT_ON_TSSPT = "/*EP 4 0 0 1 25 1 ?1 r3 # 1 1 1 2 r8 7 3 1 23 24 25 26 27 28 29 # 0 2 2 3 # \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 Unit 0 6 Nom 1 7 DataType # \u0000*/";
  // Update concept SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_CONCEPT_UPDATE = null; // TODO

  // Insert into event values (?,?,?,?,?,?,?,?,?)
  String EP_EVENT_INSERT = "/*EP 5 0 0 1 8 4 1 30 ?1 31 ?2 32 ?3 33 ?4 34 ?5 35 ?6 36 ?7 37 ?8 #  5 1 1 2 3 0 0 5 r9 10 r11 0 r13 #  7 2 2 3 4 #  7 3 3 7 10 #  4 4 4 5 5 0 r4 r8 #  1 5 5 6 r9 1 1 1 5 # 0 6 6 1 # 4 7 7 8 10 0 r5 r10 #  1 8 8 9 r11 1 2 1 10 #  0 9 9 2 #  4 10 10 11 0 0 r6 r12 #  1 11 11 12 r13 1 0 1 0 #  0 12 12 0 # \u0000*/";
  // Select from event where IdGlobal = ?
  String EP_EVENT_SELECT_BY_ID =
    "/*EP 0 2 2 4 # " +
    "1 1 1 2 r0 8 4 1 30 31 32 33 34 35 36 37 # " +
    "4 0 0 1 30 0 ?1 r1 # " +
    " \u0000 8 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent # \u0000*/";
  // Select * from event where TSSPT > ?
  String EP_EVENT_SELECT_ON_TSSPT = "/*EP 0 2 2 4 # 1 1 1 2 r0 8 4 1 30 31 32 33 34 35 36 37 # 4 0 0 1 32 1 ?1 r3 # \u0000 8 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent # \u0000*/";
  // Update event SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_EVENT_UPDATE = null; // TODO

  // Insert into info values (?,?,?,?,?,?,?,?,?,?)
  String EP_INFO_INSERT = "/*EP 5 0 0 1 10 5 1 38 ?1 39 ?2 40 ?3 41 ?4 42 ?5 43 ?6 44 ?7 45 ?8 46 ?9 47 ?10 # 6 1 1 2 5 5 42 r4 t5 # 6 2 2 3 5 5 43 r5 t5 # 5 3 3 4 6 1 0 30 r16 5 r12 10 r13 0 r14 23 r18 48 r20 # 7 4 4 12 9 # 7 12 12 5 13 # 1 5 5 6 r16 3 0 0 0 1 2 #  4 6 6 7 30 0 r4 r15 # 1 7 7 8 r16 1 4 1 30 # 0 8 8 4 #  4 9 9 10 23 0 r5 r17 #  1 10 10 11 r18 1 3 1 23 #  0 11 11 3 # 4 13 13 14 48 0 r6 r19 #  1 14 14 15 r20 1 6 1 48 #  0 15 15 6 # \u0000*/";
  // Select from info where IdGlobal = ?
  String EP_INFO_SELECT_BY_ID =
    "/*EP 0 2 2 5 # " +
    "1 1 1 2 r0 10 5 1 38 39 40 41 42 43 44 45 46 47 # " +
    "4 0 0 1 38 0 ?1 r1 # " +
    " \u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdConcept 1 7 IdComment 0 8 ValChar 1 9 ValNum 1 10 Position # \u0000*/";
  // Select * from info where TSSPT > ?
  String EP_INFO_SELECT_ON_TSSPT = "/*EP 0 2 2 5 # 1 1 1 2 r0 10 5 1 38 39 40 41 42 43 44 45 46 47 # 4 0 0 1 40 1 ?1 r3 # \u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdConcept 1 7 IdComment 0 8 ValChar 1 9 ValNum 1 10 Position # \u0000*/";
  // Update info SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_INFO_UPDATE = null; // TODO

  // Insert into comment values (?,?,?,?,?)
  String EP_COMMENT_INSERT = "/*EP 5 0 0 -1 5 6 1 48 ?1 49 ?2 50 ?3 51 ?4 52 ?5 # \u0000*/";
  // Select from comment where IdGlobal = ?
  String EP_COMMENT_SELECT_BY_ID =
    "/*EP 0 2 2 6 # " +
    "1 1 1 2 r0 5 6 1 48 49 50 51 52 # " +
    "4 0 0 1 48 0 ?1 r1 # " +
    " \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # \u0000*/";
  // Select * from comment where TSSPT > ?
  String EP_COMMENT_SELECT_ON_TSSPT = "/*EP 4 0 0 1 50 1 ?1 r3 # 1 1 1 2 r6 5 6 1 48 49 50 51 52 # 0 2 2 6 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # \u0000*/";
  // Update comment SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_COMMENT_UPDATE = null; // TODO

  // Insert into role values (?,?,?,?,?)
  String EP_ROLE_INSERT = "/*EP 5 0 0 -1 5 7 1 53 ?1 54 ?2 55 ?3 56 ?4 57 ?5 # \u0000*/";
  // Select from role where IdGlobal = ?
  String EP_ROLE_SELECT_BY_ID =
    "/*EP 0 2 2 7 # " +
    "1 1 1 2 r0 5 7 1 53 54 55 56 57 # " +
    "4 0 0 1 53 0 ?1 r1 # " +
    " \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // SELECT * FROM role WHERE TSSPT > ?
  String EP_ROLE_SELECT_ON_TSSPT = "/*EP 4 0 0 1 55 1 ?1 r3 # 1 1 1 2 r6 5 7 1 53 54 55 56 57 # 0 2 2 7 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";
  // Update role SET (?,?,?,?) WHERE IdGlobal = ?
  String EP_ROLE_UPDATE = null; // TODO

  // Insert into habilitation values (?,?,?,?,?,?,?,?)
  String EP_HABILITATION_INSERT = "/*EP 5 0 0 1 6 8 1 58 ?1 59 ?2 60 ?3 61 ?4 62 ?5 63 ?6 # 5 1 1 2 2 2 0 53 r7 10 r9 # 7 2 2 10 4 # 4 4 4 5 53 0 r4 r6 # 1 5 5 6 r7 1 7 1 53 # 0 6 6 7 # 4 10 10 11 10 0 r5 r8 # 1 11 11 12 r9 1 2 1 10 # 0 12 12 2 # \u0000*/";
  // Select from habilitation where IdGlobal = ?
  String EP_HABILITATION_SELECT_BY_ID =
    "/*EP 0 2 2 8 # " +
    "1 1 1 2 r0 6 8 1 58 59 60 61 62 63 # " +
    "4 0 0 1 58 0 ?1 r1 # " +
    " \u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";
  // select * from habilitation where TSSPT > ?
  String EP_HABILITATION_SELECT_ON_TSSPT = "/*EP 4 0 0 1 60 1 ?1 r3 # 1 1 1 2 r7 6 8 1 58 59 60 61 62 63 # 0 2 2 8 # \u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";
  // Update habilitation SET (?,?,?,?,?,?,?) WHERE IdGlobal = ?
  String EP_HABILITATION_UPDATE = null; // TODO

  // Insert into matriceDMSP values (?,?,?,?,?,?,?,?,?)
  String EP_DMSP_INSERT = "/*EP 5 0 0 1 7 9 1 64 ?1 65 ?2 66 ?3 67 ?4 68 ?5 69 ?6 70 ?7 # 5 1 1 2 2 3 0 5 r8 53 r10 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r7 # 1 5 5 6 r8 1 1 1 5 # 0 6 6 1 # 4 10 10 11 53 0 r5 r9 # 1 11 11 12 r10 1 7 1 53 # 0 12 12 7 # \u0000*/";
  // Select from matriceDMSP where IdGlobal = ?
  String EP_DMSP_SELECT_BY_ID =
    "/*EP 0 2 2 9 # " +
    "1 1 1 2 r0 7 9 1 64 65 66 67 68 69 70 # " +
    "4 0 0 1 64 0 ?1 r1 # " +
    " \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdRole 1 7 Autorisations # \u0000*/";
  // Select * from matriceDMSP where TSSPT > ?
  String EP_DMSP_SELECT_ON_TSSPT = "/*EP 0 2 2 9 # 1 1 1 2 r0 7 9 1 64 65 66 67 68 69 70 # 4 0 0 1 66 1 ?1 r3 # \u0000 7 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdRole 1 7 Autorisations # \u0000*/";
  // Update matriceDMSP SET (?,?,?,?,?,?,?,?) WHERE IdGlobal = ?
  String EP_DMSP_UPDATE = null; // TODO

  // there are multiple insert EPs, selection is done in tupleInsert below
  //---- Multiple INSERTS ----
  // CATEGORIE AUTRE:
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_AUTRE_USER = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 53 r11 # 4 10 10 11 10 0 r6 r10 # 1 11 11 12 r11 1 2 1 10 # 0 12 12 2 # \u0000*/";
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_AUTRE_ROLE = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 53 r11 # 4 10 10 11 53 0 r6 r10 # 1 11 11 12 r11 1 7 1 53 # 0 12 12 7 # \u0000*/";
  // CATEGORIE USER:
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_USER_USER = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_USER_ROLE = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";
  // CATEGORIE FORMULAIRE:
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_FORMULAIRE_USER = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";
  //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_FORMULAIRE_ROLE = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";
  // CATEGORIE EPISODE:
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_EPISODE_USER = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/";
  // Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
  String EP_PATIENT_INSERT_EPISODE_ROLE = "/*EP 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 53 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 53 0 r6 r11 # 1 11 11 12 r12 1 7 1 53 # 0 12 12 7 # \u0000*/";
  //---- end of INSERTS ----
  // Select from matricepatient where IdGlobal = ?
  String EP_PATIENT_SELECT_BY_ID =
    " /*EP 0 2 2 10 # " +
    "1 1 1 2 r0 9 10 1 71 72 73 74 75 76 77 78 79 # " +
    "4 0 0 1 71 0 ?1 r1 # " +
    " \u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";
  // Select * from matricepatient where TSSPT > ?
  String EP_PATIENT_SELECT_ON_TSSPT = " /*EP 0 2 2 10 # 1 1 1 2 r0 9 10 1 71 72 73 74 75 76 77 78 79 # 4 0 0 1 73 1 ?1 r3 # \u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";
  // Update matricepatient SET (?,?,?,?,?,?,?,?) WHERE IdGlobal = ?
  String EP_PATIENT_UPDATE = null; // TODO
}
