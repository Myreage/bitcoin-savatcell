package test.jdbc.INRIA_18;

/**
 * Test big amount of insertions together with rollback
 *
 * @author Quentin Lefebvre
 * @date 2014/06/17
 */

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DataGenerator_2;
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
	    DataGenerator_2 dg = new DataGenerator_2(out);
		dg.perf=perf;
		dg.PDS_INSERT_Data_Generated(
				3 /*user*/, 3 /*role*/, 3 /*hab*/, 3 /*form*/, 1 /*episode*/,
				50 /*event*/, 3 /*comment*/, 13 /*info*/, db); 

		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		db.commit();
		
		if(perf==0){out.println("========== ALL INFOS END AFTER COMMIT======== ");}
		
		if(perf==0){out.println("Inserting LOTS OF data in the DBMS (about 131 100 tuples)");}
		dg.PDS_INSERT_Data_Generated(
				3 /*user*/, 3 /*role*/, 3 /*hab*/, 3 /*form*/, 1 /*episode*/,
				50 /*event*/, 3 /*comment*/, 131073 /*info*/, db); 

		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_INFO_INSERT);
		Tools_dmsp.Test_INFO_INSERT(dg.IdGlobal++, 0, 0, 0, 39, 66, "cc", 131073, java.sql.Date.valueOf("2001-03-03"), 1, 0, 131073, ps);
		
		if(perf==0){out.println("========== ALL INFOS END BEFORE ROLLBACK======== ");}
	    // ROLLBACK:
	    try{
	    	db.rollback();
	    } catch(Exception e){
	    	if(perf==0){out.println("\nRollback Failure: FUNC_NOR_ERASE_FAIL!");
	    	out.println("Restart DBMS and recover database.");}
	    }
		if(perf==0){out.println("=========== ROLLBACK DONE. ");}

		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		//SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);
		
		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
