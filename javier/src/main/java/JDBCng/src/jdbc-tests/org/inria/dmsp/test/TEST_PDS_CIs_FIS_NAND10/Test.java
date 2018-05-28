package org.inria.dmsp.test.TEST_PDS_CIs_FIS_NAND10;

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DataGenerator;
import org.inria.dmsp.tools.Tools_dmsp;

import test.runner.ITest;

public class Test extends Tools implements ITest {
	 
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {

		this.out = out;
		perf = 0;	// for traces (test traces ==> perf)

		Tools_dmsp t = new Tools_dmsp(out);
		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		int i;				// for loops

		// connect without authentication
		openConnection(dbmsHost, null);

		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();

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

	    int ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		if(perf==0){out.println("// TS_SPT currently = "+ts_spt);}

	    int id = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		if(perf==0){out.println("// Patient id = "+id);}

		/*
		 *  TEST EP_*_INSERTS (Synch) - except into MATRICE_PATIENT
         */
	    // Call the data generator
	    DataGenerator dg = new DataGenerator(out);
		dg.perf=perf;
		dg.PDS_INSERT_Data_Generated(
				3 /*user*/, 3 /*role*/, 3 /*hab*/, 3 /*form*/, 1 /*episode*/,
				50 /*event*/, 3 /*comment*/, 1303 /*info*/, db); 
		
		/*
		 *  TEST EP_*_SELECT_BY_ID (Synch)
         */
		// EP_EPISODE_SELECT_BY_ID:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EPISODE_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_ep+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ep+i, ps);
			if(perf==0){out.println("EP_EPISODE_SELECT_BY_ID -> ID="+(dg.first_ep+i));}
			lireResultSet(rs, out);
		}
		// EP_FORMULAIRE_SELECT_BY_ID:		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_FORMULAIRE_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_fo+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_fo+i, ps);
			if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID="+(dg.first_fo+i));}
			lireResultSet(rs, out);
		}
		// EP_USER_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_USER_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_us+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_us+i, ps);
			if(perf==0){out.println("EP_USER_SELECT_BY_ID -> ID="+(dg.first_us+i));}
			lireResultSet(rs, out);
		}
		// EP_EVENT_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_ev+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev+i, ps);
			if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID="+(dg.first_ev+i));}
			lireResultSet(rs, out);
		}
		// EP_EVENT_SELECT_BY_IDFORM
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_IDFORM);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_fo+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_fo+i, ps);
			if(perf==0){out.println("EP_EVENT_SELECT_BY_IDFORM -> ID="+(dg.first_fo+i));}
			lireResultSet(rs, out);
		}
		// EP_INFO_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_INFO_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_in+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in+i, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID="+(dg.first_in+i));}
			lireResultSet(rs, out);
		}
		// EP_COMMENT_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_COMMENT_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_co+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co+i, ps);
			if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID="+(dg.first_co+i));}
			lireResultSet(rs, out);
		}
		// EP_ROLE_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_ROLE_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_ro+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ro+i, ps);
			if(perf==0){out.println("EP_ROLE_SELECT_BY_ID -> ID="+(dg.first_ro+i));}
			lireResultSet(rs, out);
		}
		// EP_HABILITATION_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_HABILITATION_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_ha+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ha+i, ps);
			if(perf==0){out.println("EP_HABILITATION_SELECT_BY_ID -> ID="+(dg.first_ha+i));}
			lireResultSet(rs, out);
		}

		/*
		 * TEST_*_SELECT_STAR (EP_TEST)
		 */
		// SELECT STAR FROM STAR:
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
