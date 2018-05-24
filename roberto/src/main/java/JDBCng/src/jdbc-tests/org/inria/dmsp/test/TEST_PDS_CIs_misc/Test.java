package org.inria.dmsp.test.TEST_PDS_CIs_misc;

import java.io.PrintWriter;
import test.jdbc.Tools;

import org.inria.dmsp.EP_PDS_TEST;
import org.inria.dmsp.schema.EP_TEST;
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
		int i = 0, IdGlobal = 1;
		byte j = 0;
		final String s = new String("Bob");

		// connect without authentication
		openConnection(dbmsHost, null);

		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();

		// load the DB schema
		String schema = "/org/inria/dmsp/pds_test_schema.rsc";
		byte[] schemaDesc = t.loadSchema(schema);
		int usedId = 404;
		Install_DBMS_MetaData(schemaDesc, usedId);
	    schemaDesc = null;

	    //int ts_spt = ((impl.jdbc.Connection) db).getGlobalTimestamp();
		//if(perf==0){out.println("// TS_SPT currently = "+ts_spt);}

	    //int id = ((impl.jdbc.Connection) db).getSptIdPatient();
		//if(perf==0){out.println("// Patient id = "+id);}

		/*
		 *	INSERTS
		 */
		//if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_INSERT);

		// Do a few (1 -> 127) inserts in NOR
		Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,0,0, s, 0/*filtre*/, ps);
		print_perfs_caption();
		while ( j < 20 )	print_perfs( j++ );
		for(i=1; i<127; i++){
			Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,i%3,0, s, i%2/*filtre*/, ps);
		}
		j = 0;

		// Now the next insert (128) will trigger a NAND write
		Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,2,0, s, 2/*filtre*/, ps);
		print_perfs_caption();
		while ( j < 20 )	print_perfs( j++ );
		j = 0;

		// Do 127 inserts (129 -> 255) in NOR again
		for(i=0; i<127; i++){
			Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,i%3,0, s, i%2/*filtre*/, ps);
		}

		// Now the next insert (256) will trigger a NAND write for KA and one for SKA
		Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,2,0, s, 2/*filtre*/, ps);
		print_perfs_caption();
		while ( j < 20 )	print_perfs( j++ );
		j = 0;

		// Do 192 inserts (257 -> 448) again to have some tuples without SKA
		for(i=0; i<192; i++){
			Tools_dmsp.Test_FORMULAIRE_INSERT(IdGlobal++, 0,i%3,0, s, i%2/*filtre*/, ps);
		}

		/*
		 *	QUERIES without CI on IdGlobal (unique)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_ID_NO_CI);

		// Query one item in NOR (409)
		rs = Tools_dmsp.Test_SELECT_BY_INT(409, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID_NO_CI -> ID=409");}
		show_perfs( rs, out );

		// Query one item in NAND, without SKA (367)
		rs = Tools_dmsp.Test_SELECT_BY_INT(367, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID_NO_CI -> ID=367");}
		show_perfs( rs, out );

		// Query one item in NAND, with a SKA (53)
		rs = Tools_dmsp.Test_SELECT_BY_INT(53, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID_NO_CI -> ID=53");}
		show_perfs( rs, out );

		/*
		 *	QUERIES with CI on IdGlobal (unique, primary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_ID);

		// Query one item in NOR (409)
		rs = Tools_dmsp.Test_SELECT_BY_INT(409, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=409");}
		show_perfs( rs, out );

		// Query one item in NAND, without SKA (367)
		rs = Tools_dmsp.Test_SELECT_BY_INT(367, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=367");}
		show_perfs( rs, out );

		// Query one item in NAND, with a SKA (53)
		rs = Tools_dmsp.Test_SELECT_BY_INT(53, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=53");}
		show_perfs( rs, out );
		
		/*
		 *	QUERIES with CI full index scan on IdGlobal (unique, primary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_ID_FIS);

		// Query one item in NOR (409)
		rs = Tools_dmsp.Test_SELECT_BY_INT(409, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=409");}
		show_perfs( rs, out );

		// Query one item in NAND, without SKA (367)
		rs = Tools_dmsp.Test_SELECT_BY_INT(367, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=367");}
		show_perfs( rs, out );

		// Query one item in NAND, with a SKA (53)
		rs = Tools_dmsp.Test_SELECT_BY_INT(53, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=53");}
		show_perfs( rs, out );
		
		/*
		 *	QUERIES with CI summary scan on IdGlobal (unique, primary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_ID_SS);

		// Query one item in NOR (409)
		rs = Tools_dmsp.Test_SELECT_BY_INT(409, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=409");}
		show_perfs( rs, out );

		// Query one item in NAND, without SKA (367)
		rs = Tools_dmsp.Test_SELECT_BY_INT(367, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=367");}
		show_perfs( rs, out );

		// Query one item in NAND, with a SKA (53)
		rs = Tools_dmsp.Test_SELECT_BY_INT(53, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID=53");}
		show_perfs( rs, out );

		/*
		 *	QUERIES without CI on Filtre (not unique)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI);

		// Query for 0 (low selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(0, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI -> ID=0");}
		show_perfs( rs, out );

		// Query for 2 (very high selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(2, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI -> ID=2");}
		show_perfs( rs, out );

		// Query for 3 (no match)
		rs = Tools_dmsp.Test_SELECT_BY_INT(3, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE_NO_CI -> ID=3");}
		show_perfs( rs, out );

		/*
		 *	QUERIES with CI on Filtre (not unique, secondary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_FILTRE);

		// Query for 0 (low selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(0, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=0");}
		show_perfs( rs, out );

		// Query for 2 (very high selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(2, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=2");}
		show_perfs( rs, out );

		// Query for 3 (no match)
		rs = Tools_dmsp.Test_SELECT_BY_INT(3, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=3");}
		show_perfs( rs, out );
		
		/*
		 *	QUERIES with CI full index scan on Filtre (not unique, secondary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_FILTRE_FIS);

		// Query for 0 (low selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(0, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=0");}
		show_perfs( rs, out );

		// Query for 2 (very high selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(2, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=2");}
		show_perfs( rs, out );

		// Query for 3 (no match)
		rs = Tools_dmsp.Test_SELECT_BY_INT(3, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=3");}
		show_perfs( rs, out );
		
		/*
		 *	QUERIES with CI summary scan on Filtre (not unique, secondary index)
		 */
		ps = db.prepareStatement(EP_PDS_TEST.EP_FORMULAIRE_SELECT_BY_FILTRE_SS);

		// Query for 0 (low selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(0, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=0");}
		show_perfs( rs, out );

		// Query for 2 (very high selectivity)
		rs = Tools_dmsp.Test_SELECT_BY_INT(2, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=2");}
		show_perfs( rs, out );

		// Query for 3 (no match)
		rs = Tools_dmsp.Test_SELECT_BY_INT(3, ps);
		//if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_FILTRE -> ID=3");}
		show_perfs( rs, out );

		/*
		 *	DEBUG
		 */
		// SELECT STAR FROM STAR:
		if(perf==0)
		{
			out.println("EP_FORMULAIRE_SELECT_STAR");
			rs = st.executeQuery(EP_TEST.EP_FORMULAIRE_SELECT_STAR);
			lireResultSet(rs, out);
		}

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
	
	public void show_perfs(java.sql.ResultSet rs, PrintWriter out) throws Exception {
		byte j = 0;
		
		lireResultSet_perf0( rs, out );
		print_perfs_caption();
		while ( j < 20 )	print_perfs( j++ );
	}
}
