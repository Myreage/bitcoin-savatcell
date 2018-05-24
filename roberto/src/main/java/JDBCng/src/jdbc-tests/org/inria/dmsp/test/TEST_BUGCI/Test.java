package org.inria.dmsp.test.TEST_BUGCI;

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.DataGeneratorWithIds;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest
{
	@Override
	  public void run(PrintWriter out, String dbmsHost) throws Exception
	  {
		// set output stream and instantiate the tools:		
		this.out = out; 
		// this not a performance test ==> output is produced:
		perf = 0;

		Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps;
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		// the statement for non-parametric select:
		java.sql.Statement st;
		// Populates the database
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
		dgwi.perf=1;
		dgwi.INSERT_Data_Generated(
				5, // nbUser
				3, // nbRole
				7, // nbHab
				20, // nbForm
				0, // nbMatriceDMSP
				1, // nbEpisode
				100, // nbEvent
				309, // nbConcept
				50, // nbComment
				1000, // nbInfo
				db,
				1); 

		// commit
		Save_DBMS_on_disk();

		if(perf==0){out.println("========== ALL INFOS ======== ");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.Execution_Plan.INFO_AC_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from info where IdEvent = 430
		out.println("Select * from info where IdEvent = "+430);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.QUERY_NOAC_SELECT_INFO_BY_EVENT);
		lireResultSet(Tools_schemaIndexInfo.Test_INFO_SELECT_STAR_BY_EVENT_ID(430, ps), out);

		// delete tuples which position > or < 500
	    out.println("Delete from info where IdEvent = " + "430");
	    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_DELETE_BY_EVENT_ID);
	    int res = Tools_schemaIndexInfo.Test_INFO_NOAC_DELETE_BY_EVENT_ID(430, ps);
	    out.println("Number of deleted tuples : " + res);

		// select * from info where IdEvent = 430
		out.println("Select * from info where IdEvent = "+430);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.QUERY_NOAC_SELECT_INFO_BY_EVENT);
		lireResultSet(Tools_schemaIndexInfo.Test_INFO_SELECT_STAR_BY_EVENT_ID(430, ps), out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
