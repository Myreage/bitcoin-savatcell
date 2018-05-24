package test.jdbc.schemaIndexInfo;

import java.io.PrintWriter;
import java.util.Calendar;

import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;

public class DataGeneratorWithIds {

	private PrintWriter out;
	public int perf;
	public int one_user_IdGlobal;
	public int one_role_IdGlobal;
	public int one_form_IdGlobal;
	public int null_episode_IdGlobal;
	public int one_concept_IdGlobal;
	public int null_comment_IdGlobal;
	private int IdGlobal;

	public DataGeneratorWithIds(PrintWriter out) {
		super();
		this.out = out;
		this.perf=0;
	}

	public int INSERT_Data_Generated(
			int numberOfUser,
			int numberOfRole,
			int numberOfHab,
			int numberOfForm,
			int numberOfMatriceDMSP,
			int numberOfEpisode,
			int numberOfEvent,
			int numberOfConcept,
			int numberOfComment,
			int numberOfInfo,
			java.sql.Connection db,
			int wantMatpat) 
	throws Exception {
		if(perf==0){
			out.println("Data generated");
		}
		java.sql.PreparedStatement ps ; 

		// TABLE  USERDMSP
		if(perf==0){
			out.println("// Insertion dans la table USERDMSP ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_INSERT_WITH_IDS);
		int IndexUser = numberOfUser;
		for(int i=0; i<IndexUser ;i++){
			IdGlobal = i + 1;
			Tools_schemaIndexInfo.Test_USERDMSP_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					Data.UserDMSP.lastNames[i%(Data.UserDMSP.lastNames.length)],
					Data.UserDMSP.firstNames[i%(Data.UserDMSP.firstNames.length)],
					Data.UserDMSP.MF[i%(Data.UserDMSP.MF.length)],
					Data.UserDMSP.adress[i%(Data.UserDMSP.adress.length)],
					Data.UserDMSP.towns[i%(Data.UserDMSP.towns.length)],
					Data.UserDMSP.zipCodes[i%(Data.UserDMSP.zipCodes.length)],
					Data.UserDMSP.tel1[i%(Data.UserDMSP.tel1.length)],
					Data.UserDMSP.tel2[i%(Data.UserDMSP.tel2.length)],
					Data.UserDMSP.userTypes[i%(Data.UserDMSP.userTypes.length)], 
					ps);
			if(i==1) // userType is Referent Social
				one_user_IdGlobal = IdGlobal;
		}

		// TABLE  ROLE
		if(perf==0){
			out.println("// Insertion dans la table ROLE ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.ROLE_NOAC_INSERT_WITH_IDS);
		int IndexRole = IndexUser + numberOfRole;
		for(int i=IndexUser; i<IndexRole ;i++){
			IdGlobal = i + 1;
			Tools_schemaIndexInfo.Test_ROLE_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					Data.Role.names[i%(Data.Role.names.length)], 
					ps);
			if(i==(IndexUser+1))
				one_role_IdGlobal = IdGlobal;
		}


		// TABLE  HABILITATION
		if(perf==0){
			out.println("// Insertion dans la table HABILITATION ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.HABILITATION_NOAC_INSERT_WITH_IDS);
		int IndexHab = IndexRole + numberOfHab;
		int j = 1;
		int k = 1;
		for(int i=IndexRole; i<IndexHab ;i++){
			int IdRole = IndexUser + j;
			int IdUser = k;
			Tools_schemaIndexInfo.Test_HABILITATION_NOAC_AC_INSERT_WITH_IDS(
					i + 1, 
					0, 0, 0, 
					IdRole, 
					IdUser, 
					ps);
			j++;
			k++;
			if(j > numberOfRole){
				j = 1;
			}
			if(k > numberOfUser){
				k = 1;
			}
		}

		// TABLE  CONCEPT
		if(perf==0){
			out.println("// Insertion dans la table CONCEPT ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_INSERT_WITH_IDS);
		int IndexConcept = IndexHab + numberOfConcept;
		for(int i=IndexHab; i<IndexConcept ;i++){
			IdGlobal = i + 1;
			if(i==(IndexHab + numberOfConcept/2))
				one_concept_IdGlobal = IdGlobal;
			Tools_schemaIndexInfo.Test_CONCEPT_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					Data.Concept.units[i%(Data.Concept.units.length)], 
					Data.Concept.names[i%(Data.Concept.names.length)], 
					Data.Concept.dataTypes[i%(Data.Concept.dataTypes.length)],
					ps);
		}

		// TABLE  FORMULAIRE
		if(perf==0){
			out.println("// Insertion dans la table FORMULAIRE ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.FORMULAIRE_NOAC_INSERT_WITH_IDS);
		int IndexForm = IndexConcept + numberOfForm;
		for(int i=IndexConcept; i<IndexForm ;i++){
			IdGlobal = i + 1;
			if(i==(IndexConcept + numberOfForm/2))
				one_form_IdGlobal = IdGlobal;
			Tools_schemaIndexInfo.Test_FORMULAIRE_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					Data.Formulaire.names[i%(Data.Formulaire.names.length)],
					ps);
		}

		// TABLE  EPISODE
		if(perf==0){
			out.println("// Insertion dans la table EPISODE ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.EPISODE_NOAC_INSERT_WITH_IDS);
		int IndexEpisode= IndexForm + numberOfEpisode;
		for(int i=IndexForm; i<IndexEpisode ;i++){
			IdGlobal = i + 1;
			if(i==IndexForm)
				null_episode_IdGlobal = IdGlobal;
			Tools_schemaIndexInfo.Test_EPISODE_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					Data.Episode.names[i%(Data.Episode.names.length)],
					ps);
		}

		// TABLE  EVENT
		if(perf==0){
			out.println("// Insertion dans la table EVENT ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_INSERT_WITH_IDS);
		int IndexEvent = IndexEpisode + numberOfEvent;
		int l = 1;
		int m = 1;
		int n = 1;
		for(int i=IndexEpisode; i<IndexEvent ;i++){
			int IdForm = IndexConcept + l;
			int IdUser = m;
			int IdEpisode = IndexForm + n;

			// event date
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Data.Event.years[i%Data.Event.years.length]);
			cal.set(Calendar.MONTH, Data.Event.months[i%Data.Event.months.length]);
			cal.set(Calendar.DAY_OF_MONTH, Data.Event.days[i%Data.Event.days.length]);

			Tools_schemaIndexInfo.Test_EVENT_NOAC_AC_INSERT_WITH_IDS(
					i + 1,
					0,0,0,
					IdForm,
					IdUser,
					IdEpisode,
					new java.sql.Date(cal.getTime().getTime()),
					ps);

			l++;
			m++;
			n++;
			if(l > numberOfForm){
				l = 1;
			}
			if(m > numberOfUser){
				m = 1;
			}
			if(n > numberOfEpisode){
				n = 1;
			}
		}

		// TABLE  COMMENT
		if(perf==0){
			out.println("// Insertion dans la table COMMENT ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.COMMENT_NOAC_INSERT_WITH_IDS);
		int IndexComment = IndexEvent + numberOfComment;
		int insertionOfCommentNoComment = 0;
		String nameConcept = "";
		for(int i=IndexEvent; i<IndexComment ;i++){
			if((Data.Comment.names[i%(Data.Comment.names.length)]).equals(Data.Comment.names[0])){
				if((insertionOfCommentNoComment == 1)){
					nameConcept = Data.Comment.names[1%(Data.Comment.names.length)];
				}else{
					nameConcept = Data.Comment.names[i%(Data.Comment.names.length)];
					insertionOfCommentNoComment = 1;
				}
			}else{
				nameConcept = Data.Comment.names[i%(Data.Comment.names.length)];
			}
			IdGlobal = i + 1;
			if(i==IndexEvent)
			{
				// the first comment is NULL
				null_comment_IdGlobal = IdGlobal;
				// write NULL
				//nameConcept = "NULL";
			}
			Tools_schemaIndexInfo.Test_COMMENT_NOAC_AC_INSERT_WITH_IDS(
					IdGlobal,
					0,0,0,
					nameConcept,
					ps);
		}

		// TABLE  INFO
		if(perf==0){
			out.println("// Insertion dans la table INFO ");
		}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_INSERT_WITH_IDS);
		int IndexInfo = IndexComment + numberOfInfo;
		int x = 1;
		int y = 1;
		int z = 1;
		for(int i=IndexComment; i<IndexInfo ;i++){
			int IdEvent = IndexEpisode + x;
			int IdConcept = IndexHab + y;
			int IdComment = IndexEvent + z;

			Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS(
					i + 1,
					0,0,0,
					IdEvent,
					IdConcept,
					IdComment,
					Data.Info.valChars[i%Data.Info.valChars.length], 
					Data.Info.valNums[i%Data.Info.valNums.length], 
					Data.Info.positions[i%Data.Info.positions.length],
					ps);

			x++;
			y++;
			z++;
			if(x > numberOfEvent){
				x = 1;
			}
			if(y > numberOfConcept){
				y = 1;
			}
			if(z > numberOfComment){
				z = 1;
			}
		}
		int compteur = IndexInfo;
		// TABLE  MATRICE_DMSP
		
		if(wantMatpat == 1){
			// TABLE  MATRICE_PATIENT
			if(perf==0){
				out.println("// Insertion dans la table MATRICE_PATIENT ");
			}
			//Categorie Formulaire - acteurs Role
			x = 1;
			y = 1;
			//compteur = IndexInfo;
			for(int i=IndexUser; i<IndexRole ;i++){
				for(j=IndexConcept;j<IndexForm;j++){
					int IdForm = IndexConcept + x;
					int IdRole = IndexUser + y;
					ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(
							IdForm, 
							Data.MatricePatient.CategorieFormulaire, 
							IdRole, 
							Data.MatricePatient.ActeurRole, 
							db);
					Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(
							compteur, 
							0, 0, 0, 
							IdForm, 
							Data.MatricePatient.CategorieFormulaire, 
							IdRole, 
							Data.MatricePatient.ActeurRole, 
							org.inria.jdbc.Connection.ComputeAuthorizationBitsVector(
									Data.MatricePatient.autorisations[j%Data.MatricePatient.autorisations.length],
									Data.MatricePatient.autorisations[j%Data.MatricePatient.autorisations.length],
									Data.MatricePatient.autorisations[j%Data.MatricePatient.autorisations.length],
									Data.MatricePatient.autorisations[j%Data.MatricePatient.autorisations.length]
							), 
							ps);
					x++;
					if(x > numberOfForm){
						x = 1;
					}
					compteur++;
				}
				y++;
				if(y > numberOfRole){
					y = 1;
				}
			}
			//Autres categories
			y = 1;
			for(int i=IndexUser; i<IndexRole ;i++){
				int IdRole = IndexUser + y;
				// Autre categorie - User
				ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(
						0, 
						Data.MatricePatient.CategorieUser, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						db);
				Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(
						compteur, 
						0, 0, 0, 
						0, 
						Data.MatricePatient.CategorieUser, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						org.inria.jdbc.Connection.ComputeAuthorizationBitsVector(
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length]
						), 
						ps);
				compteur++;
				//Autre categorie - Episode
				ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(
						0, 
						Data.MatricePatient.CategorieEpisode, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						db);
				Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(
						compteur, 
						0, 0, 0, 
						0, 
						Data.MatricePatient.CategorieEpisode, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						org.inria.jdbc.Connection.ComputeAuthorizationBitsVector(
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length]
						), 
						ps);
				compteur++;
				//Autre categorie - Formulaire
				ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(
						0, 
						Data.MatricePatient.CategorieFormulaire, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						db);
				Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(
						compteur, 
						0, 0, 0, 
						0, 
						Data.MatricePatient.CategorieFormulaire, 
						IdRole, 
						Data.MatricePatient.ActeurRole, 
						org.inria.jdbc.Connection.ComputeAuthorizationBitsVector(
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length],
								Data.MatricePatient.autorisations[i%Data.MatricePatient.autorisations.length]
						), 
						ps);
				compteur++;
				y++;
			}
		}
		return compteur;
	}
	
	public void SCE_Test_DB_NOAC(java.sql.Connection db, PrintWriter out) throws Exception {
		// TABLE  USERDMSP
		// TABLE  ROLE
		// TABLE  HABILITATION
		// TABLE  CONCEPT
		// TABLE  FORMULAIRE
		// TABLE  EPISODE
		// TABLE  EVENT
		// TABLE  COMMENT
		// TABLE  INFO
		// TABLE  MATRICEDMSP
		// TABLE  MATRICEPATIENT

		java.sql.Statement stmt = db.createStatement();

		out.println("== USERDMSP NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);

		out.println("== ROLE NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.ROLE_NOAC_SELECT_STAR), out);

		out.println("== HABILITATION NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.HABILITATION_NOAC_SELECT_STAR), out);

		out.println("== CONCEPT NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);

		out.println("== FORMULAIRE NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.FORMULAIRE_NOAC_SELECT_STAR), out);

		out.println("== EPISODE NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.EPISODE_NOAC_SELECT_STAR), out);

		out.println("== EVENT NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_SELECT_STAR), out);

		out.println("== COMMENT NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.COMMENT_NOAC_SELECT_STAR), out);

		out.println("== INFO NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

		out.println("== MATRICEDMSP NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.MATRICEDMSP_NOAC_SELECT_STAR), out);

		out.println("== MATRICEPATIENT NOAC TESTS ==");
		Tools.lireResultSet(Tools.request(stmt, DMSP_QEP_IDs.Execution_Plan.MATRICEPATIENT_NOAC_SELECT_STAR), out);
	}

}
