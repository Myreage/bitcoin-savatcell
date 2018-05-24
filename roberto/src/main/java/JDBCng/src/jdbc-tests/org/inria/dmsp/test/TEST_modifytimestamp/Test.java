package org.inria.dmsp.test.TEST_modifytimestamp;

/////////////////////////////////////////////////////
// IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////
// TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////

import test.runner.ITest;		// super class
import test.jdbc.Tools;			// tools specified for any DB schema

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

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
		String authent[] = new String [2];

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

		// get timestamp:
		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
	    if (perf == 0) {out.println("==========> getGlobalTimestamp: tsspt = "+ts_spt);}

	    // incr. timestamp:
	    ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
	    if (perf == 0) {out.println("==========> incGlobalTimestamp: tsspt = "+ts_spt);}

	    // incr. timestamp:
	    ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
	    if (perf == 0) {out.println("==========> incGlobalTimestamp: tsspt = "+ts_spt);}

		//--
		// Insert a small form (Fiche de liaison)
		//--
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 0(Amaury), role 1(patient)
		authent[0] = "0"; authent[1]= "1"; 
		openConnection(dbmsHost, null);//authent);
	    if (perf == 0) {out.println("========== INSERT FORM ==========");}
	    int eventid = insert(db, 1 /* fiche de liaison */, 0 /* GIR medical */);
	    if (perf == 0) {out.println("========== FORM INSERTED : eventid =  ==========" + eventid);}

		// get timestamp:
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
	    if (perf == 0) {out.println("==========> tsspt = "+ts_spt);}

	    // incr. timestamp:
	    ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
	    if (perf == 0) {out.println("==========> incGlobalTimestamp: tsspt = "+ts_spt);}

	    // add 1000 to the timestamp:
	    int gap = 1000;
	    if (perf == 0) {out.println("==========> added to tsspt : "+gap);}
		((org.inria.jdbc.Connection)db).setGlobalTimestamp(ts_spt+gap);

		// get timestamp:
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
	    if (perf == 0) {out.println("==========> tsspt = "+ts_spt);}

		//--
		// Q7: Insert a small form (Fiche de liaison)
		//--
	    if (perf == 0) {out.println("========== INSERT FORM ==========");}
	    eventid = insert(db, 1 /* fiche de liaison */, 0 /* GIR medical */);
	    if (perf == 0) {out.println("========== FORM INSERTED : eventid =  ==========" + eventid);}

		// get timestamp:
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
	    if (perf == 0) {out.println("==========> tsspt = "+ts_spt);}

		// select * from *
		select_star();

		// Commit and clean exit
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	private int insert(java.sql.Connection db, int nb_sform, int nb_bform) throws Exception
	{
		java.sql.PreparedStatement ps;
		int t2, t3, tmp_co, tmp_ev=0, no_co; // temporary vars
		// idglobal of no comment is 3:
		no_co = 3;

		// insert small forms (fiche de liaison)
		for (int i = 0; i < nb_sform; i++) {
			// insert 1 tuple into EVENT 
			if (perf == 0) {out.println("// Insertion in table EVENT");}
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
					java.sql.Statement.RETURN_GENERATED_KEYS);
			int nb = Tools_dmsp.Test_EVENT_INSERT(0, 0, 1012 /*formid*/, 1022 /*userid*/, 
					2 /*no_ep*/, java.sql.Date.valueOf("2000-10-20"), 
					java.sql.Date.valueOf("2000-10-20"), 12 /*filtre*/, ps);
			if (perf == 0) {out.println(nb+" tuple inserted...");}
			// ask for generated key
		    tmp_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());

			// insert 1 tuple into COMMENT		
			if (perf == 0) {out.println("// Insertion in table COMMENT");}
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, 
					java.sql.Statement.RETURN_GENERATED_KEYS);
			nb = Tools_dmsp.Test_COMMENT_INSERT(0, 0, "comment can be big, but more than 450 bytes can't be written", ps);
			if (perf == 0) {out.println(nb+" tuple inserted...");}
			// ask for generated key
		    tmp_co = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());

			// insert 6 tuples into INFO, the first is linked to the comment
			t3 = 0; // current value of field info.position 
			for (int j = 0; j < 6; j++) {
				// at each (nb_in/nb_co) infos:
				if (j == 0)
					t2 = tmp_co; // link info to the last comment
				else 
					t2 = no_co; // link info to the comment no_comment

				if (perf == 0) {out.println("// Insertion in table INFO");}
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
						java.sql.Statement.RETURN_GENERATED_KEYS);
				nb = Tools_dmsp.Test_INFO_INSERT(
					0, 0, tmp_ev /*event*/, t2 /*comment*/, "char value", i++,
					java.sql.Date.valueOf("2000-10-20"), 
					t3++ /*position*/, 1 /*filtre*/, j, ps);
				if (perf == 0) {out.println(nb+" tuple inserted...");}
			}
			// commit;
			Save_DBMS_on_disk();
			// check form insertion:
			if(perf==0){
				out.println("SELECT INSERTED FORM (fiche de liaison): event n°"+tmp_ev);
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
				lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(tmp_ev, ps), out); 
			}
		}
		// insert big forms (GIR Med)
		for (int i = 0; i < nb_bform; i++) {
			// one big form is generated after several small forms
			//insert 1 tuple into EVENT
			if (perf == 0) {out.println("// Insertion in table EVENT");}
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
					java.sql.Statement.RETURN_GENERATED_KEYS);
			int nb = Tools_dmsp.Test_EVENT_INSERT(
					0, 0, 1006 /*GIR medical*/, 1021, 2, 
					java.sql.Date.valueOf("2000-10-20"), 
					java.sql.Date.valueOf("2000-10-20"), 12, ps);
			if (perf == 0) {out.println(nb+" tuple inserted...");}
			tmp_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());

			// insert 73 tuples into INFO
			t3 = 0; // current value of field info.position 
			for (int j = 0; j < 73; j++) {
				// 24 tuples are linked to newly inserted comments:
				if (j >= 24) {
					t2 = no_co; // link info to the comment no_comment
				} 
				else {
					// insert 24 tuples into COMMENT
					if (perf == 0) {out.println("// Insertion in table COMMENT");}
					ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, 
							java.sql.Statement.RETURN_GENERATED_KEYS);
					nb = Tools_dmsp.Test_COMMENT_INSERT(0, 0, "comment can be big, but more than 450 bytes can't be written", ps);
					if (perf == 0) {out.println(nb+" tuple inserted...");}
					t2 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys()); // link info to one inserted comment
				}

				if (perf == 0) {out.println("// Insertion in table INFO");}
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
						java.sql.Statement.RETURN_GENERATED_KEYS);
				nb = Tools_dmsp.Test_INFO_INSERT(
					0, 0, tmp_ev /*event*/, t2 /*comment*/, "char value", i,
					java.sql.Date.valueOf("2000-10-20"), 
					t3++ /*position*/, 1 /*filtre*/, j, ps);
				if (perf == 0) {out.println(nb+" tuple inserted...");}
			}
			// commit;
			Save_DBMS_on_disk();
			// check form insertion:
			if(perf==0){
				out.println("SELECT INSERTED FORM (GIR Medical): event n°"+tmp_ev);
				ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
				lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(tmp_ev, ps), out); 
			}
		}
		return tmp_ev;
	}

	// SELECT STAR FROM STAR
	public void select_star() throws Exception {

		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
		// the resultset used for the queries:
		java.sql.ResultSet rs;

		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);

		// SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		// SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);
	}
}