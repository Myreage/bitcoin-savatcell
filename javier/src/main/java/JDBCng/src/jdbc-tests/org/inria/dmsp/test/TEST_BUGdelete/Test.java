package org.inria.dmsp.test.TEST_BUGdelete;

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
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		// the statement for non-parametric select:
		java.sql.Statement st;
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

		// select * from *
//		select_star();

		// insert a fiche de liaison: (rather than résumé obs)
		int info_id = insert(db);

		// scan the infos :
		if(perf==0){out.println("========== ALL INFOS ======== ");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("========== ALL INFOS END ======== ");}

		/*
		 *  TEST INFO_AC_UPDATE_BY_ID (UI):
         */
		if(perf==0){out.println("// ---------- TEST : DELETE INFO ------------ ");}

		int IdGlobal_info = info_id; 	// info liked to event 1017: from 1021 to 1060 // TOD : change ref to jjv update

		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}

		// SELECT THE INFO (BEFORE DELETE):
		if(perf==0){out.println("-- BEFORE DELETE : SELECT INFO with IdG="+IdGlobal_info);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_info, ps), out);

		// TRY TO DLETE THE INFO:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG="+IdGlobal_info);}
		int res = Tools_dmsp.Test_DELETE_BY_ID(IdGlobal_info, ps);
		if(perf==0){out.println("--> delete return: "+res+" row(s) deleted...");}

		// SELECT THE INFO (AFTER DELETE):
		if(perf==0){out.println("-- AFTER DELETE : SELECT INFO with IdG="+IdGlobal_info);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_info, ps), out);

		// Case 2: Delete the normal tuple which is less than NB_MAX_UPDATE_DELETE(500) 
/*		// SELECT THE INFO (BEFORE DELETE):
		if(perf==0){out.println("-- BEFORE DELETE : SELECT INFO with IdG="+1126);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1126, ps), out);
*/		
		// TRY TO DLETE THE INFO:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_INFO_AC_DELETE_BY_ID);
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
		if(perf==0){out.println("-- TRY TO DELETE INFO with idG="+1126);}
		res = Tools_dmsp.Test_DELETE_BY_ID(1126, ps);
		if(perf==0){out.println("--> delete return: "+res+" row(s) deleted...");}

		// SELECT THE INFO (AFTER DELETE):
		if(perf==0){out.println("-- AFTER DELETE : SELECT INFO with IdG="+1126);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1126, ps), out);

	/*	// generate a FP in update/delte list :
		// insert 100 fiche de liaison: we have more than 500 infos => a FP in update/delete list is there
		for(int i=0;i<100;i++){
			perf=1; insert(db); perf=0;
		}*/
		// scan the infos :
		if(perf==0){out.println("========== ALL INFOS ======== ");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("========== ALL INFOS END ======== ");}

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
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

		//SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);
	}

	private int insert(java.sql.Connection db) throws Exception {
		
		java.sql.PreparedStatement ps;
		int t2, t3, tmp_co, tmp_ev, tmp_in=0, no_co; // temporary vars
		// idglobal of no comment is 3:
		no_co = 3;

		// insert small forms (fiche de liaison)
		// insert 1 tuple into EVENT 
		if (perf == 0) {out.println("// Insertion in table EVENT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		int nb = Tools_dmsp.Test_EVENT_INSERT(0, 0, 1012 /*formid*/, 1021 /*userid*/, 
				2 /*no_ep*/, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 12 /*filtre*/, ps);
		if (perf == 0) {out.println(nb+" tuple inserted...");}
		// ask for generated key
	    tmp_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		
		// insert 1 tuple into COMMENT		
		if (perf == 0) {out.println("// Insertion in table COMMENT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_COMMENT_INSERT(0, 0,
				"comment can be big, but more than 450 bytes can't be written", ps);
		if (perf == 0) {out.println(nb+" tuple inserted...");}
		// ask for generated key
	    tmp_co = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());

		// insert 6 tuples into INFO, the first is linked to the comment
		t3 = 0; // current value of field info.position 

		for (int j = 0; j < 6; j++) {
			// at each (nb_in/nb_co) infos:
			if (j == 0)
				t2 = tmp_co;	// link info to the last comment
			else 
				t2 = no_co;		// link info to the comment no_comment
			if (perf == 0) {out.println("// Insertion in table INFO");}
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
					java.sql.Statement.RETURN_GENERATED_KEYS);
			nb = Tools_dmsp.Test_INFO_INSERT(
					0, 0, tmp_ev /*event*/, t2 /*comment*/, "char value", 10101010,
					java.sql.Date.valueOf("2000-10-20"), 
					t3++ /*position*/, 1 /*filtre*/, j, ps);
			if (perf == 0) {out.println(nb+" tuple inserted...");}
			// ask for generated key
		    tmp_in = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		}
		// commit
		Save_DBMS_on_disk();
		// check form insertion:
		if (perf==0) {
			out.println("SELECT INSERTED FORM (fiche de liaison):");
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
			lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(tmp_ev, ps), out); 
		}

		// return an info linked to no-comment:
		return tmp_in;
	}		
}
