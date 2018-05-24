package test.jdbc.INRIA_5;

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

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
			4, // nbHab
			9, // nbForm
			0, // nbMatriceDMSP
			1, // nbEpisode
			40, // nbEvent
			28, // nbConcept
			10, // nbComment
			100,// nbInfo
			db,
			1); // MatPatGenerated
	Save_DBMS_on_disk();

    java.sql.Statement stmt = db.createStatement();
    out.println("\nSELECT * FROM INFO :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

    java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_UPDATE_Valchar_BY_ID);
    
    String newValue = "new value";
    out.println("update Info set ValChar = " + newValue + " where Info.IdGlobal = " + "160");
    int res = Tools_schemaIndexInfo.Test_INFO_NOAC_UPDATE_Valchar_BY_ID(160, newValue, ps);
    out.println("Number of updated tuples : " + res);
    out.println("\nSELECT * FROM INFO :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

    newValue = "new value 2";
    out.println("update Info set ValChar = " + newValue + " where Info.IdGlobal = " + "126");    
    res = Tools_schemaIndexInfo.Test_INFO_NOAC_UPDATE_Valchar_BY_ID(126, newValue, ps);
    out.println("Number of updated tuples : " + res);
    out.println("\nSELECT * FROM INFO :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);
    
    newValue = "new value 2";
    out.println("update Info set ValChar = " + newValue + " where Info.IdGlobal = " + "160");
    res = Tools_schemaIndexInfo.Test_INFO_NOAC_UPDATE_Valchar_BY_ID(160, newValue, ps);
    out.println("Number of updated tuples : " + res);
    out.println("\nSELECT * FROM INFO :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

    Shutdown_DBMS();

    openConnection(dbmsHost, null);
    Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }
}
