package org.inria.dmsp.test.TEST_PDS_CIs_strings;

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.Tools_dmsp;

import test.runner.ITest;

public class Test extends Tools implements ITest {

	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {

		this.out = out;
		perf = 0;	// 0 for traces (test traces ==> perf)

		Tools_dmsp t = new Tools_dmsp(out);
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ;
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		int i = 0;
		//byte k = 0;
		
		// DBSize: 90k
		// *Dup100: an exact match returns 100 tuples
		// *Dup10 :                        10
		// *Ms1   :                        1% of the tuples
		// *Ms10  :                        10%
		int dbsize = 500,
			dup100 = dbsize / 100,
			dup10 = dbsize / 10,
		    ms1 = (int)(dbsize / (dbsize * 0.01)),
			ms10 = (int)(dbsize / (dbsize * 0.1));

		// connect without authentication
		openConnection(dbmsHost, null);

		// the statement for non-parametric select:
		//java.sql.Statement st = db.createStatement();

		// load the DB schema
		String schema = "/org/inria/dmsp/idx_schemaV4.rsc";
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
		 *	INSERTS
		 */
		//if(perf==0){out.println("// Insertion dans la table MyTable ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_POPULATE);

		for(i=1; i<=dbsize; i++){
			populate(i, i % dup100, i % dup10, i % ms1, i % ms10,
					 "A" + i % dup100 + "AA", "BC" + i % dup10 + "DEF", "111" + i % ms1 + "KKJ", "" + i % ms10 + "A1Z2",
					 "" + i + "ABCDEFGHIJKLMNOPQRSTUVWXYZ", ps);
		}

		// Queries on ChDup100
		i = dup100 / 2;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP100_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("A" + i + "AA", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP100_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("A" + i + "AA", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP100_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("A" + i + "AA", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP100_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("A" + i + "AA", ps);
		lireResultSet(rs, out);
		
		// Queries on ChDup10
		i = dup10 / 2;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BC" + i + "DEF", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BC" + i + "DEF", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BC" + i + "DEF", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHDUP10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BC" + i + "DEF", ps);
		lireResultSet(rs, out);

		// Queries on ChMs1
		i = ms1 / 2;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS1_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("111" + i + "KKJ", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS1_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("111" + i + "KKJ", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS1_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("111" + i + "KKJ", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS1_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("111" + i + "KKJ", ps);
		lireResultSet(rs, out);
		
		// Queries on ChMs10
		i = ms10 / 2;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("" + i + "A1Z2", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("" + i + "A1Z2", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("" + i + "A1Z2", ps);
		lireResultSet(rs, out);
		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_IDX.EP_CHMS10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("" + i + "A1Z2", ps);
		lireResultSet(rs, out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
	
	public int populate(int idglobal, int numdup100, int numdup10, int numms1, int numms10,
						String chdup100, String chdup10, String chms1, String chms10,
						String something, java.sql.PreparedStatement Pstmt) throws Exception {
		Pstmt.setInt(1, idglobal);
		Pstmt.setInt(2, numdup100);
		Pstmt.setInt(3, numdup10);
		Pstmt.setInt(4, numms1);
		Pstmt.setInt(5, numms10);
		Pstmt.setString(6, chdup100);
		Pstmt.setString(7, chdup10);
		Pstmt.setString(8, chms1);
		Pstmt.setString(9, chms10);
		Pstmt.setString(10, something);
		int res = Pstmt.executeUpdate();
		return res;
	}
}
