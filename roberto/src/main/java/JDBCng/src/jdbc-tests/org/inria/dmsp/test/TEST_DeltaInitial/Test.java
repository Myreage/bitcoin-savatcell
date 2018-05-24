package org.inria.dmsp.test.TEST_DeltaInitial;

/////////////////////////////////////////////////////
// IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

/////////////////////////////////////////////////////////////////////////
// TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////



import test.runner.ITest; // super class

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;
//import java.sql.Date;



import test.jdbc.Tools; // tools specified for any DB schema

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
		
		// initialize the driver:
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
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
		select_star();
		
		if(perf==0){out.println("// ---------- TEST 0 : build the required dataset ----------------------");}
		if(perf==0){out.println("// Insert into EVENT (form=1005/*GIR soc.*/, user=1091, episode=2) - plan = OEP_EVENT_AC_INSERT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		int nb = Tools_dmsp.Test_EVENT_INSERT(2,0, 1005/*form*/, 1091/*user*/, 2/*episode*/, 
				java.sql.Date.valueOf("2000-10-20"), java.sql.Date.valueOf("2000-10-30"), 1515/*filtre*/, ps);
		if(perf==0){out.println("//		--> Nb tuples inserted = "+ nb);}
		int eventid_girsoc = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("//		--> Generated IdGlobal = "+ eventid_girsoc);}
		// CHECK INSERTION:
		if(perf==0){out.println("//		--> select event by id (no ac):"+eventid_girsoc+" - plan = EP_EVENT_SELECT_BY_ID"); }
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_girsoc , ps), out);
		
		if(perf==0){out.println("// ---------- TEST 1 : medecinValideurGIR user/role = 1103/1107 ------------");}
		if(perf==0){out.println("// AC : S/U/D/I = 01 for all forms except for :");}
		if(perf==0){out.println("//		1005 : only 01 for S but 00 for U/D/I");}
		if(perf==0){out.println("//		1008 & 1009 : 00 for S/U/D/I");}

	    // close session and connect valideur/medecinValideurGIR:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		String[] authent = new String[2];
		authent[0] = "1103"; authent[1]= "1107"; openConnection(dbmsHost, authent);
		
		if(perf==0){out.println("// ********* non-authorized actions: ");}
		// insert 1 event with a GIR social (form 1005) 
		if(perf==0){out.println("// Insert into EVENT (form=1005/*GIR soc.*/, user=1091, episode=2) - plan = OEP_EVENT_AC_INSERT");}
		if(perf==0){out.println("//		--> should fail:"); }
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_EVENT_INSERT(2,0, 1005/*form*/, 1091/*user*/, 2/*episode*/, 
				java.sql.Date.valueOf("2000-10-20"), java.sql.Date.valueOf("2000-10-30"), 1234567890/*filtre*/, ps);
		if(perf==0){out.println("//		--> Nb tuples inserted = "+ nb);}
		int eventid_try = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("//		--> Generated IdGlobal = "+ eventid_try);}
		// CHECK INSERTION:
		if(perf==0){out.println("//		--> AFTER INSERT, select event no ac: "+ eventid_try);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_try , ps), out);

		if(perf==0){out.println("// ********** do set of authorized actions: ");}
		if(perf==0){out.println("// select from event by filtre (with AC): 1 - plan = EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE"); }
		if(perf==0){out.println("//		--> event linked to form=1005 must appear:"); }
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);

		if(perf==0){out.println("// update event (with AC) form=1005 /*GIR social*/ - plan = EP_EVENT_SELECT_BY_ID"); }
		if(perf==0){out.println("//		--> should fail"); }
		if(perf==0){out.println("//		--> BEFORE UPDATE (no ac): ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_girsoc, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EVENT_AC_UPDATE_BY_ID);
		int ts_spt= ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		int res = Tools_dmsp.Test_EVENT_UPDATE( 
				ts_spt /*ts_spt*/,
				java.sql.Date.valueOf("2222-02-02")/*dateEvent*/,
				java.sql.Date.valueOf("2222-02-02")/*dateFin*/, 
				888/*filtre*/, 
				eventid_girsoc/*idG*/, ps);
		// CHECK UPDATE:
		if(perf==0){out.println("//		--> TRY TO UPDATE (with ac)... number of rows udpated "+res+"... AFTER UPDATE (no ac): ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_girsoc, ps), out);

		if(perf==0){out.println("// delete event (with AC) form=1005 /*GIR social*/ - plan = "); }
		if(perf==0){out.println("//		--> should fail:"); }
		// TODO: when delete event plan available, test if delete event N°eventid_girsoc with AC fails as expected

		
		if(perf==0){out.println("// ---------- TEST 2 : quidam user/role = 1102/1106 ------------");}
		if(perf==0){out.println("// AC : S/U/D = 00 and I = 01 for any form ");}
		if(perf==0){out.println("// AC : S/U/D/I = 00 for all forms except fiche de liaison /*1012*/ ");}
		
		if(perf==0){out.println("// ********* non-authorized actions: ");}
		// insert 1 event with a GIR social (form 1005) 
		if(perf==0){out.println("// Insert into EVENT (form=1005/*GIR soc.*/, user=1091, episode=2) - plan = OEP_EVENT_AC_INSERT");}
		if(perf==0){out.println("//		--> should fail:"); }
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_EVENT_INSERT(2,0, 1005/*form*/, 1091/*user*/, 2/*episode*/, 
				java.sql.Date.valueOf("2000-10-20"), java.sql.Date.valueOf("2000-10-30"), 1234567890/*filtre*/, ps);
		if(perf==0){out.println("//		--> Nb tuples inserted = "+ nb);}
		eventid_try = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("//		--> Generated IdGlobal = "+ eventid_try);}
		if(perf==0){out.println("//		--> AFTER INSERT, select event no ac: "+ eventid_try);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_try , ps), out);

		if(perf==0){out.println("// ********* authorized actions: ");}
		// insert 1 event with a GIR social (form 1005) 
		if(perf==0){out.println("// Insert into EVENT (form=1012/*fiche liaison*/, user=1091, episode=2) - plan = OEP_EVENT_AC_INSERT");}
		if(perf==0){out.println("//		--> should succeed:"); }
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_EVENT_INSERT(2,0, 1012/*form*/, 1091/*user*/, 2/*episode*/, 
				java.sql.Date.valueOf("2000-10-20"), java.sql.Date.valueOf("2000-10-30"), 1234567890/*filtre*/, ps);
		if(perf==0){out.println("//		--> Nb tuples inserted = "+ nb);}
		eventid_try = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("//		--> Generated IdGlobal = "+ eventid_try);}
		if(perf==0){out.println("//		--> AFTER INSERT, select event no ac: "+ eventid_try);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(eventid_try , ps), out);

		// close session and connect valideur/medecinValideurGIR:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		authent = new String[2];
		authent[0] = "1103"; authent[1]= "1107"; openConnection(dbmsHost, authent);
		
		
		// Commit and clean exit
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


	
}

		