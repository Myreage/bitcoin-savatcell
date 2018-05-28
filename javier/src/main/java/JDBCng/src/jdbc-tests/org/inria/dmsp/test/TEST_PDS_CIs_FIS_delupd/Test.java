package org.inria.dmsp.test.TEST_PDS_CIs_FIS_delupd;

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
		int i,				// for loops
			IdG1, IdG2,		// Generated id (insert from UI)
			res = 0;		// number of updated results

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
				50 /*event*/, 3 /*comment*/, 100 /*info*/, db); 
		
		IdG2 = IdG1 = dg.first_in + dg.nb_in;
		/*
		 *  TEST EP_*_INSERT (Synch)
         */
		// EP_EVENT_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EVENT_INSERT, 
				java.sql.Statement.NO_GENERATED_KEYS);
		Tools_dmsp.Test_EVENT_INSERT(IdG1, 0, 2, 0, dg.first_fo, dg.first_us, 
				dg.first_ep, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 25, ps);
		if(perf==0){out.println("EP_EVENT_INSERT -> ID=" + IdG1);}
		if(perf==0){out.println("");}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_ID);
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID=" + IdG1);}
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		// more
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EVENT_INSERT, 
				java.sql.Statement.NO_GENERATED_KEYS);
		for(i=0;i<10;++i){
			Tools_dmsp.Test_EVENT_INSERT(++IdG2, 0, 3, 0, dg.first_fo+i%dg.nb_fo,
				dg.first_us+i%dg.nb_us, dg.first_ep, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 25, ps);
			if(perf==0){out.println("EP_EVENT_INSERT -> ID=" + IdG2);}
			if(perf==0){out.println("");}
		}
		
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
		 *  TEST EP_*_UPDATE (Synch)
         */
		// EP_EVENT_UPDATE:
		// select first_event
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev, ps);
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID=" + dg.first_ev);}
		lireResultSet(rs, out);
		// update first_event
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_UPDATE);
		res = Tools_dmsp.Test_EVENT_UPDATE(10, 0, 10, java.sql.Date.valueOf("2009-07-14"), java.sql.Date.valueOf("2099-01-01"), 1, dg.first_ev, ps);
		if(perf==0){out.println("EP_EVENT_UPDATE -> ID=" + dg.first_ev + " - " + res + " tuple(s) updated");}
		if(perf==0){out.println("");}
		// select first_event again
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev, ps);
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID=" + dg.first_ev);}
		lireResultSet(rs, out);

		// EP_COMMENT_UPDATE:
		// select first_co
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_COMMENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co, ps);
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID=" + dg.first_co);}
		lireResultSet(rs, out);
		// update first_co
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_COMMENT_UPDATE);
		Tools_dmsp.Test_COMMENT_UPDATE(0, 0, 299, "updated comment", dg.first_co, ps);
		if(perf==0){out.println("EP_COMMENT_UPDATE -> ID=" + dg.first_co + " - " + res + " tuple(s) updated");}
		if(perf==0){out.println("");}
		// select first_co again
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_COMMENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co, ps);
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID=" + dg.first_co);}
		lireResultSet(rs, out);
		
		/*
		 *  TEST EP_*_DELETE (Synch)
         */
		// EP_EVENT_DELETE
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_DELETE);
		// delete 1 tuple
		res = Tools_dmsp.Test_DELETE_BY_ID(IdG1, ps);
		if(perf==0){out.println("EP_EVENT_DELETE -> ID="+ IdG1 + " - " + res + " tuple(s) deleted");}
		if(perf==0){out.println("");}
		// EP_EVENT_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_ID);
		// read the deleted tuple
		rs = Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps);
		if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID="+IdG1);}
		lireResultSet(rs, out);
		// EP_EVENT_SELECT_BY_IDFORM
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_EVENT_SELECT_BY_IDFORM);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_fo+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_fo+i, ps);
			if(perf==0){out.println("EP_EVENT_SELECT_BY_IDFORM -> ID="+(dg.first_fo+i));}
			lireResultSet(rs, out);
		}
		
		// EP_INFO_DELETE
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_INFO_DELETE);
		// delete 1/3 tuples
		for(i=0;i<dg.nb_in+1;i+=3){
			res = Tools_dmsp.Test_DELETE_BY_ID(dg.first_in+i, ps);
			if(perf==0){out.println("EP_INFO_DELETE -> ID="+ (dg.first_in+i) + " - " + res + " tuple(s) deleted");}
		}
		if(perf==0){out.println("");}
		// EP_INFO_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS_FIS.EP_INFO_SELECT_BY_ID);
		// read all tuples plus one non existing
		for(i=0;i<dg.nb_in+1;++i){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in+i, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID="+(dg.first_in+i));}
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
