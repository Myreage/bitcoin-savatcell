package org.inria.dmsp;



public class EP_Synchro {

  /*
   * PLANS FOR SYNCHRO:
   *   EP_*_INSERT
   *   EP_*_SELECT_BY_ID : used only to process delta from SANTEOS to SPT.
   *   EP_*_SELECT_ON_TSSPT : used to generate delta from SPT to SANTEOS (Secret data is avoided). 
   *   EP_*_UPDATE
   */

public static String EP_INFO_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Info WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 6 ?1 # 1 0 0 1 r1 11 4 1 37 38 39 40 41 42 43 44 45 46 92 # \u0000 12 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdComment 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 Filtre 1 12 IdConcept # \u0000*/";


public static String EP_USER_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM UserDMSP WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 4 ?1 # 1 0 0 1 r1 18 2 1 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 93 # \u0000 19 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Type 0 7 Responsable 0 8 Identifiant 1 9 Civilite 0 10 Prenom 0 11 Adresse 0 12 Ville 0 13 CodePost 0 14 Tel 0 15 Mobile 0 16 Courriel 1 17 InfoLegale 0 18 Certificate 1 19 IdRole # \u0000*/";


public static String EP_EPISODE_SELECT_BY_ID =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Episode WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 0 ?1 # 1 0 0 1 r1 4 0 1 1 2 3 4 # \u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


public static String EP_FORMULAIRE_SELECT_BY_ID =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Formulaire WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 1 ?1 # 1 0 0 1 r1 5 1 1 6 7 8 9 94 # \u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # \u0000*/";


public static String EP_ROLE_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Role WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 8 ?1 # 1 0 0 1 r1 4 6 1 53 54 55 56 # \u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


public static String EP_EVENT_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Event WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 5 ?1 # 1 0 0 1 r1 9 3 1 29 30 31 32 33 34 35 95 96 # \u0000 10 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # \u0000*/";


public static String EP_HABILITATION_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Habilitation WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 9 ?1 # 1 0 0 1 r1 5 8 1 58 59 60 62 63 # \u0000 6 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";


public static String EP_PATIENT_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST ???
/* SQL : 	SELECT * FROM MatricePatient WHERE IdGlobal = ?; */
"/*EP \u0001 0 2 2 10 # 1 1 1 2 r0 9 10 1 71 72 73 74 75 76 77 78 79 # 4 0 0 1 71 0 ?1 r1 # \u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";


public static String EP_COMMENT_SELECT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT * FROM Comment WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 7 ?1 # 1 0 0 1 r1 4 5 1 48 49 50 51 # \u0000 5 1 0 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 ValComment # \u0000*/";


public static final String EP_INFO_SELECT_ON_TSSPT =
/* SQL : 	SELECT i.IdGlobal, i.Author, i.TSSPT, i.TSSanteos, i.IdEvent, i.IdComment, i.ValChar, i.ValNum, i.ValDate, i.Position, i.Filtre, i.IdConcept FROM info i, event e WHERE i.IdEvent=e.IdGlobal AND e.TSSPT > 0 AND i.TSSPT > ? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 12 4 1 36 37 38 39 40 41 42 43 44 45 46 92 # 4 3 3 4 38 1 ?1 r3 # 1 2 2 3 r0 1 1 0 3 # 1 1 1 2 r14 1 3 1 30 # 4 0 0 1 30 1 v10 r15 # \u0000 12 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdEvent 1 6 IdComment 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 Filtre 1 12 IdConcept # \u0000*/";


public static final String EP_COMMENT_SELECT_ON_TSSPT =
/* SQL : 	SELECT c.IdGlobal, c.Author, c.TSSPT, c.TSSanteos, c.ValComment FROM Comment c, Info i, Event e WHERE i.IdEvent=e.IdGlobal AND i.IdComment=c.IdGlobal AND e.TSSPT > 0 AND c.TSSPT > ? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 2 1 0 4 3 # 1 3 3 4 r2 1 3 1 30 # 4 2 2 3 30 1 v10 r3 # 1 1 1 2 r1 5 5 1 47 48 49 50 51 # 4 0 0 1 49 1 ?1 r7 # \u0000 5 1 5 IdGlobal 1 6 Author 1 7 TSSPT 1 8 TSSanteos 0 9 ValComment # \u0000*/";


public static final String EP_EVENT_UPDATE =
/* SQL : 	UPDATE Event SET Author = ?, TSSPT = ?, TSSANTEOS = ? ,  DateEvent = ?, DateFin = ?, Filtre = ? WHERE IdGlobal = ?; */
"/*EP \u0007 0 3 3 3 # 1 2 2 3 r0 4 3 1 32 33 34 28 # 4 1 1 2 28 0 ?7 r4 # a 0 0 1 10 3 r0 28 ?7 29 ?1 30 ?2 31 ?3 32 r1 33 r2 34 r3 35 ?4 95 ?5 96 ?6 # \u0000*/";


public static final String EP_INFO_UPDATE =
/* SQL : 	UPDATE Info SET Author = ?, TSSPT = ?, TSSanteos = ?, ValChar = ?, ValNum = ?, ValDate = ?, Position = ?, Filtre = ?, IdConcept = ? WHERE IdGlobal = ?; */
"/*EP \n 0 3 3 4 # 1 2 2 3 r0 3 4 1 40 41 36 # 4 1 1 2 36 0 ?10 r3 # a 0 0 1 12 4 r0 36 ?10 37 ?1 38 ?2 39 ?3 40 r1 41 r2 42 ?4 43 ?5 44 ?6 45 ?7 46 ?8 92 ?9 # \u0000*/";


public static final String EP_COMMENT_UPDATE =
/* SQL : 	UPDATE Comment SET Author = ?, TSSPT = ?, TSSanteos = ?, ValComment = ? WHERE IdGlobal = ?; */
"/*EP \u0005 0 3 3 5 # 1 2 2 3 r0 1 5 1 47 # 4 1 1 2 47 0 ?5 r1 # a 0 0 1 5 5 r0 47 ?5 48 ?1 49 ?2 50 ?3 51 ?4 # \u0000*/";


public static final String EP_USER_UPDATE =
/* SQL : 	UPDATE UserDMSP SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ?, Type = ?, Responsable = ?, Identifiant = ?, Civilite = ?, Prenom = ?, Adresse = ?, Ville = ?, CodePost = ?, Tel = ?, Mobile = ?, Courriel = ?, InfoLegale = ?, Certificate = ?, IdRole = ? WHERE IdGlobal = ? */
"/*EP \u0013 0 3 3 2 # 1 2 2 3 r0 1 2 1 10 # 4 1 1 2 10 0 ?19 r1 # a 0 0 1 19 2 r0 10 ?19 11 ?1 12 ?2 13 ?3 14 ?4 15 ?5 16 ?6 17 ?7 18 ?8 19 ?9 20 ?10 21 ?11 22 ?12 23 ?13 24 ?14 25 ?15 26 ?16 27 ?17 93 ?18 # \u0000*/";


public static final String EP_EPISODE_UPDATE =
/* SQL : 	UPDATE Episode SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ? WHERE IdGlobal = ?; */
"/*EP \u0005 0 3 3 0 # 1 2 2 3 r0 1 0 1 0 # 4 1 1 2 0 0 ?5 r1 # a 0 0 1 5 0 r0 0 ?5 1 ?1 2 ?2 3 ?3 4 ?4 # \u0000*/";


public static final String EP_FORMULAIRE_UPDATE =
/* SQL : 	UPDATE Formulaire SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ?, Filtre = ? WHERE IdGlobal = ? */
"/*EP \u0006 0 3 3 1 # 1 2 2 3 r0 1 1 1 5 # 4 1 1 2 5 0 ?6 r1 # a 0 0 1 6 1 r0 5 ?6 6 ?1 7 ?2 8 ?3 9 ?4 94 ?5 # \u0000*/";


public static final String EP_ROLE_UPDATE =
/* SQL : 	UPDATE Role SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ? WHERE IdGlobal = ? */
"/*EP \u0005 0 3 3 6 # 1 2 2 3 r0 1 6 1 52 # 4 1 1 2 52 0 ?5 r1 # a 0 0 1 5 6 r0 52 ?5 53 ?1 54 ?2 55 ?3 56 ?4 # \u0000*/";


public static String EP_EPISODE_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Episode (IdGlobal, Author, TSSPT, TSSanteos, Nom) VALUES (?, ?, ?, ?, ?); */
"/*EP \u0005 6 1 1 -1 0 ?1 # 5 0 0 1 5 0 1 0 r0 1 ?2 2 ?3 3 ?4 4 ?5 # \u0000*/";


public static String EP_FORMULAIRE_INSERT =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Formulaire (IdGlobal, Author, TSSPT, TSSanteos, Nom, Filtre) VALUES (?, ?, ?, ?, ?, ?); */
"/*EP \u0006 6 1 1 -1 1 ?1 # 5 0 0 1 6 1 1 5 r0 6 ?2 7 ?3 8 ?4 9 ?5 94 ?6 # \u0000*/";


public static String EP_USER_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO UserDMSP (IdGlobal, Author, TSSPT, TSSanteos, Nom, Type, Responsable, Identifiant, Civilite, Prenom, Adresse, Ville, CodePost, Tel, Mobile, Courriel, InfoLegale, Certificate, IdRole) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); */
"/*EP \u0013 6 1 1 -1 4 ?1 # 5 0 0 1 19 2 1 10 r0 11 ?2 12 ?3 13 ?4 14 ?5 15 ?6 16 ?7 17 ?8 18 ?9 19 ?10 20 ?11 21 ?12 22 ?13 23 ?14 24 ?15 25 ?16 26 ?17 27 ?18 93 ?19 # \u0000*/";


public static final String EP_PATIENT_SELECT_ON_TSSPT =
/* SQL : 	SELECT * FROM MatricePatient WHERE TSSPT > ?; */
"/*EP \u0001 0 2 2 10 # 1 1 1 2 r0 9 10 1 71 72 73 74 75 76 77 78 79 # 4 0 0 1 73 1 ?1 r3 # \u0000 9 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdCategorie 1 6 TypeCategorie 1 7 IdActeur 1 8 TypeActeur 1 9 Autorisations # \u0000*/";


public static final String EP_EPISODE_SELECT_ON_TSSPT =
/* SQL : 	SELECT * FROM Episode WHERE TSSPT > ?; */
"/*EP \u0001 0 2 2 0 # 1 1 1 2 r0 5 0 1 0 1 2 3 4 # 4 0 0 1 2 1 ?1 r3 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


public static final String EP_HABILITATION_SELECT_ON_TSSPT =
/* SQL : 	SELECT * FROM Habilitation WHERE TSSPT > ?; */
"/*EP \u0001 0 2 2 8 # 1 1 1 2 r0 6 8 1 57 58 59 60 62 63 # 4 0 0 1 59 1 ?1 r3 # \u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdRole 1 6 IdUser # \u0000*/";


public static String EP_EVENT_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Event (IdGlobal, Author, TSSPT, TSSanteos, IdForm, IdUser, IdEpisode, DateEvent, DateFin, Filtre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); */
"/*EP \n 2 6 6 -1 1 ?5 # 2 7 7 -1 4 ?6 # 7 5 5 6 7 # 2 8 8 -1 0 ?7 # 7 4 4 5 8 # 6 3 3 4 2 ?5 # 6 2 2 3 5 ?1 # 5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # 5 0 0 1 10 3 1 28 r7 29 ?2 30 ?3 31 ?4 32 ?5 33 ?6 34 ?7 35 ?8 95 ?9 96 ?10 # \u0000*/";


public static String EP_INFO_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Info (IdGlobal, Author, TSSPT, TSSanteos, IdEvent, IdComment, ValChar, ValNum, ValDate, Position, Filtre, IdConcept) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); */
"/*EP \u000C 2 7 7 -1 5 ?5 # 1 6 6 7 r1 3 0 0 0 1 2 # 1 5 5 6 r2 1 1 1 5 # 2 8 8 -1 7 ?6 # 7 4 4 5 8 # 6 3 3 4 3 r5 # 6 2 2 3 6 ?1 # 5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r7 # 5 0 0 1 12 4 1 36 r8 37 ?2 38 ?3 39 ?4 40 ?5 41 ?6 42 ?7 43 ?8 44 ?9 45 ?10 46 ?11 92 ?12 # \u0000*/";


public static String EP_COMMENT_INSERT 	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Comment (IdGlobal, Author, TSSPT, TSSanteos, ValComment) VALUES (?, ?, ?, ?, ?); */
"/*EP \u0005 6 1 1 -1 7 ?1 # 5 0 0 1 5 5 1 47 r0 48 ?2 49 ?3 50 ?4 51 ?5 # \u0000*/";


public static final String EP_EVENT_SELECT_ON_TSSPT =
/* SQL : 	Select * from event where TSSPT > ?; */
"/*EP \u0001 0 2 2 3 # 1 1 1 2 r0 10 3 1 28 29 30 31 32 33 34 35 95 96 # 4 0 0 1 30 1 ?1 r3 # \u0000 10 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 1 5 IdForm 1 6 IdUser 1 7 IdEpisode 2 8 DateEvent 2 9 DateFin 1 10 Filtre # \u0000*/";


public static final String EP_USER_SELECT_ON_TSSPT =	  
/* SQL : 	Select * from userdmsp where TSSPT > ?; */
"/*EP \u0001 0 2 2 2 # 1 1 1 2 r0 19 2 1 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 93 # 4 0 0 1 12 1 ?1 r3 # \u0000 19 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Type 0 7 Responsable 0 8 Identifiant 1 9 Civilite 0 10 Prenom 0 11 Adresse 0 12 Ville 0 13 CodePost 0 14 Tel 0 15 Mobile 0 16 Courriel 1 17 InfoLegale 0 18 Certificate 1 19 IdRole # \u0000*/";


public static String EP_ROLE_INSERT  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Role (IdGlobal, Author, TSSPT, TSSanteos, Nom) VALUES (?, ?, ?, ?, ?); */
"/*EP \u0005 6 1 1 -1 8 ?1 # 5 0 0 1 5 6 1 52 r0 53 ?2 54 ?3 55 ?4 56 ?5 # \u0000*/";


public static String EP_HABILITATION_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK	  
/* SQL : 	INSERT INTO Habilitation (IdGlobal, Author, TSSPT, TSSanteos, IdRole, IdUser) VALUES (?, ?, ?, ?, ?, ?); */
"/*EP \u0006 2 4 4 -1 8 ?5 # 2 5 5 -1 4 ?6 # 7 3 3 4 5 # 6 2 2 3 9 ?1 # 5 1 1 2 2 2 0 0 r1 1 r3 # 5 0 0 1 6 8 1 57 r4 58 ?2 59 ?3 60 ?4 62 ?5 63 ?6 # \u0000*/";


public static final String EP_FORMULAIRE_SELECT_ON_TSSPT =
/* SQL : 	Select * from formulaire where TSSPT > ?; */
"/*EP \u0001 0 2 2 1 # 1 1 1 2 r0 6 1 1 5 6 7 8 9 94 # 4 0 0 1 7 1 ?1 r3 # \u0000 6 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom 1 6 Filtre # \u0000*/";


public static final String EP_HABILITATION_DELETE =
/* SQL : 	DELETE FROM Habilitation WHERE IdGlobal = ?; */
"/*EP \u0001 0 4 4 8 # 1 3 3 4 r0 1 8 1 57 # 4 2 2 3 57 0 ?1 r1 # 5 1 1 2 3 12 1 85 v18 86 r0 87 v10 # 9 0 0 1 8 r0 # \u0000*/";


public static final String EP_INFO_DELETE =
/* SQL : 	DELETE FROM Info WHERE IdGlobal = ?; */
"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 1 4 1 36 # 4 2 2 3 36 0 ?1 r1 # 5 1 1 2 3 12 1 85 v14 86 r0 87 v10 # 9 0 0 1 4 r0 # \u0000*/";


public static final String EP_COMMENT_DELETE =
/* SQL : 	DELETE FROM Comment WHERE IdGlobal = ?; */
"/*EP \u0001 0 4 4 5 # 1 3 3 4 r0 1 5 1 47 # 4 2 2 3 47 0 ?1 r1 # 5 1 1 2 3 12 1 85 v15 86 r0 87 v10 # 9 0 0 1 5 r0 # \u0000*/";


//TODO: secret data should never be inserted into TupleDeleted when deleted (for now secret data is never deleted)
public static final String EP_DELETED_SELECT_ON_TSSPT =
/* SQL : 	SELECT * from TupleDeleted where TSSPT > ? */
"/*EP \u0001 0 2 2 11 # 1 1 1 2 r0 5 11 1 80 81 82 83 84 # 4 0 0 1 83 1 ?1 r4 # \u0000 5 1 1 IdGlobal 1 2 TabId 1 3 Author 1 4 TSSPT 1 5 TSSanteos # \u0000*/";


  // there are multiple insert EPs, selection is done in tupleInsert below
  //---- Multiple INSERTS ----
  // CATEGORIE AUTRE:

  public static String EP_PATIENT_INSERT_AUTRE_USER	=  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 52 r11 # 4 10 10 11 10 0 r6 r10 # 1 11 11 12 r11 1 2 1 10 # 0 12 12 2 # \u0000*/"; 

  public static String EP_PATIENT_INSERT_AUTRE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 10 2 4 0 5 v10 52 r11 # 4 10 10 11 52 0 r6 r10 # 1 11 11 12 r11 1 6 1 52 # 0 12 12 6 # \u0000*/"; 

  // CATEGORIE USER:
  public static String EP_PATIENT_INSERT_USER_USER	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/"; 

  public static String EP_PATIENT_INSERT_USER_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 10 0 r4 r9 # 1 5 5 6 r10 1 2 1 10 # 0 6 6 2 # 4 10 10 11 52 0 r6 r11 # 1 11 11 12 r12 1 6 1 52 # 0 12 12 6 # \u0000*/"; 

  // CATEGORIE FORMULAIRE:
  public static String EP_PATIENT_INSERT_FORMULAIRE_USER	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/"; 

  public static String EP_PATIENT_INSERT_FORMULAIRE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 5 0 r4 r9 # 1 5 5 6 r10 1 1 1 5 # 0 6 6 1 # 4 10 10 11 52 0 r6 r11 # 1 11 11 12 r12 1 6 1 52 # 0 12 12 6 # \u0000*/"; 

  // CATEGORIE EPISODE:
  public static String EP_PATIENT_INSERT_EPISODE_USER	=  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 10 0 r6 r11 # 1 11 11 12 r12 1 2 1 10 # 0 12 12 2 # \u0000*/"; 

  public static String EP_PATIENT_INSERT_EPISODE_ROLE	= // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into matricepatient values (?,?,?,?,?,?,?,?,?)
	  "/*EP \u0009 5 0 0 1 9 10 1 71 ?1 72 ?2 73 ?3 74 ?4 75 ?5 76 ?6 77 ?7 78 ?8 79 ?9 # 5 1 1 2 2 4 0 5 r10 52 r12 # 7 2 2 10 4 # 4 4 4 5 0 0 r4 r9 # 1 5 5 6 r10 1 0 1 0 # 0 6 6 0 # 4 10 10 11 52 0 r6 r11 # 1 11 11 12 r12 1 6 1 52 # 0 12 12 6 # \u0000*/"; 

  //---- end of INSERTS ----

public static final String EP_ROLE_SELECT_ON_TSSPT =
/* SQL : 	SELECT * FROM role WHERE TSSPT > ? */
"/*EP \u0001 0 2 2 6 # 1 1 1 2 r0 5 6 1 52 53 54 55 56 # 4 0 0 1 54 1 ?1 r3 # \u0000 5 1 1 IdGlobal 1 2 Author 1 3 TSSPT 1 4 TSSanteos 0 5 Nom # \u0000*/";


public static final String EP_HABILITATION_UPDATE =
/* SQL : 	UPDATE Habilitation SET Author = ?, TSSPT = ?, TSSanteos = ?, IdRole = ?, IdUser = ? WHERE IdGlobal = ?; */
"/*EP \u0006 2 1 1 -1 9 ?6 # a 0 0 1 6 8 r1 57 ?6 58 ?1 59 ?2 60 ?3 62 ?4 63 ?5 # \u0000*/";


//  =============================================================================================== 
//  BELOW THIS LINE, PORTING STATUS UNKNOWN: NOBODY CHECKED IF THEY STILL WORK WITH THE NEW INDEXES 
//  =============================================================================================== 

  public static String EP_EVENT_INSERT_SIGMOD = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
	  //Insert into event values (?,?,?,?,?,?,?,?,?,?)
	  "/*EP \n 2 6 6 -1 1 5 ?5 # 2 7 7 -1 2 10 ?6 # 7 5 5 6 7 # 2 8 8 -1 0 0 ?7 # 7 4 4 5 8 # 6 3 3 4 3 3 28 ?1 t3 # 6 2 2 3 3 1 5 r0 r7 # 5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # 5 0 0 1 10 3 1 28 r6 29 ?2 30 ?3 31 ?4 32 ?5 33 ?6 34 ?7 35 ?8 95 ?9 96 ?10 # \u0000*/";

  public static String EP_INFO_INSERT_SIGMOD = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
    //Insert into info values (?,?,?,?,?, ?,?,?,?,?,?,?) 
	  "/*EP \u000C 2 6 6 -1 3 28 ?5 # 1 5 5 6 r1 3 0 0 0 1 2 # 2 7 7 -1 5 47 ?6 # 7 4 4 5 7 # 1 3 3 4 r2 1 1 1 5 # 6 2 2 3 4 1 5 r7 t4 # 5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r6 # 5 0 0 1 12 4 1 36 ?1 37 ?2 38 ?3 39 ?4 40 ?5 41 ?6 42 ?7 43 ?8 44 ?9 45 ?10 46 ?11 92 ?12 # \u0000*/";
  public static String EP_INFO_INSERT_OLD = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
	//Insert into info values (?,?,?,?,?, ?,?,?,?,?, ?)
	  "/*EP \u000C 5 0 0 1 12 4 1 36 ?1 37 ?2 38 ?3 39 ?4 40 ?5 41 ?6 42 ?7 43 ?8 44 ?9 45 ?10 46 ?11 92 ?12 # 5 1 1 2 5 1 0 0 r12 1 r13 2 r14 3 r16 4 r18 # 7 2 2 3 7 # 1 3 3 4 r16 3 0 0 0 1 2 # 4 4 4 5 28 0 r4 r15 # 1 5 5 6 r16 1 3 1 28 # 0 6 6 3 # 4 7 7 8 47 0 r5 r17 # 1 8 8 9 r18 1 5 1 47 # 0 9 9 5 # \u0000*/"; 

  public static final String EP_DMSP_INSERT = null;				//Probably not useful in this version
  public static final String EP_DMSP_SELECT_BY_ID = null;		//Probably not useful in this version
  public static final String EP_DMSP_SELECT_ON_TSSPT = null;	//Probably not useful in this version
  public static final String EP_DMSP_UPDATE = null;				//Probably not useful in this version
  public static final String EP_PATIENT_UPDATE = null;  	//Probably not useful in this version

  public final static String EP_EVENT_DELETE =
	  /* SQL : 	DELETE FROM Event WHERE IdGlobal=? */
	  "/*EP \u0001 0 4 4 3 # 1 3 3 4 r0 1 3 1 28 # 4 2 2 3 28 0 ?1 r1 # 5 1 1 2 3 12 1 85 v13 86 r0 87 v10 # 9 0 0 1 3 r0 # \u0000*/";


  // TupleDeleted TABLE
  public static final String EP_DELETED_INSERT =
    // INSERT into TupeDeleted values (?1,?5,?2,?3,?4)
    null;  	//Probably not useful in this version

  public static final String EP_DELETED_SELECT_BY_ID =
    // SELECT from TupeDeleted where IdGlobal = ?
    null;  	//Probably not useful in this version

  public static final String EP_DELETED_DELETE =
    // DELETE FROM TupeDeleted WHERE IdGlobal = ?
    null;  	//Probably not useful in this version

}
