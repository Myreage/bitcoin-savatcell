package org.inria.dmsp.test.TEST_EP_UI_auto;

/////////////////////////////////////////////////////
//IMPORTS THAT CAN BE CHANGED TO TEST OTHER PLANS //
/////////////////////////////////////////////////////

///////////////////////////////
//DO NOT CHANGE AFTER HERE  //
///////////////////////////////

import java.io.PrintWriter;		// to produce the output of the test
import java.io.InputStreamReader;

import test.jdbc.Tools;
import test.runner.ITest;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
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
		// the resultset used for the queries:
		java.sql.ResultSet rs;
		// the statement for non-parametric select:
		java.sql.Statement st;
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

		// select * from *
//		select_star();

		  /*
		   *  FIRST PLANS FOR UI: 
		   *
		   *  LOGIN:
		   * n   EP_QUERY_NOAC_SELECT_USER_BY_CERTIF					--> selection du user selon son certificat
		   * n   EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER			--> role pour lequel user est habilité
		   * y   EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM			--> formulaires autorisés en insert
		   *  SELECT PLANS:
		   * n 	EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE		--> vue chronologique
		   * n 	EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE		--> vue chronologique
		   * n 	EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM 	--> fiche patient [+ plan d'acc.] + liste passages/ev. importants)
		   * y 	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM 		--> fiche patient [+ plan d'acc.] + liste passages/ev. importants)
		   * y 	EP_QUERY_NOAC_SELECT_USER_BY_ID						--> détail d'un intervenant
		   * y 	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT		--> ouverture d'un formulaire
		   * y 	EP_QUERY_AC_SELECT_INFO_BY_FILTRE					--> listes à base d'infos (entourage, etc.)
		   * n 	EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE				--> liste des intervenants
		   * y 	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE --> calendrier
		   * n 	EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE  	--> traitements en cours
		   * y 	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT --> list of comments to fill in a given cell of a form
		   *  INSERT PLANS:
		   * y 	OEP_EVENT_AC_INSERT
		   * y 	OEP_INFO_AC_INSERT
		   * y 	OEP_COMMENT_NOAC_INSERT
		   * n 	EP_HABILITATION_NOAC_INSERT
		   *  UPDATE PLANS:
		   * y 	INFO_AC_UPDATE_BY_ID
		   * y 	COMMENT_AC_UPDATE_BY_ID
		   * y 	EVENT_AC_UPDATE_BY_ID
		   */

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 0 ------------ = " +
				"Accès à 1 user --> EP_QUERY_NOAC_SELECT_USER_BY_ID");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		// NB: QUERY ON USER HAVE NO ACCESS RIGHT CHECKS...
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_USER_BY_ID");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1068, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 0.1 ------------ = " +
				"Accès à 1 event (datefin) --> EP_QUERY_AC_SELECT_EVENT_BY_ID");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		int form_id1 = 1005; // authorized to soc, forbidden to med
		//int form_id2 = 1006; // forbidden to soc, authorized to med
		int comment_id1 = 1694498840;// form 1005
		int comment_id2 = 1694498841;// form 1006
		int event_id1 = 1694498821;// form 1005
		int event_id2 = 1694498822;// form 1006
		int info_id1 = 1694498864;// form 1005
		int info_id2 = 1694498866;// form 1006
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_BY_ID: fordidden access - event n°"+event_id1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(event_id1/*forbidden*/, ps), out);
		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_BY_ID: authrorized access - event n°"+event_id2);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(event_id2/*authorized*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 0.2 ------------ = " +
				"\n - Accès à la liste des users --> EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE" +
				"\n - Liste des users habilités --> EP_QUERY_NOAC_SELECT_HABILITATION  ");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE :");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1/*infolegale=1*/, ps), out);	
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_HABILITATION :");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_HABILITATION);
		lireResultSet(rs, out);

		if(perf==0){out.println("----------- TEST BEGIN 0.3 ------------ = " +
				"\n - Accès à la liste des users (sans test sur infolegale) --> EP_QUERY_NOAC_SELECT_USER" );}
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_USER:");}
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER);
		lireResultSet(rs, out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 0.5 ------------ = " +
				"liste des form autorisés --> EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1/*id intern user*/,
				1/*id intern episode*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_AUTHORIZED_INSERT_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1/*id intern user*/,
				1/*id intern episode*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 1 ------------ = " +
				"listes (entourage, etc.) (Info.Filtre=?) --> " +
				"EP_QUERY_AC_SELECT_INFO_BY_FILTRE");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_INFO_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1/*filtre*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_INFO_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1/*filtre*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 1.1 ------------ = " +
				"list of comments to fill in a given cell in a form --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1694498822/*event.IdG: authorized*/,
				9/*info.idconcept*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT_AND_CONCEPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1694498822/*event.IdG: forbidden*/,
				9/*info.idconcept*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 2 ------------ = " +
				"liste des rendez-vous du calendrier --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with privileges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		if(perf==0){out.println("FORBIDDEN FORM = 1005 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1005/*form.IdG: forbidden*/,
				1/*info.filtre=1 : 50%*/, ps), out);
		if(perf==0){out.println("AUTHORIZED FORM = 1006 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1006/*form.IdG: authorized*/,
				1/*info.filtre=1 : 50%*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with privileges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		if(perf==0){out.println("FORBIDDEN FORM = 1015 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1015/*form.IdG: forbidden*/,
				1/*info.filtre=1 : 50%*/, ps), out);
		if(perf==0){out.println("AUTHORIZED FORM = 1005 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1005/*form.IdG: authorized*/,
				1/*info.filtre=1 : 50%*/, ps), out);
		
		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 2.5 ------------ = " +
				"liste des rendez-vous du calendrier --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with privileges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		if(perf==0){out.println("FORBIDDEN FORM = 1005 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1005/*form.IdG: forbidden*/,
				12/*event.filtre=12*/, ps), out);
		if(perf==0){out.println("AUTHORIZED FORM = 1006 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1006/*form.IdG: authorized*/,
				12/*event.filtre=12*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with privileges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		if(perf==0){out.println("FORBIDDEN FORM = 1015 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1015/*form.IdG: forbidden*/,
				12/*event.filtre=12*/, ps), out);
		if(perf==0){out.println("AUTHORIZED FORM = 1005 ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE_FROM_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT( 1005/*form.IdG: authorized*/,
				12/*event.filtre=12*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 3 ------------ = " +
				"liste des traitements en cours --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_DATE( 1013/*event.idForm: "traitement"*/,
				java.sql.Date.valueOf("2000-12-30")/*event.dateFin*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_DATE( 1013/*event.idForm: "traitement"*/,
				java.sql.Date.valueOf("2000-12-30")/*event.dateFin*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 4 ------------ = " +
				"fiche patient [+ plan d'acc.] + liste passages/ev. importants) --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1013/*event.idForm: "traitement"*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1013/*event.idForm: "traitement"*/, ps), out);

		// BEGIN TEST
		if(perf==0){out.println("----------- TEST BEGIN 5 ------------ = " +
				"ouverture d'un formulaire --> " +
				"EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1694498829/*event.idG linked to form 1013*/, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1065/1076:
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT( 1694498829/*event.idG linked to form 1013*/, ps), out);

		// BEGIN TEST
		/*  update plans 	--> INFO_AC_UPDATE_BY_ID (SET TSSPT = ?, ValChar = ?, ValNum = ?, ValDate = ? WHERE IdGlobal = ?)
		 *  				--> COMMENT_AC_UPDATE_BY_ID (SET TSSPT = ?, ValComment = ? WHERE IdGlobal = ?)
		 *  				--> EVENT_AC_UPDATE_BY_ID (SET TSSPT = ?, DateEvent = ?, DateFin = ?, Filtre = ? WHERE IdGlobal = ?)
		 */
		if(perf==0){out.println("----------- TEST BEGIN 6 ------------ UPDATE EVENT/INFO/COMMENT");}

		// connect with user 1067(SAGE-CH0162), role 1075(medecin),
		// then connect with user 1065(Lefebvre), role 1076 (referent social)
		// execute updates with priviledges :
		if(perf==0){out.println("--------- MED - FORBIDDEN ACTION : UPDATE Event/Info/comment ");}
		update_event_info_comment(dbmsHost, "1067", "1075", event_id1, info_id1, comment_id1);
		if(perf==0){out.println("--------- SOC - FORBIDDEN ACTION : UPDATE Event/Info/comment ");}
		update_event_info_comment(dbmsHost, "1065", "1076", event_id2, info_id2, comment_id2);
		if(perf==0){out.println("--------- MED - AUTHORIZED ACTION : UPDATE Event/Info/comment ");}
		update_event_info_comment(dbmsHost, "1067", "1075", event_id2, info_id2, comment_id2);
		if(perf==0){out.println("--------- SOC - AUTHORIZED ACTION : UPDATE Event/Info/comment ");}
		update_event_info_comment(dbmsHost, "1065", "1076", event_id1, info_id1, comment_id1);

		// BEGIN TEST
		/*  insert plans 	   
		 *  	OEP_EVENT_AC_INSERT
		 *  	OEP_INFO_AC_INSERT
		 */
		if(perf==0){out.println("----------- TEST BEGIN 7.0 ------------ INSERT EVENT");}
		/*  INSERT INTO EVENT */
		form_id1 = 1005; // authorized to soc, forbidden to med
//		form_id2 = 1006; // forbidden to soc, authorized to med
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("Insert into EVENT without priviledge:");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		int author = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		int tsspt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();		
		int res = Tools_dmsp.Test_EVENT_INSERT(
				author/*author*/, 
				tsspt/*tsspt*/, 
			form_id1/*fo_id*/, 1067/*us_id*/, 2/*no_ep*/, 
			java.sql.Date.valueOf("2000-12-30"),
			java.sql.Date.valueOf("2000-12-31"), 12, ps);
		int id = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("Number of rows inserted = "+ res);}
		if(perf==0){out.println("GET GENERATED (EVENT) ID = "+ id);}
		// CHECK INSERT:
		if(perf==0){out.println("TRY TO INSERT.... AFTER INSERT : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(id, ps), out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1065 (Lefebvre), role 1076( referent social)
		authent[0] = "1065"; authent[1]= "1076"; openConnection(dbmsHost, authent);
		// execute query with privileges of user 1067/1075:
		if(perf==0){out.println("Insert into EVENT with priviledge:");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		author = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		tsspt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();		
		res = Tools_dmsp.Test_EVENT_INSERT(
			author/*author*/, 
			tsspt/*tsspt*/, 
			form_id1/*fo_id*/, 1067/*us_id*/, 2/*no_ep*/, 
			java.sql.Date.valueOf("2000-12-30"),
			java.sql.Date.valueOf("2000-12-31"), 12, ps);
		id = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("Number of rows inserted = "+ res);}
		if(perf==0){out.println("GET GENERATED (EVENT) ID = "+ id);}
		// CHECK INSERT:
		if(perf==0){out.println("TRY TO INSERT.... AFTER INSERT : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(id, ps), out);

		if(perf==0){out.println("----------- TEST BEGIN 7.1 ------------ INSERT INFO");}
		/*  INSERT INTO INFO */
		form_id1 = 1005; // authorized to soc, forbidden to med
//		form_id2 = 1006; // forbidden to soc, authorized to med
		int no_co = 3;// idglobal of no_comment
		event_id1 = 1694498821;// form 1005
//		event_id2 = 1694498822;// form 1006
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; openConnection(dbmsHost, authent);
		// execute query with priviledges of user 1067/1075:
		if(perf==0){out.println("Insert into INFO without priviledge:");}
		// ----- WITHOUT PRIVILEGES --------
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		author = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		tsspt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();		
		res = Tools_dmsp.Test_INFO_INSERT(author/*author*/, tsspt/*tsspt*/, 
				event_id1/*event*/, no_co/*comment*/,
				"test insertion", 67890, java.sql.Date.valueOf("2000-10-20"), 
				101/*position*/, 10/*filtre*/, 100/*idconcept*/, ps);
		id = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("Number of rows inserted = "+ res);}
		if(perf==0){out.println("GET GENERATED (INFO) ID = "+ id);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(id, ps), out);
		// ------ WITH PRIVILEGES --------
		if(perf==0){out.println("Insert into INFO with priviledge:");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		author = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		tsspt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();		
		res = Tools_dmsp.Test_INFO_INSERT(author/*author*/, tsspt/*tsspt*/, 
				event_id2/*event*/, no_co/*comment*/,
				"test insertion", 67890, java.sql.Date.valueOf("2000-10-20"), 
				101/*position*/, 10/*filtre*/, 100/*idconcept*/, ps);
		id = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("Number of rows inserted = "+ res);}
		if(perf==0){out.println("GET GENERATED (INFO) ID = "+ id);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(id, ps), out);

		if(perf==0){out.println("----------- TEST BEGIN 8 ------------ REMOVE HABILITATION");}
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		// connect with authentication : user 0(NumeroUn), role 1(patient)
		authent[0] = "0"; authent[1]= "1"; openConnection(dbmsHost, authent);
		if(perf==0){out.println("All habilitations before delete: ");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("Remoove habilitation of user 1067/1075 (a doctor)...");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_HABILITATION_NOAC_DELETE_BY_USER);
		res = Tools_dmsp.Test_HABILITATION_DELETE(
				1067/*user 1067(SAGE-CH0162), role 1075(medecin)*/, ps);
		if(perf==0){out.println(""+res+" row(s) deleted...");}
		if(perf==0){out.println("All habilitations after delete: ");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
	    // close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		if(perf==0){out.println("Try to connect user 1067/1075 (no more habilited)... ");}
		// connect with authentication : user 1067(SAGE-CH0162), role 1075(medecin)
		authent[0] = "1067"; authent[1]= "1075"; 
		try{
			openConnection(dbmsHost, authent);
			if(perf==0){out.println("User Connected... ");}
		}
		catch (Exception e){
			if(perf==0){out.println(e.getMessage());}
		}
		// connect with authentication : user 0(NumeroUn), role 1(patient)
		authent[0] = "0"; authent[1]= "1"; 
		openConnection(dbmsHost, authent);
		// re-insert habilitation for 1067/1075:
		if(perf==0){out.println("Re-inert habilitation of user 1067/1075 (a doctor)...");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_HABILITATION_NOAC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		author = ((org.inria.jdbc.Connection) db).getSptIdPatient();
		tsspt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();		
		res = Tools_dmsp.Test_HABILITATION_INSERT(author/*author*/, tsspt/*tsspt*/, 
				0/*Tssanteos*/, 1075/*idrole*/, 1067/*iduser*/,ps);
		id = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("Nb of inserted tuples = "+ res);}
		if(perf==0){out.println("GET GENERATED (HABILITATION) ID = "+ id);}
		// Check insertion:
		if(perf==0){out.println("All habilitations after re-insert: ");}
		// the statement for non-parametric select:
		st = db.createStatement();
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		// try to re-connect user 1067(SAGE-CH0162), role 1075(medecin)
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		authent[0] = "1067"; authent[1]= "1075"; 
		openConnection(dbmsHost, authent);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}

	public void update_event_info_comment(String dbmsHost, String user, String role, int event_id1, 
			int info_id1, int comment_id1) throws Exception
	{
		if(perf==0){out.println("TRY TO UPDATE (event, info, comment) n° = ("+
				event_id1+", "+ info_id1+", "+ comment_id1+")");}

		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 

		// close session:
		Save_DBMS_on_disk();
		Shutdown_DBMS();
		String[] authent=new String[2];

		// connect with user, role 
		authent[0] = user; authent[1]= role; 
		if(perf==0){out.println("CONNECT USER/ROLE = "+authent[0]+"/"+authent[1]);}
		openConnection(dbmsHost, authent);

		// 1- TRY UPDATE EVENT:
		if(perf==0){out.println("    ----- EVENT n° "+event_id1 +" BEFORE UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(event_id1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EVENT_AC_UPDATE_BY_ID);
		int ts_spt= ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		int res = Tools_dmsp.Test_EVENT_UPDATE( 
				ts_spt /*ts_spt*/,
				java.sql.Date.valueOf("2000-04-04")/*dateEvent*/,
				java.sql.Date.valueOf("2030-04-04")/*dateFin*/, 
				888/*filtre*/, 
				event_id1/*idG: authorized*/, ps);
		// CHECK UPDATE:
		if(perf==0){out.println("TRY TO UPDATE.... number of rows udpated "+res+"... AFTER UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(event_id1, ps), out);

		// 2- TRY UPDATE INFO:
		if(perf==0){out.println("    ----- INFO n° "+info_id1 +" BEFORE UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(info_id1, ps), out);		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.INFO_AC_UPDATE_BY_ID);
		res = Tools_dmsp.Test_INFO_UPDATE( 
				// params: 1=38(TSSPT), 2=42(ValChar), 3=43(ValNum), 4=44(ValDate), 5=36(IdG)
				((org.inria.jdbc.Connection) db).getGlobalTimestamp()/*ts_spt*/,
				"nouveau valchar saisi" /*valchar*/,
				1010101 /*valnum*/,
				java.sql.Date.valueOf("1999-05-19")/*valdate*/, 
				887 /*Filtre*/,
				info_id1 /*idG*/, ps);
		// CHECK UPDATE:
		if(perf==0){out.println("TRY TO UPDATE.... number of rows udpated "+res+"... AFTER UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(info_id1, ps), out);		
		// 3- TRY UPDATE COMMENT:
		if(perf==0){out.println("    ----- COMMENT n° "+info_id1 +" BEFORE UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(comment_id1, ps), out);		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.COMMENT_AC_UPDATE_BY_ID);
		res = Tools_dmsp.Test_COMMENT_UPDATE( 
				// params: params: 1=49(TSSPT), 2=51(ValComment), 3=47(IdGlobal)
				((org.inria.jdbc.Connection) db).getGlobalTimestamp()/*ts_spt*/,
				"nouveau valcommment saisi ... pour tester l'update" /*valcomment*/,
				comment_id1 /*idG: authorized*/, ps);
		// CHECK UPDATE:
		if(perf==0){out.println("TRY TO UPDATE.... number of rows udpated "+res+"... AFTER UPDATE : ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(comment_id1, ps), out);		
	}

	// SELECT STAR FROM STAR
	public void select_star() throws Exception
	{
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
//		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
//		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
//		lireResultSet(rs, out);
	}
}
