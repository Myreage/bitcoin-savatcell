package org.inria.dmsp.test.TEST_checktimestamp;

import java.io.PrintWriter;
import java.io.InputStreamReader;

import test.jdbc.Tools;
import test.runner.ITest;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	///
	/// FYI: This test requires restart of database "from scratch" -
	/// it should be empty for successful run
	///
	@Override
	public void run( PrintWriter out, String dbmsHost ) throws Exception
	{
		// set output stream and instantiate the tools:
		this.out = out;

		perf = 1;	// set it to !=0 to avoid logging in DeltaLoader.loadDelta()

		// Tests are split into following suites:
		initAll( dbmsHost );
		run_loaddelta( out );
		closeAll( false );

		perf = 0;	// this not a performance test ==> output is produced

		initAll( dbmsHost );
		run_update( dbmsHost );
		closeAll( false );

		initAll( dbmsHost );
		run_delete();
		closeAll( false );

		initAll( dbmsHost );
		run_insert( );
		closeAll( true );	// ends here
	}

	/// Utility method
	/// Prints given message on console if Tools.perf == 0
	protected void logMsg( String msg )
	{
		if ( perf == 0 )
			out.println( msg );
	}
	/// Utility method
	/// Initializes database driver and opens connection without authentication
	protected void initAll( String dbmsHost ) throws Exception
	{
		init();								// initialize the driver
		openConnection( dbmsHost, null );	// connect without authentication
	}
	/// Utility method
	/// Saves database on disk and performs its shutdown
	protected void closeAll( boolean deinstall_metadata ) throws Exception
	{
		Save_DBMS_on_disk();	// commit
		// when we want to correctly clean up the db after the tests
		if ( deinstall_metadata )
			Desinstall_DBMS_MetaData();
		Shutdown_DBMS();		// exit
	}

	///
	/// Run 1st test scenario
	///
	protected void run_loaddelta( PrintWriter out ) throws Exception
	{
		Tools_dmsp t = new Tools_dmsp( out );

		// load the DB schema
		byte[] schemaDesc = t.loadSchema( "/org/inria/dmsp/schemaV4.rsc" );
		Install_DBMS_MetaData( schemaDesc, 404 );
		schemaDesc = null;
		
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
		QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
		QEPng.installExecutionPlans( db );

		// instantiate delta loader
		DeltaLoader dl = new DeltaLoader( out, perf );
		// open the delta file
		InputStreamReader fr = new InputStreamReader( Test.class.getResourceAsStream( "delta.csv" ) );
		dl.LoadDelta( fr, db );	// load the data into database
	}

	///
	/// Run 2nd test scenario
	///
	protected void run_update( String dbmsHost ) throws Exception
	{
			// get timestamp:
		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		logMsg( "==========> getGlobalTimestamp: tsspt = " + ts_spt );
			// set timestamp:
		int gap = 1000;
		logMsg( "==========> set to tsspt : " + gap );
		((org.inria.jdbc.Connection)db).setGlobalTimestamp( gap );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1000, timestamp = 1001
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );

		// ================================ UPDATE TEST FOR EXSITENCE ========================================
		logMsg( "// ---------- TEST : UPDATE TEST FOR EXSITENCE ------------" );

			// get timestamp
		// this operation will cause timestamp increase 1, return 1001, timestamp = 1002
		logMsg( "// SHOULD FAIL\n// NON-EXISTING TUPLE IdG=1234567, only rows" );
		update( 1234567 );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1002, timestamp = 1003
		logMsg( "// SHOULD SUCCEED\n// EXISTING TUPLE IdG=1126, only rows" );
		update( 1126 );

		// ================================ UPDATE TEST FOR EXSITENCE ========================================
		logMsg( "// ---------- TEST : UPDATE TEST FOR AC ------------" );

		Save_DBMS_on_disk();	// commit and
		Shutdown_DBMS();		// clean exit

		// connect valideur/medecinValideurGIR:
		openConnection( dbmsHost, new String [] { "1020", "1030" } );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1003, timestamp = 1004
		logMsg( "// SHOULD SUCCEED\n// EXISTING TUPLE IdG=1126, only rows" );
		update( 1126 );
	}
	private void update( int idG ) throws Exception
	{
		int res, ts_spt;
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.INFO_AC_UPDATE_BY_ID );
		logMsg( "-- TRY TO UPDATE INFO with idG=" + idG );
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
		res = Tools_dmsp.Test_INFO_UPDATE( ts_spt,
				"NEW INFO1", 1010101,
				java.sql.Date.valueOf("1111-11-01"), 888, idG, ps );
		logMsg( "--> update return: row(s) updated = " + res + "." );
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_INFO_SELECT_BY_ID );
		lireResultSet( Tools_dmsp.Test_SELECT_BY_INT( idG, ps ), out );
	}

	///
	/// Run 3rd test scenario
	protected void run_delete() throws Exception
	{
			// get timestamp:
		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		logMsg( "==========> getGlobalTimestamp: tsspt = " + ts_spt );
			// set timestamp:
		int gap = 1100;
		logMsg( "==========> set to tsspt : " + gap );
		((org.inria.jdbc.Connection)db).setGlobalTimestamp( gap );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1100, timestamp = 1101
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );

		// ================================ DELETE ========================================
		logMsg( "// ---------- TEST : DELETE INFO ------------" );
			// no change to timestamp
		logMsg( "// NON-EXISTING TUPLE IdG=1234567, only rows" );
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.EP_INFO_AC_DELETE_BY_ID );
		logMsg( "-- TRY TO DELETE INFO with idG=1234567" );
		int res = Tools_dmsp.Test_DELETE_BY_ID( 1234567, ps );
		logMsg( "--> delete return: row(s) deleted = " + res + "." );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1101, timestamp = 1102
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
			// change to timestamp
		// if (Static_Ram.Variables_V.NbUpdated > 0), timestamp increase 1 : 1103
		logMsg( "// EXISTING TUPLE IdG=1126, only rows" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.EP_INFO_AC_DELETE_BY_ID );
		logMsg( "-- TRY TO DELETE INFO with idG=1126" );
		res = Tools_dmsp.Test_DELETE_BY_ID( 1126, ps );
		logMsg( "--> delete return: row(s) deleted = " + res + "." );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1103, timestamp = 1104
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
			// no change to timestamp
		logMsg( "// SEVERAL NON-EXISTING TUPLES IdEvent=3456789, only rows" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.EP_INFO_AC_DELETE_BY_EVENT );
		logMsg( "-- TRY TO DELETE INFO with idEvent=3456789" );
		res = Tools_dmsp.Test_DELETE_BY_ID( 3456789, ps );
		logMsg( "--> delete return: row(s) deleted = " + res + "." );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1104, timestamp = 1105
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
			// change to timestamp: succeed timestamp increase 1, 1106
		logMsg( "// SEVERAL EXISTING TUPLES IdEvent=1115, only rows" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.EP_INFO_AC_DELETE_BY_EVENT );
		logMsg( "-- TRY TO DELETE INFO with idEvent=1115" );
		res = Tools_dmsp.Test_DELETE_BY_ID( 1115, ps );
		logMsg( "--> delete return: row(s) deleted = " + res + "." );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1106, timestamp = 1107
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
		// ==================================================================================

			// set timestamp:
		gap = 1200;
		logMsg( "==========> set to tsspt : " + gap );
		((org.inria.jdbc.Connection)db).setGlobalTimestamp( gap );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1200, timestamp = 1201
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
	}

	///
	/// Run 4th test scenario
	protected void run_insert() throws Exception
	{
			// get timestamp:
		int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		logMsg( "==========> getGlobalTimestamp: tsspt = " + ts_spt );
			// set timestamp:
		int gap = 1300;
		logMsg( "==========> set to tsspt : " + gap );
		((org.inria.jdbc.Connection)db).setGlobalTimestamp( gap );
			// get timestamp
		// this operation will cause timestamp increase 1, return 1300, timestamp = 1301
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );

		// ================================ INSERTION FOR UI ========================================
		logMsg( "// ---------- TEST : INSERT UI ------------" );

		logMsg( "// SHOULD FAIL\n-- Insertion in table INFO, only rows" );
		// NOTE: IdGlobal is automatically generated
		// cause timestamp increase 1 : 1301 + 1 = 1302
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.OEP_INFO_AC_INSERT );
		int res = Tools_dmsp.Test_INFO_INSERT(
			0, 0, 1234567, 1234567, "char value", 10101010,
			java.sql.Date.valueOf("2000-10-20"),
			1234567, 1, 1234567, ps );
		logMsg( "--> update return: row(s) inserted = " + res + "." );
			// get timestamp:
		// return 1302, timestamp + 1 = 1303
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );

		// NOTE: IdGlobal is already assigned, so no changes to timestamp: 1009
		logMsg( "// SHOULD SUCCEED\n-- TRY TO INSERT TUPLEDELETED" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.EP_DELETED_NOAC_INSERT );
		// 1 tuple from info
		Tools_dmsp.Test_DELETED_INSERT( 1344, 4, 0, 3, 0, ps );
			// get timestamp:
		// return 1303, timestamp + 1 = 1304
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );

		// ================================ INSERTION FOR SYNCHRO ========================================
		logMsg( "// ---------- TEST : INSERT SYNCHRO ------------" );

		logMsg( "// SHOULD SUCCEED\n-- TRY TO INSERT EVENT, rows + keys" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_EVENT_INSERT );
		int i = 400;

		// NOTE: IdGlobal is already assigned
		// no change to timestamp : 1310
		String date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
		Tools_dmsp.Test_EVENT_INSERT(
				i, 0,0,0, 200+i%3/*nb_fo*/, 300+i%10, 100,
				java.sql.Date.valueOf( date ),
				java.sql.Date.valueOf( date ),
				12, ps );

			// get timestamp:
		// return 1304, timestamp + 1 = 1305
		ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
		logMsg( "==========> get tsspt = " + ts_spt );
	}
}
