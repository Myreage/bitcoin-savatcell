package org.inria.dmsp.tools;

/**
 * @author Aydogan Ersoz
 */
public class DMSP_QEP_IDs
{
	/* EP_QEP class must exist in every application. It allows to interact hardcoded QEPs inside SGBD.
	 * Application QEP start id should be greater than the value of last element of this class. */
	public static class EP_QEP // 1
	{
		public static final int EP_QEP_INSERT	= 0;
	}
	
	/* Application QEP ids */
	public static class EP_Synchro // 2
	{
		public static final int EP_INFO_SELECT_BY_ID 				= EP_QEP.EP_QEP_INSERT + 1; /* Application QEP start id */
		public static final int EP_USER_SELECT_BY_ID 				= EP_INFO_SELECT_BY_ID + 1;
		public static final int EP_EPISODE_SELECT_BY_ID 			= EP_USER_SELECT_BY_ID + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID 			= EP_EPISODE_SELECT_BY_ID + 1;
		public static final int EP_ROLE_SELECT_BY_ID 				= EP_FORMULAIRE_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_ID 				= EP_ROLE_SELECT_BY_ID + 1;
		public static final int EP_HABILITATION_SELECT_BY_ID 		= EP_EVENT_SELECT_BY_ID + 1;
		public static final int EP_PATIENT_SELECT_BY_ID 			= EP_HABILITATION_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_SELECT_BY_ID 			= EP_PATIENT_SELECT_BY_ID + 1;
		public static final int EP_INFO_SELECT_ON_TSSPT 			= EP_COMMENT_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_SELECT_ON_TSSPT 			= EP_INFO_SELECT_ON_TSSPT + 1;
		public static final int EP_EVENT_UPDATE 					= EP_COMMENT_SELECT_ON_TSSPT + 1;
		public static final int EP_INFO_UPDATE 						= EP_EVENT_UPDATE + 1;
		public static final int EP_COMMENT_UPDATE 					= EP_INFO_UPDATE + 1;
		public static final int EP_USER_UPDATE 						= EP_COMMENT_UPDATE + 1;
		public static final int EP_EPISODE_UPDATE 					= EP_USER_UPDATE + 1;
		public static final int EP_FORMULAIRE_UPDATE 				= EP_EPISODE_UPDATE + 1;
		public static final int EP_ROLE_UPDATE 						= EP_FORMULAIRE_UPDATE + 1;
		public static final int EP_EPISODE_INSERT 					= EP_ROLE_UPDATE + 1;
		public static final int EP_FORMULAIRE_INSERT 				= EP_EPISODE_INSERT + 1;
		public static final int EP_USER_INSERT 						= EP_FORMULAIRE_INSERT + 1;
		public static final int EP_PATIENT_SELECT_ON_TSSPT 			= EP_USER_INSERT + 1;
		public static final int EP_EPISODE_SELECT_ON_TSSPT 			= EP_PATIENT_SELECT_ON_TSSPT + 1;
		public static final int EP_HABILITATION_SELECT_ON_TSSPT 	= EP_EPISODE_SELECT_ON_TSSPT + 1;
		public static final int EP_EVENT_INSERT 					= EP_HABILITATION_SELECT_ON_TSSPT + 1;
		public static final int EP_INFO_INSERT 						= EP_EVENT_INSERT + 1;
		public static final int EP_COMMENT_INSERT 					= EP_INFO_INSERT + 1;
		public static final int EP_EVENT_SELECT_ON_TSSPT 			= EP_COMMENT_INSERT + 1;
		public static final int EP_USER_SELECT_ON_TSSPT 			= EP_EVENT_SELECT_ON_TSSPT + 1;
		public static final int EP_ROLE_INSERT 						= EP_USER_SELECT_ON_TSSPT + 1;
		public static final int EP_HABILITATION_INSERT 				= EP_ROLE_INSERT + 1;
		public static final int EP_FORMULAIRE_SELECT_ON_TSSPT 		= EP_HABILITATION_INSERT + 1;
		public static final int EP_HABILITATION_DELETE 				= EP_FORMULAIRE_SELECT_ON_TSSPT + 1;
		public static final int EP_INFO_DELETE 						= EP_HABILITATION_DELETE + 1;
		public static final int EP_COMMENT_DELETE 					= EP_INFO_DELETE + 1;
		public static final int EP_DELETED_SELECT_ON_TSSPT 			= EP_COMMENT_DELETE + 1;
		public static final int EP_PATIENT_INSERT_AUTRE_USER 		= EP_DELETED_SELECT_ON_TSSPT + 1;
		public static final int EP_PATIENT_INSERT_AUTRE_ROLE 		= EP_PATIENT_INSERT_AUTRE_USER + 1;
		public static final int EP_PATIENT_INSERT_USER_USER 		= EP_PATIENT_INSERT_AUTRE_ROLE + 1;
		public static final int EP_PATIENT_INSERT_USER_ROLE 		= EP_PATIENT_INSERT_USER_USER + 1;
		public static final int EP_PATIENT_INSERT_FORMULAIRE_USER 	= EP_PATIENT_INSERT_USER_ROLE + 1;
		public static final int EP_PATIENT_INSERT_FORMULAIRE_ROLE 	= EP_PATIENT_INSERT_FORMULAIRE_USER + 1;
		public static final int EP_PATIENT_INSERT_EPISODE_USER 		= EP_PATIENT_INSERT_FORMULAIRE_ROLE + 1;
		public static final int EP_PATIENT_INSERT_EPISODE_ROLE 		= EP_PATIENT_INSERT_EPISODE_USER + 1;
		public static final int EP_ROLE_SELECT_ON_TSSPT 			= EP_PATIENT_INSERT_EPISODE_ROLE + 1;
		public static final int EP_HABILITATION_UPDATE 				= EP_ROLE_SELECT_ON_TSSPT + 1;
		public static final int EP_EVENT_INSERT_SIGMOD 				= EP_HABILITATION_UPDATE + 1;
		public static final int EP_INFO_INSERT_SIGMOD 				= EP_EVENT_INSERT_SIGMOD + 1;
		public static final int EP_INFO_INSERT_OLD 					= EP_INFO_INSERT_SIGMOD + 1;
		public static final int EP_DMSP_INSERT 						= EP_INFO_INSERT_OLD + 1;
		public static final int EP_DMSP_SELECT_BY_ID 				= EP_DMSP_INSERT + 1;
		public static final int EP_DMSP_SELECT_ON_TSSPT 			= EP_DMSP_SELECT_BY_ID + 1;
		public static final int EP_DMSP_UPDATE 						= EP_DMSP_SELECT_ON_TSSPT + 1;
		public static final int EP_PATIENT_UPDATE 					= EP_DMSP_UPDATE + 1;
		public static final int EP_EVENT_DELETE 					= EP_PATIENT_UPDATE + 1;
		public static final int EP_DELETED_INSERT 					= EP_EVENT_DELETE + 1;
		public static final int EP_DELETED_SELECT_BY_ID 			= EP_DELETED_INSERT + 1;
		public static final int EP_DELETED_DELETE 					= EP_DELETED_SELECT_BY_ID + 1;
	}
	
	public static class EP_Debug // 3
	{
		public static final int EP_EVENT_SELECT_STAR 							= EP_Synchro.EP_DELETED_DELETE + 1;
		public static final int EP_INFO_SELECT_STAR 							= EP_EVENT_SELECT_STAR + 1;
		public static final int EP_COMMENT_SELECT_STAR 							= EP_INFO_SELECT_STAR + 1;
		public static final int EP_HABILITATION_SELECT_STAR 					= EP_COMMENT_SELECT_STAR + 1;
		public static final int EP_PATIENT_SELECT_STAR 							= EP_HABILITATION_SELECT_STAR + 1;
		public static final int EP_SYSTEM_SKTINFO_SELECT_STAR 					= EP_PATIENT_SELECT_STAR + 1;
		public static final int EP_SYSTEM_SKTEVENT_SELECT_STAR					= EP_SYSTEM_SKTINFO_SELECT_STAR + 1;
		public static final int EP_SYSTEM_LogDeleted 							= EP_SYSTEM_SKTEVENT_SELECT_STAR + 1;
		public static final int EP_SYSTEM_UpdateLog 							= EP_SYSTEM_LogDeleted + 1;
		public static final int EP_SYSTEM_SKTEVENT_SELECT_FORMPOS_BY_EVENTPOS	= EP_SYSTEM_UpdateLog + 1;
	}
	
	public static class EP_IDX // 4
	{
		public static final int EP_POPULATE 		= EP_Debug.EP_SYSTEM_SKTEVENT_SELECT_FORMPOS_BY_EVENTPOS + 1;
		public static final int EP_IDGLOBAL_NO 		= EP_POPULATE + 1;
		public static final int EP_IDGLOBAL_FIS 	= EP_IDGLOBAL_NO + 1;
		public static final int EP_IDGLOBAL_SS 		= EP_IDGLOBAL_FIS + 1;
		public static final int EP_IDGLOBAL_HS 		= EP_IDGLOBAL_SS + 1;
		public static final int EP_NUMDUP100_NO 	= EP_IDGLOBAL_HS + 1;
		public static final int EP_NUMDUP100_FIS 	= EP_NUMDUP100_NO + 1;
		public static final int EP_NUMDUP100_SS 	= EP_NUMDUP100_FIS + 1;
		public static final int EP_NUMDUP100_HS 	= EP_NUMDUP100_SS + 1;
		public static final int EP_NUMDUP10_NO 		= EP_NUMDUP100_HS + 1;
		public static final int EP_NUMDUP10_FIS 	= EP_NUMDUP10_NO + 1;
		public static final int EP_NUMDUP10_SS 		= EP_NUMDUP10_FIS + 1;
		public static final int EP_NUMDUP10_HS 		= EP_NUMDUP10_SS + 1;
		public static final int EP_NUMMS1_NO 		= EP_NUMDUP10_HS + 1;
		public static final int EP_NUMMS1_FIS 		= EP_NUMMS1_NO + 1;
		public static final int EP_NUMMS1_SS 		= EP_NUMMS1_FIS + 1;
		public static final int EP_NUMMS1_HS 		= EP_NUMMS1_SS + 1;
		public static final int EP_NUMMS10_NO 		= EP_NUMMS1_HS + 1;
		public static final int EP_NUMMS10_FIS 		= EP_NUMMS10_NO + 1;
		public static final int EP_NUMMS10_SS 		= EP_NUMMS10_FIS + 1;
		public static final int EP_NUMMS10_HS 		= EP_NUMMS10_SS + 1;
		public static final int EP_CHDUP100_NO 		= EP_NUMMS10_HS + 1;
		public static final int EP_CHDUP100_FIS 	= EP_CHDUP100_NO + 1;
		public static final int EP_CHDUP100_SS 		= EP_CHDUP100_FIS + 1;
		public static final int EP_CHDUP100_HS 		= EP_CHDUP100_SS + 1;
		public static final int EP_CHDUP10_NO 		= EP_CHDUP100_HS + 1;
		public static final int EP_CHDUP10_FIS 		= EP_CHDUP10_NO + 1;
		public static final int EP_CHDUP10_SS 		= EP_CHDUP10_FIS + 1;
		public static final int EP_CHDUP10_HS 		= EP_CHDUP10_SS + 1;
		public static final int EP_CHMS1_NO 		= EP_CHDUP10_HS + 1;
		public static final int EP_CHMS1_FIS 		= EP_CHMS1_NO + 1;
		public static final int EP_CHMS1_SS 		= EP_CHMS1_FIS + 1;
		public static final int EP_CHMS1_HS 		= EP_CHMS1_SS + 1;
		public static final int EP_CHMS10_NO 		= EP_CHMS1_HS + 1;
		public static final int EP_CHMS10_FIS 		= EP_CHMS10_NO + 1;
		public static final int EP_CHMS10_SS 		= EP_CHMS10_FIS + 1;
		public static final int EP_CHMS10_HS 		= EP_CHMS10_SS + 1;
		public static final int EP_FAKE 			= EP_CHMS10_HS + 1;
		public static final int EP_FAKE_DUMMY 		= EP_FAKE + 1;
	}
	
	public static class EP_PDS_FIS // 5
	{
		public static final int EP_EPISODE_SELECT_BY_ID 		= EP_IDX.EP_FAKE_DUMMY + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID 		= EP_EPISODE_SELECT_BY_ID + 1;
		public static final int EP_USER_SELECT_BY_ID 			= EP_FORMULAIRE_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_ID 			= EP_USER_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_IDFORM 		= EP_EVENT_SELECT_BY_ID + 1;
		public static final int EP_INFO_SELECT_BY_ID 			= EP_EVENT_SELECT_BY_IDFORM + 1;
		public static final int EP_COMMENT_SELECT_BY_ID 		= EP_INFO_SELECT_BY_ID + 1;
		public static final int EP_ROLE_SELECT_BY_ID 			= EP_COMMENT_SELECT_BY_ID + 1;
		public static final int EP_HABILITATION_SELECT_BY_ID 	= EP_ROLE_SELECT_BY_ID + 1;
		public static final int EP_EVENT_UPDATE 				= EP_HABILITATION_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_UPDATE 				= EP_EVENT_UPDATE + 1;
		public static final int EP_INFO_DELETE 					= EP_COMMENT_UPDATE + 1;
		public static final int EP_EVENT_DELETE 				= EP_INFO_DELETE + 1;
	}
	
	public static class EP_PDS_SS // 6
	{
		public static final int EP_EPISODE_SELECT_BY_ID 		= EP_PDS_FIS.EP_EVENT_DELETE + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID 		= EP_EPISODE_SELECT_BY_ID + 1;
		public static final int EP_USER_SELECT_BY_ID 			= EP_FORMULAIRE_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_ID 			= EP_USER_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_IDFORM 		= EP_EVENT_SELECT_BY_ID + 1;
		public static final int EP_INFO_SELECT_BY_ID 			= EP_EVENT_SELECT_BY_IDFORM + 1;
		public static final int EP_COMMENT_SELECT_BY_ID 		= EP_INFO_SELECT_BY_ID + 1;
		public static final int EP_ROLE_SELECT_BY_ID 			= EP_COMMENT_SELECT_BY_ID + 1;
		public static final int EP_HABILITATION_SELECT_BY_ID	= EP_ROLE_SELECT_BY_ID + 1;
		public static final int EP_EVENT_UPDATE 				= EP_HABILITATION_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_UPDATE 				= EP_EVENT_UPDATE + 1;
		public static final int EP_INFO_DELETE 					= EP_COMMENT_UPDATE + 1;
		public static final int EP_EVENT_DELETE 				= EP_INFO_DELETE + 1;
	}
	
	public static class EP_PDS_TEST // 7
	{
		public static final int EP_FORMULAIRE_INSERT 					= EP_PDS_SS.EP_EVENT_DELETE + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID 				= EP_FORMULAIRE_INSERT + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID_NO_CI 		= EP_FORMULAIRE_SELECT_BY_ID + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID_FIS 			= EP_FORMULAIRE_SELECT_BY_ID_NO_CI + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID_SS 			= EP_FORMULAIRE_SELECT_BY_ID_FIS + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_FILTRE 			= EP_FORMULAIRE_SELECT_BY_ID_SS + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI 	= EP_FORMULAIRE_SELECT_BY_FILTRE + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_FILTRE_FIS 		= EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_FILTRE_SS 		= EP_FORMULAIRE_SELECT_BY_FILTRE_FIS + 1;
	}
	
	public static class EP_PDS // 8
	{
		public static final int EP_EPISODE_INSERT 				= EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_FILTRE_SS + 1;
		public static final int EP_EPISODE_SELECT_BY_ID 		= EP_EPISODE_INSERT + 1;
		public static final int EP_FORMULAIRE_INSERT 			= EP_EPISODE_SELECT_BY_ID + 1;
		public static final int EP_FORMULAIRE_SELECT_BY_ID 		= EP_FORMULAIRE_INSERT + 1;
		public static final int EP_USER_INSERT 					= EP_FORMULAIRE_SELECT_BY_ID + 1;
		public static final int EP_USER_SELECT_BY_ID 			= EP_USER_INSERT + 1;
		public static final int EP_EVENT_INSERT 				= EP_USER_SELECT_BY_ID + 1;
		public static final int EP_EVENT_SELECT_BY_ID 			= EP_EVENT_INSERT + 1;
		public static final int EP_EVENT_SELECT_BY_IDFORM 		= EP_EVENT_SELECT_BY_ID + 1;
		public static final int EP_INFO_INSERT 					= EP_EVENT_SELECT_BY_IDFORM + 1;
		public static final int EP_INFO_SELECT_BY_ID 			= EP_INFO_INSERT + 1;
		public static final int EP_COMMENT_INSERT 				= EP_INFO_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_SELECT_BY_ID 		= EP_COMMENT_INSERT + 1;
		public static final int EP_ROLE_INSERT 					= EP_COMMENT_SELECT_BY_ID + 1;
		public static final int EP_ROLE_SELECT_BY_ID 			= EP_ROLE_INSERT + 1;
		public static final int EP_HABILITATION_INSERT 			= EP_ROLE_SELECT_BY_ID + 1;
		public static final int EP_HABILITATION_SELECT_BY_ID 	= EP_HABILITATION_INSERT + 1;
		public static final int EP_EVENT_UPDATE 				= EP_HABILITATION_SELECT_BY_ID + 1;
		public static final int EP_COMMENT_UPDATE 				= EP_EVENT_UPDATE + 1;
		public static final int EP_INFO_DELETE 					= EP_COMMENT_UPDATE + 1;
		public static final int EP_EVENT_DELETE 				= EP_INFO_DELETE + 1;
	}
	
	public static class EP_UI // 9
	{
		public static final int EP_QUERY_AC_SELECT_USER_EVENT_INFO_BY_FILTRE 								= EP_PDS.EP_EVENT_DELETE + 1;
		public static final int EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE_BETWEEN_DATEEVENT 		= EP_QUERY_AC_SELECT_USER_EVENT_INFO_BY_FILTRE + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_pos_num_BY_FORM 									= EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE_BETWEEN_DATEEVENT + 1;
		public static final int EP_QUERY_NOAC_SELECT_nom_prenom_type_tel_idglobal_USER 						= EP_QUERY_AC_SELECT_INFO_pos_num_BY_FORM + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_civilite_nom_prenom_BY_IDGLOBAL 					= EP_QUERY_NOAC_SELECT_nom_prenom_type_tel_idglobal_USER + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_BY_EVENT_AND_FILTRE 								= EP_QUERY_NOAC_SELECT_USER_civilite_nom_prenom_BY_IDGLOBAL + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_valnum_BY_EVENT_AND_POSITION 						= EP_QUERY_AC_SELECT_INFO_BY_EVENT_AND_FILTRE + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_BY_FORM 											= EP_QUERY_AC_SELECT_INFO_valnum_BY_EVENT_AND_POSITION + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_BY_CERTIF 										= EP_QUERY_AC_SELECT_INFO_BY_FORM + 1;
		public static final int EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER 								= EP_QUERY_NOAC_SELECT_USER_BY_CERTIF + 1;
		public static final int EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM 									= EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER + 1;
		public static final int EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE 								= EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE 								= EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE + 1;
		public static final int EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM 							= EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM 								= EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT 								= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_BY_ID 											= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_BY_FILTRE 											= EP_QUERY_NOAC_SELECT_USER_BY_ID + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE 									= EP_QUERY_AC_SELECT_INFO_BY_FILTRE + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER 													= EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE 					= EP_QUERY_NOAC_SELECT_USER + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT 		= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE 								= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT 							= EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT 					= EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_BY_ID 												= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT + 1;
		public static final int EP_QUERY_NOAC_SELECT_HABILITATION 											= EP_QUERY_AC_SELECT_EVENT_BY_ID + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM 										= EP_QUERY_NOAC_SELECT_HABILITATION + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_POSITION 							= EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM + 1;
		public static final int EP_QUERY_SELECT_USER_HABILITATION 											= EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_POSITION + 1;
		public static final int OEP_EVENT_AC_INSERT 														= EP_QUERY_SELECT_USER_HABILITATION + 1;
		public static final int OEP_INFO_AC_INSERT 															= OEP_EVENT_AC_INSERT + 1;
		public static final int OEP_COMMENT_NOAC_INSERT 													= OEP_INFO_AC_INSERT + 1;
		public static final int EP_HABILITATION_NOAC_INSERT 												= OEP_COMMENT_NOAC_INSERT + 1;
		public static final int EP_DELETED_NOAC_INSERT 														= EP_HABILITATION_NOAC_INSERT + 1;
		public static final int INFO_AC_UPDATE_BY_ID 														= EP_DELETED_NOAC_INSERT + 1;
		public static final int COMMENT_AC_UPDATE_BY_ID 													= INFO_AC_UPDATE_BY_ID + 1;
		public static final int EVENT_AC_UPDATE_BY_ID 														= COMMENT_AC_UPDATE_BY_ID + 1;
		public static final int EP_HABILITATION_NOAC_DELETE_BY_USER 										= EVENT_AC_UPDATE_BY_ID + 1;
		public static final int EP_EVENT_AC_DELETE_BY_ID 													= EP_HABILITATION_NOAC_DELETE_BY_USER + 1;
		public static final int EP_INFO_AC_DELETE_BY_EVENT 													= EP_EVENT_AC_DELETE_BY_ID + 1;
		public static final int EP_INFO_AC_DELETE_BY_ID 													= EP_INFO_AC_DELETE_BY_EVENT + 1;
		public static final int EP_QUERY_SELECT_NOAC_INFO_BY_EVENT 											= EP_INFO_AC_DELETE_BY_ID + 1;
		public static final int EP_QUERY_NOAC_SELECT_BY_ID 													= EP_QUERY_SELECT_NOAC_INFO_BY_EVENT + 1;
		public static final int EP_QUERY_SELECT_EVENT_INFO_BY_EVENT_AND_FILTRE 								= EP_QUERY_NOAC_SELECT_BY_ID + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_BY_EVENT_AND_POSITION 						= EP_QUERY_SELECT_EVENT_INFO_BY_EVENT_AND_FILTRE + 1;
		public static final int EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_SIGMOD 						= EP_QUERY_AC_SELECT_EVENT_INFO_BY_EVENT_AND_POSITION + 1;
		public static final int EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_OPT 						= EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_SIGMOD + 1;
		public static final int EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE 						= EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_OPT + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_BETWEEN_DATEEVENT				= EP_QUERY_AC_SELECT_USER_EVENT_INFO_COMMENT_BY_FILTRE + 1;
		public static final int EP_QUERY_AC_SELECT_INFOnoc_BY_FORM 											= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_BETWEEN_DATEEVENT + 1;
	}
	
	public static class EP_TEST // 10
	{
		public static final int EP_EVENT_DELETE_ALL 									= EP_UI.EP_QUERY_AC_SELECT_INFOnoc_BY_FORM + 1;
		public static final int EP_INFO_DELETE_ALL 										= EP_EVENT_DELETE_ALL + 1;
		public static final int EP_EPISODE_DELETE_ALL 									= EP_INFO_DELETE_ALL + 1;
		public static final int EP_FORMULAIRE_DELETE_ALL 								= EP_EPISODE_DELETE_ALL + 1;
		public static final int EP_USER_DELETE_ALL 										= EP_FORMULAIRE_DELETE_ALL + 1;
		public static final int EP_COMMENT_DELETE_ALL 									= EP_USER_DELETE_ALL + 1;
		public static final int EP_ROLE_DELETE_ALL 										= EP_COMMENT_DELETE_ALL + 1;
		public static final int EP_HABILITATION_DELETE_ALL 								= EP_ROLE_DELETE_ALL + 1;
		public static final int EP_EVENT_INSERT 										= EP_HABILITATION_DELETE_ALL + 1;
		public static final int EP_INFO_INSERT 											= EP_EVENT_INSERT + 1;
		public static final int EP_COMMENT_INSERT 										= EP_INFO_INSERT + 1;
		public static final int EP_EPISODE_SELECT_STAR 									= EP_COMMENT_INSERT + 1;
		public static final int EP_FORMULAIRE_SELECT_STAR 								= EP_EPISODE_SELECT_STAR + 1;
		public static final int EP_USER_SELECT_STAR 									= EP_FORMULAIRE_SELECT_STAR + 1;
		public static final int EP_EVENT_SELECT_STAR 									= EP_USER_SELECT_STAR + 1;
		public static final int EP_INFO_SELECT_STAR 									= EP_EVENT_SELECT_STAR + 1;
		public static final int EP_COMMENT_SELECT_STAR 									= EP_INFO_SELECT_STAR + 1;
		public static final int EP_ROLE_SELECT_STAR 									= EP_COMMENT_SELECT_STAR + 1;
		public static final int EP_HABILITATION_SELECT_STAR 							= EP_ROLE_SELECT_STAR + 1;
		public static final int EP_PATIENT_SELECT_STAR 									= EP_HABILITATION_SELECT_STAR + 1;
		public static final int EP_FORMULAIRE_INSERT 									= EP_PATIENT_SELECT_STAR + 1;
		public static final int EP_USER_INSERT 											= EP_FORMULAIRE_INSERT + 1;
		public static final int EP_ROLE_INSERT 											= EP_USER_INSERT + 1;
		public static final int EP_EPISODE_INSERT 										= EP_ROLE_INSERT + 1;
		public static final int EP_PATIENT_INSERT_AUTRE_USER 							= EP_EPISODE_INSERT + 1;
		public static final int EP_PATIENT_INSERT_AUTRE_ROLE 							= EP_PATIENT_INSERT_AUTRE_USER + 1;
		public static final int EP_PATIENT_INSERT_USER_USER								= EP_PATIENT_INSERT_AUTRE_ROLE + 1;
		public static final int EP_PATIENT_INSERT_USER_ROLE 							= EP_PATIENT_INSERT_USER_USER + 1;
		public static final int EP_PATIENT_INSERT_FORMULAIRE_USER 						= EP_PATIENT_INSERT_USER_ROLE + 1;
		public static final int EP_PATIENT_INSERT_FORMULAIRE_ROLE 						= EP_PATIENT_INSERT_FORMULAIRE_USER + 1;
		public static final int EP_PATIENT_INSERT_EPISODE_USER 							= EP_PATIENT_INSERT_FORMULAIRE_ROLE + 1;
		public static final int EP_PATIENT_INSERT_EPISODE_ROLE 							= EP_PATIENT_INSERT_EPISODE_USER + 1;
		public static final int OEP_EVENT_AC_INSERT 									= EP_PATIENT_INSERT_EPISODE_ROLE + 1;
		public static final int OEP_INFO_AC_INSERT 										= OEP_EVENT_AC_INSERT + 1;
		public static final int EP_SKTINFO_SELECT_STAR 									= OEP_INFO_AC_INSERT + 1;
		public static final int EP_SKTEVENT_SELECT_STAR 								= EP_SKTINFO_SELECT_STAR + 1;
		public static final int EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT 			= EP_SKTEVENT_SELECT_STAR + 1;
		public static final int EP_DELETED_SELECT_STAR 									= EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT + 1;
		public static final int EP_NOAC_SELECT_FORM_BY_NAME								= EP_DELETED_SELECT_STAR + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER 								= EP_NOAC_SELECT_FORM_BY_NAME + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_BETWEEN_NOM 					= EP_QUERY_NOAC_SELECT_USER + 1;
		public static final int EP_SELECT_FORM_BY_NAME_AND_IDEVENT 						= EP_QUERY_NOAC_SELECT_USER_BETWEEN_NOM + 1;
		public static final int EP_AC_SELECT_FORM_BY_ID_AND_IDEVENT 					= EP_SELECT_FORM_BY_NAME_AND_IDEVENT + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_BY_FORM_BETWEEN_EVENT 			= EP_AC_SELECT_FORM_BY_ID_AND_IDEVENT + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM 				= EP_QUERY_AC_SELECT_INFO_BY_FORM_BETWEEN_EVENT + 1;
		public static final int EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM_BETWEEN_EVENT 	= EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM + 1;
		public static final int EP_QUERY_AC_SELECT_EVENTnop_INFO_COMMENT_BY_EVENT 		= EP_QUERY_AC_SELECT_INFO_COMMENT_BY_FORM_BETWEEN_EVENT + 1;
		public static final int EP_QUERY_NOAC_SELECT_USER_DUMMY 						= EP_QUERY_AC_SELECT_EVENTnop_INFO_COMMENT_BY_EVENT + 1;
	}
	
	public static class Execution_Plan // 11
	{
		public static final int COMMENT_NOAC_INSERT_WITH_IDS 									= EP_TEST.EP_QUERY_NOAC_SELECT_USER_DUMMY + 1;
		public static final int COMMENT_NOAC_INSERT 											= COMMENT_NOAC_INSERT_WITH_IDS + 1;
		public static final int COMMENT_NOAC_SELECT_STAR 										= COMMENT_NOAC_INSERT + 1;
		public static final int CONCEPT_NOAC_INSERT_WITH_IDS 									= COMMENT_NOAC_SELECT_STAR + 1;
		public static final int CONCEPT_NOAC_SELECT_STAR 										= CONCEPT_NOAC_INSERT_WITH_IDS + 1;
		public static final int EPISODE_NOAC_INSERT_WITH_IDS 									= CONCEPT_NOAC_SELECT_STAR + 1;
		public static final int EPISODE_NOAC_SELECT_STAR 										= EPISODE_NOAC_INSERT_WITH_IDS + 1;
		public static final int EVENT_AC_INSERT 												= EPISODE_NOAC_SELECT_STAR + 1;
		public static final int EVENT_NOAC_INSERT_WITH_IDS 										= EVENT_AC_INSERT + 1;
		public static final int EVENT_NOAC_SELECT_STAR 											= EVENT_NOAC_INSERT_WITH_IDS + 1;
		public static final int FORMULAIRE_NOAC_INSERT_WITH_IDS 								= EVENT_NOAC_SELECT_STAR + 1;
		public static final int FORMULAIRE_NOAC_SELECT_STAR 									= FORMULAIRE_NOAC_INSERT_WITH_IDS + 1;
		public static final int SELECT_NOAC_FORM 												= FORMULAIRE_NOAC_SELECT_STAR + 1;
		public static final int HABILITATION_NOAC_INSERT_WITH_IDS 								= SELECT_NOAC_FORM + 1;
		public static final int HABILITATION_NOAC_SELECT_STAR 									= HABILITATION_NOAC_INSERT_WITH_IDS + 1;
		public static final int INFO_AC_SELECT_STAR 											= HABILITATION_NOAC_SELECT_STAR + 1;
		public static final int INFO_NOAC_INSERT 												= INFO_AC_SELECT_STAR + 1;
		public static final int INFO_AC_INSERT 													= INFO_NOAC_INSERT + 1;
		public static final int INFO_NOAC_INSERT_WITH_IDS 										= INFO_AC_INSERT + 1;
		public static final int INFO_NOAC_SELECT_STAR 											= INFO_NOAC_INSERT_WITH_IDS + 1;
		public static final int INFO_NOAC_DELETE_BY_EVENT_ID 									= INFO_NOAC_SELECT_STAR + 1;
		public static final int INFO_NOAC_UPDATE_Valchar_BY_ID 									= INFO_NOAC_DELETE_BY_EVENT_ID + 1;
		public static final int MATRICEDMSP_NOAC_INSERT_WITH_IDS 								= INFO_NOAC_UPDATE_Valchar_BY_ID + 1;
		public static final int MATRICEDMSP_NOAC_SELECT_STAR 									= MATRICEDMSP_NOAC_INSERT_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_EPISODE_USER_WITH_IDS 				= MATRICEDMSP_NOAC_SELECT_STAR + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_EPISODE_ROLE_WITH_IDS 				= MATRICEPATIENT_NOAC_INSERT_EPISODE_USER_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_FORM_USER_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_EPISODE_ROLE_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_FORM_ROLE_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_FORM_USER_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_USER_USER_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_FORM_ROLE_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_USER_ROLE_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_USER_USER_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_OTHER_USER_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_USER_ROLE_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_INSERT_OTHER_ROLE_WITH_IDS 					= MATRICEPATIENT_NOAC_INSERT_OTHER_USER_WITH_IDS + 1;
		public static final int MATRICEPATIENT_NOAC_SELECT_STAR 								= MATRICEPATIENT_NOAC_INSERT_OTHER_ROLE_WITH_IDS + 1;
		public static final int QUERY_AC_SELECT_EVENT_BY_FORM 									= MATRICEPATIENT_NOAC_SELECT_STAR + 1;
		public static final int QUERY_NOAC_SELECT_INFO_BY_EVENT 								= QUERY_AC_SELECT_EVENT_BY_FORM + 1;
		public static final int SELECT_NOAC_FORM_EVENT_USER 									= QUERY_NOAC_SELECT_INFO_BY_EVENT + 1;
		public static final int SELECT_NOAC_EVENT_USER_BY_FORM 									= SELECT_NOAC_FORM_EVENT_USER + 1;
		public static final int SELECT_NOAC_EVENT_USER_BY_FORM_2 								= SELECT_NOAC_EVENT_USER_BY_FORM + 1;
		public static final int SELECT_AC_EVENT_USER_BY_EVENT 									= SELECT_NOAC_EVENT_USER_BY_FORM_2 + 1;
		public static final int SELECT_AC_INFO_COMMENT_CONCEPT_BY_EVENT 						= SELECT_AC_EVENT_USER_BY_EVENT + 1;
		public static final int SELECT_AC_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT 					= SELECT_AC_INFO_COMMENT_CONCEPT_BY_EVENT + 1;
		public static final int SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT 				= SELECT_AC_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT + 1;
		public static final int SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE 		= SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT + 1;
		public static final int SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE_FORM	= SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE + 1;
		public static final int ROLE_NOAC_INSERT_WITH_IDS 										= SELECT_AC_USER_EVENT_INFO_COMMENT_CONCEPT_BY_EVENT_DATE_FORM + 1;
		public static final int ROLE_NOAC_SELECT_STAR 											= ROLE_NOAC_INSERT_WITH_IDS + 1;
		public static final int USERDMSP_NOAC_INSERT 											= ROLE_NOAC_SELECT_STAR + 1;
		public static final int USERDMSP_NOAC_INSERT_WITH_IDS 									= USERDMSP_NOAC_INSERT + 1;
		public static final int USERDMSP_NOAC_SELECT_STAR 										= USERDMSP_NOAC_INSERT_WITH_IDS + 1;
		public static final int TupleDeleted_NOAC_SELECT_STAR 									= USERDMSP_NOAC_SELECT_STAR + 1;
		public static final int LogDeleted_NOAC_SELECT_STAR 									= TupleDeleted_NOAC_SELECT_STAR + 1;
	}
	
	public static class EP_BLOB // 12
	{
		public static final int EP_EPISODE_INSERT 		= Execution_Plan.LogDeleted_NOAC_SELECT_STAR + 1;
		public static final int EP_EPISODE_SELECT_BY_ID	= EP_EPISODE_INSERT + 1;
		public static final int EP_EPISODE_UPDATE 		= EP_EPISODE_SELECT_BY_ID + 1;
		public static final int EP_EPISODE_DELETE 		= EP_EPISODE_UPDATE + 1;
	}
	
	public static class EP_string_column // 12
	{
		public static final int EP_EPISODE_INSERT 	= EP_BLOB.EP_EPISODE_DELETE + 1;
		public static final int EP_EPISODE_SELECT 	= EP_EPISODE_INSERT + 1;
		public static final int EP_EPISODE_UPDATE 	= EP_EPISODE_SELECT + 1;
		public static final int EP_EPISODE_DELETE 	= EP_EPISODE_UPDATE + 1;
	}
}
