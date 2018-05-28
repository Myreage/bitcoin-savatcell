package test.jdbc.INRIA_3;

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.DataGeneratorWithIds;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {

    @Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {
		this.out = out;
		Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
		init();
		openConnection(dbmsHost, null);
		String schema = "schemaV4.rsc";
		byte[] schemaDesc = t.loadSchema(schema);
		int usedId = 404;
		Install_DBMS_MetaData(schemaDesc, usedId);
	    schemaDesc = null;
	    
		// load and install QEPs
	    Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
		QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
		QEPng.installExecutionPlans( db );

		DataGeneratorWithIds dgwi = new DataGeneratorWithIds(out);
		int index = dgwi.INSERT_Data_Generated(
				5, // nbUser
				3, // nbRole
				4, // nbHab
				35, // nbForm
				0, // nbMatriceDMSP
				1, // nbEpisode
				4, // nbEvent
				309, // nbConcept
				10, // nbComment
				69,// nbInfo
				db,
				0); // noMatPatGenerated
		Save_DBMS_on_disk();
		out.println("index = "+index);
		
		// TABLE  MATRICE_PATIENT
		java.sql.PreparedStatement ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((341), 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 422, (341), 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((341), 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 423, (341), 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((341), 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 424, (341), 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((345), 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 425, (345), 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((345), 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 426, (345), 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('0', '0', '0', '0'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((345), 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 427, (345), 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((336), 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 428, (336), 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((336), 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 429, (336), 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('0', '0', '0', '0'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((336), 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 430, (336), 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((347), 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 431, (347), 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('0', '0', '0', '0'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((347), 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 432, (347), 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((347), 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 433, (347), 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((349), 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 434, (349), 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((349), 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 435, (349), 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT((349), 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 436, (349), 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 437, 0, 1, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 438, 0, 1, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 439, 0, 1, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 440, 0, 2, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('?', '?', '?', '?'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 441, 0, 2, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('?', '?', '?', '?'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 442, 0, 2, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (8), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 443, 0, 0, (8), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('?', '?', '?', '?'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (6), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 444, 0, 0, (6), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('?', '?', '?', '?'), ps);
		index++;
		
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, (7), 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS((index + 1), 0, 0, 445, 0, 0, (7), 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		// Génération TESTS : Fin parsing
		Save_DBMS_on_disk();
		
		dgwi.SCE_Test_DB_NOAC(db, out);
		Shutdown_DBMS();

		String[] authent = new String[2];
		// Docteur 1 nommé Pierre Dupont se connecte
		String cur_User = "3"; // Martin Roger
		String cur_Role = "8"; // Medecin
		authent[0] = cur_User;
		authent[1] = cur_Role;
		out.println("\n\nUserDMSP = " + cur_User + ", with role = " + cur_Role + " connects to the DBMS :\n");
		openConnection(dbmsHost, authent);
		out.println("select e.DateEvent, e.IdGlobal, e.IdForm, e.IdUser, f.Nom " +
				"from event e, formulaire f " +
		"where f.IdGlobal = e.IdForm with Access Grant");

		java.sql.Statement stmt = db.createStatement();
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.QUERY_AC_SELECT_EVENT_BY_FORM), out);
		// TABLE  EVENT
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		// Add of Formulaire Grille AGGIR Dupont Pierre appartenant à noEpisode
		Tools_schemaIndexInfo.Test_EVENT_NOAC_AC_INSERT(0, 0, 0, (341), (3), (357), java.sql.Date.valueOf("2008-12-18"), ps);
		// Add of Formulaire Observation médicales par Dupont Pierre appartenant à noEpisode
		Tools_schemaIndexInfo.Test_EVENT_NOAC_AC_INSERT(0, 0, 0, (345), (3), (357), java.sql.Date.valueOf("2008-12-18"), ps);
		Save_DBMS_on_disk();
		out.println("select e.DateEvent, e.IdGlobal, e.IdForm, e.IdUser, f.Nom " +
				"from event e, formulaire f " +
		"where f.IdGlobal = e.IdForm with Access Grant");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.QUERY_AC_SELECT_EVENT_BY_FORM), out);
		Shutdown_DBMS();

		// Soc nommé Julien Lefebvre se connecte
		cur_User = "4"; // Julien Lefebvre
		cur_Role = "6"; // ReferentSocial
		authent[0] = cur_User;
		authent[1] = cur_Role;
		out.println("\n\nUserDMSP = " + cur_User + ", with role = " + cur_Role + " connects to the DBMS :\n");
		openConnection(dbmsHost, authent);
		out.println("select e.DateEvent, e.IdGlobal, e.IdForm, e.IdUser, f.Nom " +
				"from event e, formulaire f " +
		"where f.IdGlobal = e.IdForm with Access Grant");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.QUERY_AC_SELECT_EVENT_BY_FORM), out);
		Shutdown_DBMS();

		openConnection(dbmsHost, null);
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}

