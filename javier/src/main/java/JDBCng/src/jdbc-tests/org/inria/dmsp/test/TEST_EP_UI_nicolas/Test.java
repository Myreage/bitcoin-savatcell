package org.inria.dmsp.test.TEST_EP_UI_nicolas;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

///////////////////////////////
//DO NOT CHANGE AFTER HERE  //
///////////////////////////////



import test.jdbc.Tools;
import test.runner.ITest;

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		// connect information:
		String authent[] = new String [2];
		this.out = out;
		perf = 0;
		Tools_dmsp t = new Tools_dmsp(out);

		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// the statement for non-parametric select:
		java.sql.Statement st;
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

		/*
		 *  Load initial delta and test result of the load
         */
	    // Call the delta loader
	    DeltaLoader dl = new DeltaLoader(out);
		dl.perf = perf;
		// open the delta file
		InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("delta.csv"));
		// load the data into database
		dl.LoadDelta(fr, db); 

		select_star();

		if(perf==0){out.println("----------- TEST 1 : REMOVE EVENT " +
				"NB :  the tested plan does not exist in org.inria.dmsp.EP_UI");}
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);

		if(perf==0){out.println("All events before delete: ");}
		st = db.createStatement();
		lireResultSet(((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR), out);

		if(perf==0){out.println("Remove event n°1121 (authorized, but linked to infos)...");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_EVENT_AC_DELETE_BY_ID);
		int res = Tools_dmsp.Test_DELETE_BY_ID(	1121/*idglobal_event*/, ps);	
		if(perf==0){out.println("NB row(s) deleted: "+res);}

		if(perf==0){out.println("Remove event n°1258 (not authorized, and linked to infos)...");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_EVENT_AC_DELETE_BY_ID);
		res = Tools_dmsp.Test_DELETE_BY_ID(	1258/*idglobal_event*/, ps);	
		if(perf==0){out.println("NB row(s) deleted: "+res);}

		if(perf==0){out.println("All events after delete: ");}
		st = db.createStatement();
		lireResultSet(((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR), out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	// SELECT STAR FROM STAR
	public void select_star() throws Exception
	{
		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
		// the resultset used for the queries:
		java.sql.ResultSet rs;

		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);

		// SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);
	}
}
