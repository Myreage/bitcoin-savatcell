package org.inria.dmsp.test.TEST_UpdateAfterReset_aydogan;

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DataGenerator;
import org.inria.dmsp.tools.Tools_dmsp;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

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
		// data generator
		DataGenerator dg;
		
		Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };

		// connect without authentication
		openConnection(dbmsHost, null);
		
		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println("// TS_SPT currently = "+ts_spt);}

		if(ts_spt==1)
		{
			// unload the DB schema
			Desinstall_DBMS_MetaData();
			
			// load the DB schema
			String schema = "/org/inria/dmsp/schemaV4.rsc";
			byte[] schemaDesc = t.loadSchema(schema);
			int usedId = 404;
			Install_DBMS_MetaData(schemaDesc, usedId);
		    schemaDesc = null;
		    
			// load and install QEPs
			QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
			QEPng.installExecutionPlans( db );
	
			/*
			 *  TEST EP_*_INSERTS (Synch) - except into MATRICE_PATIENT
	         */
		    // Call the data generator
		    dg = new DataGenerator(out);
			dg.perf=perf;
			dg.INSERT_Data_Generated(
					5 /*user*/, 3 /*role*/, 5 /*hab*/, 20 /*form*/, 1 /*episode*/,
					10 /*event*/, 30 /*comment*/, 100 /*info*/, db); 
	
			/*
			 *  TEST EP_*_UPDATE (Synch)
	         */
			// EP_INFO_UPDATE:
			// select first_info
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID=" + dg.first_in);}
			lireResultSet(rs, out);
			// update first_info
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_UPDATE);
			Tools_dmsp.Test_INFO_UPDATE(0, 0, 298, "updated varchar", 25, null, 50, 1, 205, dg.first_in, ps);
			if(perf==0){out.println("EP_INFO_UPDATE -> ID=" + dg.first_in);}
			// select first_info again
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID=" + dg.first_in);}
			lireResultSet(rs, out);
			
			((org.inria.jdbc.Connection) db).setGlobalTimestamp(dg.first_in);
			
			/*
			 * Commit
			 */
			Save_DBMS_on_disk();
		}
		else
		{
			((org.inria.jdbc.Connection)db).bypassInitialization();
			
			// load QEPs
			QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
			
			// select first_info
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
			rs = Tools_dmsp.Test_SELECT_BY_INT(ts_spt, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID=" + ts_spt);}
			lireResultSet(rs, out);
		}

		/*
		 * Clean exit
		 */
		Shutdown_DBMS();
	}
}
