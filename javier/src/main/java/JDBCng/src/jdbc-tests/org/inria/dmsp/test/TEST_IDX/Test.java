package org.inria.dmsp.test.TEST_IDX;

import java.io.PrintWriter;
import test.jdbc.Tools;

import org.inria.dmsp.EP_IDX;
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
		byte k = 0;
		
		// DBSize: 90k
		// *Dup100: an exact match returns 100 tuples
		// *Dup10 :                        10
		// *Ms1   :                        1% of the tuples
		// *Ms10  :                        10%
		int dbsize = 90000,
			dup100 = dbsize / 100,
			dup10 = dbsize / 10,
		    ms1 = (int)(dbsize / (dbsize / 100)),
			ms10 = (int)(dbsize / (dbsize / 10));

		// connect without authentication
		openConnection(dbmsHost, null);

		// the statement for non-parametric select:
		//java.sql.Statement st = db.createStatement();

		// load the DB schema
		String schema = "/org/inria/dmsp/idx_schema.rsc";
		byte[] schemaDesc = t.loadSchema(schema);
		int usedId = 404;
		Install_DBMS_MetaData(schemaDesc, usedId);
	    schemaDesc = null;

		/*
		 *	INSERTS
		 */
		//if(perf==0){out.println("// Insertion dans la table MyTable ");}
		ps = db.prepareStatement(EP_IDX.EP_POPULATE);

		for(i=1; i<=dbsize; i++){
			populate(i, i % dup100, i % dup10, i % ms1, i % ms10,
					 "AAAA" + i % dup100, "BBBB" + i % dup10, "CCCC" + i % ms1, "DDDD" + i % ms10,
					 "" + i, ps);
			if(i<1025){
				if(i==1||i%100==0||i%128==0){
					print_perfs_caption();
					while ( k < 20 )	print_perfs( k++ );
					k = 0;
				}
			}
		}

		// Queries on IdGlobal
		i = dbsize / 2;
		ps = db.prepareStatement(EP_IDX.EP_IDGLOBAL_NO);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_IDGLOBAL_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_IDGLOBAL_SS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_IDGLOBAL_HS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		// Queries on NumDup100
		i = dup100 / 2;
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP100_NO);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP100_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP100_SS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP100_HS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		// Queries on NumDup10
		i = dup10 / 2;
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMDUP10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );

		// Queries on NumMs1
		i = ms1 / 2;
		ps = db.prepareStatement(EP_IDX.EP_NUMMS1_NO);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS1_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS1_SS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS1_HS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		// Queries on NumMs10
		i = ms10 / 2;
		ps = db.prepareStatement(EP_IDX.EP_NUMMS10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_NUMMS10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_INT(i, ps);
		show_perfs( rs, out );

		// Queries on ChDup100
		i = dup100 / 2;
		ps = db.prepareStatement(EP_IDX.EP_CHDUP100_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("AAAA" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP100_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("AAAA" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP100_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("AAAA" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP100_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("AAAA" + i, ps);
		show_perfs( rs, out );
		
		// Queries on ChDup10
		i = dup10 / 2;
		ps = db.prepareStatement(EP_IDX.EP_CHDUP10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BBBB" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BBBB" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BBBB" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHDUP10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("BBBB" + i, ps);
		show_perfs( rs, out );

		// Queries on ChMs1
		i = ms1 / 2;
		ps = db.prepareStatement(EP_IDX.EP_CHMS1_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("CCCC" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS1_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("CCCC" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS1_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("CCCC" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS1_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("CCCC" + i, ps);
		show_perfs( rs, out );
		
		// Queries on ChMs10
		i = ms10 / 2;
		ps = db.prepareStatement(EP_IDX.EP_CHMS10_NO);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("DDDD" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS10_FIS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("DDDD" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS10_SS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("DDDD" + i, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_CHMS10_HS);
		rs = Tools_dmsp.Test_SELECT_BY_STRING("DDDD" + i, ps);
		show_perfs( rs, out );
		
		// Fake queries
		ps = db.prepareStatement(EP_IDX.EP_FAKE);
		rs = Tools_dmsp.Test_SELECT_BY_INT(1, ps);
		show_perfs( rs, out );
		
		ps = db.prepareStatement(EP_IDX.EP_FAKE_DUMMY);
		rs = Tools_dmsp.Test_SELECT_BY_INT(1, ps);
		show_perfs( rs, out );

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
	
	public void show_perfs(java.sql.ResultSet rs, PrintWriter out) throws Exception {
		byte j = 0;
		
		lireResultSet_perf0( rs, out );
		print_perfs_caption();
		while ( j < 20 )	print_perfs( j++ );
	}
}
