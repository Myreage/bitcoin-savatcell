package test.jdbc.INRIA_17;

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.DataGeneratorWithIds;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {
	static private final int NUMBER_OF_INFOS = 128;
	static private final int NUMBER_OF_COMMENTS = 20;

	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {
		this.out = out;
		perf = 0;
		Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
		init();

		//Populates the database
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
		dgwi.perf=0;
		int index = dgwi.INSERT_Data_Generated(
				10, // nbUser
				3, // nbRole
				7, // nbHab
				20, // nbForm
				0, // nbMatriceDMSP
				1, // nbEpisode
				10, // nbEvent
				309, // nbConcept
				10, // nbComment
				100, // nbInfo
				db,
				0); 

		// TABLE  MATRICE_PATIENT: insert manually
		java.sql.PreparedStatement ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(dgwi.one_form_IdGlobal, 1, dgwi.one_role_IdGlobal, 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(index + 1, 0, 0, index+1, dgwi.one_form_IdGlobal, 1, dgwi.one_role_IdGlobal, 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, dgwi.one_role_IdGlobal, 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(index + 1, 0, 0, index+1, 0, 1, dgwi.one_role_IdGlobal, 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, dgwi.one_role_IdGlobal, 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(index + 1, 0, 0, index+1, 0, 2, dgwi.one_role_IdGlobal, 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);
		index++;
		ps = Tools_schemaIndexInfo.Get_EP_MATRICE_PATIENT_NOAC_INSERT(0, 1, dgwi.one_role_IdGlobal, 1, db);
		Tools_schemaIndexInfo.Test_MATRICE_PATIENT_NOAC_AC_INSERT_WITH_IDS(index + 1, 0, 0, index+1, 0, 0, dgwi.one_role_IdGlobal, 1, org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1', '1', '1', '1'), ps);

		Save_DBMS_on_disk();
		Shutdown_DBMS();
		
		//====================================================//
		// MEASURE I - rollback from Formular insertions //
		//====================================================//
		String[] authent = new String[2];
		// The user connects
		String cur_User = Integer.toString(dgwi.one_user_IdGlobal); // produced by the data generator
		String cur_Role = Integer.toString(dgwi.one_role_IdGlobal); //
		authent[0] = cur_User;
		authent[1] = cur_Role;
		openConnection(dbmsHost, authent); 
		// TABLE  EVENT Add of Formulaire Grille AGGIR Dupont Pierre appartenant à noEpisode
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		java.sql.ResultSet rs = Tools_schemaIndexInfo.Test_EVENT_NOAC_AC_INSERT(0, 0, 0, dgwi.one_form_IdGlobal, dgwi.one_user_IdGlobal, dgwi.null_episode_IdGlobal, java.sql.Date.valueOf("2008-12-18"), ps);
		int IdG = getIdGlobalGetGeneratedKey(rs);
		// insert tuples to table INFO and COMMENT
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_INSERT);
		java.sql.PreparedStatement psc = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.COMMENT_NOAC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		for(int i=0;i<NUMBER_OF_INFOS;i++)
		{
			if(i<NUMBER_OF_COMMENTS)
			{
				rs = Tools_schemaIndexInfo.Test_COMMENT_NOAC_AC_INSERT(0, 0, 0, "Principales plaintes du patient", psc);
				int IdG2 = getIdGlobalGetGeneratedKey(rs);
				Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT(0, 0, 0, IdG, dgwi.one_concept_IdGlobal, IdG2, null, 0, 1, ps);
			}
			else
				Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT(0, 0, 0, IdG, dgwi.one_concept_IdGlobal, dgwi.null_comment_IdGlobal, null, 0, 1, ps);
		}
		//long start = ((org.inria.jdbc.Connection) db).getDBMSCallTime();
		Rollback_Transaction();
		//long end = ((org.inria.jdbc.Connection) db).getDBMSCallTime();
		// END MEASURE I
		Shutdown_DBMS();
		
		openConnection(dbmsHost, null);
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}