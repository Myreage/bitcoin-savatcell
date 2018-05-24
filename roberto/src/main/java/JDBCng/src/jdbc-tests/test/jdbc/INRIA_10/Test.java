package test.jdbc.INRIA_10;

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

		// select * from info where IdEvent = 53
		out.println("Select * from info where IdEvent = "+53);
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.QUERY_NOAC_SELECT_INFO_BY_EVENT);
		lireResultSet(Tools_schemaIndexInfo.Test_INFO_SELECT_STAR_BY_EVENT_ID(53, ps), out);

		// commit
		Save_DBMS_on_disk();

		// insert 5 tuples to table INFO
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.INFO_NOAC_INSERT_WITH_IDS);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((459), 0, 0, 459, (53), (29), (60), null, 0, 1, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((460), 0, 0, 460, (53), (38), (58), null, 0, 14, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((462), 0, 0, 462, (53), (36), (61), null, 1, 10, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((463), 0, 0, 463, (53), (37), (56), null, 20080312, 5, ps);
		Tools_schemaIndexInfo.Test_INFO_NOAC_AC_INSERT_WITH_IDS((464), 0, 0, 464, (53), (40), (63), null, 0, 0, ps);

		// the same query
		out.println("Select * from info where IdEvent = "+53);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.QUERY_NOAC_SELECT_INFO_BY_EVENT);
		lireResultSet(Tools_schemaIndexInfo.Test_INFO_SELECT_STAR_BY_EVENT_ID(53, ps), out);

		// roll back
		Rollback_Transaction();

		// the same query
		out.println("Select * from info where IdEvent = "+53);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.Execution_Plan.QUERY_NOAC_SELECT_INFO_BY_EVENT);
		lireResultSet(Tools_schemaIndexInfo.Test_INFO_SELECT_STAR_BY_EVENT_ID(53, ps), out);

		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}

