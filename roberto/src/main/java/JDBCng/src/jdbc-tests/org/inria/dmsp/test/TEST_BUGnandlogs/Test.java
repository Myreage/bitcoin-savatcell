package org.inria.dmsp.test.TEST_BUGnandlogs;

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
	/// Utility method
	/// Prints given message on console if Tools.perf == 0
	protected void logMsg(String msg)
	{
		if (perf == 0)
		{
			out.println( msg );
		}
	}
	/// Utility method
	/// Prints given message on console if Tools.perf == 0
	protected void perfMsg(String msg)
	{
		if (perf == 1)
		{
			out.println( msg );
		}
	}
	/// Utility method
	/// Initializes database driver and opens connection without authentication
	protected void initAll(String dbmsHost) throws Exception
	{
		init();							// initialize the driver
		openConnection(dbmsHost, null);	// connect without authentication
	}
	/// Utility method
	/// Saves database on disk and performs its shutdown
	protected void closeAll() throws Exception
	{
		Save_DBMS_on_disk();	// commit
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();		// exit
	}
	
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		this.out = out;
		perf = 0;

		initAll( dbmsHost );

		// load the DB schema
		Tools_dmsp t = new Tools_dmsp( out );
		byte[] schemaDesc = t.loadSchema( "/org/inria/dmsp/schemaV4.rsc" );
		int usedId = 404;
		Install_DBMS_MetaData( schemaDesc, usedId );
		schemaDesc = null;
		
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
		QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
		QEPng.installExecutionPlans( db );

		// load the data into database
		DeltaLoader dl = new DeltaLoader( out, perf );
		InputStreamReader fr = new InputStreamReader( Test.class.getResourceAsStream("delta.csv") );
		dl.LoadDelta( fr, db );

		// pre-check
		Show_Logs( "(before Q0)" );
		
		// insert 200 comments
		logMsg ( "========== Q0 (insertion) ==========");
		int first_co = Insert_Comments( 200 );
		
		// post-check
		Show_Logs( "(after Q0)" );
		
		// pre-check
		Show_Logs( "(before Q1)" );
		
		// insert 200 comments
		logMsg ( "========== Q1 (deletion) ==========");
		Delete_Comments( first_co, 100 );
		
		// post-check
		Show_Logs( "(after Q1)" );
		
		// pre-check
		Show_Logs( "(before Q2)" );
		
		// insert 200 comments
		logMsg ( "========== Q2 (update) ==========");
		Update_Comments( first_co + 100, 100 );
		
		// post-check
		Show_Logs( "(after Q2)" );
		
		// pre-check
		Show_Logs( "(before Q3)" );
		
		// insert 200 comments
		logMsg ( "========== Q3 (delete) ==========");
		Delete_Comments( first_co + 100, 100 );
		
		// post-check
		Show_Logs( "(after Q3)" );
		
		closeAll();
	}

	
	private void Show_Logs(String msg) throws Exception
	{
		// the resultset used for all queries:
		java.sql.ResultSet rs;
		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
		
		logMsg( "EP_COMMENT_SELECT_STAR " + msg );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_TEST.EP_COMMENT_SELECT_STAR );
		lireResultSet( rs, out );
		logMsg( "EP_SYSTEM_LogDeleted " + msg );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_Debug.EP_SYSTEM_LogDeleted );
		lireResultSet( rs, out );
		logMsg( "EP_SYSTEM_UpdateLog " + msg );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_Debug.EP_SYSTEM_UpdateLog );
		lireResultSet( rs, out );
	}

	private int Insert_Comments(int nb) throws Exception
	{
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		// id of the spt (patient) and current ts_spt
		int id, ts_spt, res = 0, kres = 0;
		// the prepared statement used for parametric queries:
		java.sql.PreparedStatement ps;
		
		// set id of the spt (patient)
		id = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		
		/* INSERT INTO COMMENT */
		logMsg( "Insert " + nb + " comments..." );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.OEP_COMMENT_NOAC_INSERT, 
								  java.sql.Statement.RETURN_GENERATED_KEYS );
		c[0] = 'a'; 	
		for (int i = 0; i < nb; ++i )
		{
			if ( i % 26 == 0)
			{
				c[0] = 'a';
			}
			c[1] = c[0];
			String s = new String (c);
			ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
			res += Tools_dmsp.Test_COMMENT_INSERT( id, ts_spt, s, ps );
			// get the first id inserted
			if ( i == 0)
			{
				kres = getIdGlobalGetGeneratedKey( ps.getGeneratedKeys() );
			}
			c[0]++;
		}
		logMsg( res + " comments inserted..." );
		
		return kres;
	}
	
	
	private void Delete_Comments(int first_co, int nb) throws Exception
	{
		int res = 0;
		// the prepared statement used for parametric queries:
		java.sql.PreparedStatement ps;
		
		/* DELETE COMMENT */
		logMsg( "Delete " + nb + " comments..." );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_COMMENT_DELETE );	
		for (int i = first_co; i < first_co + nb; ++i )
		{
			res += Tools_dmsp.Test_DELETE_BY_ID( i, ps );
		}
		logMsg( res + " comments deleted..." );
	}
	
	
	private void Update_Comments(int first_co, int nb) throws Exception
	{
		int ts_spt;
		// the prepared statement used for parametric queries:
		java.sql.PreparedStatement ps;
		
		/* UPDATE COMMENT */
		logMsg( "Updating " + nb + " comments..." );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.COMMENT_AC_UPDATE_BY_ID );
		for (int i = first_co; i < first_co + nb; ++i )
		{
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_COMMENT_UPDATE );
			ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
			Tools_dmsp.Test_COMMENT_UPDATE( 404, ts_spt, 0, "comment " + i + " after update containing something", i, ps );
		}
		logMsg( nb + " comments (probably) updated..." );
	}
}
