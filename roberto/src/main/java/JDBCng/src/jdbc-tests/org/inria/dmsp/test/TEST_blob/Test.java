package org.inria.dmsp.test.TEST_blob;

import java.io.InputStream;
import java.io.PrintWriter;		// to produce the output of the test
import java.sql.SQLException;

import org.inria.jdbc.Blob;
import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.Tools_dmsp;

import test.jdbc.Tools;
import test.runner.ITest;

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

		// initialize the driver:
		init();
		// connect without authentication
		openConnection( dbmsHost, null );

		// load the DB schema
		byte[] schemaDesc = t.loadSchema( "/org/inria/dmsp/blob_schemaV4.rsc" );
		Install_DBMS_MetaData( schemaDesc, 404 /*usedId*/ );
		schemaDesc = null;
		
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
				org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
				org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
				test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
		QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
		QEPng.installExecutionPlans( db );

		// we create duplicate streams because they have to be re-open
		InputStream is1 = Test.class.getResourceAsStream("beastie.gif"),
					is2 = Test.class.getResourceAsStream("daemon.jpg"),
					isA = Test.class.getResourceAsStream("beastie.gif"),
					isB = Test.class.getResourceAsStream("beastie.gif");

		// perform tests
		simple_test();

		complex_test( isA, isB );

		insert_test( is1 );
		select_test();

		update_test( is2 );
		select_test();

		delete_test();
		select_test();

		Save_DBMS_on_disk();	/* Commit and clean exit */
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	private void simple_test() throws SQLException
	{
		logMsg( "BLOB create..." );
		Blob blb = new Blob( ((org.inria.jdbc.Connection)db).mDB );
		logMsg( "OK\n" );

		printBlobId( blb );

		logMsg( "BLOB write..." );
		byte[] b1 = new byte[] { (byte)0x1, (byte)0x3, (byte)0x2, (byte)0x7 };
		blb.setBytes( 1, b1 );	// first byte starts from 1
		logMsg( "OK\n" );

		printBlobId( blb );

		logMsg( "BLOB read..." );
		byte[] b2 = blb.getBytes( 1, 4 );	// first byte starts from 1
		logMsg( "OK\n" );

		if ( java.util.Arrays.equals( b1, b2 ) )
			logMsg( "BLOB read OK\n" );
		else
			logMsg( "BLOB read KO\n" );

		printBlobId( blb );

		logMsg( "BLOB free..." );
		blb.free();
		logMsg( "OK\n" );

		printBlobId( blb );
		b1 = null;
		b2 = null;
	}
	private void complex_test( InputStream isA, InputStream isB ) throws Exception
	{
		logMsg( "BLOB create/load..." );
		Blob blb = read_blob_file( isA );
		logMsg( "OK\n" );

		printBlobId( blb );

		logMsg( "BLOB read/compare..." );
		byte[] b1;
		byte[] b2 = new byte[ 32 ];
		int pos = 1,	// first byte starts from 1
			len = isB.read( b2, 0, 32 );
		boolean ok = true;
		while ( pos <= blb.getId().getSize() && len > 0 && ok )
		{	// get a chunk of BLOB data by 32 bytes at once
			b1 = blb.getBytes( pos, 32 );
			ok = ok || java.util.Arrays.equals( b1, b2 );
			pos += b1.length;
			len = isB.read( b2, 0, 32 );
		}
		isB.close();
		if ( ok )
			logMsg( "BLOB read OK\n" );
		else
			logMsg( "BLOB read KO\n" );

		logMsg( "BLOB free..." );
		blb.free();
		logMsg( "OK\n" );

		printBlobId( blb );
		b1 = null;
		b2 = null;
	}
	private void insert_test( InputStream is ) throws Exception
	{
		logMsg( "insert BLOB..." );
		Blob blb = read_blob_file( is );

		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_BLOB.EP_EPISODE_INSERT );
		ps.setInt( 1, 10 );			// IdGlobal
		ps.setInt( 2, 11 );			// Author
		ps.setInt( 3, 22 );			// TSSPT
		ps.setInt( 4, 33 );			// TSSanteos
		ps.setString( 5, "test" );	// Nom
		ps.setBlob( 6, blb );		// Image
		int res = ps.executeUpdate();

		logMsg( "OK " + res + "\n" );
		out.flush();
	}
	private void select_test() throws Exception
	{
		logMsg( "select BLOB...\n" );

		java.sql.ResultSet rs;
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_BLOB.EP_EPISODE_SELECT_BY_ID );
		rs = Tools_dmsp.Test_SELECT_BY_INT( 10, ps );
		lireResultSet( rs, out );

		logMsg( "select BLOB OK\n" );
		out.flush();
	}
	private void update_test( InputStream is ) throws Exception
	{
		logMsg( "update BLOB..." );

		Blob blb = read_blob_file( is );

		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_BLOB.EP_EPISODE_UPDATE );
		ps.setInt( 1, 111 );		// Author
		ps.setInt( 2, 222 );		// TSSPT
		ps.setInt( 3, 333 );		// TSSanteos
		ps.setString( 4, "test1" );	// Nom
		ps.setBlob( 5, blb );		// Image
		ps.setInt( 6, 10 );			// IdGlobal
		int res = ps.executeUpdate();

		logMsg( "OK " + res + "\n" );
		out.flush();
	}
	private void delete_test() throws Exception
	{
		logMsg( "delete BLOB..." );

		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( DMSP_QEP_IDs.EP_BLOB.EP_EPISODE_DELETE );
		int res = Tools_dmsp.Test_DELETE_BY_ID( 10, ps );

		logMsg( "OK " + res + "\n" );
		out.flush();
	}

	private Blob read_blob_file( InputStream is ) throws Exception
	{
		Blob blb = new Blob( ((org.inria.jdbc.Connection)db).mDB );
		byte[] b = new byte[ 32 ]; // read buffer
		int cnt = is.read( b );
		long pos = 1;
		while ( cnt > 0 )
		{
			blb.setBytes( pos, b, 0, cnt );
			pos += cnt;
			cnt = is.read( b );
		}
		is.close();
		b = null;
		return blb;
	}

	private void printBlobId( Blob blb )
	{
		Blob.BlobId blb_id = blb.getId();
		if ( perf == 0 )
		{
			out.print( "BLOB id(h,s,hash) : " + blb_id.getHead() + ", " + blb_id.getSize() + ", 0x" );
			byte[] hash = blb_id.getHash();
			boolean has0 = false;
			for ( byte bb : hash )
			{
				out.print( Tools.byteTo02x( bb ) );
				has0 = has0 || ( bb == 0x0 );
			}
			if ( !has0 )
				out.println( " [" + new String( hash ) + "]" );
			else
				out.println( " []" );
			out.flush();
		}
	}

	private void logMsg( String s )
	{
		if ( perf == 0 )
			out.print( s );
	}
}
