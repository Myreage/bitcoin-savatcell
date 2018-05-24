/*
 * @(#)Driver.java	1.25 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.sql;

import java.util.Properties;

/**
 * The interface that every driver class must implement.
 * <P>The Java SQL framework allows for multiple database drivers.
 *
 * <P>Each driver should supply a class that implements
 * the Driver interface.
 *
 * <P>The DriverManager will try to load as many drivers as it can
 * find and then for any given connection request, it will ask each
 * driver in turn to try to connect to the target URL.
 *
 * <P>It is strongly recommended that each Driver class should be
 * small and standalone so that the Driver class can be loaded and
 * queried without bringing in vast quantities of supporting code.
 *
 * <P>When a Driver class is loaded, it should create an instance of
 * itself and register it with the DriverManager. This means that a
 * user can load and register a driver by calling
 * <pre>
 *   <code>Class.forName("foo.bah.Driver")</code>
 * </pre>
 *
 * @see DriverManager
 * @see Connection
 */
public interface Driver {

  /**
   * Attempts to make a database connection to the given URL.
   * The driver should return "null" if it realizes it is the wrong kind
   * of driver to connect to the given URL.  This will be common, as when
   * the JDBC driver manager is asked to connect to a given URL it passes
   * the URL to each loaded driver in turn.
   *
   * <P>The driver should throw an <code>SQLException</code> if it is the right
   * driver to connect to the given URL but has trouble connecting to
   * the database.
   *
   * <P>The <code>java.util.Properties</code> argument can be used to pass
   * arbitrary string tag/value pairs as connection arguments.
   * Normally at least "user" and "password" properties should be
   * included in the <code>Properties</code> object.
   *
   * @param url the URL of the database to which to connect
   * @param info a list of arbitrary string tag/value pairs as
   * connection arguments. Normally at least a "user" and
   * "password" property should be included.
   * @return a <code>Connection</code> object that represents a
   *         connection to the URL
   * @exception SQLException if a database access error occurs
   */
  Connection connect(String url, java.util.Properties info)
  throws SQLException;

  /**
   * Retrieves whether the driver thinks that it can open a connection
   * to the given URL.  Typically drivers will return <code>true</code> if they
   * understand the subprotocol specified in the URL and <code>false</code> if
   * they do not.
   *
   * @param url the URL of the database
   * @return <code>true</code> if this driver understands the given URL;
   *         <code>false</code> otherwise
   * @exception SQLException if a database access error occurs
   */
  boolean acceptsURL(String url) throws SQLException;


  /**
   * Retrieves the driver's major version number. Initially this should be 1.
   *
   * @return this driver's major version number
   */
  int getMajorVersion();

  /**
   * Gets the driver's minor version number. Initially this should be 0.
   * @return this driver's minor version number
   */
  int getMinorVersion();


  /**
   * Reports whether this driver is a genuine JDBC
   * Compliant<sup><font size=-2>TM</font></sup> driver.
   * A driver may only report <code>true</code> here if it passes the JDBC
   * compliance tests; otherwise it is required to return <code>false</code>.
   * <P>
   * JDBC compliance requires full support for the JDBC API and full support
   * for SQL 92 Entry Level.  It is expected that JDBC compliant drivers will
   * be available for all the major commercial databases.
   * <P>
   * This method is not intended to encourage the development of non-JDBC
   * compliant drivers, but is a recognition of the fact that some vendors
   * are interested in using the JDBC API and framework for lightweight
   * databases that do not support full database functionality, or for
   * special databases such as document information retrieval where a SQL
   * implementation may not be feasible.
   * @return <code>true</code> if this driver is JDBC Compliant; <code>false</code>
   *         otherwise
   */
  boolean jdbcCompliant();

  DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
		  throws SQLException;
}

