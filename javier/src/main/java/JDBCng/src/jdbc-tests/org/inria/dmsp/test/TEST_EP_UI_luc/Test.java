package org.inria.dmsp.test.TEST_EP_UI_luc;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

///////////////////////////////
// DO NOT CHANGE AFTER HERE  //
///////////////////////////////



import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import test.jdbc.Tools;
import test.runner.ITest;

import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		// connect information:
		this.out = out;
		perf = 0;
		Tools_dmsp t = new Tools_dmsp(out);

		init();
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

		// select * from *
		//select_star();

		java.sql.PreparedStatement ps;
		java.sql.Statement st;
		java.sql.ResultSet rs;
		// BEGIN TEST 16 (Test above are from Shaoyi's file)
		if(perf==0){out.println("----------- TEST BEGIN 16 ------------ = " +
		"Delete n tuples from Info --> EP_INFO_DELETE");}

		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);

		/*
		 *  DELETE FROM INFO
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_EVENT);  
		int res = Tools_dmsp.Test_DELETE_BY_ID(1121, ps);
		if(perf==0){out.println(""+res+" row(s) deleted...");}

		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR: after deleting "+res+" info (event 1121)");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);	
		// select * from TupleDeleted
		if(perf==0){out.println("EP_DELETED_SELECT_STAR: after inserting "+res+" tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_DELETED_SELECT_STAR);
		lireResultSet(rs, out);
		// END TEST 16

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
