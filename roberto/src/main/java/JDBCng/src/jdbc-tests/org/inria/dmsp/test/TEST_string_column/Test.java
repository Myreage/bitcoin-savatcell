package org.inria.dmsp.test.TEST_string_column;

import java.io.PrintWriter;		// to produce the output of the test
import java.sql.SQLException;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.Tools_dmsp;

import test.jdbc.Tools;
import test.runner.ITest;

/*
 * This test inserts maximum number of characters in the table column
 * and checks if it could damage data in the database
 */
public class Test extends Tools implements ITest
{
	@Override
	public void run( PrintWriter out, String dbmsHost ) throws Exception
	{
		// set output stream and instantiate the tools:
		this.out = out;
		Tools_dmsp t = new Tools_dmsp( out );
		// this not a performance test ==> output is produced:
		perf = 0;

		init();	// initialize the driver and connect without authentication
		openConnection( dbmsHost, null );

		// load default DB schema
		byte[] schemaDesc = t.loadSchema( "/org/inria/dmsp/schemaV4.rsc" );
		Install_DBMS_MetaData( schemaDesc, 404 /*usedId*/ );
		schemaDesc = null;
		
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
		QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
		QEPng.installExecutionPlans( db );

		// perform tests
		StringBuilder s = new StringBuilder();
		s = s.append( "1234567890" ).append( "1234567890" );
		s = s.append( "1234567890" ).append( "1234567890" );
		s = s.append( "1234567890" ).append( "12" ); // i.e. 52 characters
		// column accepts only 50 characters
		// (see schema.rsc, column size is 52 = 50 characters + 2 bytes size)
		String st = new String( s );
		logMsg( "set " + st + "\n" );
		insert_test( st );
		select_test();

		s.delete( 15, 25 );
		s.append( "qwertyuiop" );	// i.e. replace 10 characters
		st = new String( s );
		logMsg( "upd " + st + "\n" );
		update_test( st );
		select_test();

		Save_DBMS_on_disk();	/* Commit and clean exit */
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	private void insert_test( String value ) throws SQLException
	{
		logMsg( "insert long value..." );
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_string_column.EP_EPISODE_INSERT );
		ps.setInt( 1, 10 );			// IdGlobal
		ps.setInt( 2, 11 );			// Author
		ps.setInt( 3, 22 );			// TSSPT
		ps.setInt( 4, 33 );			// TSSanteos
		ps.setString( 5, value );	// Nom
		int res = ps.executeUpdate();

		logMsg( "OK " + res + "\n" );

		// insert second tuple just after the first one
		ps.setInt( 1, 11 );			// IdGlobal
		ps.setInt( 2, 12 );			// Author
		ps.setInt( 3, 23 );			// TSSPT
		ps.setInt( 4, 34 );			// TSSanteos
		ps.setString( 5, "test" );	// Nom
		res = ps.executeUpdate();

		logMsg( "OK " + res + "\n" );
		out.flush();
	}
	private void select_test() throws Exception
	{
		logMsg( "select long value...\n" );

		java.sql.ResultSet rs;
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_string_column.EP_EPISODE_SELECT );
		rs = Tools_dmsp.Test_SELECT_BY_INT( 10, ps );
		lireResultSet( rs, out );

		logMsg( "select long value OK\n" );
		out.flush();
	}
	private void update_test( String value ) throws Exception
	{
		logMsg( "update long value..." );

		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_string_column.EP_EPISODE_UPDATE );
		ps.setInt( 1, 111 );		// Author
		ps.setInt( 2, 222 );		// TSSPT
		ps.setInt( 3, 333 );		// TSSanteos
		ps.setString( 4, value );	// Nom
		ps.setInt( 5, 10 );			// IdGlobal
		int res = ps.executeUpdate();

		logMsg( "OK " + res + "\n" );
		out.flush();
	}

	private void logMsg( String s )
	{
		if ( perf == 0 )
			out.print( s );
	}
}
