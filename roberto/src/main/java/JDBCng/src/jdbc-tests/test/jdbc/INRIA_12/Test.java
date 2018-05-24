package test.jdbc.INRIA_12;

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {

	private int counter = 0;

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
	 	
	 	// commit
	 	Save_DBMS_on_disk();

		// insert data
		InsertDataUser(
				500, 
				"Dupont",
				"Pierre",
				"1",
				"1 rue du marechal Joffre",
				"Paris",
				"75000",
				"0987654321",
				"2222222222",
				3);

		// select * from userdmsp
		java.sql.Statement stmt = db.createStatement();
		out.println("== USERDMSP NOAC TESTS ==");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);

		// roll back
		Rollback_Transaction();

		// select * from userdmsp
		out.println("== USERDMSP NOAC TESTS ==");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);

		// insert the same date again
		InsertDataUser(
				500, 
				"Martin",
				"Roger",
				"0",
				"1 rue du general Leclerc",
				"Grenoble",
				"38000",
				"0123456789",
				"1111111111",
				0);

		// commit
		Save_DBMS_on_disk();

		// select * from userdmsp
		out.println("== USERDMSP NOAC TESTS ==");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_SELECT_STAR), out);

		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	private void InsertDataUser(
			int numberOfUser, 
			String lastNames,
			String firstNames,
			String MF,
			String adress,
			String towns,
			String zipCodes,
			String tel1,
			String tel2,
			int userTypes
			) throws Exception{
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.USERDMSP_NOAC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		int indexUser = numberOfUser + counter;
		
		for(int i=counter; i<indexUser ;i++){
			Tools_schemaIndexInfo.Test_USERDMSP_NOAC_AC_INSERT(
					0,0,0,
					lastNames,
					firstNames,
					MF,
					adress,
					towns,
					zipCodes,
					tel1,
					tel2,
					userTypes, 
					ps);
		}
		
		counter = counter + numberOfUser;
	}
	
}

