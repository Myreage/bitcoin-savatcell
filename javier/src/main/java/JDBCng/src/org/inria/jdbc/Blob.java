/**
 * 
 */
package org.inria.jdbc;

/**
 * Implementation of the Blob interface to support BLOB SQL data type in JDBC
 *
 * @author Alexei TROUSSOV
 * @date 2011/06/17
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import org.inria.database.DBEngine;
import org.inria.database.Tools;

/**
 * @author Alexei Troussov
 */
public class Blob implements java.sql.Blob
{
	@SuppressWarnings( "unused" )
	private final String TAG = Blob.class.getSimpleName();

	public static class BlobId
	{
		public static final int BLOB_HASH_SIZE = 16;
		public byte[] id;
		public BlobId() { id = new byte[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 }; }
		public int getHead() { return Tools.bytea2int( id ); }
		public void setHead( int h ) { Tools.int2bytea( h, id, 0 ); }
		public int getSize() { return Tools.bytea2int( id, Tools.T_SIZEINT ); }
		public void setSize( int s ) { Tools.int2bytea( s, id, Tools.T_SIZEINT ); }
		public byte[] getHash()
		{
			byte[] temp = new byte[] { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
			for ( int k = Tools.T_SIZEINT + Tools.T_SIZEINT;
					k < Tools.T_SIZEINT + Tools.T_SIZEINT + BLOB_HASH_SIZE - 1; ++k )
				temp[ k - Tools.T_SIZEINT - Tools.T_SIZEINT ] = id[ k ];
			return temp;
		}
		public void setHash( byte[] b )
		{
			for ( int i=0; i<BLOB_HASH_SIZE; ++i )
				id[ i + Tools.T_SIZEINT + Tools.T_SIZEINT ] = b[ i ];
		}
	}

	private DBEngine mDB;
	private BlobId id;

	/**
	 * Send CMD_BLOB_NEW
	 * Used to create new blob object or to abandon existing one
	 * @return New blob head part of id
	 */
	private int newBlob() throws SQLException
	{
		int status = mDB.sendData( Tools.CMD_BLOB_NEW, null, null );

		// receive resulting data if status code is OK...
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte[] res = new byte[ Tools.T_SIZEINT ];
		mDB.receiveData( res );

		return Tools.bytea2int( res );
	}
	/**
	 * Send CMD_BLOB_GET
	 * Used to retrieve length bytes of data of given blob's head from its id at given offset
	 * @param id Blob id
	 * @param offset Offset from blob's head to retrieve data from
	 * @param length Number of bytes to retrieve
	 * @return Received data
	 */
	private byte[] getBlob( long pos, short len ) throws SQLException
	{
		int p = 0;
		byte[] buffer = new byte[ Tools.T_SIZEINT + Tools.T_SIZEINT + Tools.T_SIZESHORT ];
		Tools.int2bytea( id.getHead(), buffer, p );	p += Tools.T_SIZEINT;
		Tools.int2bytea( (int) pos, buffer, p );	p += Tools.T_SIZEINT;
		Tools.short2bytea( len, buffer, p );		p += Tools.T_SIZESHORT;
		int status = mDB.sendData( Tools.CMD_BLOB_GET, buffer, null );

		// receive resulting data if status code is OK...
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		byte[] res = new byte[ Tools.T_SIZESHORT ];
		mDB.receiveData( res );

		short length = Tools.bytea2short( res );
		byte[] retval = new byte[ length ];
		mDB.receiveData( retval );

		return retval;
	}
	/**
	 * Send CMD_BLOB_SET
	 * Used to append data to the specified blob's head and size from its id
	 * @param id Blob id
	 * @param bytes Data to append
	 * @return New blob hash
	 */
	private byte[] setBlob( byte[] bytes ) throws SQLException
	{
		int p = 0;
		final int n = Tools.T_SIZEINT + Tools.T_SIZEINT + BlobId.BLOB_HASH_SIZE;
		byte[] buffer = new byte[ n + Tools.T_SIZESHORT + bytes.length ];
		System.arraycopy( id.id, 0, buffer, p, n ); p += n;// is better than:
		//DBMSFactory.int2bytea( id.getHead(), buffer, p ); p += Tools.T_SIZEINT;
		//DBMSFactory.int2bytea( id.getSize(), buffer, p ); p += Tools.T_SIZEINT;
		//System.arraycopy( id.getHash(), 0, buffer, p, BlobId.BLOB_HASH_SIZE ); p += BlobId.BLOB_HASH_SIZE;
		Tools.short2bytea( (short)bytes.length, buffer, p ); p += Tools.T_SIZESHORT;
		System.arraycopy( bytes, 0, buffer, p, bytes.length );
		int status = mDB.sendData( Tools.CMD_BLOB_SET, buffer, null );

		// receive resulting data if status code is OK...
		if ( status != 0 )
			throw new SQLException( "Expected 0, received " + status + "." );

		// receive new id
		byte [] hash = new byte[ Blob.BlobId.BLOB_HASH_SIZE ];
		mDB.receiveData( hash );
		return hash;
	}

	///
	/// Implementation of the interface java.sql.Blob
	///

	/**
	 * Default constructor used to create a new empty blob object.
	 * Generates new blob identifier as well
	 */
	public Blob( DBEngine db ) throws SQLException
	{
		if ( db == null )
			throw new SQLException( "Wrong connection object" );
		mDB = db;
		id = new BlobId();
		id.setHead( newBlob() );
	}

	/**
	 * Constructor used to create instance of already existing blob object
	 * Specified identifier must not be empty string to be able to attach
	 * to the existing blob object in the database
	 */
	public Blob( DBEngine db, BlobId id /** id of existing blob */ ) throws SQLException
	{
		if ( db == null )
			throw new SQLException( "Wrong connection object" );
		mDB = db;
		if ( id == null )
			throw new SQLException( "Wrong BLOB id" );
		this.id = id;
	}

	/**
	 * Accessor to retrieve blob identifier
	 */
	public BlobId getId()
	{
		return id;
	}

	/*
	 * This method frees the Blob object and releases the resources that it holds
	 * @see java.sql.Blob#free()
	 */
	@Override
	public void free() throws SQLException
	{
		id.setHead( newBlob() );	// re-generate new Id
	}

	/*
	 * Returns the number of bytes in the BLOB value designated by this Blob object
	 * @see java.sql.Blob#length()
	 */
	@Override
	public long length() throws SQLException
	{
		return id.getSize();
	}

	/**
	 * Retrieves all or part of the BLOB value that this Blob object represents, as an array of bytes
	 * @param length should be less than 2^16 for optimal performance
	 * @see java.sql.Blob#getBytes(long, int)
	 */
	@Override
	public byte[] getBytes( long pos, int length ) throws SQLException
	{
		return getBlob( pos-1, (short)length );
	}

	/*
	 * Writes the given array of bytes to the BLOB value that this Blob object represents,
	 * starting at position pos (>1), and returns the number of bytes written
	 * @see java.sql.Blob#setBytes(long, byte[])
	 */
	@Override
	public int setBytes( long pos, byte[] bytes ) throws SQLException
	{
		int sz = id.getSize();
		if ( pos-1 != sz )
			throw new SQLException( "Cannot overwrite BLOB bytes at offset " + (pos-1) + ", BLOB size is " + sz );
		id.setHash( setBlob( bytes ) );
		id.setSize( sz + bytes.length );
		return bytes.length;
	}

	/*
	 * Writes all or part of the given byte array to the BLOB value that this Blob object
	 * represents and returns the number of bytes written
	 * @see java.sql.Blob#setBytes(long, byte[], int, int)
	 */
	@Override
	public int setBytes( long pos, byte[] bytes, int offset, int len ) throws SQLException
	{
		int sz = id.getSize();
		if ( pos-1 != sz )
			throw new SQLException( "Cannot overwrite BLOB bytes at offset " + (pos-1) + ", BLOB size is " + sz );
		if ( offset+len > bytes.length )
			throw new SQLException( "Wrong offset/length parameters in BLOB.setBytes()" );
		byte[] dummy = new byte[ len ];
		for ( int k = offset; k < offset + len; ++k )
			dummy[ k - offset ] = bytes[ k ];
		id.setHash( setBlob( dummy ) );
		id.setSize( sz + len );
		return len;
	}

	/* ======= THE FOLLOWING METHODS ARE LEFT NOT IMPLEMENTED ======== */

	/*
	 * Truncates the BLOB value that this Blob object represents to be length bytes
	 * @see java.sql.Blob#truncate(long)
	 */
	@Override
	public void truncate( long len ) throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}

	/*
	 * Retrieves the BLOB value designated by this Blob instance as a stream
	 * @see java.sql.Blob#getBinaryStream()
	 */
	@Override
	public InputStream getBinaryStream() throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}

	/*
	 * Returns an InputStream object that contains a partial Blob value, starting with
	 * the byte specified by pos, which is length bytes in length
	 * @see java.sql.Blob#getBinaryStream(long, long)
	 */
	@Override
	public InputStream getBinaryStream( long pos, long length ) throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}

	/*
	 * Retrieves a stream that can be used to write to the BLOB value that this Blob object represents
	 * @see java.sql.Blob#setBinaryStream(long)
	 */
	@Override
	public OutputStream setBinaryStream( long pos ) throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}

	/*
	 * Retrieves the byte position at which the specified byte array pattern begins
	 * within the BLOB value that this Blob object represents
	 * @see java.sql.Blob#position(byte[], long)
	 */
	@Override
	public long position( byte[] pattern, long start ) throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}

	/*
	 * Retrieves the byte position in the BLOB value designated by this Blob object at which pattern begins.
	 * @see java.sql.Blob#position(java.sql.Blob, long)
	 */
	@Override
	public long position( java.sql.Blob pattern, long start ) throws SQLException
	{
		throw new java.sql.SQLFeatureNotSupportedException();
	}
}
