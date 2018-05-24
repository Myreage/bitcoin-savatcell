package test.jdbc.INRIA_19;

/**
 * Test multiple DELETE/UPDATE commands together with rollback
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
		
		if(perf==0){out.println("========== FIRST UPDATE / DELETE BEGIN ======== ");}
		if(perf==0){out.println("Deleting some data in the DBMS");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=67");}
		int res = Tools_dmsp.Test_DELETE_BY_ID(67, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=69");}
		res = Tools_dmsp.Test_DELETE_BY_ID(69, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=71");}
		res = Tools_dmsp.Test_DELETE_BY_ID(71, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=73");}
		res = Tools_dmsp.Test_DELETE_BY_ID(73, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}

		if(perf==0){out.println("Updating some data in the DBMS");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.INFO_AC_UPDATE_BY_ID);
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=75");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO1", 1010101, java.sql.Date.valueOf("1111-11-01"), 888, 75, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=77");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO2", 2020202, java.sql.Date.valueOf("1212-12-02"), 889, 77, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=79");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO3", 1212121, java.sql.Date.valueOf("1313-03-03"), 890, 79, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

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
		
		if(perf==0){out.println("========== SECOND UPDATE / DELETE BEGIN ======== ");}
		if(perf==0){out.println("Deleting again the same data in the DBMS");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=67");}
		res = Tools_dmsp.Test_DELETE_BY_ID(67, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=69");}
		res = Tools_dmsp.Test_DELETE_BY_ID(69, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=71");}
		res = Tools_dmsp.Test_DELETE_BY_ID(71, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG=73");}
		res = Tools_dmsp.Test_DELETE_BY_ID(73, ps);
		if(perf==0){out.println("--> delete return: row(s) deleted = " + res + ".");}

		if(perf==0){out.println("Updating again the same data in the DBMS");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.INFO_AC_UPDATE_BY_ID);
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=75");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO1", 1010101, java.sql.Date.valueOf("1111-11-01"), 888, 75, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=77");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO2", 2020202, java.sql.Date.valueOf("1212-12-02"), 889, 77, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG=79");}
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "NEW INFO3", 1212121, java.sql.Date.valueOf("1313-03-03"), 890, 79, ps);
		if(perf==0){out.println("--> update return: row(s) updated = " + res + ".");}
		
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

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
