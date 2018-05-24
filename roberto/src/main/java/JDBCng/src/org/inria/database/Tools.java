/**
 * 
 */
package org.inria.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Alexei Troussov
 */
public class Tools
{
	private final static int MASK_00FF = 0x000000FF;

	// Request types
//	public static final byte STATE_EXIT		= 0; // never used, but can be
//	public static final byte STATE_DBMS		= 1;
//	public static final byte STATE_TUPLE	= 2;

	// Command codes
	public static final byte CMD_QUERY				=  1;
	public static final byte CMD_QUERYMD			=  8;
	public static final byte CMD_UPDATE				=  2;
	public static final byte CMD_NEXT				=  3;
	public static final byte CMD_CLOSE				=  4;
	public static final byte CMD_COMMIT				=  5;
	public static final byte CMD_INIT				=  6;
	public static final byte CMD_INIT_WITHOUT_CA	= 61;
	public static final byte CMD_INSTALL_META		=  7;
	public static final byte CMD_UPDATE_AND_GET_KEY	= 11;
	public static final byte CMD_SETTSGLOBAL		= 18;
	public static final byte CMD_IDPATIENT			=  9;
	public static final byte CMD_TSGLOBAL			= 10;
	public static final byte CMD_TSGLOBAL_NO_INC	= 12;
	public static final byte CMD_RESET_DATABASE		= 13;
	public static final byte CMD_EMPTY_DATABASE		= 14;
	public static final byte CMD_ROLLBACK			= 17;
	public static final byte CMD_DEBUG				= 19;
//	public static final byte CMD_TUPLE_PRODUCED		= 20;	// Obsolete command
//	public static final byte CMD_NEXT_COLUMN_SIZE	= 21;	// Obsolete command
//	public static final byte CMD_NEXT_COLUMN_COPY	= 22;	// Obsolete command
	// Macros for BLOB management
	public static final byte CMD_BLOB_NEW			= 23;
	public static final byte CMD_BLOB_SET			= 24;
	public static final byte CMD_BLOB_GET			= 25;
	
	// Microphone module commands
	public static final byte CMD_GET_MICROPHONE_STATUS				= 34;
	public static final byte CMD_GET_NUMBER_OF_RECORDED_MESSAGES	= 35;
	public static final byte CMD_GET_FIRST_RECORDED_MESSAGE			= 36;
	public static final byte CMD_NEXT_FIRST_RECORDED_MESSAGE		= 37;
	public static final byte CMD_DELETE_FIRST_RECORDED_MESSAGE		= 38;
	public static final byte CMD_SIMULATE_MIC_RECORDS				= 39;

	// Fingerprint module commands
	public static final byte CMD_FP_ACTIVATE				= 62;
	public static final byte CMD_FP_DEACTIVATE				= 63;
	public static final byte CMD_FP_GET_AUTHENTICATED_ID	= 64;
	public static final byte CMD_FP_DEAUTHENTICATE			= 65;

	// Backup / Recovery module commands
	public static final byte CMD_RECOVERY_GET_TOTAL_NAND_PACKETS_TO_BACKUP	= 77;
	public static final byte CMD_RECOVERY_GET_TOTAL_NOR_PACKETS_TO_BACKUP	= 78;
	public static final byte CMD_RECOVERY_GET_TOTAL_RAMP_PACKETS_TO_BACKUP	= 79;
	public static final byte CMD_RECOVERY_GET_TOTAL_FTL_PACKETS_TO_BACKUP	= 80;
	public static final byte CMD_RECOVERY_INIT_BACKUP						= 81;
	public static final byte CMD_RECOVERY_GET_NEXT_NAND_PACKET_TO_BACKUP	= 82;
	public static final byte CMD_RECOVERY_GET_NEXT_NOR_PACKET_TO_BACKUP		= 83;
	public static final byte CMD_RECOVERY_GET_NEXT_RAMP_PACKET_TO_BACKUP	= 84;
	public static final byte CMD_RECOVERY_GET_NEXT_FTL_PACKET_TO_BACKUP		= 85;
	public static final byte CMD_RECOVERY_FINALIZE_BACKUP					= 86;
	public static final byte CMD_RECOVERY_INIT_RESTORE						= 87;
	public static final byte CMD_RECOVERY_RESTORE_NEXT_NAND_PACKET			= 88;
	public static final byte CMD_RECOVERY_RESTORE_NEXT_NOR_PACKET			= 89;
	public static final byte CMD_RECOVERY_RESTORE_NEXT_RAMP_PACKET			= 90;
	public static final byte CMD_RECOVERY_RESTORE_NEXT_FTL_PACKET			= 91;
	public static final byte CMD_RECOVERY_FINALIZE_RESTORE					= 92;
	public static final byte CMD_RECOVERY_GET_RSA_PUBLIC_KEY			= 93;
	
	// Database data types
	public static final byte T_CHAR			= 0;
	public static final byte T_NUMBER		= 1;
	public static final byte T_DATE			= 2;
	public static final byte T_PK			= 3;
	public static final byte T_BINARY		= 8;
	public static final byte NULL_CHAR		= 4;
	public static final byte NULL_NUMBER	= 5;
	public static final byte NULL_DATE		= 6;
	public static final byte NULL_BINARY	= 7;
	public static final byte T_BLOB			= 9;
	public static final byte NULL_BLOB		=10;
	public static final byte T_VARCHAR		=11;
	public static final byte NULL_VARCHAR	=12;

	// Other constants
//	protected	static final int TUPLE_COLUMN_MAX_SIZE	= 2048;
//	protected	static final int METADATE_FILE_MAX_SIZE	= 3000;
	public		static final int PLAN_PARAMS_MAX_SIZE	= 600; // can be tuned when we have the final query set

	public		static final byte T_SIZEINT			= Integer.SIZE / 8;
	public		static final byte T_SIZESHORT		= Short.SIZE / 8;
	protected	static final byte ERROR_CODE_SIZE	= Integer.SIZE / 8;
	
	// JiBson constants
	protected static final byte 	JIBSON_HEADER_SIZE		= 17;
	protected static final String 	JIBSON_PLUGDB_MODULE_ID	= "PlDB";
	protected static final byte		JIBSON_TOTAL_LEN		= 0;
	protected static final byte 	JIBSON_MSG_ID			= 0;
	protected static final byte 	JIBSON_EOT				= '\0';
	
	/**
	 * Composes JiBson header
	 * 
	 * @param sz binary data length
	 * @return Composed JiBson header
	 * @throws IOException 
	 */
	public static byte[] composeJibsonHeader( int sz ) throws IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write( JIBSON_PLUGDB_MODULE_ID.getBytes() );
		outputStream.write( Tools.int2bytea( (sz + 1), new byte[ Tools.T_SIZEINT ], 0 ) );
		outputStream.write( Tools.int2bytea( sz, new byte[ Tools.T_SIZEINT ], 0 ) );
		outputStream.write( Tools.int2bytea( JIBSON_MSG_ID, new byte[ Tools.T_SIZEINT ], 0 ) );
		outputStream.write( JIBSON_EOT );
		
		return outputStream.toByteArray();
	}
	
	/**
	 * Checks the header of received JiBson packet
	 *
	 * @param t received JiBson packet byte[]
	 * @return Check result
	 */
	public static boolean checkJibsonHeader( byte[] t )
	{
		String moduleID = new String( t, 0, JIBSON_PLUGDB_MODULE_ID.length() );
		int totalLength = bytea2int( t, 4 );
		int binaryLength = bytea2int( t, 8 );
		int msgID = bytea2int( t, 12 );
		byte eot = t[ 16 ];
		boolean retVal = true;
		
		if ( !moduleID.equals( JIBSON_PLUGDB_MODULE_ID ) || msgID != JIBSON_MSG_ID || eot != JIBSON_EOT || totalLength != JIBSON_TOTAL_LEN || binaryLength <= 0 )
			retVal = false;

		return retVal;
	}

	/**
	 * FONCTION CONVERSION byte[] to int
	 */
	public static int bytea2int( byte[] t )
	{
	    return ((t[3] << 24) | ((t[2]&0xff) << 16) | ((t[1]&0xff) << 8) | ((t[0]&0xff)));
	}

	/**
	 * FONCTION CONVERSION byte[] to int, with base pos
	 */
	public static int bytea2int( byte[] t, int pos )
	{
	    return ((t[pos+3] << 24) | ((t[pos+2]&0xff) << 16) | ((t[pos+1]&0xff) << 8) | ((t[pos]&0xff)));
	}

	/**
	 * FONCTION CONVERSION integer to byte[]
	 */
	public static byte[] int2bytea( int i, byte[] t )
	{
		t[ 0 ] = (byte) ( i       );
		t[ 1 ] = (byte) ( i >>  8 );
		t[ 2 ] = (byte) ( i >> 16 );
		t[ 3 ] = (byte) ( i >> 24 );
		return t;
	}

	/**
	 * FONCTION CONVERSION integer to byte[]
	 */
	public static byte[] int2bytea( int i, byte[] t, int index )
	{
		t[ index + 0 ] = (byte) ( i       );
		t[ index + 1 ] = (byte) ( i >>  8 );
		t[ index + 2 ] = (byte) ( i >> 16 );
		t[ index + 3 ] = (byte) ( i >> 24 );
		return t;
	}

	/**
	 * Temp function to convert user and role global Ids to byte[]
	 * for the Connection.open method. Note that bytes are stored
	 * in the order opposite to int2bytea
	 *
	 * @param id the global id
	 * @param t the destination byte[]
	 * @param index position at which the integer will be stored
	 */
	public static void int2byteaInv( String id, byte[] t, int index )
	{
		int i = Integer.parseInt( id );
		t[ index + 3 ] = (byte) (   i          & MASK_00FF );
		t[ index + 2 ] = (byte) ( ( i >>>  8 ) & MASK_00FF );
		t[ index + 1 ] = (byte) ( ( i >>> 16 ) & MASK_00FF );
		t[ index + 0 ] = (byte) ( ( i >>> 24 ) & MASK_00FF );
	}

	/**
	 * FONCTION CONVERSION short to byte[]
	 */
	public static byte[] short2bytea( short s, byte[] t )
	{
		t[ 0 ] = (byte) ( s      );
		t[ 1 ] = (byte) ( s >> 8 );
		return t;
	}

	/**
	 * FONCTION CONVERSION short to byte[]
	 */
	public static byte[] short2bytea( short s, byte[] t, int index )
	{
		t[ index + 0 ] = (byte) ( s      );
		t[ index + 1 ] = (byte) ( s >> 8 );
		return t;
	}

	/**
	 * FONCTION CONVERSION byte[] to short
	 */
	public static short bytea2short( byte[] t )
	{
		return (short) (
				( ( t[ 1 ] & 0xff ) << 8 ) |
				  ( t[ 0 ] & 0xff )
						);
	}

	/**
	 * FONCTION CONVERSION byte to int (because byte is SIGNED)
	 */
	public static int byte2int( byte b ) {
		return ( b < 0 ) ? ( 256 + b ) : b;
	}
}
