package test.jdbc.INRIA_22_Recovery;

/**
 * Tests wiping the NOR memory of the token
 *
 * @author Quentin Lefebvre
 * @date 2014/06/17
 */

import java.io.PrintWriter;

import org.inria.database.QEPng;
import org.inria.dmsp.tools.DMSP_QEP_IDs;
import org.inria.dmsp.tools.DataGenerator_2;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.DataGeneratorWithIds;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {

  @Override
  public void run(PrintWriter out, String dbmsHost) throws Exception {
    this.out = out;
    init();
    openConnection(dbmsHost, null);
    Desinstall_DBMS_MetaData();

    // ********** 1st backup: database empty, no schema loaded ***************
    int nbNANDPackets0 = Recovery_getTotalNumberOfNANDPacketsToBackup();
	out.println(nbNANDPackets0 + " NAND packets to backup after erasing the database");
	int nbNORPackets0 = Recovery_getTotalNumberOfNORPacketsToBackup();
	out.println(nbNORPackets0 + " NOR packets to backup after erasing the database");
	int nbRamPPackets0 = Recovery_getTotalNumberOfRamPPacketsToBackup();
	out.println(nbRamPPackets0 + " RamP packets to backup after erasing the database");
	int nbFTLPackets0 = Recovery_getTotalNumberOfFTLPacketsToBackup();
	out.println(nbFTLPackets0 + " FTL packets to backup after inserting data in the database");
	
	byte[][] norPackets0 = new byte[nbNORPackets0][];
	byte[][] nandPackets0 = new byte[nbNANDPackets0][];
	byte[][] RamPPackets0 = new byte[nbRamPPackets0][];
	byte[][] FTLPackets0 = new byte[nbFTLPackets0][];

	out.println("FIRST backup -> database is empty (state 0)");
	byte res0 = Recovery_InitBackup();
	if( res0 == 0 )
	{
		for(int i = 0 ; i < nbNORPackets0 ; i++)
			norPackets0[i] = Recovery_getNextNORPacketToBackup();
		for(int i = 0 ; i < nbNANDPackets0 ; i++)
			nandPackets0[i] = Recovery_getNextNANDPacketToBackup();
		for(int i = 0 ; i < nbRamPPackets0 ; i++)
			RamPPackets0[i] = Recovery_getNextRamPPacketToBackup();
		for(int i = 0 ; i < nbFTLPackets0 ; i++)
			FTLPackets0[i] = Recovery_getNextFTLPacketToBackup();
		Recovery_FinalizeBackup();
		out.println("FIRST backup completed");
	}

	String schema = "schemaV4.rsc";
    Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
    byte[] schemaDesc = t.loadSchema(schema);
    int usedId = 404;
    Install_DBMS_MetaData(schemaDesc, usedId);
    
	// load and install QEPs
    Class<?>[] executionPlans = new Class[] { org.inria.dmsp.EP_Synchro.class, org.inria.dmsp.EP_Debug.class,
			org.inria.dmsp.EP_IDX.class, org.inria.dmsp.EP_PDS_FIS.class, org.inria.dmsp.EP_PDS_SS.class, org.inria.dmsp.EP_PDS_TEST.class,
			org.inria.dmsp.EP_PDS.class, org.inria.dmsp.EP_UI.class, org.inria.dmsp.schema.EP_TEST.class,
			test.jdbc.schemaIndexInfo.Execution_Plan.class, org.inria.dmsp.EP_BLOB.class, org.inria.dmsp.EP_string_column.class };
	QEPng.loadExecutionPlans( org.inria.dmsp.tools.DMSP_QEP_IDs.class, executionPlans );
	QEPng.installExecutionPlans( db );

    DataGeneratorWithIds dgwi = new DataGeneratorWithIds(out);
	dgwi.INSERT_Data_Generated(
			5, // nbUser
			3, // nbRole
			7, // nbHab
			20, // nbForm
			0, // nbMatriceDMSP
			1, // nbEpisode
			10, // nbEvent
			309, // nbConcept
			50, // nbComment
			100,// nbInfo
			db,
			1); // MatPatGenerated

	Save_DBMS_on_disk();	
	dgwi.SCE_Test_DB_NOAC(db, out);
	
    // ********** 2nd backup: database filled (state 1) ***************
	int nbNANDPackets1 = Recovery_getTotalNumberOfNANDPacketsToBackup();
	out.println(nbNANDPackets1 + " NAND packets to backup after inserting data in the database");
	int nbNORPackets1 = Recovery_getTotalNumberOfNORPacketsToBackup();
	out.println(nbNORPackets1 + " NOR packets to backup after inserting data in the database");
	int nbRamPPackets1 = Recovery_getTotalNumberOfRamPPacketsToBackup();
	out.println(nbRamPPackets1 + " RamP packets to backup after inserting data in the database");
	int nbFTLPackets1 = Recovery_getTotalNumberOfFTLPacketsToBackup();
	out.println(nbFTLPackets1 + " FTL packets to backup after inserting data in the database");

	byte[][] norPackets1 = new byte[nbNORPackets1][];
	byte[][] nandPackets1 = new byte[nbNANDPackets1][];
	byte[][] RamPPackets1 = new byte[nbRamPPackets1][];
	byte[][] FTLPackets1 = new byte[nbFTLPackets1][];
	out.println("SECOND backup -> database is filled (state 1)");
	byte res1 = Recovery_InitBackup();
	if( res1 == 0 )
	{	
		for(int i = 0 ; i < nbNORPackets1 ; i++)
			norPackets1[i] = Recovery_getNextNORPacketToBackup();
		for(int i = 0 ; i < nbNANDPackets1 ; i++)
			nandPackets1[i] = Recovery_getNextNANDPacketToBackup();
		for(int i = 0 ; i < nbRamPPackets1 ; i++)
			RamPPackets1[i] = Recovery_getNextRamPPacketToBackup();
		for(int i = 0 ; i < nbFTLPackets1 ; i++)
			FTLPackets1[i] = Recovery_getNextFTLPacketToBackup();
		Recovery_FinalizeBackup();
		out.println("SECOND backup completed");
	}

    // ********** 1st recovery: to empty database (no schema) ***************
	out.println("FIRST recovery -> to empty state 0");
	Recovery_InitRestore();
	// The following operations must be performed exactly in this order!!!
	// 1st step: NOR memory must be restored
	for(int i = 0 ; i < nbNORPackets0 ; i++)
		Recovery_restoreNextNORPacket(norPackets0[i]);
	// 2nd step: RamP must be restored
	for(int i = 0 ; i < nbRamPPackets0 ; i++)
		Recovery_restoreNextRamPPacket(RamPPackets0[i]);
	// 3rd step: FTL must be restored
	for(int i = 0 ; i < nbFTLPackets0 ; i++)
		Recovery_restoreNextFTLPacket(FTLPackets0[i]);
	// 4th step: NAND memory must be restored
	for(int i = 0 ; i < nbNANDPackets0 ; i++)
		Recovery_restoreNextNANDPacket(nandPackets0[i]);
	Recovery_FinalizeRestore();
	
	schema = "schemaV4.rsc";
    schemaDesc = t.loadSchema(schema);
    usedId = 404;
    Install_DBMS_MetaData(schemaDesc, usedId);
    
	// re-install QEPs
	QEPng.installExecutionPlans( db );

	dgwi.SCE_Test_DB_NOAC(db, out);

    Desinstall_DBMS_MetaData();

    // load the 2nd DB schema
	schema = "/org/inria/dmsp/schemaV4.rsc";
	schemaDesc = t.loadSchema(schema);
	usedId = 404;
	Install_DBMS_MetaData(schemaDesc, usedId);
	
	// re-install QEPs
	QEPng.installExecutionPlans( db );
    
    // Call the data generator
    DataGenerator_2 dg = new DataGenerator_2(out);
	dg.perf=perf;
	dg.PDS_INSERT_Data_Generated(
			3, 3, 3, 3, 1,
			50, 3, 13, db); 

	java.sql.Statement st = db.createStatement();
	if(perf==0){out.println("EP_INFO_SELECT_STAR");}
	java.sql.ResultSet rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_EVENT_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
	lireResultSet(rs, out);
	
	Save_DBMS_on_disk();

	// ********** 3rd backup: database filled (state 2a) ***************
	int nbNANDPackets2a = Recovery_getTotalNumberOfNANDPacketsToBackup();
	out.println(nbNANDPackets2a + " NAND packets to backup after inserting data in the database");
	int nbNORPackets2a = Recovery_getTotalNumberOfNORPacketsToBackup();
	out.println(nbNORPackets2a + " NOR packets to backup after inserting data in the database");
	int nbRamPPackets2a = Recovery_getTotalNumberOfRamPPacketsToBackup();
	out.println(nbRamPPackets2a + " RamP packets to backup after inserting data in the database");
	int nbFTLPackets2a = Recovery_getTotalNumberOfFTLPacketsToBackup();
	out.println(nbFTLPackets2a + " FTL packets to backup after inserting data in the database");

	byte[][] norPackets2a = new byte[nbNORPackets2a][];
	byte[][] nandPackets2a = new byte[nbNANDPackets2a][];
	byte[][] RamPPackets2a = new byte[nbRamPPackets2a][];
	byte[][] FTLPackets2a = new byte[nbFTLPackets2a][];
	out.println("THIRD backup -> database is filled (state 2a)");

	byte res2 = Recovery_InitBackup();
	if( res2 == 0 )
	{
		for(int i = 0 ; i < nbNORPackets2a ; i++)
			norPackets2a[i] = Recovery_getNextNORPacketToBackup();
		for(int i = 0 ; i < nbNANDPackets2a ; i++)
			nandPackets2a[i] = Recovery_getNextNANDPacketToBackup();
		for(int i = 0 ; i < nbRamPPackets2a ; i++)
			RamPPackets2a[i] = Recovery_getNextRamPPacketToBackup();
		for(int i = 0 ; i < nbFTLPackets2a ; i++)
			FTLPackets2a[i] = Recovery_getNextFTLPacketToBackup();
		Recovery_FinalizeBackup();
		out.println("THIRD backup completed");
	}
	
	dg.PDS_INSERT_Data_Generated(
			1, 1, 1, 1, 2,
			25, 1, 7, db); 

	st = db.createStatement();
	if(perf==0){out.println("EP_INFO_SELECT_STAR");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_EVENT_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
	lireResultSet(rs, out);
	
	Save_DBMS_on_disk();

	// ********** 4th backup: database filled (state 2b) ***************
	int nbNANDPackets2b = Recovery_getTotalNumberOfNANDPacketsToBackup();
	out.println(nbNANDPackets2b + " NAND packets to backup after inserting data in the database");
	int nbNORPackets2b = Recovery_getTotalNumberOfNORPacketsToBackup();
	out.println(nbNORPackets2b + " NOR packets to backup after inserting data in the database");
	int nbRamPPackets2b = Recovery_getTotalNumberOfRamPPacketsToBackup();
	out.println(nbRamPPackets2b + " RamP packets to backup after inserting data in the database");
	int nbFTLPackets2b = Recovery_getTotalNumberOfFTLPacketsToBackup();
	out.println(nbFTLPackets2b + " FTL packets to backup after inserting data in the database");

	byte[][] norPackets2b = new byte[nbNORPackets2b][];
	byte[][] nandPackets2b = new byte[nbNANDPackets2b][];
	byte[][] RamPPackets2b = new byte[nbRamPPackets2b][];
	byte[][] FTLPackets2b = new byte[nbFTLPackets2b][];
	out.println("FOURTH backup -> database is filled (state 2b)");

	byte res2b = Recovery_InitBackup();
	if( res2b == 0 )
	{
		for(int i = 0 ; i < nbNORPackets2b ; i++)
			norPackets2b[i] = Recovery_getNextNORPacketToBackup();
		for(int i = 0 ; i < nbNANDPackets2b ; i++)
			nandPackets2b[i] = Recovery_getNextNANDPacketToBackup();
		for(int i = 0 ; i < nbRamPPackets2b ; i++)
			RamPPackets2b[i] = Recovery_getNextRamPPacketToBackup();
		for(int i = 0 ; i < nbFTLPackets2b ; i++)
			FTLPackets2b[i] = Recovery_getNextFTLPacketToBackup();
		Recovery_FinalizeBackup();
		out.println("FOURTH backup completed");
	}
	
	// ********** 2nd recovery: to filled database (state 1) ***************
	out.println("SECOND recovery -> to filled state 1");
	Recovery_InitRestore();
	// The following operations must be performed exactly in this order!!!
	// 1st step: NOR memory must be restored
	for(int i = 0 ; i < nbNORPackets1 ; i++)
		Recovery_restoreNextNORPacket(norPackets1[i]);
	// 2nd step: RamP must be restored
	for(int i = 0 ; i < nbRamPPackets1 ; i++)
		Recovery_restoreNextRamPPacket(RamPPackets1[i]);
	// 3rd step: FTL must be restored
	for(int i = 0 ; i < nbFTLPackets1 ; i++)
		Recovery_restoreNextFTLPacket(FTLPackets1[i]);
	// 4th step: NAND memory must be restored
	for(int i = 0 ; i < nbNANDPackets1 ; i++)
		Recovery_restoreNextNANDPacket(nandPackets1[i]);
	Recovery_FinalizeRestore();
	
	dgwi.SCE_Test_DB_NOAC(db, out);

    // ********** 3rd recovery: to filled database (state 2b) ***************
	out.println("THIRD recovery -> to filled state 2b");
	Recovery_InitRestore();
	// The following operations must be performed exactly in this order!!!
	// 1st step: NOR memory must be restored
	for(int i = 0 ; i < nbNORPackets2b ; i++)
		Recovery_restoreNextNORPacket(norPackets2b[i]);
	// 2nd step: RamP must be restored
	for(int i = 0 ; i < nbRamPPackets2b ; i++)
		Recovery_restoreNextRamPPacket(RamPPackets2b[i]);
	// 3rd step: FTL must be restored
	for(int i = 0 ; i < nbFTLPackets2b ; i++)
		Recovery_restoreNextFTLPacket(FTLPackets2b[i]);
	// 4th step: NAND memory must be restored (INCREMENTAL)
	for(int i = 0 ; i < nbNANDPackets2a ; i++)
		Recovery_restoreNextNANDPacket(nandPackets2a[i]);
	for(int i = 0 ; i < nbNANDPackets2b ; i++)
		Recovery_restoreNextNANDPacket(nandPackets2b[i]);
	Recovery_FinalizeRestore();

	if(perf==0){out.println("EP_INFO_SELECT_STAR");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_EVENT_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
	lireResultSet(rs, out);

    // ********** 3rd recovery: to filled database (state 2b) ***************
	out.println("FOURTH recovery -> to filled state 2a");
	Recovery_InitRestore();
	// The following operations must be performed exactly in this order!!!
	// 1st step: NOR memory must be restored
	for(int i = 0 ; i < nbNORPackets2a ; i++)
		Recovery_restoreNextNORPacket(norPackets2a[i]);
	// 2nd step: RamP must be restored
	for(int i = 0 ; i < nbRamPPackets2a ; i++)
		Recovery_restoreNextRamPPacket(RamPPackets2a[i]);
	// 3rd step: FTL must be restored
	for(int i = 0 ; i < nbFTLPackets2a ; i++)
		Recovery_restoreNextFTLPacket(FTLPackets2a[i]);
	// 4th step: NAND memory must be restored
	for(int i = 0 ; i < nbNANDPackets2a ; i++)
		Recovery_restoreNextNANDPacket(nandPackets2a[i]);
	Recovery_FinalizeRestore();

	if(perf==0){out.println("EP_INFO_SELECT_STAR");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_INFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_SKTINFO_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_SKTINFO_SELECT_STAR);
	lireResultSet(rs, out);
	if(perf==0){out.println("-----/////// EP_EVENT_SELECT_STAR /////////---------");}
	rs = ((org.inria.jdbc.Statement)st).executeQuery(DMSP_QEP_IDs.EP_TEST.EP_EVENT_SELECT_STAR);
	lireResultSet(rs, out);

	Desinstall_DBMS_MetaData();
	Shutdown_DBMS();
  }
}

