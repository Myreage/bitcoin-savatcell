package org.inria.dmsp.test.TEST_EP_UI_yanli;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

///////////////////////////////
// DO NOT CHANGE AFTER HERE  //
///////////////////////////////



import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import test.jdbc.Tools;
import test.runner.ITest;

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

		// connect information:
		String authent[] = new String [2];
		// initialize the driver:
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// the results' set used for the queries:
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

		st = db.createStatement();

		// TEST BEGIN 1
		// EP_SELECT_NOAC_INFO_BY_EVENT
		if(perf==0){out.println("----------- TEST BEGIN 1 ------------ = ");}

		// select * from info
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		//--
		// SELECT IdGlobal FROM Info WHERE IdEvent = ?
		if(perf==0){out.println("EP_SELECT_NOAC_INFO_BY_EVENT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_SELECT_NOAC_INFO_BY_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1017, ps), out);
		// TEST END 1

		/*
		 * Build 7 Plans for Gelmato (06-11-2009) 
		 */
		// test 5: SELECT Nom, Prenom FROM UserDMSP u WHERE u.idGlobal = ?
		if(perf==0){out.println("----------- TEST BEGIN II-1 ------------ = ");}

		// select * from user
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);

		//--
		// SELECT Nom, Prenom FROM UserDMSP u WHERE u.idGlobal = ?
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_BY_ID");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1064, ps), out);

		// test 6: SELECT ValChar, Position FROM Info WHERE IdEvent = ? AND Filtre = ?
		if(perf==0){out.println("----------- TEST BEGIN II-2 ------------ = ");}

		//--
		// SELECT ValChar, Position FROM Info WHERE IdEvent = ? AND Filtre = ?
		if(perf==0){out.println("EP_QUERY_SELECT_EVENT_INFO_BY_EVENT_AND_FILTRE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_SELECT_EVENT_INFO_BY_EVENT_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT(1017, 0, ps), out);

		// test 7: SELECT ValNum FROM Info WHERE IdEvent = ? AND Position = ?
		if(perf==0){out.println("----------- TEST BEGIN II-3 ------------ = ");}

		//--
		// SELECT ValNum FROM Info WHERE IdEvent = ? AND Position = ?
		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_INFO_BY_EVENT_AND_POSITION");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_EVENT_AND_POSITION);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT(1017, 1, ps), out);

		/*
		 * Build 7 Plans for Gelmato (18-11-2009) 
		 */	
		// close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067";  authent[1]= "1075"; 
		openConnection(dbmsHost, authent);

		// SELECT i.IdGlobal, i.Position, i.ValChar, i.ValDate, i.ValNum, i.IdEvent, c.ValComment 
	    // FROM event e, info i, comment c, Formulaire f WHERE i.IdEvent=e.IdGlobal and i.IdComment=c.IdGlobal and e.IdForm = f.IdGlobal and f.IdGlobal=? and e.DateEvent > ? and e.DateEvent < ?
		if (perf == 0) {out.println("========== Q1 ==========");}

		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_BETWEEN_DATEEVENT);
		rs = Tools_dmsp.Test_SELECT_BY_INT_AND_DATE_AND_DATE(1013, java.sql.Date.valueOf("2009-09-07"), java.sql.Date.valueOf("2009-12-01"), ps);
		lireResultSet(rs, out);

		// SELECT i.IdEvent, i.Position, i.ValChar, i.ValDate, i.ValNum FROM event e, info i, 
		// Formulaire f WHERE i.IdEvent= e.IdGlobal and e.IdForm = f.IdGlobal and f.IdGlobal=? 
		if (perf == 0) {out.println("========== Q2 ==========");}

		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_INFOnoc_BY_FORM);
		rs = Tools_dmsp.Test_SELECT_BY_INT(1013, ps);
		lireResultSet(rs, out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
