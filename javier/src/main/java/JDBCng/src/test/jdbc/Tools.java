package test.jdbc;

import test.jdbc.DBMSFactory;

import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.inria.database.DBEngine;
import org.inria.usb.Storage;

public class Tools {

	protected Connection db;
	protected DBEngine mStorage = null;
	protected PrintWriter out;
	private boolean initialized = false;
	private byte [] perfs = null;
	public static int perf = 0;

	public byte[] getDebugInfo( byte param, byte[] result ) throws SQLException
	{
		return ((org.inria.jdbc.Connection)db).getDebugInfo( param, result );
	}

	///
	/// Helper method
	private int print_perf_item( String title, int idx )
	{
		String hi, lo;
		out.print( title );
		// open + next + close
		hi = String.valueOf( DBMSFactory.bytea2int(perfs, idx) );
		if ( hi.compareTo("0") != 0 )
			out.print( hi );
		lo = String.valueOf( DBMSFactory.bytea2int(perfs, idx + 4 ) );
		int n = 9 - lo.length();
		while ( n-- > 0 )
			out.print( '0' );
		out.print( lo );
//		out.println( "\t" );
		return idx + 8;
	}
	public void print_perfs_caption() throws SQLException
	{
		out.println("OP;GLOBAL;NOR_ERASE;NOR_WRITE;NANDDIR_ERASE;NANDDIR_WRITE;NANDDIR_READ;NANDDIR_CHECK;NANDFTL_READ;NANDFTL_WRITE;NANDFTL_SYNC;");
	}
	public void print_perfs( byte param ) throws SQLException
	{
		perfs = getDebugInfo( param, perfs );
		int i = 0;
		if( perf == 0 )
		{
			if ( param == 20 || param == 21 )
			{
				out.println("===========================PBIndex========================");
				if ( param == 20 )
					out.print("=========> insert time = ");
				else
					out.print("=========> lookup time = ");
				out.println( DBMSFactory.bytea2int(perfs) );
				out.println("==========================================================");
			}
			else if ( param < 20 )
			{	// TODO: 20 = Maximum number of operators in the query
				out.print( param );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				i = print_perf_item( ";", i );
				out.println(";");
			}
		}
	}

	/*=============================================================================

	Name: lireResultSet

	Abs:  read a ResultSet column by column then go to the next line

	Args: Type			Name		 Access			  Description
   ------------------- -----------	---------- ----------------------------
   java.sql.ResultSet		rs			read/write	  the resultset to read

	Ret:  void

  =============================================================================*/
	public static void lireResultSet( java.sql.ResultSet rs, PrintWriter out ) throws Exception
	{
		if (rs == null) {
			return;
		}
		//Get the Metadata of the ResultSet
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int row_i = 1;

		if(perf==0){
			out.print("Row\t");
		}
		//display the names of each column
		if(perf==0){
			for (int i = 0 ;i < rsmd.getColumnCount() ;i++) {
				out.print(rsmd.getColumnName(i + 1) + "\t");
			}
		}
		if(perf==0){
			out.println();
		}

		//work on each line of the ResultSet
		// so call next to have the next line of the ResultSet
		while (rs.next()) {
			if(perf==0){
				out.print(row_i);
			}
			for (int i = 0 ;i < rsmd.getColumnCount() ;i++) {
				switch (rsmd.getColumnType(i + 1)) {

				case java.sql.Types.VARCHAR :
				case java.sql.Types.CHAR :
					String s = rs.getString(i + 1);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							out.print("\t" + s);
						}
					}
					break;

				case java.sql.Types.DATE :
					java.sql.Date d = rs.getDate(i + 1);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							out.print("\t" + d);
						}
					}
					break;

				case java.sql.Types.INTEGER :
					int k = rs.getInt(i + 1);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							out.print("\t" + k);
						}
					}
					break;

				case java.sql.Types.BINARY :
					byte[] b = rs.getBytes(i + 1);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							out.print("\t0x");
							//out.print("\t");
							for (int j = 0;j < b.length; j++) {
								out.print(byteTo02x(b[j]));
								//out.print(b[j]);
							}
						}
					}
					break;

				case java.sql.Types.BLOB :
					Blob blb = rs.getBlob( i + 1 );
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							org.inria.jdbc.Blob.BlobId blb_id = ((org.inria.jdbc.Blob)blb).getId();
							int hd = blb_id.getHead();
							int sz = blb_id.getSize();
							int pos = 1;
							out.print("\t["+hd+","+sz+"] 0x");
							// FYI: we commented out printing the whole BLOB data, just first 32-byte chunk whould be enough...
							//while ( pos <= sz )
							//{	// get a chunk of BLOB data by 32 bytes at once
								byte[] bb = blb.getBytes( pos, ( sz > 32 ? 32 : sz ) );
								for ( int j=0; j<bb.length; ++j )
									out.print( byteTo02x(bb[j]) );
							//	pos += bb.length;
							//}
						}
					}
					break;

				default :
					throw new SQLException("ERROR : Type unknown in MetaData : column = "
							+ i + 1 + ", type = " + rsmd.getColumnType(i + 1) + ".");
				}
			}
			row_i++;
			if(perf==0){
				out.println();
				out.flush();
			}
		}
		if(perf==0){
			out.println("reach EndOfFile\n");
			out.flush();
		}
	}

	// same with perf=1
	public static void lireResultSet_perf1(java.sql.ResultSet rs, PrintWriter out) throws Exception
	{
		if ( rs != null )
		{	// get the metadata...
			rs.getMetaData();
			// call ResultSet.next() to retrieve all its records...
			while ( rs.next() ) {}
		}
	}

	// same with perf=0
	public static void lireResultSet_perf0(java.sql.ResultSet rs, PrintWriter out) throws Exception
	{
		if (rs == null)
			return;

		int row_i = 1;
		int c;

		//Get the Metadata of the ResultSet
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		out.print("Row\t");
		c = rsmd.getColumnCount()+1;
		//display the names of each column
		for (int i=1; i<c; ++i) {
			out.print(rsmd.getColumnName(i));
			out.print("\t");
		}
		out.println();

		//work on each line of the ResultSet
		// so call next to have the next line of the ResultSet
		while (rs.next()) {
			out.print(row_i);
			for ( int i=1; i<c; ++i ) {
				switch (rsmd.getColumnType(i)) {
				case java.sql.Types.VARCHAR :
				case java.sql.Types.CHAR :
					String s = rs.getString(i);
					if (rs.wasNull()) {
						out.print("\tNULL");
					} else {
						out.print("\t");
						out.print(s);
					}
					break;
				case java.sql.Types.DATE :
					java.sql.Date d = rs.getDate(i);
					if (rs.wasNull()) {
						out.print("\tNULL");
					} else {
						out.print("\t");
						out.print(d);
					}
					break;
				case java.sql.Types.INTEGER :
					int k = rs.getInt(i);
					if (rs.wasNull()) {
						out.print("\tNULL");
					} else {
						out.print("\t");
						out.print(k);
					}
					break;
				case java.sql.Types.BINARY : // never called in the sigmod demo => begin of not optimized code
					byte[] b = rs.getBytes(i);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							out.print("\t0x");
							//out.print("\t");
							for (int j = 0;j < b.length; j++) {
								out.print(byteTo02x(b[j]));
								//out.print(b[j]);
							}
						}
					}
					break;
				case java.sql.Types.BLOB :
					Blob blb = rs.getBlob(i+1);
					if(perf==0){
						if (rs.wasNull()) {
							out.print("\tNULL");
						} else {
							org.inria.jdbc.Blob.BlobId blb_id = ((org.inria.jdbc.Blob)blb).getId();
							int hd = blb_id.getHead();
							int sz = blb_id.getSize();
							int pos = 1;
							out.print("\t[");
							out.print(hd);
							out.print(",");
							out.print(sz);
							out.print("] 0x");
							// FYI: we commented out printing the whole BLOB data, just first 32-byte chunk whould be enough...
							//while ( pos <= sz )
							//{	// get a chunk of BLOB data by 32 bytes at once
								byte[] bb = blb.getBytes( pos, 32 );
								for ( int j=0; j<bb.length; ++j )
									out.print( byteTo02x(bb[j]) );
							//	pos += bb.length;
							//}
						}
					} // sigmod demo => end not optimized code
					break;
				default :
					throw new SQLException("ERROR : Type unknown in MetaData : column = "
							+ i + ", type = " + rsmd.getColumnType(i) + ".");
				}
			}
			row_i++;
			out.println();
			out.flush(); // Do we win something if we skip this flush ?
		}
		out.println("reach EndOfFile\n");
		out.flush();
	}

	/*=============================================================================

	Name: getIdGlobalGetGeneratedKey

	Abs:  read a ResultSet column by column then go to the next line

	Args: Type			Name		 Access			  Description
   ------------------- -----------	---------- ----------------------------
   java.sql.ResultSet		rs			read/write	  the resultset to read

	Ret:  byte[]

	=============================================================================*/
	public static int getIdGlobalGetGeneratedKey(java.sql.ResultSet rs) throws Exception {
		int b = 0;
		if (rs == null) {
			return 0;
		}
		// Get the MetaData of the ResultSet
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			for (int i = 0 ; i < rsmd.getColumnCount() ; i++) {
				switch (rsmd.getColumnType(i + 1)) {
				case java.sql.Types.INTEGER :
					b = rs.getInt(i + 1);
				}
			}
		}
		return b;
	}

	/*=============================================================================

   For the processing of IdGlobals

  =============================================================================*/
	public static String byteTo02x(byte b) {
		String s = Integer.toHexString((b < 0) ? (256 + b) : b);
		if ((b >= 0) && (b <= 15)) {
			s = "0" + s;
		}
		return s;
	}

	public static String IdGlobalFromByteArrayToString(byte[] b) {
		StringBuffer s = new StringBuffer(12);
		s.append("0x");
		for (int j = 3;j >= 0;j--) {
			s.append(byteTo02x(b[j]));
		}
		return s.toString();
	}

	public static String IdGlobalFromIntToString(int i) {
		byte[] b = new byte[4];
		b = DBMSFactory.int2bytea(i, b, 0);
		return IdGlobalFromByteArrayToString(b);
	}

	public void Fingerprint_Activate() throws Exception {
		if(perf==0){
			out.println("Fingerprint module activated");
		}
		((org.inria.jdbc.Connection)db).fpActivate();
	}
	
	public void Fingerprint_Deactivate() throws Exception {
		if(perf==0){
			out.println("Fingerprint module de-activated");
		}
		((org.inria.jdbc.Connection)db).fpDeactivate();
	}
	
	public int Fingerprint_GetAuthenticatedId() throws Exception {
		return ((org.inria.jdbc.Connection)db).fpGetAuthenticatedId();
	}
	
	public void Fingerprint_Deauthenticate() throws Exception {
		if(perf==0){
			out.println("Fingerprint de-authenticated");
		}
		((org.inria.jdbc.Connection)db).fpDeauthenticate();
	}

	public short micGetMicrophoneStatus() throws Exception {
		if(perf==0){
			out.println("Asking microphone's status");
		}
		return ((org.inria.jdbc.Connection)db).micGetStatus();
	}

	public short micGetNumberOfRecordedMessages() throws Exception {
		if(perf==0){
			out.println("Asking number of recorded messages");
		}
		return ((org.inria.jdbc.Connection)db).micGetNumberOfRecordedMessages();
	}

	public byte[] micGetFirstRecordedMessage() throws Exception {
		if(perf==0){
			out.println("Retrieving the first recorded message");
		}
		return ((org.inria.jdbc.Connection)db).micGetFirstRecordedMessage();
	}

	public void micDeleteFirstRecordedMessage() throws Exception {
		if(perf==0){
			out.println("Deleting the first recorded message");
		}
		((org.inria.jdbc.Connection)db).micDeleteFirstRecordedMessage();
	}

	public void micSimulateRecordedMessages() throws Exception {
		if(perf==0){
			out.println("Retrieving the first recorded message");
		}
		((org.inria.jdbc.Connection)db).micSimulateRecordedMessages();
	}

	public int Recovery_getTotalNumberOfNANDPacketsToBackup() throws Exception {
		if(perf==0){
			out.println("Retrieving the total number of NAND packets to backup");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetTotalNumberOfNANDPacketsToBackup();
	}
	
	public byte Recovery_InitBackup() throws Exception {
		if(perf==0){
			out.println("Initializing backup");
		}
		return ((org.inria.jdbc.Connection)db).recoveryInitBackup();
	}
	
	public void Recovery_InitRestore() throws Exception {
		if(perf==0){
			out.println("Initializing restore");
		}
		((org.inria.jdbc.Connection)db).recoveryInitRestore();
	}
	
	public int Recovery_getTotalNumberOfNORPacketsToBackup() throws Exception {
		if(perf==0){
			out.println("Computing the number of NOR packets to backup");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetTotalNumberOfNORPacketsToBackup();
	}

	public int Recovery_getTotalNumberOfRamPPacketsToBackup() throws Exception {
		if(perf==0){
			out.println("Computing the number of RamP packets to backup");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetTotalNumberOfRamPPacketsToBackup();
	}

	public int Recovery_getTotalNumberOfFTLPacketsToBackup() throws Exception {
		if(perf==0){
			out.println("Computing the number of FTL packets to backup");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetTotalNumberOfFTLPacketsToBackup();
	}

	public byte[] Recovery_getNextNANDPacketToBackup() throws Exception {
		if(perf==0){
			out.println("Backup next NAND packet");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetNextNANDPacketToBackup();
	}

	public byte[] Recovery_getNextNORPacketToBackup() throws Exception {
		if(perf==0){
			out.println("Backup next NOR packet");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetNextNORPacketToBackup();
	}
	
	public byte[] Recovery_getNextRamPPacketToBackup() throws Exception {
		if(perf==0){
			out.println("Backup next RamP packet");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetNextRamPPacketToBackup();
	}
	
	public byte[] Recovery_getNextFTLPacketToBackup() throws Exception {
		if(perf==0){
			out.println("Backup next FTL packet");
		}
		return ((org.inria.jdbc.Connection)db).recoveryGetNextFTLPacketToBackup();
	}
	
	public void Recovery_restoreNextNANDPacket(byte[] NANDPacket) throws Exception {
		if(perf==0){
			out.println("Restore next NAND packet");
		}
		((org.inria.jdbc.Connection)db).recoveryRestoreNextNANDPacket(NANDPacket);
	}

	public void Recovery_restoreNextNORPacket(byte[] NORPacket) throws Exception {
		if(perf==0){
			out.println("Restore next NOR packet");
		}
		((org.inria.jdbc.Connection)db).recoveryRestoreNextNORPacket(NORPacket);
	}

	public void Recovery_restoreNextRamPPacket(byte[] RamPPacket) throws Exception {
		if(perf==0){
			out.println("Restore next RamP packet");
		}
		((org.inria.jdbc.Connection)db).recoveryRestoreNextRamPPacket(RamPPacket);
	}

	public void Recovery_restoreNextFTLPacket(byte[] FTLPacket) throws Exception {
		if(perf==0){
			out.println("Restore next FTL packet");
		}
		((org.inria.jdbc.Connection)db).recoveryRestoreNextFTLPacket(FTLPacket);
	}

	public void Recovery_FinalizeBackup() throws Exception {
		if(perf==0){
			out.println("Finalizing backup");
		}
		((org.inria.jdbc.Connection)db).recoveryFinalizeBackup();
	}
	
	public void Recovery_FinalizeRestore() throws Exception {
		if(perf==0){
			out.println("Finalizing restore");
		}
		((org.inria.jdbc.Connection)db).recoveryFinalizeRestore();
	}
	
	/*=============================================================================

	Name: Install_DBMS_MetaData

	Abs:  Install the MetaData of the DBMS embedded in the card

  =============================================================================*/
	public void Install_DBMS_MetaData(byte[] schema, int IdP) throws Exception {
		((org.inria.jdbc.Connection)db).installSchema(schema, IdP);
	}

	/*=============================================================================

	Name: Desinstall_DBMS_MetaData

	Abs:  Desinstall the MetaData of the DBMS embedded in the card

  =============================================================================*/
	public void Desinstall_DBMS_MetaData() throws Exception {
		if(perf==0){
			out.println("Desinstall the MetaData of the DBMS embedded in the card");
		}
		((org.inria.jdbc.Connection)db).uninstallSchema();
	}

	/*=============================================================================

	Name: Empty_DBMS_MetaData

	Abs:  Empty the data of the DBMS embedded in the card

  =============================================================================*/
	public void Empty_DBMS_MetaData() throws Exception {
		if(perf==0){
			out.println("Empty the Data of the DBMS embedded in the card");
		}
		((org.inria.jdbc.Connection)db).eraseData();
	}

	/*=============================================================================

	Name: Save_DBMS_on_disk

	Abs:  Save the data of the DBSM stored in NAND and NOR (which have been emulated).
			NAND and NOR are seved in 3 files :
				NAND_FTL_on_Disk.bin
				NAND_PHY_on_Disk.bin
				NOR_on_Disk.bin

  =============================================================================*/
	public void Save_DBMS_on_disk() throws Exception {
		if(perf==0){
			out.println("Save DBMS");
		}
		db.commit();
	}

	/*=============================================================================

	Name: Shutdown_DBMS

	Abs:  Stop the DBMS

  =============================================================================*/
	public void Shutdown_DBMS() throws Exception {
		if(perf==0){
			out.print("Closing DB connection...");
		}
		db.close();
		
		if (mStorage != null)
		{	// Close virtual COM port connection
			((Storage)mStorage).detach();
			mStorage = null;
		}
		
		if(perf==0){
			out.println(" Done.");
		}
	}

	/*=============================================================================

	Name: Init

	Abs:  Init

  =============================================================================*/
	public void init() throws Exception {
		if (initialized) { // fast exit
		  return;
		}
		initialized = true;
		if(perf==0){
			out.println("Initializing DBMS... ");
		}
//		if (DriverManager.getDrivers().hasMoreElements() == false) {
		Class.forName("org.inria.jdbc.Driver");
//		}
		/* INIT THE DRIVER */
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void Insert_Failure_Point(byte function_id,
									  byte struct_id,
									  byte struct_type) throws Exception {
		if(perf==0){
			out.println("Inserting Failure Point...");
		}
		((org.inria.jdbc.Connection)db).insert_failure(function_id, struct_id, struct_type);
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void Remove_Failure_Point() throws Exception {
		if(perf==0){
			out.println("Removing Failure Point...");
		}
		((org.inria.jdbc.Connection)db).remove_failure();
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void Rollback_Transaction() throws Exception {
		if(perf==0){
			out.println("Rolling back transaction...");
		}
		db.rollback();
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void openConnection(String dbHost, String[] authent) throws Exception {

		if ((dbHost == null) || (dbHost.length() == 0)) {
			throw new IllegalArgumentException("MISSING HOST");
		}

		if(perf==0){
			out.print("Connecting to DB: ");
		}
		
		if (dbHost.substring(0, 3).equals("COM") || dbHost.substring(0, 8).equals("/dev/tty"))
		{	// Connection through virtual COM port
			String dbmsHost = org.inria.jdbc.Driver.DBMS_URL;
			if (authent != null) {
				String authentRequest =	 "?user=" + authent[0] + "&role=" + authent[1];
				dbmsHost += authentRequest;
			}
			if(perf==0)
				out.println(dbmsHost);

			if (mStorage == null) {
				mStorage = Storage.instance();
				org.inria.jdbc.Driver.register();
				( (Storage) mStorage ).attach( dbHost );
			}
			db = DriverManager.getConnection( dbmsHost );
		}
		else
		{	// Connection through ethernet
			String dbmsHost = "dbHost";
			if (authent != null) {
				String authentRequest =	 "?user=" + authent[0] + "&role=" + authent[1];
				dbmsHost += authentRequest;
				dbHost += authentRequest;
			}
			if(perf==0){
				out.println(dbmsHost);
			}
			db = DriverManager.getConnection(dbHost);
		}
		if (authent != null) {
			if(perf==0){
				out.println("Access Granted for UserDMSP = " + authent[0] + " with the Role = " + authent[1]);
			}
		}
		if(perf==0){
			out.println("Done");
		}
	}

	public static java.sql.ResultSet request(
			java.sql.Statement stmt,
			int EP) throws Exception {
		java.sql.ResultSet rs = ((org.inria.jdbc.Statement)stmt).executeQuery(EP);
		return rs;
	}

//	The tearing package has been refactored. Use the new package and
//	not these methods anymore.
	public void Insert_Tearing_Point(byte function_id) throws Exception {
		if(perf==0){
			out.println("Inserting Tearing Point...");
		}
		((org.inria.jdbc.Connection)db).insert_tearingpoint(function_id);
		if(perf==0){
			out.println("Done. ");
		}
	}

//	The tearing package has been refactored. Use the new package and
//	not these methods anymore.
	public void Remove_Tearing_Point() throws Exception {
		if(perf==0){
			out.println("Removing Tearing Point...");
		}
		((org.inria.jdbc.Connection)db).remove_tearingpoint();
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void PBFilter_Init( byte with_partition, byte n_hashes ) throws Exception {
		if(perf==0){
			out.println("Initializing PBFilter...");
		}
		((org.inria.jdbc.Connection)db).PBFilter_init(with_partition, n_hashes);
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void PBFilter_Insert_Key(String key) throws Exception {
		if(perf==0){
			out.println("Inserting a key into PBFilter index...");
		}
		((org.inria.jdbc.Connection)db).PBFilter_insert_key(key);
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void PBFilter_Lookup_Key(String key) throws Exception {
		if(perf==0){
			out.println("Looking up a key from PBFilter index...");
		}
		((org.inria.jdbc.Connection)db).PBFilter_lookup_key(key);
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void PBFilter_Delete_key(String key) throws Exception {
		if(perf==0){
			out.println("Deleting a key from PBFilter index...");
		}
		((org.inria.jdbc.Connection)db).PBFilter_delete_key(key);
		if(perf==0){
			out.println("Done. ");
		}
	}

	public void PBFilter_Close(byte reset_db) throws Exception {
		if(perf==0){
			out.println("Closing PBFilter...");
		}
		((org.inria.jdbc.Connection)db).PBFilter_close(reset_db);
		if(perf==0){
			out.println("Done. ");
		}
	}
}
