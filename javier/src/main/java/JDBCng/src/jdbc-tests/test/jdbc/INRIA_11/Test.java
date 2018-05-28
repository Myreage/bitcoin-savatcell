package test.jdbc.INRIA_11;

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

		// insert data
		DataGeneratorWithIds dgwi = new DataGeneratorWithIds(out);
		dgwi.INSERT_Data_Generated(
				5, // nbUser
				3, // nbRole
				4, // nbHab
				9, // nbForm
				0, // nbMatriceDMSP
				1, // nbEpisode
				4, // nbEvent
				28, // nbConcept
				10, // nbComment
				26,// nbInfo
				db,
				1); // MatPatGenerated

		// select * from event
		out.println("Select * from event");
		java.sql.Statement stmt = db.createStatement();
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_SELECT_STAR), out);
		// select * from info
		out.println("Select * from info");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

		// commit
		Save_DBMS_on_disk();

		// insert one tuple into table EVENT
		// Add of Formulaire Reperage fragilite Dupont Pierre appartenant à noEpisode
		out.println("Inserting 1 tuples into table EVENT...");
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_INSERT_WITH_IDS);
		Tools_schemaIndexInfo.Test_EVENT_NOAC_AC_INSERT_WITH_IDS((446), 0, 0, 446, (44), (2), (50), java.sql.Date.valueOf("2008-12-26"), ps);
		out.println("Done.");

		// insert 17 tuples to table INFO
		out.println("Inserting 17 tuples into table INFO...");
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_INSERT_WITH_IDS);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((447), 0, 0, 447, (446), (36), (60), null, 1, 15, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((448), 0, 0, 448, (446), (37), (60), null, 1, 4, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((449), 0, 0, 449, (446), (40), (60), null, 1, 8, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((450), 0, 0, 450, (446), (23), (60), null, 1, 11, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((451), 0, 0, 451, (446), (36), (63), null, 1, 16, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((452), 0, 0, 452, (446), (38), (63), null, 2, 3, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((453), 0, 0, 453, (446), (39), (63), null, 2, 7, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((454), 0, 0, 454, (446), (39), (63), null, 1, 12, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((455), 0, 0, 455, (446), (23), (59), null, 1, 2, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((456), 0, 0, 456, (446), (36), (59), null, 3, 13, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((457), 0, 0, 457, (446), (23), (59), null, 20080313, 9, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((458), 0, 0, 458, (446), (36), (62), "inconnue", 0, 6, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((459), 0, 0, 459, (446), (36), (62), null, 0, 1, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((460), 0, 0, 460, (446), (38), (58), null, 0, 14, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((462), 0, 0, 462, (446), (36), (56), null, 1, 10, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((463), 0, 0, 463, (446), (23), (64), null, 20080312, 5, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((464), 0, 0, 464, (446), (40), (64), null, 0, 0, ps);
		out.println("Done.");

		// the same query
		out.println("Select * from event");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_SELECT_STAR), out);
		out.println("Select * from info");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

		// roll back
		Rollback_Transaction();

		// the same query
		out.println("Select * from event");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.EVENT_NOAC_SELECT_STAR), out);
		out.println("Select * from info");
		lireResultSet(request(stmt, DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_SELECT_STAR), out);

		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}

