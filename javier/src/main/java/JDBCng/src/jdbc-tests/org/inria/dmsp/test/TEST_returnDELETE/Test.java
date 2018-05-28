package org.inria.dmsp.test.TEST_returnDELETE;

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
		
		if(perf==0){out.println("// ---------- TEST : DELETE INFO ------------ ");}
		
		if(perf==0){out.println("// NON-EXISTING TUPLE IdG=1234567, only rows");}
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=1234567");}
		int res = Tools_dmsp.Test_DELETE_BY_ID(1234567, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		
		if(perf==0){out.println("// NON-EXISTING TUPLE IdG=2345678, rows + keys");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID, java.sql.Statement.RETURN_GENERATED_KEYS);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=2345678");}
		res = Tools_dmsp.Test_DELETE_BY_ID(2345678, ps);
		int kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + "; generated key = " + kres + ".");}
		
		if(perf==0){out.println("// EXISTING TUPLE IdG=1126, only rows");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=1126");}
		res = Tools_dmsp.Test_DELETE_BY_ID(1126, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		
		if(perf==0){out.println("// EXISTING TUPLE IdG=1127, rows + keys");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID, java.sql.Statement.RETURN_GENERATED_KEYS);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=1127");}
		res = Tools_dmsp.Test_DELETE_BY_ID(1127, ps);
		kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + "; generated key = " + kres + ".");}
		
		if(perf==0){out.println("// SEVERAL NON-EXISTING TUPLES IdEvent=3456789, only rows");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_EVENT);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idEvent=3456789");}
		res = Tools_dmsp.Test_DELETE_BY_ID(3456789, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		
		if(perf==0){out.println("// SEVERAL NON-EXISTING TUPLES IdEvent=4567890, rows + keys");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_EVENT, java.sql.Statement.RETURN_GENERATED_KEYS);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idEvent=4567890");}
		res = Tools_dmsp.Test_DELETE_BY_ID(4567890, ps);
		kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + "; generated key = " + kres + ".");}
		
		if(perf==0){out.println("// SEVERAL EXISTING TUPLES IdEvent=1115, only rows");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_EVENT);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idEvent=1115");}
		res = Tools_dmsp.Test_DELETE_BY_ID(1115, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		
		if(perf==0){out.println("// SEVERAL EXISTING TUPLES IdEvent=1116, rows + keys");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_EVENT, java.sql.Statement.RETURN_GENERATED_KEYS);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idEvent=1116");}
		res = Tools_dmsp.Test_DELETE_BY_ID(1116, ps);
		kres = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + "; generated key = " + kres + ".");}

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
