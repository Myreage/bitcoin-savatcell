package test.jdbc.INRIA_2;

import java.io.PrintWriter;

import org.inria.database.QEPng;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.DataGeneratorWithIds;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {

  @Override
  public void run(PrintWriter out, String dbmsHost) throws Exception {
    this.out = out;
    Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
    init();
    openConnection(dbmsHost, null);
    String schema = "schemaV4.rsc";
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

    DataGeneratorWithIds dgwi = new DataGeneratorWithIds(out);
	dgwi.INSERT_Data_Generated(
			5, // nbUser
			3, // nbRole
			7, // nbHab
			20, // nbForm
			0, // nbMatriceDMSP
			1, // nbEpisode
			100, // nbEvent
			309, // nbConcept
			50, // nbComment
			1000,// nbInfo
			db,
			1); // MatPatGenerated
	Save_DBMS_on_disk();
	dgwi.SCE_Test_DB_NOAC(db, out);
    
    Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }

}

