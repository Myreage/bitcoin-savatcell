package org.inria.dmsp.test.TEST_rollback;

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;
import java.util.Calendar;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

import test.runner.ITest;

/*
 * This test takes initial delta with empty INFO table and does the following:
 * 
 * - fills INFO table with data: 2 complete NAND blocks + 1 sector
 * - commit it
 * - dump its contents
 * - insert one more tuple in INFO table
 * - rollback it
 * - tries to dump its contents
 *
 * Expected behavior:
 * - test shouldn't crash database and exits normally
 * - fisrt and second dumps must be the same
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

		// initialize the driver:
		init();
		// connect without authentication
		openConnection( dbmsHost, null );

		// load the DB schema
		String schema = "/org/inria/dmsp/schemaV4.rsc";
		byte[] schemaDesc = t.loadSchema( schema );
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

		DeltaLoader dl = new DeltaLoader( out, perf ); 	// instantiate delta loader
		InputStreamReader fr =	// open the delta file
			new InputStreamReader( Test.class.getResourceAsStream("delta.csv") );
		dl.LoadDelta( fr, db ); 	// load the data into database
/**/
		if ( perf == 0 ) { out.println( "========== BEGIN INSERT INFOs ======== " ); }
		Insert_INFO(
			2560+5,					// 2565 tuples to insert
			1115, 1125-1115+1,		// IdEvent in [ 1115 ... 1125 ]
			3, 1 );					// IdComment in [ 3 ... 3 ]
//			1272, 1281-1272+1 );	// IdComment in [ 1272 ... 1281 ]
		if ( perf == 0 ) { out.println( "========== END INSERT INFOs ======== " ); }
/**/
		db.commit();

		if ( perf == 0 ) { out.println( "========== ALL INFOS BEGIN BEFORE ROLLBACK======== " ); }
		Select_INFO();
		if ( perf == 0 ) { out.println( "========== ALL INFOS END BEFORE ROLLBACK======== " ); }

/**/
		Insert_INFO(
			1,						// 1 tuple to insert
			1115, 1125-1115+1,		// IdEvent in [ 1115 ... 1125 ]
			3, 1 );					// IdComment in [ 3 ... 3 ]

		try
		{
			db.rollback();
		}
		catch ( Exception e )
		{
			out.println( "Exception during rollback!" );
		}

		if ( perf == 0 ) { out.println( "========== ALL INFOS BEGIN AFTER ROLLBACK======== " ); }
		Select_INFO();
		if ( perf == 0 ) { out.println( "========== ALL INFOS END AFTER ROLLBACK======== " ); }
/**/
		Save_DBMS_on_disk();	/* Commit and clean exit */
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	/*
	 *  INSERT INTO INFO
	 */
	private void Insert_INFO(
			int nb_in,		// number of tuples to insert
			int first_ev,	// first id of event
			int nb_ev,		// number of event id to iterate
			int first_co,	// first id of comment
			int nb_co		// number of comment id to iterate
			) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		java.sql.PreparedStatement ps ;
		int i, t1, t2, t3;

		// to build the value of the strings to insert:
		byte c[] = new byte[] { 'a', 'a' };

		// Insert into info
		//   (Author, TSSPT, TSSanteos, IdEvent, IdComment, ValChar, ValNum, ValDate,Position, Filtre, IdConcept)
		//  values (?, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?)
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs. EP_UI.OEP_INFO_AC_INSERT );
		// init idglobals of event and comment:
		t1 = first_ev; // first event
		t3 = 0; // current value of field info.position
		for ( i = 1; i <= nb_in; ++i )
		{
			cal.set( Calendar.YEAR, 2000 + i % 2 );
			cal.set( Calendar.MONTH, i % 12 + 1 );
			cal.set( Calendar.DAY_OF_MONTH, i % 20 + 1 );

			t2 = first_co + i % nb_co;

			c[ 0 ] = c[ 1 ];
			if ( i % 26 == 0 )
				c[ 1 ] = 'a';
			else
				c[ 1 ]++;

			Tools_dmsp.Test_INFO_INSERT(
				0,				// Author
				0,				// TSSPT
				t1,				// IdEvent
				t2,				// IdComment
				new String( c ),// ValChar
				i,				// ValNum
				new java.sql.Date(cal.getTime().getTime()), // ValDate
				t3++,			// Position
				i % 2,			// Filtre
				i,				// IdConcept
				ps );
			t1 = first_ev + i % nb_ev; // increment idglobal of event
		}
	}

	private void Select_INFO() throws Exception
	{
		java.sql.Statement st = db.createStatement();

		if ( perf == 0 ) { out.println( "EP_INFO_SELECT_STAR" ); }
		java.sql.ResultSet rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs. EP_TEST.EP_INFO_SELECT_STAR );
		lireResultSet( rs, out );
/*
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
*/
	}
}
