package org.inria.dmsp.test.TEST_EP_Synchro_BUG_OPauto;

/////////////////////////////////////////////////////
// IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

/////////////////////////////////////////////////////////////////////////
// TEST CODE WHICH SHOULD REMAIN THE SAME WHATEVER THE IMPORTED PLANS  //
/////////////////////////////////////////////////////////////////////////



import test.runner.ITest;		// super class
import test.jdbc.Tools;			// tools specified for any DB schema

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;
import java.sql.SQLException;

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

		//--
		// EP_COMMENT_SELECT_BY_ID : single next on empty result 
		//--
		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID (non existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		// get the resultset:
		rs = Tools_dmsp.Test_SELECT_BY_INT(10000, ps);
		//Get the Metadata of the ResultSet
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		// work on 1 line of the ResultSet : first line is empty
		if(rs.next()) {
			// one result produced:
			out.print("Result produced - first line is:");
			for (int i = 0 ;i < rsmd.getColumnCount() ;i++) {
				if (rs.wasNull()) out.print("\tNULL");
				else {
					switch (rsmd.getColumnType(i + 1)) {
					case java.sql.Types.CHAR :
						String s = rs.getString(i + 1);
						out.print("\t" + s);
						break;
					case java.sql.Types.DATE :
						java.sql.Date d = rs.getDate(i + 1);
						out.print("\t" + d);
						break;
					case java.sql.Types.INTEGER :
						int k = rs.getInt(i + 1);
						out.print("\t" + k);
						break;
					case java.sql.Types.BINARY :
						byte[] b = rs.getBytes(i + 1);
						out.print("\t0x");
						for (int j = 0;j < b.length; j++) {
							out.print(byteTo02x(b[j]));
						}
						break;
					default :
					throw new SQLException("ERROR : Type unknown in MetaData : column = "
							+ i + 1 + ", type = " + rsmd.getColumnType(i + 1) + ".");
					}
				}
			}
		}
		// close the resultset (before EOF):
		rs.close();

		//--
		// EP_COMMENT_SELECT_BY_ID
		//--
		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("\nEP_COMMENT_SELECT_BY_ID (non existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(10000, ps), out);

		//--
		// EP_COMMENT_SELECT_BY_ID : single next on empty result 
		//--
		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		// get the resultset:
		rs = Tools_dmsp.Test_SELECT_BY_INT(1204, ps);
		// work on 1 line of the ResultSet : first line is empty
		if(rs.next()) {
			// one result produced:
			out.print("Result produced - first line is:");
			for (int i = 0 ;i < rsmd.getColumnCount() ;i++) {
				if (rs.wasNull()) out.print("\tNULL");
				else {
					switch (rsmd.getColumnType(i + 1)) {
					case java.sql.Types.CHAR :
						String s = rs.getString(i + 1);
						out.print("\t" + s);
						break;
					case java.sql.Types.DATE :
						java.sql.Date d = rs.getDate(i + 1);
						out.print("\t" + d);
						break;
					case java.sql.Types.INTEGER :
						int k = rs.getInt(i + 1);
						out.print("\t" + k);
						break;
					case java.sql.Types.BINARY :
						byte[] b = rs.getBytes(i + 1);
						out.print("\t0x");
						for (int j = 0;j < b.length; j++) {
							out.print(byteTo02x(b[j]));
						}
						break;
					default :
					throw new SQLException("ERROR : Type unknown in MetaData : column = "
							+ i + 1 + ", type = " + rsmd.getColumnType(i + 1) + ".");
					}
				}
			}
		}
		// close the resultset (before EOF):
		rs.close();

		//--
		// EP_COMMENT_SELECT_BY_ID
		//--
		// select * from comment where IdGlobal = ?
		if(perf==0){out.println("\nEP_COMMENT_SELECT_BY_ID (existing)");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1204, ps), out);

		// testing successive close:
		rs.close();		
		rs.close();

		// Commit and clean exit
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
