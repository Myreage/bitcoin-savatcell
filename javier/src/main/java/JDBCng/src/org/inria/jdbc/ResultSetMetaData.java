/**
 * 
 */
package org.inria.jdbc;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.inria.database.QEPng;
import org.inria.database.Tools;

/**
 * ResultSetMetaData is used to find out about the types
 * and properties of the columns in a ResultSet.
 *
 * @author Alexei Troussov
 */
public class ResultSetMetaData implements java.sql.ResultSetMetaData
{
	@SuppressWarnings( "unused" )
	private final String TAG = ResultSetMetaData.class.getSimpleName();

	private static final String TYPENAME_CHAR = "Character";
	private static final String TYPENAME_DATE = "Date";
	public  static final String TYPENAME_NUMBER = "Number"; // public for AutoGeneratedKeyResultSet
	private static final String TYPENAME_BINARY = "Binary";
	private static final String TYPENAME_BLOB = "Blob";
	private static final String TYPENAME_VARCHAR = "Varchar";

	public static final String META_START = " \u0000 ";
	public static final int META_START_LEN = META_START.length();

	int nb_cols;
	byte[] meta_to_send;
	String[] col_names;

	private byte[] col_types;
	private static Hashtable<Integer, ResultSetMetaData> stored_metadata =
				new Hashtable <Integer, ResultSetMetaData>();

	public static ResultSetMetaData build( int ep_full ) throws SQLException
	{
		ResultSetMetaData metadata = stored_metadata.get( ep_full );
		if ( metadata == null )
		{
			metadata = new ResultSetMetaData( ep_full );
			stored_metadata.put( ep_full, metadata );
		}
		return metadata;
	}

	private ResultSetMetaData( int ep_full ) throws SQLException
	{
		String ep_metadata = QEPng.getMetaData( ep_full );

		StringTokenizer tokens = new StringTokenizer( ep_metadata, " ", false );
		nb_cols = Integer.parseInt( tokens.nextToken() );
		col_types = new byte[ nb_cols ];
		meta_to_send = new byte[ nb_cols + 2 ];
		col_names = new String[ nb_cols ];

		meta_to_send[ 0 ] = (byte) nb_cols;
		for ( int i = 0; i < nb_cols; i++ )
		{
			col_types[ i ] = Byte.parseByte( tokens.nextToken() );
			meta_to_send[ i + 1 ] = Byte.parseByte( tokens.nextToken() );
			col_names[ i ] = tokens.nextToken();
		}
	}

	///
	/// Implementation of interface java.sql.ResultSetMetaData
	///

	/*
	 * @see java.sql.ResultSetMetaData#getColumnCount()
	 */
	@Override
	public int getColumnCount() throws SQLException
	{
		return nb_cols;
	}

	/*
	 * @see java.sql.ResultSetMetaData#getColumnLabel(int)
	 */
	@Override
	public String getColumnLabel( int column ) throws SQLException
	{
		if ( column <= 0 || column > nb_cols )
			throw new SQLException( "Invalid column index" );
		return getColumnName( column ) + ", " + getColumnTypeName( column ) + " = ";
	}

	/*
	 * @see java.sql.ResultSetMetaData#getColumnType(int)
	 */
	@Override
	public int getColumnType( int column ) throws SQLException
	{
		if ( column <= 0 || column > nb_cols )
			throw new SQLException( "Invalid column index" );
		switch ( col_types[ column - 1 ] )
		{
		case Tools.T_CHAR:		return java.sql.Types.CHAR;
		case Tools.T_DATE:		return java.sql.Types.DATE;
		case Tools.T_NUMBER:	return java.sql.Types.INTEGER;
		case Tools.T_BINARY:	return java.sql.Types.BINARY;
		case Tools.T_BLOB:		return java.sql.Types.BLOB;
		case Tools.T_VARCHAR:	return java.sql.Types.VARCHAR;
		default:
			throw new SQLException( "Type not supported" );
		}
	}

	/*
	 * @see java.sql.ResultSetMetaData#getColumnName(int)
	 */
	@Override
	public String getColumnName( int column ) throws SQLException
	{
		if ( column <= 0 || column > nb_cols )
			throw new SQLException( "Invalid column index" );
		return col_names[ column - 1 ];
	}

	/*
	 * @see java.sql.ResultSetMetaData#getColumnTypeName(int)
	 */
	@Override
	public String getColumnTypeName( int column ) throws SQLException
	{
		if ( column <= 0 || column > nb_cols )
			throw new SQLException( "Invalid column index" );
		switch ( col_types[ column - 1 ] )
		{
		case Tools.T_CHAR:		return TYPENAME_CHAR;
		case Tools.T_DATE:		return TYPENAME_DATE;
		case Tools.T_NUMBER:	return TYPENAME_NUMBER;
		case Tools.T_BINARY:	return TYPENAME_BINARY;
		case Tools.T_BLOB:		return TYPENAME_BLOB;
		case Tools.T_VARCHAR:	return TYPENAME_VARCHAR;
		default:
			throw new SQLException( "Type not supported" );
		}
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
	/// interface java.sql.ResultSetMetaData
	///

	@Override
	public String getCatalogName( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getColumnClassName( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getColumnDisplaySize( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getPrecision( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int getScale( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getSchemaName( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public String getTableName( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isAutoIncrement( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isCaseSensitive( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isCurrency( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isDefinitelyWritable( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public int isNullable( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isReadOnly( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isSearchable( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isSigned( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
	@Override
	public boolean isWritable( int column ) throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

}