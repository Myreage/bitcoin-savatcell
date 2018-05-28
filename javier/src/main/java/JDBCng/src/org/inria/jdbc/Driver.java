/**
 * 
 */
package org.inria.jdbc;

import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import org.inria.database.DBEngine;

/**
 * @author Alexei Troussov
 */
public class Driver implements java.sql.Driver
{
	@SuppressWarnings( "unused" )
	private static final String TAG = Driver.class.getSimpleName();
	private static final int _MAJORVERSION = 1;
	private static final int _MINORVERSION = 0;
	private static boolean registered = false;
	
	public static final String DBMS_URL = "jdbc:inria:smis";

	public static void register()
	{
		try
		{
			if ( !registered )
			{
				@SuppressWarnings( "unused" )
				Driver d = new Driver();
				registered = true;
			}
		}
		catch ( java.sql.SQLException sqle )
		{
		}
	}

	protected Driver() throws SQLException
	{
		java.sql.DriverManager.registerDriver( this );
	}

	@Override
	public boolean acceptsURL( String url ) throws SQLException
	{
		int pos_ac = url.indexOf( "?", 0 );
		if ( pos_ac == -1 )
			return url.equals( DBMS_URL );
		else
			return url.substring( 0, pos_ac ).equals( DBMS_URL);
	}

	@Override
	public Connection connect( String url, Properties info ) throws SQLException
	{
		Connection conn = null;
		try
		{
			if ( acceptsURL( url ) )
			{
				String user = null;
				String password = null;
				if ( info != null )
				{
					user = (String) info.get( "user" );
					password = (String) info.get( "password" );
				}
				conn = new Connection( DBEngine.instance(), user, password );
				conn.init(url);
			}
		}
		catch ( Exception e )
		{
			throw new SQLException(
					"Cannot load connection class because of underlying exception: '" +
					e.toString() + "'" );
		}
		return conn;
	}

	@Override
	public int getMajorVersion()
	{
		return _MAJORVERSION;
	}

	@Override
	public int getMinorVersion()
	{
		return _MINORVERSION;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo( String url, Properties info ) throws SQLException
	{
		DriverPropertyInfo[] p = new DriverPropertyInfo[ 2 ];
		p[ 0 ] = new DriverPropertyInfo( "user", "" );
		p[ 0 ].required = true;
		p[ 1 ] = new DriverPropertyInfo( "password", "" );
		p[ 1 ].required = true;
		return p;
		// FYI: we implement abstract method here to make compiler happy
		// return null;
	}

	/**
	 * Report whether the driver is a genuine JDBC complaint driver.
	 * A driver may only report "true" here if it passes the JDBC compliance
	 * tests, otherwise it is required to return false.
	 * JDBC compliance requires full support for the JDBC API
	 * and full support for SQL 92 Entry Level.
	 */
	@Override
	public boolean jdbcCompliant()
	{
		return false;
	}
}
