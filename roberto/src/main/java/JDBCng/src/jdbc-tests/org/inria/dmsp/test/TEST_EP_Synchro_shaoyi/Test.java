package org.inria.dmsp.test.TEST_EP_Synchro_shaoyi;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

///////////////////////////////
// DO NOT CHANGE AFTER HERE  //
///////////////////////////////



import test.jdbc.Tools;
import test.runner.ITest;

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;
import java.util.Calendar;

import org.inria.dmsp.tools.DeltaLoader;
import org.inria.dmsp.tools.Tools_dmsp;

public class Test extends Tools implements ITest
{
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception
	{
		// connect information:
		this.out = out;
		perf = 0;
		Tools_dmsp t = new Tools_dmsp(out);

		init();
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
		dl.perf = perf;
		// open the delta file
		InputStreamReader fr = new InputStreamReader(Test.class.getResourceAsStream("delta.csv"));
		// load the data into database
		dl.LoadDelta(fr, db); 

		// select * from *
		//select_star();

		// BEGIN TEST 1 
		if(perf==0){out.println("----------- TEST BEGIN 1 ------------ = " +
				"Select * from episode where IdGlobal = ? --> EP_EPISODE_SELECT_BY_ID");}

		/*
		 *  INSERT INTO EPISODE
		 */
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_INSERT);
		byte c [] = new byte[2];
		c[0]='a'; c[1]='a';	
		for(int i=0; i<10; i++){
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_EPISODE_INSERT(
				0,2,0, s, ps);
		}

		// select * from episode
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR: after inserting 10 tuples");}
		java.sql.Statement st = db.createStatement();
		java.sql.ResultSet rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from episode where IdGlobal = 2
		if(perf==0){out.println("EP_EPISODE_SELECT_BY_ID --> 2: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		// select * from episode where IdGlobal = 1001
		if(perf==0){out.println("EP_EPISODE_SELECT_BY_ID --> 1001: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1001, ps), out);

		// select * from episode where IdGlobal = 1694498820
		if(perf==0){out.println("EP_EPISODE_SELECT_BY_ID --> 1694498820: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1694498820, ps), out);		
		// END TEST 1

		// BEGIN TEST 2
		if(perf==0){out.println("----------- TEST BEGIN 2 ------------ = " +
				"Select * from Formulaire where IdGlobal = ? --> EP_FORMULAIRE_SELECT_BY_ID");}

		// select * from Formulaire
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from formulaire where IdGlobal = 1001
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID --> 1001: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1001, ps), out);

		// select * from formulaire where IdGlobal = 2
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID --> 2: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		// select * from formulaire where IdGlobal = 1008
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID --> 1008: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1008, ps), out);

		// select * from formulaire where IdGlobal = 1016
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID --> 1016: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1016, ps), out);		
		// END TEST 2

		// BEGIN TEST 3
		if(perf==0){out.println("----------- TEST BEGIN 3 ------------ = " +
		"Select * from Role where IdGlobal = ? --> EP_ROLE_SELECT_BY_ID");}

		// select * from Role
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from role where IdGlobal = 1075
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID --> 1075: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1075, ps), out);

		// select * from role where IdGlobal = 2
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID --> 2: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		// select * from role where IdGlobal = 1076
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID --> 1076: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1076, ps), out);

		// select * from role where IdGlobal = 1
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID --> 1: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);		
		// END TEST 3

		// BEGIN TEST 4
		if(perf==0){out.println("----------- TEST BEGIN 4 ------------ = " +
				"Select * from Habilitation where IdGlobal = ? --> EP_HABILITATION_SELECT_BY_ID");}

		// select * from Habilitation
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from Habilitation where IdGlobal = 1077
		if(perf==0){out.println("EP_HABILITATION_SELECT_BY_ID --> 1077: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1077, ps), out);

		// select * from Habilitation where IdGlobal = 2
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID --> 2: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		// select * from Habilitation where IdGlobal = 1085
		if(perf==0){out.println("EP_HABILITATION_SELECT_BY_ID --> 1085: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1085, ps), out);

		// select * from Habilitation where IdGlobal = 1092
		if(perf==0){out.println("EP_HABILITATION_SELECT_BY_ID --> 1092: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1092, ps), out);		
		// END TEST 4

		// BEGIN TEST 5
		if(perf==0){out.println("----------- TEST BEGIN 5 ------------ = " +
				"Select * from Role where IdGlobal = ? --> EP_PATIENT_SELECT_BY_ID");}

		// select * from MatricePatient
		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from MatricePatient where IdGlobal = 1093
		if(perf==0){out.println("EP_PATIENT_SELECT_BY_ID --> 1093: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1093, ps), out);

		// select * from MatricePatient where IdGlobal = 2
		if(perf==0){out.println("EP_PATIENT_SELECT_BY_ID --> 2: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(2, ps), out);

		// select * from MatricePatient where IdGlobal = 1106
		if(perf==0){out.println("EP_PATIENT_SELECT_BY_ID --> 1106: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1106, ps), out);

		// select * from MatricePatient where IdGlobal = 1115
		if(perf==0){out.println("EP_PATIENT_SELECT_BY_ID --> 1115: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1115, ps), out);		
		// END TEST 5

		// BEGIN TEST 6
		if(perf==0){out.println("----------- TEST BEGIN 6 ------------ = " +
				"Select * from Info where TSSPT > ? --> EP_INFO_SELECT_ON_TSSPT");}
		/*
		 *  INSERT INTO EVENT, INFO
		 */
		// insert 1 tuple into EVENT (TSSPT=10)
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		int nb = Tools_dmsp.Test_EVENT_INSERT(0, 10/*TSSPT*/, 1012 /*formid*/, 1063 /*userid*/, 
				2 /*no_ep*/, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 12 /*filtre*/, ps);
		int tmp_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());	// ask for generated key
		if(perf==0) out.println(nb+" event tuple inserted..." + tmp_ev);

		// insert 1 tuple into INFO (TSSPT=1)
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_INFO_INSERT);
		nb = Tools_dmsp.Test_INFO_INSERT(0, 1/*TSSPT*/, 0, tmp_ev/*event*/, 3/*comment*/,
				"char value", 12345, java.sql.Date.valueOf("2000-10-20"), 
				13/*position*/, 1/*filtre*/, 100, ps);
		if(perf==0) out.println(nb+" info tuple inserted...");

		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);

		// select * from Info where TSSPT > 0
		if(perf==0){out.println("EP_INFO_SELECT_ON_TSSPT --> TSSPT > 0: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		// select * from Info where TSSPT > 1
		if(perf==0){out.println("EP_INFO_SELECT_ON_TSSPT --> TSSPT > 1: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);
		// END TEST 6

		// BEGIN TEST 7
		if(perf==0){out.println("----------- TEST BEGIN 7 ------------ = " +
				"Select * from Comment where TSSPT > ? --> EP_COMMENT_SELECT_ON_TSSPT");}
		/*
		 *  INSERT INTO COMMENT + INFO + EVENT
		 */
		// insert 1 tuple into EVENT (TSSPT=10)
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_EVENT_INSERT(0, 10, 1012 /*formid*/, 1063 /*userid*/, 
				2 /*no_ep*/, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 12 /*filtre*/, ps);
		if(perf==0) out.println(nb+" event tuple inserted...");
		tmp_ev = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());	// ask for generated key

		// insert 1 tuple into COMMENT (TSSPT=100)		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_COMMENT_INSERT(0, 101 /*TSSPT*/, 0, "TEST_EP_Synchro_shaoyi", ps);
		if(perf==0) out.println(nb+" comment tuple inserted...");
		int tmp_co = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());	// ask for generated key

		// insert 1 tuple into INFO (TSSPT=102)		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.OEP_INFO_AC_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS);
		nb = Tools_dmsp.Test_INFO_INSERT(
			0, 102 /*TSSPT*/, tmp_ev /*event*/, tmp_co /*comment*/, "my comment", 1234,
			java.sql.Date.valueOf("2000-10-20"), 2 /*position*/, 1 /*filtre*/, 3, ps);
		if(perf==0) out.println(nb+" info tuple inserted...");

		// select * from Comment 
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		
		// select * from Comment where TSSPT > 100
		if(perf==0){out.println("EP_COMMENT_SELECT_ON_TSSPT --> TSSPT > 100: exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(100, ps), out);

		// select * from Comment where TSSPT > 101
		if(perf==0){out.println("EP_COMMENT_SELECT_ON_TSSPT --> TSSPT > 101: not exist");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(101, ps), out);
		// END TEST 7

		// BEGIN TEST 8
		if(perf==0){out.println("----------- TEST BEGIN 8 ------------ = " +
				"Insert 10 tuples into EPISODE --> EP_EPISODE_INSERT");}

		// select * from episode
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO EPISODE
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_INSERT);
		c[0]='m'; c[1]='m';	
		for(int i=100; i<110; i++){
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_EPISODE_INSERT(
				i,	0,2,0, s, ps);
		}

		// select * from episode
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR: after inserting 10 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);		
		// END TEST 8

		// BEGIN TEST 9
		if(perf==0){out.println("----------- TEST BEGIN 9 ------------ = " +
		"Insert 10 tuples into Formulaire --> EP_FORMULAIRE_INSERT");}

		// select * from Formulaire
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO FORMULAIRE
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_INSERT);
		c[0]='a'; c[1]='a';	
		for(int i=200; i<210; i++){
			if(i%32==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_FORMULAIRE_INSERT(
				i, 0,2,0, s, i%2/*filtre*/, ps);
		}

		// select * from Formulaire
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR: after inserting 10 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 9

		// BEGIN TEST 10
		if(perf==0){out.println("----------- TEST BEGIN 10 ------------ = " +
		"Insert 10 tuples into UserDMSP --> EP_USER_INSERT");}

		// select * from UserDMSP
		if(perf==0){out.println("EP_USER_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO USERDMSP
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_INSERT);
		c[0]='a'; c[1]='a';	
		byte certificate[] = new byte[20];
		for (int i=0;i<certificate.length;i++)
			certificate[i] = 'a';

		for(int i=300; i<310; i++){
			if(i%32==0){c[0]='a';certificate[certificate.length-1]='a';}
			c[1]=c[0];
			String s = new String (c);
			String cert = new String(certificate);
 			Tools_dmsp.Test_USER_INSERT(i, 0,2,0, 
 				s,1,s,s,1,s,s,s,s,s,s,s,1,cert,1075,ps);
			certificate[certificate.length-1]++;
			c[0]++;
		}

		// select * from UserDMSP
		if(perf==0){out.println("EP_USER_SELECT_STAR: after inserting 10 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 10

		// BEGIN TEST 11
		if(perf==0){out.println("----------- TEST BEGIN 11 ------------ = " +
		"Insert 10 tuples into Event --> EP_EVENT_INSERT");}

		// select * from Event
		if(perf==0){out.println("EP_EVENT_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO EVENT
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT);
		String date;
		for(int i=400; i<410; i++){
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			Tools_dmsp.Test_EVENT_INSERT(
				i,	0,0,0, 200+i%3/*nb_fo*/, 300+i%10, 100, 
				java.sql.Date.valueOf(date), java.sql.Date.valueOf(date), 12, ps);
		}

		// select * from Event
		if(perf==0){out.println("EP_EVENT_SELECT_STAR: after inserting 10 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 11

		// BEGIN TEST 12
		if(perf==0){out.println("----------- TEST BEGIN 12 ------------ = " +
				"Insert 3 tuples into Comment --> EP_COMMENT_INSERT");}

		// select * from Comment
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO COMMENT
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
		c[0]='a'; c[1]='a';	
		for(int i=500; i<503; i++){
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_COMMENT_INSERT(
				i, 0,0,0, s, ps);
		}

		// select * from Comment
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR: after inserting 3 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 12

		// BEGIN TEST 13
		if(perf==0){out.println("----------- TEST BEGIN 13 ------------ = " +
				"Insert 10 tuples into Info --> EP_INFO_INSERT");}
		
		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO INFO
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT);
		c[0]='a'; c[1]='a';	
		int t3=0; // current value of field info.position 
		for(int i=600; i<610; i++){
			// info.valdate
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2000+i%2);
			cal.set(Calendar.MONTH, i%12+1);
			cal.set(Calendar.DAY_OF_MONTH, i%20+1);
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_INFO_INSERT(
				i,	0,0,0, 405 /*event*/, 501 /*comment*/,s,i,
				new java.sql.Date(cal.getTime().getTime()),	
				t3++ /*position*/, i%2/*filtre*/, i, ps);
		}

		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR: after inserting 10 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 13

		// BEGIN TEST 14
		if(perf==0){out.println("----------- TEST BEGIN 14 ------------ = " +
				"Insert 3 tuples into Role --> EP_ROLE_INSERT");}

		// select * from Role
		if(perf==0){out.println("EP_ROLE_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO ROLE
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_INSERT);
		c[0]='a'; c[1]='a';	
		for(int i=700; i<703; i++){
			if(i%32==0){c[0]='a';}
			c[0]++; c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_ROLE_INSERT(i,0,2,0,s,ps);
		}		

		// select * from Role
		if(perf==0){out.println("EP_ROLE_SELECT_STAR: after inserting 3 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 14

		// BEGIN TEST 15
		if(perf==0){out.println("----------- TEST BEGIN 15 ------------ = " +
				"Insert 3 tuples into Habilitation --> EP_HABILITATION_INSERT");}

		// select * from Habilitation
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR: before new insertions");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		/*
		 *  INSERT INTO HABILITATION
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_INSERT);
		for(int i=800; i<803; i++){
			Tools_dmsp.Test_HABILITATION_INSERT(
 				i, 0,0,0, 700+i%3, 300+i%10,ps);
		}

		// select * from Habilitation
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR: after inserting 3 tuples");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 15

		// BEGIN TEST 16
		if(perf==0){out.println("----------- TEST BEGIN 16 ------------ = " +
				"Delete 1 tuple from Habilitation --> Test_HABILITATION_DELETE");}

		/*
		 *  DELETE FROM HABILITATION
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_DELETE);
		int res = Tools_dmsp.Test_DELETE_BY_ID(800, ps);
		if(perf==0){out.println(""+res+" row(s) deleted...");}

		// select * from Habilitation
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR: after deleting 1 tuple");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 16

		// BEGIN TEST 17
		if(perf==0){out.println("----------- TEST BEGIN 17 ------------ = " +
				"Delete 1 tuple from Info --> EP_INFO_DELETE");}

		/*
		 *  DELETE FROM INFO
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_DELETE);
		res = Tools_dmsp.Test_DELETE_BY_ID(605, ps);
		if(perf==0){out.println(""+res+" row(s) deleted...");}

		// select * from Info
		if(perf==0){out.println("EP_INFO_SELECT_STAR: after deleting 1 tuple");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 17

		// BEGIN TEST 18
		if(perf==0){out.println("----------- TEST BEGIN 18 ------------ = " +
				"Delete 1 tuple from Comment --> EP_COMMENT_DELETE");}

		/*
		 *  DELETE FROM COMMENT
		 */
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_DELETE);
		res = Tools_dmsp.Test_DELETE_BY_ID(501, ps);
		if(perf==0){out.println(""+res+" row(s) deleted...");}

		// select * from Comment
		if(perf==0){out.println("EP_COMMENT_SELECT_STAR: after deleting 1 tuple");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);	
		// END TEST 18

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	// SELECT STAR FROM STAR
	public void select_star() throws Exception {

		// the statement for non-parametric select:
		java.sql.Statement st = db.createStatement();
		// the resultset used for the queries:
		java.sql.ResultSet rs;

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
		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);

		// SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);
	}
}
		