package org.inria.dmsp.test.TEST_BUGplanacc;
/*
 * Reference to this bug in JIRA:
 * 
 * Synchro des tarifs du plan d'aide
 * ---------------------------------

                 Key: SAN-DMSP-40
                 URL: https://jira.atosworldline.com/jira/browse/SAN-DMSP-40
             Project: DMSP
          Issue Type: Bug
      Security Level: Internal
          Components: SPT
            Reporter: CG78 (EXT)
            Assignee: INRIA (EXT)
            Priority: Major

 * Les tarifs du plan d'aide mis à jour sur le SC ne se synchronisent pas vers les SPT
 */

// IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////
// TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////

import test.runner.ITest;		// super class
import test.jdbc.Tools;			// tools specified for any DB schema



//import java.io.FileReader;
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

		perf = 1;	// set it to !=0 to avoid logging in DeltaLoader.loadDelta()

		//========= Load initial database dump
		init();							// initialize the driver
		openConnection(dbmsHost, null);	// connect without authentication
		run_loaddelta( out );
		Save_DBMS_on_disk();	// commit
		Shutdown_DBMS();		// exit

		perf = 0;	// this not a performance test ==> output is produced

		//========= Dump its content
//		openConnection(dbmsHost, new String [] {"1026","1018"}); // user 1026(SAGE-CH0162), role 1018(medecin)
		openConnection(dbmsHost, new String [] {"1024","1019"}); // user 1024(Lefebvre), role 1019(referentSocial)
		select_star();	// select * from *
		run_select( dbmsHost );
		Save_DBMS_on_disk();	// commit
		Shutdown_DBMS();		// exit

		//========= Replay traces to update some records
//		run_traces( dbmsHost );
		
		//========= Update an info
		openConnection(dbmsHost, null);	// connect without authentication
		run_update(dbmsHost);
		Save_DBMS_on_disk();	// commit
		Shutdown_DBMS();		// exit

		openConnection(dbmsHost, new String [] {"1024","1019"}); // user 1024(Lefebvre), role 1019(referentSocial)
		//========= Dump database content again
		select_star();	// select * from *

		//========= Visualize update log
		run_select_ul( dbmsHost );

		//========= Try to run select query (it has to return some records)
		run_select( dbmsHost );

		Save_DBMS_on_disk();	// commit
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();		// exit
	}

	protected void run_loaddelta(PrintWriter out) throws Exception
	{
		Tools_dmsp t = new Tools_dmsp(out);
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
		dl.LoadDelta(fr, db);	// load the data into database
	}

	///
	/// load the traces and replay the process
	///
/*	protected void run_traces(String dbmsHost) throws Exception
	{
		// open the traces file
//		InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("traces.txt"));
		FileReader fr = new FileReader("D:/dev/JDBC/src/jdbc-tests/org/inria/dmsp/test/TEST_BUGplanacc/traces.txt");
		// replay traces
		TracesReplayer tr = new TracesReplayer();
		tr.replayTraces(fr, dbmsHost);
	}
*/	///
	/// Run test scenario
	///
	protected void run_select(String dbmsHost) throws Exception
	{
		if (perf == 0) { out.println("EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT"); }
		java.sql.PreparedStatement ps =
			((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
		lireResultSet( Tools_dmsp.Test_SELECT_BY_INT(1115, ps), out );
	}
	///
	/// Visualize update log
	///
	protected void run_select_ul(String dbmsHost) throws Exception
	{
		if (perf == 0) { out.println("EP_SYSTEM_UpdateLog"); }
		java.sql.PreparedStatement ps =
			((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Debug.EP_SYSTEM_UpdateLog);
		lireResultSet( Tools_dmsp.Test_SELECT(ps), out );
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
	protected void run_update(String dbmsHost) throws Exception
	{
		int nb_tuples = 0;
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_UPDATE);
		ps.setInt(1, 0);		// Author 37
		ps.setInt(2, 0);	// TSSPT 38
		ps.setInt(3, 1296450);	// TSSanteos 39
		ps.setString(4, null);	// ValChar 42
		ps.setInt(5, 1384);		// ValNum 43
		ps.setDate(6, null);	// ValDate 44
		ps.setInt(7, 15);	// Position 45
		ps.setInt(8, 0);		// Filtre 46
		ps.setInt(9, 317);		// IdConcept 92
		ps.setInt(10, 1130);	// IdGlobal 36
		nb_tuples = ps.executeUpdate();
		((org.inria.jdbc.Connection) db).commit();
/*
<T i="1130" a="0" p="0" s="1296450">
  <A>1115</A> 
  <A>3</A> 
  <A>[]</A> 
  <A>1384</A> 
  <A>[]</A> 
  <A>15</A> 
  <A>0</A> 
  <A>317</A> 
</T>

36	IdGlobal	= 1130
37	Author		= 0
38	TSSPT		= 0
39	TSSanteos	= 1296450
40	IdEvent		= 1115
41	IdComment	= 3
42	ValChar		= [NULL]
43	ValNum		= 1384
44	ValDate		= [NULL]
45	Position	= 15
46	Filtre		= 0
92	IdConcept	= 317
*/
		if(perf==0){out.println("--> " + nb_tuples + " row(s) updated...");}
	}
}
