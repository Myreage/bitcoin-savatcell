package org.inria.dmsp.test.TEST_PDS_CIs_SS_HybridSkip2;

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DataGenerator;
import org.inria.dmsp.tools.Tools_dmsp;

import test.runner.ITest;

public class Test extends Tools implements ITest {
	 
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {

		this.out = out;
		perf = 0;	// for traces (test traces ==> perf)

		Tools_dmsp t = new Tools_dmsp(out);
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		int[] links = {0, 639, 1919};	// special pos
		int special_ev = 13;							//XXX: change that if you make changes to HybridSkip_INSERT_Data_Generated!

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

	    int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println("// TS_SPT currently = "+ts_spt);}

	    int id = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		if(perf==0){out.println("// Patient id = "+id);}

		/*
		 *  TEST EP_*_INSERTS (Synch) - except into MATRICE_PATIENT
         */
	    // Call the data generator
	    DataGenerator dg = new DataGenerator(out);
		dg.perf=perf;
		dg.HybridSkip_INSERT_Data_Generated(1920, links, db);
		
		/*
		 *  TEST EP_*_SELECT_BY_ID (Synch)
         */
		// EP_EVENT_SELECT_BY_IDFORM
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_SS.EP_EVENT_SELECT_BY_IDFORM);
		rs = Tools_dmsp.Test_SELECT_BY_INT(special_ev, ps);
		if(perf==0){out.println("EP_EVENT_SELECT_BY_IDFORM -> ID="+special_ev);}
		lireResultSet(rs, out);

		/*
		 * TEST_*_SELECT_STAR (EP_TEST)
		 */
		// SELECT STAR FROM STAR:
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
