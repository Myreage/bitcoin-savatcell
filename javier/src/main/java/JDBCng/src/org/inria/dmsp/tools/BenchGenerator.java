package org.inria.dmsp.tools;

import java.io.PrintWriter;
import java.util.Calendar;

public class BenchGenerator {

	private PrintWriter out;
	public int perf;
	// store the first IdGlobal of each table:
	public int first_us, first_ro, first_ha, first_fo, first_mu, first_ep, 
		first_ev, first_co, first_in; 
	// store the current IdGlobal
	public int IdGlobal;
	// null comment and episode
	public int no_ep, no_co;
	// number of tuples
	public int nb_us, nb_ro, nb_ha, nb_ep;
	// number of small forms ("Fiche de liaison")
	public int nb_sform;
	// number of big forms ("GIR m\u00E9dical")
	public int nb_bform;
	// temperate IdGlobal
	private int tmp_ev, tmp_co;

	public BenchGenerator(PrintWriter out) {
		super();
		this.out = out;
		this.perf=0;
	}

	public BenchGenerator(PrintWriter out, int perf) {
		super();
		this.out = out;
		this.perf=perf;
	}
	/*
	 * Generate random tuples for tables user, role, habilitation and episode. 
	 * Number of tuples for each is given by nb_us, nb_ro, nb_ha and nb_ep.
	 * Generate tuples for tables formulaire, event, info and comment.
	 * Tuples for each tables is produced by the following principle:
	 * 1) only two tuples in formulaire: "Fiche de liaison" and "GIR m\u00E9dical",
	 *    representing small forms and big forms respectively.
	 * 2) the first tuple of table comment is "no comment".
	 * 3) each small form produce 1 tuple for the table event, 5 tuples for info, and 
	 * 	  1 tuples for comment.
	 * 4) each big form produce 1 tuple for the table event, 30 tuples for info, and 
	 *    16 tuples for comment.
	 * */
	public int INSERT_Data_Generated(
			int nb_us, int nb_ro, int nb_ha, int nb_ep,
			int nb_sform, int nb_bform, java.sql.Connection db) 
	throws Exception {
		
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		byte cc [] = new byte[60]; // to generate ValComment for table comment
		byte ci [] = new byte[30]; // to generate ValChar for table INFO
		byte certificate[] = new byte[20];
		
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// for loops:
		int i, j;
		// temp variables
		int t2, t3;
		
		// set number of tuples:
		this.nb_us=nb_us; this.nb_ro=nb_ro; this.nb_ha=nb_ha; this.nb_ep=nb_ep;
		this.nb_sform = nb_sform; this.nb_bform = nb_bform;
		// set idglobal
		IdGlobal = 1;

		if(perf==0){out.println("Generating data...");}

		/*
		 *  INSERT INTO ROLE 
		 */
		if(perf==0){out.println("// Insertion dans la table ROLE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_ROLE_INSERT);
		c[0]='a'; c[1]='a';	
		first_ro = IdGlobal;
		for(i=0; i<nb_ro; i++){
			if(i%32==0){c[0]='a';}
			c[0]++; c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_ROLE_INSERT(IdGlobal++,0,2,0,s,ps);
		}		

		/*
		 *  INSERT INTO USER 
		 */
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_INSERT);
		c[0]='a'; c[1]='a';	
		for (i=0;i<certificate.length;i++)
			certificate[i] = 'a';
		first_us = IdGlobal;
		for(i=0; i<nb_us; i++){
			if(i%32==0){c[0]='a';certificate[certificate.length-1]='a';}
			c[1]=c[0];
			String s = new String (c);
			String cert = new String(certificate);
 			Tools_dmsp.Test_USER_INSERT(IdGlobal++, 0,2,0, 
 				s,1,s,s,1,s,s,s,s,s,s,s,1,cert,first_ro+i%nb_ro,ps);
			certificate[certificate.length-1]++;
			c[0]++;
		}

		/*
		 *  INSERT INTO HABILITATION 
		 */
		first_ha = IdGlobal;
		if(perf==0){out.println("// Insertion dans la table HABILITATION ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_HABILITATION_INSERT);
		first_ha = IdGlobal;
		for(i=0; i<nb_ha; i++){
			Tools_dmsp.Test_HABILITATION_INSERT(
 				IdGlobal++, 0,0,0, first_ro+i%nb_ro, first_us+i%nb_us,ps);
		}

		/*
		 *  INSERT INTO FORMULAIRE 
		 */
		if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_INSERT);
		c[0]='a'; c[1]='a';	
		first_fo = IdGlobal;
		// insert the first tuple with Nom = "fiche de liaison"
		Tools_dmsp.Test_FORMULAIRE_INSERT(
				IdGlobal++, 0,2,0, "fiche de liaison", 1/*filtre*/, ps);
		// insert the second tuple with Nom = "GIR medical"
		Tools_dmsp.Test_FORMULAIRE_INSERT(
				IdGlobal++, 0,2,0, "GIR m\u00E9dical", 1/*filtre*/, ps);
	
		/*
		 *  INSERT INTO EPISODE
		 */
		if(perf==0){out.println("// Insertion dans la table EPISODE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_INSERT);
		c[0]='a'; c[1]='a';	
		first_ep = IdGlobal;
		no_ep = first_ep;
		Tools_dmsp.Test_EPISODE_INSERT(
				IdGlobal++,	0,2,0, "NULL", ps);
		for(i=0; i<nb_ep; i++){
			if(i%24==0){c[0]='a';}
			c[1]=c[0]; String s = new String (c);
			c[0]++;
			Tools_dmsp.Test_EPISODE_INSERT(
				IdGlobal++,	0,2,0, s, ps);
		}

		/*
		 *  INSERT INTO EVENT, INFO and COMMENT
		 */
		if(perf==0){out.println("// Insertion dans les tables EVENT, INFO and COMMENT");}
		// the first comment is NULL
		first_co = IdGlobal;
		no_co = first_co;
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
		Tools_dmsp.Test_COMMENT_INSERT(
				IdGlobal++, 0,0,0, "NULL", ps);
		//initialize the value of the comment
		for(i=0; i<cc.length; i++)
			cc[i] = 'a';
		
		first_ev = IdGlobal;
		first_in = first_ev + 2; // only 1 event and 1 comment is produced before the first info
		
		String date;
		for(i=0; i<nb_sform; i++){
			// a small form is generated
			// insert 1 tuple into EVENT
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT);
			tmp_ev = IdGlobal;
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			Tools_dmsp.Test_EVENT_INSERT(
					IdGlobal++,	0,0,0, first_fo/*fiche de liaison*/, first_us+i%nb_us, no_ep, 
					java.sql.Date.valueOf(date), java.sql.Date.valueOf(date), 12, ps);
			// insert 1 tuple into COMMENT
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
			tmp_co = IdGlobal;
			String s = new String(cc);
			if(cc[0]=='z'){
				for(j=0; j<cc.length; j++)
					cc[j] = 'a';
			} else {
				for(j=0; j<cc.length; j++)
					cc[j]++;
			}
			Tools_dmsp.Test_COMMENT_INSERT(
					IdGlobal++, 0,0,0, s, ps);
			// insert 6 tuples into INFO
			ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT);
			t3=0; // current value of field info.position 
			for(j=0; j<6; j++){
				// info.valdate
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, 2000+j%2);
				cal.set(Calendar.MONTH, j%12+1);
				cal.set(Calendar.DAY_OF_MONTH, j%20+1);
				// at each (nb_in/nb_co) infos:
				if(j==0){
					t2 = tmp_co; // link info to the last comment
				} else t2 = no_co; // link info to the comment no_comment
				if(j%24==0){ci[0]='a';}else{ci[0]++;}
				for(int k=1; k<ci.length; k++){
					ci[k] = ci[0];
				}
				s = new String (ci);
				Tools_dmsp.Test_INFO_INSERT(
					IdGlobal++,	0,0,0, tmp_ev/*event*/, t2 /*comment*/,s,i,
					new java.sql.Date(cal.getTime().getTime()),	
					t3++ /*position*/, 1/*filtre*/, j, ps);
			}
			
			// one big form is generated after several small forms
			if(nb_bform !=0){
				if(i%(nb_sform/nb_bform)==0){
					//insert 1 tuple into EVENT
					ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT);
					tmp_ev = IdGlobal;
					date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
					Tools_dmsp.Test_EVENT_INSERT(
							IdGlobal++,	0,0,0, first_fo+1/*GIR medical*/, first_us+i%nb_us, no_ep, 
							java.sql.Date.valueOf(date), java.sql.Date.valueOf(date), 12, ps);
					// insert 24 tuples into COMMENT
					ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
					tmp_co = IdGlobal;
					for(int k =0; k<24; k++){
						s = new String(cc);
						if(cc[0]=='z'){
							for(j=0; j<cc.length; j++)
								cc[j] = 'a';
						} else {
							for(j=0; j<cc.length; j++)
								cc[j]++;
						}
						Tools_dmsp.Test_COMMENT_INSERT(
								IdGlobal++, 0,0,0, s, ps);
					}
					// insert 73 tuples into INFO
					ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT);
					for(j=0; j<73; j++){
						// info.valdate
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.YEAR, 2000+j%2);
						cal.set(Calendar.MONTH, j%12+1);
						cal.set(Calendar.DAY_OF_MONTH, j%20+1);
						// 24 tuples are linked to newly inserted comments:
						if(j>=24){
							t2 = no_co; // link info to the comment no_comment
						} else t2 = tmp_co++; // link info to one inserted comment
						if(j%24==0){ci[0]='a';}else{ci[0]++;}
						for(int k=1; k<ci.length; k++){
							ci[k] = ci[0];
						}
						s = new String (ci);
						Tools_dmsp.Test_INFO_INSERT(
							IdGlobal++,	0,0,0, tmp_ev/*event*/, t2 /*comment*/,s,i,
							new java.sql.Date(cal.getTime().getTime()),	
							t3++ /*position*/, 1/*filtre*/, j, ps);
					}
				}
			}
		}
		

		return IdGlobal;
	}
	
}
