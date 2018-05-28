package org.inria.dmsp.test.TEST_EP_UI_tristanauto;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

///////////////////////////////
//DO NOT CHANGE AFTER HERE  //
///////////////////////////////



import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import test.jdbc.Tools;
import test.runner.ITest;

import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		// connect information:
		String authent[] = new String [2];
		this.out = out;
		perf = 0;
		Tools_dmsp t = new Tools_dmsp(out);

		init();
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
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

		/*
		 *  Load initial delta and test result of the load
		 */
	    // Call the delta loader
	    DeltaLoader dl = new DeltaLoader(out);
		dl.perf=perf;
		// open the delta file
		InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("delta.csv"));
		// load the data into database
		dl.LoadDelta(fr, db); 

		// select * from *
//		select_star();

		// load data:
		Generate_data(1062/*first_us*/, 13/*nb_us*/, 1001/*first_fo*/, 16/*nb_fo*/, 2/*first_ep*/,
				3/*first_co*/, 20/*nb_add_ev*/, 20/*nb_add_co*/, 50/*nb_add_in*/);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT -------");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1016/*event.idForm: "Plan d'accompagnement"*/, 
				ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_SINGLEEVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1016/*event.idForm: "Plan d'accompagnement"*/,  
				ps), out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	// generate event/info/comments with UI plans:
	public void Generate_data(
			// parameters for already existing tuples
			// assumption : idglobal values are continuous in each table
			int first_us, int nb_us, int first_fo, int nb_fo, int first_ep, int first_co,
			// parameters for tuples to be created: must be > 0
			int nb_add_ev, int nb_add_co, int nb_add_in) throws Exception
	{
		// loops and temp variables
		int i, t1, t2, t3, t4;
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		// the prepared statement used for parametric queries:
		java.sql.PreparedStatement ps ; 
		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
		// the resultset used for all queries:
		java.sql.ResultSet rs;
		// id of the spt (patient) and current ts_spt
		int id, ts_spt;
		// idglobal of first tuple of event, info, comment:
		int first_add_ev=0, /*first_add_in=0,*/ first_add_co=0;

		if(perf==0){out.println("Start to generate data...");}
		// set id of the spt (patient)
		id = ((org.inria.jdbc.Connection) db).getSptIdPatient();

		/*  INSERT INTO EVENT  */
		if(perf==0){out.println("Insert into EVENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		String date, date2;
		for(i=0; i<nb_add_ev; i++){
			ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			date2 = (2010+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			Tools_dmsp.Test_EVENT_INSERT(
				id, ts_spt, first_fo+i%nb_fo, first_us+i%nb_us, first_ep, 
				java.sql.Date.valueOf(date), java.sql.Date.valueOf(date2), 12, ps);
			if(i==0){
				first_add_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
				if(perf==0){out.println("first_add_ev = "+first_add_ev);}
			}
		}

		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);

		/* INSERT INTO COMMENT */
		if(perf==0){out.println("Insert into COMMENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		c[0]='a'; 	
		for(i=0; i<nb_add_co; i++){
			if(i%26==0){c[0]='a';}
			c[1]=c[0]; String s = new String (c);
			ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
			Tools_dmsp.Test_COMMENT_INSERT(id, ts_spt, s, ps);
			c[0]++;
			if(i==0){
				first_add_co = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
				if(perf==0){out.println("first_add_co = "+first_add_co);}
			}
		}

		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);

		/*  INSERT INTO INFO */
		if(perf==0){out.println("Insert into INFO ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		c[0]='a'; // to build value of valchars
		t1=first_add_ev; // first event
		t2=first_add_co-1; // first comment
		t3=0; // current value of comment 
		t4=0; // current position
		for(i=0; i<nb_add_in; i++){
			ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
			// at each (nb_in/nb_co) infos:
			if((i+1)%(nb_add_in/nb_add_co)==0){
				t2++; // link info to the next comment
				// if more than max number of comment
				if(t2-first_add_co >= nb_add_co*2)
					t2=first_add_co; // link to first added comment
				t3=t2;
			} else 
				t3=first_co; // link info to the comment no_comment
			if(i%26==0){c[0]='a';}
			c[1]=c[0]; String s = new String (c);
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			Tools_dmsp.Test_INFO_INSERT(
				id, ts_spt, t1 /*event*/, t3 /*comment*/,s,i,
				java.sql.Date.valueOf(date),	
				t4++ /*position*/, i%2/*filtre*/, i, ps);
			c[0]++;
			// at each (nb_in/nb_ev) infos:
			if(i%(nb_add_in/nb_add_ev)==0) { 
				t1++; // increment idglobal of event
				// if more than max number of event
				if(t1-first_add_ev >= nb_add_ev*2)
					t1=first_add_ev; // link to first added event
				t4=0; // reset info.position
			}
		}

		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
	}
}
