package org.inria.dmsp.test.TEST_EP_Synchro_yanliauto;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

/////////////////////////////////////////////////////////////////////////
//TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////



import test.runner.ITest;		// super class
import test.jdbc.Tools;			// tools specified for any DB schema

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

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
		// the results' set used for the queries:
		java.sql.ResultSet rs;
		// the statement for non-parametric select:
		java.sql.Statement st;
		// for loops:
		int i;
		// to store value of certificate
		byte[] certificate = new byte [20]; 
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

		// BEGIN TEST 1 : UPDATE Event SET Author = ?, TSSPT = ?, TSSANTEOS = ? ,  
		// DateEvent = ?, DateFin = ?, Filtre = ? WHERE IdGlobal = ? 
		if(perf==0){out.println("----------- TEST BEGIN 1 ------------ = ");}
		/*
		 *  UDPATE EVENT
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_UPDATE);
		Tools_dmsp.Test_EVENT_UPDATE(
				10,
				1,
				10,
				java.sql.Date.valueOf("2009-07-14") /*dateEvent*/,
				java.sql.Date.valueOf("2099-01-01") /*DateFin*/, 1, 
				1017 /*IdG_event*/,ps); 

		// select * from Event
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		// END TEST 1

		// BEGIN TEST 2
		// UPDATE User SET <all fields except IdGlobal> WHERE IdGlobal = ?
		if(perf==0){out.println("----------- TEST BEGIN 2 ------------ = ");}
		/*
		 *  UDPATE USER
		 */		
		// init certificate:
		for (i=0;i<certificate.length;i++)
			certificate[i] = 'b';

		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_UPDATE);
		Tools_dmsp.Test_USER_UPDATE(
				10,
				10,
				10,
				"bb",
				2,
				"bb",
				"bb",
				0,
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				0,
				certificate,
				4, 
				1062,ps); 

		// select * from user
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		// END TEST 2

		// BEGIN TEST 3
		if(perf==0){out.println("----------- TEST BEGIN 3 ------------ = ");}
		/*
		 *  UDPATE FORMULAIR
		 */	
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_UPDATE);
		Tools_dmsp.Test_FORMULAIRE_UPDATE(
				10,
				1,
				10,
				"aa",
				2,
				1001,ps); 

		// select * from Formulair 
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		// END TEST 3

		// BEGIN TEST 4
		if(perf==0){out.println("----------- TEST BEGIN 4 ------------ = ");}
		/*
		 *  UDPATE Role
		 */	
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_UPDATE);
		Tools_dmsp.Test_ROLE_UPDATE(0, 0, 300, "updated nom", 1075, ps);

		// select * from Role 
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 4

		// BEGIN TEST 5
		//--
		if(perf==0){out.println("----------- TEST BEGIN 5 ------------ = ");}
		// EP_EVENT_SELECT_ON_TSSPT
		// SELECT * FROM MatricePatient WHERE TSSPT > ?
		if(perf==0){out.println("EP_EVENT_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);	
		// END TEST 5

		// BEGIN TEST 6
		if(perf==0){out.println("----------- TEST BEGIN 6 ------------ = ");}
		// EP_USER_SELECT_ON_TSSPT
		// Select * from userdmsp where TSSPT > ?
		if(perf==0){out.println("EP_USER_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(9, ps), out);	
		// END TEST 6

		// BEGIN TEST 7
		if(perf==0){out.println("----------- TEST BEGIN 7 ------------ = ");}
		// EP_FORMULAIRE_SELECT_ON_TSSPT
		// Select * from formulaire where TSSPT > ?
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);	
		// END TEST 7

		// BEGIN TEST 8
		if(perf==0){out.println("----------- TEST BEGIN 8 ------------ = " +
		"Insert 2 tuples into TupleDeleted --> EP_DELETED_NOAC_INSERT");}

		// select * from TupleDeleted
		if(perf==0){out.println("EP_DELETED_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_DELETED_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO TupleDeleted
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_DELETED_NOAC_INSERT);
        // 1 tuple from info
		Tools_dmsp.Test_DELETED_INSERT(1344, 4, 0, 3, 0, ps);
		// 1 tuple from comment
		Tools_dmsp.Test_DELETED_INSERT(1345, 5, 0, 4, 0, ps);

		// select * from TupleDeleted
		if(perf==0){out.println("EP_DELETED_SELECT_STAR: after inserting 2 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_DELETED_SELECT_STAR);
		lireResultSet(rs, out);

		// SELECT * from TupeDeleted where TSSPT > ?	
		if(perf==0){out.println("EP_DELETED_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_DELETED_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(3, ps), out);	
		// END TEST 8
		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	  }
}
