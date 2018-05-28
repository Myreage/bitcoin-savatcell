package org.inria.dmsp.test.TEST_sync;

import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.sql.Date;

import test.jdbc.Tools;
import test.runner.ITest;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	class EventData
	{
		Date de;
		Date df;
		int f;
		EventData( String _de, String _df, int _f )
		{
			de = ( _de == null ? null : java.sql.Date.valueOf( _de ) );
			df = ( _df == null ? null : java.sql.Date.valueOf( _df ) );
			f = _f;
		}
	};
	class InfoData
	{
		String vs;
		int vn;
		Date vd;
		int p;
		int f;
		int c;
		InfoData( String _vs, int _vn, String _vd, int _p, int _f, int _c )
		{
			vs = _vs;
			vn = _vn;
			vd = ( _vd == null ? null : java.sql.Date.valueOf( _vd ) );
			p = _p;
			f = _f;
			c = _c;
		}
	};

	/////////////////// DATA //////////////////////////
	//
	private EventData[] edata =
	{	//				DateEvent		DateFin		Filtre
		new EventData(	"2012-01-19", "2012-01-30", 0 ),// DateFin (2012-02-27 => 2012-01-30)
		new EventData(	"2012-01-19", "2012-01-31", 0 ),// DateFin (2012-02-27 => 2012-01-31)
	};
	private InfoData[] idata1 =
	{	//				ValChar ValNum	ValDate		 Position	Filtre	Concept
		new InfoData(	null,	-1,		"2012-01-21",	2,		3,		287 ),// ValDate (2012-01-23 => 2012-01-21)
		new InfoData( "encore",	-1,		null,			1,		3,		289 ),// ValChar ("test" => "encore")
		new InfoData(	null,	4,		null,			4,		3,		290 ),// ValNum (1 => 4)
		new InfoData(	null,	3,		null,			5,		3,		291 ),// ValNum (2 => 3)
		new InfoData(	null,	2,		null,			6,		3,		292 ),// ValNum (3 => 2)
		new InfoData(	null,	1,		null,			7,		3,		293 ) // ValNum (4 => 1)
	};
	private InfoData[] idata2 =
	{	//				ValChar ValNum	ValDate		 Position	Filtre	Concept
		new InfoData(	null,	-1,		"2012-01-24",	2,		3,		287 ),// ValDate (2012-01-23 => 2012-01-24)
		new InfoData( "changed",-1,		null,			1,		3,		289 ),// ValChar ("test" => "changed")
		new InfoData(	null,	100,	null,			4,		3,		290 ),// ValNum (1 => 100)
		new InfoData(	null,	200,	null,			5,		3,		291 ),// ValNum (2 => 200)
		new InfoData(	null,	300,	null,			6,		3,		292 ),// ValNum (3 => 300)
		new InfoData(	null,	400,	null,			7,		3,		293 ) // ValNum (4 => 400)
	};
	static final int lastAck = 9048;		// last TSSPT acknowledged by SC
	static final int Author = 73;
	//
	/////////////////// DATA //////////////////////////

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
		run_loaddelta( out, lastAck );
		closeAll( false );

		perf = 0;	// this not a performance test ==> output is produced

		initAll( dbmsHost );
		run_update();
		dump_data();
		dump_sync( lastAck );
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
	/// Load initial database
	///
	protected void run_loaddelta( PrintWriter out, int lastAck ) throws Exception
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
		((org.inria.jdbc.Connection)db).setGlobalTimestamp( lastAck + 1 );	// set TSSPT
	}

	///
	/// Run test scenario
	///
	protected void run_update() throws Exception
	{
		InfoData[] data = idata1;
		java.sql.PreparedStatement
			pse = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_EVENT_UPDATE ),
			psi = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_INFO_UPDATE );
		int idG = 0, res = 0, ts_spt, n = 0;
		do	// loop twice
		{	idG = 306193234;
			logMsg( "-- TRY TO UPDATE EVENT with idG=" + idG );
			ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
			res = Tools_dmsp.Test_EVENT_UPDATE
			(
				Author,			// Author (no changes)
				ts_spt,			// TSSPT (++)
				0,				// TSSanteos ( => 0)
				edata[ n ].de,	// DateEvent (no changes)
				edata[ n ].df,	// DateFin (2012-01-27 => 2012-01-26)
				edata[ n ].f,	// Filtre (no changes)
				idG++,			// IdGlobal
				pse
			);
			n++;
			logMsg( "--> update return: row(s) updated = " + res + "." );

			for ( InfoData d : data )
			{
				logMsg( "-- TRY TO UPDATE INFO with idG=" + idG );
				ts_spt = ((org.inria.jdbc.Connection) db).nextGlobalTimestamp();
				psi.setInt(    1, Author );	// Author (no changes)
				psi.setInt(    2, ts_spt );	// TSSPT (++)
				psi.setInt(    3, 0 );		// TSSanteos ( => 0)
				psi.setString( 4, d.vs );	// ValChar
											// ValNum
				if ( d.vn < 0 ) psi.setNull( 5, java.sql.Types.INTEGER );
				else			psi.setInt(  5, d.vn );
				psi.setDate(   6, d.vd );	// ValDate
				psi.setInt(    7, d.p );	// Position
				psi.setInt(    8, d.f );	// Filtre 
				psi.setInt(    9, d.c );	// IdConcept
				psi.setInt(   10, idG++ );	// IdGlobal 
				psi.executeUpdate();
			}
			if ( data != idata1 )
				break;
			data = idata2;
		} while ( true );
	}

	///
	/// Dump various data
	///
	protected void dump_data() throws Exception
	{
		java.sql.Statement st = db.createStatement();
		java.sql.ResultSet rs = null;

		logMsg( "EP_TEST.EP_EVENT_SELECT_STAR" );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_TEST.EP_EVENT_SELECT_STAR );
		lireResultSet( rs, out );

		logMsg( "EP_TEST.EP_INFO_SELECT_STAR" );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_TEST.EP_INFO_SELECT_STAR );
		lireResultSet( rs, out );

/*		logMsg( "EP_SYSTEM_LogDeleted" );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. org.inria.dmsp.EP_Debug.EP_Debug.EP_SYSTEM_LogDeleted );
		lireResultSet( rs, out );
		logMsg( "EP_SYSTEM_UpdateLog" );
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. org.inria.dmsp.EP_Debug.EP_Debug.EP_SYSTEM_UpdateLog );
		lireResultSet( rs, out );*/
	}
	///
	/// Dump data to be synchronized
	///
	protected void dump_sync( int lastAck ) throws Exception
	{
		java.sql.PreparedStatement ps = null;

		logMsg( "EP_Synchro.EP_EVENT_SELECT_ON_TSSPT" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_EVENT_SELECT_ON_TSSPT );
		lireResultSet( Tools_dmsp.Test_SELECT_BY_INT( lastAck, ps ), out );

		logMsg( "EP_Synchro.EP_INFO_SELECT_ON_TSSPT" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_INFO_SELECT_ON_TSSPT );
		lireResultSet( Tools_dmsp.Test_SELECT_BY_INT( lastAck, ps ), out );

		logMsg( "EP_Synchro.EP_DELETED_SELECT_ON_TSSPT" );
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_Synchro.EP_DELETED_SELECT_ON_TSSPT );
		lireResultSet( Tools_dmsp.Test_SELECT_BY_INT( lastAck, ps ), out );
	}
}
