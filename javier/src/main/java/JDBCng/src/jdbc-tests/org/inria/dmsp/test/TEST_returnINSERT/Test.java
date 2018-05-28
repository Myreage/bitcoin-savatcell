package org.inria.dmsp.test.TEST_returnINSERT;

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

import test.runner.ITest;

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

		java.sql.PreparedStatement ps;
		int res, kres;

		// initialize the driver:
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

	    // instantiate delta loader
	    DeltaLoader dl = new DeltaLoader(out, perf); 
		// open the delta file
	    InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("delta.csv"));
		// load the data into database
		dl.LoadDelta(fr, db); 

		if(perf==0){out.println("// ---------- TEST : INSERT COMMENT ------------ ");}

		if(perf==0){out.println("// SHOULD FAIL ");}
		if (perf == 0) {out.println("-- Insertion in table INFO, only rows");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT);
		res = Tools_dmsp.Test_INFO_INSERT(
			0, 0, 1234567, 1234567, "char value", 10101010,
			java.sql.Date.valueOf("2000-10-20"), 
			1234567, 1, 1234567, ps);
	    if(perf==0){out.println("--> update return: row(s) inserted = " + res + ".");}

		if(perf==0){out.println("// SHOULD FAIL ");}
		if (perf == 0) {out.println("-- Insertion in table INFO, rows + keys");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		res = Tools_dmsp.Test_INFO_INSERT(
			0, 0, 2345678, 2345678, "another char value", 10101010,
			java.sql.Date.valueOf("2000-10-20"), 
			2345678, 1, 2345678, ps);
	    kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
	    if(perf==0){out.println("--> update return: row(s) inserted = " + res + "; generated key = " + kres + ".");}

	    if(perf==0){out.println("// SHOULD SUCCEED ");}
	    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT);
		if(perf==0){out.println("-- TRY TO INSERT COMMENT, only rows");}
		res = Tools_dmsp.Test_COMMENT_INSERT(0, 0, "comment can be big, but more than 450 bytes can't be written", ps);
	    if(perf==0){out.println("--> update return: row(s) inserted = " + res + ".");}

	    if(perf==0){out.println("// SHOULD SUCCEED ");}
	    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		if(perf==0){out.println("-- TRY TO INSERT COMMENT, rows + keys");}
		res = Tools_dmsp.Test_COMMENT_INSERT(0, 0, "comment can be big, but more than 450 bytes can't be written", ps);
	    kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
	    if(perf==0){out.println("--> update return: row(s) inserted = " + res + "; generated key = " + kres + ".");}

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
