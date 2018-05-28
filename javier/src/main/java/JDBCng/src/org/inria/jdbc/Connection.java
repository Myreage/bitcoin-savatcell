/**
 * 
 */
package org.inria.jdbc;

import java.security.AccessControlException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import org.inria.database.DBEngine;
import org.inria.database.Tools;

import test.jdbc.Macro;

/**
 * @author Alexei Troussov, Quentin Lefebvre
 */
public class Connection implements java.sql.Connection
{
	@SuppressWarnings( "unused" )
	private final String TAG = Connection.class.getSimpleName();

	public DBEngine mDB;	// TODO : change it to private
	private String mUser;
//	private String mPassword;

	private boolean mIsClosed;

	public Connection( DBEngine storage, String user, String pwd )
	{
		mDB = storage;
		mUser = user;
//		mPassword = pwd;

		mIsClosed = true;
	}

	public byte[] getDebugInfo( byte param, byte[] result ) throws SQLException
	{	// TODO : implement this method
		return new byte[0];
	}
	
	private static int parseRight( char c ) throws AccessControlException
	{
		switch ( c )
		{
		case '1':	return 1;
		case '0':	return 0;
		case '?':	return 2;
		}
		throw new AccessControlException(
				"Right " + c + " not in {0,1,?}, cannot be parsed !!" );
	}

	/* return the bits vector of one byte long which describes an authorization
	 * (read/update/delete/insert)
	 *
	 * right can be '0' = deny '1' = grant '?' = no deny, no grant, depends on the
	 * others rights from others categories
	 */
	public static int ComputeAuthorizationBitsVector(
		char readright,
		char updateright,
		char deleteright,
		char insertright
		) throws AccessControlException
	{
		return (((parseRight(readright  ) & 0x00000003) << 6) |
				((parseRight(updateright) & 0x00000003) << 4) |
				((parseRight(deleteright) & 0x00000003) << 2) |
				 (parseRight(insertright) & 0x00000003) );
	}

	/**
	 * Increments global Id
	 * @return Old global Id value
	 * @throws SQLException
	 */
	public int nextGlobalTimestamp() throws SQLException
	{
		int status = mDB.sendData(
				Tools.CMD_TSGLOBAL,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	/**
	 * @return Current global Id value
	 * @throws SQLException
	 */
	public int getGlobalTimestamp() throws SQLException
	{
		int status = mDB.sendData(
				Tools.CMD_TSGLOBAL_NO_INC,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte[] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	/**
	 * Set new Global Id value
	 * @throws SQLException
	 */
	public void setGlobalTimestamp(int ts) throws SQLException
	{
		byte[] buffer = new byte[ Tools.T_SIZEINT + Tools.T_SIZEINT ];
		Tools.int2bytea( Tools.T_SIZEINT, buffer, 0 );
		Tools.int2bytea( ts, buffer, Tools.T_SIZEINT );
		int status = mDB.sendData( Tools.CMD_SETTSGLOBAL, buffer, null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );
	}
	/**
	 * @return Unique database owner Id
	 * @throws SQLException
	 */
	public int getSptIdPatient() throws SQLException
	{
		int status = mDB.sendData(
				Tools.CMD_IDPATIENT,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	/**
	 * Activates fingerprint module
	 * @throws SQLException
	 */
	public void fpActivate() throws Exception
	{
		mDB.call( Tools.CMD_FP_ACTIVATE, null, null, 0, null );
	}
	/**
	 * De-activates fingerprint module
	 * @throws SQLException
	 */
	public void fpDeactivate() throws Exception
	{
		mDB.call( Tools.CMD_FP_DEACTIVATE, null, null, 0, null );
	}
	/**
	 * @return Authenticated fingerprint ID
	 * @throws SQLException
	 */
	public int fpGetAuthenticatedId() throws SQLException
	{
		int status = mDB.sendData(
				Tools.CMD_FP_GET_AUTHENTICATED_ID,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	/**
	 * De-authenticates fingerprint module
	 * @throws SQLException
	 */
	public void fpDeauthenticate() throws Exception
	{
		mDB.call( Tools.CMD_FP_DEAUTHENTICATE, null, null, 0, null );
	}

	/**
	 * Get the status of the microphone
	 * @throws SQLException
	 */
	public short micGetStatus() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_GET_MICROPHONE_STATUS,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ 1 ];
		mDB.receiveData( res );
		return res[ 0 ];
	}
	
	/**
	 * Get the number of recorded messages in the token
	 * @throws SQLException
	 */
	public short micGetNumberOfRecordedMessages() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_GET_NUMBER_OF_RECORDED_MESSAGES,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		return Tools.bytea2short( res );
	}
	
	/**
	 * Get the first recorded message in the token as a byte array
	 * @throws SQLException
	 */
	public byte[] micGetFirstRecordedMessage() throws Exception
	{
		if ( micGetStatus() != 0 )
			return null;		// The microphone driver is not ready yet
		
		int offsetInFile = 0;
		
		int status = mDB.sendData(
				Tools.CMD_GET_FIRST_RECORDED_MESSAGE,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		// Receive the full size of the file
		byte[] fileSize_array = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( fileSize_array );
		int fileSize = Tools.bytea2int( fileSize_array );
		
		if( fileSize == 0 )	// The microphone is probably not ready
			return null;
			
		// Allocate the byte array to store the file
		byte[] recordedMessage = new byte[ fileSize ];

		// Receive the first packet size
		byte [] packetSize_array = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( packetSize_array );
		short packetSize = Tools.bytea2short( packetSize_array );
		
		// Receive the first payload
		byte[] packet = new byte[ packetSize ];
		mDB.receiveData( packet );
		System.arraycopy( packet, 0, recordedMessage, offsetInFile, packet.length );
		offsetInFile += packet.length;
		
		if( offsetInFile == fileSize )
			return recordedMessage;

		do
		{
			status = mDB.sendData(
					Tools.CMD_NEXT_FIRST_RECORDED_MESSAGE,
					null,
					null );
			if ( status != 0 )
				throw new SQLException( "Expected 0, received " + status + "." );
			
			// Receive the next packet size
			mDB.receiveData( packetSize_array );
			packetSize = Tools.bytea2short( packetSize_array );
			
			// Receive the first payload
			packet = new byte[ packetSize ];
			mDB.receiveData( packet );
			System.arraycopy( packet, 0, recordedMessage, offsetInFile, packet.length );
			offsetInFile += packet.length;
		} while( offsetInFile < fileSize );
		
		return recordedMessage;
	}
	
	/**
	 * Deletes the first recorded message in the token
	 * @throws SQLException
	 */
	public void micDeleteFirstRecordedMessage() throws Exception
	{
		mDB.call( Tools.CMD_DELETE_FIRST_RECORDED_MESSAGE, null, null, 0, null );
	}
	
	/**
	 * Simulates recorded messages in the token
	 * @throws SQLException
	 */
	public void micSimulateRecordedMessages() throws Exception
	{
		mDB.call( Tools.CMD_SIMULATE_MIC_RECORDS, null, null, 0, null );
	}
	
	/**
	 * Gets the total number of NAND sectors to backup
	 * @throws SQLException
	 */
	public int recoveryGetTotalNumberOfNANDPacketsToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_TOTAL_NAND_PACKETS_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	
	/**
	 * Gets the total number of NOR packets to backup
	 * @throws SQLException
	 */
	public int recoveryGetTotalNumberOfNORPacketsToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_TOTAL_NOR_PACKETS_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	
	/**
	 * Gets the total number of RamP packets to backup
	 * @throws SQLException
	 */
	public int recoveryGetTotalNumberOfRamPPacketsToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_TOTAL_RAMP_PACKETS_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	
	/**
	 * Gets the total number of FTL packets to backup
	 * @throws SQLException
	 */
	public int recoveryGetTotalNumberOfFTLPacketsToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_TOTAL_FTL_PACKETS_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );
		return Tools.bytea2int( res );
	}
	
	/**
	 * Initializes the backup
	 * @throws SQLException
	 */
	public byte recoveryInitBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_INIT_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte[] res = new byte[ 1 ];
		mDB.receiveData( res );
		return res[ 0 ];
	}
	
	/**
	 * Initializes the restore
	 * @throws SQLException
	 */
	public void recoveryInitRestore() throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_INIT_RESTORE, null, null, 0, null );
	}
	
	/**
	 * Get the next packet of NAND to backup
	 * @throws SQLException
	 */
	public byte[] recoveryGetNextNANDPacketToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_NEXT_NAND_PACKET_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		short packetSize = Tools.bytea2short( res );
		byte result[] = new byte[packetSize];
		mDB.receiveData( result );
		return result;
	}
	
	/**
	 * Get the next packet of NOR to backup
	 * @throws SQLException
	 */
	public byte[] recoveryGetNextNORPacketToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_NEXT_NOR_PACKET_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		short packetSize = Tools.bytea2short( res );
		byte result[] = new byte[packetSize];
		mDB.receiveData( result );
		return result;
	}
	
	/**
	 * Get the next packet of RamP to backup
	 * @throws SQLException
	 */
	public byte[] recoveryGetNextRamPPacketToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_NEXT_RAMP_PACKET_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		short packetSize = Tools.bytea2short( res );
		byte result[] = new byte[packetSize];
		mDB.receiveData( result );
		return result;
	}
	
	/**
	 * Get the next packet of FTL to backup
	 * @throws SQLException
	 */
	public byte[] recoveryGetNextFTLPacketToBackup() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_NEXT_FTL_PACKET_TO_BACKUP,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		short packetSize = Tools.bytea2short( res );
		byte result[] = new byte[packetSize];
		mDB.receiveData( result );
		return result;
	}
	
	/**
	 * Get the next packet of NOR to backup
	 * @throws SQLException
	 */
	public void recoveryRestoreNextNANDPacket(byte[] NANDPacket) throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_RESTORE_NEXT_NAND_PACKET, null, NANDPacket, NANDPacket.length, null );
	}
	
	/**
	 * Get the next packet of NOR to backup
	 * @throws SQLException
	 */
	public void recoveryRestoreNextNORPacket(byte[] NORPacket) throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_RESTORE_NEXT_NOR_PACKET, null, NORPacket, NORPacket.length, null );
	}
	
	/**
	 * Get the next packet of RamP to backup
	 * @throws SQLException
	 */
	public void recoveryRestoreNextRamPPacket(byte[] RamPPacket) throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_RESTORE_NEXT_RAMP_PACKET, null, RamPPacket, RamPPacket.length, null );
	}
	
	/**
	 * Get the next packet of FTL to backup
	 * @throws SQLException
	 */
	public void recoveryRestoreNextFTLPacket(byte[] FTLPacket) throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_RESTORE_NEXT_FTL_PACKET, null, FTLPacket, FTLPacket.length, null );
	}
	
	/**
	 * Finalizes the backup
	 * @throws SQLException
	 */
	public void recoveryFinalizeBackup() throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_FINALIZE_BACKUP, null, null, 0, null );
	}
	
	/**
	 * Finalizes the restore
	 * @throws SQLException
	 */
	public void recoveryFinalizeRestore() throws Exception
	{
		mDB.call( Tools.CMD_RECOVERY_FINALIZE_RESTORE, null, null, 0, null );
	}
	
	/**
	 * Get the RSA public key from the smartcard
	 * @throws SQLException
	 */
	public byte[] recoveryGetRSAPublicKey() throws Exception
	{
		int status = mDB.sendData(
				Tools.CMD_RECOVERY_GET_RSA_PUBLIC_KEY,
				null,
				null );
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte [] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );
		short packetSize = Tools.bytea2short( res );
		byte result[] = new byte[packetSize];
		mDB.receiveData( result );
		return result;
	}
	
	/**
	 * Create database structures for the given DDL schema
	 * @param schema	DDL schema description
	 * @param userid	Owner Id
	 * @throws SQLException
	 */
	public void installSchema( byte[] schema, int userid ) throws SQLException
	{
		mDB.installSchema( schema, userid );
	}
	/**
	 * Removes all database structures
	 * @throws Exception
	 */
	public void uninstallSchema() throws Exception
	{
		mDB.uninstallSchema();
	}
	/**
	 * Tells the JDBC that a schema is already loaded into the token.
	 */
	public void bypassInitialization()
	{
		mDB.bypassInitialization();
	}
	/**
	 * Removes all database data
	 * @throws Exception
	 */
	public void eraseData() throws Exception
	{
		mDB.call( Tools.CMD_EMPTY_DATABASE, null, null, 0, null );
	}

	/**
	 * Removes all database data (wiping NOR)
	 * @throws Exception
	 */
	public void wipeData() throws Exception
	{
		mDB.call( Tools.CMD_RESET_DATABASE, null, null, 0, null );
	}

	public void init( String url ) throws SQLException
	{
		try
		{
			// realizes the connection to the DBMS without user/role
			int pos_ac = url.indexOf( "?", 0 );
			if ( pos_ac == -1 )
			{
				mDB.open( url, mUser, null );
				mIsClosed = false;
				return;
			}
			// computes user/role
			String host = url.substring( 0, pos_ac );
			int pos_sep_user_role = url.indexOf( '&', 0 );
			if ( pos_sep_user_role == -1 )
			{
				throw new SQLException( "Invalid connection URL: " + url );
			}
			int pos_user_val = url.indexOf( '=', 0 );
			int pos_role_val = url.indexOf( '=', pos_sep_user_role );
			if ( pos_role_val == -1 || pos_user_val == -1 )
			{
				throw new SQLException( "Invalid connection URL: " + url );
			}
			// realizes the connection to the DBMS
			mDB.open( host, url.substring( pos_user_val + 1, pos_sep_user_role ), url.substring( pos_role_val + 1 ) );
			mIsClosed = false;
		}
		catch ( SQLException sqle )
		{	// exit in case of error
			mDB.close();
			throw sqle;
		}
		catch ( Throwable t )
		{
			mDB.close();
			throw new SQLException( t.toString() );
		}
		
	}

	@Override
	public void close() throws SQLException
	{
		if ( !mIsClosed )
			mDB.close();
		mIsClosed = true;
	}

	@Override
	public boolean isClosed() throws SQLException
	{
		return mIsClosed;
	}

	@Override
	public boolean isReadOnly() throws SQLException
	{
		return false;
	}

	@Override
	public boolean isValid( int timeout ) throws SQLException
	{
		return mDB.isAttached();
	}

	@Override
	public Blob createBlob() throws SQLException
	{
		return new org.inria.jdbc.Blob( mDB );
	}

	@Override
	public Statement createStatement() throws SQLException
	{
		return new org.inria.jdbc.Statement( mDB );
	}

	@Override
	public PreparedStatement prepareStatement( String sql ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PreparedStatement prepareStatement( String sql, int autoGeneratedKeys ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	
	public PreparedStatement prepareStatement( int sql ) throws SQLException
	{
		return new org.inria.jdbc.PreparedStatement( mDB, sql, Statement.NO_GENERATED_KEYS );
	}
	
	public PreparedStatement prepareStatement( int sql, int autoGeneratedKeys ) throws SQLException
	{
		return new org.inria.jdbc.PreparedStatement( mDB, sql, autoGeneratedKeys );
	}

	@Override
	public void commit() throws SQLException
	{
		mDB.call( Tools.CMD_COMMIT, null, null, 0, null );
	}

	@Override
	public void rollback() throws SQLException
	{
		mDB.call( Tools.CMD_ROLLBACK, null, null, 0, null );
	}

	//////////////////////////////////////////////////////////
	/// Not implemented methods
	//////////////////////////////////////////////////////////

	///
	/// interface java.sql.Wrapper
	///

	@Override
	public <T> T unwrap( Class<T> iface ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	
	@Override
	public boolean isWrapperFor( Class<?> iface ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	///
	/// interface java.sql.Connection
	///

	@Override
	public void clearWarnings() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Struct createStruct( String typeName, Object[] attributes ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Array createArrayOf( String typeName, Object[] elements ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Clob createClob() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public NClob createNClob() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean getAutoCommit() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public String getCatalog() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public String getClientInfo( String name ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Properties getClientInfo() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getHoldability() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getTransactionIsolation() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public String nativeSQL( String sql ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public CallableStatement prepareCall( String sql ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public CallableStatement prepareCall( String sql, int resultSetType, int resultSetConcurrency ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public CallableStatement prepareCall( String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Statement createStatement( int resultSetType, int resultSetConcurrency ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Statement createStatement( int resultSetType, int resultSetConcurrency, int resultSetHoldability )
			throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PreparedStatement prepareStatement( String sql, int[] columnIndexes ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PreparedStatement prepareStatement( String sql, int resultSetType, int resultSetConcurrency )
			throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PreparedStatement prepareStatement( String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public PreparedStatement prepareStatement( String sql, String[] columnNames ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void releaseSavepoint( Savepoint savepoint ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void rollback( Savepoint savepoint ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setAutoCommit( boolean autoCommit ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setCatalog( String catalog ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setClientInfo( String name, String value ) throws SQLClientInfoException
	{
		throw new SQLClientInfoException();
	}

	@Override
	public void setClientInfo( Properties properties ) throws SQLClientInfoException
	{
		throw new SQLClientInfoException();
	}

	@Override
	public void setHoldability( int holdability ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setReadOnly( boolean readOnly ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public Savepoint setSavepoint( String name ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setTransactionIsolation( int level ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setTypeMap( Map<String, Class<?>> map ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	public void insert_failure( byte function_id, byte struct_id, byte struct_type )
	{
		// To enable the failure point
		try
		{
		    byte [] buffer = new byte[3];
		    buffer[0]   = function_id;
		    buffer[1]   = struct_id;
		    buffer[2]   = struct_type;
		    mDB.call(Macro.CMD_INSERT_FAILURE_POINT, null, buffer, buffer.length, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void remove_failure()
	{
		// To disable the failure point
		try
		{
		    mDB.call(Macro.CMD_REMOVE_FAILURE_POINT, null, null, 0, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void insert_tearingpoint( byte function_id )
	{
		// To enable the tearing point
		try
		{
		    byte [] buffer = new byte[1];
		    buffer[0]   = function_id;
		    mDB.call(Macro.CMD_INSERT_TEARING_POINT, null, buffer, buffer.length, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void remove_tearingpoint()
	{
		// To disable the tearing point
		try
		{
		    mDB.call(Macro.CMD_REMOVE_TEARING_POINT, null, null, 0, null);
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void PBFilter_init( byte with_partition, byte n_hashes )
	{
		// To disable the tearing point
		try
		{
		    byte [] buffer = new byte[2];
		    buffer[0]   = with_partition;
		    buffer[1]   = n_hashes;
		    mDB.call(Macro.CMD_PBFILTER_INIT, null, buffer, buffer.length, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void PBFilter_insert_key( String key )
	{
		// To enable the failure point
		try
		{
			mDB.call(Macro.CMD_PBFILTER_INSERT_KEY, key, null, 0, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void PBFilter_delete_key( String key )
	{
		// To enable the failure point
		try
		{
			mDB.call(Macro.CMD_PBFILTER_DELETE_KEY, key, null, 0, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void PBFilter_lookup_key( String key )
	{
		// To enable the failure point
		try
		{
			mDB.call(Macro.CMD_PBFILTER_LOOKUP_KEY, key, null, 0, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void PBFilter_close( byte reset_db )
	{
		// To enable the failure point
		try
		{
			byte [] buffer = new byte[1];
			buffer[0]   = reset_db;
			mDB.call(Macro.CMD_PBFILTER_CLOSE, null, buffer, buffer.length, null);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

}
