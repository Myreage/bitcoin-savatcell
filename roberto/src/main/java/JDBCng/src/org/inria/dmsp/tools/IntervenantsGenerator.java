package org.inria.dmsp.tools;

import java.io.PrintWriter;

public class IntervenantsGenerator {

	private PrintWriter out;
	public int perf;
	// store the current IdGlobal
	public int IdGlobal;
	// number of intervenors
	public int nb_interv;
	public int role_Id;

	public IntervenantsGenerator(PrintWriter out) {
		super();
		this.out = out;
		this.perf=0;
	}

	public IntervenantsGenerator(PrintWriter out, int perf) {
		super();
		this.out = out;
		this.perf=perf;
	}
	/*
	 * Generate fake tuples for table UserDMSP starting by a given IdGlobal. 
	 * Number of tuples is given by nb_interv.
	 * 
	 * */
	public int INSERT_Data_Generated(
			int nb_firstIdGlobal, int nb_interv, java.sql.Connection db) 
	throws Exception {
		
		// to build the value of the strings to insert:
		byte c [] = new byte[2];
		byte certificate[] = new byte[20];
		
		// the prepared statement used for all inserts:
		java.sql.PreparedStatement ps ; 
		// for loops:
		int i;
		
		// set number of tuples:
		this.nb_interv = nb_interv;
		// set the role: fake role
		this.role_Id = 0;
		// set idglobal
		IdGlobal = nb_firstIdGlobal;

		if(perf==0){out.println("Generating data...");}

		/*
		 *  INSERT INTO USER 
		 */
		if(perf==0){out.println("// Insertion dans la table USERDMSP ");}
		ps = ((org.inria.jdbc.Connection)db).prepareStatement(DMSP_QEP_IDs.EP_Synchro.EP_USER_INSERT);
		c[0]='a'; c[1]='a';	
		for (i=0;i<certificate.length;i++)
			certificate[i] = 'a';
		for(i=0; i<nb_interv; i++){
			if(i%26==0){c[0]='a';certificate[certificate.length-1]='a';}
			c[1]=c[0];
			String s = new String (c);
			String cert = new String(certificate);
 			Tools_dmsp.Test_USER_INSERT(IdGlobal++, 0,2,0, 
 				s,1,s,s,1,s,s,s,s,s,s,s,1,cert,role_Id,ps);
			certificate[certificate.length-1]++;
			c[0]++;
		}

		return IdGlobal;
	}
	
}
