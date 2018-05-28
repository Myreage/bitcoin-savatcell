package test.jdbc.INRIA_6;

import test.jdbc.Macro;

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

    // insert failure point: NOR_WRITE_ARRAY, table CONCEPT
    Insert_Failure_Point(Macro.FUNC_NOR_WRITE_ARRAY_FAIL, (byte)3, Macro.STRUCT_TYPE_TABLE);

    out.println("\nInserting one tuple into USERDMSP.");
    java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
    Tools_schemaIndexInfo.Test_USERDMSP_NOAC_AC_INSERT(0, 0, 0, "all", "all", "0", "1 rue du general Leclerc", "Paris", "75000", "0123456789", "0123456789", 4, ps);
    out.println("Tuple inserted.");
    
    try{
    out.println("\nInserting one tuple into CONCEPT.");
    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_INSERT_WITH_IDS);
    Tools_schemaIndexInfo.Test_CONCEPT_NOAC_AC_INSERT_WITH_IDS((36), 0, 0, 36, 0, "effectuePar", 2, ps);
    out.println("Tuple inserted.");
    } catch(Exception e){
    	//Rollback_Transaction();
    	out.println("\nHardware Failure: NOR_WRITE_ARRAY_FAIL!");
    	out.println("Restart DBMS and recover database.");
    }
    java.sql.Statement stmt = db.createStatement();
    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);

    // remove failure point
    Remove_Failure_Point();

    out.println("\nInserting one tuple into USERDMSP.");
    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
    Tools_schemaIndexInfo.Test_USERDMSP_NOAC_AC_INSERT(0, 0, 0, "all", "all", "0", "1 rue du general Leclerc", "Paris", "75000", "0123456789", "0123456789", 4, ps);
    out.println("Tuple inserted.");
    out.println("\nInserting one tuple into CONCEPT.");
    ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_INSERT_WITH_IDS);
    Tools_schemaIndexInfo.Test_CONCEPT_NOAC_AC_INSERT_WITH_IDS((36), 0, 0, 36, 0, "effectuePar", 2, ps);
    out.println("Tuple inserted.");
    // commit
    Save_DBMS_on_disk();

    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);

    // insert failure point: NAND_WRITE_FAIL, table CONCEPT
    Insert_Failure_Point(Macro.FUNC_NAND_WRITE_FAIL, (byte)3, Macro.STRUCT_TYPE_TABLE);

    // insert data
    try{
    	INSERT_SynchroScenarioDemo();
    }catch(Exception e){
    	//Rollback_Transaction();
    	out.println("\nHardware Failure: NAND_WRITE_FAIL!");
    	out.println("Restart DBMS and recover database.");
    }

    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);
    
    
    // remove failure point
    Remove_Failure_Point();
    
    // insert data
    INSERT_SynchroScenarioDemo();
    // commit
    Save_DBMS_on_disk();
 
    // query
    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);

    // insert failure point: NOR_ERASE_FAIL, table CONCEPT
    Insert_Failure_Point(Macro.FUNC_NOR_ERASE_FAIL, (byte)3, Macro.STRUCT_TYPE_TABLE);

    // insert data
    try{
    	INSERT_SynchroScenarioDemo();
    }catch(Exception e){
    	//Rollback_Transaction();
    	out.println("\nHardware Failure: NOR_ERASE_FAIL!");
    	out.println("Restart DBMS and recover database.");
    }
    
    // query
    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);
    
    // remove failure point
    Remove_Failure_Point();
    
    // insert data
    INSERT_SynchroScenarioDemo();
 
    // query
    out.println("\nSELECT * FROM USERDMSP :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);
    out.println("\nSELECT * FROM CONCEPT :");
    lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.CONCEPT_NOAC_SELECT_STAR), out);


    Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }

  private void INSERT_SynchroScenarioDemo() throws Exception {
    out.println("\nInserting data by synchronization");
    
    DataGeneratorWithIds dgwi = new DataGeneratorWithIds(out);
	dgwi.INSERT_Data_Generated(
			6, // nbUser
			3, // nbRole
			4, // nbHab
			0, // nbForm
			0, // nbMatriceDMSP
			0, // nbEpisode
			0, // nbEvent
			5, // nbConcept
			0, // nbComment
			0,// nbInfo
			db,
			1); // MatPatGenerated

    out.println("Data inserted.");
  }

}
