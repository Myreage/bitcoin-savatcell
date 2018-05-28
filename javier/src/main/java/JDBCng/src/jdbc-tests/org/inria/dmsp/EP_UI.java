
package org.inria.dmsp;

public class EP_UI {

  /*
   * 
   * 
   *  LEGENDE:
   *  x --> à supprimer
   *  ? --> à supprimer ?
   *  v --> utilisée
   *  o --> à faire
   *  
   * 
   *  FIRST PLANS FOR UI: 
   *
   *  TODO (FOR OPTIM):
   *  o	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_BETWEEN_DATE
   *  o	EP_QUERY_AC_SELECT_ INFOnoc_BY_FORM
   *  o	EP_QUERY_AC_SELECT_ INFO_pos_num_BY_FORM // done by lionel, 19/11/09
   *  o	EP_QUERY_NOAC_SELECT_nom_prenom_type_tel_idglobal_USER // done by lionel, 19/11/09
   *  o	EP_QUERY_NOAC_SELECT_USER_civilite_nom_prenom_BY_IDGLOBAL // done by shaoyi, 18/09/09
   *  TODO (REQUIRED):
   *  o EP_QUERY_AC_SELECT_INFO_BY_EVENT_AND_FILTRE // done by shaoyi, 18/09/09
   *  o EP_QUERY_AC_SELECT_INFO_valnum_BY_EVENT_AND_POSITION // done by shaoyi, 17/09/09
   *  
   *  LOGIN:
   *  v  EP_QUERY_NOAC_SELECT_USER_BY_CERTIF				--> selection du user selon son certificat
   *  v  EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER		--> role pour lequel user est habilité
   *  v  EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM			--> formulaires autorisés en insert
   *  SELECT PLANS:
   *  v EP_QUERY_AC_SELECT_INFO_BY_FORM						--> OPTIM for tableau de bord + optim for plan d'acc
   *  v	EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE		--> vue chronologique
   *  ?	EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE		--> vue chronologique
   *  ?	EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM 	--> fiche patient [+ plan d'acc.] + liste passages/ev. importants)
   *  v	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM 		--> fiche patient [+ plan d'acc.] + liste passages/ev. importants)
   *  v	EP_QUERY_NOAC_SELECT_USER_BY_ID						--> détail d'un intervenant
   *  v	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT		--> ouverture d'un formulaire
   *  v	EP_QUERY_AC_SELECT_INFO_BY_FILTRE					--> listes à base d'infos (entourage, etc.)
   *  x	EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE				--> liste des intervenants
   *  v	EP_QUERY_NOAC_SELECT_USER
   *  v	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE --> calendrier
   *  v EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT
   *  v	EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE  	--> traitements en cours
   *  v	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT --> list of comments to fill in a given cell of a form
   *  ?  EP_QUERY_AC_SELECT_EVENT_BY_ID 
   *  v	EP_QUERY_NOAC_SELECT_HABILITATION					--> liste des user habilités à jouer leur role
   *  ?	EP_QUERY_SELECT_NOAC_INFO_BY_EVENT
   *  v  EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM
   *  v  EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_POSITION
   *  x  EP_QUERY_SELECT_USER_HABILITATION
   *  INSERT PLANS:
   *  v	OEP_EVENT_AC_INSERT
   *  v	OEP_INFO_AC_INSERT
   *  v	OEP_COMMENT_NOAC_INSERT
   *  v	EP_HABILITATION_NOAC_INSERT
   *  v	EP_DELETED_NOAC_INSERT
   *  UPDATE PLANS:
   *  v	INFO_AC_UPDATE_BY_ID
   *  v	COMMENT_AC_UPDATE_BY_ID
   *  v	EVENT_AC_UPDATE_BY_ID
   *  DELETE PLANS:
   *  v EP_HABILITATION_NOAC_DELETE_BY_USER
   *  ? EP_EVENT_AC_DELETE_BY_ID
   *  ?	EP_INFO_AC_DELETE_BY_EVENT
   *  ?	EP_INFO_AC_DELETE_BY_ID
   *	
   */

// liste de l'entourage (Info.Filtre=? pour récupérer les infos nom, prénom, lien => pas de comment) 
// NB: identique à EP_QUERY_AC_SELECT_INFO_BY_FILTRE avec en + le champs IdUser 
public static String EP_QUERY_AC_SELECT_USER_EVENT_INFO_BY_FILTRE = 
/* SQL : 	SELECT i.Position, i.ValChar, i.ValNum, i.IdEvent, i.ValDate, i.IdComment, i.TSSPT, e.IdUser FROM Event e, Info i WHERE i.Idevent=e.IdGlobal AND i.Filtre=?; */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 7 4 1 45 42 43 44 41 38 46 # 4 3 3 4 46 0 ?1 r7 # 1 2 2 3 r0 4 1 0 1 0 2 3 # 8 1 1 2 0 r9 r10 r11 # 1 0 0 1 r12 2 3 1 28 33 # \u0000 8 1 1 Position 0 2 ValChar 1 3 ValNum 1 13 IdEvent 2 4 ValDate 1 5 IdComment 1 6 TSSPT 1 14 IdUser # \u0000*/";


// liste des fiches de liaison (Info.Filtre=? pour récupérer les infos Date, Type, Origine, Cat. Prof., Obs. => un comment) 
// NB: identique à EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE avec en + un critère d'intervalle sur la date de l'event 
public static String EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE_BETWEEN_DATEEVENT = 
/* SQL : 	SELECT i.Position, i.ValChar, i.ValNum, i.IdEvent, i.ValDate, i.TSSPT, e.IdUser, c.ValComment FROM Event e, Info i, Comment c WHERE i.Idevent=e.IdGlobal AND i.IdComment=c.IdGlobal AND i.Filtre=? AND e.DateEvent>? AND e.DateEvent<?; */
"/*EP \u0003 0 8 8 4 # 1 7 7 8 r0 6 4 1 45 42 43 44 38 46 # 4 6 6 7 46 0 ?1 r6 # 1 5 5 6 r0 5 1 0 1 0 2 3 4 # 8 4 4 5 0 r8 r9 r10 # 1 3 3 4 r11 3 3 1 28 33 35 # 4 2 2 3 35 1 ?2 r15 # 4 1 1 2 35 2 ?3 r15 # 1 0 0 1 r12 1 5 1 51 # \u0000 8 1 1 Position 0 2 ValChar 1 3 ValNum 1 13 IdEvent 2 4 ValDate 1 5 TSSPT 1 14 IdUser 0 18 ValComment # \u0000*/";


/*
 *  Produce the list of Planning d’interventions given the idglobal of the 
 *  form Planning d’intervention and keep only position and valnum 
 *
 *
 *  OUT: 	Position:=Info.Position, ValNum:=Info.ValNum
 *  IN:  	?1:=Formulaire.IdGlobal
 */
// Optimization query for the "Plan d'aide"==> OK 
// Special version of EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM projecting only position and valnum 
// We add the form table for optimization ... 
public static String EP_QUERY_AC_SELECT_INFO_pos_num_BY_FORM =  
/* SQL : 	SELECT I.Position, I.ValNum FROM info I, event E, formulaire F WHERE I.IdEvent = E.IdGlobal and E.IdForm = F.IdGlobal AND F.IdGlobal = ? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 3 1 0 1 0 2 # 8 3 3 4 0 r1 r2 r3 # 1 2 2 3 r2 1 1 1 5 # 4 1 1 2 5 0 ?1 r4 # 1 0 0 1 r0 2 4 1 45 43 # \u0000 2 1 6 Position 1 7 ValNum # \u0000*/";


/* to get only the useful attributes of table UserDMSP
 *  
 *
 *  OUT: 	Nom, Prenom, Type, Tel, IdGlobal
 *  IN:  
 */
// Optimization query for the "Liste des Intervenants" ==> OK 
// Version of EP_QUERY_NOAC_SELECT_USER projecting only useful fields 
public static String EP_QUERY_NOAC_SELECT_nom_prenom_type_tel_idglobal_USER =  
/* SQL : 	SELECT Nom, Prenom, Type, Tel, IdGlobal FROM UserDMSP */
"/*EP \u0000 0 1 1 2 # 1 0 0 1 r0 5 2 1 14 19 15 23 10 # \u0000 5 0 1 Nom 0 2 Prenom 1 3 Type 0 4 Tel 1 5 IdGlobal # \u0000*/";


/* to get only the useful attributes of table UserDMSP by IdGlobal
 *  
 *
 *  OUT: 	Civilite, Nom, Prenom
 *  IN:  ?1:=UserDMSP.IdGlobal
 *     		-->
 */
// Optimization query for the "Fiche de synthèse - référent user" ==> OK 
// Version of EP_QUERY_NOAC_SELECT_USER_BY_ID  projecting only useful fields 
public static String EP_QUERY_NOAC_SELECT_USER_civilite_nom_prenom_BY_IDGLOBAL =  
/* SQL : 	SELECT Civilite, Nom, Prenom FROM UserDMSP u WHERE u.IdGlobal = ? */
"/*EP \u0001 2 1 1 -1 4 ?1 # 1 0 0 1 r1 3 2 1 18 14 19 # \u0000 3 1 2 Civilite 0 3 Nom 0 4 Prenom # \u0000*/";


/* to get only the useful attributes of table info by event id and filtre
 *  
 *
 *  OUT: 	Position:=Info.Position, ValChar:=Info.ValChar, ValNum:=Info.ValNum
 *  IN:  ?1:=Event.IdGlobal
 *     		-->
 *     	?2:=Info.Filtre
 *     		--> 
 */
// Optimization query for the "Fiche de synthèse - référent entourage"==> OK 
// Special version of EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT projecting only useful fields 
// The filter (Filtre) should indicate the useful fields for the query (civilite, nom, prenom) 
// Valnum for civilité ???? 
public static String EP_QUERY_AC_SELECT_INFO_BY_EVENT_AND_FILTRE =  
/* SQL : 	SELECT i.Position, i.ValChar, i.ValNum FROM Event e, Info i WHERE i.IdEvent=e.IdGlobal and e.IdGlobal=? and i.Filtre = ? */
"/*EP \u0002 0 6 6 4 # 1 5 5 6 r0 4 4 1 45 42 43 46 # 4 4 4 5 46 0 ?2 r4 # 1 3 3 4 r0 4 1 0 1 0 2 3 # 8 2 2 3 0 r6 r7 r8 # 1 1 1 2 r9 1 3 1 28 # 4 0 0 1 28 0 ?1 r10 # \u0000 3 1 1 Position 0 2 ValChar 1 3 ValNum # \u0000*/";


/* to get only the valnum attribute of table info by event id and position
 *  
 *
 *  OUT: 	ValNum:=Info.ValNum
 *  IN:  ?1:=Event.IdGlobal
 *     		-->
 *     	?2:=Info.Position
 *     		--> 
 */
// Optimization query for the "Fiche de synthèse - GIR" ==> OK 
// We add the event table for optimization ... 
public static final String EP_QUERY_AC_SELECT_INFO_valnum_BY_EVENT_AND_POSITION =  
/* SQL : 	SELECT i.ValNum FROM Event e, Info i WHERE i.IdEvent=e.IdGlobal and e.IdGlobal=? and i.Position = ? */
"/*EP \u0002 0 6 6 4 # 1 5 5 6 r0 2 4 1 43 45 # 4 4 4 5 45 0 ?2 r2 # 1 3 3 4 r0 4 1 0 1 0 2 3 # 8 2 2 3 0 r4 r5 r6 # 1 1 1 2 r7 1 3 1 28 # 4 0 0 1 28 0 ?1 r8 # \u0000 1 1 1 ValNum # \u0000*/";


public static String EP_QUERY_AC_SELECT_INFO_BY_FORM =
/* SQL : 	SELECT i.IdGlobal, i.Position, i.IdEvent, i.IdConcept, i.ValChar, i.ValDate, i.ValNum, i.IdComment FROM Event e, Info i WHERE i.IdEvent = e.IdGlobal AND e.IdForm = ? */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 4 1 0 1 0 2 3 # 8 3 3 4 0 r1 r2 r3 # 1 2 2 3 r4 1 3 1 32 # 4 1 1 2 32 0 ?1 r5 # 1 0 0 1 r0 8 4 1 36 45 40 92 42 44 43 41 # \u0000 8 1 7 IdGlobal 1 8 Position 1 9 IdEvent 1 10 IdConcept 0 11 ValChar 2 12 ValDate 1 13 ValNum 1 14 IdComment # \u0000*/";


/*
 *  selection du UserDMSP selon un certificat donné
 *
 *  OUT: 	IdGlobal:=UserDMSP.IdGlobal, IdRole:=UserDMSP.IdRole,
 *  		Nom:=UserDMSP.Nom, Prenom:=UserDMSP.Prenom
 *  IN:  	?1:=UserDMSP.Certificate
 */
// selection du user ayant un certificat donné 
public static String EP_QUERY_NOAC_SELECT_USER_BY_CERTIF = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST ???
/* SQL : 	SELECT IdGlobal, IdRole, Nom, Prenom FROM UserDMSP WHERE Certificate = ?; */
"/*EP \u0001 0 2 2 2 # 1 1 1 2 r0 5 2 1 10 93 14 19 27 # 4 0 0 1 27 0 ?1 r5 # \u0000 4 1 1 IdGlobal 1 2 IdRole 0 3 Nom 0 4 Prenom # \u0000*/";


/*
 *  selection du rôle pour lequel le UserDMSP d’IdGlobal donné est habilité
 *  OUT: 	Habilitation.IdRole
 *  IN:  	?1:=Habilitation.IdUser
 */
// selection du role pour lequel le user est habilité 
public static String EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST ???
/* SQL : 	SELECT IdRole FROM Habilitation WHERE IdUser = ?; */
"/*EP \u0001 0 2 2 8 # 1 1 1 2 r0 2 8 1 62 63 # 4 0 0 1 63 0 ?1 r2 # \u0000 1 1 1 IdRole # \u0000*/";


/*
 *  selection des formulaires autorisés en insertion pour l'utilisateur (user/role) connecté
 *  OUT: 	IdGlobal:=Formulaire.IdGlobal
 *  IN:  	?1:=Episode.IdIntern (attribut système)-->affecter arbitrairement la valeur 1 (integer)
 *  		?2:=UserDMSP.IdIntern (attribut système)-->affecter arbitrairement la valeur 1 (integer)
 */
// selection des formulaires autorisés en insert 
public static final String EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM =
//SELECT F.IdGlobal
//FROM Form F, UserDMSP U, Episode E
//WHERE U.Idintern = ? AND E.IdIntern = ?; 
// ATTENTION: Special EP checking access rights in INSERT mode 
// NB: 1rst epis. and user (id_intern=0) are always autorized in INSERT 
// ==> params ?1=1 & ?2=1 
"/*EP \u0002 0 8 8 0 # 4 7 7 8 0 0 ?1 r0 # 0 6 6 2 # 4 5 5 6 10 0 ?2 r2 # 0 3 3 1 # 7 4 4 5 7 # 7 2 2 3 4 # 8 1 1 2 3 r2 r4 r0 # 1 0 0 1 r4 1 1 1 5 # \u0000 1 1 5 IdGlobal # \u0000*/"; 


/*
 *  construction de la vue chronologique
 *  OUT: 	Nom:=UserDMSP.Nom, DateEvent:=Event.DateEvent,
 *  		eIdGlobal:=Event.IdGlobal, fIdGlobal:=Form.IdGlobal,
 *  		uIdGlobal:=User.IdGlobal, TSSPT:=Event.TSSPT
 *  IN:  	?1:=Formulaire.Filtre
 *  			-->	affecter la valeur du filtre correspondant aux formulaires
 *  				devant apparaitre dans la vue chronologique ==> valeur 1
 *  				pour la version n°379 de fromSanteos.xml. Cette valeur est
 *  				fixée par Santeos (delta initial).
 */
// vue chronologique (Formulaire.Filtre=? /*1*/): 
public static final String EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE  =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT u.Nom, u.IdGlobal, e.DateEvent, e.IdGlobal, f.IdGlobal, e.TSSPT, u.IdGlobal FROM UserDMSP u, Event e, Formulaire f WHERE f.idGlobal = e.idForm AND u.idGlobal = e.idUser AND f.Filtre = ?; */
"/*EP \u0001 0 5 5 3 # 1 4 4 5 r0 2 0 0 0 1 # 1 3 3 4 r1 2 1 1 5 94 # 4 2 2 3 94 0 ?1 r4 # 1 1 1 2 r2 2 2 1 14 10 # 1 0 0 1 r0 3 3 1 35 28 30 # \u0000 6 0 6 Nom 2 8 DateEvent 1 9 eIdGlobal 1 3 fIdGlobal 1 7 uIdGlobal 1 10 TSSPT # \u0000*/"; 


/*
 *  construction de la vue chronologique avec controle d'access
 *  OUT: 	Nom:=UserDMSP.Nom, DateEvent:=Event.DateEvent,
 *  		eIdGlobal:=Event.IdGlobal, fIdGlobal:=Form.IdGlobal,
 *  		uIdGlobal:=User.IdGlobal, TSSPT:=Event.TSSPT
 *  IN:  	?1:=Formulaire.Filtre
 *  			-->	affecter la valeur du filtre correspondant aux formulaires
 *  				devant apparaitre dans la vue chronologique ==> valeur 1
 *  				pour la version n°379 de fromSanteos.xml. Cette valeur est
 *  				fixée par Santeos (delta initial).
 */
// vue chronologique (Formulaire.Filtre=? /*1*/): 
public static final String EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE  =  // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT u.Nom, u.IdGlobal, e.DateEvent, e.IdGlobal, f.IdGlobal, e.TSSPT, u.IdGlobal FROM UserDMSP u, Event e, Formulaire f WHERE f.idGlobal = e.idForm AND u.idGlobal = e.idUser AND f.Filtre = ?; */
"/*EP \u0001 0 6 6 3 # 1 5 5 6 r0 3 0 0 0 1 2 # 8 4 4 5 0 r2 r1 r3 # 1 3 3 4 r1 2 1 1 5 94 # 4 2 2 3 94 0 ?1 r5 # 1 1 1 2 r2 2 2 1 14 10 # 1 0 0 1 r0 3 3 1 35 28 30 # \u0000 6 0 7 Nom 2 9 DateEvent 1 10 eIdGlobal 1 4 fIdGlobal 1 8 uIdGlobal 1 11 TSSPT # \u0000*/"; 


/*
 *  accès à la fiche patient selon son Formulaire.IdGlobal
 *  OUT: 	IdGlobal:=Info.IdGlobal, Position:=Info.Position, ValChar:=Info.ValChar,
 *  		ValDate:=Info.ValDate, ValNum:=Info.ValNum,
 *  		ValComment:=Comment.ValComment
 *     		-->	il se peut qu'un meme comment UI soit stocké dans plusieurs
 *     			ValComment du SGBD (Info.Positions consécutives).
 *     			Il faut alors "recoller les morceaux" dans l'UI.
 *  IN:  	?1:=Formulaire.IdGlobal
 *  			-->	affecter la valeur de l'IdGlobal de la fiche patient, telle
 *  				que Formulaire.Nom = "Identite du patient" =>
 *  				Formulaire.IdGlobal=1001 (version n°379 de fromSanteos.xml)
 *  				valeurs fixés par Santeos dans le delta initial. Pour
 *  				ce formulaire particulier, il n'y a qu'un seul event associé.
 */
// fiche patient (Formulaire.IdGlobal=? /*1001*/): 
public static final String EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm=?; */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 2 1 0 4 3 # 1 3 3 4 r2 1 3 1 32 # 4 2 2 3 32 0 ?1 r3 # 1 1 1 2 r0 5 4 1 36 45 42 44 43 # 1 0 0 1 r1 1 5 1 51 # \u0000 6 1 5 IdGlobal 1 6 Position 0 7 ValChar 2 8 ValDate 1 9 ValNum 0 10 ValComment # \u0000*/";


/*
 *  accès à la fiche patient selon son Formulaire.IdGlobal
 *  OUT:  IdGlobal:=Info.IdGlobal, Position:=Info.Position, ValChar:=Info.ValChar,
 *        ValDate:=Info.ValDate, ValNum:=Info.ValNum,
 *        ValComment:=Comment.ValComment
 *            --> il se peut qu'un meme comment UI soit stocké dans plusieurs
 *                ValComment du SGBD (Info.Positions consécutives).
 *                Il faut alors "recoller les morceaux" dans l'UI.
 *  IN:   ?1:=Formulaire.IdGlobal
 *            --> affecter la valeur de l'IdGlobal de la fiche patient, telle
 *                que Formulaire.Nom = "Identite du patient" =>
 *                Formulaire.IdGlobal=1001 (version n°379 de fromSanteos.xml)
 *                valeurs fixés par Santeos dans le delta initial. Pour
 *                ce formulaire particulier, il n'y a qu'un seul event associé.
 */
// fiche patient (Formulaire.IdGlobal=? /*1001*/): 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm=?; */
"/*EP \u0001 0 6 6 4 # 1 5 5 6 r0 5 1 0 1 0 2 4 3 # 8 4 4 5 0 r1 r2 r3 # 1 3 3 4 r5 1 3 1 32 # 4 2 2 3 32 0 ?1 r6 # 1 1 1 2 r0 5 4 1 36 45 42 44 43 # 1 0 0 1 r4 1 5 1 51 # \u0000 6 1 8 IdGlobal 1 9 Position 0 10 ValChar 2 11 ValDate 1 12 ValNum 0 13 ValComment # \u0000*/";


/*
 *  Open a form given its Event.IdGlobal (from the chronological view)
 *  NB: Event.DateFin is only required for the form treatment 
 *  OUT: 	IdGlobal:=Info.IdGlobal, Position:=Info.Position, ValChar:=Info.ValChar,
 *  		ValDate:=Info.ValDate, ValNum:=Info.ValNum,
 *  		ValComment:=Comment.ValComment, DateFin:=Event.DateFin
 *     		-->	a single comment cell can be stored in several ValComment 
 *     			in table Comment (each corresponding to 1 Info with 
 *     			successive Info.Positions).
 *  IN:  ?1:=Event.IdGlobal
 */
// ouverture d'un formulaire (Event.IdGlobal=?) 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment, e.DateFin FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdGlobal=?; */
"/*EP \u0001 0 6 6 4 # 1 5 5 6 r0 5 1 0 1 0 2 4 3 # 8 4 4 5 0 r1 r2 r3 # 1 3 3 4 r5 2 3 1 95 28 # 4 2 2 3 28 0 ?1 r7 # 1 1 1 2 r0 5 4 1 36 45 42 44 43 # 1 0 0 1 r4 1 5 1 51 # \u0000 7 1 9 IdGlobal 1 10 Position 0 11 ValChar 2 12 ValDate 1 13 ValNum 0 14 ValComment 2 6 DateFin # \u0000*/";


/*
 *  accès aux champs de UserDMSP selon UserDMSP.idglobal, pour accéder au détail
 *  des informations correspondant à un intervenant
 *  OUT: 	IdGlobal:=UserDMSP.IdGlobal, Nom:=UserDMSP.Nom, Type:=UserDMSP.Type,
 *  		Responsable:=UserDMSP.Responsable, Identifiant:=UserDMSP.Identifiant,
 *  		Civilite:=UserDMSP.Civilite, Prenom:=UserDMSP.Prenom,
 *  		Adresse:=UserDMSP.Adresse, CodePost:=UserDMSP.CodePost,
 *  		Ville:=UserDMSP.Ville, Tel:=UserDMSP.Tel, Mobile:=UserDMSP.Mobile,
 *  		Courriel:=UserDMSP.Courriel, InfoLegale:=UserDMSP.InfoLegale,
 *  		IdRole:=UserDMSP.IdRole
 *  IN:  ?1:=UserDMSP.IdGlobal
 */
// détail d'un intervenant (User.IdGlobal=?): 
public static final String EP_QUERY_NOAC_SELECT_USER_BY_ID  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT Nom, Type, Responsable, Identifiant, Civilite, Prenom, Adresse, CodePost, Ville, Tel, Mobile, Courriel, InfoLegale, IdRole FROM UserDMSP WHERE IdGlobal = ?; */
"/*EP \u0001 2 1 1 -1 4 ?1 # 1 0 0 1 r1 14 2 1 14 15 16 17 18 19 20 22 21 23 24 25 26 93 # \u0000 14 0 2 Nom 1 3 Type 0 4 Responsable 0 5 Identifiant 1 6 Civilite 0 7 Prenom 0 8 Adresse 0 9 CodePost 0 10 Ville 0 11 Tel 0 12 Mobile 0 13 Courriel 1 14 InfoLegale 1 15 IdRole # \u0000*/";


/*
 *  Accès aux Infos constituant la liste 
 *  (ex: des personnes de l'entourage, des fiches de liaison)
 *  selon la valeur d'Info.Filtre identifiant cette liste
 *  OUT: 	Position:=Info.Position, ValChar:=Info.ValChar,
 *  		ValNum:=Info.ValChar, IdEvent:=Event.IdGlobal,
 *  		ValDate:=Info.ValDate, IdComment:=Info.IdComment,
 *  		TSSPT:=Info.TSSPT
 *     		-->	A chaque ligne de la liste correspond un meme IdEvent
 *  IN:  ?1:=Info.Filtre
 *     		-->	A chaque liste correspond une valeur particulière de Filtre.
 *     			Les valeurs identifiant chaque liste sont décidées avec
 *     			Santeos.
 */
// listes (entourage, fiche liaison, etc.) (Info.Filtre=?) 
public static final String EP_QUERY_AC_SELECT_INFO_BY_FILTRE  = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST NOK
/* SQL : 	SELECT Position, ValChar, ValNum, IdEvent, ValDate, IdComment, TSSPT FROM Info WHERE Filtre=?; */
"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 8 4 1 45 42 43 40 44 41 38 46 # 4 2 2 3 46 0 ?1 r8 # 1 1 1 2 r0 3 1 0 1 0 2 # 8 0 0 1 0 r10 r11 r12 # \u0000 7 1 1 Position 0 2 ValChar 1 3 ValNum 1 4 IdEvent 2 5 ValDate 1 6 IdComment 1 7 TSSPT # \u0000*/";


/*
 *  Accès à la liste des intervenants ayant été informés que les informations
 *  les concernant pourraient etre produites (selon la valeur de
 *  User.InfoLegale).
 *  OUT: 	IdGlobal:=UserDMSP.IdGlobal, Nom:=UserDMSP.Nom, Type:=UserDMSP.Type,
 *  		Identifiant:=UserDMSP.Identifiant, Civilite:=UserDMSP.Civilite,
 *  		Prenom:=UserDMSP.Prenom, Tel:=UserDMSP.Tel, Mobile:=UserDMSP.Mobile
 *  		IdRole:=UserDMSP.IdRole, Ville:=UserDMSP.Ville
 *  IN:  ?1:=UserDMSP.InfoLegale
 * 			-->	une valeur particulière de InfoLegale est attribuée aux users
 *     			ayant été informés du partage d'information. Cette valeur est
 *     			à définir avec Santeos. (v. n°379 de fromSanteos.xml ==> est
 *     			à NULL pour tous les UserDMSP)
 */
// liste des intervenants "informés" (User.InfoLegale=? /*1*/) 
public static final String EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE /*EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE*/ = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT IdGlobal, Nom, Type, Identifiant, Civilite, Prenom, Tel, Mobile, IdRole, Ville FROM UserDMSP WHERE infolegale=?; */
"/*EP \u0001 0 2 2 2 # 1 1 1 2 r0 11 2 1 10 14 15 17 18 19 23 24 93 21 26 # 4 0 0 1 26 0 ?1 r11 # \u0000 10 1 1 IdGlobal 0 2 Nom 1 3 Type 0 4 Identifiant 1 5 Civilite 0 6 Prenom 0 7 Tel 0 8 Mobile 1 9 IdRole 0 10 Ville # \u0000*/";


public static final String EP_QUERY_NOAC_SELECT_USER = 
/* SQL : 	SELECT IdGlobal, Nom, Type, Identifiant, Civilite, Prenom, Tel, Mobile, IdRole, Ville FROM UserDMSP; */
"/*EP \u0000 0 1 1 2 # 1 0 0 1 r0 10 2 1 10 14 15 17 18 19 23 24 93 21 # \u0000 10 1 1 IdGlobal 0 2 Nom 1 3 Type 0 4 Identifiant 1 5 Civilite 0 6 Prenom 0 7 Tel 0 8 Mobile 1 9 IdRole 0 10 Ville # \u0000*/";


/*
 *  Accès à la liste des rendez-vous du calendrier selon une valeur
 *  donnée de Formulaire.IdGlobal et de Info.Filtre.
 *
 *  Contenu du calendrier (version alpha):
 *  on met dans le tableau (semaine type) les passages réguliers, au minimum
 *  hebdomadaire. Cela correspond aux informations renseignées dans les
 *  formulaires "Passage d un intervenant" (v. n°379 de fromSanteos.xml) dont:
 *   - la première case est cochée (passages journaliers),
 *   - la deuxième case est cochée (passages 1 ou plusieurs fois par semaine).
 *  Dans ces deux cas, il est convenu avec Santeos que la valeur Info.Filtre
 *  correspondantes est 0 (dans les autres cas, Info.Filtre=1 pour l'extension
 *  prévue à la version suivante)
 *
 *  OUT: 	IdGlobal:=Event.IdGlobal,
 *     		-->	un meme IdGlobal identifie un meme passage d'intervenant.
 *  		Position:=Info.Position, ValChar:=Info.ValChar,
 *  		ValDate:=Info.ValDate, ValNum:=Info.ValNum,
 *  		ValComment:=Comment.ValComment
 *  IN:  ?1:=Formulaire.IdGlobal
 *     		-->	IdGlobal of Formulaire.Nom="Passage d un intervenant"
 *     			(1010 in v. n°379 de fromSanteos.xml).
 *     	?2:=Info.Filtre
 *     		--> value 0 (see explanation above)
 */
// liste des rendez-vous du calendrier 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE =
/* SQL : 	SELECT e.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM event e, info i, comment c WHERE i.Idevent=e.IdGlobal and i.IdComment=c.IdGlobal and e.IdForm=? and i.Filtre = ?; */
"/*EP \u0002 0 7 7 4 # " +
// scan of table info(4) --> r0 
"1 6 6 7 r0 4 1 0 0 1 2 4 # " +
// TABLE LOOKUP pos r0, 4 cols, SKTinfo(1), is SKT(0), 0 (formPos->5), 1 (userPos->10), 2 (episodePos->0), 4(commentPos->47) --> r1-r4 
"8 5 5 6 0 r2 r1 r3 # " +
// access_type(0:read), in_res_UserDMSP(r2), in_res_Formulaire(r1), in_res_Episode(r3) 
"1 4 4 5 r1 1 1 1 5 # " +
// TABLE LOOKUP pos r1,  1 col, table form(1), is table(1), 5(IDG) -->  r5(Form.IdG) 
"4 3 3 4 5 0 ?1 r5 # " +
// SELECT Form.IdG(5), equal(0), compare param 1, with value in r3 --> r6(param1: Form.IdG) 
"1 2 2 3 r0 7 4 1 40 36 42 43 45 44 46 # " +
// TABLE LOOKUP at pos r0,  7 cols, table info(4), is table (1), 
// 40(IdEvent), 36(IDG), 42(ValChar), 43(ValNum), 45(Pos), 44(ValDate), 46(Filtre) -->  r7-r13 
"4 1 1 2 46 0 ?2 r13 # " +
// SELECT Col Info.Filtre (46), equal (0), compare param 2, with value in r13 --> r14(param2: info.filtre) 
"1 0 0 1 r4 1 5 1 51 # " +
// TABLE LOOKUP pos r2,  1 cols, table Comment(5), is table(1), 51(ValComment)--> r15 
"\u0000 6 1 7 IdEvent 1 11 Position 0 9 ValChar 2 12 ValDate 1 10 ValNum  0 15 ValComment # \u0000*/";
// 6 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i 


/*
 *  cf. EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE (i.Filtre= ? remplacé par e.Filtre = ?)
 */
// liste des rendez-vous du calendrier 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT =
/* SQL : 	SELECT e.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c  WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm=? AND e.Filtre = ?; */
"/*EP \u0002 0 7 7 4 # 1 6 6 7 r0 5 1 0 1 0 2 4 3 # 8 5 5 6 0 r1 r2 r3 # 1 4 4 5 r5 2 3 1 32 96 # 4 3 3 4 32 0 ?1 r6 # 4 2 2 3 96 0 ?2 r7 # 1 1 1 2 r0 5 4 1 40 45 42 44 43 # 1 0 0 1 r4 1 5 1 51 # \u0000 6 1 10 IdGlobal 1 11 Position 0 12 ValChar 2 13 ValDate 1 14 ValNum 0 15 ValComment # \u0000*/";


/*
 *  Produce the list of (non terminated) treatments given the idglobal of the 
 *  form treatment and the current date (day). 
 *  NB: Event.DateFin is the date the treatment is terminated. 
 *
 *  Attention: the end date must be initialized at year 9999 and not at NULL: 
 *  the database do support NULL values for dates but a comparaison with a NULL 
 *  value is always false.
 *
 *  OUT: 	ValChar:=Info.ValChar, ValNum:=Info.ValNum,
 *  		ValDate:=Info.ValDate, Position:=Info.Position,
 *  		TSSPT:=Info.TSSPT, DateFin:=Event.DateFin,
 *  		IdEvent:=Event.IdGlobal, IdUser:=Event.IdUser
 *  IN:  	?1:=Formulaire.IdGlobal
 *     		-->	IdGlobal of Formulaire.Nom="Traitement"
 *     			(1013 in v. n°379 de fromSanteos.xml).
 *       	?2:=Event.DateFin
 *     		--> current date
 */
// list of (non terminated) treatments 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE =
/* SQL : 	SELECT I.ValChar, I.ValNum, I.ValDate, I.Position, I.TSSPT, E.DateFin, E.IdGlobal, E.IdUser FROM Info I, Event E WHERE I.IdEvent = E.IdGlobal AND E.IdForm = ? AND E.DateFin > ?; */
"/*EP \u0002 0 6 6 4 # " +       // scan of table info(4) --> r0 (infoPos)
"1 5 5 6 r0 4 1 0 0 1 2 3 # " +  // TABLE LOOKUP pos r0, 4 cols, SKTinfo(1), is SKT(0), 0 (formPos->5), 1 (userPos->10), 2 (episodePos->0), 3 (EventPos->28) --> r1-r4
"8 4 4 5 0 r2 r1 r3 # " +        // access_type(0:read), in_res_UserDMSP(r2), in_res_Formulaire(r1), in_res_Episode(r3)
"1 3 3 4 r4 4 3 1 32 95 28 33 # " +    // TABLE LOOKUP pos r4, 3 col, table EVENT(3), is table(1), 32(IDForm), 95(DateFin), 28(IdGlobal), 33(IdUser) -->  r5-r8
"4 2 2 3 32 0 ?1 r5 # " +        // SELECT IdForm(32), equal(0), compare param 1, with value in r2 --> r8 (param1: IdForm)
"4 1 1 2 95 1 ?2 r6 # " +        // SELECT DateFin(95), greater(1), compare param 2, with value in r3 --> r9 (param2: Date)
"1 0 0 1 r0 5 4 1 42 43 44 45 38 # " +  // TABLE LOOKUP pos r0, 5 cols, table info(4), is table(1), 42(ValChar), 43(ValNum), 44(ValDate), 45(Pos), 38(TSSPT) -->  r10-r14
"\u0000 8 0 11 ValChar 1 12 ValNum 2 13 ValDate 1 14 Position 1 15 TSSPT 2 6 DateFin 1 7 IdEvent 1 8 IdUser # \u0000*/";
// 8 cols, Type_i(0 String, 1 Num, 2 Date), Ri , Name_i 


/*
 *  Produce the list of Plan d'aide or Plan d'accompagnement given the idglobal 
 *  of the form Plan d'aide or Plan d'accompagnement. 
 *
 *
 *  OUT: 	ValChar:=Info.ValChar, ValNum:=Info.ValNum,
 *  		ValDate:=Info.ValDate, Position:=Info.Position,
 *  IN:  	?1:=Formulaire.IdGlobal
 *     		-->	IdGlobal of Formulaire.Nom="Plan d'accompagnement"
 *     			(1016 in v. n°379 de fromSanteos.xml).
 */
// list of Planning d’intervention 
public static String EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT =
/* SQL : 	SELECT I.ValChar, I.ValNum, I.ValDate, I.Position FROM Info I, Event E WHERE I.IdEvent = E.IdGlobal AND E.IdForm=?; */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 4 1 0 1 0 2 3 # 8 3 3 4 0 r1 r2 r3 # 1 2 2 3 r4 1 3 1 32 # 4 1 1 2 32 0 ?1 r5 # 1 0 0 1 r0 4 4 1 42 43 44 45 # \u0000 4 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position # \u0000*/";


/*
 *  List of the comments corresponding to a given cell in a form in UI.
 *  The cell is identified by its IdConcept and IdEvent.
 *
 *  OUT: 	Position:=Info.Position, ValComment:=Comment.ValComment 
 *  IN:  ?1:=Info.IdConcept
 *     		--> idglobal of the concept of the cell
 *       ?2:=Event.IdGlobal
 *     		--> idglobal of the event corresponding to the form
 */
// list of comments corresponding to a given cell in a form 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT =
/* SQL : 	SELECT i.Position, c.ValComment FROM Info i, Comment c, Event e WHERE e.IdGlobal = i.IdEvent AND i.IdComment = c.IdGlobal AND e.Idglobal=? AND i.IdConcept=?; */
"/*EP \u0002 0 7 7 4 # 1 6 6 7 r0 2 4 1 45 92 # 4 5 5 6 92 0 ?2 r2 # 1 4 4 5 r0 5 1 0 1 0 2 4 3 # 8 3 3 4 0 r4 r5 r6 # 1 2 2 3 r8 1 3 1 28 # 4 1 1 2 28 0 ?1 r9 # 1 0 0 1 r7 1 5 1 51 # \u0000 2 1 1 Position 0 11 ValComment # \u0000*/";


/*
 *  Access Event.DateFin given Event.IdGlobal.
 *  Used to fill DateFin cell in form treatment. 
 *  OUT: DateFin:=Event.DateFin 
 *  IN:  ?1:=Event.IdGlobal
 */
// Access DateFin of the given event 
public static String EP_QUERY_AC_SELECT_EVENT_BY_ID = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT DateFin FROM Event WHERE IdGlobal = ?; */
"/*EP \u0001 2 3 3 -1 5 ?1 # 1 2 2 3 r1 3 0 0 1 0 2 # 8 1 1 2 0 r2 r3 r4 # 1 0 0 1 r1 1 3 1 95 # \u0000 1 2 5 DateFin # \u0000*/";


/*
 *  Access Habilitation.IdGlobal.
 *  Used to identifiy the set of habilited users. 
 *  OUT: IdUser:=Habilitation.IdUser 
 *  IN:  
 */
// Access Habilitation.IdGlobal 
public static String EP_QUERY_NOAC_SELECT_HABILITATION = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	SELECT IdUser,IdGlobal FROM habilitation; */
"/*EP \u0000 0 1 1 8 # 1 0 0 1 r0 2 8 1 63 57 # \u0000 2 1 1 IdUser 1 2 IdGlobal # \u0000*/";


/*
 *  Produce the list of Planning d’interventions given the idglobal of the 
 *  form Planning d’intervention. 
 *
 *
 *  OUT: 	ValChar:=Info.ValChar, ValNum:=Info.ValNum,
 *  		ValDate:=Info.ValDate, Position:=Info.Position,
 *  		IdEvent:=Event.IdGlobal
 *  IN:  	?1:=Formulaire.IdGlobal
 *     		-->	IdGlobal of Formulaire.Nom="Planning d’intervention"
 *     			(1010 in v. n°379 de fromSanteos.xml).
 */
// list of Planning d’intervention 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM =
/* SQL : 	SELECT I.ValChar, I.ValNum, I.ValDate, I.Position, E.IdGlobal IdEvent FROM Info I, Event E WHERE I.IdEvent = E.IdGlobal AND E.IdForm=?; */
"/*EP \u0001 0 5 5 4 # 1 4 4 5 r0 4 1 0 1 0 2 3 # 8 3 3 4 0 r1 r2 r3 # 1 2 2 3 r4 1 3 1 32 # 4 1 1 2 32 0 ?1 r5 # 1 0 0 1 r0 5 4 1 42 43 44 45 40 # \u0000 5 0 7 ValChar 1 8 ValNum 2 9 ValDate 1 10 Position 1 11 IdEvent # \u0000*/";


/* to get only the names of each current treatments from the form Certificat Medical
 *  
 *
 *  OUT: 	ValChar:=Info.ValChar
 *  IN:  ?1:=Formulaire.IdGlobal
 *     		-->	IdGlobal of Formulaire.Nom="Certificat Medical"
 *     			(1015 in v. n°379 de fromSanteos.xml).
 *     	?2:=Info.Position
 *     		--> 
 */
// the names of each current treatments 
public static final String EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_POSITION =
/* SQL : 	SELECT i.ValChar FROM Event e, Info i WHERE i.IdEvent = e.IdGlobal AND e.IdForm=? AND i.Position = ?; */
"/*EP \u0002 0 6 6 4 # 1 5 5 6 r0 2 4 1 42 45 # 4 4 4 5 45 0 ?2 r2 # 1 3 3 4 r0 4 1 0 1 0 2 3 # 8 2 2 3 0 r4 r5 r6 # 1 1 1 2 r7 1 3 1 32 # 4 0 0 1 32 0 ?1 r8 # \u0000 1 0 1 ValChar # \u0000*/";


// To get all the habilitated users 
//public static final String EP_QUERY_SELECT_USER_HABILITATION =
/* SQL : 	SELECT u.Nom, u.Prenom, u.Type, u.Tel, u.IdGlobal, h.IdRole FROM UserDMSP u, Habilitation h WHERE u.IdGlobal = h.IdUser; */
//"/*EP \u0000 0 3 3 8 # 1 2 2 3 r0 1 2 0 1 # 1 1 1 2 r1 5 2 1 14 19 15 23 10 # 1 0 0 1 r0 1 8 1 62 # \u0000 6 0 2 Nom 0 3 Prenom 1 4 Type 0 5 Tel 1 6 IdGlobal 1 7 IdRole # \u0000*/";

public static final String EP_QUERY_SELECT_USER_HABILITATION =
/* SQL : 	SELECT u.Nom, u.Prenom, u.Type, u.Tel, u.IdGlobal, h.IdRole FROM UserDMSP u, Habilitation h WHERE u.IdGlobal = h.IdUser; */
"/*EP \u0000 " + 
"0 3 3 8 # " + /* SCAN, Table Habilitation (8) -->R0 */
"1 2 2 3 r0 1 2 0 1 # " + /* TABLE_LOOKUP pos:R0, 1 cols, skt SktHabili(2), is_table: 0  -->R1 - R1 */
"1 1 1 2 r1 5 2 1 14 19 15 23 10 # " + /* TABLE_LOOKUP pos:R1, 5 cols, table UserDMSP(2), is_table: 1	-->R2 - R6 */
"1 0 0 1 r0 1 8 1 62 # " + /* TABLE_LOOKUP pos:R0, 1 cols, table Habilitation(8), is_table: 1	-->R7 - R7 */
"\u0000 6 0 2 Nom 0 3 Prenom 1 4 Type 0 5 Tel 1 6 IdGlobal 1 7 IdRole # " + /* META_RESULT, 6 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";


/*
 *  Insertion dans la table event. Fait référence à un Formulaire.IdGlobal,
 *  UserDMSP.IdGlobal, Episode.IdGlobal existants.
 *
 *  Attention: c'est l'Episode.IdGLobal du premier épisode inséré qui
 *  correspond à l'épisode null
 *  Dans v. n°379 de fromSanteos.xml ==> Nom = "noEpisode", IdGlobal=2
 *
 *  Droit d'accès: l'utilisateur connecté doit avoir le privilège en insertion
 *  pour le Formulaire.IdGlobal spécifié.
 *
 *  OUT: 	idGlobal du tuple inséré
 *  IN:	?1:=Event.Author
 *     		-->	SPT_ID (java.sql.Connection.getSptIdPatient)
 *     	?2:=Event.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?3:=Event.IdForm
 *     		--> Formulaire.IdGLobal (must exist)
 *     	?4:=Event.IdUser
 *     		--> UserDMSP.IdGLobal (must exist)
 *     	?5:=Event.IdEpisode
 *     		--> Episode.IdGLobal (must exist)
 *     	?6:=Event.DateEvent
 *     		--> current date
 *     	?7:=Event.DateFin
 *     		--> Formulaire.Nom="traitement" ==> specified date (UI)
 *     			or 3000-01-01 if not specified
 *     		--> else ==> NULL
 *     	?8:=Event.Filtre
 *     		--> NULL
 */
public static final String OEP_EVENT_AC_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Event (Author, TSSPT, TSSanteos, IdForm, IdUser, IdEpisode, DateEvent, DateFin, Filtre) VALUES (?, ?, 0, ?, ?, ?, ?, ?, ?); */
"/*EP \u0008 2 7 7 -1 1 ?3 # 2 8 8 -1 4 ?4 # 7 6 6 7 8 # 2 9 9 -1 0 ?5 # 7 5 5 6 9 # 8 4 4 5 3 r3 r1 r5 # 6 3 3 4 2 ?3 # 6 2 2 3 5 v30 # 5 1 1 2 3 0 0 0 r1 1 r3 2 r5 # 5 0 0 1 10 3 1 29 ?1 30 ?2 31 v10 32 ?3 33 ?4 34 ?5 35 ?6 95 ?7 96 ?8 28 r7 # \u0000*/";


/*
 *  Insertion dans la table info, référence à un Comment.IdGlobal,
 *  Event.IdGlobal existant.
 *  Attention: le champ Info.IdConcept n'est pas considérée comme une
 *  référence dans la BD mais comme un champ classique (pas de
 *  vérification d'intégrité)
 *  Droit d'accès: l'utilisateur connecté doit avoir le privilège en insertion
 *  pour le Formulaire.IdGlobal correspondant à spécifié.
 *
 *  OUT: 	idGlobal du tuple inséré
 *  IN:	?1:=Info.Author
 *     		-->	SPT_ID (java.sql.Connection.getSptIdPatient)
 *     	?2:=Info.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?3:=Info.IdEvent
 *     		--> Event.IdGLobal (must exist)
 *     	?4:=Info.IdComment
 *     		--> Comment.IdGLobal (must exist)
 *     	?5:=Info.ValChar
 *     		--> CHAR value inserted by UI (or NULL)
 *     	?6:=Info.ValNum
 *     		--> INTEGER value inserted by UI (or NULL)
 *     	?7:=Info.ValDate
 *     		--> DATE value inserted by UI (or NULL)
 *     	?8:=Info.Position
 *     		--> position of the cell in the form, decided in hard with
 *     			Santeos (gaps are let for potentially long free text)
 *     	?9:=Info.Filtre
 *     		--> Formulaires.Nom="Passage d un intervenant" ==> 0 or 1
 *     			Formulaires.Nom"Entourage" & Info.Position IN {...} ==> xx
 *     			else default value dd
 *     			[xx and dd are defined with Santeos]
 *     	?10:=Info.IdConcept
 *     		--> concept id of the value, decided in hard with Santeos
 */
public static final String OEP_INFO_AC_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Info (Author, TSSPT, TSSanteos, IdEvent, IdComment, ValChar, ValNum, ValDate, Position, Filtre, IdConcept) VALUES (?, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?); */
"/*EP \n 2 8 8 -1 5 ?3 # 1 7 7 8 r1 3 0 0 0 1 2 # 8 6 6 7 3 r3 r2 r4 # 1 5 5 6 r2 1 1 1 5 # 2 9 9 -1 7 ?4 # 7 4 4 5 9 # 6 3 3 4 3 r5 # 6 2 2 3 6 v30 # 5 1 1 2 5 1 0 0 r2 1 r3 2 r4 3 r1 4 r7 # 5 0 0 1 12 4 1 37 ?1 38 ?2 39 v10 40 ?3 41 ?4 42 ?5 43 ?6 44 ?7 45 ?8 46 ?9 92 ?10 36 r8 # \u0000*/";


/*
 *  Insertion dans la table comment. Le comment inséré doit ensuite etre
 *  couplé à un Info. Si l'info correpsondant n'est pas inséré, le comment ne
 *  sera pas accessible, pas meme pour la synchro.
 *
 *  OUT: 	idGlobal du tuple inséré
 *  IN:	?1:=Comment.Author
 *     		-->	SPT_ID (java.sql.Connection.getSptIdPatient)
 *     	?2:=Comment.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?3:=Comment.ValComment
 *     		--> value filled in UI, max 540 Bytes
 */
public static final String OEP_COMMENT_NOAC_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO Comment (Author, TSSPT, TSSanteos, ValComment) VALUES (?, ?, 0, ?); */
"/*EP \u0003 6 1 1 -1 7 v30 # 5 0 0 1 5 5 1 48 ?1 49 ?2 50 v10 51 ?3 47 r0 # \u0000*/";


/*
 *  Insertion dans la table habilitation, référence à un UserDMSP.IdGlobal,
 *  Role.IdGlobal existant.
 *  Droit d'accès: rien de prévu pour l'instant.
 *
 *  OUT: 	idGlobal du tuple inséré
 *  IN:	?1:=Info.Author
 *     		-->	SPT_ID (java.sql.Connection.getSptIdPatient)
 *     	?2:=Info.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?3:=Info.TSSanteos
 *     		--> 0
 *     	?4:=Info.IdRole
 *     		--> Role.IdGLobal (must exist)
 *     	?5:=Info.IdUser
 *     		--> UserDMSP.IdGLobal (must exist)
 */
public static String EP_HABILITATION_NOAC_INSERT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL : 	INSERT INTO habilitation (Author, TSSPT, TSSanteos, IdRole, IdUSer) VALUES (?, ?, ?, ?, ?); */
"/*EP \u0005 2 4 4 -1 8 ?4 # 2 5 5 -1 4 ?5 # 7 3 3 4 5 # 6 2 2 3 9 v30 # 5 1 1 2 2 2 0 0 r1 1 r3 # 5 0 0 1 6 8 1 58 ?1 59 ?2 60 ?3 62 ?4 63 ?5 57 r4 # \u0000*/";


public static String EP_DELETED_NOAC_INSERT =
/* SQL : 	INSERT INTO TupleDeleted (IdGlobal, TabId, Author, TSSPT, TSSanteos) VALUES (?, ?, ?, ?, ?); */
"/*EP \u0005 5 0 0 -1 5 11 1 80 ?1 81 ?2 82 ?3 83 ?4 84 ?5 # \u0000*/";


/*
 *  Update dans la table info.
 *  Droit d'accès: l'utilisateur connecté doit avoir le privilège en modification
 *  pour le Formulaire.IdGlobal correspondant.
 *
 *  OUT: 	number of updated tuples
 *  IN:	?1:=Info.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?2:=Info.ValChar
 *     		--> CHAR value modified by UI (or old value)
 *     	?3:=Info.ValNum
 *     		--> INTEGER value modified by UI (or old value)
 *     	?4:=Info.ValDate
 *     		--> DATE value modified by UI (or old value)
 *     	?5:=Info.IdGLobal
 *     		--> IdGLobal of the tuple to update
 */
public static final String INFO_AC_UPDATE_BY_ID =
/* SQL : 	UPDATE Info SET TSSPT=?, ValChar=?, ValNum=?, ValDate=?, Filtre=? WHERE IdGlobal=?; */
"/*EP \u0006 0 5 5 4 # 1 4 4 5 r0 7 4 1 37 39 40 41 45 92 36 # 4 3 3 4 36 0 ?6 r7 # 1 2 2 3 r0 3 1 0 1 0 2 # 8 1 1 2 1 r9 r10 r11 # a 0 0 1 12 4 r0 36 ?6 37 r1 38 ?1 39 r2 40 r3 41 r4 42 ?2 43 ?3 44 ?4 45 r5 46 ?5 92 r6 # \u0000*/";


/*
 *  Update dans la table comment.
 *  Droit d'accès: l'utilisateur connecté doit avoir le privilège en modification
 *  pour le Formulaire.IdGlobal correspondant à l'Info référencant ce Comment.
 *
 *  OUT: number of updated tuples
 *  IN:	?1:=Comment.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?2:=Comment.ValComment
 *     		--> new value if updated in UI, otherwise old value
 *     	?3:=Comment.IdGLobal
 *     		--> IdGLobal of the tuple to update
 */
public static final String COMMENT_AC_UPDATE_BY_ID =
// AC UPDATE Comment SET TSSPT = ?, ValComment = ? WHERE IdGlobal = ?;
"/*EP \u0003 0 5 5 4 # 1 4 4 5 r0 4 1 0 0 1 2 4 # 8 3 3 4 1 r2 r1 r3 # 1 2 2 3 r4 3 5 1 47 48 50 # 4 1 1 2 47 0 ?3 r5 # a 0 0 1 5 5 r4 47 r5 48 r6 49 ?1 50 r7 51 ?2 # \u0000*/";


/*
 *  Modification dans la table event.
 *
 *  Droit d'accès: l'utilisateur connecté doit avoir le privilège en modification
 *  pour le Formulaire.IdGlobal référencé.
 *
 *  OUT: 	idGlobal du tuple inséré
 *  IN:	?1:=Event.TSSPT
 *     		--> TSSPT (java.sql.Connection.getGlobalTimestamp)
 *     	?2:=Event.DateEvent
 *     		--> new value if updated in UI, otherwise old value
 *     	?3:=Event.DateFin
 *     		--> new value if updated in UI, otherwise old value
 *     	?4:=Event.Filtre
 *     		--> new value if updated in UI, otherwise old value
 *     	?5:=Event.IdGLobal
 *     		--> IdGLobal of the tuple to update
 */
public static final String EVENT_AC_UPDATE_BY_ID =
/* SQL : 	UPDATE Event SET TSSPT = ?, DateEvent = ?, DateFin = ?, Filtre = ? WHERE IdGlobal = ?; */
"/*EP \u0005 0 5 5 3 # 1 4 4 5 r0 6 3 1 29 31 32 33 34 28 # 4 3 3 4 28 0 ?5 r6 # 1 2 2 3 r0 3 0 0 1 0 2 # 8 1 1 2 1 r8 r9 r10 # a 0 0 1 10 3 r0 28 ?5 29 r1 30 ?1 31 r2 32 r3 33 r4 34 r5 35 ?2 95 ?3 96 ?4 # \u0000*/";


/*
 *  Remove habilitation(s) given the IdGlobal of a UserDMSP.
 *  NB: authorizations for running this query ?
 *  OUT: 	Number of deleted tuples
 *  IN:	?1:=UserDMSP.IdGlobal
 *     		--> user for which habilitation is removed
 */
public static final String EP_HABILITATION_NOAC_DELETE_BY_USER = 
/* SQL : 	DELETE FROM Habilitation WHERE IdUser=?; */
"/*EP \u0001 0 4 4 8 # 1 3 3 4 r0 2 8 1 57 63 # 4 2 2 3 63 0 ?1 r2 # 5 1 1 2 3 12 1 85 v18 86 r0 87 v10 # 9 0 0 1 8 r0 # \u0000*/";


public static String EP_EVENT_AC_DELETE_BY_ID =
/* SQL : 	DELETE FROM Event WHERE idGlobal = ?; */
"/*EP \u0001 0 6 6 3 # 1 5 5 6 r0 1 3 1 28 # 4 4 4 5 28 0 ?1 r1 # 1 3 3 4 r0 3 0 0 1 0 2 # 8 2 2 3 1 r3 r4 r5 # 5 1 1 2 3 12 1 85 v13 86 r0 87 v10 # 9 0 0 1 3 r0 # \u0000*/";


public static final String EP_INFO_AC_DELETE_BY_EVENT =
/* SQL : 	DELETE FROM Info WHERE IdEvent = ?; */
"/*EP \u0001 0 6 6 4 # 1 5 5 6 r0 2 4 1 36 40 # 4 4 4 5 40 0 ?1 r2 # 1 3 3 4 r0 3 1 0 1 0 2 # 8 2 2 3 1 r4 r5 r6 # 5 1 1 2 3 12 1 85 v14 86 r0 87 v10 # 9 0 0 1 4 r0 # \u0000*/";


public static String EP_INFO_AC_DELETE_BY_ID =
/* SQL : 	DELETE FROM Info WHERE IdGlobal = ?; */
"/*EP \u0001 0 6 6 4 # 1 5 5 6 r0 1 4 1 36 # 4 4 4 5 36 0 ?1 r1 # 1 3 3 4 r0 3 1 0 1 0 2 # 8 2 2 3 1 r3 r4 r5 # 5 1 1 2 3 12 1 85 v14 86 r0 87 v10 # 9 0 0 1 4 r0 # \u0000*/";


public static final String EP_QUERY_SELECT_NOAC_INFO_BY_EVENT = 
/* SQL : 	SELECT IdGlobal FROM Info WHERE IdEvent = ?; */
"/*EP \u0001 0 2 2 4 # 1 1 1 2 r0 2 4 1 36 40 # 4 0 0 1 40 0 ?1 r2 # \u0000 1 1 1 IdGlobal # \u0000*/";


public static final String EP_QUERY_NOAC_SELECT_BY_ID =
/* SQL : 	SELECT Nom, Prenom FROM UserDMSP u WHERE u.idGlobal = ?; */
"/*EP \u0001 2 1 1 -1 4 ?1 # 1 0 0 1 r1 2 2 1 14 19 # \u0000 2 0 2 Nom 0 3 Prenom # \u0000*/";


public static final String EP_QUERY_SELECT_EVENT_INFO_BY_EVENT_AND_FILTRE =
/* SQL : 	SELECT ValChar, Position FROM Info WHERE IdEvent = ? AND Filtre = ?; */
"/*EP \u0002 0 3 3 4 # 1 2 2 3 r0 4 4 1 42 45 40 46 # 4 1 1 2 40 0 ?1 r3 # 4 0 0 1 46 0 ?2 r4 # \u0000 2 0 1 ValChar 1 2 Position # \u0000*/";


public static final String EP_QUERY_AC_SELECT_EVENT_INFO_BY_EVENT_AND_POSITION =
/* SQL : 	SELECT ValNum FROM Info WHERE IdEvent = ? AND Position = ?; */
"/*EP \u0002 0 5 5 4 # 1 4 4 5 r0 3 4 1 43 40 45 # 4 3 3 4 40 0 ?1 r2 # 4 2 2 3 45 0 ?2 r3 # 1 1 1 2 r0 3 1 0 1 0 2 # 8 0 0 1 0 r6 r7 r8 # \u0000 1 1 1 ValNum # \u0000*/";


//  =============================================================================================== 
//  BELOW THIS LINE, PORTING STATUS UNKNOWN: NOBODY CHECKED IF THEY STILL WORK WITH THE NEW INDEXES 
//  =============================================================================================== 

public static final String EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_SIGMOD = 
/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm=?; */
"/*EP \u0001 2 3 3 -1 4 5 ?1 # 1 2 2 3 r1 1 1 0 4 # 1 1 1 2 r1 5 4 1 36 45 42 44 43 # 1 0 0 1 r2 1 5 1 51 # \u0000 6 1 3 IdGlobal 1 4 Dosage 0 5 Label 2 6 Datebegin 1 7 Quantity 0 8 Name # \u0000*/";


public static final String EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_OPT = // NEWSCHEMA OK // COMMENT OK // CHECK OK // TEST OK
/* SQL :     SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, c.ValComment FROM Event e, Info i, Comment c WHERE i.IdEvent = e.IdGlobal AND i.IdComment = c.IdGlobal AND e.IdForm=?; */
"/*EP \u0001 " +
"0 8 8 1 # " +                 // Scan Form (1) --> R0
"1 7 7 8 r0 1 1 1 5 # " +         // Lookup in (R0), one column (1), table form (1), a table (1), column IdGlobal (5) --> R1
"4 6 6 7 5 0 ?1 r1 # " +         // Select IdGlobal (5)  = (0)  param 1 (?1)  with (R1) --> R2
"0 5 5 4 # " +                 // Scan Info (4) --> R3
"7 4 4 6 5 # " +                 // Flow child 1 (6) child 2 (5)
"1 3 3 4 r3 2 1 0 0 4 # " +     // Lookup in (R3), 2 cols (2), SKT INFO (1), a SKT (0), columns FormID (0) --> R4, CommentID (4)--> R5
"4 2 2 3 5 0 r4 r0 # " +       // Select FormId (5 (Attention, normally should be an internal ID)) = (0) with (R0)
"1 1 1 2 r3 5 4 1 36 45 42 44 43 # " + // Lookup in (R3), 5 cols (5), Table INFO (4), a table (1), columns  36 45 42 44 43 --> R6-R10
"1 0 0 1 r5 1 5 1 51 # " +     // Lookup in (R5), 1 cols (1), table Comment (5), a table (1), coms 51 --> R11
"\u0000 6 1 6 IdGlobal 1 7 Dosage 0 8 Label 2 9 Datebegin 1 10 Quantity 0 11 Name # \u0000*/";


// liste des fiches de liaison (Info.Filtre=? pour récupérer les infos Date, Type, Origine, Cat. Prof., Obs. => un comment) 
// NB: identique à EP_QUERY_AC_SELECT_INFO_BY_FILTRE avec en + le champs IdUser, et l'accès au comment (=> valcomment à la place de IdComment) 
public static String EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE = 
/* SQL : 	SELECT i.Position, i.ValChar, i.ValNum, i.IdEvent, i.ValDate, i.TSSPT, e.IdUser, c.ValComment FROM Event e, Info i, Comment c WHERE i.Idevent=e.IdGlobal AND i.IdComment=c.IdGlobal AND i.Filtre=?;	 */
"/*EP \u0001 0 6 6 4 # 1 5 5 6 r0 6 4 1 45 42 43 44 38 46 # 4 4 4 5 46 0 ?1 r6 # 1 3 3 4 r0 5 1 0 1 0 2 3 4 # 8 2 2 3 0 r8 r9 r10 # 1 1 1 2 r11 2 3 1 28 33 # 1 0 0 1 r12 1 5 1 51 # \u0000 8 1 1 Position 0 2 ValChar 1 3 ValNum 1 13 IdEvent 2 4 ValDate 1 5 TSSPT 1 14 IdUser 0 15 ValComment # \u0000*/";

//Optimization query for the "tableau de bord" ==> OK 
//We add the form table for optimization ... 
public static String EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_BETWEEN_DATEEVENT = 
/* SQL : 	SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, i.IdEvent, c.ValComment FROM event e, info i, comment c, Formulaire f WHERE i.IdEvent=e.IdGlobal and i.IdComment=c.IdGlobal and e.IdForm = f.IdGlobal and f.IdGlobal=? and e.DateEvent > ? and e.DateEvent < ? */
"/*EP \u0003 " + 
"0 9 9 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 8 8 9 r0 5 1 0 1 0 2 4 3 # " + /* TABLE_LOOKUP pos:R0, 5 cols, skt SktInfo(1), is_table: 0  -->R1 - R5 */
"8 7 7 8 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 6 6 7 r2 1 1 1 5 # " + /* TABLE_LOOKUP pos:R2, 1 cols, table Formulaire(1), is_table: 1	-->R6 - R6 */
"4 5 5 6 5 0 ?1 r6 # " + /* SELECT, att: IdGlobal(5), comparator: 0, parameter: ?1 (R7), from pos: R6*/
"1 4 4 5 r5 1 3 1 35 # " + /* TABLE_LOOKUP pos:R5, 1 cols, table Event(3), is_table: 1	-->R8 - R8 */
"4 3 3 4 35 1 ?2 r8 # " + /* SELECT, att: DateEvent(35), comparator: 1, parameter: ?2 (R9), from pos: R8*/
"4 2 2 3 35 2 ?3 r8 # " + /* SELECT, att: DateEvent(35), comparator: 2, parameter: ?3 (R10), from pos: R8*/
"1 1 1 2 r0 6 4 1 36 45 42 44 43 40 # " + /* TABLE_LOOKUP pos:R0, 6 cols, table Info(4), is_table: 1	-->R11 - R16 */
"1 0 0 1 r4 1 5 1 51 # " + /* TABLE_LOOKUP pos:R4, 1 cols, table Comment(5), is_table: 1	-->R17 - R17 */
"\u0000 7 1 11 IdGlobal 1 12 Position 0 13 ValChar 2 14 ValDate 1 15 ValNum 1 16 IdEvent 0 17 ValComment # " + /* META_RESULT, 7 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";

//Optimization query for the "calendrier" ==> OK 
//Should replace EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE 
//Un des infoDM repéré par son concept indique dans quel cas on est : 
//(1) case du calendrier (2) note de bas de page 
//Dans tous les cas, pas besoin de comments   
//We add the form table for optimization ...
public static String EP_QUERY_AC_SELECT_INFOnoc_BY_FORM =  
/* SQL : 	SELECT i.IdEvent, i.Position, i.ValChar, i.ValDate, i.ValNum FROM event e, info i, formulaire f WHERE i.IdEvent=e.IdGlobal and e.IdForm = f.IdGlobal and f.IdGlobal=? */
"/*EP \u0001 " + 
"0 5 5 4 # " + /* SCAN, Table Info (4) -->R0 */
"1 4 4 5 r0 3 1 0 1 0 2 # " + /* TABLE_LOOKUP pos:R0, 3 cols, skt SktInfo(1), is_table: 0  -->R1 - R3 */
"8 3 3 4 0 r1 r2 r3 # " + /* ACCESS_RIGHTS access_type: 0 (0-s,1-u,2-d,3-i) res_user:R1 res_form:R2 res_epis:R3 */
"1 2 2 3 r2 1 1 1 5 # " + /* TABLE_LOOKUP pos:R2, 1 cols, table Formulaire(1), is_table: 1	-->R4 - R4 */
"4 1 1 2 5 0 ?1 r4 # " + /* SELECT, att: IdGlobal(5), comparator: 0, parameter: ?1 (R5), from pos: R4*/
"1 0 0 1 r0 5 4 1 40 45 42 44 43 # " + /* TABLE_LOOKUP pos:R0, 5 cols, table Info(4), is_table: 1	-->R6 - R10 */
"\u0000 5 1 6 IdEvent 1 7 Position 0 8 ValChar 2 9 ValDate 1 10 ValNum # " + /* META_RESULT, 5 cols, type(0-char 1-num 2-date) [out_result name] */
"\u0000*/";

} 
