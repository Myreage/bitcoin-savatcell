package org.inria.dmsp.tools;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

import org.inria.dmsp.tools.Tools_dmsp;

public class DataGenerator_2 {

	private PrintWriter out;
	public int perf;
	// store the first IdGlobal of each table:
	public int first_us, first_ro, first_ha, first_fo, first_mu, first_ep,
		first_ev, first_co, first_in;
	// store the current IdGlobal
	public int IdGlobal = 1;
	// null comment and episode
	public int no_ep, no_co;
	// number of tuples
	public int nb_us, nb_ro, nb_ha, nb_fo, nb_ep, nb_ev, nb_co, nb_in;

	public DataGenerator_2(PrintWriter out) {
		super();
		this.out = out;
		this.perf=0;
	}

	public DataGenerator_2(PrintWriter out, int perf) {
		super();
		this.out = out;
		this.perf = perf;
	}

	/*
	 * scale: scale factor for the number of tuples
	 */
	public int INSERT_Data_Generated(short scale, boolean index, java.sql.Connection db)
			throws Exception
	{
		int factor = 8*scale;
		return INSERT_Data_Generated(
				20,			/* users */
				5,			/* roles */
				30,			/* habilitations */
				1*factor,	/* formulaires */
				10,			/* episodes */
				10*factor,	/* events */
				30*factor,	/* comments */
				100*factor,	/* infos */
				false,		/* generic, no dmsp */
				index, db );
	}

	/*
	 * compatibility layer not to break existing tests
	 */
	//XXX
	public int INSERT_Data_Generated(
			int nb_us, int nb_ro, int nb_ha, int nb_fo, int nb_ep,
			int nb_ev, int nb_co, int nb_in, java.sql.Connection db) throws Exception
	{
		return INSERT_Data_Generated(
				nb_us, nb_ro, nb_ha, nb_fo, nb_ep, nb_ev, nb_co, nb_in,
				true /* dmsp */, false /* no CI */, db
				);
	}

	/*
	 * nb_* : number of tuples to insert
	 * dmsp : if true, insert them the "dmsp way" (e.g. many consecutive events/comments belong to the same info)
	 * index: if true, use INSERT EP with climbing indexes
	 */
	public int INSERT_Data_Generated(
			int nb_us, int nb_ro, int nb_ha, int nb_fo, int nb_ep,
			int nb_ev, int nb_co, int nb_in, boolean dmsp, boolean index,
			java.sql.Connection db) throws Exception
	{
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		byte certificate[] = new byte[20];
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ;
		int i, j, k, l,	// for loops
			t1, t2, t3;		// temp variables

		// set number of tuples:
		this.nb_us=nb_us; this.nb_ro=nb_ro; this.nb_ha=nb_ha; this.nb_fo=nb_fo;
		this.nb_ep=nb_ep; this.nb_ev=nb_ev; this.nb_co=nb_co; this.nb_in=nb_in;

		if(perf==0){out.println("Data generated");}
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
		// the resultset used for the queries:
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_ROLE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
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
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_USER_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
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
		// the resultset used for the queries:
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_HABILITATION_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO FORMULAIRE
		 */
		if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_FORMULAIRE_INSERT);
		c[0]='a'; c[1]='a';
		if (!dmsp) {IdGlobal+=945;} // Pour arriver à 1001 comme dans le DeltaLoader.
		first_fo = IdGlobal;
		for(i=0; i<nb_fo; i++){
			if(i%32==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_FORMULAIRE_INSERT(
				IdGlobal++, 0,2,0, s, i%2/*filtre*/, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EPISODE
		 */
		if(perf==0){out.println("// Insertion dans la table EPISODE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_EPISODE_INSERT);
		c[0]='a'; c[1]='a';
		first_ep = IdGlobal;
		for(i=0; i<nb_ep+1; i++){
			if(dmsp){if(i==0) no_ep = IdGlobal;}
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_EPISODE_INSERT(
				IdGlobal++,	0,2,0, s, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EPISODE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EVENT
		 */
		if(perf==0){out.println("// Insertion dans la table EVENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(index?DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT_SIGMOD:DMSP_QEP_IDs.EP_Synchro.EP_EVENT_INSERT);
		first_ev = IdGlobal;
		String date;
		j=0; l=1;
		if (!dmsp) {
		Tools_dmsp.Test_EVENT_INSERT(
				99996,	0,0,0, first_fo, first_us+i%nb_us, first_ep+i%nb_ep, java.sql.Date.valueOf("2010-06-06"),
				java.sql.Date.valueOf("2010-06-06"), 12, ps);
		}
		for(i=0; i<nb_ev; i++){
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			
			
			if (dmsp)
			{
				k = first_fo + i % 3; /* why 3? Dunno. */
			}
			else
			{
				/* don't do round-robin on forms for non-dmsp */
				k = first_fo + j;
				if( j == nb_fo-l ){
					j = 0;
					l++;
				}
				else
					j++;
				if (l==nb_fo)
					l = 1;
			}
			
			Tools_dmsp.Test_EVENT_INSERT(
				IdGlobal++,	0,0,0, k, first_us+i%nb_us,
				(dmsp? no_ep : first_ep+i%nb_ep), java.sql.Date.valueOf(date), java.sql.Date.valueOf(date),
				12, ps);
		}
		if (!dmsp) {
			Tools_dmsp.Test_EVENT_INSERT(
					99994,	0,0,0, first_fo, first_us+i%nb_us, first_ep+i%nb_ep, java.sql.Date.valueOf("2010-06-06"),
					java.sql.Date.valueOf("2010-06-06"), 12, ps);
			}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EVENT_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO COMMENT
		 */
		if(perf==0){out.println("// Insertion dans la table COMMENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_COMMENT_INSERT);
		c[0]='a'; c[1]='a';
		first_co = IdGlobal;
		if (!dmsp) {Tools_dmsp.Test_COMMENT_INSERT(99997, 0,0,0, "plouf", ps);}
		for(i=0; i<nb_co+1; i++){
			if(dmsp){if(i==0) no_co = IdGlobal;}
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_COMMENT_INSERT(
				IdGlobal++, 0,0,0, s, ps);
		}
		if (!dmsp) {Tools_dmsp.Test_COMMENT_INSERT(99995, 0,0,0, "plouf", ps);}
		/*
		 *  INSERT INTO INFO
		 */
		if(perf==0){out.println("// Insertion dans la table INFO ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(index?DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT_SIGMOD:DMSP_QEP_IDs.EP_Synchro.EP_INFO_INSERT);
		first_in = IdGlobal;
		c[0]='a'; c[1]='a';
		// init idglobals of event and comment:
		t1=first_ev; // first event
		if(dmsp){t2=first_co+1%nb_co;} // first comment (if any), otherwise no_comment
		t3=0; // current value of field info.position
		for(i=1; i<=nb_in; i++){
			// info.valdate
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2000+i%2);
			cal.set(Calendar.MONTH, i%12+1);
			cal.set(Calendar.DAY_OF_MONTH, i%20+1);
			if (dmsp)
			{
				// at each (nb_in/nb_co) infos:
				if((i+1)%(nb_in/nb_co)==0){
					t2=first_co+(i/(nb_in/nb_co))%(nb_co); // link info to the next comment
				} else t2=no_co; // link info to the comment no_comment
			}
			else
				t2=first_co+i%nb_co; // link info to the next comment

			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_INFO_INSERT(
				IdGlobal++,	0,0,0, t1 /*event*/, t2 /*comment*/,s,i,
				new java.sql.Date(cal.getTime().getTime()),
				t3++ /*position*/, i%2/*filtre*/, i, ps);
			if (dmsp)
			{
				// at each (nb_in/nb_ev) infos:
				if(i%(nb_in/nb_ev)==0) {
					t1++; // increment idglobal of event
					t3=0; // reset info.position
				}
			}
			else
				t1 = first_ev+i%nb_ev; // increment idglobal of event
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_INFO_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		return IdGlobal;
	}
	
	/*
	 * nb_* : number of tuples to insert
	 */
	public int PDS_INSERT_Data_Generated(
			int nb_us, int nb_ro, int nb_ha, int nb_fo, int nb_ep,
			int nb_ev, int nb_co, int nb_in, java.sql.Connection db) throws Exception
	{
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		byte certificate[] = new byte[20];
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ;
		int i;	// for loops
		Random r = new Random(0); // we don't care about the randomness, only the reproducibility

		// set number of tuples:
		this.nb_us=nb_us; this.nb_ro=nb_ro; this.nb_ha=nb_ha; this.nb_fo=nb_fo;
		this.nb_ep=nb_ep; this.nb_ev=nb_ev; this.nb_co=nb_co; this.nb_in=nb_in;

		if(perf==0){out.println("Data generated");}
		/*
		 *  INSERT INTO ROLE
		 */
		if(perf==0){out.println("// Insertion dans la table ROLE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_ROLE_INSERT);
		c[0]='a'; c[1]='a';
		first_ro = IdGlobal;
		for(i=0; i<nb_ro; i++){
			if(i%32==0){c[0]='a';}
			c[0]++; c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_ROLE_INSERT(IdGlobal++,0,2,0,s,ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_ROLE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO USER
		 */
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_USER_INSERT);
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
 				s,1,s,s,1,s,s,s,s,s,s,s,1,cert,first_ro+r.nextInt(nb_ro),ps);
			certificate[certificate.length-1]++;
			c[0]++;
		}
/*
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_USER_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO HABILITATION
		 */
		first_ha = IdGlobal;
		if(perf==0){out.println("// Insertion dans la table HABILITATION ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_HABILITATION_INSERT);
		first_ha = IdGlobal;
		for(i=0; i<nb_ha; i++){
			Tools_dmsp.Test_HABILITATION_INSERT(
 				IdGlobal++, 0,0,0, first_ro+r.nextInt(nb_ro), first_us+r.nextInt(nb_us),ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_HABILITATION_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO FORMULAIRE
		 */
		if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_FORMULAIRE_INSERT);
		c[0]='a'; c[1]='a';
		first_fo = IdGlobal;
		for(i=0; i<nb_fo; i++){
			if(i%32==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_FORMULAIRE_INSERT(
				IdGlobal++, 0,2,0, s, i%2/*filtre*/, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EPISODE
		 */
		if(perf==0){out.println("// Insertion dans la table EPISODE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EPISODE_INSERT);
		c[0]='a'; c[1]='a';
		first_ep = IdGlobal;
		for(i=0; i<nb_ep; i++){
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_EPISODE_INSERT(
				IdGlobal++,	0,2,0, s, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EPISODE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EVENT
		 */
		if(perf==0){out.println("// Insertion dans la table EVENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EVENT_INSERT);
		first_ev = IdGlobal;
		String date;
		for(i=0; i<nb_ev; i++){
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			
			Tools_dmsp.Test_EVENT_INSERT(
				IdGlobal++,	0,0,0, first_fo+r.nextInt(nb_fo), first_us+r.nextInt(nb_us), first_ep+r.nextInt(nb_ep), java.sql.Date.valueOf(date),
				java.sql.Date.valueOf(date), 12, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EVENT_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO COMMENT
		 */
		if(perf==0){out.println("// Insertion dans la table COMMENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_COMMENT_INSERT);
		c[0]='a'; c[1]='a';
		first_co = IdGlobal;
		for(i=0; i<nb_co; i++){
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_COMMENT_INSERT(
				IdGlobal++, 0,0,0, s, ps);
		}
		/*
		 *  INSERT INTO INFO
		 */
		if(perf==0){out.println("// Insertion dans la table INFO ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_INFO_INSERT);
		first_in = IdGlobal;
		c[0]='a'; c[1]='a';
		for(i=1; i<=nb_in; i++){
			// info.valdate
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2000+i%2);
			cal.set(Calendar.MONTH, i%12+1);
			cal.set(Calendar.DAY_OF_MONTH, i%20+1);

			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_INFO_INSERT(
				IdGlobal++,	0,0,0, first_ev+r.nextInt(nb_ev), first_co+r.nextInt(nb_co),
				s, i, new java.sql.Date(cal.getTime().getTime()),
				i%3 /*position*/, i%2 /*filtre*/, i, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_INFO_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_INFO_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		return IdGlobal;
	}
	
	/*
	 * nb_* : number of tuples to insert
	 * sort the 'links' array elements before passing them here (beware, java.util.Arrays isn't available on the javacard!
	 */
	public int HybridSkip_INSERT_Data_Generated(int nb_ev, int[] links, java.sql.Connection db) throws Exception
	{
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		byte certificate[] = new byte[20];
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ;
		int i, j, special_fo, id_fo;	// for loops
		boolean found;
		Random r = new Random(0); // we don't care about the randomness, only the reproducibility

		// set number of tuples:
		this.nb_us=3; this.nb_ro=2; this.nb_ha=5;
		this.nb_fo=10; this.nb_ep=10; this.nb_ev=nb_ev;

		if(perf==0){out.println("Data generated");}
		/*
		 *  INSERT INTO ROLE
		 */
		if(perf==0){out.println("// Insertion dans la table ROLE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_ROLE_INSERT);
		c[0]='a'; c[1]='a';
		first_ro = IdGlobal;
		for(i=0; i<this.nb_ro; i++){
			if(i%32==0){c[0]='a';}
			c[0]++; c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_ROLE_INSERT(IdGlobal++,0,2,0,s,ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_ROLE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_ROLE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO USER
		 */
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_USER_INSERT);
		c[0]='a'; c[1]='a';
		for (i=0;i<certificate.length;i++)
			certificate[i] = 'a';
		first_us = IdGlobal;
		for(i=0; i<this.nb_us; i++){
			if(i%32==0){c[0]='a';certificate[certificate.length-1]='a';}
			c[1]=c[0];
			String s = new String (c);
			String cert = new String(certificate);
 			Tools_dmsp.Test_USER_INSERT(IdGlobal++, 0,2,0,
 				s,1,s,s,1,s,s,s,s,s,s,s,1,cert,first_ro+r.nextInt(this.nb_ro),ps);
			certificate[certificate.length-1]++;
			c[0]++;
		}
/*
		if(perf==0){out.println("EP_USER_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_USER_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO HABILITATION
		 */
		first_ha = IdGlobal;
		if(perf==0){out.println("// Insertion dans la table HABILITATION ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_HABILITATION_INSERT);
		first_ha = IdGlobal;
		for(i=0; i<this.nb_ha; i++){
			Tools_dmsp.Test_HABILITATION_INSERT(
 				IdGlobal++, 0,0,0, first_ro+r.nextInt(this.nb_ro), first_us+r.nextInt(this.nb_us),ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_HABILITATION_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_HABILITATION_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO FORMULAIRE
		 */
		if(perf==0){out.println("// Insertion dans la table FORMULAIRE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_FORMULAIRE_INSERT);
		c[0]='a'; c[1]='a';
		first_fo = IdGlobal;
		for(i=0; i<this.nb_fo; i++){
			if(i%32==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_FORMULAIRE_INSERT(
				IdGlobal++, 0,2,0, s, i%2/*filtre*/, ps);
		}
		
		// special value to test
		special_fo = first_fo+r.nextInt(this.nb_fo);
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_FORMULAIRE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_FORMULAIRE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EPISODE
		 */
		if(perf==0){out.println("// Insertion dans la table EPISODE ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EPISODE_INSERT);
		c[0]='a'; c[1]='a';
		first_ep = IdGlobal;
		for(i=0; i<this.nb_ep; i++){
			if(i%24==0){c[0]='a';}
			c[0]++;c[1]=c[0]; String s = new String (c);
			Tools_dmsp.Test_EPISODE_INSERT(
				IdGlobal++,	0,2,0, s, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EPISODE_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EPISODE_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		/*
		 *  INSERT INTO EVENT
		 */
		if(perf==0){out.println("// Insertion dans la table EVENT ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_PDS.EP_EVENT_INSERT);
		first_ev = IdGlobal;
		String date;
		for(i=0; i<nb_ev; i++){
			date = (2000+i%10)+"-"+((i%12+1)<10?"0":"")+(i%12+1)+"-"+((i%30+1)<10?"0":"")+(i%30+1);
			
			found = false;
			for(j=0; j < links.length && !found; j++)
			{
				found = (links[j] == i);
			}
			if(found){
				// found it
				id_fo = special_fo;
			}else{
				// ensure we don't use special_fo
				do{
					id_fo = first_fo+r.nextInt(this.nb_fo);
				}while(id_fo == special_fo);
			}
			
			Tools_dmsp.Test_EVENT_INSERT(
				IdGlobal++,	0,0,0, id_fo, first_us+r.nextInt(this.nb_us), first_ep+r.nextInt(this.nb_ep), java.sql.Date.valueOf(date),
				java.sql.Date.valueOf(date), 12, ps);
		}
/*
		// the resultset used for the queries:
		if(perf==0){out.println("EP_EVENT_SELECT_STAR");}
		rs = st.executeQuery(EP_TEST.EP_EVENT_SELECT_STAR);
		Tools.lireResultSet(rs, out);
*/
		return IdGlobal;
	}
}
