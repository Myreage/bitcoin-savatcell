package org.inria.dmsp.test.TEST_EP_UI_marcelauto;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

//import org.inria.dmsp.schema.EP_Synchro;
import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

/////////////////////////////////////////////////////////////////////////
//TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////



import test.runner.ITest;		// super class
import test.jdbc.Tools;			// tools specified for any DB schema

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
		// connect without authentication
		openConnection(dbmsHost, null);

		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
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

		// select * from *
		//select_star();

		if (perf==0) {out.println("EP_QUERY_NOAC_SELECT_HABILITATION");}
		lireResultSet(((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_HABILITATION),out);

		//select_star();

		// Commit and clean exit
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

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

		// SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);
	}
}
