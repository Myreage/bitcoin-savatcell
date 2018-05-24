package org.inria.dmsp.test.TEST_1;

import test.jdbc.Macro;

import java.io.PrintWriter;

import test.jdbc.Tools;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DataGenerator;
import org.inria.dmsp.tools.Tools_dmsp;
import org.inria.dmsp.tools.DMSP_QEP_IDs;

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
			first_mp,		// first IdGlobal inserted into table matrice_patient
			nb_mp=0,		// number of tuples into matrice patient
			IdG1, IdG2,		// Generated id (insert from UI)
			res = 0;		// number of updated results
		// to store value of certificate
		byte[] certificate = new byte [20]; 

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
		dg.INSERT_Data_Generated(
				5 /*user*/, 3 /*role*/, 5 /*hab*/, 20 /*form*/, 1 /*episode*/,
				10 /*event*/, 30 /*comment*/, 100 /*info*/, db); 

		/*
		 *  TEST EP_PATIENT_INSERTS_*_* (Synch) - (NB: semantically non correct for AC)
         */

		if(perf==0){out.println("// Insertion dans la table MATRICE PAT");}
		// INSERT INTO MATRICE PATIENT
		// 1- EP_PATIENT_INSERT_AUTRE_USER : (id_cat,id_actor) = (0,id_us)
		first_mp = dg.IdGlobal;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_AUTRE_USER);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 2, 0, 0 /*id_cat=0*/, Macro.CATEGORIE_USERDMSP, 
				dg.first_us /*id_us*/,	Macro.ACTEUR_USERDMSP, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 2- EP_PATIENT_INSERT_AUTRE_FORM : (id_cat,id_actor) = (0,id_ro) 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_AUTRE_ROLE);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, 0 /*id_cat=0*/, Macro.CATEGORIE_USERDMSP, 
				dg.first_ro /*id_ro*/,	Macro.ACTEUR_ROLE, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 3- EP_PATIENT_INSERT_USER_USER : (id_cat,id_actor) = (id_us,id_us) 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_USER_USER);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_us /*id_us*/, Macro.CATEGORIE_USERDMSP, 
				dg.first_us+1 /*id_us*/,	Macro.ACTEUR_USERDMSP, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 4- EP_PATIENT_INSERT_USER_ROLE : (id_cat,id_actor) = (id_us,id_ro) 
		// prepare statement EP_PATIENT_INSERT_USER_USER:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_USER_ROLE);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_us /*id_us*/, Macro.CATEGORIE_USERDMSP, 
				dg.first_ro /*id_ro*/,	Macro.ACTEUR_ROLE, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 5- EP_PATIENT_INSERT_FORMULAIRE_USER : (id_cat,id_actor) = (id_fo,id_us) 
		// prepare statement EP_PATIENT_INSERT_FORMULAIRE_USER:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_USER);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_fo /*id_fo*/, Macro.CATEGORIE_FORMULAIRE, 
				dg.first_us /*id_us*/,	Macro.ACTEUR_USERDMSP, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 6- EP_PATIENT_INSERT_FORMULAIRE_ROLE : (id_cat,id_actor) = (id_fo,id_ro) 
		// prepare statement EP_PATIENT_INSERT_FORMULAIRE_ROLE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_FORMULAIRE_ROLE);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_fo /*id_fo*/, Macro.CATEGORIE_FORMULAIRE, 
				dg.first_ro /*id_ro*/,	Macro.ACTEUR_ROLE, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 7- EP_PATIENT_INSERT_EPISODE_USER : (id_cat,id_actor) = (id_ep,id_us) 
		// prepare statement EP_PATIENT_INSERT_EPISODE_USER:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_EPISODE_USER);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_ep /*id_ep*/, Macro.CATEGORIE_EPISODE, 
				dg.first_us /*id_us*/,	Macro.ACTEUR_USERDMSP, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;
		// 8- EP_PATIENT_INSERT_EPISODE_ROLE : (id_cat,id_actor) = (id_fo,id_ro) 
		// prepare statement EP_PATIENT_INSERT_EPISODE_ROLE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_INSERT_EPISODE_ROLE);
		Tools_dmsp.Test_PATIENT_INSERT(
				dg.IdGlobal++,	0, 0, 0, dg.first_ep /*id_ep*/, Macro.CATEGORIE_EPISODE, 
				dg.first_ro /*id_ro*/,	Macro.ACTEUR_ROLE, 
				org.inria.jdbc.Connection.ComputeAuthorizationBitsVector('1','1','1','1'), ps);
		nb_mp++;

		/*
		 *  TEST EP_*_SELECT_BY_ID (Synch)
         */
		// EP_EPISODE_SELECT_BY_ID:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		// read no_ep (first_ep), last ep (first_ep+nb_ep), and unqualified ep
		for(i=0;i<2*dg.nb_ep+1;i+=dg.nb_ep){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ep+i, ps);
			if(perf==0){out.println("EP_EPISODE_SELECT_BY_ID -> ID="+(dg.first_ep+i));}
			lireResultSet(rs, out);
		}
		// EP_FORMULAIRE_SELECT_BY_ID:		
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_fo;i+=dg.nb_fo-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_fo+i, ps);
			if(perf==0){out.println("EP_FORMULAIRE_SELECT_BY_ID -> ID="+(dg.first_fo+i));}
			lireResultSet(rs, out);
		}
		// EP_USER_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_us;i+=dg.nb_us-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_us+i, ps);
			if(perf==0){out.println("EP_USER_SELECT_BY_ID -> ID="+(dg.first_us+i));}
			lireResultSet(rs, out);
		}
		// EP_EVENT_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_ev;i+=dg.nb_ev-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev+i, ps);
			if(perf==0){out.println("EP_EVENT_SELECT_BY_ID -> ID="+(dg.first_ev+i));}
			lireResultSet(rs, out);
		}
		// EP_INFO_SELECT_BY_ID
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_in;i+=dg.nb_in-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in+i, ps);
			if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID="+(dg.first_in+i));}
			lireResultSet(rs, out);
		}
		// EP_COMMENT_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		// read no_co (first_co), last co, and unqualified co
		for(i=0;i<2*dg.nb_co+1;i+=dg.nb_co){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co+i, ps);
			if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID="+(dg.first_co+i));}
			lireResultSet(rs, out);
		}
		// EP_ROLE_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_ro;i+=dg.nb_ro-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ro+i, ps);
			if(perf==0){out.println("EP_ROLE_SELECT_BY_ID -> ID="+(dg.first_ro+i));}
			lireResultSet(rs, out);
		}
		// EP_HABILITATION_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_ha;i+=dg.nb_ha-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ha+i, ps);
			if(perf==0){out.println("EP_HABILITATION_SELECT_BY_ID -> ID="+(dg.first_ha+i));}
			lireResultSet(rs, out);
		}
		// EP_PATIENT_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_BY_ID);
		// read first and last, and unqualified
		for(i=0;i<2*nb_mp;i+=nb_mp-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(first_mp+i, ps);
			if(perf==0){out.println("EP_PATIENT_SELECT_BY_ID -> ID="+(first_mp+i));}
			lireResultSet(rs, out);
		}

		/*
		 *  TEST EP_*_UPDATE (Synch)
         */
		// EP_INFO_UPDATE:
		// select first_info
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in, ps);
		if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID=" + dg.first_in);}
		lireResultSet(rs, out);
		// update first_info
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_UPDATE);
		Tools_dmsp.Test_INFO_UPDATE(0, 0, 298, "updated varchar", 25, null, 50, 1, 205, dg.first_in, ps);
		if(perf==0){out.println("EP_INFO_UPDATE -> ID=" + dg.first_in);}
		// select first_info again
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_in, ps);
		if(perf==0){out.println("EP_INFO_SELECT_BY_ID -> ID=" + dg.first_in);}
		lireResultSet(rs, out);

		// EP_COMMENT_UPDATE:
		// select first_co
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co, ps);
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID=" + dg.first_co);}
		lireResultSet(rs, out);
		// update first_co
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_UPDATE);
		Tools_dmsp.Test_COMMENT_UPDATE(0, 0, 299, "updated comment", dg.first_co, ps);
		if(perf==0){out.println("EP_INFO_UPDATE -> ID=" + dg.first_co);}
		// select first_co again
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_co, ps);
		if(perf==0){out.println("EP_COMMENT_SELECT_BY_ID -> ID=" + dg.first_co);}
		lireResultSet(rs, out);

		// EP_ROLE_UPDATE:
		// select first_ro
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ro, ps);
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID -> ID=" + dg.first_ro);}
		lireResultSet(rs, out);
		// update first_ro
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_UPDATE);
		Tools_dmsp.Test_ROLE_UPDATE(0, 0, 300, "updated nom", dg.first_ro, ps);
		if(perf==0){out.println("EP_INFO_UPDATE -> ID=" + dg.first_ro);}
		// select first_ro again
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_BY_ID);
		rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_ro, ps);
		if(perf==0){out.println("EP_ROLE_SELECT_BY_ID -> ID=" + dg.first_ro);}
		lireResultSet(rs, out);

		/*
		 * CONNEXION (UI)
		 * 		EP_USER_SELECT_BY_CERTIF
		 * 		EP_HABILITATION_SELECT_BY_IDUSER
		 */
		// EP_HABILITATION_SELECT_BY_ID 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_HABILITATION_BY_IDUSER);
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_us;i+=dg.nb_us-1){
			rs = Tools_dmsp.Test_SELECT_BY_INT(dg.first_us+i, ps);
			if(perf==0){out.println("EP_HABILITATION_SELECT_BY_IDUSER -> IDUSER="+(dg.first_us+i));}
			lireResultSet(rs, out);
		}
		// EP_USER_SELECT_BY_CERTIF 
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER_BY_CERTIF);
		// init certificate to search:
		for (i=0;i<certificate.length;i++)
			certificate[i] = 'a';
		// read first and last, and unqualified
		for(i=0;i<2*dg.nb_us;i+=dg.nb_us-1){
			certificate[19] += (byte)i;
			String cert = new String(certificate);
			rs = Tools_dmsp.Test_SELECT_BY_STRING(cert, ps);
			if(perf==0){
				out.print("EP_USER_SELECT_BY_CERTIF -> CERTIF=" + cert);
				out.println();
			}
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
		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT info
		if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
		lireResultSet(rs, out);

		//SELECT * from SKT event
		if(perf==0){out.println("-----/////// EP_SKTEVENT_SELECT_STAR /////////---------");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTEVENT_SELECT_STAR);
		lireResultSet(rs, out);

		/*  TEST EP_SELECT_*** (UI):
		 *  	EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE	--> vue chronologique
		 *  	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM --> fiche patient [+ plan d'acc.] + liste passages/ev. importants)
		 *  	EP_QUERY_NOAC_SELECT_USER_BY_ID					--> détail d'un intervenant	
		 *  	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT--> ouverture d'un formulaire
		 *  	EP_QUERY_AC_SELECT_INFO_BY_FILTRE				--> listes à base d'infos (entourage, etc.)
		 *  	EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE			--> liste des intervenants
		 *  	EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE --> calendrier
		 *  	EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE --> traitements en cours
 		 */
		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_EVENT_FORM_USER_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);

		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM = "+dg.first_fo);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT((dg.first_fo), ps), out);

		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_USER_BY_ID");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(dg.first_us, ps), out);

		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_EVENT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev+1, ps), out);

		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_INFO_BY_FILTRE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_INFO_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		if(perf==0){out.println("SELECT_NOAC_USER_BY_INFOLEGALE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_NOAC_SELECT_USER_BY_INFOLEGALE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);

		if(perf==0){out.println("EP_QUERY_NOAC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM_AND_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_INT(dg.first_fo+1/*form*/,0/*filtre*/, ps), out);

		if(perf==0){out.println("EP_QUERY_SELECT_EVENT_INFO_BY_FORM_AND_DATE : fo = " 
				+ (dg.first_fo+1)+", dateFin > 2004-05-04");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_DATE(dg.first_fo+1/*form*/,
				java.sql.Date.valueOf("2004-05-04")/*e.datefin*/, ps), out);

		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE : fo = " 
				+ (dg.first_fo+1)+", dateFin > 2004-05-05");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_BY_FORM_AND_DATE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT_AND_DATE(dg.first_fo+1/*form*/,
				java.sql.Date.valueOf("2004-05-05")/*e.datefin*/, ps), out);

		/*
		 *  TEST EP_*_INSERT (for test: EVENT, COMMENT, INFO)
		 *  NB : Non optimized plan (now in EP_TEST !)
         */
		// EP_EVENT_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_EVENT_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_EVENT_INSERT(0,2,0, dg.first_fo, dg.first_us, 
				dg.no_ep, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-20"), 25, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (EVENT) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// EP_COMMENT_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_COMMENT_INSERT(0,2,0, "comment can be big, but more than 450 bytes can't be written", ps);
		IdG2 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (COMMENT) ID = "+ IdG2);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG2, ps), out);

		// EP_INFO_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_INFO_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_INFO_INSERT(0,2,0, IdG1/*event*/, IdG2/*comment*/,
				"char value", 12345, java.sql.Date.valueOf("2000-10-20"), 
				13/*position*/, 1/*filtre*/, 100, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (INFO) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		/*
		 *  TEST OEP_*_*_INSERT (UI: EVENT_AC, COMMENT_NOAC, INFO_AC)
		 *  NB : OPTIMIZED PLANS 
         */
		// OEP_EVENT_AC_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_EVENT_INSERT(2,0, dg.first_fo, dg.first_us, 
				dg.no_ep, java.sql.Date.valueOf("2000-10-20"), java.sql.Date.valueOf("2000-10-30"),1212, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (EVENT) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// OEP_COMMENT_NOAC_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_COMMENT_NOAC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_COMMENT_INSERT(2,0, "comment can be big, but more than 450 bytes can't be written", ps);
		IdG2 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (COMMENT) ID = "+ IdG2);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG2, ps), out);

		// OEP_INFO_AC_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.OEP_INFO_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_INFO_INSERT(2,0, IdG1/*event*/, IdG2/*comment*/,
				"char value", 12345, java.sql.Date.valueOf("2000-10-20"), 
				13/*position*/, 1/*filtre*/, 100, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (INFO) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		/*
		 *  TEST EP_HABILITATION_INSERT (UI)
         */
		// EP_HABILITATION_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_HABILITATION_NOAC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_HABILITATION_INSERT(0,2,0, dg.first_ro, 
				dg.first_us,ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (HABILITATION) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// CHECK EP_*_SELECT_ON_TSSPT
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EPISODE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_EPISODE_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);
		IdG1 = dg.first_ep /*ep_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE EPISODE: IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_UPDATE);
		res = Tools_dmsp.Test_EPISODE_UPDATE(
				10,
				0,
				10,
				"aa",
				IdG1 /*IdG_user*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE EPISODE: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);
		IdG1 = dg.first_fo /*form_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE FORMULAIRE: IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_UPDATE);
		res = Tools_dmsp.Test_FORMULAIRE_UPDATE(
				10,
				0,
				10,
				"aa",
				2,
				IdG1 /*IdG_user*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE FORMULAIRE: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_USER_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_USER_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);
		IdG1 = dg.first_us /*user_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE USER: IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_UPDATE);
		res = Tools_dmsp.Test_USER_UPDATE(
				10,
				0,
				10,
				"bb",
				2,
				"bb",
				"bb",
				0,
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				"bb",
				0,
				certificate,
				4, 
				IdG1 /*IdG_user*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE USER: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_EVENT_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);
		IdG1 = dg.first_ev /*event_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE EVENT: IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_UPDATE);
		res = Tools_dmsp.Test_EVENT_UPDATE(
				10,
				0,
				10,
				java.sql.Date.valueOf("2009-07-14") /*dateEvent*/,
				java.sql.Date.valueOf("2099-01-01") /*DateFin*/, 1, 
				IdG1 /*IdG_event*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE EVENT: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_INFO_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		if(perf==0){out.println("EP_COMMENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_COMMENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_COMMENT_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_ROLE_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_ROLE_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_HABILITATION_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_HABILITATION_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		if(perf==0){out.println("EP_PATIENT_SELECT_STAR");}
		rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_PATIENT_SELECT_STAR);
		lireResultSet(rs, out);
		if(perf==0){out.println("EP_PATIENT_SELECT_ON_TSSPT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_PATIENT_SELECT_ON_TSSPT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(0, ps), out);

		Save_DBMS_on_disk();
		Shutdown_DBMS();

		// CONNECTION
		String[] authent = new String[2];
		String cur_User = "4";
		String cur_Role = "1";
		authent[0] = cur_User;
		authent[1] = cur_Role;
		out.println("\n\nUserDMSP = " + cur_User + ", with role = " + cur_Role + " connects to the DBMS :\n");
		openConnection(dbmsHost, authent);
		out.println("select e.DateEvent, e.IdGlobal, e.IdForm, e.IdUser, f.Nom " +
				"from event e, formulaire f " +
		"where f.IdGlobal = e.IdForm with Access Grant");

		/*
		 *  TEST EP_EP_QUERY_* WITH Access Control (UI: EVENT, FORM, USER)
         */
		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_FORM_USER_BY_FILTRE);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(1, ps), out);

		/*
		 *  TEST EP_*_INSERT WITH Access Control (UI: EVENT, INFO)
         */
		// EP_EVENT_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.OEP_EVENT_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_EVENT_INSERT(0,2,dg.first_fo, dg.first_us, 
				dg.no_ep, java.sql.Date.valueOf("2000-10-20"), 
				java.sql.Date.valueOf("2000-10-30"), 1212, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (EVENT) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// EP_INFO_INSERT:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.OEP_INFO_AC_INSERT, 
				java.sql.Statement.RETURN_GENERATED_KEYS);
		Tools_dmsp.Test_INFO_INSERT(0,2,IdG1/*event*/, IdG2/*comment*/,
				"char value", 12345, java.sql.Date.valueOf("2000-10-20"), 
				13/*position*/, 1/*filtre*/, 100, ps);
		IdG1 = getIdGlobalGetGeneratedKey(ps.getGeneratedKeys());
		if(perf==0){out.println("GET GENERATED (INFO) ID = "+ IdG1);}
		// CHECK INSERTION:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// QUERY WITH AC
		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM = "+dg.first_fo);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_FORM);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT((dg.first_fo), ps), out);

		if(perf==0){out.println("EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_TEST.EP_QUERY_AC_SELECT_EVENT_INFO_COMMENT_BY_EVENT);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(dg.first_ev+1, ps), out);

		/*
		 *  TEST *_UPDATE_BY_ID (UI) *=EVENT_AC,INFO_AC,COMMENT_NOAC
         */
		// EVENT_UPDATE:
	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		IdG1 = dg.first_ev /*event_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE EVENT: TS_SPT = "+ts_spt+", IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.EVENT_AC_UPDATE_BY_ID);
		res = Tools_dmsp.Test_EVENT_UPDATE(ts_spt, 
				java.sql.Date.valueOf("2009-07-14") /*dateEvent*/,
				java.sql.Date.valueOf("2099-01-01") /*DateFin*/, 1, 
				IdG1 /*IdG_event*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE EVENT: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// INFO_UPDATE:
	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		IdG1 = dg.first_in /*info_id*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE INFO: TS_SPT = "+ts_spt+", IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.INFO_AC_UPDATE_BY_ID);
		res = Tools_dmsp.Test_INFO_UPDATE(ts_spt, "updatedchar" /*valchar*/, 555 /*valNum*/, 
				java.sql.Date.valueOf("3000-01-01") /*ValDate*/, 0,  IdG1 /*IdG_info*/,ps); 
		if(perf==0){out.println("-- AFTER UPDATE INFO: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		// COMMENT_UPDATE:
	    ts_spt = ((org.inria.jdbc.Connection) db).getGlobalTimestamp();
		IdG1 = dg.first_co+1 /*info_co*/;
		// BEFORE UPDATE:
		if(perf==0){out.println("-- BEFORE UPDATE COMMENT: TS_SPT = "+ts_spt+", IdG="+IdG1);}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_UI.COMMENT_AC_UPDATE_BY_ID);
		// params: 1=49(TSSPT), 2=51(ValComment), 3=47(IdGlobal)
		res = Tools_dmsp.Test_COMMENT_UPDATE(ts_spt, 
				"comment after update containing something" /*valchar*/, 
				IdG1 /*IdG_info*/, ps); 
		if(perf==0){out.println("-- AFTER UPDATE COMMENT: IdG="+IdG1+" - "+res+" tuple(s) updated ");}
		// CHECK UPDATE:
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_SELECT_BY_ID);
		lireResultSet(Tools_dmsp.Test_SELECT_BY_INT(IdG1, ps), out);

		/*
		 * Commit and clean exit
		 */
		Save_DBMS_on_disk();
		Desinstall_DBMS_MetaData();
		Shutdown_DBMS();
	}
}
