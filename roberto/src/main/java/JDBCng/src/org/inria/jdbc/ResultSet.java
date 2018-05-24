/**
 * 
 */
package org.inria.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.inria.database.DBEngine;
import org.inria.database.Tools;

/**
 * @author Alexei Troussov
 */
public class ResultSet implements java.sql.ResultSet
{
	@SuppressWarnings( "unused" )
	private final String TAG = ResultSet.class.getSimpleName();

	private DBEngine mDB;
	private boolean wasNull;
	private ResultSetMetaData metadata;
	private byte[][] row; // storage for the results
	private int numberOfTuplesCachedForCMD_NEXT;
	private int offsetInCacheForCMD_NEXT;
	private byte[] bufferForCMD_NEXT;
	private boolean hasMoreTuples;
	
	/**
	 * Constructs a ResultSet from a metadata description provided by an EP
	 *
	 * @param ep_full
	 * @param client
	 * @throws SQLException
	 */
	public ResultSet( int ep_full, DBEngine db ) throws SQLException
	{
		mDB = db;
		metadata = ResultSetMetaData.build( ep_full );
		mDB.call( Tools.CMD_QUERYMD, null,
				 metadata.meta_to_send, metadata.meta_to_send.length, null );
		row = new byte[ metadata.nb_cols ][];
//row_id = -1;
		numberOfTuplesCachedForCMD_NEXT = 0;
		hasMoreTuples = true;
	}
//private static final String[] rsdata = new String[] {
//"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
//"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
//"Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
//"Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
//"Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
//"Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
//"Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon" };
//private static int row_id = -1;

	/////////////////////////////////////////////
	/////////////////////////////////////////////
	//
	//		Connection Management
	//
	/////////////////////////////////////////////
	/////////////////////////////////////////////

	@Override
	public void close() throws SQLException
	{
		mDB.close();
	}

	/////////////////////////////////////////////
	/////////////////////////////////////////////
	//
	//		METADATA
	//
	/////////////////////////////////////////////
	/////////////////////////////////////////////

	@Override
	public int findColumn( String columnName ) throws SQLException
	{
		for ( int i = 0, nb_cols = metadata.nb_cols; i < nb_cols; ++i )
		{
			if ( metadata.col_names[ i ].equals( columnName ) )
				return i + 1;
		}
		throw new SQLException( "Unknown column name: " + columnName );
	}

	@Override
	public java.sql.ResultSetMetaData getMetaData() throws SQLException
	{
		return metadata;
	}

	/////////////////////////////////////////////
	/////////////////////////////////////////////
	//
	//		MOVEMENTS IN THE RESULTSET
	//
	/////////////////////////////////////////////
	/////////////////////////////////////////////

	@Override
	public boolean next() throws SQLException
	{
		byte[] col_size = new byte[2];
		int size_data;
		
		if( numberOfTuplesCachedForCMD_NEXT == 0 )
		{
			if ( !hasMoreTuples )
			{
				hasMoreTuples = true;
				return false;
			}
			int status = mDB.sendData( Tools.CMD_NEXT, new byte[] { 0 }, null );
			if ( status != 0 )
				throw new SQLException( "Expected 0, received " + status + "." );
	
			byte[] res = new byte[ Tools.T_SIZESHORT ];
			mDB.receiveData( res );
			short packet_size = Tools.bytea2short( res );
	
			if ( packet_size == 0 )
			{
				hasMoreTuples = true;
				return false;
			}
			
			bufferForCMD_NEXT = new byte[ packet_size ];
			mDB.receiveData( bufferForCMD_NEXT );
			
			numberOfTuplesCachedForCMD_NEXT = Tools.bytea2short( bufferForCMD_NEXT );
			hasMoreTuples = (bufferForCMD_NEXT[ 2 ] == 1);
			offsetInCacheForCMD_NEXT = 3;
		}	

		col_size[0] = bufferForCMD_NEXT[ offsetInCacheForCMD_NEXT++ ];
		col_size[1] = bufferForCMD_NEXT[ offsetInCacheForCMD_NEXT++ ];
		size_data = Tools.bytea2short( col_size );
			
		int i = 0;
		while ( size_data != 0 )
		{
			row[ i ] = new byte[ size_data ];
			System.arraycopy(bufferForCMD_NEXT, offsetInCacheForCMD_NEXT, row[ i++ ], 0, size_data);
			offsetInCacheForCMD_NEXT += size_data;

			col_size[0] = bufferForCMD_NEXT[ offsetInCacheForCMD_NEXT++ ];
			col_size[1] = bufferForCMD_NEXT[ offsetInCacheForCMD_NEXT++ ];
			size_data = Tools.bytea2short( col_size );
		}

		numberOfTuplesCachedForCMD_NEXT--;
		return true;
	}

	@Override
	public boolean relative( int rows ) throws SQLException
	{
		if ( rows < 0 )
			throw new SQLException( "Negative relative movements not supported" );
		for ( int i = 0; i < rows; ++i )
		{
			if ( !next() )
				return false;
		}
		return true;
	}

	/////////////////////////////////////////////
	/////////////////////////////////////////////
	//
	//		GETXXX METHODS
	//
	/////////////////////////////////////////////
	/////////////////////////////////////////////

	@Override
	public boolean wasNull() throws SQLException
	{
		return wasNull;
	}

	@Override
	public Date getDate( int columnIndex ) throws SQLException
	{
		int row_index = columnIndex - 1;
		if ( row[ row_index ] == null )
		{
			wasNull = true;
			return null;
		}
		int year =  ( (row[ row_index ][ 2 ] + 256) % 256 ) |
					(((row[ row_index ][ 3 ] + 256) % 256) << 8 );
		wasNull = ( year == 0 );
		if ( wasNull )
			return null;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set( Calendar.YEAR  , year );
		cal.set( Calendar.MONTH , row[ row_index ][ 1 ] - 1 );
		cal.set( Calendar.DATE  , row[ row_index ][ 0 ] );
		cal.set( Calendar.HOUR  , 0 );
		cal.set( Calendar.MINUTE, 0 );
		return new java.sql.Date( cal.getTime().getTime() );
	}

	/**
	 * FIXME: Ugly hack to avoid usage of heavy Calendar and Date
	 */
	public String getRawDate( int index ) throws SQLException
	{
		byte[] r = row[ index - 1 ];
		if ( wasNull = (r == null) )
			return null;
		int val = ( r[ 3 ] << 8 | ( r[ 2 ] & 0xff ) );
		if ( wasNull = (val == 0) )
			return null;
		StringBuilder res = new StringBuilder().append( val ).append( '-' );
		val = r[ 1 ]; // month
		if ( val < 10 )
			res.append( '0' );
		res.append( val ).append( '-' );
		val = r[ 0 ]; // day
		if ( val < 10 )
			res.append( '0' );
		return res.append( val ).toString();
	}

	@Override
	public int getInt( int columnIndex ) throws SQLException
	{
		int res = -1;
		byte[] r = row[ columnIndex - 1 ];
		if ( r != null )
			res = ( (r[3] << 24) |
					( (r[2] & 0xff) << 16) |
					  ( (r[1] & 0xff) << 8) |
					    ( (r[0] & 0xff) ) );
		wasNull = (res == -1);
		return res;
	}

	@Override
	public String getString( int columnIndex ) throws SQLException
	{
//return rsdata[row_id];
		byte[] r = row[ columnIndex - 1 ];
		wasNull = ( r == null || r.length <= 2 );
		return wasNull ? null : new String( r, 2, r.length - 2 );
	}

	@Override
	public byte[] getBytes( int columnIndex ) throws SQLException
	{
		byte[] r = row[ columnIndex - 1 ];
		wasNull = ( r == null || r.length == 0 );
		if ( wasNull )
			return null;
		byte[] res = new byte[ r.length - 2 ];
		System.arraycopy( r, 2, res, 0, res.length );
		return res;
	}

	@Override
	public Blob getBlob( int columnIndex ) throws SQLException
	{
		byte[] r = row[ columnIndex - 1 ];
		wasNull	= ( r == null || r.length == 0 );
		if ( wasNull )
			return null;
		org.inria.jdbc.Blob.BlobId id = new org.inria.jdbc.Blob.BlobId();
		System.arraycopy( r, 0, id.id, 0,
				org.inria.jdbc.Blob.BlobId.BLOB_HASH_SIZE + Tools.T_SIZEINT + Tools.T_SIZEINT );
		return new org.inria.jdbc.Blob( mDB, id );
	}

	@Override
	public Date getDate( String columnName ) throws SQLException { return getDate( findColumn( columnName ) ); }
	@Override
	public int getInt( String columnName ) throws SQLException { return getInt( findColumn( columnName ) ); }
	@Override
	public String getString( String columnName ) throws SQLException { return getString( findColumn( columnName ) ); }
	@Override
	public byte[] getBytes( String columnName ) throws SQLException { return getBytes( findColumn( columnName ) ); }
	@Override
	public Blob getBlob( String columnName ) throws SQLException { return getBlob( findColumn( columnName ) ); }

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
	/// interface java.sql.ResultSetMetaData
	///

	@Override
	public boolean absolute( int row ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void afterLast() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void beforeFirst() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void cancelRowUpdates() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void clearWarnings() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void deleteRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean first() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Array getArray( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Array getArray( String colName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getAsciiStream( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getAsciiStream( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public BigDecimal getBigDecimal( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public BigDecimal getBigDecimal( int columnIndex, int scale ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public BigDecimal getBigDecimal( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public BigDecimal getBigDecimal( String columnName, int scale ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getBinaryStream( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getBinaryStream( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean getBoolean( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean getBoolean( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public byte getByte( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public byte getByte( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Reader getCharacterStream( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Reader getCharacterStream( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Clob getClob( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Clob getClob( String colName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getConcurrency() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getCursorName() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Date getDate( int columnIndex, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Date getDate( String columnName, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public double getDouble( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public double getDouble( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getFetchDirection() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getFetchSize() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public float getFloat( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public float getFloat( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public long getLong( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public long getLong( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Object getObject( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Object getObject( int columnIndex, Map<String, Class<?>> map ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Object getObject( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Object getObject( String columnName, Map<String, Class<?>> map ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Ref getRef( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Ref getRef( String colName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public short getShort( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public short getShort( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Statement getStatement() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Time getTime( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Time getTime( int columnIndex, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Time getTime( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Time getTime( String columnName, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Timestamp getTimestamp( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Timestamp getTimestamp( int columnIndex, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Timestamp getTimestamp( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Timestamp getTimestamp( String columnName, Calendar cal ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getType() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getUnicodeStream( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public InputStream getUnicodeStream( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public URL getURL( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public URL getURL( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public SQLWarning getWarnings() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void insertRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isAfterLast() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isBeforeFirst() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isFirst() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isLast() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean last() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void moveToCurrentRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void moveToInsertRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean previous() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void refreshRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean rowDeleted() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean rowInserted() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean rowUpdated() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void setFetchDirection( int direction ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void setFetchSize( int rows ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateArray( int columnIndex, Array x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateArray( String columnName, Array x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( int columnIndex, InputStream x, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( String columnName, InputStream x, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBigDecimal( int columnIndex, BigDecimal x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBigDecimal( String columnName, BigDecimal x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( int columnIndex, InputStream x, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( String columnName, InputStream x, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( int columnIndex, Blob x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( String columnName, Blob x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBoolean( int columnIndex, boolean x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBoolean( String columnName, boolean x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateByte( int columnIndex, byte x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateByte( String columnName, byte x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBytes( int columnIndex, byte[] x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBytes( String columnName, byte[] x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( int columnIndex, Reader x, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( String columnName, Reader reader, int length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( int columnIndex, Clob x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( String columnName, Clob x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateDate( int columnIndex, Date x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateDate( String columnName, Date x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateDouble( int columnIndex, double x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateDouble( String columnName, double x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateFloat( int columnIndex, float x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateFloat( String columnName, float x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateInt( int columnIndex, int x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateInt( String columnName, int x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateLong( int columnIndex, long x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateLong( String columnName, long x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNull( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNull( String columnName ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateObject( int columnIndex, Object x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateObject( int columnIndex, Object x, int scale ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateObject( String columnName, Object x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateObject( String columnName, Object x, int scale ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateRef( int columnIndex, Ref x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateRef( String columnName, Ref x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateRow() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateShort( int columnIndex, short x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateShort( String columnName, short x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateString( int columnIndex, String x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateString( String columnName, String x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateTime( int columnIndex, Time x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateTime( String columnName, Time x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateTimestamp( int columnIndex, Timestamp x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateTimestamp( String columnName, Timestamp x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public RowId getRowId( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public RowId getRowId( String columnLabel ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateRowId( int columnIndex, RowId value ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateRowId( String columnLabel, RowId value ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getHoldability() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isClosed() throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNString( int columnIndex, String nString ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNString( String columnLabel, String nString ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( int columnIndex, NClob nClob ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( String columnLabel, NClob nClob ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public NClob getNClob( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public NClob getNClob( String columnLabel ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public SQLXML getSQLXML( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public SQLXML getSQLXML( String columnLabel ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateSQLXML( int columnIndex, SQLXML xmlObject ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateSQLXML( String columnLabel, SQLXML xmlObject ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getNString( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getNString( String columnLabel ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Reader getNCharacterStream( int columnIndex ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public Reader getNCharacterStream( String columnLabel ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNCharacterStream( int columnIndex, Reader x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNCharacterStream( String columnLabel, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( int columnIndex, InputStream x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( int columnIndex, InputStream x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( int columnIndex, Reader x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( String columnLabel, InputStream x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( String columnLabel, InputStream x, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( String columnLabel, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( int columnIndex, InputStream inputStream, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( String columnLabel, InputStream inputStream, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( int columnIndex, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( String columnLabel, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( int columnIndex, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( String columnLabel, Reader reader, long length ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNCharacterStream( int columnIndex, Reader x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNCharacterStream( String columnLabel, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( int columnIndex, InputStream x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( int columnIndex, InputStream x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( int columnIndex, Reader x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateAsciiStream( String columnLabel, InputStream x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBinaryStream( String columnLabel, InputStream x ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateCharacterStream( String columnLabel, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( int columnIndex, InputStream inputStream ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateBlob( String columnLabel, InputStream inputStream ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( int columnIndex, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateClob( String columnLabel, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( int columnIndex, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public void updateNClob( String columnLabel, Reader reader ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
