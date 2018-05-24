package test.jdbc.INRIA_24_DBStatus;

/**
 * Tests DBStatus in the token.
 * Should be run in debugging mode to watch the NOR memory of the token.
 *
 * @author Quentin Lefebvre
 * @date 2015/06/17
 */

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DataGenerator_2;
import org.inria.dmsp.tools.Tools_dmsp;

import test.jdbc.Tools;
import test.runner.ITest;

public class Test extends Tools implements ITest {

  @Override
  public void run(PrintWriter out, String dbmsHost) throws Exception {
	this.out = out;
	perf = 0;	// for traces (test traces ==> perf)

	Tools_dmsp t = new Tools_dmsp(out);
    init();
    openConnection(dbmsHost, null);
    Desinstall_DBMS_MetaData();
    // At this point, 2 DbStatus written
    
	String schema = "/org/inria/dmsp/schemaV4.rsc";
	byte[] schemaDesc = t.loadSchema(schema);
	int usedId = 404;
	Install_DBMS_MetaData(schemaDesc, usedId);
	// At this point, 3 DbStatus written
    
	// load and install QEPs
	Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
			org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
			org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
			test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
	QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
	QEPng.installExecutionPlans( db );
	db.commit();
	// At this point, 5 DbStatus written
    
    DataGenerator_2 dg = new DataGenerator_2(out);
	dg.perf = 1;
    // Call the data generator as many times as necessary to fill the first bank of NOR used by DbStatus
	for( int i = 0 ; i < ( (16384 / 2) - 6 ) / 2 ; i++ )
	{
		dg.PDS_INSERT_Data_Generated(
				0 /*user*/, 0 /*role*/, 0 /*hab*/, 1 /*form*/, 0 /*episode*/,
				0 /*event*/, 0 /*comment*/, 0 /*info*/, db); 
		db.commit();
	}
	dg.PDS_INSERT_Data_Generated(
			0 /*user*/, 0 /*role*/, 0 /*hab*/, 1 /*form*/, 0 /*episode*/,
			0 /*event*/, 0 /*comment*/, 0 /*info*/, db); 
	// At this point, 8192 DbStatus written in bank 1

	out.println("========== FIRST BANK FILLED (1st 16kB for DbStatus) ======== ");
	out.println("========== Check last bytes written at 0x08007FFE in NOR ======== ");
	
	db.commit();
	// At this point, 1 DbStatus written in bank 2

	out.println("========== FIRST BANK ERASED (1st 16kB for DbStatus) ======== ");
	out.println("========== Check first bytes at 0x08004000 in NOR ======== ");
	out.println("========== SECOND BANK WRITTEN (2nd 16kB for DbStatus) ======== ");
	out.println("========== Check first bytes at 0x08008000 in NOR ======== ");

    // Call the data generator as many times as necessary to fill the first bank of NOR used by DbStatus
	for( int i = 0 ; i < ( (16384 / 2) - 2 ) / 2 ; i++ )
	{
		dg.PDS_INSERT_Data_Generated(
				0 /*user*/, 0 /*role*/, 0 /*hab*/, 1 /*form*/, 0 /*episode*/,
				0 /*event*/, 0 /*comment*/, 0 /*info*/, db); 
		db.commit();
	}
	dg.PDS_INSERT_Data_Generated(
			0 /*user*/, 0 /*role*/, 0 /*hab*/, 1 /*form*/, 0 /*episode*/,
			0 /*event*/, 0 /*comment*/, 0 /*info*/, db); 
	// At this point, 8192 DbStatus written in bank 2

	out.println("========== SECOND BANK FILLED (2nd 16kB for DbStatus) ======== ");
	out.println("========== Check last bytes written at 0x0800BFFE in NOR ======== ");

	db.commit();
	// At this point, 1 DbStatus written in bank 1

	out.println("========== SECOND BANK ERASED (2nd 16kB for DbStatus) ======== ");
	out.println("========== Check first bytes at 0x08008000 in NOR ======== ");
	out.println("========== FIRST BANK WRITTEN (1st 16kB for DbStatus) ======== ");
	out.println("========== Check first bytes at 0x08004000 in NOR ======== ");

	Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }
}

