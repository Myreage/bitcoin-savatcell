package org.inria.dmsp.test.TEST_4;

import java.io.PrintWriter;

import test.jdbc.Tools;

import java.io.InputStreamReader;

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

		if(perf==0){out.println("Connected user : medecinValideurGIR (user/role = 1103/1107) ------------");}
		if(perf==0){out.println("// AC : S/U/D/I = 01 for all forms except for :");}
		if(perf==0){out.println("//		1005 : only 01 for S but 00 for U/D/I");}
		if(perf==0){out.println("//		1008 & 1009 : 00 for S/U/D/I");}
	    // close session and connect valideur/medecinValideurGIR:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		String[] authent = new String[2];
		authent[0] = "1103"; authent[1]= "1107"; openConnection(dbmsHost, authent);

		/*
		 *  TEST EVENT_AC_UPDATE_BY_ID (UI):
         */
		if(perf==0){out.println("// ---------- TEST 1 : UPDATE EVENT ------------ ");}
		if(perf==0){out.println("/********** FIRST UPDATE: **********/");}
		int IdGlobal_event = 1017; 	// couple of possible (event, form) loaded from the delta: 
									// (1017,1001) (1018,1013) (1019,1002) (1020,1012)
	    int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
//	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
//		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}

		// SELECT THE EVENT (BEFORE UPDATE):
		if(perf==0){out.println("-- SELECT EVENT with IdG="+IdGlobal_event);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_event, ps), out);

		// TRY TO UPDATE THE EVENT:
		if(perf==0){out.println("-- TRY TO UPDATE EVENT with idG="+IdGlobal_event);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EVENT_AC_UPDATE_BY_ID);
		int res = Tools_dmsp.Test_EVENT_UPDATE(ts_spt, 
				java.sql.Date.valueOf("1111-01-01") /*dateEvent*/,
				java.sql.Date.valueOf("1111-01-02") /*DateFin*/, 1, 
				IdGlobal_event /*IdG_event*/,ps); 
		if(perf==0){out.println("--> update return: "+res+" row(s) updated...");}
//	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
//		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
//	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
//		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}

		// SELECT THE EVENT (AFTER UPDATE):
		if(perf==0){out.println("-- SELECT EVENT with IdG="+IdGlobal_event);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_event, ps), out);

		if(perf==0){out.println("/********** SECOND UPDATE: **********/");}
		IdGlobal_event = 1017; 	// couple of possible (event, form) loaded from the delta: 
									// (1017,1001) (1018,1013) (1019,1002) (1020,1012)
	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}

		// TRY TO UPDATE THE EVENT:
		if(perf==0){out.println("-- TRY TO UPDATE EVENT with idG="+IdGlobal_event);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EVENT_AC_UPDATE_BY_ID);
		res = Tools_dmsp.Test_EVENT_UPDATE(ts_spt, 
				java.sql.Date.valueOf("2222-02-01") /*dateEvent*/,
				java.sql.Date.valueOf("2222-02-02") /*DateFin*/, 1, 
				IdGlobal_event /*IdG_event*/,ps); 
		if(perf==0){out.println("--> update return: "+res+" row(s) updated...");}

		// SELECT THE EVENT (AFTER UPDATE):
		if(perf==0){out.println("-- SELECT EVENT with IdG="+IdGlobal_event);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_event, ps), out);

		/*
		 *  TEST INFO_AC_UPDATE_BY_ID (UI):
         */
		if(perf==0){out.println("// ---------- TEST 2 : UPDATE INFO ------------ ");}
		int IdGlobal_info = 1021; 	// info liked to event 1017: from 1021 to 1060 
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}

		// SELECT THE INFOS (BEFORE UPDATE):
		if(perf==0){out.println("-- SELECT INFO with IdG="+IdGlobal_info);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_info, ps), out);
		if(perf==0){out.println("-- SELECT INFO with IdG="+(IdGlobal_info+1));}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT((IdGlobal_info+1), ps), out);

		// TRY TO UPDATE THE INFO:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.INFO_AC_UPDATE_BY_ID);
		// First info: 
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG="+IdGlobal_info);}
		res = Tools_dmsp.Test_INFO_UPDATE( 
				ts_spt/*ts_spt*/,
				"NEW INFO1" /*valchar*/,
				1010101 /*valnum*/,
				java.sql.Date.valueOf("1111-11-01")/*valdate*/, 
				888 /*Filtre*/,
				IdGlobal_info /*idG*/, ps);
		if(perf==0){out.println("--> update return: "+res+" row(s) updated...");}
		// second info:
		ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println(" --> call to getGlobalTimestamp, TS_SPT = "+ts_spt);}
		if(perf==0){out.println("-- TRY TO UPDATE INFO with idG="+(IdGlobal_info+1));}
		res = Tools_dmsp.Test_INFO_UPDATE( 
				ts_spt/*ts_spt*/,
				"NEW INFO2" /*valchar*/,
				1010101 /*valnum*/,
				java.sql.Date.valueOf("1111-11-02")/*valdate*/, 
				888 /*Filtre*/,
				(IdGlobal_info+1) /*idG*/, ps);
		if(perf==0){out.println("--> update return: "+res+" row(s) updated...");}

		// SELECT THE INFOS (AFTER UPDATE):
		if(perf==0){out.println("-- SELECT INFO with IdG="+IdGlobal_info);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_info, ps), out);
		if(perf==0){out.println("-- SELECT INFO with IdG="+IdGlobal_info+1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdGlobal_info+1, ps), out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
	// SELECT STAR FROM STAR
	public void select_star() throws Exception
	{
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
