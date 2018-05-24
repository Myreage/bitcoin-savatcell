/**
 * 
 */
package org.inria.database;

import java.io.IOException;
import java.sql.SQLException;

/**
 * DBEngine driver wrapper interface
 * 
 * @author Alexei Troussov
 */
public abstract class DBEngine
{
//	private final String TAG = DBEngine.class.getSimpleName();

	protected static DBEngine mInstance = null;	// derived class has to set this to itself

	/**
	 * Reads as many bytes as possible into destination buffer
	 * 
	 * @param buf           destination byte buffer
	 */
	protected abstract void read( byte[] buf ) throws IOException;

	/**
	 * Writes as many bytes as possible from source buffer
	 * 
	 * @param buf           source byte buffer
	 */
	protected abstract void write( byte[] buf ) throws IOException;

	/**
	 * Check if driver is attached
	 * 
	 * @return true if driver is already attached
	 */
	public abstract boolean isAttached();

	/**
	 * Singleton implementation
	 * @return	Class instance or null if it doesn't exist yet
	 */
	public static DBEngine instance()
	{
		return mInstance;
	}

	/**
	 * Low-level routine to send a frame over communication channel to database
	 * @param cmd		Command to send
	 * @param buffer	Data buffer to send
	 * @param res		Result buffer to receive
	 * @return			Return code from database
	 */
	public int sendData( byte cmd, byte[] buffer, byte[] res )
	{
		int status = 0;
		try
		{	// send data to server...
			short len = ( buffer != null ) ? (short)buffer.length : 0;
			byte[] data = new byte[ 1 + len + Tools.JIBSON_HEADER_SIZE ];
			data[ Tools.JIBSON_HEADER_SIZE ] = cmd;
			if ( buffer != null )
				System.arraycopy( buffer, 0, data, 1 + Tools.JIBSON_HEADER_SIZE, len );
			System.arraycopy( Tools.composeJibsonHeader( 1 + len ), 0, data, 0, Tools.JIBSON_HEADER_SIZE );

			// send JiBson header and data
			write( data );
//			System.out.println( "write jibson formatted data: " + java.util.Arrays.toString( data ) );

			// receive JiBson header and ending frame for our call...
			byte[] frame = new byte[ Tools.JIBSON_HEADER_SIZE + Tools.ERROR_CODE_SIZE ];
			read( frame );
			status = Tools.bytea2int( frame, Tools.JIBSON_HEADER_SIZE );
			
			// check received JiBson header if it's corrupted or not
			if ( !Tools.checkJibsonHeader( frame ) )
				status = -1;

			// receive resulting data if status code and JiBson header are OK and there is something to receive...
			if ( status == 0 && res != null )
				read( res );
//			System.out.println( "read jibson formatted data: " + java.util.Arrays.toString( frame ) + java.util.Arrays.toString( res ) );
		}
		catch ( IOException e )
		{
// TODO:
//			Log.d( TAG,
//					"Cannot send data to the host because of underlying exception: '" +
//					e.toString() + "'" );
			status = -1;
		}
		return status;
	}

	/**
	 * Low-level routine to receive a frame over communication channel from database
	 * @param buffer	Data received (only buffer.length() bytes are received)
	 */
	public void receiveData( byte[] buffer )
	{
		try
		{	// receive data and put it into given buffer...
			read( buffer );
		}
		catch ( IOException e )
		{
// TODO:
//			Log.d( TAG,
//					"Cannot receive data from the host because of underlying exception: '" +
//					e.toString() + "'" );
		}
	}

	private static final boolean[] locked = new boolean[ 1 ];
	private static final boolean[] lockedReent = new boolean[ 1 ];
	private boolean initialized = false;

	private void unlockedReent()
	{
		synchronized ( lockedReent )
		{
			if ( lockedReent[ 0 ] )
			{
				lockedReent[ 0 ] = false;
				lockedReent.notify();
			}
		}
	}

	private int call0( byte cmd, String static_plan, byte[] plan_params, int plan_params_size,
			byte[] res ) throws SQLException
	{
		int status = 0;
		if ( static_plan == null )
		{
			if ( plan_params == null )
				status = sendData( cmd, new byte[] { 0 }, res );
			else
			{
				int len = plan_params_size;
				byte[] buffer = new byte[ len + Tools.T_SIZEINT ];
				Tools.int2bytea( plan_params_size, buffer, 0 );
				System.arraycopy( plan_params, 0, buffer, Tools.T_SIZEINT, plan_params_size );
				status = sendData( cmd, buffer, res );
			}
		}
		else
		{
			int static_len = static_plan.length() + 1;
			int len = static_len + plan_params_size;
			byte[] buffer = new byte[ len + Tools.T_SIZEINT ];
			Tools.int2bytea( static_len, buffer, 0 );
			System.arraycopy(
					static_plan.getBytes(),
					0,
					buffer,
					Tools.T_SIZEINT,
					static_len - 1 );
			// put 0 in the end of the static plan...
			buffer[ static_len - 1 + Tools.T_SIZEINT ] = 0;
			if ( plan_params_size > 0 )
				System.arraycopy( plan_params, 0, buffer, Tools.T_SIZEINT + static_len, plan_params_size );
			status = sendData( cmd, buffer, res );
		}
		return status;
	}

	public void call( byte cmd, String static_plan, byte[] plan_params, int plan_params_size, byte[] res ) throws SQLException
	{
		if ( !initialized )
		{
			switch ( cmd )
			{
			case Tools.CMD_INIT:
			case Tools.CMD_INIT_WITHOUT_CA:
			case Tools.CMD_INSTALL_META:
			case Tools.CMD_CLOSE:
			case Tools.CMD_RESET_DATABASE:
			case Tools.CMD_FP_ACTIVATE:
			case Tools.CMD_FP_DEACTIVATE:
			case Tools.CMD_FP_GET_AUTHENTICATED_ID:
			case Tools.CMD_FP_DEAUTHENTICATE:
			case Tools.CMD_GET_MICROPHONE_STATUS:
			case Tools.CMD_GET_NUMBER_OF_RECORDED_MESSAGES:
			case Tools.CMD_GET_FIRST_RECORDED_MESSAGE:
			case Tools.CMD_NEXT_FIRST_RECORDED_MESSAGE:
			case Tools.CMD_DELETE_FIRST_RECORDED_MESSAGE:
			case Tools.CMD_SIMULATE_MIC_RECORDS:
			case Tools.CMD_RECOVERY_INIT_BACKUP:
			case Tools.CMD_RECOVERY_INIT_RESTORE:
			case Tools.CMD_RECOVERY_FINALIZE_BACKUP:
			case Tools.CMD_RECOVERY_FINALIZE_RESTORE:
			case Tools.CMD_RECOVERY_GET_TOTAL_NAND_PACKETS_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_TOTAL_NOR_PACKETS_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_TOTAL_RAMP_PACKETS_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_TOTAL_FTL_PACKETS_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_NEXT_NAND_PACKET_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_NEXT_NOR_PACKET_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_NEXT_RAMP_PACKET_TO_BACKUP:
			case Tools.CMD_RECOVERY_GET_NEXT_FTL_PACKET_TO_BACKUP:
			case Tools.CMD_RECOVERY_RESTORE_NEXT_NAND_PACKET:
			case Tools.CMD_RECOVERY_RESTORE_NEXT_NOR_PACKET:
			case Tools.CMD_RECOVERY_RESTORE_NEXT_RAMP_PACKET:
			case Tools.CMD_RECOVERY_RESTORE_NEXT_FTL_PACKET:
				break;
			default:
				throw new SQLException( "DBMS is not initialized" );
			}
		}

		switch ( cmd )
		{
		case Tools.CMD_QUERY:
		case Tools.CMD_UPDATE:
			synchronized ( lockedReent )
			{
				while ( lockedReent[ 0 ] )
				{
					try
					{
						lockedReent.wait();
					}
					catch ( InterruptedException e )
					{
					}
				}
				lockedReent[ 0 ] = true;
			}
		default:
			break;
		}

		int ret = call0( cmd, static_plan, plan_params, plan_params_size, res );
		if ( ret < 0 )
		{
			unlockedReent();
			throw new SQLException( "1.DBMS Error code = " + ret + " cmd = " + cmd );
		}
		while ( ret > 0 )
		{
			cmd = (byte) ret;
			ret = call0( cmd, null, null, 0, res );
		}
		unlockedReent();
		if ( ret < 0 )	// TODO: make a constant for error codes somewhere
			throw new SQLException( "2.DBMS Error code = " + ret + " cmd = " + cmd );
	}

	/**
	 * Create database structures for the given DDL schema
	 * @param schema	DDL schema description
	 * @param userid	Owner Id
	 * @throws SQLException
	 */
	public void installSchema( byte[] schema, int userid ) throws SQLException
	{
		int len = schema.length;
		byte[] tmp = new byte[ len + Tools.T_SIZEINT ];
		Tools.int2bytea( userid, tmp, 0 );
		System.arraycopy( schema, 0, tmp, Tools.T_SIZEINT, len );
		call( Tools.CMD_INSTALL_META, null, tmp, len + Tools.T_SIZEINT, null );
		initialized = true;
	}

	/**
	 * Removes all database structures
	 * @throws Exception
	 */
	public void uninstallSchema() throws Exception
	{
		call( Tools.CMD_RESET_DATABASE, null, null, 0, null );
		initialized = false;
	}

	/**
	 * Tells the JDBC that a schema is already loaded into the token.
	 */
	public void bypassInitialization()
	{
		initialized = true;
	}

	/**
	 * Open database connection
	 * @param host	unused?!
	 * @param user	user Id
	 * @param role	role Id
	 * @throws SQLException
	 */
	public void open( String host, String user, String role ) throws SQLException
	{
		synchronized ( locked )
		{
			while ( locked[ 0 ] )
			{
				try
				{
					locked.wait();
				}
				catch ( InterruptedException e )
				{
				}
			}
			locked[ 0 ] = true;
		}
		if ( ( user == null ) || ( role == null ) )
		{
			call( Tools.CMD_INIT_WITHOUT_CA, null, null, 0, null );
		}
		else
		{
			byte[] res = new byte[ Tools.T_SIZEINT ];
			byte[] globalId = new byte[ Tools.T_SIZEINT + Tools.T_SIZEINT ];
			Tools.int2byteaInv( user, globalId, 0 );
			Tools.int2byteaInv( role, globalId, Tools.T_SIZEINT );

			call( Tools.CMD_INIT, null, globalId, globalId.length, res );
			if ( Tools.bytea2int( res ) == 0 ) // 1 - access rights are OK, 0 - KO
			{
				locked[ 0 ] = false; // unlock connection (enables next attempts)
				throw new SQLException(
						"UserDMSP = " + user + " with the Role = " + role
						+ " has no access at all to this DMSP" );
			}
		}
	}
	/**
	 * Close database
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		call( Tools.CMD_CLOSE, null, null, 0, null );
		synchronized( locked )
		{
			locked[ 0 ] = false;
			locked.notify();
		}
	}
}
