package org.inria.dmsp.test.TEST_EP_Synchro_lionel;

/////////////////////////////////////////////////////
// IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

/////////////////////////////////////////////////////////////////////////
// TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////



import test.runner.ITest; // super class
import test.jdbc.Tools; // tools specified for any DB schema

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		// set output stream and instantiate the tools:		
		this.out = out;
		Tools_dmsp t = new Tools_dmsp(out);
		// this not a performance test ==> output is produced:
		perf = 0;

		// initialize the driver:
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// connect without authentication
		openConnection(dbmsHost, null);

		// load the DB schema
		String schema = "/org/inria/dmsp/schemaV4.rsc";
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

	    // instantiate delta loader
	    DeltaLoader dl = new DeltaLoader(out, perf); 
		// open the delta file
	    InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("delta.csv"));
		// load the data into database
		dl.LoadDelta(fr, db); 

		//--
		// EP_INFO_SELECT_BY_ID
		//--
		// select * from info where IdGlobal = ?
		if(perf==0){out.println("EP_INFO_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1344, ps), out);

		// select * from info where IdGlobal = ?
		if(perf==0){out.println("EP_INFO_SELECT_BY_ID (missing id)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1017, ps), out);

		//--
		// EP_USER_SELECT_BY_ID
		//--
		// select * from userdmsp where IdGlobal = ?
		if(perf==0){out.println("EP_USER_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1067, ps), out);

		// select * from userdmsp where IdGlobal = ?
		if(perf==0){out.println("EP_USER_SELECT_BY_ID (missing id)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(9999, ps), out);

		//--
		// EP_EVENT_SELECT_BY_ID
		//--
		// select * from event where IdGlobal = ?
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1307, ps), out);

		// select * from event where IdGlobal = ?
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID (missing id)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(990, ps), out);

		//--
		// EP_COMMENT_SELECT_BY_ID
		//--
		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1204, ps), out);

		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID (missing id)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);

		//--
		// EP_INFO_UPDATE
		//--
		// UPDATE Info SET Author = ?, TSSPT = ?, TSSanteos = ?, ValChar = ?, ValNum = ?, ValDate = ?, Position = ?, Filtre = ?, IdConcept = ? WHERE IdGlobal = ?
		if(perf==0){out.println("EP_INFO_UPDATE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_UPDATE);
		Tools_dmsp.Test_INFO_UPDATE(1, 42, 712, "bli", 1, new java.sql.Date(0), 34, 1, 62, 1344, ps);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1344, ps), out);

		//--
		// EP_COMMENT_UPDATE
		//--
		// UPDATE Comment SET Author = ?, TSSPT = ?, TSSanteos = ?, ValComment = ? WHERE IdGlobal = ?
		if(perf==0){out.println("EP_COMMENT_UPDATE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1059, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_UPDATE);
		Tools_dmsp.Test_COMMENT_UPDATE(1, 513, 1025, "bli", 1059, ps);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1059, ps), out);

		//--
		// EP_EPISODE_UPDATE
		//--
		// UPDATE Episode SET Author = ?, TSSPT = ?, TSSanteos = ?, Nom = ? WHERE IdGlobal = ?
		if(perf==0){out.println("EP_EPISODE_UPDATE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_UPDATE);
		Tools_dmsp.Test_EPISODE_UPDATE(2, 1025, 2049, "bli", 2, ps);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		//--
		// EP_PATIENT_SELECT_ON_TSSPT
		//--
		// SELECT * FROM MatricePatient WHERE TSSPT > ?
		if(perf==0){out.println("EP_PATIENT_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(18, ps), out);

		//--
		// EP_EPISODE_SELECT_ON_TSSPT
		//--
		// SELECT * FROM Episode WHERE TSSPT > ?
		if(perf==0){out.println("EP_EPISODE_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(513, ps), out);

		//--
		// EP_HABILITATION_SELECT_ON_TSSPT
		//--
		// SELECT * FROM Habilitation WHERE TSSPT > ?
		if(perf==0){out.println("EP_HABILITATION_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(42, ps), out);

		// Commit and clean exit
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
